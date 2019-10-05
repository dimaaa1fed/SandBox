package com.example.sandboxapp.game_objects;

import java.util.ArrayList;

public class Sand {
    private ArrayList<SandParticle> m_sand = new ArrayList<SandParticle>();

    public Sand (int size) {
        m_sand.ensureCapacity(size);
    }

    public SandParticle At (int i) { return m_sand.get(i); }

    public int Size () { return m_sand.size(); }

    public void Push (SandParticle sp) { m_sand.add(sp); }
}
