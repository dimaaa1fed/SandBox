package com.example.sandboxapp.game_objects;

import android.graphics.Color;

import com.example.sandboxapp.game.Render;
import com.example.sandboxapp.math.Vec2f;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.PhysBox;
import com.example.sandboxapp.render.RenderBox;

import java.util.HashMap;

public class SandParticle {

    static float WIDTH_HEIGHT = 0.01f;
    static float WIDTH_WIDTH = 0.01f;

    static float SAND_MASS = 1.f;

    static Vec2f DEFAULT_POS = new Vec2f(0.0f, 0.0f);


    public RenderBox m_renderBox;
    public PhysBox   m_physBox;



    public SandParticle ()
    {
        m_physBox = new PhysBox(DEFAULT_POS, WIDTH_WIDTH, WIDTH_HEIGHT,
                    new Vec2f(0.0f, 0.0f), new Vec2f(0.0f, 0.0f), SAND_MASS);

        m_renderBox = new RenderBox((GeomBox)m_physBox, Color.YELLOW);
    }

    public RenderBox GetRenderBox () {
        return m_renderBox;
    }
}
