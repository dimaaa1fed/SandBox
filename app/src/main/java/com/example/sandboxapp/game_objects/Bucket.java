package com.example.sandboxapp.game_objects;

import com.example.sandboxapp.math.Vec2d;

import static java.lang.Math.min;

public class Bucket {
    static public double W = 0.15;
    static public double H = 0.2;

    private Vec2d m_a, m_startA;
    private Vec2d m_b, m_startB;
    private Vec2d m_center, m_startC;
    private double m_Dist;

    public Bucket (Vec2d a, Vec2d b, Vec2d center) {
        m_a = a.clone(); //x
        m_b = b.clone();  //y
        m_center = center.clone();

        m_startA = a.clone();
        m_startB = b.clone();
        m_startC = center.clone();

        m_Dist = min(a.getLength(), b.getLength());
    }

    public double getM_Dist() {
        return m_Dist;
    }

    public void RotateBy (double rotAngle)
    {
        m_center = m_center.getRotatedBy(rotAngle);
        m_a = m_a.getRotatedBy(rotAngle);
        m_b = m_b.getRotatedBy(rotAngle);
    }

    public Vec2d GetA () {
        return m_a;
    }

    public Vec2d GetB () {
        return m_b;
    }

    public Vec2d GetCenter () {
        return m_center;
    }

    public Vec2d getM_a() {
        return m_a;
    }

    public Vec2d getM_startA() {
        return m_startA;
    }

    public Vec2d getM_startB() {
        return m_startB;
    }

    public Vec2d getM_startC() {
        return m_startC;
    }

    public Vec2d getM_b() {
        return m_b;
    }
}
