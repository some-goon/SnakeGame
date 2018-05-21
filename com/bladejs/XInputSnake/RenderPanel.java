package com.bladejs.XInputSnake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel{

    private static Color blue=new Color(7184107);
    private static Color green=new Color(9684093);
    private static Color red=new Color(14706278);
    private Color mix=Color.WHITE;

    RenderPanel(){
        super();
        Dimension dimension = new Dimension(Arena.WIDTH, Arena.HEIGHT);
        setPreferredSize(dimension);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(blue);
        g.fillRect(0,0,Arena.WIDTH,Arena.HEIGHT);
        drawFruit(g);
        drawSnake(g);
    }

    private void drawFruit(Graphics g) {
        g.setColor(red);
        Fruit fruit=Arena.fruit;
        int x=(int) fruit.point.getX();
        int y=(int) fruit.point.getY();
        g.fillRect(x,y,Arena.GRAIN-2,Arena.GRAIN-2);
    }

    private void drawSnake(Graphics g){
        g.setColor(green);
        Snake snake=Arena.snake;
        int x=(int) snake.getHead().getX();
        int y=(int) snake.getHead().getY();
        g.fillRect(x,y,Arena.GRAIN-2,Arena.GRAIN-2);
        ArrayList<Point> parts=snake.getTail();
        for (Point part : parts) {
            x = (int) part.getX();
            y = (int) part.getY();
            g.fillRect(x,y,Arena.GRAIN-2,Arena.GRAIN-2);
        }
    }
}
