package com.example.sandboxapp.game;

import com.example.sandboxapp.game_objects.Sand;
import com.example.sandboxapp.game_objects.SandGenerator;
import com.example.sandboxapp.math.Vec2f;

public class GameScene {
    private Sand m_sand;

    public GameScene () {
        m_sand = SandGenerator.Generate(new Vec2f(-0.0f, -0.0f), new Vec2f(0.5f, 0.5f), 1);
    }

    public Sand GetSand () {
        return m_sand;
    }

}
