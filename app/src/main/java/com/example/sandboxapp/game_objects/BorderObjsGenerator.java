package com.example.sandboxapp.game_objects;

import android.util.Pair;

import com.example.sandboxapp.math.Vec2d;

import java.util.ArrayList;
import java.util.HashSet;

public class BorderObjsGenerator {

    static public ArrayList<StaticRect> Generate (double wallLen, double wallWidth) {

        ArrayList<StaticRect> walls = new ArrayList<>();
//-0.9f / Math.sqrt(2)


        Vec2d c1 = new Vec2d(0, wallLen / 2 - wallWidth / 2);
        Vec2d c2 = new Vec2d(wallLen / 2 - wallWidth / 2, 0);
        Vec2d c3 = new Vec2d(0, -wallLen / 2 + wallWidth / 2);
        Vec2d c4 = new Vec2d(-wallLen / 2 + wallWidth / 2, 0);

        /*   wallLen
        *   ::::c1:::
        *   ::     ::
            c4     c2
        *   ::     ::
        *   ::::c3:::
        *           wallWidth
        * */
        walls.add(new StaticRect(c1, wallLen, wallWidth));
        walls.add(new StaticRect(c2, wallWidth, wallLen));
        walls.add(new StaticRect(c3, wallLen * 0.75, wallWidth));
        walls.add(new StaticRect(c4, wallWidth, wallLen));

        return walls;
    }

    static public ArrayList<StaticRect> GenerateByField (double wallLen, String field, int size) {

        double wallWidth = wallLen / size;
        Vec2d start = new Vec2d(-wallLen / 2 + wallWidth / 2, wallLen / 2 - wallWidth / 2);
        ArrayList<StaticRect> walls = new ArrayList<>();

        HashSet<Pair<Integer, Integer>> used = new HashSet<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field.charAt(i * size + j) == '*' && !used.contains(new Pair<>(j, i)))
                {
                    int hor_cnt = 1, ver_cnt = 1;

                    int k = j - 1;
                    while (k >= 0 && field.charAt(i * size + k) == '*' &&
                            !used.contains(new Pair<>(k, i))) {
                        k--;
                        hor_cnt++;
                    }
                    k = j + 1;
                    while (k < size && field.charAt(i * size + k) == '*' &&
                        !used.contains(new Pair<>(k, i))) {
                        k++;
                        hor_cnt++;
                    }

                    k = i - 1;
                    while (k >= 0 && field.charAt(k * size + j) == '*' &&
                            !used.contains(new Pair<>(j, k))) {
                        k--;
                        ver_cnt++;
                    }

                    k = i + 1;
                    while (k < size && field.charAt(k * size + j) == '*' &&
                            !used.contains(new Pair<>(j, k))) {
                        k++;
                        ver_cnt++;
                    }

                    if (hor_cnt > ver_cnt)
                    {
                        Pair<Integer, Integer> min = new Pair<>(j, i);
                        Pair<Integer, Integer> max = new Pair<>(j, i);
                        k = j - 1;
                        while (k >= 0 && field.charAt(i * size + k) == '*' &&
                                !used.contains(new Pair<>(k, i))) {
                            min = new Pair<>(k, i);
                            used.add(min);
                            k--;
                        }
                        k = j + 1;
                        while (k < size && field.charAt(i * size + k) == '*' &&
                                !used.contains(new Pair<>(k, i))) {
                            max = new Pair<>(k, i);
                            used.add(max);
                            k++;
                        }
                        // add wall
                        Vec2d pos = new Vec2d((double)min.first, -(double)min.second).
                                getAdded(new Vec2d((double)max.first, -(double)max.second));
                        pos.multiply(wallWidth/ 2);

                        Vec2d curPos = start.getAdded(new Vec2d(pos.x, pos.y));
                        walls.add(new StaticRect(curPos, wallWidth * (Math.abs(max.first - min.first) + 1), wallWidth));
                    }
                    else
                    {
                        Pair<Integer, Integer> min = new Pair<>(j, i);
                        Pair<Integer, Integer> max = new Pair<>(j, i);
                        k = i - 1;
                        while (k >= 0 && field.charAt(k * size + j) == '*' &&
                                !used.contains(new Pair<>(j, k))) {
                            min = new Pair<>(j, k);
                            used.add(min);
                            k--;
                        }

                        k = i + 1;
                        while (k < size && field.charAt(k * size + j) == '*' &&
                                !used.contains(new Pair<>(j, k))) {
                            max = new Pair<>(j, k);
                            used.add(max);
                            k++;
                        }
                        // add wall
                        Vec2d pos = new Vec2d((double)min.first, -(double)min.second).
                                getAdded(new Vec2d((double)max.first, -(double)max.second));
                        pos.multiply(wallWidth / 2);

                        Vec2d curPos = start.getAdded(new Vec2d(pos.x, pos.y));
                        walls.add(new StaticRect(curPos, wallWidth, wallWidth * (Math.abs(max.second - min.second) + 1)));
                    }
                }
            }
        }

        return walls;
    }

}
