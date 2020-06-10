package main.blackjack;

import main.blackjack.typedefs.CardNum;
import main.blackjack.typedefs.CardType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Blackjack {
    // Blackjackのルール定義
    private int maxScore = 21;
    private int dealerMinScore = 17;

    public Blackjack() {}
    public Blackjack(int maxScore, int dealerMinScore) {
        this.maxScore = maxScore;
        this.dealerMinScore = dealerMinScore;
    }

    public void play() {
        Dealer dealer = new Dealer("ディーラー", this.dealerMinScore);
        Challenger challenger = new Challenger("チャレンジャー");

        ArrayList<Card> cardDeck = shuffle();

        // 以降、cardsの先頭を引いていく(カード山の上から引くように…)
        int drawCnt = 0;
        while(cardDeck.size() > 0) { // カード山が無くなるまで
            drawCnt = 0;
            // Dealer
            if(dealer.allowedToDraw() &&
                    dealer.needCard(this.maxScore)) {
                dealer.draw(cardDeck.remove(0), this.maxScore);
                drawCnt++;
            }

            // Challenger
            if(challenger.allowedToDraw() &&
                    challenger.needCard()) {
                challenger.draw(cardDeck.remove(0), this.maxScore);
                drawCnt++;
            }

            if(drawCnt == 0) {
                // 全員カードを引くことをやめた場合、勝利判定へ移行
                System.out.println("=============================================");
                break;
            }

            printStatus(dealer, challenger);
            System.out.println("=============================================");
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
            diffs[i] = players.get(i).getScore() - this.maxScore;
            if(diffs[i] <= 0) {
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

    private void printStatus(Dealer dealer, Challenger ...challengers) {
        dealer.printStatus();
        for(Challenger challenger: challengers) {
            challenger.printStatus();
        }
    }
}