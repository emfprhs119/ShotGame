package min.ui;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import min.manager.Assets;

public class MenuButton {
    List list;
    public MenuButton(){
        list = new List(Assets.uiskin);
        list.setItems("YES","NO","WHY");
    }

    public List getList() {
        return list;
    }
}
