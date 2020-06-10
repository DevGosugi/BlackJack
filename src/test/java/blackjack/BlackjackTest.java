package blackjack;

import blackjack.typedefs.CardNum;
import blackjack.typedefs.CardType;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class BlackjackTest {
    final int MAX_SCORE = 21;
    final int DEALER_MIN_SCORE = 17;
    private Blackjack bj;
    private Dealer dealer;
    private Challenger[] challengers;

    // @BeforeAll // @BeforeAllつけるとstatic出なければならず、フィールドが使えなくなるため、自クラス呼び出しにした
    private void setup() {
        this.bj = new Blackjack(); // MAX_SCORE, DEALER_MIN_SCORE
        List<Card> hands;

        this.dealer = new Dealer(
                "ディーラー",
                DEALER_MIN_SCORE);
        hands = Arrays.asList(
                // (1)チャレンジャー勝ち
//                new Card(CardType.CLOVER, CardNum.FOUR),
//                new Card(CardType.CLOVER, CardNum.QUEEN),
//                new Card(CardType.SPADE, CardNum.QUEEN)

                // (2)引き分け
//                new Card(CardType.SPADE, CardNum.TEN),
//                new Card(CardType.DIAMOND, CardNum.FOUR),
//                new Card(CardType.CLOVER, CardNum.FOUR)

                // (3)引き分け
//                new Card(CardType.SPADE, CardNum.SIX),
//                new Card(CardType.DIAMOND, CardNum.FOUR),
//                new Card(CardType.CLOVER, CardNum.THREE),
//                new Card(CardType.HEART, CardNum.SIX)

                // (4)ディーラー勝ち
//                new Card(CardType.HEART, CardNum.FOUR),
//                new Card(CardType.SPADE, CardNum.TWO),
//                new Card(CardType.SPADE, CardNum.THREE)

                // (5)引き分け
                new Card(CardType.SPADE, CardNum.THREE),
                new Card(CardType.SPADE, CardNum.EIGHT),
                new Card(CardType.SPADE, CardNum.KING)
        );
        autoDraw(dealer, hands, MAX_SCORE);

        Challenger challenger = new Challenger("チャレンジャー");
        hands = Arrays.asList(
                // (1)
//                new Card(CardType.DIAMOND, CardNum.THREE),
//                new Card(CardType.CLOVER, CardNum.JACK),
//                new Card(CardType.DIAMOND, CardNum.EIGHT)

                // (2)
//                new Card(CardType.HEART, CardNum.TWO),
//                new Card(CardType.DIAMOND, CardNum.QUEEN),
//                new Card(CardType.DIAMOND, CardNum.SIX)

                // (3)
//                new Card(CardType.DIAMOND, CardNum.QUEEN),
//                new Card(CardType.HEART, CardNum.NINE)

                // (4)
//                new Card(CardType.DIAMOND, CardNum.TEN),
//                new Card(CardType.SPADE, CardNum.SEVEN),
//                new Card(CardType.HEART, CardNum.QUEEN)

                // (5)
                new Card(CardType.DIAMOND, CardNum.THREE),
                new Card(CardType.DIAMOND, CardNum.EIGHT),
                new Card(CardType.DIAMOND, CardNum.KING)
        );
        autoDraw(challenger, hands, MAX_SCORE);
        this.challengers = new Challenger[]{
                challenger
        };
    }

    private static void autoDraw(Player player, List<Card> hands, int max_score) {
        for(Card hand: hands) {
            player.draw(hand, max_score);
        }
    }

    @Test
    @DisplayName("getWinners()の結果のテスト")
    public void testGetWinners() {
        setup();
        List<Player> expected = Arrays.asList(
                // (1)
//                this.challengers[0]

                // (2), (3), (5)
                this.challengers[0], this.dealer

                // (4)
//                this.dealer
        );

        try {
            Method getWinners = this.bj.getClass()
                    .getDeclaredMethod("getWinners", Dealer.class, Challenger[].class);
            getWinners.setAccessible(true);
            assertGetWinner(
                    expected,
                    (ArrayList<Player>) getWinners.invoke(this.bj, this.dealer, this.challengers));
        } catch(NoSuchMethodException e) {
            e.printStackTrace();
        } catch(SecurityException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        } catch(InvocationTargetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void assertGetWinner(List<Player> expected, ArrayList<Player> result) {
        boolean isMatched = true;
        HashSet<Player> setExpected = new HashSet<>(expected);
        HashSet<Player> setResult = new HashSet<>(result);
        if(setExpected.size() != expected.size()) {
            isMatched = false;
        }
        if(setResult.size() != result.size()) {
            isMatched = false;
        }

        // 手作りAssertion
//        if(!setExpected.equals(setResult)) {
//            isMatched = false;
//        }
//        if(isMatched) {
//            System.out.println("[OK]");
//            System.out.println("Expected: " + expected);
//            System.out.println("Result  : " + result);
//        } else {
//            System.out.println("[Not matched]");
//            System.out.println("Expected: " + expected);
//            System.out.println("Result  : " + result);
//        }

        // Set同士のAssertもいけるんやな
        assertAll("Result of getWinners()",
                () -> assertEquals(setExpected, setResult));
    }

//    @AfterEach
//    void border() {
//        System.out.println("==========================================");
//    }
}