package store.view.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.Order;
import store.exception.InvalidInputException;

class OrderInputParserTest {

    private final OrderInputParser orderInputParser;

    OrderInputParserTest() {
        this.orderInputParser = new OrderInputParser();
    }

    @Test
    @DisplayName("parseOrder 정상 동작 테스트")
    void parseOrder() {
        // given
        final String input = "[콜라-10],[사이다-3]";
        final List<Order> expectedResult = List.of(new Order("콜라", 10), new Order("사이다", 3));

        // when
        final List<Order> result = orderInputParser.parseOrders(input);

        // when
        assertThat(result).containsExactlyElementsOf(expectedResult);
    }

    @Test
    @DisplayName("parseOrder는 형식에 맞지 않은 입력에 대해 IllegalArgumentException을 던진다.")
    void parseOrder_WithInvalidFormat_ThrowIllegalArgumentException() {
        // given
        final String input1 = "콜라-10,사이다-3";
        final String input2 = "[콜라-10-1],[사이다-3]";

        // when & then
        assertThatThrownBy(
                () -> orderInputParser.parseOrders(input1)
        ).isInstanceOf(InvalidInputException.class);
        assertThatThrownBy(
                () -> orderInputParser.parseOrders(input2)
        ).isInstanceOf(InvalidInputException.class);
    }
}
