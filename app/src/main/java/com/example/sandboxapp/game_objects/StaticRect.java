package com.example.sandboxapp.game_objects;

import android.graphics.Color;

import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.PhysBox;
import com.example.sandboxapp.render.RenderBox;


public class StaticRect {

    public static double HEIGHT = 0.04;
    public static double WIDTH = 0.04;

    public static double STATIC_MASS = Double.POSITIVE_INFINITY;

    private RenderBox m_renderBox;
    private PhysBox   m_physBox;


    public StaticRect (Vec2d m_min, Vec2d m_max)
    {
        m_physBox = new PhysBox(m_min, m_max, STATIC_MASS);
        m_renderBox = new RenderBox((GeomBox)m_physBox, Color.BLACK);
    }

    public RenderBox GetRenderBox () {
        return m_renderBox;
    }
}
