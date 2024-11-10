package store.controller;

import java.util.ArrayList;
import java.util.List;
import store.domain.Order;
import store.exception.InvalidArgumentException;
import store.exception.InvalidInputException;
import store.service.OrderService;
import store.service.ProductStockService;
import store.service.PromotionService;
import store.view.InputErrorView;
import store.view.OrderInputView;
import store.view.PromotionAddSelectView;

public class OrderController {

    private final OrderService orderService;
    private final PromotionService promotionService;
    private final ProductStockService productStockService;

    public OrderController(OrderService orderService, PromotionService promotionService, ProductStockService productStockService) {
        this.orderService = orderService;
        this.promotionService = promotionService;
        this.productStockService = productStockService;
    }

    public void order() {
        List<Order> orders = inputValidOrders();
        orders = applyPromotion(orders);
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

    private List<Order> applyPromotion(List<Order> orders) {
        List<Order> ordersAppliedPromotion = new ArrayList<>();
        for (Order order : orders) {
            ordersAppliedPromotion.add(applyPromotion(order));
        }

        return ordersAppliedPromotion;
    }

    private Order applyPromotion(Order order) {
        int additionalQuantity = promotionService.getAvailablePromotionQuantity(order);
        if (additionalQuantity != 0 && inputPromotionAddSelect(order.getProductName(), additionalQuantity)) {
            return new Order(order.getProductName(), order.getQuantity() + additionalQuantity);
        }
        return order;
    }

    private boolean inputPromotionAddSelect(String productName, int additionalQuantity) {
        Boolean select = null;
        while (select == null) {
            select = attemptInputPromotionAddSelect(productName, additionalQuantity);
        }
        return select;
    }

    private Boolean attemptInputPromotionAddSelect(String productName, int additionalQuantity) {
        try {
            Boolean select = PromotionAddSelectView.select(productName, additionalQuantity);
            return select;
        } catch (InvalidInputException e) {
            InputErrorView.announce(e);
            return null;
        }
    }
}
