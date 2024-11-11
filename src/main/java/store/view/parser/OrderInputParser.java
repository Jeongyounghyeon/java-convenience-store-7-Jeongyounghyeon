package store.view.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import store.domain.order.Order;
import store.exception.InvalidInputException;

public class OrderInputParser {

    private static final String INPUT_FORMAT_EXCEPTION_MESSAGE = "올바르지 않은 형식으로 입력했습니다.";
    private static final String ORDER_OPEN_BRACKET = "[";
    private static final String ORDER_CLOSE_BRACKET = "]";

    public OrderInputParser() {
    }

    public List<Order> parseOrders(String ordersInput) {
        List<String> orderInputs = Arrays.stream(ordersInput.split(",")).toList();

        List<Order> orders = new ArrayList<>();
        for (String orderInput : orderInputs) {
            String orderInputRemovedBracket = orderInput;
            orders.add(parseOrder(orderInputRemovedBracket));
        }
        return orders;
    }

    private Order parseOrder(String input) {
        return parseOrderRemovedBracket(removeBracket(input));
    }

    private Order parseOrderRemovedBracket(String input) {
        List<String> splitResult = Arrays.stream(input.split("-")).toList();
        if (splitResult.size() != 2) {
            throw new InvalidInputException(INPUT_FORMAT_EXCEPTION_MESSAGE);
        }
        String productName = parseProductName(splitResult.get(0));
        int quantity = parseQuantity(splitResult.get(1));

        return new Order(productName, quantity);
    }

    private String parseProductName(String input) {
        return input.trim();
    }

    private int parseQuantity(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException(INPUT_FORMAT_EXCEPTION_MESSAGE);
        }
    }

    private String removeBracket(String input) {
        if (!input.startsWith(ORDER_OPEN_BRACKET) || !input.endsWith(ORDER_CLOSE_BRACKET)) {
            throw new InvalidInputException(INPUT_FORMAT_EXCEPTION_MESSAGE);
        }
        return input.substring(1, input.length() - 1);
    }
}
