package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.view.parser.YOrNoInputParser;

public class PromotionAddSelectView {

    private static final String INFO_FORMAT = "현재 %s은(는) %s개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    private static final YOrNoInputParser PARSER = new YOrNoInputParser();

    public static boolean select(String promotionName, int addQuantity) {
        announce(promotionName, addQuantity);
        return PARSER.parseYOrN(Console.readLine());
    }

    private static void announce(String promotionName, int addQuantity) {
        System.out.printf(INFO_FORMAT, promotionName, addQuantity);
    }
}
