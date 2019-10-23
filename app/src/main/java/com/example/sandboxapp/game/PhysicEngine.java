package com.example.sandboxapp.game;

import android.util.Log;

import com.example.sandboxapp.game_objects.SandParticle;
import com.example.sandboxapp.math.Vec2d;
import com.example.sandboxapp.physics.GeomBox;
import com.example.sandboxapp.physics.Intersection;
import com.example.sandboxapp.physics.Mainfold;
import com.example.sandboxapp.physics.PhysBox;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.ceil;

public class PhysicEngine {
    private ArrayList<PhysBox> m_objs = new ArrayList<>();
    private ArrayList<Mainfold> m_contacts = new ArrayList<>();

    private Vec2d m_gravity = new Vec2d(0, -0.5);

    private double EPSILON = 0.0001;

    private double m_maxdt = 0.01;

    private GeomBox m_gameBox;

    double m_rotAngle;
    double m_prevAngle;

    public PhysicEngine() {
        //this.m_gameBox = m_gameBox;
    }

    public void SetGameBox(GeomBox gameBox) {
        this.m_gameBox = gameBox;
    }

    public void AddPhysBox(PhysBox box) {
        m_objs.add(box);
    }


    public void Update(double dt, double rotAngle) {
        int steps;
        double dt_step;

        this.m_rotAngle = rotAngle + Math.PI;

        for (int i = 0; i < m_objs.size(); i++) {
            if (m_objs.get(i).m_type == PhysBox.Type.SAND && !Intersection.GeomBoxVsGeomBox(m_gameBox, m_objs.get(i))){
                Vec2d center = m_objs.get(i).getCenter();
                center.rotateBy(m_prevAngle - this.m_rotAngle);
                m_objs.get(i).setCenter(center);

                m_objs.get(i).m_velocity.rotateBy(m_prevAngle - this.m_rotAngle);
            }
        }
        //m_gravity.rotateBy(-m_rotAngle);

        if (dt <= m_maxdt) {
            steps = 1;
            dt_step = dt;
        } else {
            dt_step = m_maxdt;
            steps = (int) ceil((dt / m_maxdt));
        }
        Log.d("profile/phys", String.format("steps = %d", steps));


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

        m_prevAngle = this.m_rotAngle;
        //m_gravity.rotateBy(m_rotAngle);
    }

    private void FindCollisions() {
        m_contacts.clear();

        for (int i = 0; i < m_objs.size(); i++) {
            PhysBox a = m_objs.get(i);

            for (int j = i + 1; j < m_objs.size(); j++) {
                PhysBox b = m_objs.get(j);

                if (a.m_imass == PhysBox.INFINITE_MASS && b.m_imass == PhysBox.INFINITE_MASS) {
                    continue;
                } else if (Intersection.GeomBoxVsGeomBox(a, b)) {
                    Mainfold mainfold = new Mainfold(a, b);
                    if (mainfold.Solve()) {
                        m_contacts.add(mainfold);
                    }
                }
            }
        }
    }

    private void IntegrateForces(double dt) {
        for (int i = 0; i < m_objs.size(); i++) {
            double r_angle = Intersection.GeomBoxVsGeomBox(m_gameBox, m_objs.get(i)) ? m_rotAngle : m_rotAngle;
            m_gravity.rotateBy(-r_angle);
            m_objs.get(i).IntegrateForces(m_gravity, dt);
            m_gravity.rotateBy(r_angle);

        }
    }

    private void InitializeCollisions() {
        for (int i = 0; i < m_contacts.size(); i++) {
            m_gravity.rotateBy(-m_rotAngle);
            m_contacts.get(i).Init(m_gravity, EPSILON);

            m_gravity.rotateBy(m_rotAngle);
        }
    }

    private void SolveCollisions() {
        for (int i = 0; i < m_contacts.size(); i++) {
            m_contacts.get(i).Resolve(EPSILON);
        }
    }

    private void IntegrateVelocities(double dt) {
        for (int i = 0; i < m_objs.size(); i++) {
            double r_angle = Intersection.GeomBoxVsGeomBox(m_gameBox, m_objs.get(i)) ? m_rotAngle : m_rotAngle;
            m_gravity.rotateBy(-r_angle);
            m_objs.get(i).IntegrateVelocity(m_gravity, dt);
            m_gravity.rotateBy(r_angle);
            m_objs.get(i).ClearForces();
        }
    }

    private void CorrectPositions() {
        for (int i = 0; i < m_contacts.size(); i++) {
            m_contacts.get(i).PositionCorrection();
        }
    }
}
