package min.data;

import com.badlogic.gdx.Gdx;

public class State {
    Status status;
    float maxHealthPoint;
    float healthPoint;

    public void init(float maxHealthPoint){
        this.maxHealthPoint = maxHealthPoint;
        healthPoint = maxHealthPoint;
    }

    public float getHealthPointPercent(){
        return healthPoint/maxHealthPoint;
    }

    public void damage(float power){
        Gdx.app.log("HP", healthPoint +"_"+power);
        healthPoint -= power;
        if (healthPoint <0){
            healthPoint =0;
        }
    }

    public void heal(float power){
        healthPoint += power;
        if (healthPoint >status.getMaxHp()){
            healthPoint = status.getMaxHp();
        }
    }

    public float getHealthPoint() {
        return healthPoint;
    }
    public boolean isDead() {
        return healthPoint ==0;
    }
}
