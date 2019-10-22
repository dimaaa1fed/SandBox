package com.example.sandboxapp.game_objects;

import android.graphics.Color;
import android.widget.HeaderViewListAdapter;

import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.PhysBox;
import com.example.sandboxapp.render.RenderBox;


public class SandParticle extends BoxObj {
    public static double WIDTH  = 0.01;
    public static double HEIGHT = 0.01;

    public static double SAND_MASS = 1;

    public SandParticle (Vec2d center)
    {
        super(center, WIDTH, HEIGHT, SAND_MASS, PhysBox.Type.SAND);
    }
}
