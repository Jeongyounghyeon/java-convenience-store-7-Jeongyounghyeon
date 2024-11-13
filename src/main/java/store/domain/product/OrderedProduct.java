package store.domain.product;

public class OrderedProduct {

    private final Product productDetail;
    private final int quantity;

    public OrderedProduct(Product productDetail, int quantity) {
        this.productDetail = productDetail;
        this.quantity = quantity;
    }

    public Product getProductDetail() {
        return productDetail;
    }

    public int getQuantity() {
        return quantity;
    }
}
