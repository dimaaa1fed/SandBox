package com.example.sandboxapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.sandboxapp.game.GameScene;
import com.example.sandboxapp.game.Render;

//****************************************************************
//class RefreshHandler
//****************************************************************
class RefreshHandler extends Handler
{
    ViewGame	m_viewGame;

    public RefreshHandler(ViewGame v)
    {
        m_viewGame = v;
    }

    public void handleMessage(Message msg)
    {
        m_viewGame.update();
        m_viewGame.invalidate();
    }

    public void sleep(long delayMillis)
    {
        this.removeMessages(0);
        sendMessageDelayed(obtainMessage(0), delayMillis);
    }
};


public class ViewGame extends View {

    private MainActivity     m_app;
    private RefreshHandler   m_refresh;
    private boolean			 m_active   = false;
    private Paint            m_paint    = new Paint();

    // game moduls
    public  Render    m_render;
    public  GameScene m_gameScene = new GameScene();

    private static final int UPDATE_TIME_MS = 30;

    public ViewGame(MainActivity app)
    {
        super(app);
        m_app = app;
        m_render = new Render(app);
        m_refresh = new RefreshHandler(this);
        setOnTouchListener(app);

    }

    public void update()
    {
        if (!m_active)
            return;
        // send next update to game
        if (m_active)
            m_refresh.sleep(UPDATE_TIME_MS);

    }

    public void start()
    {
        m_active 	= true;
        m_refresh.sleep(UPDATE_TIME_MS);
    }

    public boolean onTouch(int x, int y, int evtType)
    {

        return true;
    }

    public void onDestroy()
    {
    }

    public void onPause()
    {
    }

    public void onDraw(Canvas canvas)
    {
        m_render.Draw(canvas, m_gameScene);
    }

}
