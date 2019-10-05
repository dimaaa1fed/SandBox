package com.example.sandboxapp.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.sandboxapp.MainActivity;
import com.example.sandboxapp.R;
import com.example.sandboxapp.game_objects.Sand;
import com.example.sandboxapp.math.Vec2f;

public class Render {

    public Bitmap m_bitmapBack;

    public Render (MainActivity app) {
        m_bitmapBack = BitmapFactory.decodeResource(app.getResources(), R.drawable.background);
    }


    public void Draw (Canvas canvas, GameScene scene) {

        // draw background
        Rect dest = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(m_bitmapBack, null, dest, paint);

        /* draw game objects */
        // draw box

        // draw sand
        Sand sand = scene.GetSand();
        for (int i = 0; i < sand.Size(); i++) {
            sand.At(i).GetRenderBox().Draw(canvas);
        }
    }

    static Vec2f ToGlobal (Vec2f vec, Canvas canvas) {
        return new Vec2f(vec.x * canvas.getWidth(), vec.y * canvas.getHeight());
    }

}
