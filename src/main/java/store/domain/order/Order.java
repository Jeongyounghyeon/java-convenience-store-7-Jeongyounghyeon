package store.domain.order;

import java.util.Objects;

public class Order {

    protected final String productName;
    protected final int quantity;

    public Order(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order order)) {
            return false;
        }
        return quantity == order.quantity && Objects.equals(productName, order.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, quantity);
    }
}
