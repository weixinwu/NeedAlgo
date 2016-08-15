import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by weixinwu on 2016-08-10.
 */
public class GameCanvas extends JPanel implements ActionListener {

    private final int width = 459;
    private final int height = 459;
    private Image snake;
    private Image heart;
    private ArrayList<SnakeBody>snakeBodies;
    private final int timeoutVal = 0;
    private Timer timer ;
    private Point heartPosition;
    private int direction;




    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
        SnakeBody  skb = snakeBodies.get(snakeBodies.size()-1);
        SnakeBody tempSnakebody = new SnakeBody(new Point(skb.getPoint()),skb.getDirection());
        move();

        if (snakeBodies.get(0).getPoint().x==(heartPosition.x)&&(snakeBodies.get(0).getPoint().y==heartPosition.y)){
            snakeBodies.add(tempSnakebody);



            //
//            if (direction==KeyEvent.VK_UP) {
//                snakeX.add(snakeX.get(snakeX.size()-1));
//                snakeY.add(snakeY.get(snakeY.size()-1)+9);
//
//            }
//            else if (direction==KeyEvent.VK_DOWN){
//                snakeX.add(snakeX.get(snakeX.size()-1));
//                snakeY.add(snakeY.get(snakeY.size()-1)-9);
//            }
//            else if (direction==KeyEvent.VK_LEFT)
//            {
//                snakeX.add(snakeX.get(snakeX.size()-1)+9);
//                snakeY.add(snakeY.get(snakeY.size()-1));
//            }else {
//                snakeX.add(snakeX.get(snakeX.size()-1)-9);
//                snakeY.add(snakeY.get(snakeY.size()-1));
//            }

            newHeartPosition();


        }
        setDirection();


    }

    public GameCanvas(){

        snakeBodies = new ArrayList<SnakeBody>();
        addKeyListener(new Listener());
        setFocusable(true);
        setBackground(java.awt.Color.GRAY);
        setSize(width,height);
        loadImage();
        repaint();

        snakeBodies.add(new SnakeBody(new Point(90,180),KeyEvent.VK_RIGHT));
        direction = KeyEvent.VK_RIGHT;


        heartPosition = new Point(180,180);
        timer = new Timer(timeoutVal,this);
        timer.start();

    }


    private class Listener implements KeyListener{

        private int keyCode;
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            keyCode = e.getKeyCode();
            SnakeBody temphead = snakeBodies.get(0);
            snakeBodies.set(0,new SnakeBody(new Point(temphead.getPoint().x,temphead.getPoint().y),keyCode));
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
    private void loadImage(){
        String path = Paths.get(".").toAbsolutePath().normalize().toString()+"/src/";

        snake = new ImageIcon(path+"yellow.png").getImage();
        heart = new ImageIcon(path+"red_heart.png").getImage();

    }
    private void setDirection(){
        if (snakeBodies.get(0).getPoint().y==heartPosition.y) {
            int vLeft = Math.abs((snakeBodies.get(0).getPoint().x - 9) - heartPosition.x);
            int vRight = Math.abs((snakeBodies.get(0).getPoint().x + 9) - heartPosition.x);
            if (vLeft > vRight)
                direction = KeyEvent.VK_RIGHT;
            else
                direction = KeyEvent.VK_LEFT;
        }else if (snakeBodies.get(0).getPoint().x==heartPosition.x){
            int vUp = Math.abs((snakeBodies.get(0).getPoint().y - 9) - heartPosition.y);
            int vDown = Math.abs((snakeBodies.get(0).getPoint().y + 9) - heartPosition.y);

            if (vUp > vDown)
                direction = KeyEvent.VK_DOWN;
            else
                direction = KeyEvent.VK_UP;

        }else {
            int vLeft = Math.abs((snakeBodies.get(0).getPoint().x - 9) - heartPosition.x);
            int vRight = Math.abs((snakeBodies.get(0).getPoint().x + 9) - heartPosition.x);
            if (vLeft > vRight)
                direction = KeyEvent.VK_RIGHT;
            else
                direction = KeyEvent.VK_LEFT;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(heart,(int)heartPosition.getX(),(int)heartPosition.getY(),this);
        for (int i = 0 ; i <snakeBodies.size();i++) {
            g.drawImage(snake, snakeBodies.get(i).getPoint().x,snakeBodies.get(i).getPoint().y, this);
        }


        Toolkit.getDefaultToolkit().sync();


    }
    private void newHeartPosition(){
        heartPosition.x = new Random().nextInt(51);
        heartPosition.x = heartPosition.x*9;

        heartPosition.y = new Random().nextInt(51);
        heartPosition.y = heartPosition.y*9;

    }
    private void move() {
        int size = snakeBodies.size();
        SnakeBody tempPre = snakeBodies.get(0);
        SnakeBody tempOld;
        for (int i = 1; i < size; i++) {
            //store the value of snake body before it gets changed
            tempOld = snakeBodies.get(i);
            snakeBodies.set(i, new SnakeBody(new Point((tempPre.getPoint().x ), tempPre.getPoint().y), tempPre.getDirection()));
            tempPre = tempOld;
        }
        Point headPoint= snakeBodies.get(0).getPoint();
            if (direction == KeyEvent.VK_RIGHT)
                snakeBodies.set(0, new SnakeBody(new Point((headPoint.x + snake.getWidth(this)), headPoint.y), direction));
            else if (direction == KeyEvent.VK_LEFT)
                snakeBodies.set(0, new SnakeBody(new Point((headPoint.x - snake.getWidth(this)), headPoint.y), direction));
            else if (direction == KeyEvent.VK_UP)
                snakeBodies.set(0, new SnakeBody(new Point((headPoint.x), headPoint.y - snake.getHeight(this)), direction));
            else if (direction == KeyEvent.VK_DOWN)
                snakeBodies.set(0, new SnakeBody(new Point((headPoint.x),headPoint.y + snake.getHeight(this)), direction));


    }
    public class SnakeBody{
        private Point p;
        private int direction;
        public SnakeBody(Point p,int direction){
            this.p = p;
            this.direction = direction;
        }
        public Point getPoint(){
            return this.p;
        }
        public int getDirection(){
            return this.direction;
        }
        public void setDirection(int direction){
            this.direction = direction;
        }
        public void setPoint(int x,int y){

            p.setLocation(x,y);
        }
    }

}
