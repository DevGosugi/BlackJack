package blackjack;

public class Card {
    private CardType type;
    private CardNum num;
    Card(CardType type, CardNum num) {
        this.type = type;
        this.num = num;
    }
    public CardType getType() {
        return this.type;
    }
    public CardNum getNum() {
        return this.num;
    }
}