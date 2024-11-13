package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.text.DecimalFormat;
import java.util.List;
import store.domain.order.Order;
import store.dto.ProductStockStatus;
import store.view.parser.OrderInputParser;

public class OrderInputView {

    private static final String INFO = """
            안녕하세요. W편의점입니다.
            현재 보유하고 있는 상품입니다.
            """;
    private static final String PRODUCT_STOCK_FORMAT = "- %s %s %s %s\n";
    private static final String ORDER_INFO = """
            구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
            """;
    private static final OrderInputParser PARSER = new OrderInputParser();

    public static List<Order> order(List<ProductStockStatus> stocks) {
        announce(stocks);
        return PARSER.parseOrders(Console.readLine());
    }

    private static void announce(List<ProductStockStatus> stockStatuses) {
        addNonPromotionProduct(stockStatuses);
        System.out.print(INFO);
        System.out.print("\n");
        announceStocks(stockStatuses);
        System.out.print("\n");
        System.out.print(ORDER_INFO);
    }

    private static void announceStocks(List<ProductStockStatus> stockStatuses) {
        for (ProductStockStatus stockStatus : stockStatuses) {
            announceStock(stockStatus);
        }
    }

    private static void announceStock(ProductStockStatus stockStatus) {
        String name = getNameString(stockStatus);
        String price = getPriceString(stockStatus);
        String quantity = getQuantityString(stockStatus);
        String promotion = getPromotionString(stockStatus);

        System.out.printf(PRODUCT_STOCK_FORMAT, name, price, quantity, promotion);
    }

    private static String getNameString(ProductStockStatus stockStatus) {
        return stockStatus.getProductName();
    }

    private static String getPriceString(ProductStockStatus stockStatus) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        int price = stockStatus.getPrice();
        return decimalFormat.format(price) + "원";
    }

    private static String getQuantityString(ProductStockStatus stockStatus) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        int quantity = stockStatus.getQuantity();
        if (quantity == 0) {
            return "재고 없음";
        }
        return decimalFormat.format(quantity) + "개";
    }

    private static String getPromotionString(ProductStockStatus stockStatus) {
        return stockStatus.getPromotion();
    }

    private static void addNonPromotionProduct(List<ProductStockStatus> stockStatuses) {
        List<ProductStockStatus> promotionStatuses = getPromotionStatuses(stockStatuses);
        List<ProductStockStatus> uniquePromotionStatuses = getUniquePromotionStatuses(stockStatuses, promotionStatuses);

        uniquePromotionStatuses.forEach(status ->
                stockStatuses.add(stockStatuses.indexOf(status) + 1, createNonPromotionStatus(status))
        );
    }

    private static List<ProductStockStatus> getPromotionStatuses(List<ProductStockStatus> stockStatuses) {
        return stockStatuses.stream()
                .filter(stockStatus -> !stockStatus.getPromotion().isEmpty())
                .toList();
    }

    private static List<ProductStockStatus> getUniquePromotionStatuses(
            List<ProductStockStatus> stockStatuses, List<ProductStockStatus> promotionStatuses) {
        return promotionStatuses.stream()
                .filter(promotionStatus -> stockStatuses.stream()
                        .filter(status -> status.getProductName().equals(promotionStatus.getProductName()))
                        .count() == 1)
                .toList();
    }

    private static ProductStockStatus createNonPromotionStatus(ProductStockStatus status) {
        return ProductStockStatus.of(status.getProductName(), status.getPrice(), 0, "");
    }
}
