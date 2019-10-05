package com.example.sandboxapp.game;

import com.example.sandboxapp.physics.PhysBox;

import java.util.HashMap;

public class PhysicEngine {

    private HashMap<Integer, PhysBox> objs = new HashMap<>();

    public PhysicEngine() {

    }

    public void AddPhysBox(int id, PhysBox box) {
        objs.put(id, box);
    }

    public void RemovePhysBox(int id) {
        objs.remove(id);
    }

    public void Update(float dt) {

    }

}
