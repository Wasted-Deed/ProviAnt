package wastedgames.proviant.maintenance;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    public static final int MAX_FPS;
    private static final long targetTime;
    private final SurfaceHolder holder;
    private final ThreadSolver solver;
    private boolean isRunning;

    static {
        MAX_FPS = 32;
        targetTime = 1000_000_000 / MAX_FPS;
    }

    public MainThread(SurfaceHolder holder, ThreadSolver solver) {
        this.holder = holder;
        this.solver = solver;
        isRunning = false;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        while (isRunning) {
            long actionStart = System.nanoTime();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                synchronized (holder) {
                    solver.update();
                    solver.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        holder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            long waitTime = targetTime - (System.nanoTime() - actionStart);
            waitTime /= 1000_000;
            try {
                if (waitTime > 0) {
                    sleep(waitTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
