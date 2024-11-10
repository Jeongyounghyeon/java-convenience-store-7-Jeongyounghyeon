package store.exception;

public class ApplicationInitException extends RuntimeException {

    private static final String MESSAGE = "애플리케이션을 초기화하는데 실패하였습니다.";

    public ApplicationInitException() {
        super(MESSAGE);
    }

    public ApplicationInitException(String message) {
        super(message);
    }
}
