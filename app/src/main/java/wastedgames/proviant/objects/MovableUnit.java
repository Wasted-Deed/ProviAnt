package wastedgames.proviant.objects;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.interfaces.Movable;
import wastedgames.proviant.interfaces.Portable;

public abstract class MovableUnit extends AbstractUnit implements Movable {
    Portable pickedObject;

    protected int MAX_JUMP_HEIGHT;

    protected float speed;
    protected float currentSpeed;
    protected int jumpSpeed;
    protected int vision;
    private int currentJumpHeight;
    private boolean isJumping;


    public MovableUnit(int x, int y) {
        super(x, y);
        isJumping = false;
        currentJumpHeight = 0;
        currentSpeed = 0;
    }

    private void jump() {
        y -= jumpSpeed;
        currentJumpHeight += jumpSpeed;
        if (currentJumpHeight >= MAX_JUMP_HEIGHT) {
            setJumping(false);
            currentSpeed = 0;
        }
    }

    @Override
    public void move(Vector2 direction) {
        isMirrored = direction.getX() < 0;
        if (isJumping) {
            jump();
            return;
        }
        currentSpeed = direction.getX();
        x += direction.getX();
        y += direction.getY();
    }

    @Override
    public void move(AbstractUnit danger) {

    }

    public float getSpeed() {
        return speed;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
        currentJumpHeight = 0;
    }

    @Override
    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public void setPickedObject(Portable pickedObject) {
        throwPickedObject();
        this.pickedObject = pickedObject;
        pickedObject.setHolder(this);
        pickedObject.setPickUp(true);
    }

    public void throwPickedObject() {
        if (this.pickedObject != null) {
            this.pickedObject.setHolder(null);
            this.pickedObject.setPickUp(false);
        }
    }
}
