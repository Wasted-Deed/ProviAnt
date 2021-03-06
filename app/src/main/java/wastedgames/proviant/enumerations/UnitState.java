package wastedgames.proviant.enumerations;

public enum UnitState {
    IDLE, WORK, WALK, EXIST, CRAWL, DESTROYED;

    public enum Type {
        FLOOR, UNDERGROUND
    }

    public static Type getType(UnitState state) {
        return state.ordinal() <= EXIST.ordinal() ? Type.FLOOR : Type.UNDERGROUND;
    }

    public static boolean isFloor(UnitState state) {
        return getType(state) == Type.FLOOR;
    }
}
