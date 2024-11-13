package store.exception;

public class InsufficientStockException extends InvalidArgumentException {

    private static final String MESSAGE = "재고 수량을 초과하여 구매할 수 없습니다.";

    public InsufficientStockException() {
        super(MESSAGE);
    }
}
