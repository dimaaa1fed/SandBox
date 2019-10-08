package com.example.sandboxapp.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.sandboxapp.physics.GeomBox;

public class RenderBox {
    private GeomBox m_geom;
    private int     m_color;

    public RenderBox(GeomBox geom, int color)
    {
        m_geom = geom;
        m_color = color;
    }

    public void Draw (Canvas canvas, double rotAngle) {
        Rect rect = GetRect(canvas, rotAngle);

        Paint yellowPaint = new Paint();
        yellowPaint.setColor(m_color);
        yellowPaint.setStyle(Paint.Style.FILL);


        canvas.translate(canvas.getWidth() / 2, canvas.getWidth() / 2);
        canvas.rotate((float) rotAngle);

        canvas.drawRect(rect, yellowPaint);

        canvas.rotate((float) -rotAngle);
        canvas.translate(-canvas.getWidth() / 2, -canvas.getWidth() / 2);
    }


    private Rect GetRect(Canvas canvas, double rotAngle) {
        Rect rect = new Rect();

        /*
         * @param left   The X coordinate of the left side of the rectangle
         * @param top    The Y coordinate of the top of the rectangle
         * @param right  The X coordinate of the right side of the rectangle
         * @param bottom The Y coordinate of the bottom of the rectangle
         */

        int left      = /*canvas.getWidth() / 2 + */(int)(m_geom.getLeftBottom().x * canvas.getWidth() / 2);
        int top       = /*canvas.getWidth() / 2 + */(int)(m_geom.getLeftUp().y * canvas.getWidth() / 2);
        int right     = /*canvas.getWidth() / 2 + */(int)(m_geom.getRightBottom().x  * canvas.getWidth() / 2);
        int bottom    = /*canvas.getWidth() / 2 + */(int)(m_geom.getRightBottom().y * canvas.getWidth() / 2);

        rect.set(left, top, right, bottom);
        return rect;
    }
}
