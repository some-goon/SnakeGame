package com.bladejs.XInputSnake;
import com.ivan.xinput.*;
import com.ivan.xinput.enums.XInputButton;
import com.ivan.xinput.exceptions.XInputNotLoadedException;
import com.ivan.xinput.listener.SimpleXInputDeviceListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Snake implements KeyListener {

    private Point head;
    private ArrayList<Point> tail=new ArrayList<>();
    private static final int UP=0, DOWN=1, LEFT=2, RIGHT=3;
    private int direction;
    private int prvDirection;
    private XInputDevice[] devices;
    private XInputDevice device;
    private boolean diagonal=true;
    Snake(){
        try {
            devices = XInputDevice.getAllDevices();
            device = XInputDevice.getDeviceFor(0);
            device.addListener(new SimpleXInputDeviceListener() {
                @Override
                public void connected() {

                }

                @Override
                public void disconnected() {

                }

                @Override
                public void buttonChanged(XInputButton button, boolean b) {
                    switch(button){
                        default:
                            System.out.println(button);
                            break;
                        case DPAD_UP:
                            direction=UP;
                            break;
                        case DPAD_DOWN:
                            direction=DOWN;
                            break;
                        case DPAD_RIGHT:
                            direction=RIGHT;
                            break;
                        case DPAD_LEFT:
                            direction=LEFT;
                            break;
                    }
                }
            });
        } catch (XInputNotLoadedException ignored){}
        reset();
    }

    public void move(){
        getXInput();
        device.poll();
        int n=Arena.GRAIN;
        tryToEat();
        checkCollision();
        if(tail.size()>0){
            updateTail();
        }
        switch(direction) {
            case UP:
                if(prvDirection!=DOWN) {
                    head.y -= n;
                    prvDirection = UP;
                }
                else {
                    head.y+=n;
                    prvDirection = DOWN;
                }
                break;
            case DOWN:
                if(prvDirection!=UP) {
                    head.y += n;
                    prvDirection = DOWN;
                }else {
                    head.y-=n;
                    prvDirection = UP;
                }
                break;
            case LEFT:
                if(prvDirection!=RIGHT) {
                    head.x -= n;
                    prvDirection = LEFT;
                }else {
                    head.x+=n;
                    prvDirection = RIGHT;
                }
                break;
            case RIGHT:
                if(prvDirection!=LEFT) {
                    head.x += n;
                    prvDirection = RIGHT;
                }else {
                    head.x-=n;
                    prvDirection = LEFT;
                }
                break;
        }
    }

    private void getXInput() {
        if (device.poll()) {
            XInputComponents components = device.getComponents();
            XInputButtons buttons = components.getButtons();
            XInputAxes axes = components.getAxes();
            int dpad=axes.dpad;
            switch(dpad){
                case 2:
                    if(diagonal){
                        direction=RIGHT;
                        diagonal=false;
                        break;
                    }
                case 1:
                    direction=UP;
                    diagonal=true;
                    break;
                case 4:
                    if(diagonal){
                        direction=DOWN;
                        diagonal=false;
                        break;
                    }
                case 3:
                    direction=RIGHT;
                    diagonal=true;
                    break;
                case 6:
                    if(diagonal){
                        direction=LEFT;
                        diagonal=false;
                        break;
                    }
                case 5:
                    direction=DOWN;
                    diagonal=true;
                    break;
                case 0:
                    if(diagonal){
                        direction=UP;
                        diagonal=false;
                        break;
                    }
                case 7:
                    direction=LEFT;
                    diagonal=true;
                    break;
            }
            float x=axes.lx;
            float y=axes.ly;
            if(abs(x)>0.2){
                if(abs(x)>abs(y)){
                    if(x>0) direction=RIGHT;
                    else direction=LEFT;
                }else{
                    if(y>0) direction=UP;
                    else direction=DOWN;
                }
            }else if(abs(y)>0.2){
                if(y>0) direction=UP;
                else direction=DOWN;
            }
        }
    }

    private void checkCollision() {
        for(Point part : tail) {
            if(head.equals(part)) {
                reset();
                break;
            }
        }
        if(head.x>Arena.WIDTH || head.x<0 || head.y<0 || head.y>Arena.HEIGHT) reset();
    }

    private void reset() {
        head=new Point();
        head.x=Tools.getRandom(Arena.WIDTH/Arena.GRAIN)*Arena.GRAIN;
        head.y=Tools.getRandom(Arena.HEIGHT/Arena.GRAIN)*Arena.GRAIN;
        tail.clear();
        if(Arena.WIDTH-head.getX()>Arena.WIDTH/2) direction=RIGHT;
        else direction=LEFT;
    }

    private void updateTail() {
        Point temp=new Point();
        Point temp2;
        temp.x=(int) head.getX();
        temp.y=(int) head.getY();
        for(int i=0; i<tail.size(); i++){
            temp2=tail.get(i);
            tail.set(i,temp);
            temp=temp2;
        }
    }

    private void tryToEat(){
        if(head.equals(Arena.fruit.point)) {
            Arena.fruit = new Fruit();
            tail.add(new Point());
        }
    }

    public Point getHead(){
        return head;
    }

    public ArrayList<Point> getTail(){
        return tail;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode=e.getKeyCode();
        switch(keyCode){
            case KeyEvent.VK_UP:
                direction=UP;
                break;
            case KeyEvent.VK_DOWN:
                direction=DOWN;
                break;
            case KeyEvent.VK_LEFT:
                direction=LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                direction=RIGHT;
                break;
            }
        }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
