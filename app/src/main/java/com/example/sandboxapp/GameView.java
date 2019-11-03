package com.example.sandboxapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

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
    private GameView	m_viewGame;

    public RefreshHandler(GameView v)
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

/**
 * TODO: document your custom view class.
 */
public class GameView extends View {
    public static GameView game_view;

    private TextPaint mTextPaint;

    // OUR
    private RefreshHandler   m_refresh;
    private boolean			 m_active   = false;

    Engine m_engine;

    private static final int UPDATE_TIME_MS = 30;

    public GameView(Context context) {
        super(context);
        init(null, 0);
        // OUR
        m_engine = new Engine();
        m_refresh = new RefreshHandler(this);

        game_view = this;
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

        // OUR
        m_engine = new Engine();
        m_refresh = new RefreshHandler(this);
        game_view = this;
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);

        // OUR
        m_engine = new Engine();
        m_refresh = new RefreshHandler(this);
        game_view = this;
    }

    public void start()
    {
        m_active 	= true;
        m_refresh.sleep(UPDATE_TIME_MS);
    }

    public void onDestroy()
    {
    }

    public void onPause()
    {
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.GameView, defStyle, 0);

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        setBackgroundResource(R.drawable.background);


        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
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




    public void update()
    {
        if (!m_active)
            return;
        // send next update to game
        if (m_active)
            m_refresh.sleep(UPDATE_TIME_MS);

    }

    private void invalidateTextPaintAndMeasurements() {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // OUR
        m_engine.Update();
        m_engine.Render(canvas);
    }
}
