package com.example.sandboxapp.render;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;

public class RenderBox {
    private GeomBox m_geom;
    private int m_type = 1;

    public RenderBox(Vec2d min, Vec2d max, int type)
    {
        m_geom = new GeomBox(min, max);
        m_type = type;
    }

    public Rect GetRect(Canvas canvas) {
        Rect rect = new Rect();
        GeomBox geom = m_geom;
        if (m_type != 1)
        {
            geom = new GeomBox(m_geom.m_min.getSubtracted(new Vec2d(m_geom.m_max.x, m_geom.m_max.x)),
                               m_geom.m_min.getAdded(new Vec2d(m_geom.m_max.x, m_geom.m_max.x)));
        }

        /*
         * @param left   The X coordinate of the left side of the rectangle
         * @param top    The Y coordinate of the top of the rectangle
         * @param right  The X coordinate of the right side of the rectangle
         * @param bottom The Y coordinate of the bottom of the rectangle
         */

        int left      = /*canvas.getWidth() / 2 + */(int)(geom.getLeftBottom().x * canvas.getWidth() / 2);
        int top       = /*canvas.getWidth() / 2 + */(int)(geom.getLeftUp().y * canvas.getWidth() / 2);
        int right     = /*canvas.getWidth() / 2 + */(int)(geom.getRightBottom().x  * canvas.getWidth() / 2);
        int bottom    = /*canvas.getWidth() / 2 + */(int)(geom.getRightBottom().y * canvas.getWidth() / 2);

        rect.set(left, top, right, bottom);
        return rect;
    }
}
