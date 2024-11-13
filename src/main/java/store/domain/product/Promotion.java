package store.domain.product;

import java.time.LocalDateTime;
import java.util.Objects;

public class Promotion {

    protected final String name;
    protected final int buy;
    protected final int get;
    protected final LocalDateTime startDate;
    protected final LocalDateTime endDate;

    public Promotion(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Promotion promotion)) {
            return false;
        }
        return buy == promotion.buy && get == promotion.get && Objects.equals(name, promotion.name)
                && Objects.equals(startDate, promotion.startDate) && Objects.equals(endDate,
                promotion.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buy, get, startDate, endDate);
    }
}
