package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.configuration.Configuration;
import store.domain.Promotion;
import store.domain.product.Product;
import store.domain.product.PromotionProduct;
import store.domain.stock.ProductStock;
import store.repository.ProductStockRepository;
import store.repository.PromotionRepository;

class ProductStockServiceTest {

    private final ProductStockRepository productStockRepository;
    private final ProductStockService productStockService;

    ProductStockServiceTest() {
        PromotionRepository promotionRepository =
                new PromotionRepository(Configuration.PROMOTION_RESOURCE_PATH);
        this.productStockRepository =
                new ProductStockRepository(Configuration.PRODUCT_STOCK_RESOURCE_PATH, promotionRepository);
        this.productStockService = new ProductStockService(productStockRepository);
    }

    @Test
    @DisplayName("isSufficientStock는 재고가 충분하지 않은 입력일 때, false를 반환한다.")
    public void isSufficientStock_WhenInSufficientProductName_ReturnFalse() {
        Product product = new Product("콜라", 1000);

        boolean result = productStockService.isSufficientStock(product, 11);

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("isSufficientStock는 재고가 충분한 입력일 때, true를 반환한다.")
    public void isSufficientStock_WhenSufficientProductName_ReturnTrue() {
        Product product = new Product("콜라", 1000);

        boolean result = productStockService.isSufficientStock(product, 10);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("decreaseStock는 수량만큼 상품의 재고에서 차감한다.")
    public void decreaseStock() {
        Product product = new Product("콜라", 1000);

        productStockService.decreaseStock(product, 5);

        ProductStock stock = productStockRepository.findByProduct(product).get();
        assertThat(stock.getQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("decreaseStock는 프로모션 상품에 대해서도 잘 작용한다.")
    public void decreaseStock_WithPromotionProduct() {
        Promotion carbonatePromotion = generateFlashDiscountPromotion();
        Product product = new PromotionProduct("콜라", 1000, carbonatePromotion);

        productStockService.decreaseStock(product, 5);

        ProductStock stock = productStockRepository.findByProduct(product).get();
        assertThat(stock.getQuantity()).isEqualTo(5);
    }

    private Promotion generateFlashDiscountPromotion() {
        String name = "반짝할인";
        int buy = 1;
        int get = 1;
        LocalDateTime startDate = LocalDate.of(2024, Month.NOVEMBER, 1).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(2024, Month.NOVEMBER, 30).atTime(LocalTime.MAX);

        return new Promotion(name, buy, get, startDate, endDate);
    }
}
