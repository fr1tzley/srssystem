package Testing;


import Exceptions.InvalidAnswerException;
import Exceptions.InvalidPromptException;
import Implementation.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.lang.Double.max;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CardTest {

    Card card;

    @BeforeEach
    public void setup() {
        card = new Card("Who is the world's greatest footballer?", "Ronaldo");
    }

    @Test
    public void testConstructorProperEntry() {
        try {
            new Card("Why do I exist?", "To spread butter");
        } catch (InvalidPromptException e) {
            fail();
        } catch (InvalidAnswerException e) {
            fail();
        }
    }

    @Test
    public void testConstructorInvalidPrompt() {
        try {
            new Card(" ", "To spread butter");
            fail();
        } catch (InvalidPromptException e) {
            //expected
        } catch (InvalidAnswerException e) {
            fail();
        }

        try {
            new Card("", "To spread butter");
            fail();
        } catch (InvalidPromptException e) {
            //expected
        } catch (InvalidAnswerException e) {
            fail();
        }
    }

    @Test
    public void testConstructorInvalidAnswer() {
        try {
            new Card("Why do I exist?", "");
            fail();
        } catch (InvalidPromptException e) {
            fail();
        } catch (InvalidAnswerException e) {
           //expected
        }

        try {
            new Card("Why do I exist?", " ");
            fail();
        } catch (InvalidPromptException e) {
            fail();
        } catch (InvalidAnswerException e) {
            //expected
        }
    }

    @Test
    public void testHandleAnswerCorrect() {
        double cardDifficulty = card.getDifficulty();
        double newDifficulty = cardDifficulty + 0.06 * (3 - 8);
        LocalDate newNextReview = LocalDate.now().plusDays(1);

        card.handleAnswer(true, 1.0);

        assertEquals(card.getDifficulty(), newDifficulty);
        assertEquals(card.getReviewInterval(), 1);
        assertEquals(card.getLastReview(), LocalDate.now());
        assertEquals(card.getNextReview(), newNextReview);

        cardDifficulty = card.getDifficulty();
        newDifficulty = cardDifficulty + 0.06 * (3 - 8);
        card.handleAnswer(true, 1.0);

    }

    @Test
    public void testHandleIntervalFirstReview() {
        card.handleInterval(true);
        assertEquals(1, card.getReviewInterval());
    }


    @Test
    public void testHandleIntervalCorrect() {
        card.incrementReviewCount();
        double testWeight = 3 - 1.7 * card.getDifficulty();
        int testInterval = (int) (card.getReviewInterval() * (1 + (testWeight - 1)));

        card.handleInterval(true);
        assertEquals(testInterval, card.getReviewInterval());
    }

    @Test
    public void testHandleIntervalIncorrect() {
        card.incrementReviewCount();
        int testInterval = (int) (max (1, (1 / (1 + 3 * card.getDifficulty()))));

        card.handleInterval(false);
        assertEquals(testInterval, card.getReviewInterval());
    }


}
