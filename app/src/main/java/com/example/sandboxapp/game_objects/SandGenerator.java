package com.example.sandboxapp.game_objects;

import com.example.sandboxapp.math.Vec2d;


public class SandGenerator {

    //TODO: rewrite this
    static public Sand Generate(Vec2d bottomLeft, Vec2d rightTop, int size) {
        int iWidth = (int)((rightTop.x - bottomLeft.x) / SandParticle.WIDTH);
        int iHeight = (int)((rightTop.y - bottomLeft.y) / SandParticle.HEIGHT);

        int totalCapacitySize = iWidth * iHeight;

        if (totalCapacitySize < size) {
            //TODO: ???
            //return new Sand();
        }

        Sand sand = new Sand(size);
        for (int i = 0; i < size; i++) {
            double w = SandParticle.WIDTH;
            double h = SandParticle.HEIGHT;
            double x = bottomLeft.x + w / 2 + i % iWidth * w;
            double y = bottomLeft.y + h / 2 + i / iHeight * h;
            sand.Push(new SandParticle(new Vec2d(x , y)));
        }
        return sand;
    }

}
