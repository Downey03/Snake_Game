import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import javax.sound.sampled.spi.AudioFileReader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;



public class GameBoard extends JPanel implements ActionListener {

    int height=500;
    int width=height;
    int[] x = new int[height*width];
    int[] y = new int[height*width];
    int dots = 3;
    int apple_x;
    int apple_y;
    int dot_size=10;
    Image head;
    Image body;
    Image apple;
    boolean up=false;
    boolean down=false;
    boolean left=true;
    boolean right=false;
    Timer timer;
    int delay=80;
    int RAND_POS=(height-10)/10;
    boolean ingame=true;
    static File audioFood;
    static File audioGameOver;
    AudioInputStream gameoverClip;
    Clip clip1;
    Clip foodClip;

    public GameBoard()
    {

        setPreferredSize(new Dimension(width,height));
        setBackground(Color.black);
        appleSpawner();
        loadImg();
        initGame();
        addKeyListener(new Tadapter());
        setFocusable(true);


    }
    public void initGame()
    {
        dots=3;
        for(int i=0;i<dots;i++)
        {
            x[i]=150+(i*dot_size);
            y[i]=150;
        }
        timer =new Timer(delay,this);
        timer.start();

    }
    public void loadImg()
    {
        ImageIcon image_apple=new ImageIcon("src/resources/apple.png");
        apple = image_apple.getImage();

        ImageIcon image_head=new ImageIcon("src/resources/head.png");
        head = image_head.getImage();

        ImageIcon image_body=new ImageIcon("src/resources/dot.png");
        body = image_body.getImage();
    }
    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        if(ingame)
        {
            graphics.drawImage(apple ,apple_x ,apple_y , this);
            for(int i=0;i<dots;i++)
            {
                if(i==0) {
                    graphics.drawImage(head, x[0], y[0], this);
                }
                else {
                    graphics.drawImage(body, x[i], y[i], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else
            gameOver(graphics);
    }
    private static void files()
    {
        audioGameOver = new File("./resources/mixkit-unlock-game-notification-253.wav");
        audioFood = new File("./resources/mixkit-wrong-answer-fail-notification-946.wav");
    }
    private void move()
    {
        for(int i=dots-1;i>0;i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
            if(left)
                x[0]-=dot_size;
            if(right)
                x[0]+=dot_size;
            if(up)
                y[0]-=dot_size;
            if(down)
                y[0]+=dot_size;

    }
    private void appleSpawner()
    {
        int r=(int)(Math.random()*RAND_POS);
        apple_x=dot_size*r;
        r=(int)(Math.random()*RAND_POS);
        apple_y=dot_size*r;
    }
    private void foodCollisionChecker()
    {
        if(x[0]==apple_x && y[0]==apple_y)
        {
            appleSpawner();
            dots++;
        }
    }
    private void outOfBoundary(){
        if(x[0]<0)
            ingame=false;
        if(x[0]>=width)
            ingame=false;
        if(y[0]<0)
            ingame=false;
        if(y[0]>=height)
            ingame=false;
        for(int i=3;i<dots;i++) {
            if (x[0] == x[i] && y[0] == y[i]){
                ingame = false;
            break;
            }
        }
    }
    private void gameOver(Graphics graphics){

//        gameoverClip = AudioSystem.getAudioInputStream(new File("./resources/mixkit-wrong-answer-fail-notification-946.wav").getAbsoluteFile());
//        clip1 = AudioSystem.getClip();
//        clip1.open(gameoverClip);
//        clip1.loop(1);
        String msg = "Game Over";
        Font small = new Font("Helvetica",Font.BOLD,16);
        FontMetrics metrics = getFontMetrics(small);
        graphics.setColor(Color.CYAN);
        graphics.setFont(small);
        graphics.drawString(msg,(width-metrics.stringWidth(msg))/2,height/2);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(ingame){
            move();
            foodCollisionChecker();
            outOfBoundary();
        }
        repaint();
    }
    public class Tadapter extends KeyAdapter
    {
        @Override
        public void keyPressed (KeyEvent k)
        {
            int key=k.getKeyCode();
            if((key==KeyEvent.VK_LEFT) && !right)
            {
                left=true;
                up=false;
                down=false;
            }
            if((key==KeyEvent.VK_RIGHT) && !left)
            {
                right=true;
                up=false;
                down=false;
            }
            if((key==KeyEvent.VK_UP) && !down)
            {
                up=true;
                left=false;
                right=false;
            }
            if((key==KeyEvent.VK_DOWN) && !up)
            {
                down=true;
                left=false;
                right=false;
            }

        }
    }

}
