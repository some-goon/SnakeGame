package com.bladejs.XInputSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

public class Arena implements ActionListener {

    public static final int WIDTH=800, HEIGHT=600, GRAIN=40;

    private JFrame frame;
    private RenderPanel renderPanel;
    private Timer timer=new Timer(20, this);
    private int tick = 0;
    public static Snake snake;
    public static Fruit fruit;
    public static Rectangle2D button=new Rectangle2D.Double(WIDTH/2-50, HEIGHT/2-50, 100, 100);

    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    Arena(){
        disableWarning();
        Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(WIDTH,HEIGHT);
        frame.setLocation((res.width-frame.getWidth())/2,(res.height-frame.getHeight())/2);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startGame();
    }

    private void startGame(){
        snake=new Snake();
        fruit=new Fruit();
        frame.add(renderPanel=new RenderPanel());
        frame.addKeyListener(snake);
        timer.start();
        frame.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        renderPanel.repaint();
        tick++;
        if(tick%5==0){
            snake.move();
        }
    }
}
