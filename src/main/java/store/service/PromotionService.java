package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import store.domain.Promotion;

public class PromotionService {

    public boolean isPromotionIncludeNowDate(Promotion promotion) {
        return isPromotionIncludeDate(promotion, DateTimes.now());
    }

    public boolean isPromotionIncludeDate(Promotion promotion, LocalDateTime date) {
        LocalDateTime startDate = promotion.getStartDate();
        LocalDateTime endDate = promotion.getEndDate();

        return (date.isAfter(startDate) && date.isBefore(endDate))
                || date.isEqual(startDate)
                || date.isEqual(endDate);
    }
}
