package com.example.sandboxapp.game_objects;

import android.graphics.Color;

import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.PhysBox;
import com.example.sandboxapp.render.RenderBox;


public class StaticRect extends BoxObj {
    public static double STATIC_MASS = PhysBox.INFINITE_MASS;

    public StaticRect (Vec2d center, double w, double h)
    {
       super(center, w, h, STATIC_MASS, PhysBox.Type.OTHER);
    }
}
