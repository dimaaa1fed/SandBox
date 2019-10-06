package com.example.sandboxapp.physics;

import com.example.sandboxapp.math.Vec2d;

public class GeomBox {

    protected Vec2d m_min;
    protected Vec2d m_max;

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

    public Vec2d getCenter () {
        return new Vec2d((m_min.x + m_max.x) / 2, (m_min.y + m_max.y) / 2);
    }

    public Vec2d getLeftBottom () {
        return m_min.clone();
    }

    public Vec2d getLeftUp () {
        return new Vec2d(m_min.x, m_max.y);
    }

    public Vec2d getRightUp () {
        return new Vec2d(m_max.x, m_max.y);
    }

    public Vec2d getRightBottom () {
        return new Vec2d(m_max.x, m_min.y);
    }

    public double getWidth () { return m_max.x - m_min.x; }

    public double getHeight () { return m_max.y - m_min.y; }
}
