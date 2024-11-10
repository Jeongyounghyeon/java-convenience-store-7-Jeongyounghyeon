package store;

import store.configuration.Configuration;
import store.controller.OrderController;
import store.exception.ApplicationInitException;
import store.repository.ProductStockRepository;
import store.repository.PromotionRepository;
import store.service.OrderService;
import store.service.ProductStockService;
import store.service.PromotionService;
import store.service.ReceiptService;

public class Application {

    private final OrderController orderController;
    private final OrderService orderService;
    private final ProductStockService productStockService;
    private final PromotionService promotionService;
    private final ReceiptService receiptService;
    private final ProductStockRepository productStockRepository;
    private final PromotionRepository promotionRepository;

    public Application() {
        this.promotionRepository =
                new PromotionRepository(Configuration.PROMOTION_RESOURCE_PATH);
        this.productStockRepository =
                new ProductStockRepository(Configuration.PRODUCT_STOCK_RESOURCE_PATH, promotionRepository);
        this.productStockService =
                new ProductStockService(productStockRepository);
        this.promotionService = new PromotionService(productStockService);
        this.receiptService = new ReceiptService();
        this.orderService =
                new OrderService(productStockService, promotionService, receiptService);
        this.orderController =
                new OrderController(orderService, promotionService, productStockService);
    }

    public static void main(String[] args) {
        Application application;
        try {
            application = new Application();
            application.run();
        } catch(ApplicationInitException e) {
            System.out.println(e.getMessage());
        }
    }

    private void run() {
        orderController.order();
    }
}
