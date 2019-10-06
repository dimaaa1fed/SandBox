package com.example.sandboxapp.game;

import android.util.Log;

import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.Intersection;
import com.example.sandboxapp.physics.Mainfold;
import com.example.sandboxapp.physics.PhysBox;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.ceil;

public class PhysicEngine {
    private ArrayList<PhysBox> objs = new ArrayList<>();
    private ArrayList<Mainfold> contacts = new ArrayList<>();

    private Vec2d gravity = new Vec2d(0, -0.5);

    private double EPSILON = 0.0001;

    private double max_dt = 0.01;

    private GeomBox game_box;

    double rotAngle;

    public PhysicEngine() {
        //this.game_box = game_box;
    }

    public void setGame_box(GeomBox game_box) {
        this.game_box = game_box;
    }

    public void AddPhysBox(PhysBox box) {
        objs.add(box);
    }


    public void Update(double dt, double rotAngle) {
        int steps;
        double dt_step;

        this.rotAngle = rotAngle + Math.PI;

        //gravity.rotateBy(-rotAngle);

        if (dt <= max_dt) {
            steps = 1;
            dt_step = dt;
        } else {
            dt_step = max_dt;
            steps = (int) ceil((dt / max_dt));
        }

        for (int i = 0; i < steps; i++) {
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
        //gravity.rotateBy(rotAngle);
    }

    private void FindCollisions() {
        contacts.clear();

        for (int i = 0; i < objs.size(); i++) {
            PhysBox a = objs.get(i);

            for (int j = i + 1; j < objs.size(); j++) {
                PhysBox b = objs.get(j);

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

    private void IntegrateForces(double dt) {
        for (int i = 0; i < objs.size(); i++) {
            double r_angle = Intersection.GeomBoxVsGeomBox(game_box, objs.get(i)) ? rotAngle : rotAngle;
            gravity.rotateBy(-r_angle);
            objs.get(i).IntegrateForces(gravity, dt);
            gravity.rotateBy(r_angle);

        }
    }

    private void InitializeCollisions() {
        for (int i = 0; i < contacts.size(); i++) {
            gravity.rotateBy(-rotAngle);
            contacts.get(i).Init(gravity, EPSILON);

            gravity.rotateBy(rotAngle);
        }
    }

    private void SolveCollisions() {
        for (int i = 0; i < contacts.size(); i++) {
            contacts.get(i).Resolve(EPSILON);
        }
    }

    private void IntegrateVelocities(double dt) {
        for (int i = 0; i < objs.size(); i++) {
            double r_angle = Intersection.GeomBoxVsGeomBox(game_box, objs.get(i)) ? rotAngle : rotAngle;
            gravity.rotateBy(-r_angle);
            objs.get(i).IntegrateVelocity(gravity, dt);
            gravity.rotateBy(r_angle);
            objs.get(i).ClearForces();
        }
    }

    private void CorrectPositions() {
        for (int i = 0; i < contacts.size(); i++) {
            contacts.get(i).PositionCorrection();
        }
    }


}
