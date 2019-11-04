package com.example.sandboxapp.game;

import com.example.sandboxapp.math.Vec2d;

public class LevelDesc {
    public Vec2d m_leftBottomSand;
    public Vec2d m_rightTopSand;

    public double m_wallLen;
    public double m_percentToWin;
    public String m_field;
    public int    m_size;

    LevelDesc(Vec2d leftBottomSand,
              Vec2d rightTopSand,
              double percentToWin,
              double wallLen,
              String field,
              int size)
    {
        m_leftBottomSand = leftBottomSand;
        m_rightTopSand = rightTopSand;
        m_percentToWin = percentToWin;
        m_field = field;
        m_size = size;
        m_wallLen = wallLen;
    }
}
