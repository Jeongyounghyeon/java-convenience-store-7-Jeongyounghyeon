package store.controller;

import java.util.List;
import store.domain.Order;
import store.exception.InvalidArgumentException;
import store.exception.InvalidInputException;
import store.service.OrderService;
import store.service.ProductStockService;
import store.view.InputErrorView;
import store.view.OrderInputView;

public class OrderController {

    private final OrderService orderService;
    private final ProductStockService productStockService;

    public OrderController(OrderService orderService, ProductStockService productStockService) {
        this.orderService = orderService;
        this.productStockService = productStockService;
    }

    public void order() {
        List<Order> orders = inputValidOrders();
    }

    private List<Order> inputValidOrders() {
        List<Order> orders;
        do {
            orders = attemptInputOrders();
        } while (orders == null);
        return orders;
    }

    private List<Order> attemptInputOrders() {
        try {
            List<Order> orders = OrderInputView.order(productStockService.generateStockStatus());
            orders.forEach(orderService::validateOrder);
            return orders;
        } catch (InvalidInputException | InvalidArgumentException e) {
            InputErrorView.announce(e);
            return null;
        }
    }
}
