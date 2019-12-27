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
        Vec2d p1 = b.GetCenter().getAdded(b.GetA()).getAdded(b.GetB());
        Vec2d p2 = b.GetCenter().getAdded(b.GetA().getDivided(-1)).getAdded(b.GetB());
        Vec2d p3 = b.GetCenter().getAdded(b.GetA().getDivided(-1)).getAdded(b.GetB().getDivided(-1));
        Vec2d p4 = b.GetCenter().getAdded(b.GetA()).getAdded(b.GetB().getDivided(-1));

        Vec2d side1 = p2.getSubtracted(p1);
        Vec2d side2 = p3.getSubtracted(p2);
        Vec2d side3 = p4.getSubtracted(p3);
        Vec2d side4 = p1.getSubtracted(p4);

        Vec2d from1 = a.getCCenter().getSubtracted(p1);
        Vec2d from2 = a.getCCenter().getSubtracted(p2);
        Vec2d from3 = a.getCCenter().getSubtracted(p3);
        Vec2d from4 = a.getCCenter().getSubtracted(p4);

        if (from1.dot(side1) >= 0 && from2.dot(side2) >= 0 && from3.dot(side3) >= 0 && from4.dot(side4) >= 0)
            return true;
        return false;
    }

    public static boolean GeomBoxOutOfWorld(GeomBox a, Bucket b)
    {
        double length = a.getCCenter().getLength();
        return length > b.GetCenter().getLength() * 2.28;
    }
}
