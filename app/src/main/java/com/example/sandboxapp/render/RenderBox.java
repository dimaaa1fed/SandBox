package com.example.sandboxapp.render;

import android.graphics.Canvas;
import android.graphics.Color;
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

    public void Draw (Canvas canvas) {


        Rect rect = GetRect(canvas);

        Paint yellowPaint = new Paint();
        yellowPaint.setColor(m_color);
        yellowPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, yellowPaint);
    }


    private Rect GetRect(Canvas canvas) {
        Rect rect = new Rect();

        int left      = (int)((m_geom.GetPos().x - m_geom.GetWidth() / 2)  * canvas.getWidth());
        int top       = (int)((m_geom.GetPos().x + m_geom.GetHeight() / 2) * canvas.getHeight());
        int right     = (int)((m_geom.GetPos().x + m_geom.GetWidth() / 2)  * canvas.getWidth());
        int bottom    = (int)((m_geom.GetPos().x - m_geom.GetHeight() / 2) * canvas.getHeight());

        rect.set(left, top, right, bottom);
        return rect;
    }
}
