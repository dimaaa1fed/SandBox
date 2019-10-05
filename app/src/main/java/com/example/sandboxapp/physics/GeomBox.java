package com.example.sandboxapp.physics;

import com.example.sandboxapp.math.Vec2f;

public class GeomBox {

    private float m_width;
    private float m_height;

    private Vec2f m_pos;

    public GeomBox (Vec2f pos, float width, float height)
    {
        m_pos = pos;
        m_width = width;
        m_height = height;
    }

    public float GetWidth () {
        return m_width;
    }

    public float GetHeight () {
        return m_height;
    }

    public Vec2f GetPos () {
        return m_pos;
    }

}
