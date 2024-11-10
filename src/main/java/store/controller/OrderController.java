package store.controller;

import java.util.List;
import store.domain.Order;
import store.exception.InvalidInputException;
import store.service.ProductStockService;
import store.view.InputErrorView;
import store.view.OrderInputView;

public class OrderController {

    private final ProductStockService productStockService;

    public OrderController(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    public void order() {
        List<Order> orders = inputOrders();
    }

    private List<Order> inputOrders() {
        List<Order> orders = null;
        while (orders == null) {
            orders = attemptInputOrders();
        }
        return orders;
    }

    private List<Order> attemptInputOrders() {
        try {
            List<Order> orders = OrderInputView.order(productStockService.generateStockStatus());
            return orders;
        } catch (InvalidInputException e) {
            InputErrorView.announce(e);
            return null;
        }
    }
}
