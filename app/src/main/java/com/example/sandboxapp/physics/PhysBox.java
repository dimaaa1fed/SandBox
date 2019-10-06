package com.example.sandboxapp.physics;

import com.example.sandboxapp.math.Vec2d;

public class PhysBox extends GeomBox {
    public Vec2d m_velocity;
    public Vec2d m_acceleration;

    public double m_mass;


    public PhysBox (Vec2d pos, double width, double height, Vec2d velocity, Vec2d acceleration, double mass)
    {
        super(pos, width, height);

        m_velocity = velocity;
        m_acceleration = acceleration;
        m_mass = mass;
    }


}
