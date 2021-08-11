package Implementation;

import javax.swing.*;
import java.util.ArrayList;

public class GUI {
    private static JFrame frame;
    private static JPanel panel;
    private static ArrayList<CardDeck> decks = new ArrayList<>();

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setVisible(true);

        panel = new JPanel();
        frame.getContentPane().add(panel);


    }

}
