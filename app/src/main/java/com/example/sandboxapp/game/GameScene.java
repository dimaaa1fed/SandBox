package com.example.sandboxapp.game;

import android.graphics.Color;

import com.example.sandboxapp.game_objects.Bucket;
import com.example.sandboxapp.game_objects.Sand;
import com.example.sandboxapp.game_objects.SandGenerator;
import com.example.sandboxapp.game_objects.BorderObjsGenerator;
import com.example.sandboxapp.game_objects.StaticRect;
import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.render.RenderBox;

import java.util.ArrayList;

public class GameScene {
    private Sand                  m_sand;
    private ArrayList<StaticRect> m_walls;
    private Bucket                m_bucket;
    private GeomBox               m_gameBox;
    private PhysicEngine          m_physEngine;
    private LevelDesc             m_levelDesc;

    public GameScene (PhysicEngine physicEngine, LevelDesc desc) {
        m_physEngine = physicEngine;
        m_levelDesc = desc;

        Vec2d leftBottomBorder = new Vec2d(-desc.m_borderLen / 2, -desc.m_borderLen / 2);
        Vec2d rightTopBorder = new Vec2d(desc.m_borderLen / 2, desc.m_borderLen / 2);
        m_gameBox = new GeomBox(leftBottomBorder, rightTopBorder);

        m_walls = BorderObjsGenerator.Generate(0.9f / Math.sqrt(2) * 2, 0.1);
        for (int i = 0; i < m_walls.size(); i++) {
            m_physEngine.AddPhysBox( m_walls.get(i).GetPhysBox());
        }

        m_sand = SandGenerator.Generate(desc.m_leftBottomSand, desc.m_rightTopSand, 50);
        for (int i = 0; i < m_sand.Size(); i++) {
            m_sand.At(i).SetRenderBox(new RenderBox(m_sand.At(i).GetPhysBox(), Color.YELLOW));
            m_physEngine.AddPhysBox( m_sand.At(i).GetPhysBox());
        }

        Vec2d y = new Vec2d(0, 1);
        Vec2d x = new Vec2d(1, 0);

        m_bucket = new Bucket(x.getMultiplied(Bucket.W), y.getMultiplied(Bucket.H), new Vec2d(0, 1.3));
    }

    public Sand GetSand () {
        return m_sand;
    }

    public ArrayList<StaticRect> GetWalls () { return m_walls; }

    public Bucket GetBucket () { return m_bucket; }

    public GeomBox GetGameBox() { return m_gameBox; }

    public LevelDesc GetLevelDesc() { return m_levelDesc; }
}
