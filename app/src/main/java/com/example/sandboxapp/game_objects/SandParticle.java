package com.example.sandboxapp.game_objects;

import android.graphics.Color;

import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.PhysBox;
import com.example.sandboxapp.render.RenderBox;


public class SandParticle {

    public static double HEIGHT = 0.05;
    public static double WIDTH = 0.05;

    public static double SAND_MASS = 1;

    public static Vec2d DEFAULT_POS = new Vec2d(0.0f, 0.0f);


    private RenderBox m_renderBox;
    private PhysBox   m_physBox;


    public SandParticle ()
    {
        m_physBox = new PhysBox(DEFAULT_POS, WIDTH, HEIGHT,
                    new Vec2d(0.0f, 0.0f), new Vec2d(0.0f, 0.0f), SAND_MASS);

        m_renderBox = new RenderBox((GeomBox)m_physBox, Color.YELLOW);
    }

    public SandParticle (Vec2d pos)
    {
        m_physBox = new PhysBox(pos, WIDTH, HEIGHT,
                new Vec2d(0.0f, 0.0f), new Vec2d(0.0f, 0.0f), SAND_MASS);

        m_renderBox = new RenderBox((GeomBox)m_physBox, Color.YELLOW);
    }

    public RenderBox GetRenderBox () {
        return m_renderBox;
    }
}
