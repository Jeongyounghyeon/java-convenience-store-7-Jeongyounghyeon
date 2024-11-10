package store.view;

import java.text.DecimalFormat;
import java.util.List;
import store.domain.receipt.Receipt;
import store.domain.receipt.ReceiptGiftDetail;
import store.domain.receipt.ReceiptProductDetail;
import store.domain.receipt.ReceiptTotalDetail;

public class ReceiptShowView {

    private static final String INTRO = "============W 편의점============\n";
    private static final String GIFT_DETAIL_INTRO = "============증  정============\n";
    private static final String TOTAL_DETAIL_INTRO = "==============================\n";
    private static final String PRODUCT_QUANTITY_AMOUNT_FORMAT = "%-10s %5s %10s\n";
    private static final String PRODUCT_QUANTITY_FORMAT = "%-10s %5s\n";
    private static final String PRODUCT_AMOUNT_FORMAT = "%-10s       %10s\n";


    public static void showReceipt(Receipt receipt) {
        System.out.print(INTRO);
        showProductDetails(receipt.getProductDetails());
        showGiftDetails(receipt.getGiftDetails());
        showTotalDetail(receipt.getTotalDetails());
    }

    private static void showProductDetails(List<ReceiptProductDetail> productDetails) {
        System.out.printf(PRODUCT_QUANTITY_AMOUNT_FORMAT, "상품명", "수량", "금액");
        for (ReceiptProductDetail productDetail : productDetails) {
            String name = productDetail.getName();
            String quantity = getIntString(productDetail.getQuantity());
            String amount = getIntString(productDetail.getAmount());

            System.out.printf(PRODUCT_QUANTITY_AMOUNT_FORMAT, name, quantity, amount);
        }
    }

    private static void showGiftDetails(List<ReceiptGiftDetail> giftDetails) {
        System.out.print(GIFT_DETAIL_INTRO);
        for (ReceiptGiftDetail giftDetail : giftDetails) {
            String name = giftDetail.getName();
            String quantity = getIntString(giftDetail.getQuantity());

            System.out.printf(PRODUCT_QUANTITY_FORMAT, name, quantity);
        }
    }

    private static void showTotalDetail(ReceiptTotalDetail totalDetails) {
        String totalPurchaseQuantity = getIntString(totalDetails.getTotalPurchaseQuantity());
        String totalPurchaseAmount = getIntString(totalDetails.getTotalPurchaseAmount());
        String promotionDiscount = getIntString(totalDetails.getPromotionDiscount());
        String membershipDiscount = getIntString(totalDetails.getMembershipDiscount());
        String amountToPay = getIntString(totalDetails.getAmountToPay());
        System.out.printf(TOTAL_DETAIL_INTRO);
        System.out.printf(PRODUCT_QUANTITY_AMOUNT_FORMAT, "총구매액", totalPurchaseQuantity, totalPurchaseAmount);
        System.out.printf(PRODUCT_AMOUNT_FORMAT, "행사할인", "-" + promotionDiscount);
        System.out.printf(PRODUCT_AMOUNT_FORMAT, "멤버십할인", "-" + membershipDiscount);
        System.out.printf(PRODUCT_AMOUNT_FORMAT, "내실돈", amountToPay);

    }

    private static String getIntString(int value) {
        return new DecimalFormat("###,###").format(value);
    }
}
