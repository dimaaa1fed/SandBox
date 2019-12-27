package com.example.sandboxapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.*;
//import android.widget.*;

import java.util.Locale;
import android.util.Log;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.content.res.*;
import android.graphics.*;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Switch;

import org.xmlpull.v1.XmlPullParser;
//import android.view.ViewGroup.LayoutParams;





// ****************************************************************************

public class MainActivity extends Activity implements  OnCompletionListener, View.OnTouchListener
{
    // ********************************************
    // CONST
    // ********************************************
    public static final int	VIEW_INTRO		= 0;
    public static final int	VIEW_GAME       = 1;


    // *************************************************
    // DATA
    // *************************************************
    int						m_viewCur = -1;

    AppIntro				m_app;
    ViewIntro			    m_viewIntro;
    GameView				m_viewGame;


    // screen dim
    int						m_screenW;
    int						m_screenH;

    Handler h_;


    // *************************************************
    // METHODS
    // *************************************************
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(0, 0);
        // No Status bar
        final Window win = getWindow();
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Application is never sleeps
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        m_screenW = point.x;
        m_screenH = point.y;

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Log.d("THREE", "Screen size is " + String.valueOf(m_screenW) + " * " +  String.valueOf(m_screenH) );

        // Detect language
        String strLang = Locale.getDefault().getDisplayLanguage();
        int language;
        if (strLang.equalsIgnoreCase("english"))
        {
            Log.d("THREE", "LOCALE: English");
            language = AppIntro.LANGUAGE_ENG;
        }
        else if (strLang.equalsIgnoreCase("русский"))
        {
            Log.d("THREE", "LOCALE: Russian");
            language = AppIntro.LANGUAGE_RUS;
        }
        else
        {
            Log.d("THREE", "LOCALE unknown: " + strLang);
            language = AppIntro.LANGUAGE_UNKNOWN;
            //AlertDialog alertDialog;
            //alertDialog = new AlertDialog.Builder(this).create();
            //alertDialog.setTitle("Language settings");
            //alertDialog.setMessage("This application available only in English or Russian language.");
            //alertDialog.show();
        }
        // Create application
        m_app = new AppIntro(this, language);
        // Create view
        setView(VIEW_INTRO);


    }
    public void setView(int viewID)
    {
        if (m_viewCur == viewID)
        {
            Log.d("THREE", "setView: already set");
            return;
        }

        m_viewCur = viewID;
        if (m_viewCur == VIEW_INTRO)
        {
            m_viewIntro = new ViewIntro(this);
            setContentView(m_viewIntro);
        }
        if (m_viewCur == VIEW_GAME)
        {
            Log.d("THREE", "Switch to m_viewGame" );

            setContentView(R.layout.sample_game_view);

            m_viewGame = GameView.game_view;

            findViewById(R.id.game).setOnTouchListener(new View.OnTouchListener() {
                                              @Override
                                              public boolean onTouch(View v, MotionEvent evt) {
                                                  int x = (int)evt.getX();
                                                  int y = (int)evt.getY();
                                                  int touchType = AppIntro.TOUCH_DOWN;

                                                  //if (evt.getAction() == MotionEvent.ACTION_DOWN)
                                                  //  Log.d("THREE", "Touessed (ACTION_DOWN) at (" + String.valueOf(x) + "," + String.valueOf(y) +  ")"  );

                                                  if (evt.getAction() == MotionEvent.ACTION_MOVE)
                                                      touchType = AppIntro.TOUCH_MOVE;
                                                  if (evt.getAction() == MotionEvent.ACTION_UP)
                                                      touchType = AppIntro.TOUCH_UP;

                                                  if (m_viewCur == VIEW_INTRO)
                                                      return m_viewIntro.onTouch( x, y, touchType);
                                                  if (m_viewCur == VIEW_GAME)
                                                      return m_viewGame.onTouch(x, y, touchType);

                                                  return true;
                                              }
                                              });


            m_viewGame.Init(
                    (ProgressBar) findViewById(R.id.progressBar),
                    (Button) findViewById(R.id.reset_button),
                    (Switch) findViewById(R.id.pause_play_switch),
                    (Button) findViewById(R.id.next_level),
                    this
            );
            h_ = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    // обновляем TextView
                    m_viewGame.invalidate();
                };
            };
            m_viewGame.start(h_);
        }
    }

    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.

        // delayedHide(100);
    }
    public void onCompletion(MediaPlayer mp)
    {
        Log.d("THREE", "onCompletion: Video play is completed");
        //switchToGame();
    }


    public boolean onTouch(View v, MotionEvent evt)
    {
        int x = (int)evt.getX();
        int y = (int)evt.getY();
        int touchType = AppIntro.TOUCH_DOWN;

        //if (evt.getAction() == MotionEvent.ACTION_DOWN)
        //  Log.d("THREE", "Touessed (ACTION_DOWN) at (" + String.valueOf(x) + "," + String.valueOf(y) +  ")"  );

        if (evt.getAction() == MotionEvent.ACTION_MOVE)
            touchType = AppIntro.TOUCH_MOVE;
        if (evt.getAction() == MotionEvent.ACTION_UP)
            touchType = AppIntro.TOUCH_UP;

        if (m_viewCur == VIEW_INTRO)
            return m_viewIntro.onTouch( x, y, touchType);
        if (m_viewCur == VIEW_GAME)
            return m_viewGame.onTouch(x, y, touchType);

        return true;
    }
    public boolean onKeyDown(int keyCode, KeyEvent evt)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            //Log.d("THREE", "Back key pressed");
            //boolean wantKill = m_app.onKey(Application.KEY_BACK);
            //if (wantKill)
            //		finish();
            //return true;
        }
        boolean ret = super.onKeyDown(keyCode, evt);
        return ret;
    }
    public AppIntro getApp()
    {
        return m_app;
    }

    protected void onResume()
    {
        super.onResume();
        if (m_viewCur == VIEW_INTRO)
            m_viewIntro.start();
        if (m_viewCur == VIEW_GAME)
        {
            h_ = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    // обновляем TextView
                    m_viewGame.invalidate();
                }
            };
            m_viewGame.start(h_);
        }
        //Log.d("THREE", "App onResume");
    }
    protected void onPause()
    {
        // stop anims
        if (m_viewCur == VIEW_INTRO)
            m_viewIntro.stop();
        if (m_viewCur == VIEW_GAME)
            m_viewGame.onPause();

        // complete system
        super.onPause();
        //Log.d("THREE", "App onPause");
    }
    protected void onDestroy()
    {
        if (m_viewCur == VIEW_GAME)
            m_viewGame.onDestroy();
        super.onDestroy();
        //Log.d("THREE", "App onDestroy");
    }
    public void onConfigurationChanged(Configuration confNew)
    {
        super.onConfigurationChanged(confNew);
        m_viewIntro.onConfigurationChanged(confNew);
    }

}

