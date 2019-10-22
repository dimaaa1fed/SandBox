package com.example.sandboxapp.game;

import com.example.sandboxapp.game_objects.Sand;
import com.example.sandboxapp.game_objects.SandParticle;
import com.example.sandboxapp.physics.Intersection;

import java.util.Iterator;

public class LogicEngine {
    private GameScene m_gameScene;
    private int       m_totalSize;
    private int       m_collectedSize;

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
    }


    void Update () {
        Sand sand = m_gameScene.GetSand();
        Iterator<SandParticle> it = sand.Iterator();
        while (it.hasNext()) {
            SandParticle particle = it.next();

            if (Intersection.GeomBoxVsGeomBox(particle.GetPhysBox(), m_gameScene.GetBucket().GetPhysBox())) {
                m_collectedSize += 1;
                it.remove();
            }
        }

         if (m_collectedSize / (float)sand.Size() < m_gameScene.GetLevelDesc().m_percentToWin) {
             m_levelPlayState = PlayState.FAILED;
         } else {
             m_levelPlayState = PlayState.SUCCSED;
         }
    }
}
