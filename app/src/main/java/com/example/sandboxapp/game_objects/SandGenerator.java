package com.example.sandboxapp.game_objects;

import com.example.sandboxapp.V2d;

public class SandGenerator {

    public Sand Generate(V2d bottomLeft, V2d rightTop, int size) {

        int totalCapacitySize = (int)((bottomLeft.x - rightTop.x) / SandParticle.WIDTH_WIDTH) *
                (int)((rightTop.y - bottomLeft.y) / SandParticle.WIDTH_HEIGHT);

        if (totalCapacitySize < size) {
            //TODO: ???
            //return new Sand();
        }

        Sand sand = new Sand(size);



        return sand;

    }


}
