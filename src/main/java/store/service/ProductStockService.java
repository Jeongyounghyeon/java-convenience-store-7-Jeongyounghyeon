package store.service;

import java.util.List;
import java.util.Optional;
import store.domain.Promotion;
import store.domain.product.Product;
import store.domain.product.PromotionProduct;
import store.domain.stock.ProductStock;
import store.domain.stock.Stock;
import store.repository.ProductStockRepository;

public class ProductStockService {

    private final ProductStockRepository productStockRepository;

    public ProductStockService(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    public boolean isSufficientStock(Product product, int quantity) {
        int stockQuantity = getStockQuantityByProduct(product);

        if (stockQuantity < quantity) {
            return false;
        }
        return true;
    }

    public void decreaseStock(Product product, int quantity) {
        ProductStock stock = productStockRepository.findByProduct(product).get();
        int stockQuantity = stock.getQuantity();
        stock.setQuantity(stockQuantity - quantity);
    }

    private int getStockQuantityByProduct(Product product) {
        ProductStock stock = productStockRepository.findByProduct(product).get();
        return stock.getQuantity();
    }
}
