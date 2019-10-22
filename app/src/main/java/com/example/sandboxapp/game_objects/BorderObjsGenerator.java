package com.example.sandboxapp.game_objects;

import com.example.sandboxapp.math.Vec2d;

import java.util.ArrayList;

public class BorderObjsGenerator {

    static public ArrayList<StaticRect> Generate (double wallLen, double wallWidth) {

        ArrayList<StaticRect> walls = new ArrayList<>();
//-0.9f / Math.sqrt(2)


        Vec2d c1 = new Vec2d(0, wallLen / 2 - wallWidth / 2);
        Vec2d c2 = new Vec2d(wallLen / 2 - wallWidth / 2, 0);
        Vec2d c3 = new Vec2d(0, -wallLen / 2 + wallWidth / 2);
        Vec2d c4 = new Vec2d(-wallLen / 2 + wallWidth / 2, 0);

        /*   wallLen
        *   ::::c1:::
        *   ::     ::
            c4     c2
        *   ::     ::
        *   ::::c3:::
        *           wallWidth
        * */
        walls.add(new StaticRect(c1, wallLen, wallWidth));
        walls.add(new StaticRect(c2, wallWidth, wallLen));
        walls.add(new StaticRect(c3, wallLen * 0.8, wallWidth));
        walls.add(new StaticRect(c4, wallWidth, wallLen));

        return walls;
    }

}
