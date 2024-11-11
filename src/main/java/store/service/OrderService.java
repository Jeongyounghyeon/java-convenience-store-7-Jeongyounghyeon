package store.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.Order;
import store.domain.OrderResult;
import store.domain.Promotion;
import store.domain.product.Product;
import store.domain.product.PromotionProduct;
import store.domain.receipt.Receipt;
import store.domain.stock.ProductStock;
import store.exception.InsufficientStockException;
import store.exception.NotFoundProductException;

public class OrderService {

    private final ProductStockService productStockService;
    private final PromotionService promotionService;
    private final ReceiptService receiptService;

    public OrderService(
            ProductStockService productStockService,
            PromotionService promotionService,
            ReceiptService receiptService
    ) {
        this.productStockService = productStockService;
        this.promotionService = promotionService;
        this.receiptService = receiptService;
    }

    public Receipt order(List<Order> orders, boolean membershipDiscountApplied) {
        List<OrderResult> orderResults = new ArrayList<>();
        for (Order order : orders) {
            orderResults.add(order(order));
        }
        return receiptService.generateReceipt(orderResults, membershipDiscountApplied);
    }

    private OrderResult order(Order order) {
        OrderProcessor orderProcessor = OrderProcessor.from(order);
        Product productDetail = productStockService.findProductDetailByProductName(order.getProductName());
        int giftQuantity = takeoutPromotionGift(order.getProductName(), orderProcessor);
        takeoutNonPromotionStock(orderProcessor);
        return generateOrderResult(order, giftQuantity, productDetail, orderProcessor.promotionAppliedAmount);
    }

    private OrderResult generateOrderResult(
            Order order,
            int giftQuantity,
            Product productDetail,
            int promotionAppliedAmount
    ) {
        return new OrderResult(
                order.getProductName(),
                order.getQuantity(),
                giftQuantity,
                productDetail.getPrice() * order.getQuantity(),
                productDetail.getPrice() * giftQuantity,
                promotionAppliedAmount
        );
    }

    private int takeoutPromotionGift(String productName, OrderProcessor orderProcessor) {
        List<ProductStock> promotionStocks = productStockService.findPromotionStockByProductName(productName);
        if (promotionStocks.isEmpty()) {
            return 0;
        }
        ProductStock promotionStock = promotionStocks.get(0);
        Promotion promotion = ((PromotionProduct) promotionStock.getProductDetail()).getPromotion();
        if (!promotionService.isPromotionIncludeNowDate(promotion)) {
            return 0;
        }
        return takeoutPromotionGift(promotionStock, promotion, orderProcessor);
    }

    private int takeoutPromotionGift(ProductStock promotionStock, Promotion promotion, OrderProcessor orderProcessor) {
        int availableTakeoutQuantity = Math.min(promotionStock.getQuantity(), orderProcessor.getQuantity());
        productStockService.takeoutStock(promotionStock.getProductDetail(), availableTakeoutQuantity);
        orderProcessor.setQuantity(orderProcessor.getQuantity() - availableTakeoutQuantity);

        int promotionSetQuantity = promotion.getBuy() + promotion.getGet();
        int giftQuantity = availableTakeoutQuantity / promotionSetQuantity;
        int priceOfProduct = promotionStock.getProductDetail().getPrice();
        orderProcessor.setPromotionAppliedAmount(promotionSetQuantity * giftQuantity * priceOfProduct);
        return giftQuantity;
    }

    private void takeoutNonPromotionStock(OrderProcessor orderProcessor) {
        if (orderProcessor.getQuantity() == 0) {
            return;
        }

        ProductStock nonPromotionStockSample =
                productStockService.findNonPromotionStockByProductName(orderProcessor.getProductName()).get();
        productStockService.takeoutStock(nonPromotionStockSample.getProductDetail(), orderProcessor.quantity);
    }

    public void validateOrder(Order order) {
        validateProductName(order.getProductName());
        validateStockQuantity(order.getProductName(), order.getQuantity());
    }

    private void validateProductName(String productName) {
        if (!productStockService.isExistStockByProductName(productName)) {
            throw new NotFoundProductException();
        }
    }

    private void validateStockQuantity(String productName, int quantity) {
        int stockQuantity = productStockService.getStockQuantityByProductName(productName);
        if (stockQuantity < quantity) {
            throw new InsufficientStockException();
        }
    }

    private static class OrderProcessor {

        private String productName;
        private int promotionAppliedAmount;
        private int quantity;

        public OrderProcessor(String productName, int quantity) {
            this.productName = productName;
            this.quantity = quantity;
        }

        public static OrderProcessor from(Order order) {
            return new OrderProcessor(order.getProductName(), order.getQuantity());
        }

        public String getProductName() {
            return productName;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setPromotionAppliedAmount(int promotionAppliedAmount) {
            this.promotionAppliedAmount = promotionAppliedAmount;
        }
    }
}
