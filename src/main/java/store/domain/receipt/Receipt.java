package store.domain.receipt;

import java.util.List;

public class Receipt {

    private final List<ReceiptProductDetail> productDetails;
    private final List<ReceiptGiftDetail> giftDetails;
    private final ReceiptTotalDetail totalDetails;

    public Receipt(
            List<ReceiptProductDetail> productDetails,
            List<ReceiptGiftDetail> giftDetails,
            ReceiptTotalDetail totalDetails
    ) {
        this.productDetails = productDetails;
        this.giftDetails = giftDetails;
        this.totalDetails = totalDetails;
    }

    public List<ReceiptProductDetail> getProductDetails() {
        return productDetails;
    }

    public List<ReceiptGiftDetail> getGiftDetails() {
        return giftDetails;
    }

    public ReceiptTotalDetail getTotalDetails() {
        return totalDetails;
    }
}
