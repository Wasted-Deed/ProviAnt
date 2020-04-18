package wastedgames.proviant.engine;

import androidx.annotation.NonNull;

public class Vector2 {
    private float x;
    private float y;

    public Vector2(float x, float y) {
        setCoordinates(x, y);
    }

    public Vector2(Vector2 vector) {
        setCoordinates(vector);
    }

    public void addVector2(Vector2 toAdd) {
        addVector2(toAdd.getX(), toAdd.getY());
    }

    public void addVector2(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void multiplyVector(float x, float y) {
        this.x *= x;
        this.y *= y;
    }

    public void setLength(float length) {
        float coefficient = length / getLength();
        multiplyVector(coefficient, coefficient);
    }

    public void subtractVector2(Vector2 toSubtract) {
        addVector2(-toSubtract.getX(), -toSubtract.getY());
    }

    public Vector2 addedCopy(Vector2 toAdd) {
        return addedCopy(toAdd.getX(), toAdd.getY());
    }

    public Vector2 addedCopy(float x, float y) {
        Vector2 res = new Vector2(this.x, this.y);
        res.addVector2(x, y);
        return res;

    }

    public Vector2 subtractedCopy(Vector2 toSubtract) {
        return addedCopy(-toSubtract.getX(), -toSubtract.getY());
    }

    public static int getDistance(Vector2 a, Vector2 b) {
        return (int) Math.sqrt(Math.pow(a.getX() - b.getX(), 2)
                + Math.pow(a.getY() - b.getY(), 2));
    }


    public float getLength() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public int getAngleWithX() {
        float cos = x / getLength();
        int ang0to180 = (int) (Math.acos(cos) * 180 / Math.PI);
        if (y > 0) {
            return 360 - ang0to180;
        }
        return ang0to180;
    }

    public Vector2 dividedCopy(float scalar) {
        if (scalar == 0) {
            return new Vector2(0, 0);
        }
        return new Vector2(x / scalar, y / scalar);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setCoordinates(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setCoordinates(Vector2 vector) {
        setCoordinates(vector.getX(), vector.getY());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @NonNull
    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }
}
