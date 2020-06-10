package main.blackjack;

import java.util.Arrays;

public class Dealer extends Player {
    private int minScore;
    public Dealer(String name, int minScore) {
        super(name);
        this.minScore = minScore;
    }

    public boolean needCard(int maxScore) {
        if(this.hands.size() == 0) {
            return true;
        }
        if(this.score < this.minScore) {
            // Dealerの最低点を下回っている場合は必ずもう1枚取るようにする
            return true;
        }
        if(this.score >= minScore) { // maxScore
            this.stopDrawing = true;
            return false;
        }

        // もう1枚取るか判定
//        if()
        return true;
    }

    @Override
    protected void addScore(Card card, int maxScore) {
        Integer[] points = card.getNum().getPoints();
        if(points.length == 1) {
            this.score += card.getNum().getPoints()[0];
        } else {
            // 2つ以上のポイントから選択できるカードだった場合
            // 今回はエースカードだった場合
            /**
             *  - pointsを大きい順にソート
             *  - 大きい方から加算して検証
             *      - バーストしていたらダメ
             *      - 残りの最大を選択？
             *          - 残りの最小でも結局バーストしてたらその最小を選んでエンド
             *          - サブクラスDealerに至ってはminScore以上max_score以下になるものを最優先で選択だが、どの道max_score以下になれば良い
             */
            Arrays.sort(points);
            for(int i = points.length - 1; 0 <= i; i--) {
                if(i == 0) {
                    this.score += points[i];
                    break;
                }
                if(this.score + points[i] > maxScore) {
                    continue;
                }
                this.score += points[i];
                break;
            }
        }
    }

    @Override
    public void printStatus() {
        System.out.println("[Dealer(" + getName() + ")]");
        System.out.println("score: " + getScore());
        System.out.println("hands: " + getStrHands());
        System.out.println("--------------------------------------------");
    }
}