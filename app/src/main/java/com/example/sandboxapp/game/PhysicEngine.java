package com.example.sandboxapp.game;

import android.util.Log;

import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.Intersection;
import com.example.sandboxapp.physics.Mainfold;
import com.example.sandboxapp.physics.PhysBox;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.ceil;

public class PhysicEngine {
    private HashMap<Integer, PhysBox> objs     = new HashMap<>();
    private ArrayList<Mainfold>       contacts = new ArrayList<>();

    private ArrayList<Integer> aliveIds = new ArrayList<>();

    private Vec2d gravity = new Vec2d(0, 0.5);

    private double EPSILON = 0.0001;

    private double max_dt = 0.01;

    public PhysicEngine() {

    }

    public void AddPhysBox(int id, PhysBox box)
    {
        aliveIds.add(id);
        objs.put(id, box);
    }

    public void RemovePhysBox(int id)
    {
        // TODO:
        aliveIds.remove(id);
        objs.remove(id);
    }

    public void Update (double dt, double rotAngle) {
        int steps;
        double dt_step;

        gravity.rotateBy(-rotAngle);

        if (dt <= max_dt)
        {
            steps = 1;
            dt_step = dt;
        }
        else
        {
            dt_step = max_dt;
            steps = (int)ceil((dt / max_dt));
        }

        for (int i = 0; i < steps; i++)
        {
            double cur_step = dt_step;
            if (cur_step * (i + 1) > dt)
                cur_step = dt - cur_step * i;

            FindCollisions();
            IntegrateForces(cur_step);
            InitializeCollisions();
            SolveCollisions();
            IntegrateVelocities(cur_step);
            CorrectPositions();
        }
        gravity.rotateBy(rotAngle);
    }

    private void FindCollisions () {
        contacts.clear();

        for (int i = 0; i < aliveIds.size(); i++) {
            PhysBox a = objs.get(aliveIds.get(i));

            for (int j = i + 1; j < aliveIds.size(); j++) {
                PhysBox b = objs.get(aliveIds.get(j));

                if (a.m_imass == PhysBox.INFINITE_MASS && b.m_imass == PhysBox.INFINITE_MASS) {
                    continue;
                } else if (Intersection.GeomBoxVsGeomBox(a, b)) {
                    Mainfold mainfold = new Mainfold(a, b);
                    if (mainfold.Solve()) {
                        contacts.add(mainfold);
                    }
                }
            }
        }
    }

    private void IntegrateForces (double dt) {
        for (int i = 0; i < aliveIds.size(); i++) {
            objs.get(aliveIds.get(i)).IntegrateForces(gravity, dt);
        }
    }

    private void InitializeCollisions () {
        for (int i = 0; i < contacts.size(); i++) {
            contacts.get(i).Init(gravity, EPSILON);
        }
    }

    private void SolveCollisions () {
        for (int i = 0; i < contacts.size(); i++) {
            contacts.get(i).Resolve(EPSILON);
        }
    }

    private void IntegrateVelocities (double dt) {
        for (int i = 0; i < aliveIds.size(); i++) {
            objs.get(aliveIds.get(i)).IntegrateVelocity(gravity, dt);
            objs.get(aliveIds.get(i)).ClearForces();
        }
    }

    private void CorrectPositions () {
        for (int i = 0; i < contacts.size(); i++) {
            contacts.get(i).PositionCorrection();
        }
    }
}
