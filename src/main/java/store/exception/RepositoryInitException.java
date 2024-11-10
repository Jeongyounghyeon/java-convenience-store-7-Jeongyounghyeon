package store.exception;

public class RepositoryInitException extends ApplicationInitException {

    private static final String MESSAGE = "저장소를 초기화하는데 실패하였습니다.";

    public RepositoryInitException() {
        super(MESSAGE);
    }

    public RepositoryInitException(String message) {
        super(message);
    }
}
