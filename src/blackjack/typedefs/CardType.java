package blackjack.typedefs;

/**
 *  急ぎ実装したが、CardTypeとCardNumがグローバルになっているのがなんかキモい
 *  【影響範囲】
 *  ・blackjack.Card
 *  ・blackjack.Challenger
 *  ・blackjack.Dealer
 *  ・test.Test
 *  ・test.blackjack.Blackjack
 */
// 使うカードの定義
public enum CardType {
    //    JOKER,
    SPADE,
    DIAMOND,
    CLOVER,
    HEART;
}