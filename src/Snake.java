import javax.swing.JFrame;
import java.awt.Color;
/**
 * @author Jakub Serweta
 */
public class Snake {

    /**
     * Set the board options and start the game.
     */
    public static void main(String[] args) {
	    JFrame gameUI = new JFrame();

	    Board board = new Board();

        gameUI.setBounds(10,10,905,700);
        gameUI.setBackground(Color.DARK_GRAY);
        gameUI.setTitle("Snake");
        gameUI.setResizable(false);
        gameUI.setVisible(true);
        gameUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameUI.add(board);
    }
}
