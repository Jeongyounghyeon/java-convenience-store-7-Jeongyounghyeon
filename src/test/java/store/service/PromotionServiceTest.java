package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.configuration.Configuration;
import store.domain.Order;
import store.domain.Promotion;
import store.repository.ProductStockRepository;
import store.repository.PromotionRepository;

class PromotionServiceTest {

    private final PromotionService promotionService;

    PromotionServiceTest() {
        PromotionRepository promotionRepository =
                new PromotionRepository(Configuration.PROMOTION_RESOURCE_PATH);
        ProductStockRepository productStockRepository =
                new ProductStockRepository(Configuration.PRODUCT_STOCK_RESOURCE_PATH, promotionRepository);
        ProductStockService productStockService =
                new ProductStockService(productStockRepository);
        this.promotionService = new PromotionService(productStockService);
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

    @Test
    @DisplayName("getAvailablePromotionQuantity 정상 작동 테스트")
    public void getAvailablePromotionQuantity() {
        Order avaiablePromotionOrder = new Order("콜라", 9);
        Order unavailablePromotionOrder1 = new Order("콜라", 10);
        Order unavailablePromotionOrder2 = new Order("콜라", 11);
        Order unavailablePromotionOrder3 = new Order("컵라면", 1);

        int availableResult = 1;
        int unavailableResult = 0;

        int result1 = promotionService.getAvailablePromotionQuantity(avaiablePromotionOrder);
        int result2 = promotionService.getAvailablePromotionQuantity(unavailablePromotionOrder1);
        int result3 = promotionService.getAvailablePromotionQuantity(unavailablePromotionOrder2);
        int result4 = promotionService.getAvailablePromotionQuantity(unavailablePromotionOrder3);

        assertThat(result1).isEqualTo(availableResult);
        assertThat(result2).isEqualTo(unavailableResult);
        assertThat(result3).isEqualTo(unavailableResult);
        assertThat(result4).isEqualTo(unavailableResult);
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
