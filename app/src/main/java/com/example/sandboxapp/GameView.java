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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.example.sandboxapp.game.Engine;

import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO: document your custom view class.
 */
public class GameView extends View {
    public static GameView game_view;

    private TextPaint mTextPaint;

    // OUR
    private boolean			 m_active   = false;

    Engine m_engine;

    private final ReentrantLock lock = new ReentrantLock();

    Switch m_pausePlaySwitch;

    private static final int UPDATE_TIME_MS = 30;

    public GameView(Context context) {
        super(context);
        init(null, 0);
        // OUR
        m_engine = new Engine();

        game_view = this;
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);

        // OUR
        m_engine = new Engine();
        game_view = this;
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);

        // OUR
        m_engine = new Engine();
        game_view = this;
    }

    public void Init(ProgressBar bar, Button reset, Switch pausePlaySwitch)
    {
        m_engine.Init(bar, lock);
        m_pausePlaySwitch = pausePlaySwitch;

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reset();
            }
        });

        pausePlaySwitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                while (!lock.tryLock())
                    ;
                try {
                    m_active = !m_active;
                    m_engine.Pause();
                } finally {
                    lock.unlock();
                }
            }
        });

    }
    Thread t;
    public void start()
    {
        m_active 	= true;
        t = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    while (!lock.tryLock())
                        ;
                    try {
                        update();
                    } finally {
                        lock.unlock();
                    }

                }
            }
        });
        t.start();
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
        if (!m_active)
            return true;

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

    public void reset() {
        m_engine.Reset();
        m_active = true;
        m_pausePlaySwitch.setChecked(false);
    }


    public void update()
    {
        if (m_active) {
            m_engine.Update();
            invalidate();
        }
    }

    private void invalidateTextPaintAndMeasurements() {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // OUR
        m_engine.Render(canvas);
    }
}
