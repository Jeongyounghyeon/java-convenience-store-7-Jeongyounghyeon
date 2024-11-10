package store.view.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.exception.InvalidInputException;

class YOrNoInputParserTest {

    private final YOrNoInputParser yOrNoInputParser;

    YOrNoInputParserTest() {
        this.yOrNoInputParser = new YOrNoInputParser();
    }

    @Test
    @DisplayName("parseYOrN 정상 동작 테스트")
    void parseYOrN() {
        // given
        final String inputY = "Y";
        final String inputN = "N";

        // when
        final boolean resultY = yOrNoInputParser.parseYOrN(inputY);
        final boolean resultN = yOrNoInputParser.parseYOrN(inputN);

        assertThat(resultY).isEqualTo(true);
        assertThat(resultN).isEqualTo(false);
    }

    @Test
    @DisplayName("parseYOrN는 Y와N이 아닌 입력에 대해 InvalidInputException을 던진다.")
    void parseYOrN_WithInvalidInput_ThrowInvalidInputException() {
        // given
        final String inputYes = "Yes";
        final String inputNo = "No";

        // when & then
        assertThatThrownBy(
                () -> yOrNoInputParser.parseYOrN(inputYes)
        ).isInstanceOf(InvalidInputException.class);
        assertThatThrownBy(
                () -> yOrNoInputParser.parseYOrN(inputNo)
        ).isInstanceOf(InvalidInputException.class);
    }
}
