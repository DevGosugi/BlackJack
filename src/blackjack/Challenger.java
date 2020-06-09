package blackjack;

import java.util.Arrays;

public class Challenger extends Player {
    Challenger(String name) {
        super(name);
    }

    @Override
    public boolean wantCard(int max_score) {
        if(this.hands.size() == 0) {
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
