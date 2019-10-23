package com.example.sandboxapp.physics;

import com.example.sandboxapp.math.Vec2d;

public class Mainfold {
    PhysBox m_a;
    PhysBox m_b;

    double   m_e;
    double   m_sf = 0;
    double   m_df = 0;

    Vec2d    m_normal = new Vec2d(0, 0);
    double   m_penetration = 0;

    public Mainfold (PhysBox a, PhysBox b) {
        m_a = a;
        m_b = b;

        m_e = Math.min(a.restitution, b.restitution);
    }

    public void Init (Vec2d gravity, double epsilon) {
        m_sf = Math.sqrt(m_a.staticFriction * m_b.staticFriction);
        m_df = Math.sqrt(m_a.dynamicFriction * m_b.dynamicFriction);

        double rx = m_b.m_velocity.x - m_a.m_velocity.x;
        double ry = m_b.m_velocity.y - m_a.m_velocity.y;

        if ((rx * rx + ry * ry) < (gravity.x * gravity.x + gravity.y * gravity.y) + epsilon) {
            m_e = 0;
        }
    }

    public boolean Solve () {
        double nx = (m_a.getCenter().x - m_b.getCenter().x) / 2;
        double ny = (m_a.getCenter().y - m_b.getCenter().y) / 2;

        double aex = m_a.getWidth() / 2;
        double bex = m_b.getWidth() / 2;

        double xoverlap = aex + bex - Math.abs(nx);
        if (xoverlap > 0) {
            double aey = m_a.getHeight() / 2;
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
    }

    public void Resolve (double epsilon) {
        PhysBox a = m_a;
        PhysBox b = m_b;

        double rx = b.m_velocity.x - a.m_velocity.x;
        double ry = b.m_velocity.y - a.m_velocity.y;

        double velAlongNormal = rx * m_normal.x + ry * m_normal.y;

        if (velAlongNormal > 0) {
            return;
        } else {
            double j = -(1.0 + m_e) * velAlongNormal;
            j /= (a.m_imass + b.m_imass);

            a.ApplyImpulse(new Vec2d(-j * m_normal.x, -j * m_normal.y));
            b.ApplyImpulse(new Vec2d(j * m_normal.x, j * m_normal.y));

            double tx = rx - (m_normal.x * velAlongNormal);
            double ty = ry - (m_normal.y * velAlongNormal);
            double tl = Math.sqrt(tx * tx + ty * ty);

            if (tl > epsilon) {
                tx /= tl;
                ty /= tl;
            }

            double jt = -(rx * tx + ry * ty);
            jt /= (a.m_imass + b.m_imass);

            if (Math.abs(jt) < epsilon) {
                return;
            }

            if (Math.abs(jt) < j * m_sf) {
                tx = tx * jt;
                ty = ty * jt;
            } else {
                tx = tx * -j * m_df;
                ty = ty * -j * m_df;
            }

            a.ApplyImpulse(new Vec2d(-tx, -ty));
            b.ApplyImpulse(new Vec2d(tx, ty));
        }
    }

    public void PositionCorrection() {
        PhysBox a = m_a;
        PhysBox b = m_b;

        double percent = 0.5;
        double slop = 0.005;
        double m = Math.max(this.m_penetration - slop, 0.0) / (a.m_imass + b.m_imass);

        double cx = m * m_normal.x * percent;
        double cy = m * m_normal.y * percent;

        a.m_min.x -= cx * a.m_imass;
        a.m_max.x -= cx * a.m_imass;

        a.m_min.y -= cy * a.m_imass;
        a.m_max.y -= cy * a.m_imass;

        b.m_min.x += cx * b.m_imass;
        b.m_max.x += cx * b.m_imass;

        b.m_min.y += cy * b.m_imass;
        b.m_max.y += cy * b.m_imass;
    }

}
