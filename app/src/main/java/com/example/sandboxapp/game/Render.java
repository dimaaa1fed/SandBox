package com.example.sandboxapp.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.sandboxapp.MainActivity;
import com.example.sandboxapp.R;
import com.example.sandboxapp.game_objects.Bucket;
import com.example.sandboxapp.game_objects.Sand;
import com.example.sandboxapp.game_objects.StaticRect;
import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.Intersection;

import java.util.ArrayList;

public class Render {

    private Bitmap m_bitmapBack;

    public Render (MainActivity app) {
        m_bitmapBack = BitmapFactory.decodeResource(app.getResources(), R.drawable.background);
    }


    public void Draw (Canvas canvas, GameScene scene, double rotAngle) {
        rotAngle = rotAngle * 180 / Math.PI;

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
        Rect rect = GetBucketRect(canvas, rotAngle, bucket);

        Paint yellowPaint = new Paint();
        yellowPaint.setColor(Color.GRAY);
        yellowPaint.setStyle(Paint.Style.FILL);


        canvas.translate(canvas.getWidth() / 2, canvas.getWidth() / 2);
        //canvas.rotate((float) rotAngle);
        //canvas.rotate((float) rotAngle);

        canvas.drawRect(rect, yellowPaint);

        /*canvas.rotate((float) -rotAngle);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        Vec2d c = bucket.GetCenter().getRotatedBy(rotAngle / 180 * Math.PI);

        canvas.drawCircle((int)(c.x  * canvas.getWidth()), (int)(c.y  * canvas.getWidth()),
                (int)(canvas.getWidth() * bucket.getM_Dist()), paint);
        canvas.rotate((float) rotAngle);*/
        //canvas.rotate((float) rotAngle);
        //canvas.rotate((float) -rotAngle);
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
