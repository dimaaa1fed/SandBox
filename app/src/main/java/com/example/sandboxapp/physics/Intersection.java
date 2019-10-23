package com.example.sandboxapp.physics;

import com.example.sandboxapp.game_objects.Bucket;
import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;

public class Intersection {

    public static boolean GeomBoxVsGeomBox(GeomBox a, GeomBox b) {
        Vec2d amax = a.getMax(), bmax = b.getMax(),
              amin = a.getMin(), bmin = b.getMin();

        // https://gamedevelopment.tutsplus.com/tutorials/collision-detection-using-the-separating-axis-theorem--gamedev-169
        return !((amax.x < bmin.x || amin.x > bmax.x) || (amax.y < bmin.y || amin.y > bmax.y));
    }

    public static boolean GeomBoxVsBucket(GeomBox a, Bucket b)
    {
        double length = a.getCenter().getAdded(b.GetCenter().getMultiplied(-1)).getLength();
        return length <= b.getM_Dist();
    }

    public static boolean GeomBoxOutOfWorld(GeomBox a, Bucket b)
    {
        double length = a.getCenter().getLength();
        return length > b.GetCenter().getLength();
    }
}
