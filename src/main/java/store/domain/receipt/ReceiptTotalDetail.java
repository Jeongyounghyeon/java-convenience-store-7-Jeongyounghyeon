package store.domain.receipt;

public class ReceiptTotalDetail {

    private final int totalPurchaseQuantity;
    private final int totalPurchaseAmount;
    private final int promotionDiscount;
    private final int membershipDiscount;
    private final int amountToPay;

    public ReceiptTotalDetail(
            int totalPurchaseQuantity,
            int totalPurchaseAmount,
            int promotionDiscount,
            int membershipDiscount,
            int amountToPay
    ) {
        this.totalPurchaseQuantity = totalPurchaseQuantity;
        this.totalPurchaseAmount = totalPurchaseAmount;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.amountToPay = amountToPay;
    }

    public int getTotalPurchaseQuantity() {
        return totalPurchaseQuantity;
    }

    public int getTotalPurchaseAmount() {
        return totalPurchaseAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getAmountToPay() {
        return amountToPay;
    }
}
