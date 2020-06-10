package test;

import main.blackjack.*;
import main.blackjack.typedefs.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TestBlackjack {
    public static void main(String[] args) {
        try {
            testGetWinner();
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

    public static void testGetWinner() throws NoSuchMethodException,
            SecurityException, IllegalAccessException, InvocationTargetException, Exception {
        /**
         * テストコードのつもりだけど、ACEへの対応ができていない。
         */
        final int MAX_SCORE = 21;
        final int DEALER_MIN_SCORE = 17;
        Blackjack bj = new Blackjack(); // MAX_SCORE, DEALER_MIN_SCORE
        List<Player> expected;
        List<Card> hands;

        Dealer dealer = new Dealer(
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

        Challenger challenger = new Challenger(
                "チャレンジャー");
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
        Challenger[] challengers = {
                challenger
        };

        expected = Arrays.asList(
                // (1)
//                challenger

                // (2), (3), (5)
                challenger, dealer

                // (4)
//                dealer
        );

        Method getWinners = bj.getClass()
                .getDeclaredMethod("getWinners", Dealer.class, Challenger[].class);
        getWinners.setAccessible(true);
        assertGetWinner(
                expected,
                (ArrayList<Player>) getWinners.invoke(bj, dealer, challengers));
    }

    private static void autoDraw(Player player, List<Card> hands, int max_score) {
        for(Card hand: hands) {
            player.draw(hand, max_score);
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
        if(!setExpected.equals(setResult)) {
            isMatched = false;
        }

        if(isMatched) {
            System.out.println("[OK]");
            System.out.println("Expected: " + expected);
            System.out.println("Result  : " + result);
        } else {
            System.out.println("[Not matched]");
            System.out.println("Expected: " + expected);
            System.out.println("Result  : " + result);
        }
    }
}
