package wastedgames.proviant.objects;

import wastedgames.proviant.engine.Vector2;

public class CollisionMask {
    Vector2 leftTop;
    Vector2 rightBottom;

    public CollisionMask(Vector2 leftTop, Vector2 rightBottom) {
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
    }

    public CollisionMask(int x1, int y1, int x2, int y2) {
        this(new Vector2(x1, y1), new Vector2(x2, y2));
    }

    public Vector2 getLeftTop() {
        return leftTop;
    }

    public Vector2 getRightBottom() {
        return rightBottom;
    }
}
