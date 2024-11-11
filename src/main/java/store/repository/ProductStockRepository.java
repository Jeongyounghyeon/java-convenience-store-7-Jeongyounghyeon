package store.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import store.domain.product.Promotion;
import store.domain.product.Product;
import store.domain.product.PromotionProduct;
import store.domain.stock.ProductStock;
import store.exception.RepositoryInitException;

public class ProductStockRepository extends FileRepository {

    private static final String NOT_MATCH_WITH_PROMOTION_EXCEPTION = "프로모션 정보가 존재하지 않는 프로모션입니다.";

    private final List<ProductStock> productStocks;

    public ProductStockRepository(String filePath, PromotionRepository promotionRepository) {
        super(filePath);
        try {
            productStocks = initProductStocks(promotionRepository);
        } catch (RuntimeException e) {
            throw new RepositoryInitException();
        }
    }

    public List<ProductStock> findAll() {
        return productStocks;
    }

    public Optional<ProductStock> findByProduct(Product product) {
        if (product instanceof PromotionProduct) {
            return findByProduct((PromotionProduct) product);
        }
        return productStocks.stream()
                .filter(productStock -> product.equals(productStock.getProductDetail()))
                .findFirst();
    }

    public Optional<ProductStock> findByProduct(PromotionProduct product) {
        return productStocks.stream()
                .filter(productStock -> product.equals(productStock.getProductDetail()))
                .findFirst();
    }

    public List<ProductStock> findByProductName(String productName) {
        return productStocks.stream()
                .filter(productStock -> productName.equals(productStock.getProductDetail().getName()))
                .toList();
    }

    private List<ProductStock> initProductStocks(PromotionRepository promotionRepository) {
        List<ProductStock> productStocks = new ArrayList<>();
        List<Promotion> promotions = promotionRepository.findAll();

        for (Map<String, String> lineData : getTableData()) {
            ProductStock newProductStock = lineDataToProductStock(lineData, promotions);
            productStocks.add(newProductStock);
        }
        return productStocks;
    }

    private ProductStock lineDataToProductStock(Map<String, String> lineData, List<Promotion> promotions) {
        String name = lineData.get("name");
        int price = parsePrice(lineData.get("price"));
        int quantity = parseQuantity(lineData.get("quantity"));
        Promotion promotion = parsePromotion(lineData.get("promotion"), promotions);

        if (promotion == null) {
            return new ProductStock(new Product(name, price), quantity);
        }
        return new ProductStock(new PromotionProduct(name, price, promotion), quantity);
    }

    private int parsePrice(String price) {
        return parseIntData(price);
    }

    private int parseQuantity(String quantity) {
        return parseIntData(quantity);
    }

    private Promotion parsePromotion(String promotionInput, List<Promotion> promotions) {
        if (promotionInput.equals("null")) {
            return null;
        }

        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionInput))
                .findFirst()
                .orElseThrow(() -> new RepositoryInitException(NOT_MATCH_WITH_PROMOTION_EXCEPTION));
    }

    private static int parseIntData(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }
}
