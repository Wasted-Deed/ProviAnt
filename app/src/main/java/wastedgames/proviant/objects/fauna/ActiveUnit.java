package wastedgames.proviant.objects.fauna;

import android.graphics.Canvas;
import android.graphics.Paint;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.objects.Attire;
import wastedgames.proviant.objects.MovableUnit;
import wastedgames.proviant.objects.landscape.TileMap;

public class ActiveUnit extends MovableUnit {
    //TODO: Change x,y to Vector2 everywhere
    public ActiveUnit(float x, float y) {
        super(x, y);
        weapon = new Attire(1, 5);
    }

    public ActiveUnit(Vector2 pos) {
        this(pos.getX(), pos.getY());
    }

    @Override
    public void update() {
        super.update();
        isAttached = currentState == UnitState.CRAWL;
    }

    @Override
    public void draw(Canvas canvas, Paint paint, Vector2 camera) {
        super.draw(canvas, paint, camera);
        drawHp(canvas);
    }

    @Override
    public boolean checkIfLanded(TileMap map) {
        return super.checkIfLanded(map) || checkIfCanAttach();
    }
}
