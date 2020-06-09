package blackjack;

import java.util.ArrayList;

public abstract class Player {
    private String name; // protected
    protected ArrayList<Card> hands; // 手札
    protected int score;
    protected boolean stopDrawing;

    Player(String name) {
        this.name = name;
        this.score = 0;
        this.hands = new ArrayList<Card>();
        this.stopDrawing = false;
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
    public boolean allowedToDraw() {
        return !this.stopDrawing;
    }

    // abstract public boolean needCard(); // サブクラスごとに引数が異なるためコメントアウト

    public void draw(Card card, int max_score) {
        hands.add(card);
        addScore(card, max_score);
    }

    abstract protected void addScore(Card card, int max_score);

    abstract public void printStatus();
}