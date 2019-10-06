package com.example.sandboxapp.game;

import android.graphics.Canvas;

import com.example.sandboxapp.MainActivity;
import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.PhysBox;

public class Engine {
    public  Render         m_render;
    public  PhysicEngine   m_physEngine;
    public  GameScene      m_gameScene;

    public  long           m_prevTime = -1;

    public double   m_rotAngle = 0;
    public Vec2d    m_prevTouch;
    public Vec2d    m_startCoord;


    public Engine (MainActivity app) {
        m_render = new Render(app);
        m_physEngine = new PhysicEngine();
        m_gameScene = new GameScene(m_physEngine);

        m_physEngine.setGame_box(m_gameScene.getGame_box());
    }


    public void Update () {
        long curTime = System.currentTimeMillis();
        if (m_prevTime == -1) {
            m_prevTime = curTime;
        }

        int dt = (int)(curTime - m_prevTime);
        m_prevTime = curTime;
        m_physEngine.Update((double) dt / 1000, m_rotAngle / 180 * Math.PI);
    }

    public void Render (Canvas canva) {
        if (m_startCoord == null) {
            m_startCoord = new Vec2d(canva.getWidth() / 2, canva.getWidth() / 2);
        }

        m_render.Draw(canva, m_gameScene, m_rotAngle);
    }


    public void OnTouchDown (int x, int y) {
        if (m_startCoord == null) {
            return;
        }

        m_prevTouch = new Vec2d(x, y);
        m_prevTouch.add(-m_startCoord.x, -m_startCoord.y);
    }


    public void OnTouchMove (int x, int y) {
        if (m_startCoord == null) {
            return;
        }

        Vec2d curTouch = new Vec2d(x, y);
        curTouch.add(-m_startCoord.x, -m_startCoord.y);

        m_rotAngle += (curTouch.getAngle() - m_prevTouch.getAngle()) * 180 / Math.PI;
        m_prevTouch = curTouch;
    }


    public void OnTouchUp (int x, int y) {
        m_prevTouch = null;
    }
}
