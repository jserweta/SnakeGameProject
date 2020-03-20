import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * @author Jakub Serweta
 *
 * Represents implementation of board.
 */

public class Board extends JPanel implements KeyListener, ActionListener {
    /**
     * xLength set width of the board
     * yLength set height of the board
     */
    private int[] xLength = new int[750];
    private int[] yLength = new int[750];

    /** Size of each element on the board. */

    private int elementSize = 25;
    /**  Variables which are incremented when snake collect an apple. */
    private int score = 0;
    private int snakeLength = 3;

    /** Delay which is used by a timer. */
    private int delay = 100;

    /** Checks if snake is moving from initial position. */
    private int moves = 0;

    /** Time which program need to set speed of snake. */
    private Timer timer;

    /** Set of variables which are changed when snake is moving left, right, up or down. */
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    /** If snake hit the wall ar himself, then it is changed to true. */
    private boolean gameOver = false;


    /**
     * <p>
     *     Set of variables which are an implementation of the
     *     Icon interface that paints different positions of
     *     head, tail and apple.
     * </p>
     */
    private ImageIcon rightHead;
    private ImageIcon upHead;
    private ImageIcon downHead;
    private ImageIcon leftHead;
    private ImageIcon tail;
    private ImageIcon apple;

    /** X coordinate of an apple on the board. */
    private int xApplePosition;

    /** Y coordinate of an apple on the board. */
    private int yApplePosition;

    /** Table of specific X coordinates, which programme use to randomize position of apple. */
    private int [] appleX = {50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,
                            475,500,525,550,575,600,625,650,675,700,725,750,775,800,825};

    /** Table of specific Y coordinates, which programme use to randomize position of apple. */
    private int [] appleY = {100,125,150,175,200,225,250,275,300,325,350,375,400,425,
                                450,475,500,525,550,575,600};

    /** An instance of this class is used to generate a stream of pseudorandom numbers. */
    private Random random = new Random();

    /**
     * The constructor
     * It starts the whole game. This method locates an apple, starts an timer and add key listeners.
     */
    public Board(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        locateApple();
        timer = new Timer(delay, this);
        timer.start();
    }


    /**
     *  This method paints the GUI of the programme.
     *  <p>
     *      It draw title and game containers,
     *      set up the graphics which are needed during the game
     *      and set up snake position.
     *
     *      This method is handled until the param gameover is false.
     *  </p>
     *
     * @param g  uses the Graphics class, which is the abstract base class for all graphics contexts that allow an application to draw onto components.
     */
    public void paint(Graphics g){
            if (moves == 0){
                xLength[2] = 50;
                xLength[1] = 75;
                xLength[0] = 100;

                yLength[2] = 100;
                yLength[1] = 100;
                yLength[0] = 100;
            }

            //draw Title box
            g.setColor(Color.WHITE);
            g.drawRect(24, 10,851,55);

            g.setColor(new Color(34,155,3));
            g.fillRect(25,11,850,54);

            //draw Title
            g.setColor(Color.WHITE);
            String msg = "Snake";
            g.setFont(new Font("arial", Font.BOLD, 30));
            g.drawString(msg, 420, 50);

            //draw Board
            g.setColor(Color.WHITE);
            g.drawRect(24,74,851,577);

            g.setColor(Color.BLACK);
            g.fillRect(25,75,850,575);


            //set Images
            rightHead = new ImageIcon(getClass().getResource("resources/headRight.png"));
            rightHead.paintIcon(this, g, xLength[0], yLength[0]);

            for(int i = 0; i < snakeLength; i++){
                if(i==0 && right){
                    rightHead = new ImageIcon(getClass().getResource("resources/headRight.png"));
                    rightHead.paintIcon(this, g, xLength[i], yLength[i]);
                }
                if(i==0 && left){
                    leftHead = new ImageIcon(getClass().getResource("resources/headLeft.png"));
                    leftHead.paintIcon(this, g, xLength[i], yLength[i]);
                }
                if(i==0 && down){
                    downHead = new ImageIcon(getClass().getResource("resources/headDown.png"));
                    downHead.paintIcon(this, g, xLength[i], yLength[i]);
                }
                if(i==0 && up){
                    upHead = new ImageIcon(getClass().getResource("resources/headUp.png"));
                    upHead.paintIcon(this, g, xLength[i], yLength[i]);
                }

                if(i!=0){
                    tail = new ImageIcon(getClass().getResource("resources/tail.png"));
                    tail.paintIcon(this,g,xLength[i],yLength[i]);
                }
            }

            apple = new ImageIcon(getClass().getResource("resources/apple.png"));
            apple.paintIcon(this,g,appleX[xApplePosition],appleY[yApplePosition]);

            if(gameOver){
                gameOver(g);
            }

            g.dispose();


    }

    /**
     * checkApple method
     *
     *  This method check if head of snake hit an apple and locate new apple.
     */
    private void checkApple() {

        if ((xLength[0] == appleX[xApplePosition]) && (yLength[0] == appleY[yApplePosition])) {
            score++;
            snakeLength++;
            locateApple();
        }
    }

    /**
     * locateApple method
     *
     *  This method locate an apple on the board.
     */
    private void locateApple() {
        xApplePosition = random.nextInt(32);
        yApplePosition = random.nextInt(21);
    }

    /**
     * move method
     *
     *  This method move the tail of the snake using road of the head.
     */
    private void move() {

        for (int z = snakeLength; z > 0; z--) {
            xLength[z] = xLength[(z - 1)];
            yLength[z] = yLength[(z - 1)];
        }

        if (left) {
            xLength[0] -= elementSize;
        }

        if (right) {
            xLength[0] += elementSize;
        }

        if (up) {
            yLength[0] -= elementSize;
        }

        if (down) {
            yLength[0] += elementSize;
        }
    }

    /**
     * gameOver method
     *
     *  This method draw 'Game over' information and shows the current score.
     *
     * @param g  uses the Graphics class, which is the abstract base class for all graphics contexts that allow an application to draw onto components.
     */
    private void gameOver(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 50));
        g.drawString("Game Over", 300, 300);

        g.setFont(new Font("arial", Font.BOLD, 20));
        g.drawString("Space to RESTART", 340, 340);

        g.setColor(Color.WHITE);
        g.setFont( new Font("arial", Font.PLAIN, 20));
        g.drawString("Score: " +score, 390,380);
    }

    /**
     * checkCollision method
     *
     *  This method check if the snake hit the wall or himself.
     */
    private void checkCollision() {

        for(int b = 1; b < snakeLength; b++){
            if(xLength[b] == xLength[0] && yLength[b] == yLength[0]) {
                right = false;
                left = false;
                down = false;
                up = false;
                gameOver = true;
            }
        }

        if(right){
            if(xLength[0] >= 825) {
                gameOver = true;
            }
        }
        if(left){
            if(xLength[0] <= 50) {
                gameOver = true;
            }
        }
        if(down){
            if(yLength[0] >= 600) {
                gameOver = true;
            }
        }
        if(up){
            if(yLength[0] <= 100) {
                gameOver = true;
            }
        }
    }

    /** Override method of ActionListener interface.
     *
     * It is repainting the board as long as the game is not over.
     * */
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (!gameOver) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    /** Override method of KeyListener interface.
     *
     * It is changing the direction where snake is moving.
     * It restarts the game when player press the space button.
     * */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            gameOver = false;
            moves = 0;
            score = 0;
            snakeLength = 3;
            repaint();
        }

        if ((key == KeyEvent.VK_LEFT) && (!right)) {
            moves++;
            left = true;
            up = false;
            down = false;
        }

        if ((key == KeyEvent.VK_RIGHT) && (!left)) {
            moves++;
            right = true;
            up = false;
            down = false;
        }

        if ((key == KeyEvent.VK_UP) && (!down)) {
            moves++;
            up = true;
            right = false;
            left = false;
        }

        if ((key == KeyEvent.VK_DOWN) && (!up)) {
            moves++;
            down = true;
            right = false;
            left = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
