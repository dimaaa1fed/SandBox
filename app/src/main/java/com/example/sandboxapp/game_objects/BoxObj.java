package com.example.sandboxapp.game_objects;

import android.graphics.Color;

import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.PhysBox;
import com.example.sandboxapp.render.RenderBox;

public class BoxObj {
    private RenderBox m_renderBox;
    private PhysBox m_physBox;

    public BoxObj (Vec2d min, Vec2d max, double mass, PhysBox.Type type)
    {
        m_physBox = new PhysBox(min, max, mass, type);
        m_renderBox = new RenderBox(m_physBox.m_min, m_physBox.m_max, type == PhysBox.Type.SAND ? 2 : 1);
    }

    public BoxObj (Vec2d center, double w, double h, double mass, PhysBox.Type type)
    {
        Vec2d min = new Vec2d(center.x - w / 2, center.y - h / 2);
        Vec2d max = new Vec2d(center.x + w / 2, center.y + h / 2);

        m_physBox = new PhysBox(min, max, mass, type);
        m_renderBox = new RenderBox(m_physBox.m_min, m_physBox.m_max, type == PhysBox.Type.SAND ? 2 : 1);
    }

    public void SetRenderBox () {
        m_renderBox = new RenderBox(m_physBox.m_min, m_physBox.m_max, m_physBox.m_type == PhysBox.Type.SAND ? 2 : 1);
    }

    public RenderBox GetRenderBox () {
        return m_renderBox;
    }

    public PhysBox   GetPhysBox () {
        return m_physBox;
    }
}
