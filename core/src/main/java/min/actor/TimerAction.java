package min.actor;


import com.badlogic.gdx.scenes.scene2d.Action;

public class TimerAction extends Action {
    boolean isLoop;
    float stateTime;
    float finishTime;
    ICallback callback;

    public TimerAction(float finishTime, boolean isLoop, ICallback callback){
        this.finishTime = finishTime;
        this.isLoop = isLoop;
        this.callback = callback;
    }

    public void restart () {
        stateTime = 0;
    }

    @Override
    public boolean act(float delta) {
        if (stateTime >= finishTime) {
            callback.exec();
            if (isLoop){
                stateTime = 0f;
            }
        }else
            stateTime+=delta;
        return false;
    }
}