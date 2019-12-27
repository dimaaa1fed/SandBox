package com.example.sandboxapp.render;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.sandboxapp.MainActivity;
import com.example.sandboxapp.R;
import com.example.sandboxapp.game.GameScene;
import com.example.sandboxapp.game_objects.Bucket;
import com.example.sandboxapp.game_objects.Sand;
import com.example.sandboxapp.game_objects.StaticRect;
import com.example.sandboxapp.math.Vec2d;

import java.util.ArrayList;

public class Render {
    private Bitmap m_bucketTextureSrc;
    private Bitmap m_wallTextureSrc;
    private Paint m_textPaint;
    private Bitmap m_sandTextureSrc;


    private MainActivity m_app;

    public Render () {

    }


    public void Init (MainActivity app) {
        m_app = app;
        m_bucketTextureSrc = BitmapFactory.decodeResource(app.getResources(), R.drawable.bucket_texture);
        m_wallTextureSrc = BitmapFactory.decodeResource(app.getResources(), R.drawable.plywood);
        m_textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        m_textPaint.setFilterBitmap(true);

        m_sandTextureSrc = BitmapFactory.decodeResource(app.getResources(), R.drawable.sand);
    }


    public void Draw (Canvas canvas, GameScene scene, double rotAngle) {
        rotAngle = rotAngle * 180 / Math.PI;

        /* draw game objects */
        // draw box

        // draw bucket
        DrawTextRectScaled(canvas, m_bucketTextureSrc,
                GetBucketRect(canvas, rotAngle, scene.GetBucket()),
                0, new Vec2d(1.15, 1.15));

        // draw sand
        Sand sand = scene.GetSand();
        for (int i = 0; i < sand.Size(); i++) {
            Rect rect = sand.At(i).GetRenderBox().GetRect(canvas);
            DrawTextRect(canvas, m_sandTextureSrc, rect, rotAngle);
        }

        // draw walls
        ArrayList<StaticRect> walls = scene.GetWalls();
        for (int i = 0; i < walls.size(); i++) {
            Rect rect = walls.get(i).GetRenderBox().GetRect(canvas);
            DrawTextRect(canvas, m_wallTextureSrc, rect, rotAngle);
        }
    }

    private void DrawColoredRect(Canvas canvas, Paint paint, Rect dest, double rotAngle) {
        canvas.translate(canvas.getWidth() / 2, canvas.getWidth() / 2);
        canvas.rotate((float) rotAngle);

        canvas.drawRect(dest, paint);

        canvas.rotate((float) -rotAngle);
        canvas.translate(-canvas.getWidth() / 2, -canvas.getWidth() / 2);
    }

    private void DrawTextRect(Canvas canvas, Bitmap rectTextureSrc, Rect dest, double rotAngle)
    {
        DrawTextRectScaled(canvas, rectTextureSrc, dest, rotAngle, new Vec2d(1, 1));
    }

    private void DrawTextRectScaled(Canvas canvas, Bitmap rectTextureSrc,
                                    Rect dest, double rotAngle, Vec2d scale)
    {
        Matrix matrix = new Matrix();
        //matrix.postTranslate(100, 0);
        float dx_scale = (float)scale.x, dy_scale = (float)scale.y;
        float delta_x_move = (float)(dest.right - dest.left) / rectTextureSrc.getWidth();
        float delta_y_move = (float)(dest.top - dest.bottom) / rectTextureSrc.getHeight();

        matrix.postScale(delta_x_move * dx_scale, delta_y_move * dy_scale);
        Bitmap texture = Bitmap.createBitmap(rectTextureSrc, 0, 0,
                rectTextureSrc.getWidth(), rectTextureSrc.getHeight(), matrix, true);

        canvas.translate(canvas.getWidth() / 2, canvas.getWidth() / 2);

        canvas.rotate((float) rotAngle);

        float translate_x = dest.left - delta_x_move * (dx_scale - 1) * rectTextureSrc.getWidth(),
                translate_y = dest.bottom - delta_y_move * (dy_scale - 1) * rectTextureSrc.getHeight();

        canvas.translate(translate_x, translate_y);

        canvas.drawBitmap(texture, 0, 0, m_textPaint);

        canvas.translate(-translate_x, -translate_y);

        canvas.rotate((float) -rotAngle);
        canvas.translate(-canvas.getWidth() / 2, -canvas.getWidth() / 2);
    }

    private Rect GetBucketRect (Canvas canvas, double rotAngle, Bucket bucket) {
        Rect rect = new Rect();

        /*
         * @param left   The X coordinate of the left side of the rectangle
         * @param top    The Y coordinate of the top of the rectangle
         * @param right  The X coordinate of the right side of the rectangle
         * @param bottom The Y coordinate of the bottom of the rectangle
         */
        Vec2d c = bucket.getM_startC();
        Vec2d a = bucket.getM_startA();
        Vec2d b = bucket.getM_startB();



        int left      = (int)((c.x - a.x) * canvas.getWidth() / 2);
        int top       = (int)((c.y + b.y) * canvas.getWidth() / 2);
        int right     = (int)((c.x + a.x)  * canvas.getWidth() / 2);
        int bottom    = (int)((c.y - b.y) * canvas.getWidth() / 2);

        rect.set(left, top, right, bottom);
        return rect;
    }
}
