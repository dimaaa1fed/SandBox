package com.example.sandboxapp.game;

import com.example.sandboxapp.game_objects.Sand;
import com.example.sandboxapp.game_objects.SandGenerator;
import com.example.sandboxapp.game_objects.StaticObjsGenerator;
import com.example.sandboxapp.game_objects.StaticRect;
import com.example.sandboxapp.math.Vec2d;

import java.util.ArrayList;

public class GameScene {
    private Sand                  m_sand;
    private ArrayList<StaticRect> m_walls;

    private PhysicEngine m_physEngine;

    public GameScene (PhysicEngine physicEngine) {
        m_physEngine = physicEngine;

        Vec2d leftBottomWall = new Vec2d((float) (-0.9f / Math.sqrt(2)), (float) (-0.9f/ Math.sqrt(2)));
        Vec2d rightTopWall = new Vec2d((float) (0.9f/ Math.sqrt(2)), (float) (0.9f/ Math.sqrt(2)));

        Vec2d leftBottomSand = new Vec2d((float) (-0.9f / Math.sqrt(2)), (float) (-0.9f/ Math.sqrt(2)));
        leftBottomSand.x += StaticRect.WIDTH;
        leftBottomSand.y += StaticRect.HEIGHT;

        Vec2d rightTopSand = new Vec2d((float) (0.9f/ Math.sqrt(2)), (float) (0.9f/ Math.sqrt(2)));
        rightTopSand.x -= StaticRect.WIDTH;
        rightTopSand.y -= StaticRect.HEIGHT;


        m_walls = StaticObjsGenerator.Generate(leftBottomWall, rightTopWall);
        for (int i = 0; i < m_walls.size(); i++) {
            m_physEngine.AddPhysBox( m_walls.get(i).GetPhysBox().id, m_walls.get(i).GetPhysBox());
        }

        m_sand = SandGenerator.Generate(leftBottomSand, rightTopSand, 50);
        for (int i = 0; i < m_sand.Size(); i++) {
            m_physEngine.AddPhysBox( m_sand.At(i).GetPhysBox().id, m_sand.At(i).GetPhysBox());
        }
    }

    public Sand GetSand () {
        return m_sand;
    }

    public ArrayList<StaticRect> GetWalls () { return m_walls; }

}
