package com.example.sandboxapp;

//import android.app.Activity;
import android.content.res.*;
import android.content.Intent;
import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.*;
import android.net.*;
import java.io.*;

public class AppIntro
{
    // CONST
    static public final int LANGUAGE_ENG				          = 0;
    static public final int LANGUAGE_RUS				          = 1;
    static public final int LANGUAGE_UNKNOWN			        = 2;

    static public final int TOUCH_DOWN					          = 0;
    static public final int TOUCH_MOVE					          = 1;
    static public final int TOUCH_UP					            = 2;

    static public final int APP_ORI_LANDSCAPE			        = 0;
    static public final int APP_ORI_PORTRAIT			        = 1;

    static public final int APP_STATE_START					      = 0;
    static public final int APP_STATE_CIRCLE_INC        	= 1;
    static public final int APP_STATE_APPLE_2ND_RADIUS  	= 2;
    static public final int APP_STATE_APPLE_FILL_OPA      = 3;
    static public final int APP_STATE_APPLE_FILL_SHADER   = 4;
    static public final int APP_STATE_GRAFT               = 5;
    static public final int APP_STATE_LEAF                = 6;

    static public final int APP_STATE_FINISHED				    = 10;

    // parameters for animation
    static private final int TIME_CIRCLE_INC              = 300;
    static private final int TIME_APPLE_INC				        = 300;
    static private final int TIME_SHADER_COLORED		      = 300;
    // should be power of 2
    static private final int TIME_LEAF					          = 256;
    static private final int NUM_SEG_APPLE				        = 60;

    static private String  m_log = "THREE";

    // DATA
    private long				m_curTime, m_prevTime;
    private int					m_renderCounter;

    private MainActivity		m_ctx;
    private int					m_language;
    private int					m_appState;
    private int					m_timeState;

    private int					m_oriChanged;

    private Path				m_pathAppleOutline;
    private Paint				m_paintGreenEmpty;
    private Paint				m_paintGreenFill;
    private Paint				m_paintGraftFill;
    private Paint				m_paintLeafFill;
    private Paint				m_paintTextWhite;
    private Paint				m_paintTextYell;
    private Paint				m_paintBitmap;
    private Path				m_pathAppleGraft;
    private Path				m_pathAppleLeaf;

    private Paint				m_paintRectButton;
    private Paint				m_paintTextButton;

    private String				m_strDepth;
    private String				m_strUniversity1;
    private String				m_strUniversity2;
    private String				m_strWeb;
    private String				m_strStart;
    private String				m_strAmdUrl;

    // Apple body parameters
    private int					m_scrW, m_scrH;
    private int					m_scrCenterX, m_scrCenterY;
    private float				m_appleRadiusBase;
    private float				m_appleRadiusMin;
    private V2d					m_point;

    // Buttons rects
    public RectF 			m_rectBtnStart;
    public RectF 			m_rectBtnWeb;
    public int        m_canPressButtons;

    // METHODS
    public AppIntro(MainActivity ctx, int language)
    {

        m_ctx 				= ctx;
        m_language 			= language;
        m_prevTime			= -1;
        m_oriChanged		= 0;

        m_appState 			= APP_STATE_START;

        m_pathAppleOutline	= new Path();
        m_pathAppleGraft	= new Path();
        m_pathAppleLeaf		= new Path();

        m_point				= new V2d(0, 0);
        m_renderCounter 	= 0;
        m_paintGreenEmpty	= new Paint();
        m_paintGreenEmpty.setStyle(Style.STROKE);
        m_paintGreenEmpty.setColor(0xFF207020);
        m_paintGreenEmpty.setAntiAlias(true);
        m_paintGreenEmpty.setStrokeWidth(3.0f);

        m_paintGreenFill 	= new Paint();
        m_paintGreenFill.setStyle(Style.FILL_AND_STROKE);
        m_paintGreenFill.setColor(0xFF207020);
        m_paintGreenFill.setAntiAlias(true);
        m_paintGreenFill.setStrokeWidth(3.0f);
        m_paintGreenFill.setAlpha(255);

        m_paintGraftFill = new Paint();
        m_paintGraftFill.setStyle(Style.FILL);
        m_paintGraftFill.setColor(0xFF905000);
        m_paintGraftFill.setAntiAlias(true);

        m_paintLeafFill = new Paint();
        m_paintLeafFill.setStyle(Style.FILL);
        m_paintLeafFill.setColor(0xFF3aa142);
        m_paintLeafFill.setAntiAlias(true);

        m_paintTextWhite = new Paint();
        m_paintTextWhite.setColor(0xFFFFFFFF);
        m_paintTextWhite.setAntiAlias(true);
        m_paintTextWhite.setStyle(Style.FILL);
        m_paintTextWhite.setTextSize(24.0f);
        m_paintTextWhite.setTextAlign(Align.CENTER);

        m_paintBitmap = new Paint();
        m_paintBitmap.setColor(0xFFFFFFFF);
        m_paintBitmap.setStyle(Style.FILL);

        m_paintTextYell = new Paint();
        m_paintTextYell.setColor(0xFFFFFF00);
        m_paintTextYell.setAntiAlias(true);
        m_paintTextYell.setStyle(Style.FILL);
        m_paintTextYell.setTextSize(14.0f);
        m_paintTextYell.setTextAlign(Align.CENTER);

        m_paintTextButton = new Paint();
        m_paintTextButton.setColor(0xFF000088);
        m_paintTextButton.setStyle(Style.FILL);
        m_paintTextButton.setTextSize(20.0f);
        m_paintTextButton.setTextAlign(Align.CENTER);
        m_paintTextButton.setAntiAlias(true);

        m_paintRectButton	= new Paint();
        m_paintRectButton.setStyle(Style.FILL);
        m_paintRectButton.setAntiAlias(true);

        m_rectBtnStart 	= new RectF();
        m_rectBtnWeb 		= new RectF();
        m_canPressButtons = 0;


        Resources res 		= ctx.getResources();
        String strPackage 	= ctx.getPackageName();
        m_strDepth 			= res.getString(res.getIdentifier("str_depth", "string", strPackage ));
        m_strUniversity1 	= res.getString(res.getIdentifier("str_university1", "string", strPackage ));
        m_strUniversity2 	= res.getString(res.getIdentifier("str_university2", "string", strPackage ));
        m_strWeb 			= res.getString(res.getIdentifier("str_toweb", "string", strPackage ));
        m_strStart 			= res.getString(res.getIdentifier("str_start", "string", strPackage ));
        m_strAmdUrl   = res.getString(res.getIdentifier("str_amd_url", "string", strPackage ));

        if ((TIME_LEAF & (TIME_LEAF - 1)) != 0)
        {
            Log.d(m_log, "!!!! Constant parameter TIME_LEAF is ont power of 2 !!!");
        }
    }

    public int		getLanguage()
    {
        return m_language;
    }

    public Bitmap	loadBitmap(String fileName)
    {
        Bitmap bmp = null;
        try
        {
            AssetManager mngr = m_ctx.getAssets();
            InputStream ins = mngr.open(fileName);
            bmp = BitmapFactory.decodeStream(ins);
        } catch (IOException e)
        {
            Log.d(m_log, "Error read bitmap");
        }
        return bmp;
    }
    public void onOrientation(int ori)
    {
        Log.d(m_log, "New orientation");
        m_oriChanged = 1;
    }


    public void drawCanvas(Canvas canvas)
    {
        m_curTime 		= System.currentTimeMillis();
        if (m_prevTime == -1)
            m_prevTime = m_curTime;
        int deltaTimeMs = (int)( m_curTime - m_prevTime);
        m_prevTime = m_curTime;
        if (deltaTimeMs > 300)
            deltaTimeMs = 300;

        if (m_oriChanged == 1)
        {
            m_oriChanged = 0;
            acceptNewScreen(canvas);
        }

        m_renderCounter++;
        if (m_appState == APP_STATE_START)
        {
            initDrawCircle(canvas);
            m_appState = APP_STATE_CIRCLE_INC;
        }
        if (m_appState == APP_STATE_CIRCLE_INC)
        {
            drawCircleInc(canvas, deltaTimeMs);
            return;
        }
        if (m_appState == APP_STATE_APPLE_2ND_RADIUS)
        {
            drawAppleEmptyInc(canvas, deltaTimeMs);
            return;
        }
        if (m_appState == APP_STATE_APPLE_FILL_OPA)
        {
            drawAppleFillOpacity(canvas, deltaTimeMs);
            return;
        }
        if (m_appState == APP_STATE_APPLE_FILL_SHADER)
        {
            drawAppleFillShader(canvas, deltaTimeMs);
            return;
        }
        if (m_appState == APP_STATE_GRAFT)
        {
            drawAppleGraft(canvas, deltaTimeMs);
            return;
        }
        if (m_appState == APP_STATE_LEAF)
        {
            drawAppleLeaf(canvas, deltaTimeMs);
            return;
        }
    }
    //public int isFinished()
    //{
    //	return (m_appState == APP_STATE_FINISHED)? 1: 0;
    //}
    private boolean isConnectedToInternet()
    {
        ConnectivityManager cm = (ConnectivityManager)m_ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected())
        {
            return true;
        } else
        {
            return false;
        }
    }


    // *****************************************************
    // Individual state draws
    // *****************************************************

    // t in [0..1]
    private void getCardioid(float t, float cx, float cy, float radiusBase, float radiusApple, V2d pointOut)
    {
        float phi;

        phi = 3.1415925636f * 2.0f * t;
        pointOut.x = (int)( cx + radiusBase * Math.sin(phi) + radiusApple * ( Math.sin(phi) - Math.sin(2.0f * phi) ) );
        pointOut.y = (int)( cy - radiusBase * Math.cos(phi) - radiusApple * ( Math.cos(phi) - Math.cos(2.0f * phi) ) );
    }
    private void acceptNewScreen(Canvas canvas)
    {
        m_scrW = canvas.getWidth();
        m_scrH = canvas.getHeight();

        m_scrCenterX = m_scrW >> 1;
        m_scrCenterY = m_scrH >> 1;
        int dimMin = (m_scrW < m_scrH)? m_scrW: m_scrH;
        int dimMax = (m_scrW > m_scrH)? m_scrW: m_scrH;
        //m_appleRadiusBase = (float)dimMin * 0.12f;
        m_appleRadiusBase = (float)dimMin * 0.09f;
        m_paintTextButton.setTextSize(dimMax * 0.02f);

        float textSize = m_scrH * 0.03f;
        if (textSize < 20.0f)
            textSize = 20.0f;
        if (textSize > 40.0f)
            textSize = 40.0f;
        float ts = 32.0f;
        //m_paintTextWhite.setTextSize(textSize);
        //m_paintTextYell.setTextSize(textSize * 0.6f);
        m_paintTextWhite.setTextSize(ts);
        m_paintTextYell.setTextSize(ts * 0.8f);
    }

    private void initDrawCircle(Canvas canvas)
    {
        acceptNewScreen(canvas);
        m_timeState = 0;
    }

    private void drawButton(Canvas canvas, RectF rectIn, String str, int color1, int color2, int alpha)
    {
        int 	scrW 	= canvas.getWidth();
        float	rectRad = scrW * 0.04f;
        float	rectBord = scrW * 0.005f;
        RectF	rect = new RectF(rectIn);

        RectF   rectInside = new RectF( rect.left + rectBord, rect.top + rectBord, rect.right - rectBord, rect.bottom - rectBord);

        int colors[] = { 0, 0 };
        colors[0] = color1 | (alpha << 24);
        colors[1] = color2 | (alpha << 24);
        LinearGradient shader = new LinearGradient(rect.left, rect.top, rect.left, rect.bottom, colors, null, Shader.TileMode.CLAMP);
        Paint	paintInside = new Paint();
        paintInside.setAntiAlias(true);
        paintInside.setShader(shader);

        m_paintRectButton.setColor(0xFFFFFF | (alpha<<24) );
        canvas.drawRoundRect(rect, rectRad, rectRad, m_paintRectButton);
        m_paintRectButton.setColor(0x808080 | (alpha<<24) );
        canvas.drawRoundRect(rectInside, rectRad, rectRad, paintInside);

        Rect rText = new Rect();
        m_paintTextButton.getTextBounds(str, 0, str.length(), rText);
        float h = rText.height();
        float cx = rect.centerX();
        float cy = rect.centerY();
        m_paintTextButton.setAlpha(alpha);
        canvas.drawText(str, cx, cy + h * 0.5f, m_paintTextButton);
    }


    private void drawCircleInc(Canvas canvas, int deltaTimeMs)
    {
        int	r, g, b;
        r = g = b = 0;
        canvas.drawRGB(r, g, b);

        float 	rAnim = (float)m_timeState / TIME_CIRCLE_INC;
        if (rAnim > 1.0f) rAnim = 1.0f;
        float	radiusBase = 5.0f * (1.0f - rAnim) + m_appleRadiusBase * rAnim;
        float   radiusApple  = 0.0f;

        m_pathAppleOutline.reset();
        float t = 0.0f, tStep = 1.0f / NUM_SEG_APPLE;
        for (int a = 0; a < NUM_SEG_APPLE; a ++)
        {
            getCardioid(t, m_scrCenterX, m_scrCenterY, radiusBase, radiusApple, m_point);
            t += tStep;
            if (a == 0)
                m_pathAppleOutline.moveTo(m_point.x, m_point.y);
            else
                m_pathAppleOutline.lineTo(m_point.x, m_point.y);
        }
        m_pathAppleOutline.close();
        // draw path
        canvas.drawPath(m_pathAppleOutline, m_paintGreenEmpty);

        // update state
        m_timeState += deltaTimeMs;
        if (m_timeState > TIME_CIRCLE_INC)
        {
            m_timeState = 0;
            m_appState = m_appState + 1;
        }
    }
    private void drawAppleEmptyInc(Canvas canvas, int deltaTimeMs)
    {
        canvas.drawRGB(0, 0, 0);

        // Setup apple shape (outline)
        float 	rAnim = (float)m_timeState / TIME_APPLE_INC;
        if (rAnim > 1.0f) rAnim = 1.0f;
        float	radiusBase = m_appleRadiusBase;
        float   radiusApple = 0.0f * (1.0f - rAnim) + radiusBase * rAnim;

        m_pathAppleOutline.reset();
        float t = 0.0f, tStep = 1.0f / NUM_SEG_APPLE;
        for (int a = 0; a < NUM_SEG_APPLE; a ++)
        {
            getCardioid(t, m_scrCenterX, m_scrCenterY, radiusBase, radiusApple, m_point);
            t += tStep;
            if (a == 0)
                m_pathAppleOutline.moveTo(m_point.x, m_point.y);
            else
                m_pathAppleOutline.lineTo(m_point.x, m_point.y);
        }
        m_pathAppleOutline.close();
        // draw path
        canvas.drawPath(m_pathAppleOutline, m_paintGreenEmpty);

        // update state
        m_timeState += deltaTimeMs;
        if (m_timeState > TIME_APPLE_INC)
        {
            m_timeState = 0;
            m_appState = m_appState + 1;
        }
    }		// func

    private void drawAppleFillOpacity(Canvas canvas, int deltaTimeMs)
    {
        canvas.drawRGB(0, 0, 0);

        // Setup apple shape (outline)
        float 	rAnim = (float)m_timeState / TIME_APPLE_INC;
        if (rAnim > 1.0f) rAnim = 1.0f;
        float	radiusBase 	= m_appleRadiusBase;
        float   radiusApple = radiusBase;
        int		opa = (int)(rAnim * 255.0f);


        m_pathAppleOutline.reset();
        float t = 0.0f, tStep = 1.0f / NUM_SEG_APPLE;
        for (int a = 0; a < NUM_SEG_APPLE; a ++)
        {
            getCardioid(t, m_scrCenterX, m_scrCenterY, radiusBase, radiusApple, m_point);
            t += tStep;
            if (a == 0)
                m_pathAppleOutline.moveTo(m_point.x, m_point.y);
            else
                m_pathAppleOutline.lineTo(m_point.x, m_point.y);
        }
        m_pathAppleOutline.close();
        m_paintGreenFill.setAlpha(opa);

        // draw path
        canvas.drawPath(m_pathAppleOutline, m_paintGreenFill);
        canvas.drawPath(m_pathAppleOutline, m_paintGreenEmpty);

        // update state
        m_timeState += deltaTimeMs;
        if (m_timeState > TIME_APPLE_INC)
        {
            m_timeState = 0;
            m_appState = m_appState + 1;
        }
    }		// func
    private void drawAppleFillShader(Canvas canvas, int deltaTimeMs)
    {
        canvas.drawRGB(0, 0, 0);

        // Setup apple shape (outline)
        float 	rAnim = (float)m_timeState / TIME_SHADER_COLORED;
        if (rAnim > 1.0f) rAnim = 1.0f;
        float	radiusBase 		= m_appleRadiusBase;
        float   radiusApple 	= radiusBase;
        int		opa 			= 255;


        m_pathAppleOutline.reset();
        float t = 0.0f, tStep = 1.0f / NUM_SEG_APPLE;
        for (int a = 0; a < NUM_SEG_APPLE; a ++)
        {
            getCardioid(t, m_scrCenterX, m_scrCenterY, radiusBase, radiusApple, m_point);
            t += tStep;
            if (a == 0)
                m_pathAppleOutline.moveTo(m_point.x, m_point.y);
            else
                m_pathAppleOutline.lineTo(m_point.x, m_point.y);
        }
        m_pathAppleOutline.close();
        m_paintGreenFill.setAlpha(opa);

        int colors[] = new int[2];
        float	xSpot, ySpot, radGrad;

        int r = (int)(0x20 * (1.0f - rAnim) + 0xAA * rAnim);
        int g = (int)(0x70 * (1.0f - rAnim) + 0xFF * rAnim);
        int b = (int)(0x20 * (1.0f - rAnim) + 0xAA * rAnim);

        colors[0] = 0xFF000000 | (r<<16) | (g<<8) | b;
        colors[1] = 0xFF207020;

        xSpot = m_scrCenterX + m_appleRadiusBase * 0.5f;
        ySpot = m_scrCenterY + m_appleRadiusBase * 0.8f;
        radGrad = m_appleRadiusBase * 2.5f;
        RadialGradient gradientRadial = new RadialGradient(xSpot, ySpot, radGrad, colors, null, Shader.TileMode.CLAMP);
        m_paintGreenFill.setShader(gradientRadial);

        // draw path
        canvas.drawPath(m_pathAppleOutline, m_paintGreenFill);

        // update state
        m_timeState += deltaTimeMs;
        if (m_timeState > TIME_SHADER_COLORED)
        {
            m_timeState = 0;
            m_appState 	= m_appState + 1;
        }
    }		// func
    private void drawAppleGraft(Canvas canvas, int deltaTimeMs)
    {
        canvas.drawRGB(0, 0, 0);

        // Setup apple shape (outline)
        float 	rAnim = (float)m_timeState / TIME_SHADER_COLORED;
        if (rAnim > 1.0f) rAnim = 1.0f;
        float	radiusBase 		= m_appleRadiusBase;
        float   radiusApple 	= radiusBase;
        int		opa 			= 255;


        m_pathAppleOutline.reset();
        float t = 0.0f, tStep = 1.0f / NUM_SEG_APPLE;
        for (int a = 0; a < NUM_SEG_APPLE; a ++)
        {
            getCardioid(t, m_scrCenterX, m_scrCenterY, radiusBase, radiusApple, m_point);
            t += tStep;
            if (a == 0)
                m_pathAppleOutline.moveTo(m_point.x, m_point.y);
            else
                m_pathAppleOutline.lineTo(m_point.x, m_point.y);
        }
        m_pathAppleOutline.close();
        m_paintGreenFill.setAlpha(opa);

        // Path for graft
        float	xLo, yLo, xHi, yHi, xMi, yMi;
        float	X_RATIO = 0.2f;
        float	Y_RATIO = 0.5f;

        xLo = m_scrCenterX;
        yLo = m_scrCenterY - m_appleRadiusBase;
        xHi = xLo + m_appleRadiusBase * 0.8f * rAnim;
        yHi = yLo - m_appleRadiusBase * 1.6f * rAnim;
        xMi = xLo * (1.0f - X_RATIO) + xHi * X_RATIO;
        yMi = yLo * (1.0f - Y_RATIO) + yHi * Y_RATIO;

        m_pathAppleGraft.reset();
        m_pathAppleGraft.moveTo(xLo,  yLo);
        m_pathAppleGraft.quadTo(xMi,  yMi, xHi, yHi);

        xHi = xLo + m_appleRadiusBase * 1.0f * rAnim;
        yHi = yLo - m_appleRadiusBase * 1.4f * rAnim;
        xMi = xLo * (1.0f - X_RATIO) + xHi * X_RATIO;
        yMi = yLo * (1.0f - Y_RATIO) + yHi * Y_RATIO;
        m_pathAppleGraft.lineTo(xHi,  yHi);
        m_pathAppleGraft.quadTo(xMi, yMi, xLo, yLo);
        m_pathAppleGraft.close();



        int colors[] = new int[2];
        float	xSpot, ySpot, radGrad;

        colors[0] = 0xFFAAFFAA;
        colors[1] = 0xFF207020;

        xSpot 	= m_scrCenterX + m_appleRadiusBase * 0.5f;
        ySpot 	= m_scrCenterY + m_appleRadiusBase * 0.8f;
        radGrad	= m_appleRadiusBase * 2.5f;
        RadialGradient gradientRadial = new RadialGradient(xSpot, ySpot, radGrad, colors, null, Shader.TileMode.CLAMP);
        m_paintGreenFill.setShader(gradientRadial);

        // draw path
        canvas.drawPath(m_pathAppleOutline, m_paintGreenFill);
        canvas.drawPath(m_pathAppleGraft,  m_paintGraftFill);

        // update state
        m_timeState += deltaTimeMs;
        if (m_timeState > TIME_SHADER_COLORED)
        {
            m_timeState = 0;
            m_appState 	= m_appState + 1;
        }
    }		// func

    private void drawAppleLeaf(Canvas canvas, int deltaTimeMs)
    {
        canvas.drawRGB(0, 0, 0);

        // Setup apple shape (outline)
        int 	tAnim = m_timeState & (TIME_LEAF - 1);
        int		animPhase = m_timeState / TIME_LEAF;
        if ( animPhase == 0)
        {
            // increase phase
            tAnim = tAnim * 256 / TIME_LEAF;
        }
        else
        {
            tAnim = 255;
        }
        int tText;
        if (animPhase == 0)
            tText = 0;
        else if (animPhase == 1)
        {
            tText = (m_timeState - TIME_LEAF) * 255 / TIME_LEAF;
        }
        else
            tText = 255;
        m_paintLeafFill.setAlpha(tAnim);
        m_paintTextWhite.setAlpha(tText);
        m_paintTextYell.setAlpha(tText);

        float	radiusBase 		= m_appleRadiusBase;
        float   radiusApple 	= radiusBase;
        int		opa 			= 255;


        m_pathAppleOutline.reset();
        float t = 0.0f, tStep = 1.0f / NUM_SEG_APPLE;
        for (int a = 0; a < NUM_SEG_APPLE; a ++)
        {
            getCardioid(t, m_scrCenterX, m_scrCenterY, radiusBase, radiusApple, m_point);
            t += tStep;
            if (a == 0)
                m_pathAppleOutline.moveTo(m_point.x, m_point.y);
            else
                m_pathAppleOutline.lineTo(m_point.x, m_point.y);
        }
        m_pathAppleOutline.close();
        m_paintGreenFill.setAlpha(opa);

        // Path for graft
        float	xLo, yLo, xHi, yHi, xMi, yMi;
        float	X_RATIO = 0.2f;
        float	Y_RATIO = 0.5f;

        xLo = m_scrCenterX;
        yLo = m_scrCenterY - m_appleRadiusBase;
        xHi = xLo + m_appleRadiusBase * 0.8f;
        yHi = yLo - m_appleRadiusBase * 1.6f;
        xMi = xLo * (1.0f - X_RATIO) + xHi * X_RATIO;
        yMi = yLo * (1.0f - Y_RATIO) + yHi * Y_RATIO;

        m_pathAppleGraft.reset();
        m_pathAppleGraft.moveTo(xLo,  yLo);
        m_pathAppleGraft.quadTo(xMi,  yMi, xHi, yHi);

        xHi = xLo + m_appleRadiusBase * 1.0f;
        yHi = yLo - m_appleRadiusBase * 1.4f;
        xMi = xLo * (1.0f - X_RATIO) + xHi * X_RATIO;
        yMi = yLo * (1.0f - Y_RATIO) + yHi * Y_RATIO;
        m_pathAppleGraft.lineTo(xHi,  yHi);
        m_pathAppleGraft.quadTo(xMi, yMi, xLo, yLo);
        m_pathAppleGraft.close();


        // internal green filler as radial gradient
        int colors[] = new int[2];
        float	xSpot, ySpot, radGrad;

        colors[0] = 0xFFAAFFAA;
        colors[1] = 0xFF207020;

        xSpot 	= m_scrCenterX + m_appleRadiusBase * 0.5f;
        ySpot 	= m_scrCenterY + m_appleRadiusBase * 0.8f;
        radGrad	= m_appleRadiusBase * 2.5f;
        RadialGradient gradientRadial = new RadialGradient(xSpot, ySpot, radGrad, colors, null, Shader.TileMode.CLAMP);
        m_paintGreenFill.setShader(gradientRadial);



        // path for leaf
        float	xL, yL, xR, yR, xU0, yU0, xU1, yU1, xD0, yD0, xD1, yD1;

        float LeafLen = m_appleRadiusBase * 1.35f;
        xR = yR = 0;
        xL = - LeafLen;
        yL = 0;
        float radR = LeafLen * 0.45f;
        float radL = LeafLen * 0.4f;

        float xLeafSource = m_scrCenterX + m_appleRadiusBase * 0.32f;
        float yLeafSource = m_scrCenterY - m_appleRadiusBase - m_appleRadiusBase * 0.90f;
        xU0 = -(float)Math.cos(80 * 3.1415f / 180.0f) * radR;
        yU0 = -(float)Math.sin(80 * 3.1415f / 180.0f) * radR;
        xD0 = -(float)Math.cos(80 * 3.1415f / 180.0f) * radR;
        yD0 = +(float)Math.sin(80 * 3.1415f / 180.0f) * radR;
        xU1 = xL + (float)Math.cos(40 * 3.1415f / 180.0f) * radL;
        yU1 = yL - (float)Math.sin(40 * 3.1415f / 180.0f) * radL;
        xD1 = xL + (float)Math.cos(15 * 3.1415f / 180.0f) * radL;
        yD1 = yL + (float)Math.sin(15 * 3.1415f / 180.0f) * radL;

        // Rotate leaf
        float	aCos = (float)Math.cos(-20.0f * 3.1415f / 180.0f);
        float	aSin = (float)Math.sin(-20.0f * 3.1415f / 180.0f);
        float	xx, yy;

        xx = xL * aCos + yL * aSin;
        yy =-xL * aSin + yL * aCos;
        xL = xx; yL = yy;
        xx = xR * aCos + yR * aSin;
        yy =-xR * aSin + yR * aCos;
        xR = xx; yR = yy;
        xx = xU0 * aCos + yU0 * aSin;
        yy =-xU0 * aSin + yU0 * aCos;
        xU0 = xx; yU0 = yy;
        xx = xU1 * aCos + yU1 * aSin;
        yy =-xU1 * aSin + yU1 * aCos;
        xU1 = xx; yU1 = yy;
        xx = xD0 * aCos + yD0 * aSin;
        yy =-xD0 * aSin + yD0 * aCos;
        xD0 = xx; yD0 = yy;
        xx = xD1 * aCos + yD1 * aSin;
        yy =-xD1 * aSin + yD1 * aCos;
        xD1 = xx; yD1 = yy;


        // Translate leaf
        xL  += xLeafSource; yL  += yLeafSource;
        xR  += xLeafSource; yR  += yLeafSource;
        xU0 += xLeafSource; yU0 += yLeafSource;
        xD0 += xLeafSource; yD0 += yLeafSource;
        xU1 += xLeafSource; yU1 += yLeafSource;
        xD1 += xLeafSource; yD1 += yLeafSource;




        m_pathAppleLeaf.reset();
        m_pathAppleLeaf.moveTo(xR,  yR);
        m_pathAppleLeaf.cubicTo(xU0,  yU0, xU1, yU1, xL, yL);
        m_pathAppleLeaf.cubicTo(xD1, yD1, xD0, yD0, xR, yR);
        m_pathAppleLeaf.close();



        // draw path
        canvas.drawPath(m_pathAppleOutline, m_paintGreenFill);
        canvas.drawPath(m_pathAppleGraft,  m_paintGraftFill);
        canvas.drawPath(m_pathAppleLeaf,   m_paintLeafFill);

        if (m_timeState > TIME_LEAF)
        {
            // draw titles
            Rect r = new Rect();

            m_paintTextWhite.getTextBounds(m_strDepth, 0, m_strDepth.length(), r);

            float h = r.height();
            float vOff = 0.0f;
            //if (m_scrH > m_scrW)
            vOff = h;
            canvas.drawText(m_strDepth, 	 0, m_strDepth.length(), 		m_scrCenterX, vOff + h , 			m_paintTextWhite);
            canvas.drawText(m_strUniversity1,0, m_strUniversity1.length(), 	m_scrCenterX, vOff + h * 2.5f,	  	m_paintTextYell);
            canvas.drawText(m_strUniversity2,0, m_strUniversity2.length(), 	m_scrCenterX, vOff + h * 2.5f + h,	m_paintTextYell);
        }
        if ( (m_timeState > 2* TIME_LEAF)  )
        {
            opa = 255;
            if (m_timeState < 3 * TIME_LEAF)
                opa = (m_timeState - 2 * TIME_LEAF) * 256 / TIME_LEAF;
            m_paintBitmap.setAlpha(opa);
            m_paintTextWhite.setAlpha(opa);

            m_canPressButtons = 1;

            final boolean hasWeb = isConnectedToInternet();
            final float BUTTON_SCALE				= 4.1f;
            final float BUTTON_W_H_RATIO		= 0.3f;

            int bw = (int)(m_appleRadiusBase * BUTTON_SCALE);
            int bh = (int)(bw * BUTTON_W_H_RATIO);
            int bwHalf = (bw >> 1);
            if (m_scrH > m_scrW)
            {
                // vertical buttons layout
                m_rectBtnStart.set(m_scrCenterX - bwHalf, m_scrH - bh * 2, m_scrCenterX + bwHalf, m_scrH - bh);
                m_rectBtnWeb.set(  m_scrCenterX - bwHalf, m_scrH - bh * 4, m_scrCenterX + bwHalf, m_scrH - bh * 3);
            }
            else
            {
                // horizontal buttons layout
                m_rectBtnStart.set(m_scrCenterX - bwHalf, m_scrH - bh, m_scrCenterX + bwHalf, m_scrH);
                m_rectBtnWeb.set(  -1000, -1000, -500, -500);
                if (hasWeb)
                {
                    m_rectBtnStart.set(  m_scrCenterX - bw, m_scrH - bh, m_scrCenterX, m_scrH);
                    m_rectBtnWeb.set(  m_scrCenterX, m_scrH - bh, m_scrCenterX + bw, m_scrH);
                    m_rectBtnStart.offset( - bh * 0.5f, 0.0f);
                    m_rectBtnWeb.offset( + bh * 0.5f, 0.0f);
                }
            }
            drawButton(canvas, m_rectBtnStart, m_strStart, 0x92DCFE, 0x1e80B0, opa);
            if (hasWeb)
            {
                drawButton(canvas, m_rectBtnWeb, m_strWeb, 0x92DCFE, 0x1e80B0, opa);
            }
        }

        // update state
        m_timeState += deltaTimeMs;
    }		// func

    public boolean	onTouch(int x, int y, int touchType)
    {
        if (touchType != AppIntro.TOUCH_DOWN)
            return false;

        if (m_rectBtnStart.contains(x,  y))
        {
            m_ctx.setView(MainActivity.VIEW_GAME);
            return false;
        }

        if (isConnectedToInternet() )
        {
            if (m_rectBtnWeb.contains(x,  y))
            {
                // go to web
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(m_strAmdUrl));
                m_ctx.startActivity(browserIntent);
                return false;
            }
        }
        // check simple click => switch to next view
        m_ctx.setView(MainActivity.VIEW_GAME);
        return true;
    }	// onTouch
}

