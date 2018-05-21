package com.bladejs.XInputSnake;

import java.awt.*;

public class Fruit {
    Point point;

    Fruit(){
        point=new Point();
        point.x=Tools.getRandom(Arena.WIDTH/Arena.GRAIN)*Arena.GRAIN;
        point.y=Tools.getRandom(Arena.HEIGHT/Arena.GRAIN)*Arena.GRAIN;
    }
}
