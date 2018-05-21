package com.bladejs.XInputSnake;

import java.util.Random;

public class Tools {
    static int getRandom(int bound){

        Random generator = new Random();
        return generator.nextInt(bound);

    }
}
