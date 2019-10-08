package com.example.sandboxapp.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.sandboxapp.MainActivity;
import com.example.sandboxapp.R;
import com.example.sandboxapp.game_objects.Sand;
import com.example.sandboxapp.game_objects.StaticRect;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.Intersection;

import java.util.ArrayList;

public class Render {

    private Bitmap m_bitmapBack;

    public Render (MainActivity app) {
        m_bitmapBack = BitmapFactory.decodeResource(app.getResources(), R.drawable.background);
    }


    public void Draw (Canvas canvas, GameScene scene, double rotAngle) {

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
    }
}
