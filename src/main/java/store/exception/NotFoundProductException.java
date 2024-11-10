package store.exception;

public class NotFoundProductException extends InvalidArgumentException {

    private static final String MESSAGE = "존재하지 않는 상품입니다.";

    public NotFoundProductException() {
        super(MESSAGE);
    }
}
