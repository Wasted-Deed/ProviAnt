package wastedgames.proviant.objects;

import java.util.function.Function;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.objects.landscape.TileMap;

public class CollisionMask {
    private Vector2 leftTop;
    private Vector2 rightBottom;
    private Vector2 leftBottom;
    private Vector2 rightTop;

    public CollisionMask(Vector2 leftTop, Vector2 rightBottom) {
        this.leftTop = leftTop;
        this.rightBottom = rightBottom;
        leftBottom = new Vector2(leftTop.getX(), rightBottom.getY());
        rightTop = new Vector2(rightBottom.getX(), leftTop.getY());
    }

    public CollisionMask(float x1, float y1, float x2, float y2) {
        this(new Vector2(x1, y1), new Vector2(x2, y2));
    }

    public boolean checkBottom(Vector2 pos, Function<Vector2, Boolean> check) {
        int startX = (int) getLeftTop().getX();
        int endX = (int) getRightBottom().getX();
        for (int i = startX; i < endX; i += TileMap.TILE_SIZE) {
            Vector2 cur = pos.addedCopy(i, 0);
            if (check.apply(cur)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkFront(Vector2 pos, Function<Vector2, Boolean> check) {
        Vector2 step = new Vector2(rightTop);
        step.subtractVector2(rightBottom);
        int count = (int) step.getLength();
        step.setLength(1);
        Vector2 cur = new Vector2(rightBottom);
        for (int i = 0; i < count; i++) {
            if (check.apply(cur.addedCopy(pos))) {
                return true;
            }
            cur.addVector2(step);
        }
        return false;
    }

    public void setRotated(int angle) {
        leftTop.rotate(angle);
        rightBottom.rotate(angle);
        leftBottom.rotate(angle);
        rightTop.rotate(angle);
    }

    public Vector2 getLeftTop() {
        return leftTop;
    }

    public Vector2 getRightBottom() {
        return rightBottom;
    }

    public Vector2 getLeftBottom() {
        return leftBottom;
    }

    public Vector2 getRightTop() {
        return rightTop;
    }
}
