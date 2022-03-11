package min.actor.component;

public class ActionComponent {
    public boolean isEnableMove() {

        return true;
    }
    StateAction stateAction;
    ActionComponent(){
        stateAction = StateAction.IDLE;
    }
    public enum StateAction{IDLE,WALK,DASH,DAMAGE,DEAD, MINING,CRAFT, UNKNOWNS, ATTACK};
    boolean isAction;
}
