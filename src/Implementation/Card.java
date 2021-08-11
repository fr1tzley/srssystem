package Implementation;

import Exceptions.InvalidAnswerException;
import Exceptions.InvalidPromptException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.lang.Double.max;
import static java.lang.Double.min;


//A class for a simple flash card, with a prompt and answer stored. The flash card also stores the date of it's most
//recent review, as well as the date it will be due for review next. Difficulty is calculated by how difficult
//users found the card to answer when reviewing, and reviewInterval is used to calculate the interval of days between
//today and the next review.
public class Card {

    String prompt;
    String answer;
    LocalDate nextReview;
    LocalDate lastReview;
    Double difficulty;
    int reviewInterval;
    int reviewCount;



    //EFFECTS:Constructs a new card intended to be reviewed today, with a reviewInterval of 1 and a difficulty rating
    //of 0.3. Throws exceptions if the prompt or answer are blank.
    public Card (String prompt, String answer) throws InvalidPromptException, InvalidAnswerException {
        if (prompt.trim().length() == 0) {
            throw new InvalidPromptException();
        }
        if (answer.trim().length() == 0) {
            throw new InvalidAnswerException();
        }

        this.prompt = prompt;
        this.answer = answer;
        nextReview = LocalDate.now();
        difficulty = 0.3;
        reviewInterval = 1;
        reviewCount = 1;
    }

    //MODIFIES:this
    //EFFECTS:based off of whether the answer was correct or not, and the rating the user gave their answer,
    //modify the difficulty and review interval before calculating the next date for review.
    public void handleAnswer(boolean correct, double rating) {
        lastReview = LocalDate.now();


        handleDifficulty(rating);
        handleInterval(correct);

        nextReview = nextReview.plusDays(reviewInterval);
        this.incrementReviewCount();
    }

    //MODIFIES:this
    //EFFECTS:alters difficulty based on how easy it was to answer. It will increase if the answer was incorrect, or
    //difficult to figure out. If not, it will increase proportionately to how easily the card was answered.
    //Difficulty is then clamped to [0, 1].
    public void handleDifficulty(double rating) {
        double newDifficulty = difficulty + 0.06 * (3 - 8 * (rating));

       difficulty = max(0, min(1, newDifficulty));
    }


    //MODIFIES:this
    //EFFECTS:alters the review interval based off of whether the answer was correct, and how the user rated their
    //answer. If this is the first time an item has been reviewed, the interval is always set to 1.
    public void handleInterval(boolean correct) {
        if (reviewCount == 1) {
            reviewInterval = 1;
        } else {
            if (correct) {
                double difficultyWeight = 3 - 1.7 * difficulty;
                reviewInterval *= (int) (1 + (difficultyWeight - 1));
            } else {
                reviewInterval = (int) (max(1, (1 / (1 + 3 * difficulty))));
            }
        }
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public LocalDate getNextReview() {
        return nextReview;
    }

    public LocalDate getLastReview() {
        return lastReview;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public int getReviewInterval() {
        return reviewInterval;
    }

    public void incrementReviewCount() {
        reviewCount++;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    //EFFECTS:returns the number of days between today and the unit's due date.
    public int getDaysTillDue() {
        return (int) LocalDate.now().until(nextReview, ChronoUnit.DAYS);
    }
}
