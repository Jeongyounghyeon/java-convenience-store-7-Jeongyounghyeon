package store.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.OrderResult;
import store.domain.receipt.Receipt;
import store.domain.receipt.ReceiptGiftDetail;
import store.domain.receipt.ReceiptProductDetail;
import store.domain.receipt.ReceiptTotalDetail;

public class ReceiptService {

    public Receipt generateReceipt(List<OrderResult> orderResults, boolean membershipDiscountApplied) {
        return new Receipt(
                generateProductDetails(orderResults),
                generateGiftDetails(orderResults),
                generateTotalDetail(orderResults, membershipDiscountApplied)
        );
    }

    private List<ReceiptProductDetail> generateProductDetails(List<OrderResult> orderResults) {
        List<ReceiptProductDetail> productDetails = new ArrayList<>();
        for (OrderResult orderResult : orderResults) {
            productDetails.add(generateProductDetail(orderResult));
        }
        return productDetails;
    }

    private ReceiptProductDetail generateProductDetail(OrderResult orderResult) {
        return new ReceiptProductDetail(
                orderResult.getProductName(),
                orderResult.getQuantity(),
                orderResult.getAmount()
        );
    }

    private List<ReceiptGiftDetail> generateGiftDetails(List<OrderResult> orderResults) {
        List<ReceiptGiftDetail> giftDetails = new ArrayList<>();
        for (OrderResult orderResult : orderResults) {
            if (orderResult.getGiftQuantity() != 0) {
                giftDetails.add(generateGiftDetail(orderResult));
            }
        }
        return giftDetails;
    }


    private ReceiptGiftDetail generateGiftDetail(OrderResult orderResult) {
        return new ReceiptGiftDetail(
                orderResult.getProductName(),
                orderResult.getGiftQuantity()
        );
    }

    private ReceiptTotalDetail generateTotalDetail(List<OrderResult> orderResults, boolean membershipDiscountApplied) {
        int totalPurchaseAmount = orderResults.stream().mapToInt(OrderResult::getAmount).sum();
        int promotionDiscount = orderResults.stream().mapToInt(OrderResult::getPromotionDiscount).sum();
        int promotionAppliedAmount = orderResults.stream().mapToInt(OrderResult::getPromotionAppliedAmount).sum();
        int membershipDiscount =
                getMembershipDiscount(membershipDiscountApplied, totalPurchaseAmount, promotionAppliedAmount);
        return new ReceiptTotalDetail(
                orderResults.stream().mapToInt(OrderResult::getQuantity).sum(),
                totalPurchaseAmount,
                promotionDiscount,
                membershipDiscount,
                totalPurchaseAmount - promotionDiscount - membershipDiscount
        );
    }

    private static int getMembershipDiscount(
            boolean membershipDiscountApplied,
            int totalPurchaseAmount,
            int promotionAppliedAmount
    ) {
        int membershipDiscount = 0;
        if (membershipDiscountApplied) {
            membershipDiscount = Math.min((totalPurchaseAmount - promotionAppliedAmount) * 30 / 100, 8000);
        }
        return membershipDiscount;
    }
}
