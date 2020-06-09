package blackjack;

import java.util.ArrayList;
import java.util.Arrays;

public class Dealer extends Player {
    private int minScore;
    Dealer(String name, int minScore) {
        super(name);
        this.minScore = minScore;
    }

    @Override
    public boolean wantCard(int max_score) {
        if(this.hands.size() == 0) {
            return true;
        }
        if(this.score < this.minScore) { // this. // super.
            // Dealerの最低点を下回っている場合は必ずもう1枚取るようにする
            return true;
        }
        if(this.score > max_score) {
            return false;
        }

        // もう1枚取るか判定
//        if()
        return true;
    }
}