package store.domain.stock;

import java.util.Objects;
import store.domain.product.Product;

public class ProductStock extends Stock {

    protected final Product productDetail;

    public ProductStock(Product productDetail, int quantity) {
        super(quantity);
        this.productDetail = productDetail;
    }

    public Product getProductDetail() {
        return productDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductStock that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(productDetail, that.productDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), productDetail);
    }
}
