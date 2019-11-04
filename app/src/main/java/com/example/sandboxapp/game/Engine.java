package com.example.sandboxapp.game;

import android.graphics.Canvas;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.sandboxapp.GameView;
import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.render.Render;

import java.util.concurrent.locks.ReentrantLock;

public class Engine {
    public Render m_render;
    public  PhysicEngine   m_physEngine;
    public  GameScene      m_gameScene;
    public  LogicEngine    m_logicEngine;

    public  long           m_prevTime = -1;

    public double   m_rotAngle = 0, m_deltaAngle = 0;
    public Vec2d    m_prevTouch;
    public Vec2d    m_startCoord;

    public ProgressBar m_progessBar;

    private ReentrantLock m_lock;

    public Engine () {
        m_render = new Render();
        m_physEngine = new PhysicEngine();
        m_gameScene = new GameScene(m_physEngine, new LevelDesc());
        m_logicEngine = new LogicEngine(m_gameScene);
        m_physEngine.SetGameBox(m_gameScene.GetGameBox());
    }

    public void Pause()
    {
        m_prevTime = -1;
    }

    public void Init(ProgressBar bar, ReentrantLock lock)
    {
        m_progessBar = bar;
        m_logicEngine.Init(bar);
        m_lock = lock;
    }

    public void Reset() {
        while (!m_lock.tryLock())
            ;
        try {
            m_rotAngle = 0;
            m_deltaAngle = 0;
            m_render = new Render();
            m_physEngine = new PhysicEngine();
            m_gameScene = new GameScene(m_physEngine, new LevelDesc());
            m_logicEngine = new LogicEngine(m_gameScene);
            m_physEngine.SetGameBox(m_gameScene.GetGameBox());
            m_logicEngine.Init(m_progessBar);
        } finally {
            m_lock.unlock();
        }
    }

    static long t = 0;
    public void Update () {
        long curTime = System.currentTimeMillis();
        if (m_prevTime == -1) {
            m_prevTime = curTime;
        }

        int dt = (int)(curTime - m_prevTime);
        m_prevTime = curTime;

        //Log.d("profile/phys", String.format("frame time = %f", (float)(System.nanoTime() - t) / 1000000000));
        t = System.nanoTime();
        m_physEngine.Update((double) dt / 1000, m_rotAngle);
        //Log.d("profile/phys", String.format("update time = %f", (float)(System.nanoTime() - t) /  1000000000));
        t = System.nanoTime();

        //if (m_deltaAngle != 0)
        //    Log.d("angle", String.format("%f", m_deltaAngle));

        m_logicEngine.Update(m_deltaAngle, m_rotAngle);
        m_deltaAngle = 0;
    }

    public void Render (Canvas canva) {
        if (m_startCoord == null) {
            m_startCoord = new Vec2d(canva.getWidth() / 2, canva.getWidth() / 2);
        }

        //Log.d("angle/render", String.format("%f", m_rotAngle));
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

        m_deltaAngle = (curTouch.getAngle() - m_prevTouch.getAngle());
        m_rotAngle += m_deltaAngle;
        m_prevTouch = curTouch;
    }


    public void OnTouchUp (int x, int y) {
        m_prevTouch = null;
        m_deltaAngle = 0;
    }
}
