package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.view.parser.YOrNoInputParser;

public class ReOrderSelectView {

    private static final String INFO = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)\n";
    private static final YOrNoInputParser PARSER = new YOrNoInputParser();

    public static boolean select() {
        announce();
        return PARSER.parseYOrN(Console.readLine());
    }

    private static void announce() {
        System.out.print(INFO);
    }
}
