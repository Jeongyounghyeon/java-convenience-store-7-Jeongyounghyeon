package store.service;

import store.domain.Order;
import store.exception.NotFoundProductException;

public class OrderService {

    private final ProductStockService productStockService;

    public OrderService(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    public void validateOrder(Order order) {
        validateProductName(order.getProductName());
    }

    private void validateProductName(String productName) {
        if (!productStockService.isExistStockByProductName(productName)) {
            throw new NotFoundProductException();
        }
    }
}
