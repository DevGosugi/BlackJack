import blackjack.Blackjack;
public class Main {
    public static void main(String[] args) {
        Blackjack bj = new Blackjack();
        bj.play();
    }
}

/**
 *  - 講師の[サンプルコード](https://github.com/MasatoshiTada/blackjack/tree/master/src/main/java/com/example)を見ての改善点
 *      - [ ] CardはEnumで
 *      - [ ] CardDeckクラス、Handsクラスを作る
 *      - [ ] maxScoreとdealerMinScoreは固定値なのでそれぞれ必要なクラスの中で定数として持たせる
 *          - maxScoreが本当に必要なクラスはどこか、よく考える(講師はHandsに持たせ、スコア判定やカード選択で用いている)
 *      - [x] Challenger.needCard()の例外潰ししない。メッセージ出力を実装する
 *      - [ ] 「どちらかがバーストしたらゲーム終了」の実装…と思ったけど、2人以上だった場合はこれは不要かな？
 */