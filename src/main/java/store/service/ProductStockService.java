package store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.domain.Promotion;
import store.domain.product.Product;
import store.domain.product.PromotionProduct;
import store.domain.stock.ProductStock;
import store.domain.stock.Stock;
import store.dto.ProductStockStatus;
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

    public boolean isExistStockByProductName(String productName) {
        List<ProductStock> productStock = productStockRepository.findByProductName(productName);
        if (productStock.isEmpty()) {
            return false;
        }
        return true;
    }

    public int getStockQuantityByProductName(String productName) {
        List<ProductStock> stocksWithProductName = productStockRepository.findByProductName(productName);
        return stocksWithProductName.stream()
                .mapToInt(ProductStock::getQuantity).sum();
    }

    private int getStockQuantityByProduct(Product product) {
        ProductStock stock = productStockRepository.findByProduct(product).get();
        return stock.getQuantity();
    }

    public List<ProductStockStatus> generateStockStatus() {
        List<ProductStockStatus> productStockStatuses = new ArrayList<>();
        List<ProductStock> productStocks = productStockRepository.findAll();
        for (ProductStock productStock : productStocks) {
            productStockStatuses.add(ProductStockStatus.from(productStock));
        }
        return productStockStatuses;
    }
}
