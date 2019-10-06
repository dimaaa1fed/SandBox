package com.example.sandboxapp.physics;

import com.example.sandboxapp.game_objects.GeomBox;
import com.example.sandboxapp.math.Vec2d;

public class PhysBox extends GeomBox {
    public Vec2d m_velocity;
    public Vec2d m_acceleration;

    public float m_mass;


    public PhysBox(float m_width, float m_height, Vec2d m_pos,
                   Vec2d m_velocity, Vec2d m_acceleration, float m_mass) {
        super(m_width, m_height, m_pos);
        this.m_velocity = m_velocity;
        this.m_acceleration = m_acceleration;
        this.m_mass = m_mass;
    }
}
