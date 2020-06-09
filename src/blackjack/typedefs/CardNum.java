package blackjack.typedefs;

/**
 *  急ぎ実装したが、CardTypeとCardNumがグローバルになっているのがなんかキモい
 *  【影響範囲】
 *  ・Card
 *  ・Challenger
 *  ・Dealer
 */
// 使うカードの定義
public enum CardNum {
    TWO(new Integer[]{2}), // 書き方よろしくないかもしれない
    THREE(new Integer[]{3}),
    FOUR(new Integer[]{4}),
    FIVE(new Integer[]{5}),
    SIX(new Integer[]{6}),
    SEVEN(new Integer[]{7}),
    EIGHT(new Integer[]{8}),
    NINE(new Integer[]{9}),
    TEN(new Integer[]{10}),
    JACK(new Integer[]{10}),
    QUEEN(new Integer[]{10}),
    KING(new Integer[]{10}),
    ACE(new Integer[]{1, 11});

    private Integer[] points;
    CardNum(Integer[] points) {
        this.points = points;
    }
    public Integer[] getPoints() {
        return points;
    }
    public String getStrPoints() {
        String str = "{";
        for(int i = 0; i < points.length; i++) {
            str += points[i];
            if(i != points.length - 1) {
                str += ", ";
            }
        }
        str += "}";
        return str;
    }
}
