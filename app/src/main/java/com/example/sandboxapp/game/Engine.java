package com.example.sandboxapp.game;

import android.graphics.Canvas;

import com.example.sandboxapp.MainActivity;
import com.example.sandboxapp.V2d;
import com.example.sandboxapp.math.Vec2d;

public class Engine {
    public  Render    m_render;
    public  GameScene m_gameScene = new GameScene();


    public double m_rotAngle = 0;
    public V2d    m_prevTouch = new V2d(-1, -1);

    public Engine (MainActivity app) {
        m_render = new Render(app);
    }

    public void Render (Canvas canva) {
        m_render.Draw(canva, m_gameScene, m_rotAngle);
    }

    public void OnTouchDown (int x, int y) {
        m_prevTouch = new V2d(x, y);
    }


    public void OnTouchMove (int x, int y) {
        m_rotAngle += 0.1;
    }


    public void OnTouchUp (int x, int y) {
        m_prevTouch = new V2d(-1, -1);
    }
}
