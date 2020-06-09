package blackjack;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Player {
    protected String name;
    protected ArrayList<Card> hands; // 手札
    protected int score;

    Player(String name) {
        this.name = name;
        this.score = 0;
        this.hands = new ArrayList<Card>();
    }
    public String getName() {
        return this.name;
    }
    public int getScore() {
        return this.score;
    }
    public String getStrHands() {
        String str = "";
        for(Card hand: this.hands) {
            str += "[" + hand.getType() + "(" + hand.getNum() + ")]";
        }
        return str;
    }

    public void draw(Card card, int max_score) { // max_scoreはフィールドにすべきか？
        hands.add(card);
        addScore(card, max_score);
    }

    abstract public boolean wantCard(int max_score);

    private void addScore(Card card, int max_score) {
        if(card.getNum().getPoints().length == 1) {
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
            int[] points = card.getNum().getPoints();
            Arrays.sort(points);
            for(int i = points.length - 1; 0 <= i; i--) {
                if(i == 0) {
                    this.score += points[i];
                    break;
                }
                if(this.score + points[i] > max_score) {
                    continue;
                }
                this.score += points[i];
                break;
            }
        }
    }
}