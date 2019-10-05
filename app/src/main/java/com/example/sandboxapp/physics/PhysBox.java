package com.example.sandboxapp.physics;

import com.example.sandboxapp.math.Vec2f;

public class PhysBox extends GeomBox {
    public Vec2f m_velocity;
    public Vec2f m_acceleration;

    public float m_mass;


    public PhysBox (Vec2f pos, float width, float height, Vec2f velocity, Vec2f acceleration, float mass)
    {
        super(pos, width, height);

        m_velocity = velocity;
        m_acceleration = acceleration;
        m_mass = mass;
    }


}
