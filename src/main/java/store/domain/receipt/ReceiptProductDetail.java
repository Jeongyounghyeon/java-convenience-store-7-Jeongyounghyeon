package store.domain.receipt;

public class ReceiptProductDetail {

    private final String name;
    private final int quantity;
    private final int amount;

    public ReceiptProductDetail(String name, int quantity, int amount) {
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAmount() {
        return amount;
    }
}
