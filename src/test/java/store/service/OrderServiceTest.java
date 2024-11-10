package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.configuration.Configuration;
import store.domain.Order;
import store.exception.InsufficientStockException;
import store.exception.NotFoundProductException;
import store.repository.ProductStockRepository;
import store.repository.PromotionRepository;

class OrderServiceTest {

    private final OrderService orderService;

    OrderServiceTest() {
        PromotionRepository promotionRepository =
                new PromotionRepository(Configuration.PROMOTION_RESOURCE_PATH);
        ProductStockRepository productStockRepository =
                new ProductStockRepository(Configuration.PRODUCT_STOCK_RESOURCE_PATH, promotionRepository);
        ProductStockService productStockService =
                new ProductStockService(productStockRepository);
        this.orderService = new OrderService(productStockService);
    }

    @Test
    @DisplayName("validateOrder 정상 통과 테스트")
    void validateOrder_WithValidOrder() {
        Order order = new Order("콜라", 3);

        assertThatCode(() ->orderService.validateOrder(order))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("validateOrder는 없는 이름의 상품에 대해 NotFoundProductException을 발생한다.")
    void validateOrder_WithNonExistProductName_ThrowNotFoundProductException() {
        Order order = new Order("환타", 4);

        assertThatThrownBy(() ->orderService.validateOrder(order))
                .isInstanceOf(NotFoundProductException.class);
    }

    @Test
    @DisplayName("validateOrder는 재고가 부족한 상품에 대해 InsufficientStockException을 발생한다.")
    void validateOrder_WithNonExistProductName_returnTrue() {
        Order order = new Order("콜라", 21);

        assertThatThrownBy(() ->orderService.validateOrder(order))
                .isInstanceOf(InsufficientStockException.class);
    }
}
