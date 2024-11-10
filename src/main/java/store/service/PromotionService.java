package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.List;
import store.domain.Order;
import store.domain.Promotion;
import store.domain.product.PromotionProduct;
import store.domain.stock.ProductStock;

public class PromotionService {

    private final ProductStockService productStockService;

    public PromotionService(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    public int getAvailablePromotionQuantity(Order order) {
        final String productName = order.getProductName();
        if (!productStockService.isRemainingPromotionStockByProductName(productName)) {
            return 0;
        }

        List<ProductStock> promotionStock = productStockService.findPromotionStockByProductName(productName);
        return getAvailablePromotionQuantity(promotionStock.get(0), order.getQuantity());
    }

    public boolean isPromotionIncludeNowDate(Promotion promotion) {
        return isPromotionIncludeDate(promotion, DateTimes.now());
    }

    public boolean isPromotionIncludeDate(Promotion promotion, LocalDateTime date) {
        LocalDateTime startDate = promotion.getStartDate();
        LocalDateTime endDate = promotion.getEndDate();

        return (date.isAfter(startDate) && date.isBefore(endDate))
                || date.isEqual(startDate)
                || date.isEqual(endDate);
    }

    private int getAvailablePromotionQuantity(ProductStock promotionStock, int orderQuantity) {
        if (!isQualifyForPromotion(promotionStock, orderQuantity)) {
            return 0;
        }
        return calculateAdditionalQuantity(promotionStock, orderQuantity);
    }

    private boolean isQualifyForPromotion(ProductStock promotionStock, int orderQuantity) {
        int stockQuantity = promotionStock.getQuantity();
        Promotion promotion = ((PromotionProduct) promotionStock.getProductDetail()).getPromotion();
        return stockQuantity >= orderQuantity && isPromotionIncludeNowDate(promotion);
    }

    private int calculateAdditionalQuantity(ProductStock promotionStock, int orderQuantity) {
        Promotion promotion = ((PromotionProduct) promotionStock.getProductDetail()).getPromotion();
        int setQuantity = promotion.getBuy() + promotion.getGet();
        int remaining = orderQuantity % setQuantity;
        if (remaining >= promotion.getBuy()) {
            int additionalQuantity = (setQuantity - remaining) % setQuantity;
            return Math.min(promotionStock.getQuantity() - orderQuantity, additionalQuantity);
        }
        return 0;
    }
}
