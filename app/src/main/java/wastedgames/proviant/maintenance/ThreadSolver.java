package wastedgames.proviant.maintenance;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.Layout;
import wastedgames.proviant.layouts.GameField;


public class ThreadSolver extends SurfaceView implements SurfaceHolder.Callback {
    public static Vector2 TOUCH;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int CURRENT_FRAME;
    public static boolean IS_TOUCHING;

    private MainThread thread;
    private GameField gameField;
    private Layout currentLayout;

    private ArrayList<Integer> touchIDs;

    public ThreadSolver(Context context) {
        super(context);
        configureStartingParameters();
    }

    private void configureStartingParameters() {
        getHolder().addCallback(this);
        setFocusable(true);
        currentLayout = Layout.GAME_FIELD;
        CURRENT_FRAME = 1;
        IS_TOUCHING = false;
        TOUCH = new Vector2(0, 0);
        touchIDs = new ArrayList<>();
    }

    private void onSurfaceCreated() {
        SCREEN_WIDTH = getWidth();
        SCREEN_HEIGHT = getHeight();
        gameField = new GameField(320, 100);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        onSurfaceCreated();
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            thread.setRunning(false);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(canvas == null){
            return;
        }
        super.draw(canvas);
        switch (currentLayout) {
            case GAME_FIELD:
                gameField.draw(canvas);
                break;
            case MENU:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                IS_TOUCHING = false;
                touchIDs.clear();
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                touchIDs.remove(Integer.valueOf(event.getActionIndex()));
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                touchIDs.add(event.getActionIndex());
                break;

        }
        IS_TOUCHING = true;
        int lastTouch = touchIDs.get(touchIDs.size() - 1);
        if (lastTouch < event.getPointerCount()) {
            TOUCH.setX((int) event.getX(lastTouch));
            TOUCH.setY((int) event.getY(lastTouch));
        }
        return true;
    }

    public void update() {
        switch (currentLayout) {
            case GAME_FIELD:
                gameField.update();
                break;
            case MENU:
                break;
        }
        CURRENT_FRAME++;
        if (CURRENT_FRAME == MainThread.MAX_FPS) {
            CURRENT_FRAME = 1;
        }
    }

}
