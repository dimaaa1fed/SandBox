package com.example.sandboxapp.game;

import com.example.sandboxapp.math.Vec2d;

public class LevelDesc {
    public double m_borderLen = 0.9f / Math.sqrt(2) * 2;
    public double m_borderWidth = 0.2;

    public Vec2d m_leftBottomSand = new Vec2d(-0.5, -0.5);
    public Vec2d m_rightTopSand = new Vec2d(0.5, 0.5);


    public double m_percentToWin = 0.5;
    //TODO: Add level objs desc

}
