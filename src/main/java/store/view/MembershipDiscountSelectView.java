package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.view.parser.YOrNoInputParser;

public class MembershipDiscountSelectView {

    private static final String INFO = "멤버십 할인을 받으시겠습니까? (Y/N)\n";
    private static final YOrNoInputParser PARSER = new YOrNoInputParser();

    public static boolean select() {
        announce();
        return PARSER.parseYOrN(Console.readLine());
    }

    private static void announce() {
        System.out.print(INFO);
    }
}
