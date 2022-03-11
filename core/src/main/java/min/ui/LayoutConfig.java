package min.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Timer;
import min.manager.Assets;

import java.sql.Timestamp;

public class LayoutConfig {
    static public Group generateGrid(ItemHolder[] holders, int rows,int cols){
        Skin skin = Assets.uiskin;

        Table window = new Table(skin);
        VerticalGroup vg = new VerticalGroup();
        for (int i = 0; i < rows; i++) {
            HorizontalGroup hg = new HorizontalGroup();
            for (int j = 0; j < cols; j++) {

                holders[i*cols+j] = new ItemHolder(36);
                holders[i*cols+j].setName(i+"_"+j);
                hg.addActor(holders[i*cols+j]);
            }
            hg.pack();
            vg.addActor(hg);
        }
        vg.pack();
        window.add(vg);
        window.pack();
        return window;
    }
}
