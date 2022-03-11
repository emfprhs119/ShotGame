package min.data;

public class Status {
    float maxHp;
    float moveSpeed = 100f;
    float dashSpeed = 200f;

    public float getDashSpeed() {
        return dashSpeed;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float speed) {
        moveSpeed = speed;
    }

    public float getMaxHp() {
        return maxHp;
    }
}
