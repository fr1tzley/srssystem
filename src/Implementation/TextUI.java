package Implementation;

import java.util.List;
import java.util.Scanner;

public class TextUI {
    CardDeck deck;
    Scanner userInput = new Scanner(System.in);

    public void main(String[] args) {
        deck = new CardDeck();
        sysOut("Welcome to Memorize, the world's worst SRS app!");
        mainMenu();
    }

    private void mainMenu() {

        sysOut("Type 1 to add new cards, or type 2 to begin reviewing.");

        int input = userInput.nextInt();
        switch (input) {
            case 1:
                addCards();
                break;
            case 2:
                reviewCards();
                break;
            default:
                sysOut("Unknown command.");
                break;
        }
    }

    private void addCards() {
        sysOut("Enter the question for your card. Type 'Exit' to return to the main menu.");
        String input = userInput.next();
        switch (input) {
            case "Exit":
                sysOut("Returning to main menu.");
                mainMenu();
                break;
            default:
                sysOut("Enter the answer for your card.");
                String input2 = userInput.next();
                deck.addCard(input, input2);
        }

    }

    private void reviewCards() {
        deck.generateTodaysReviews();
        List<Card> cards = deck.getTodaysCards();
        

        for (Card c : cards) {

        }

    }



    private void sysOut(String e) {
        System.out.println(e);
    }
}
