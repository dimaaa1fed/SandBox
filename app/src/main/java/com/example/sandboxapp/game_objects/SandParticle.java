package com.example.sandboxapp.game_objects;

import android.graphics.Color;

import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.PhysBox;
import com.example.sandboxapp.render.RenderBox;


public class SandParticle {
    public static double HEIGHT = 0.02;
    public static double WIDTH = 0.02;

    public static double SAND_MASS = 1;

    private RenderBox m_renderBox;
    private PhysBox   m_physBox;


    public SandParticle (Vec2d m_min, Vec2d m_max)
    {
        m_physBox = new PhysBox(m_min, m_max, SAND_MASS, PhysBox.Type.SAND);
        m_renderBox = new RenderBox((GeomBox)m_physBox, Color.YELLOW);
    }

    public RenderBox GetRenderBox () {
        return m_renderBox;
    }
    public PhysBox   GetPhysBox () {
        return m_physBox;
    }
}
