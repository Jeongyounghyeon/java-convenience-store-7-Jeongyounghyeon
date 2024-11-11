package store.domain.order;

public class OrderResult {

    private final String productName;
    private int quantity;
    private int giftQuantity;
    private int amount;
    private int promotionDiscount;
    private int promotionAppliedAmount;

    public OrderResult(String productName, int quantity, int giftQuantity, int amount, int promotionDiscount, int promotionAppliedAmount) {
        this.productName = productName;
        this.quantity = quantity;
        this.giftQuantity = giftQuantity;
        this.amount = amount;
        this.promotionDiscount = promotionDiscount;
        this.promotionAppliedAmount = promotionAppliedAmount;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getGiftQuantity() {
        return giftQuantity;
    }

    public int getAmount() {
        return amount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getPromotionAppliedAmount() {
        return promotionAppliedAmount;
    }
}
