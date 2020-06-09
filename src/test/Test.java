package test;

import blackjack.Card;
import blackjack.Challenger;
import blackjack.Dealer;
import blackjack.Player;
import blackjack.typedefs.*;
import test.blackjack.TestBlackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        /**
         * テストコードのつもりだけど、ACEへの対応ができていない。
         */
        final int MAX_SCORE = 21;
        final int DEALER_MIN_SCORE = 17;
        TestBlackjack bj = new TestBlackjack(MAX_SCORE);
        List<Player> expected;
        List<Card> hands;

        Dealer dealer = new Dealer(
                "ディーラー",
                DEALER_MIN_SCORE);
        hands = Arrays.asList(
                // (1)チャレンジャー勝ち抜け
//                new Card(CardType.CLOVER, CardNum.FOUR),
//                new Card(CardType.CLOVER, CardNum.QUEEN),
//                new Card(CardType.SPADE, CardNum.QUEEN)

                // (2)引き分け
//                new Card(CardType.SPADE, CardNum.TEN),
//                new Card(CardType.DIAMOND, CardNum.FOUR),
//                new Card(CardType.CLOVER, CardNum.FOUR)

                // (3)引き分け
                new Card(CardType.SPADE, CardNum.SIX),
                new Card(CardType.DIAMOND, CardNum.FOUR),
                new Card(CardType.CLOVER, CardNum.THREE),
                new Card(CardType.HEART, CardNum.SIX)
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
                new Card(CardType.DIAMOND, CardNum.QUEEN),
                new Card(CardType.HEART, CardNum.NINE)
        );
        autoDraw(challenger, hands, MAX_SCORE);
        Challenger[] challengers = {
                challenger
        };

        expected = Arrays.asList(
                // (1)
//                challenger

                // (2), (3)
                challenger, dealer

                //
//                dealer
        );
        assertGetWinner(
                expected,
                bj.getWinners(dealer, challengers));
    }

    private static void autoDraw(Player player, List<Card> hands, int max_score) {
        for(Card hand: hands) {
            player.draw(hand, max_score);
        }
    }

    private static void assertGetWinner(List<Player> expected, ArrayList<Player> result) {
        boolean isMatched = true;
        HashSet setExpected = new HashSet(expected);
        HashSet setResult = new HashSet(result);
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
