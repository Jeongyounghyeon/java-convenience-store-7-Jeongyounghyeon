package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;

class PromotionServiceTest {

    private final PromotionService promotionService;

    PromotionServiceTest() {
        this.promotionService = new PromotionService();
    }

    @Test
    @DisplayName("isPromotionIncludeDate 정상 작동 테스트")
    public void isPromotionIncludeDate() {
        Promotion promotion = generateSelfPromotion();
        LocalDateTime trueTime = LocalDateTime.of(2024, Month.JANUARY, 1, 12, 0);
        LocalDateTime falseTime = LocalDateTime.of(2024, Month.JANUARY, 2, 12, 0);

        boolean resultTrue = promotionService.isPromotionIncludeDate(promotion, trueTime);
        boolean resultFalse = promotionService.isPromotionIncludeDate(promotion, falseTime);

        assertThat(resultTrue).isTrue();
        assertThat(resultFalse).isFalse();
    }

    Promotion generateSelfPromotion() {
        String name = "내맘대로프로모션";
        int buy = 1;
        int get = 1;
        LocalDateTime startDate = LocalDate.of(2024, Month.JANUARY, 1).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(2024, Month.JANUARY, 1).atTime(LocalTime.MAX);

        return new Promotion(name, buy, get, startDate, endDate);
    }
}
