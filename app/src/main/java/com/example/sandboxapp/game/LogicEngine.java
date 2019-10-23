package com.example.sandboxapp.game;

import android.util.Log;

import com.example.sandboxapp.game_objects.Sand;
import com.example.sandboxapp.game_objects.SandParticle;
import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.Intersection;

import java.util.Iterator;

public class LogicEngine {
    private GameScene m_gameScene;
    private int       m_totalSize;
    private int       m_collectedSize;
    private int       m_loosedSize;

    public enum PlayState {
        PROCESSING,
        FAILED,
        SUCCSED
    }

    PlayState m_levelPlayState;

    LogicEngine (GameScene scene)
    {
        m_gameScene = scene;
        m_levelPlayState = PlayState.PROCESSING;
        m_totalSize = scene.GetSand().Size();
        m_collectedSize = m_loosedSize = 0;
    }


    void Update (double deltaAngle, double rotAngle) {
        m_gameScene.GetBucket().RotateBy(-rotAngle);
        //Log.d("app/logic", String.format("box coord %f %f", m_gameScene.GetBucket().GetCenter().x, m_gameScene.GetBucket().GetCenter().y));
        Sand sand = m_gameScene.GetSand();
        Iterator<SandParticle> it = sand.Iterator();
        int i = 0;
        while (it.hasNext()) {
            SandParticle particle = it.next();
            if (i == 0) {
                i++;
                //Log.d("app/logic", String.format("sand %f %f", particle.GetPhysBox().getCenter().x, particle.GetPhysBox().getCenter().y));
            }

            if (Intersection.GeomBoxVsBucket(particle.GetPhysBox(), m_gameScene.GetBucket())) {
                //Log.d("app/logic", "sand collected");
                m_collectedSize += 1;
                it.remove();
            } else if (Intersection.GeomBoxOutOfWorld(particle.GetPhysBox(), m_gameScene.GetBucket()))  {
                m_loosedSize += 1;
                it.remove();
            }
        }

         if (m_collectedSize / (float)m_totalSize < m_gameScene.GetLevelDesc().m_percentToWin) {
             if (m_loosedSize / (float)m_totalSize <= 1 - m_gameScene.GetLevelDesc().m_percentToWin)
                m_levelPlayState = PlayState.PROCESSING;
             else
                 m_levelPlayState = PlayState.FAILED;
         } else {
             m_levelPlayState = PlayState.SUCCSED;
         }

        m_gameScene.GetBucket().RotateBy(rotAngle);
    }
}
