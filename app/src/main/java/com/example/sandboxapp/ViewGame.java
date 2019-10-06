package com.example.sandboxapp;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.view.View;

import com.example.sandboxapp.game.Engine;

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

    Engine m_engine;

    private static final int UPDATE_TIME_MS = 30;

    public ViewGame(MainActivity app)
    {
        super(app);
        m_app = app;
        m_engine = new Engine(app);
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
        switch (evtType) {
            case AppIntro.TOUCH_DOWN:
                m_engine.OnTouchDown(x, y);
                break;
            case AppIntro.TOUCH_MOVE:
                m_engine.OnTouchMove(x, y);
                break;
            case AppIntro.TOUCH_UP:
                m_engine.OnTouchUp(x, y);
                break;
        }
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
        m_engine.Render(canvas);
    }

}
