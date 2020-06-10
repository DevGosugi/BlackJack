package blackjack;

import java.util.Arrays;
import java.util.Scanner;

public class Challenger extends Player {
    public Challenger(String name) {
        super(name);
    }

    public boolean needCard() {
        if(this.hands.size() == 0) {
            return true;
        }
        boolean needCard = false;
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("Do you want to draw?(y/n): ");
            String input = sc.next();
            if(input.equals("y")) {
                needCard = true;
                break;
            }
            if(input.equals("n")) {
                needCard = false;
                this.stopDrawing = true;
                break;
            }
            System.out.println("Invalid input!!!");
        }
        return needCard;
    }

    @Override
    protected void addScore(Card card, int maxScore) {
        Integer[] points = card.getNum().getPoints();
        if(points.length == 1) {
            this.score += card.getNum().getPoints()[0];
        } else {
            System.out.println("You drew [" + card.getType() + "(" + card.getNum() + ")]" + "!");
            Scanner sc = new Scanner(System.in);
            int selectedNum = 0;
            while(true) {
                System.out.print("Select number to add from " + card.getNum().getStrPoints() + ": ");
                try {
                    selectedNum = Integer.parseInt(sc.next());
                    if(Arrays.asList(points).contains(selectedNum)) {
                        break;
                    }
                } catch(NumberFormatException e) {}
                System.out.println("Invalid input!!!");
            }
            this.score += selectedNum;
        }
    }

    @Override
    public void printStatus() {
        System.out.println("[Challenger(" + getName() + ")]");
        System.out.println("score: " + getScore());
        System.out.println("hands: " + getStrHands());
    }
}
