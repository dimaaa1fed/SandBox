package com.example.sandboxapp.render;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.sandboxapp.MainActivity;
import com.example.sandboxapp.R;
import com.example.sandboxapp.game.GameScene;
import com.example.sandboxapp.game_objects.Bucket;
import com.example.sandboxapp.game_objects.Sand;
import com.example.sandboxapp.game_objects.StaticRect;
import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.Intersection;

import java.util.ArrayList;
import java.util.Iterator;

public class Render {

    private MainActivity m_app;
    private Bitmap       m_bucketTextureSrc;
    private Bitmap       m_bucketTexture;

    public Render () {

    }


    public void Init (MainActivity app) {
        m_app = app;
        m_bucketTextureSrc = BitmapFactory.decodeResource(app.getResources(), R.drawable.bucket_texture);
    }


    public void Draw (Canvas canvas, GameScene scene, double rotAngle) {
        rotAngle = rotAngle * 180 / Math.PI;

        /* draw game objects */
        // draw box

        // draw sand
        Sand sand = scene.GetSand();

        for (int i = 0; i < sand.Size(); i++) {
            sand.At(i).GetRenderBox().Draw(canvas, rotAngle);
        }

        // draw walls
        ArrayList<StaticRect> walls = scene.GetWalls();
        for (int i = 0; i < walls.size(); i++) {
            walls.get(i).GetRenderBox().Draw(canvas, rotAngle);
        }

        DrawBucket(canvas, rotAngle, scene.GetBucket());
    }


    public void DrawBucket (Canvas canvas, double rotAngle, Bucket bucket) {
        Matrix matrix = new Matrix();
        Rect dest = GetBucketRect(canvas, rotAngle, bucket);
        //matrix.postTranslate(100, 0);

        matrix.postScale((float)(dest.right - dest.left) / m_bucketTextureSrc.getWidth(),
                (float)(dest.top - dest.bottom) / m_bucketTextureSrc.getHeight());
        //matrix.postScale(0.1f, 0.1f);
        m_bucketTexture = Bitmap.createBitmap(m_bucketTextureSrc, 0, 0,
                m_bucketTextureSrc.getWidth(), m_bucketTextureSrc.getHeight(), matrix, true);

        //Rect dest = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);

        canvas.translate(canvas.getWidth() / 2, canvas.getWidth() / 2);
        canvas.translate(dest.left, dest.bottom);

        canvas.drawBitmap(m_bucketTexture, 0, 0, paint);

        canvas.translate(-dest.left, -dest.bottom);
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
