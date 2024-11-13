package store.dto;

import store.domain.product.PromotionProduct;
import store.domain.stock.ProductStock;

public class ProductStockStatus {

    private final String productName;
    private final int price;
    private final int quantity;
    private final String promotion;

    private ProductStockStatus(String productName, int price, int quantity, String promotion) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static ProductStockStatus of(String productName, int price, int quantity, String promotion) {
        return new ProductStockStatus(productName, price, quantity, promotion);
    }

    public static ProductStockStatus from(ProductStock productStock) {
        String promotion = "";
        if (productStock.getProductDetail() instanceof PromotionProduct) {
            promotion = ((PromotionProduct) productStock.getProductDetail()).getPromotion().getName();
        }
        return ProductStockStatus.of(
                productStock.getProductDetail().getName(),
                productStock.getProductDetail().getPrice(),
                productStock.getQuantity(),
                promotion
        );
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }
}
