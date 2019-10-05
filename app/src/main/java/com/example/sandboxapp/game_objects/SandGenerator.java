package com.example.sandboxapp.game_objects;

import com.example.sandboxapp.math.Vec2f;


public class SandGenerator {

    //TODO: rewrite this
    static public Sand Generate(Vec2f bottomLeft, Vec2f rightTop, int size) {
        int iWidth = (int)((rightTop.x - bottomLeft.x) / SandParticle.WIDTH_WIDTH);
        int iHeight = (int)((rightTop.y - bottomLeft.y) / SandParticle.WIDTH_HEIGHT);

        int totalCapacitySize = iWidth * iHeight;

        if (totalCapacitySize < size) {
            //TODO: ???
            //return new Sand();
        }

        Sand sand = new Sand(size);
        for (int i = 0; i < size; i++) {
            float w = SandParticle.WIDTH_WIDTH;
            float h = SandParticle.WIDTH_HEIGHT;
            float x = bottomLeft.x + w / 2 + i % iWidth * w;
            float y = bottomLeft.y + h / 2 + i / iHeight * h;
            sand.Push(new SandParticle(new Vec2f(x, y)));
        }
        return sand;
    }

}
