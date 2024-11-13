package store.view;

import camp.nextstep.edu.missionutils.Console;
import store.view.parser.YOrNoInputParser;

public class InsufficientPromotionStockSelectInputView {

    private static final String INFO_FORMAT = "현재 %s %s개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
    private static final YOrNoInputParser PARSER = new YOrNoInputParser();

    public static boolean select(String productName, int insufficientQuantity) {
        announce(productName, insufficientQuantity);
        return PARSER.parseYOrN(Console.readLine());
    }

    private static void announce(String productName, int insufficientQuantity) {
        System.out.printf(INFO_FORMAT, productName, insufficientQuantity);
    }
}
