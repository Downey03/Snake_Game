import javax.swing.*;

public class SnakeGame extends JFrame {

    private GameBoard board;

    public SnakeGame()
    {

        board =new GameBoard();
        setBounds(400,100,board.width,board.height);
        add(board);
        setResizable(false);
        pack();
        setVisible(true);

    }
    public static void main(String[] args)
    {
        SnakeGame game=new SnakeGame();

    }

}