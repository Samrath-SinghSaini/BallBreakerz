package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;

public class Gameplay extends JPanel  implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 0;

    //setting up co-oridnate var
    private int playerX = 310;


    private int ballPosX = 120;
    private int ballPosY = 350;

    private int ballXDir = -3;
    private int ballYDir = -4;
    private MapGenerator map;

    public Gameplay(){
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics){
        //setting background
        graphics.setColor(Color.black);
        graphics.fillRect(1,1,692,592);

        //scores
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString(" " + score, 590, 30);
        //drawing map
        map.drawBrick((Graphics2D) graphics);
        //setting borders
        graphics.setColor(Color.yellow);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(691, 0, 3, 592);

        //setting brick paddle
        graphics.setColor(Color.white);
        graphics.fillRect(playerX, 550, 100, 8);

        // the ball
        graphics.setColor(Color.white);
        graphics.fillOval(ballPosX, ballPosY, 20, 20);

        if(totalBricks <= 0){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            graphics.setColor(Color.red);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("YOU WON\nScores: " + score, 190, 300);

            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("Press Enter to restart " + score, 250, 350);
        }

        if(ballPosY > 570){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            graphics.setColor(Color.red);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("GAME OVER\nScores: " + score, 190, 300);

            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("Press Enter to restart ", 250, 350);
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        //detecting ball collision with borders or walls
        if(play){

            //detecting ball collision with paddle
            if(new Rectangle (ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYDir = -ballYDir;
            }
            A : for(int i = 0; i < map.map.length; i++){
                for(int j = 0; j < map.map[i].length; j++){
                    if(map.map[i][j] > 0){
                        int brickWidth = map .brickWidth;
                        int brickHeight= map.brickHeight;
                        int brickPosX = j* brickWidth + 80;
                        int brickPosY = i* brickHeight + 50;

                        Rectangle brickRectangle = new Rectangle(brickPosX, brickPosY, brickWidth, brickHeight);
                        Rectangle ballRectangle = new Rectangle(ballPosX, ballPosY, 20, 20);
                        if(ballRectangle.intersects(brickRectangle)){
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;
                            ballXDir++;
                            ballYDir++;
                            if(ballXDir > 6){ ballXDir = 6; }
                            if(ballXDir > 7){ ballXDir = 7; }
                            if(ballPosX + 19 <= brickRectangle.x || ballPosX + 1 >= brickRectangle.x + brickRectangle.width){
                                ballXDir = -ballXDir;
                            } else{
                                ballYDir = -ballYDir;
                            }
                            break A;
                        }

                    }
                }
            }

            //incrementing ball pos and changing ball pos when ball
            //reaches the borders
            ballPosX += ballXDir;
            ballPosY += ballYDir;
            if(ballPosX < 0){
                ballXDir = -ballXDir;
            }
            if(ballPosY < 0){
                ballYDir = -ballYDir;
            }
            if(ballPosX > 670){
                ballXDir = -ballXDir;
            }
        }

        // repainting graphics for ball after event or key press
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    //detecting left or right arrow key press and moving paddle and ball
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX > 600){
                playerX = 600;
            } else{
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX < 10){
                playerX = 10;
            } else{
                moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXDir = -1;
                ballYDir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }
    }

    //changing position of player
    public void moveRight(){
        play = true;
        playerX += 40;
    }

    public void moveLeft(){
        play = true;
        playerX -= 40;
    }


}
