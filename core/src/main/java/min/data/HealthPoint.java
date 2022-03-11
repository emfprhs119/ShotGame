package min.data;

public class HealthPoint {
    private float maxHealthPoint;
    private float healthPoint;

    public float getHealthPoint() {
        return healthPoint;
    }

    public float getCurrHealthPercent() {
        return healthPoint/maxHealthPoint;
    }
}
