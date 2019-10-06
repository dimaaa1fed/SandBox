package com.example.sandboxapp.physics;

import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.math.Vec2d;

public class PhysBox extends GeomBox {
    public Vec2d m_velocity;
    public Vec2d m_acceleration;

    public double m_mass;


    public PhysBox(Vec2d m_min, Vec2d m_max,
                   Vec2d m_velocity, Vec2d m_acceleration, float m_mass) {
        super(m_min, m_max);
        this.m_velocity = m_velocity;
        this.m_acceleration = m_acceleration;
        this.m_mass = m_mass;
    }


    public PhysBox(Vec2d m_min, Vec2d m_max, double m_mass) {
        super(m_min, m_max);
        this.m_velocity = new Vec2d(0, 0);
        this.m_acceleration = new Vec2d(0, 0);;
        this.m_mass = m_mass;
    }
}
