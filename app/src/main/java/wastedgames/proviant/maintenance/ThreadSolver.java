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
    public static Vector2 FIRST_TOUCH;
    public static Vector2 LAST_TOUCH;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int CURRENT_FRAME;
    public static boolean IS_TOUCHING;
    public static boolean HAD_TOUCHED;
    private MainThread thread;
    private GameField gameField;
    private Layout currentLayout;

    private static ArrayList<Integer> touchIDs;

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
        HAD_TOUCHED = false;
        FIRST_TOUCH = new Vector2(0, 0);
        LAST_TOUCH = new Vector2(0, 0);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (canvas == null) {
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
                HAD_TOUCHED = false;
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
        int firstTouch = touchIDs.get(0);
        int lastTouch = touchIDs.get(touchIDs.size() - 1);
        if (firstTouch >= 0 && firstTouch < event.getPointerCount()) {
            FIRST_TOUCH.setX((int) event.getX(firstTouch));
            FIRST_TOUCH.setY((int) event.getY(firstTouch));
        }
        if (touchIDs.size() > 1 && lastTouch < event.getPointerCount()) {
            LAST_TOUCH.setX((int) event.getX(lastTouch));
            LAST_TOUCH.setY((int) event.getY(lastTouch));
        }
        return true;
    }

    public static int getTouchCount() {
        return touchIDs.size();
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
