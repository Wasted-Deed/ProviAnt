package wastedgames.proviant.objects.landscape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import java.util.HashMap;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.TileState;
import wastedgames.proviant.enumerations.TileType;
import wastedgames.proviant.interfaces.Destroyable;
import wastedgames.proviant.interfaces.Drawable;
import wastedgames.proviant.maintenance.ThreadSolver;
import wastedgames.proviant.objects.Appearance;

public abstract class Tile implements Destroyable, Drawable {
    protected HashMap<TileState, Appearance> appearance;
    protected TileState currentState;
    protected TileType type;

    protected int x;
    protected int y;

    protected int durability;
    protected int damage;

    protected boolean isSolid;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        appearance = new HashMap<>();
        damage = 0;
    }

    public Tile(int x, int y, int size) {
        this(x * size, y * size);
    }

    @Override
    public void damage(int damageFrequency) {
        if (ThreadSolver.CURRENT_FRAME % damageFrequency == 0) {
            damage++;
        }
    }

    public boolean isDestroyed() {
        return damage >= durability;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return getCurrentFrame().getWidth();
    }

    @Override
    public int getHeight() {
        return getCurrentFrame().getHeight();
    }

    protected Bitmap getCurrentFrame() {
        return appearance.get(currentState).getCurrentFrame();
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Vector2 camera) {
        canvas.drawBitmap(appearance.get(currentState).getCurrentFrame(), x, y, paint);
    }

    @NonNull
    @Override
    public String toString() {
        return "Tile with type: " + type + " at position: " + "[" + x + ", " + y + "]";
    }
}
