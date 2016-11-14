package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.Math.abs;


public class GamePanel extends JPanel implements KeyListener {

    private final int WIDTH=75, HEIGHT=40;  //wymiary planszy, plansza 90x50 kwadracików, każdy rozmiaru 20 px
    private final int SQUARE_SIZE=20;

    Snake snake = new Snake();
    Apple apple = new Apple();


    public GamePanel(){
        super();

        setFocusable(true);

        setPreferredSize(new Dimension(WIDTH*SQUARE_SIZE, HEIGHT*SQUARE_SIZE));

        setBackground(Color.BLACK);

        addKeyListener(this);

        setVisible(true);

        setDoubleBuffered(true);

        new snakeMovement().start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.paint(g);
        apple.paint(g);
    }


    public void keyTyped(KeyEvent e){}

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && snake.direction!=Snake.LEFT && snake.wasMovedOnce){
            snake.direction=Snake.RIGHT;
            snake.wasMovedOnce=false;
        }

        if(e.getKeyCode()==KeyEvent.VK_LEFT && snake.direction!=Snake.RIGHT && snake.wasMovedOnce) {
            snake.direction=Snake.LEFT;
            snake.wasMovedOnce=false;
        }

        if(e.getKeyCode()==KeyEvent.VK_UP && snake.direction!=Snake.DOWN && snake.wasMovedOnce){
            snake.direction=Snake.UP;
            snake.wasMovedOnce=false;
        }

        if(e.getKeyCode()==KeyEvent.VK_DOWN && snake.direction!=Snake.UP && snake.wasMovedOnce){
            snake.direction=Snake.DOWN;
            snake.wasMovedOnce=false;
        }
    }
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE) System.exit(0);
    }


    //--------------KLASY WEWNĘTRZNE-------------------------------

    class Snake{
        private int length;     //długość węża aktualna, bez głowy, w ilościach kwadratów

        private static final int LEFT=0, RIGHT=1, UP=2, DOWN=3;    //aktualny kierunek węża
        private int direction;

        private LinkedList<Ind2d> snakeSquares = new LinkedList<Ind2d>();   //lista indeksów kwadracików, bez głowy

        private Ind2d headLocation;
        private Ind2d startingPoint= new Ind2d(WIDTH/2, HEIGHT/2);

        private Color SNAKE_COLOR=Color.WHITE;

        private LinkedList<Ind2d> freeLocs = new LinkedList<Ind2d>();

        private boolean wasMovedOnce;


        public Snake(){
            headLocation = startingPoint;

            direction=DOWN;

            Ind2d tmp = new Ind2d();
            tmp.set(headLocation);
            tmp.moveRight();
            snakeSquares.add(tmp);

            Ind2d tmp2 = new Ind2d();
            tmp2.set(tmp);
            tmp2.moveRight();
            snakeSquares.add(tmp2);

            for(int i=0; i<WIDTH*HEIGHT; i++)
                if(toInd2d(i)!=tmp && toInd2d(i)!=tmp2)freeLocs.add(toInd2d(i));


        }

        //OBSŁUGA LINEARYZACJI I PRZEJŚCIA NA PIKSELE
        private Ind2d toInd2d(int linearInd){   //zwraca indeksy w dwuwymiarowej tablicy planszy (z linearnego indeksu)
            int x, y;
            x=linearInd%WIDTH;
            y=linearInd/WIDTH;
            Ind2d ind = new Ind2d(x, y);
            return ind;
        }

        private int toLinearInd(Ind2d ind){    //zwraca linearny indeks ze współrzędnych tablicy planszy
            int x = ind.getX();
            int y = ind.getY();
            return y*WIDTH + x;
        }

        private Ind2d toLocation(Ind2d ind){      //zwraca lewy górny róg prostokąta o współrzędnych ind
            int x = ind.getX()*SQUARE_SIZE;
            int y = ind.getY()*SQUARE_SIZE;
            Ind2d toRet = new Ind2d(x, y);
            return toRet;
        }
        //--------------------------------------------



        public void moveSnake(){
            Ind2d mem = new Ind2d(headLocation);

            if(direction == Snake.LEFT)
                headLocation.moveLeft(WIDTH);
            if(direction == Snake.RIGHT)
                headLocation.moveRight(WIDTH);
            if(direction == Snake.UP)
                headLocation.moveUp(HEIGHT);
            if(direction == Snake.DOWN)
                headLocation.moveDown(HEIGHT);

            snakeSquares.addFirst(mem);
            snakeSquares.removeLast();

            wasMovedOnce=true;

        }


        void paint(Graphics g){
            g.setColor(SNAKE_COLOR);
            Ind2d LocPx = toLocation(headLocation);

            g.fillOval(LocPx.getX(), LocPx.getY(), SQUARE_SIZE, SQUARE_SIZE);     //rysujemy głowę

            for(Ind2d i : snakeSquares){                                                    //rysowanie ciała węża
                LocPx = toLocation(i);
                g.setColor(SNAKE_COLOR);
                g.fillRect(LocPx.getX(), LocPx.getY(), SQUARE_SIZE, SQUARE_SIZE);
                g.setColor(Color.RED);
                g.fillRect(LocPx.getX() +SQUARE_SIZE/5, LocPx.getY() +SQUARE_SIZE/5, SQUARE_SIZE-2*SQUARE_SIZE/5, 4*SQUARE_SIZE/5-2*SQUARE_SIZE/5);
            }

        }
    }





    class Apple{
        Ind2d location;     //będzie się zmieniała po zjedzeniu
        Color appleColor;
        int appleSize;

        Random randMachine = new Random();

        public Apple(){
            changeLocation();
            appleColor = Color.RED;
            appleSize = 20;
        }

        public void changeLocation(){
            int i = abs(randMachine.nextInt()%snake.freeLocs.size());
            location = snake.freeLocs.get(i);
        }

        void paint(Graphics g){
            g.setColor(appleColor);
            Ind2d locationPX = new Ind2d();
            locationPX = toLocation(location);
            g.fillOval(locationPX.getX(), locationPX.getY(), appleSize, appleSize);
        }

        private Ind2d toLocation(Ind2d ind){      //zwraca lewy górny róg prostokąta o współrzędnych ind
            int x = ind.getX()*SQUARE_SIZE;
            int y = ind.getY()*SQUARE_SIZE;
            Ind2d toRet = new Ind2d(x, y);
            return toRet;
        }

    }

    class snakeMovement extends Thread {

        private boolean isCrashed(){
            for(Ind2d x: snake.snakeSquares){
                if(x.isEqual(snake.headLocation)) return true;
            }
            return false;
        }

        private void gameOver(){
            System.out.println("Tu bedzie koniec gry");
        }

        private void wait(int millis){
            try {
                sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run(){
            Ind2d mem = new Ind2d();    //tu będziemy pamiętali ostatni kwadracik w ciele węża (ogon)
            while(true){
                wait(50);
                mem.set(snake.snakeSquares.getLast());
                if(isCrashed()){
                    gameOver();
                    break;
                }
                if(snake.headLocation.isEqual(apple.location)){
                    System.out.print("zebrane jablko");
                    apple.changeLocation();
                    snake.moveSnake();
                    snake.snakeSquares.addLast(mem);
                    continue;
                }

                snake.moveSnake();
                updateUI();
                repaint();
            }
        }
    }
}
