package com.example.sandboxapp.math;


public class Vec2d {

    public double x;
    public double y;

    public Vec2d() { }

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2d(Vec2d v) {
        set(v);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vec2d v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void setZero() {
        x = 0;
        y = 0;
    }

    public double[] getComponents() {
        return new double[]{x, y};
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public double getLengthSq() {
        return (x * x + y * y);
    }

    public double distanceSq(double vx, double vy) {
        vx -= x;
        vy -= y;
        return (vx * vx + vy * vy);
    }

    public double distanceSq(Vec2d v) {
        double vx = v.x - this.x;
        double vy = v.y - this.y;
        return (vx * vx + vy * vy);
    }

    public double distance(double vx, double vy) {
        vx -= x;
        vy -= y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    public double distance(Vec2d v) {
        double vx = v.x - this.x;
        double vy = v.y - this.y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    public double getAngle() {
        return Math.atan2(y, x);
    }

    public void normalize() {
        double magnitude = getLength();
        x /= magnitude;
        y /= magnitude;
    }

    public Vec2d getNormalized() {
        double magnitude = getLength();
        return new Vec2d(x / magnitude, y / magnitude);
    }

    public static Vec2d toCartesian(double magnitude, double angle) {
        return new Vec2d(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }

    public void add(Vec2d v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void add(double vx, double vy) {
        this.x += vx;
        this.y += vy;
    }

    public static Vec2d add(Vec2d v1, Vec2d v2) {
        return new Vec2d(v1.x + v2.x, v1.y + v2.y);
    }

    public Vec2d getAdded(Vec2d v) {
        return new Vec2d(this.x + v.x, this.y + v.y);
    }

    public void subtract(Vec2d v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void subtract(double vx, double vy) {
        this.x -= vx;
        this.y -= vy;
    }

    public static Vec2d subtract(Vec2d v1, Vec2d v2) {
        return new Vec2d(v1.x - v2.x, v1.y - v2.y);
    }

    public Vec2d getSubtracted(Vec2d v) {
        return new Vec2d(this.x - v.x, this.y - v.y);
    }

    public void multiply(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    public Vec2d getMultiplied(double scalar) {
        return new Vec2d(x * scalar, y * scalar);
    }

    public void divide(double scalar) {
        x /= scalar;
        y /= scalar;
    }

    public Vec2d getDivided(double scalar) {
        return new Vec2d(x / scalar, y / scalar);
    }

    public Vec2d getPerp() {
        return new Vec2d(-y, x);
    }

    public double dot(Vec2d v) {
        return (this.x * v.x + this.y * v.y);
    }

    public double dot(double vx, double vy) {
        return (this.x * vx + this.y * vy);
    }

    public static double dot(Vec2d v1, Vec2d v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public double cross(Vec2d v) {
        return (this.x * v.y - this.y * v.x);
    }

    public double cross(double vx, double vy) {
        return (this.x * vy - this.y * vx);
    }

    public static double cross(Vec2d v1, Vec2d v2) {
        return (v1.x * v2.y - v1.y * v2.x);
    }

    public double project(Vec2d v) {
        return (this.dot(v) / this.getLength());
    }

    public double project(double vx, double vy) {
        return (this.dot(vx, vy) / this.getLength());
    }

    public static double project(Vec2d v1, Vec2d v2) {
        return (dot(v1, v2) / v1.getLength());
    }

    public Vec2d getProjectedVector(Vec2d v) {
        return this.getNormalized().getMultiplied(this.dot(v) / this.getLength());
    }

    public Vec2d getProjectedVector(double vx, double vy) {
        return this.getNormalized().getMultiplied(this.dot(vx, vy) / this.getLength());
    }

    public static Vec2d getProjectedVector(Vec2d v1, Vec2d v2) {
        return v1.getNormalized().getMultiplied(Vec2d.dot(v1, v2) / v1.getLength());
    }

    public void rotateBy(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double rx = x * cos - y * sin;
        y = x * sin + y * cos;
        x = rx;
    }

    public Vec2d getRotatedBy(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vec2d(x * cos - y * sin, x * sin + y * cos);
    }

    public void rotateTo(double angle) {
        set(toCartesian(getLength(), angle));
    }

    public Vec2d getRotatedTo(double angle) {
        return toCartesian(getLength(), angle);
    }

    public void reverse() {
        x = -x;
        y = -y;
    }

    public Vec2d getReversed() {
        return new Vec2d(-x, -y);
    }

    @Override
    public Vec2d clone() {
        return new Vec2d(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Vec2d) {
            Vec2d v = (Vec2d) obj;
            return (x == v.x) && (y == v.y);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vec2d[" + x + ", " + y + "]";
    }
}
