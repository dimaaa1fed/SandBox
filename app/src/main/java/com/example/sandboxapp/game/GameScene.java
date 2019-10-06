package com.example.sandboxapp.game;

import com.example.sandboxapp.game_objects.Sand;
import com.example.sandboxapp.game_objects.SandGenerator;
import com.example.sandboxapp.game_objects.SandParticle;
import com.example.sandboxapp.game_objects.StaticObjsGenerator;
import com.example.sandboxapp.game_objects.StaticRect;
import com.example.sandboxapp.math.Vec2d;

import java.util.ArrayList;

public class GameScene {
    private Sand                  m_sand;
    private ArrayList<StaticRect> m_walls;

    public GameScene () {
        // TODO: Calculate
        Vec2d leftBottomWall = new Vec2d((float) (-0.9f / Math.sqrt(2)), (float) (-0.9f/ Math.sqrt(2)));
        Vec2d rightTopWall = new Vec2d((float) (0.9f/ Math.sqrt(2)), (float) (0.9f/ Math.sqrt(2)));

        Vec2d leftBottomSand = new Vec2d((float) (-0.9f / Math.sqrt(2)), (float) (-0.9f/ Math.sqrt(2)));
        leftBottomSand.x += StaticRect.WIDTH;
        leftBottomSand.y += StaticRect.HEIGHT;

        Vec2d rightTopSand = new Vec2d((float) (0.9f/ Math.sqrt(2)), (float) (0.9f/ Math.sqrt(2)));
        rightTopSand.x -= StaticRect.WIDTH;
        rightTopSand.y -= StaticRect.HEIGHT;


        m_walls = StaticObjsGenerator.Generate(leftBottomWall, rightTopWall);
        m_sand = SandGenerator.Generate(leftBottomSand, rightTopSand, 100);
    }

    public Sand GetSand () {
        return m_sand;
    }

    public ArrayList<StaticRect> GetWalls () { return m_walls; }

}
