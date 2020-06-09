package test.blackjack;

import blackjack.Challenger;
import blackjack.Dealer;
import blackjack.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class TestBlackjack {
    private int max_score;
    public TestBlackjack(int max_score) {
        this.max_score = max_score;
    }
    public ArrayList<Player> getWinners(Dealer dealer, Challenger ...challengers) {
        /**
         *  - blackjack.Blackjack.getWinner()からコピペして、
         *      - `MAX_SCORE` -> `this.max_score`
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
            diffs[i] = players.get(i).getScore() - this.max_score;
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
}
