package blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *  急ぎ実装したが、CardTypeとCardNumがグローバルになっているのがなんかキモい
 *  【影響範囲】
 *  ・Card
 *  ・Challenger
 *  ・Dealer
 */
// 使うカードの定義
enum CardType {
    //    JOKER(0),
    SPADE(1),
    DIAMOND(2),
    CLOVER(3),
    HEART(4);

    private int typeId;
    CardType(int typeId) {
        this.typeId = typeId;
    }
    public int getTypeId() {
        return this.typeId;
    }
}
enum CardNum {
    TWO(new int[]{2}), // 書き方よろしくないかもしれない
    THREE(new int[]{3}),
    FOUR(new int[]{4}),
    FIVE(new int[]{5}),
    SIX(new int[]{6}),
    SEVEN(new int[]{7}),
    EIGHT(new int[]{8}),
    NINE(new int[]{9}),
    TEN(new int[]{10}),
    JACK(new int[]{10}),
    QUEEN(new int[]{10}),
    KING(new int[]{10}),
    ACE(new int[]{1, 11});

    private int[] points;
    CardNum(int[] points) {
        this.points = points;
    }
    public int[] getPoints() {
        return points;
    }
}

// Blackjackゲーム
public class Blackjack {
    // Blackjackのルール定義
    private final int MAX_SCORE = 21;
    private final int DEALER_MIN_SCORE = 17;

    public void play() {
        Challenger challenger = new Challenger("チャレンジャー");
        Dealer dealer = new Dealer("ディーラー", this.DEALER_MIN_SCORE);

        ArrayList<Card> cardDeck = shuffle();

        // 以降、cardsの先頭を引いていく(カード山の上から引くように…)
        int drawCnt = 0;
        while(cardDeck.size() > 0) { // カード山が無くなるまで
            drawCnt = 0;
            // Challenger
            if(challenger.wantCard(this.MAX_SCORE)) {
                challenger.draw(cardDeck.remove(0), MAX_SCORE);
                drawCnt++;
            }

            // Dealer
            if(dealer.wantCard(this.MAX_SCORE)) {
                dealer.draw(cardDeck.remove(0), MAX_SCORE);
                drawCnt++;
            }

            if(drawCnt == 0) {
                // 全員カードを引くことをやめた場合、勝利判定へ移行
                break;
            }

            printState(dealer, challenger);
        }

        // 勝敗判定
        ArrayList<Player> winners = getWinners(dealer, challenger);
        if(winners.size() == 1) {
            System.out.println(winners.get(0).getName() + "さんの勝ち！");
        } else {
            System.out.println("引き分けでした…");
        }
    }

    private ArrayList<Card> shuffle() {
        /**
         *  ゲームの準備(カードを切る)
         */
        ArrayList<Card> cards = new ArrayList<>();
        CardType[] types = CardType.values();
        CardNum[] nums = CardNum.values();
        for(CardType type: types) {
            for(CardNum num: nums) {
                cards.add(new Card(type, num));
            }
        }
        Collections.shuffle(cards);
        return cards;
    }

    private ArrayList<Player> getWinners(Dealer dealer, Challenger ...challengers) {
        /**
         *  - [ ] diffにおいて負かつ最大(つまりMAX_SCORE以下かつMAX_SCOREに近い)がWinner
         *      - MAX_SCOREを上回っている場合はcontinue
         *  - [x] 全員がMAX_SCOREを上回った場合はDealerがWinner
         *  - 枚数による判定は無し
         *      - [ ] よってMAX_SCORE以下かつ同点の場合は引き分け
         *  - Dealerの、DEALER_MIN_SCOREを下回ることについては、Dealer.wantCard()で下回らないように制御しているので下回ることがない前提とする
         */
        // dealerを先頭にしてリスト化(Dealer勝ち抜けの際の取得のため)
        // 微妙
        ArrayList<Player> players = new ArrayList<>();
        players.add(dealer);
        players.addAll(Arrays.asList(challengers));

        ArrayList<Integer> winnerIndexes = null; // 全員がMAX_SCOREを上回っていたらnullのままになるはず
        int[] diffs = new int[players.size()];
        Arrays.fill(diffs, 0); // 0であるほど目標値に近い。正ならバースト
        for(int i = 0; i < players.size(); i++) {
            diffs[i] = players.get(i).getScore() - MAX_SCORE;
            if(diffs[i] < 0) {
                if(winnerIndexes == null) {
                    winnerIndexes = new ArrayList<Integer>();
                    winnerIndexes.add(i);
                } else {
                    if(diffs[winnerIndexes.get(0)] < diffs[i]) {
                        // 負の差分において大きい方がMAX_SCOREに近い
                        winnerIndexes.clear();
                        winnerIndexes.add(i);
                    } else if(diffs[winnerIndexes.get(0)] == diffs[i]) {
                        winnerIndexes.add(i);
                    }
                }
            }
        }

        ArrayList<Player> winners = new ArrayList<>();
        if(winnerIndexes == null) {
            // 全員MAX_SCOREを上回っていた場合は、Dealerの勝ち抜け
            winners.add(dealer);
            return winners;
        }
        if(winnerIndexes.size() == 1) {
            // 勝利者が1人の場合
            winners.add(players.get(winnerIndexes.get(0)));
            return winners;
        }
        // 勝利者が複数人の場合
        for(int winnerIndex: winnerIndexes) {
            winners.add(players.get(winnerIndexes.get(winnerIndex)));
        }
        return winners;
    }

    private void printState(Dealer dealer, Challenger ...challengers) {
        System.out.println("[Dealer(" + dealer.getName() + ")]");
        System.out.println("score: " + dealer.getScore());
        System.out.println("hands: " + dealer.getStrHands());
        System.out.println("--------------------------------------------");
        for(Challenger challenger: challengers) {
            System.out.println("[Challenger(" + challenger.getName() + ")]");
            System.out.println("score: " + challenger.getScore());
            System.out.println("hands: " + challenger.getStrHands());
        }
        System.out.println("=============================================");
    }
}