package store.service;

import store.domain.Order;
import store.exception.InsufficientStockException;
import store.exception.NotFoundProductException;

public class OrderService {

    private final ProductStockService productStockService;

    public OrderService(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    public void validateOrder(Order order) {
        validateProductName(order.getProductName());
        validateStockQuantity(order.getProductName(), order.getQuantity());
    }

    private void validateProductName(String productName) {
        if (!productStockService.isExistStockByProductName(productName)) {
            throw new NotFoundProductException();
        }
    }

    private void validateStockQuantity(String productName, int quantity) {
        int stockQuantity = productStockService.getStockQuantityByProductName(productName);
        if (stockQuantity < quantity) {
            throw new InsufficientStockException();
        }
    }
}
