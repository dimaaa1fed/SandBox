package com.example.sandboxapp.physics;

import com.example.sandboxapp.game.PhysicEngine;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.math.Vec2d;

public class PhysBox extends GeomBox {
    public static double   INFINITE_MASS = 0;

    public Vec2d m_velocity;
    public Vec2d m_force;

    public double m_imass;

    public enum Type {
        SAND,
        OTHER
    }

    public Type m_type;

    //TODO: change coeff
    public double restitution = 0.2;

    public double staticFriction = 1;
    public double dynamicFriction = 0.3;

    public PhysBox(Vec2d m_min, Vec2d m_max, double m_imass, Type type) {
        super(m_min, m_max);
        this.m_velocity = new Vec2d(0, 0);
        this.m_force = new Vec2d(0, 0);
        this.m_imass = m_imass;
        this.m_type = type;
    }

    public void IntegrateForces (Vec2d gravity, double dt) {
        if (m_imass != 0) {
            m_velocity.x += (m_force.x * m_imass + gravity.x) / 2 * dt;
            m_velocity.y += (m_force.y * m_imass + gravity.y) / 2 * dt;
        }
    }

    public void IntegrateVelocity (Vec2d gravity, double dt) {
        if (m_imass != 0) {
            m_min.x += m_velocity.x * dt;
            m_min.y += m_velocity.y * dt;

            m_max.x += m_velocity.x * dt;
            m_max.y += m_velocity.y * dt;

            IntegrateForces(gravity, dt);
        }
    }

    public void ApplyImpulse (Vec2d impulse) {
        if (m_imass != 0) {
            m_velocity.x += impulse.x;
            m_velocity.y += impulse.y;
        }
    }

    public void ApplyForce (Vec2d force) {
        m_velocity.x += force.x;
        m_velocity.y += force.y;
    }

    public void ClearForces () {
        m_force.x = 0;
        m_force.y = 0;
    }
}
