package store.domain.receipt;

public class ReceiptGiftDetail {

    private final String name;
    private final int quantity;

    public ReceiptGiftDetail(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
