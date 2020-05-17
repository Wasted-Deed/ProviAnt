package wastedgames.proviant.engine;

import static java.lang.Math.PI;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

public class MyMath {
    public static final float INFELICITY = 0.5f;
    public static float triangleArea(Vector2 p1, Vector2 p2, Vector2 p3) {
        Vector2 side1 = new Vector2(p2.getX(), p2.getY());
        Vector2 side2 = new Vector2(p3.getX(), p3.getY());
        side1.subtractVector2(p1);
        side2.subtractVector2(p1);
        float len1 = side1.getLength();
        float len2 = side2.getLength();
        if (len1 == 0 || len2 == 0) {
            return 0;
        }
        float angleProduct = scalarProduct(side1, side2) / (len1 * len2);
        angleProduct = (float) sqrt(1 - angleProduct * angleProduct);
        return len1 * len2 * angleProduct / 2;
    }

    public static Vector2 getNewPoint(Vector2 old, float angle) {
        float cos = (float) cos(angle);
        float sin = (float) sin(angle);
        float tan = (float) tan(angle);
        float newX = (old.getX() + old.getY() * tan) / (cos + sin * tan);
        float newY = (old.getY() - newX * sin) / cos;
        return new Vector2(newX, newY);
    }

    public static float scalarProduct(Vector2 v1, Vector2 v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    public static float getAngle(float cos, float sin) {
        return (float) (sin < 0 ? 2 * PI - acos(cos) : acos(cos));
    }

    public static float getDist(Vector2 point1, Vector2 point2) {
        Vector2 newVector = new Vector2(point1);
        newVector.subtractVector2(point2);
        return newVector.getLength();
    }

    public static float getDist(float x1, float y1, float x2, float y2) {
        return getDist(new Vector2(x1, y1), new Vector2(x2, y2));
    }
}
