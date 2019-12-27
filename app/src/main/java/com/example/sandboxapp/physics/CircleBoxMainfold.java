package com.example.sandboxapp.physics;

import com.example.sandboxapp.math.Vec2d;

import static java.util.Collections.swap;

public class CircleBoxMainfold extends Mainfold {
    private boolean m_revers = false;

    public CircleBoxMainfold (PhysBox a, PhysBox b) {
        super(a, b);

        if (!(a.m_type == PhysBox.Type.SAND || b.m_type == PhysBox.Type.SAND))
                System.exit(-1);
        if (a.m_type != PhysBox.Type.SAND) {
            m_a = b;
            m_b = a;
            m_revers = true;
        }
    }

    public boolean Solve () {
        if (m_a.m_type == PhysBox.Type.SAND && m_b.m_type == PhysBox.Type.SAND) {
            Vec2d a_c = m_a.getMin(), b_c = m_b.getMin();
            double a_r = m_a.getMax().x, b_r = m_b.getMax().x,
                   dist = a_c.distance(b_c);
            if (dist > a_r + b_r)
                return false;

            m_normal = b_c.getSubtracted(a_c).getNormalized();
            if (m_revers)
                m_normal = m_normal.getMultiplied(-1);
            m_penetration = a_r + b_r - dist;

            return true;
        }
        else {
            double nx = (m_a.getCCenter().x - m_b.getCenter().x);
            double ny = (m_a.getCCenter().y - m_b.getCenter().y);

            double aex = m_a.getMax().x;
            double bex = m_b.getWidth() / 2;

            double xoverlap = aex + bex - Math.abs(nx);
            if (xoverlap > 0) {
                double aey = m_a.getMax().x;
                double bey = m_b.getHeight() / 2;

                double yoverlap = aey + bey - Math.abs(ny);

                if (yoverlap > 0) {
                    if (xoverlap < yoverlap) {
                        m_normal.x = nx < 0 ? 1 : -1;
                        m_normal.y = 0;
                        m_penetration = xoverlap;
                        return true;
                    } else {
                        m_normal.x = 0;
                        m_normal.y = ny < 0 ? 1 : -1;
                        m_penetration = yoverlap;
                        return true;
                    }
                }
            }
            return false;
            /*Vec2d a_c = m_a.getMin();
            double a_r = m_a.getMax().x;
            Vec2d b_min = m_b.getMin(), b_max = m_b.getMax();
            Vec2d left_up = m_b.getLeftUp(), right_up = m_b.getRightUp(),
                  right_b = m_b.getRightBottom(), left_b = m_b.getLeftBottom();

            if (a_c.x <= b_min.x && a_c.y >= b_max.y) {
               if (left_up.distance(a_c) > a_r)
                   return false;
                m_normal = new Vec2d(-1, 1).getNormalized();
                m_penetration = a_r - left_up.distance(a_c);
            }
            else if (a_c.x >= b_min.x && a_c.x <= b_max.x && a_c.y >= b_max.y) {
                if (a_c.y - b_max.y > a_r)
                    return false;
                m_normal = new Vec2d(0, 1).getNormalized();
                m_penetration = a_r - (a_c.y - b_max.y);
            }
            else if (a_c.x >= b_min.x && a_c.x >= b_max.x && a_c.y >= b_max.y) {
                if (right_up.distance(a_c) > a_r)
                    return false;
                m_normal = new Vec2d(1, 1).getNormalized();
                m_penetration = a_r - right_up.distance(a_c);
            }
            else if (a_c.y >= b_min.y && a_c.x >= b_max.x && a_c.y <= b_max.y) {
                if (a_c.x - b_max.x > a_r)
                    return false;
                m_normal = new Vec2d(1, 0).getNormalized();
                m_penetration = a_r - (a_c.x - b_max.x);
            }
            else if (a_c.x >= b_max.x && a_c.y <= b_min.y) {
                if (right_b.distance(a_c) > a_r)
                    return false;
                m_normal = new Vec2d(1, -1).getNormalized();
                m_penetration = a_r - right_b.distance(a_c);
            }
            else if (a_c.x >= b_min.x && a_c.x <= b_max.x && a_c.y <= b_min.y) {
                if (b_min.y - a_c.y > a_r)
                    return false;
                m_normal = new Vec2d(0, -1).getNormalized();
                m_penetration = a_r - (b_min.y - a_c.y);
            }
            else if (a_c.x <= b_min.x && a_c.y <= b_min.y) {
                if (left_b.distance(a_c) > a_r)
                    return false;
                m_normal = new Vec2d(-1, -1).getNormalized();
                m_penetration = a_r - left_b.distance(a_c);
            }
            else if (a_c.y >= b_min.y && a_c.x <= b_min.x && a_c.y <= b_max.y) {
                if (b_min.x - a_c.x > a_r)
                    return false;
                m_normal = new Vec2d(-1, 0).getNormalized();
                m_penetration = a_r - (b_min.x - a_c.x);
            }
            else if (a_c.x >= b_min.x && a_c.x <= b_max.x &&
                     a_c.y >= b_min.y && a_c.y <= b_max.y)
            {
                m_normal = m_a.m_velocity.getMultiplied(-1).getNormalized();
                m_penetration = a_r;
            }
            else {
                System.exit(-1);
            }
            m_normal.multiply(-1);
            return true;*/
        }
    }


}
