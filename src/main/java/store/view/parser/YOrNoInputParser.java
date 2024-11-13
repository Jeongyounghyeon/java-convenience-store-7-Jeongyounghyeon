package store.view.parser;

import store.exception.InvalidInputException;

public class YOrNoInputParser {

    private static final String INVALID_INPUT_EXCEPTION_MESSAGE = "잘못된 입력입니다.";

    public YOrNoInputParser() {
    }

    public boolean parseYOrN(String input) {
        input = input.trim();
        if (input.equals("Y")) {
            return true;
        }
        if (input.equals("N")) {
            return false;
        }
        throw new InvalidInputException(INVALID_INPUT_EXCEPTION_MESSAGE);
    }
}
