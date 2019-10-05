package com.example.sandboxapp.game_objects;

import android.graphics.Color;

import com.example.sandboxapp.math.Vec2f;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.PhysBox;
import com.example.sandboxapp.render.RenderBox;


public class SandParticle {

    public static float WIDTH_HEIGHT = 0.1f;
    public static float WIDTH_WIDTH = 0.1f;

    public static float SAND_MASS = 1.f;

    public static Vec2f DEFAULT_POS = new Vec2f(0.0f, 0.0f);


    private RenderBox m_renderBox;
    private PhysBox   m_physBox;


    public SandParticle ()
    {
        m_physBox = new PhysBox(DEFAULT_POS, WIDTH_WIDTH, WIDTH_HEIGHT,
                    new Vec2f(0.0f, 0.0f), new Vec2f(0.0f, 0.0f), SAND_MASS);

        m_renderBox = new RenderBox((GeomBox)m_physBox, Color.YELLOW);
    }

    public SandParticle (Vec2f pos)
    {
        m_physBox = new PhysBox(pos, WIDTH_WIDTH, WIDTH_HEIGHT,
                new Vec2f(0.0f, 0.0f), new Vec2f(0.0f, 0.0f), SAND_MASS);

        m_renderBox = new RenderBox((GeomBox)m_physBox, Color.YELLOW);
    }

    public RenderBox GetRenderBox () {
        return m_renderBox;
    }
}
