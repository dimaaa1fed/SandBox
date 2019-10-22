package com.example.sandboxapp.game_objects;

import com.example.sandboxapp.math.Vec2d;

public class Bucket extends StaticRect {
    static double W = 0.25;
    static double H = 0.4;

    public Bucket (Vec2d center)
    {
        super(center, W, H);
    }

}
