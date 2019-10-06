package com.example.sandboxapp.game_objects;

import com.example.sandboxapp.math.Vec2d;

public class GeomBox {

    private Vec2d m_min;
    private Vec2d m_max;

    public GeomBox(Vec2d min, Vec2d max) {
        this.m_min = min;
        this.m_max = max;
    }

    public Vec2d getMin() {
        return m_min;
    }

    public Vec2d getMax() {
        return m_max;
    }
}
