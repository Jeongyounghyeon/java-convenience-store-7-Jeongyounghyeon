package store.domain.product;

import java.util.Objects;

public class PromotionProduct extends Product {

    protected final Promotion promotion;

    public PromotionProduct(String name, int price, Promotion promotion) {
        super(name, price);
        this.promotion = promotion;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PromotionProduct that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(promotion, that.promotion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), promotion);
    }
}
