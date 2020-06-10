package study;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
class StreamSample {
    public static void main(String[] args) {
        // fruitsを回すだけの場合
        List<String> fruits = List.of("apple", "banana", "strawberry", "orange", "melon");

//        System.out.println("■ これまでの書き方(forでループする)");
//        for (String fruit : fruits) {
//            if (fruit.length() > 5) {
//                String upperCase = fruit.toUpperCase();
//                System.out.println(upperCase);
//            }
//        }
        System.out.println("■ Stream APIでの書き方");
        fruits.stream()
                .filter(s -> s.length() > 5)
                .map(s -> s.toUpperCase())
                .forEach(s -> System.out.println(s));

        System.out.println(fruits);



        // fruitsを更新せず新しい変数に代入する場合
        List<String> fruits2 = List.of("apple", "banana", "strawberry", "orange", "melon");
        System.out.println("■ これまでの書き方(forでループする)");
        List<String> upperLongFruits1 = new ArrayList<>();
        for (String fruit : fruits2) {
            if (fruit.length() > 5) {
                String upperCase = fruit.toUpperCase();
                upperLongFruits1.add(upperCase);
            }
        }
        System.out.println("■ Stream APIでの書き方");
        List<String> upperLongFruits2 = fruits2.stream()
                .filter(s -> s.length() > 5)
                .map(s -> s.toUpperCase())
                .collect(Collectors.toList());

        System.out.println(fruits2);
        System.out.println(upperLongFruits2);
    }
}
