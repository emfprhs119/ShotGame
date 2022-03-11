package min.data;

public class BulletData {
    float power = 5;
    float moveSpeed = 150;
    float finishTime;
    short target;

    public BulletData(short target, float power, float moveSpeed, float finishTime){
        this.target = target;
        this.power = power;
        this.moveSpeed = moveSpeed;
        this.finishTime = finishTime;
    }

    public short getTarget() {
        return target;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getFinishTime() {
        return finishTime;
    }

    public float getPower() {
        return power;
    }
}

