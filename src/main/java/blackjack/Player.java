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

    public void draw(Card card, int maxScore) {
        hands.add(card);
        addScore(card, maxScore);
    }

    abstract protected void addScore(Card card, int maxScore);

    abstract public void printStatus();
}