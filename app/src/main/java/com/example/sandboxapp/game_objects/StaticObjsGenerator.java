package com.example.sandboxapp.game_objects;

import com.example.sandboxapp.math.Vec2d;

import java.util.ArrayList;

public class StaticObjsGenerator {

    static public ArrayList<StaticRect> Generate (Vec2d bottomLeft, Vec2d rightTop) {
        int iWidth = (int)((rightTop.x - bottomLeft.x) / StaticRect.WIDTH);
        int iHeight = (int)((rightTop.y - bottomLeft.y) / StaticRect.HEIGHT);


        ArrayList<StaticRect> walls = new ArrayList<>();
        for (int i = 0; i < iWidth; i++) {
            double w = StaticRect.WIDTH;
            double h = StaticRect.HEIGHT;
            double x = bottomLeft.x + w / 2 + i * w;
            double y = bottomLeft.y + h / 2;
            walls.add(new StaticRect(new Vec2d(x - w, y - h), new Vec2d(x + w, y + h)));
        }

        for (int i = 0; i < iWidth; i++) {
            double w = StaticRect.WIDTH;
            double h = StaticRect.HEIGHT;
            double x = bottomLeft.x + w / 2 + i * w;
            double y = rightTop.y - h / 2;
            walls.add(new StaticRect(new Vec2d(x - w, y - h), new Vec2d(x + w, y + h)));
        }


        for (int j = 0; j < iHeight; j++) {
            double w = StaticRect.WIDTH;
            double h = StaticRect.HEIGHT;
            double x = bottomLeft.x + w / 2;
            double y = bottomLeft.y + h / 2 + j * h;
            walls.add(new StaticRect(new Vec2d(x - w, y - h), new Vec2d(x + w, y + h)));
        }

        for (int j = 0; j < iHeight; j++) {
            double w = StaticRect.WIDTH;
            double h = StaticRect.HEIGHT;
            double x = rightTop.x - w / 2;
            double y = bottomLeft.y + h / 2 + j * h;
            walls.add(new StaticRect(new Vec2d(x - w, y - h), new Vec2d(x + w, y + h)));
        }

        return walls;
    }

}
