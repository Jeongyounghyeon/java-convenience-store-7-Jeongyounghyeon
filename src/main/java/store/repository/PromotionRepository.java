package store.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.Promotion;
import store.exception.RepositoryInitException;

public class PromotionRepository extends FileRepository {

    private final List<Promotion> promotions;

    public PromotionRepository(String filePath) {
        super(filePath);
        try {
            this.promotions = initPromotions();
        } catch (RuntimeException e) {
            throw new RepositoryInitException();
        }
    }

    public List<Promotion> findAll() {
        return promotions;
    }

    private List<Promotion> initPromotions() {
        List<Promotion> promotions = new ArrayList<>();

        for (Map<String, String> lineData : getTableData()) {
            Promotion newPromotion = lineDataToPromotion(lineData);
            promotions.add(newPromotion);
        }
        return promotions;
    }

    private Promotion lineDataToPromotion(Map<String, String> lineData) {
        String name = lineData.get("name");
        int buy = Integer.parseInt(lineData.get("buy"));
        int get = Integer.parseInt(lineData.get("get"));
        LocalDateTime startDate = LocalDate.parse(lineData.get("start_date")).atStartOfDay();
        LocalDateTime endDate = LocalDate.parse(lineData.get("end_date")).atTime(LocalTime.MAX);

        return new Promotion(name, buy, get, startDate, endDate);
    }
}
