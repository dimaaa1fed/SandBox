package com.example.sandboxapp.physics;

import com.example.sandboxapp.math.Vec2d;

public class GeomBox {

    private double m_width;
    private double m_height;

    private Vec2d m_pos;
    
    public GeomBox (Vec2d pos, double width, double height)
    {
        m_pos = pos;
        m_width = width;
        m_height = height;
    }

    public double GetWidth () {
        return m_width;
    }

    public double GetHeight () {
        return m_height;
    }

    public Vec2d GetPos () {
        return m_pos;
    }

}
