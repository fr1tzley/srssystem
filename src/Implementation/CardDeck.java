package Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//A deck of cards to be reviewed.
public class CardDeck {

    ArrayList<Card> cards;
    ArrayList<Card> todaysCards;
    public final int DAYS_TO_CALCULATE_AVERAGE_REVIEWS = 7;

    public CardDeck() {
        cards = new ArrayList<>();
        todaysCards = new ArrayList<>();
    }

    //MODIFIES:this
    //EFFECTS:empties out today's cards, then sorts them in ascending order by the time until they're due to be reviewed
    //The program then calculates the average number of reviews that will occur over the next DAYS_TO_CALCULATE_
    // AVERAGE_REVIEWS, and adds reviews to today's review list from the sorted list of all cards until that
    //average is reached. This is to ensure that there will never be a day with a massive pile-up of reviews,
    //or a day with very few, due to due dates clustering around certain days.
    public void generateTodaysReviews() {
        todaysCards.clear();

        Collections.sort(cards, new SortByDaysLeft());

        int reviewTarget = this.countCardsWithinDays() / DAYS_TO_CALCULATE_AVERAGE_REVIEWS;

        for (int i = 0; i < reviewTarget; i++) {
            todaysCards.add(cards.get(i));
        }


    }

    //EFFECTS:Counts the number of cards due within the next DAYS_TO_CALCULATE days, and returns the count.
    public int countCardsWithinDays() {
        int count = 0;

        for (Card c : this.cards) {
            if (c.getDaysTillDue() >= DAYS_TO_CALCULATE_AVERAGE_REVIEWS) {
                count++;
            }
        }
        return count;
    }

    public void addCard(String question, String answer) {
        cards.add(new Card(question, answer));
    }

    public void removeCard(Card c) {
        cards.remove(c);
    }

    public List<Card> getAllCards() {
        return this.cards;
    }

    public List<Card> getTodaysCards() {
        return this.todaysCards;
    }

    //A comparator class that sorts cards in ascending order by how many days are left until they're due.
    class SortByDaysLeft implements Comparator<Card> {

        @Override
        public int compare(Card c1, Card c2) {
            return c1.getDaysTillDue() - c2.getDaysTillDue();
        }
    }
}
