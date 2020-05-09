package wastedgames.proviant.objects;

import wastedgames.proviant.engine.Vector2;
import wastedgames.proviant.enumerations.UnitState;
import wastedgames.proviant.interfaces.Movable;
import wastedgames.proviant.interfaces.Portable;
import wastedgames.proviant.layouts.GameField;

public abstract class MovableUnit extends AbstractUnit implements Movable {
    Portable pickedObject;

    protected int MAX_JUMP_HEIGHT;

    protected Vector2 aim;
    protected float speed;
    protected float currentSpeed;
    protected int jumpSpeed;
    protected int vision;
    protected int damage;
    protected boolean isAttached;

    private int currentJumpHeight;
    private boolean isJumping;

    public MovableUnit(float x, float y) {
        super(x, y);
        isJumping = false;
        currentJumpHeight = 0;
        currentSpeed = 0;
        hp = 1;
        aim = new Vector2(x, y);
    }

    private void jump() {
        pos.addVector2(0, -jumpSpeed);
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
        pos.addVector2(direction);
    }

    @Override
    public void update() {
        super.update();
        if (!hasCome()) {
            move(new Vector2(aim.getX() > pos.getX() ? speed : -speed, 0));
        }
    }

    public boolean hasCome() {
        return Vector2.getDistance(aim, pos) < speed;
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

    public Portable getPickedObject() {
        return pickedObject;
    }

    public void throwPickedObject() {
        if (this.pickedObject != null) {
            this.pickedObject.setHolder(null);
            this.pickedObject.setPickUp(false);
        }
    }

    public int getDamage() {
        return damage;
    }

    public boolean checkIfLandedOnBlock() {
        return super.checkIfLanded();
    }

    public boolean checkIfCanAttach() {
        return getY() >= GameField.FLOOR_Y;
    }

    public boolean isAttached() {
        return isAttached || !UnitState.isFloor(getCurrentState());
    }

    public void setAim(float x, float y) {
        aim.setCoordinates(x, y);
    }
}
