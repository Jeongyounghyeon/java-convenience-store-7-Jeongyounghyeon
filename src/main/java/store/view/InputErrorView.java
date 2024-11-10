package store.view;

import store.exception.InvalidInputException;

public class InputErrorView {

    private static final String EXCEPTION_PREFIX = "[ERROR] ";
    private static final String ILLEGAL_INPUT_EXCEPTION_SUFFIX = " 다시 입력해 주세요.";

    public static void announce(InvalidInputException e) {
        final String message = e.getMessage();
        System.out.println(EXCEPTION_PREFIX + message + ILLEGAL_INPUT_EXCEPTION_SUFFIX);
    }
}
