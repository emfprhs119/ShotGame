package min.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.HashMap;

/**
 * Class to load and handle all the assets
 *
 * @author Basim
 *
 */
public class Assets {
    /* for Debug (Time) */
    public static Drawable blankBackground;
    public static Drawable selectBackground;


    /*-----------------*/


    /* Global constants */

    public static final int FONT_FILE_SIZE = 200;
    /*-----------------*/

    public static class Images {
        public static final String BACKGROUND = "images/bg.jpg";
        public static final String SENSOR_TILE = "images/quad_grey.png";
        public static final String WALL_TILE = "images/quad.png";
        public static final String TRANSITION_BACKGROUND = "images/transition-bg.png";
        public static final String TRAJECTORY = "images/trajectory.png";
        public static final String BLOCKED = "images/blocked.png";
        public static final String SETTINGS_BACKGROUND = "images/settings_bg.png";
        public static final String NEXT_LEVEL_DISABLED = "buttons/next_level_disabled.png";
        public static final String LEVEL_SELECT_DISABLED = "buttons/level_select_disabled.png";
        public static final String LASER_BASE = "images/laser-base.png";
        public static final String LASER_HEAD = "images/laser-head.png";
        public static final String STAR = "images/star.png";
        public static final String EMPTY_STAR = "images/empty_star.png";
    }
    private static final String[] _Images = {
            /*
            Images.BACKGROUND, Images.SENSOR_TILE,
            Images.WALL_TILE, Images.TRANSITION_BACKGROUND,
            Images.TRAJECTORY, Images.NEXT_LEVEL_DISABLED,
            Images.BLOCKED, Images.SETTINGS_BACKGROUND,
            Images.LASER_BASE, Images.LASER_HEAD,
            Images.LEVEL_SELECT_DISABLED, Images.STAR,
            Images.EMPTY_STAR
             */
    };

    public static class Debugs{
        public static final String mines_resources = "Downloads/mine-resources.png";
        public static final Rectangle mines_resources_ret = new Rectangle(16,16,4,4);
        public static final String mines = "Downloads/mines.png";
        public static final Rectangle mines_ret = new Rectangle(54,54,23,15);
        public static final String icon = "Downloads/IconsPJ.png";
        public static final Rectangle icon_ret = new Rectangle(32,32,6,4);
        public static final String items = "Downloads/items.png";
        public static final Rectangle items_ret = new Rectangle(32,32,5,7);
        public static final String runes = "Downloads/Runes1.png";
        public static final Rectangle runes_ret = new Rectangle(61,61,4,5);
        public static final String jewels = "Downloads/Misc_Jewels.png";
        public static final Rectangle jewels_ret = new Rectangle(61,61,1,3);
    }
    private static final String[] _Debugs = {
            Debugs.mines_resources,
            Debugs.mines,
            Debugs.icon,
            Debugs.items,
            Debugs.runes,
            Debugs.jewels
    };

    public static class Buttons {
        public static final String MENU_PLAY = "buttons/menu_play";
        public static final String NEXT_LEVEL = "buttons/next_level";
        public static final String PREVIOUS_LEVEL = "buttons/previous_level";
        public static final String REPLAY_LEVEL = "buttons/replay_level";
        public static final String BACK_MENU = "buttons/back_menu";
        public static final String PLAIN = "buttons/level_select";
        public static final String PAUSE = "buttons/pause";
        public static final String SETTINGS = "buttons/settings";
        public static final String CROSS = "buttons/cross";
        public static final String BACK = "buttons/back";
        public static final String RED = "buttons/red";
        public static final String GREEN = "buttons/green";
    }
    private static final String[] _Buttons = {
            /*
            Buttons.MENU_PLAY, Buttons.NEXT_LEVEL,
            Buttons.REPLAY_LEVEL, Buttons.BACK_MENU,
            Buttons.PLAIN, Buttons.PAUSE,
            Buttons.SETTINGS, Buttons.CROSS,
            Buttons.BACK, Buttons.PREVIOUS_LEVEL,
            Buttons.RED, Buttons.GREEN
            */
    };

    public static class FontSizes {
        public static final int TWENTY = 20;
        public static final int TWENTY_FIVE = 25;
        public static final int THIRTY = 30;
        public static final int FORTY = 40;
        public static final int FIFTY = 50;
        public static final int HUNDRED = 100;
        public static final int TWO_HUNDRED = 200;
    }
    private static final int[] _FontSizes = { FontSizes.TWENTY, FontSizes.TWENTY_FIVE, FontSizes.THIRTY, FontSizes.FORTY, FontSizes.FIFTY, FontSizes.HUNDRED, FontSizes.TWO_HUNDRED };

    public static class Fonts {
        public static final String DIN_ALT = "fonts/DINAlternate";

        public static final String DEFAULT = DIN_ALT;
    }
    private static final String[] _Fonts = {
            /*Fonts.DIN_ALT
             */
            };
    private static final ArrayMap<String, ArrayMap<Integer, BitmapFont>> fontCache = new ArrayMap<String, ArrayMap<Integer, BitmapFont>>();

    public static class Animations {
        public static final String PLAYER = "character/adventurer-v1.5-Sheet.png";
        public static final String PLAYER_STILL = "images/ball_anim.png";
        public static final String PLAYER_JUMPING = "images/ball_anim_jump.png";
        public static final String PLAYER_FALLING = "images/ball_anim_fall.png";
    }
    public static final ArrayMap<String, Animation> animationList = new ArrayMap<String, Animation>();
    private static final String[] _Animations = {
            /*Animations.PLAYER*/
            /*Animations.PLAYER_STILL, Animations.PLAYER_JUMPING, Animations.PLAYER_FALLING
             */
            };

    public static final String UI_SKIN = "ui/uiskin.json";

    public static class Tmxs {
        public static final String MAP_001 = "tile/untitled.tmx";
        public static final String MAP_002 = "tile/outline.tmx";
    }
    private static final String[] _Tmxs = {
            Tmxs.MAP_001, Tmxs.MAP_002
    };

    private static AssetManager assetManager;

    //Private constructor to prevent instantiation
    private Assets () { }

    /**
     *  Static method that loads all the assets in the asset classes
     */
    public static void loadAll(AssetManager manager) {
        assetManager = manager;
        for (String debug:_Debugs) assetManager.load(debug, Texture.class);
        for (String image: _Images) assetManager.load(image, Texture.class);
        for (String animation: _Animations) assetManager.load(animation, Texture.class);
        assetManager.load("character/FinnSprite.png",Texture.class);
        for (String button: _Buttons) {
            assetManager.load(button + ".png", Texture.class);
            assetManager.load(button + "_hover.png", Texture.class);
            assetManager.load(button + "_down.png", Texture.class);
        }

        assetManager.load(UI_SKIN, Skin.class);

        manager.setLoader(TiledMap.class, new TmxMapLoader());
        for (String tmx: _Tmxs) assetManager.load(tmx, TiledMap.class);


        healthPixel = new Texture(createProceduralPixmap(1,1,1,0.1f,0.1f));
    }

    public static Texture healthPixel;

    /**
     * Separate function to load fonts synchronously
     */
    public static void loadFonts() {
        //Load fonts separately
        for (String fontName: _Fonts) {
            ArrayMap<Integer, BitmapFont> localCache = new ArrayMap<Integer, BitmapFont>();

            for (int i = 0; i < _FontSizes.length; i++) {
                BitmapFont font = new BitmapFont(Gdx.files.internal(fontName + ".fnt"), false);
                font.getData().setScale((1.0f * _FontSizes[i]) / FONT_FILE_SIZE);

                localCache.put(_FontSizes[i], font);
            }

            fontCache.put(fontName, localCache);
        }
    }


    public static Skin uiskin;
    /*
    public static Skin getSkin(){
        Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        return skin;
    }
    */
    /**
     * Handler to be called when the AssetManager finishes
     * loading all the required assets
     */
    public static HashMap<String, TextureRegion> imageMap = new HashMap<>();
    public static HashMap<String, TextureRegion> layoutMap = new HashMap<>();
    public static void finishLoading() {
        assetManager.finishLoading();
        uiskin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        //Setup the animations
        loadItem(imageMap,Debugs.mines_resources,Debugs.mines_resources_ret);
        loadItem(imageMap,Debugs.mines,Debugs.mines_ret);
        loadItem(imageMap,Debugs.icon,Debugs.icon_ret);
        loadItem(imageMap,Debugs.items,Debugs.items_ret);
        loadItem(imageMap,Debugs.runes,Debugs.runes_ret);
        loadItem(imageMap,Debugs.jewels,Debugs.jewels_ret);

        for (String animation: _Animations) {
            TextureRegion texture = new TextureRegion(assetManager.get(animation, Texture.class));
            TextureRegion[] regions = texture.split(24, 24)[0];

            //animationList.put(animation, new Animation(0.1f, regions));
        }
        loadAnimation();

    }

    static void loadAnimation(){
        TextureRegion[] t = {imageMap.get(Debugs.mines_resources+"_2_2")};
        animationList.put("minerock_idle",new Animation(0,t));


        Texture sheet = getTexture("character/FinnSprite.png");
        TextureRegion[] textureRegions;
        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,0,9);
        Animation animation = new Animation<TextureRegion>(0.4f,textureRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        animationList.put("player_idle",animation);
        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,9,6);
        animation = new Animation<TextureRegion>(0.1f,textureRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        animationList.put("player_walk",animation);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,9,6);
        animation = new Animation<TextureRegion>(0.05f,textureRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        animationList.put("player_dash",animation);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,16,2);
        animation = new Animation<TextureRegion>(0.2f,textureRegions);
        animationList.put("player_damage",animation);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,18,5);
        animation = new Animation<TextureRegion>(0.1f,textureRegions);
        animationList.put("player_dead",animation);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,0,1);
        animation = new Animation<TextureRegion>(0f,textureRegions);
        animationList.put("player_mining",animation);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,0,9);
        animation = new Animation<TextureRegion>(0.4f,textureRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        animationList.put("player_craft",animation);

        textureRegions = Assets.loadFramesFromSheet(sheet,32,32,23,5);
        animation = new Animation<TextureRegion>(0.1f,textureRegions);
        animationList.put("player_attack",animation);
    }

    static RandomXS128 randomXS128 = new RandomXS128();

    public static TextureRegion getRandom(){
        return imageMap.get(imageMap.keySet().toArray(new String[0])[Math.abs(randomXS128.nextInt())%imageMap.keySet().size()]);
    }
    public static void loadItem(HashMap<String, TextureRegion> map, String path, Rectangle rect){
        loadItem(map,path,(int)rect.x,(int)rect.y,(int)rect.width,(int)rect.height);
    }
    public static void loadItem(HashMap<String, TextureRegion> map,String path, int divW, int divH,int rows,int cols){
        Texture sheet = getTexture(path);
        TextureRegion[][] trs= TextureRegion.split(sheet,divW,divH);

        Gdx.app.log("loads-path : ",path+","+trs.length+"--"+trs[0].length);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String str = path+"_"+i+"_"+j;
                map.put(str,trs[i][j]);
            }
        }
    }

    public static TextureRegion loadGridTexture(Texture texture, int width, int height, int posX, int posY){
        return TextureRegion.split(texture,width,height)[posX][posY];
    }

    public static TextureRegion[] loadFramesFromSheet(Texture sheet, int width, int height, int skip, int count){
        TextureRegion[][] tmp = TextureRegion.split(sheet,width,height);
        TextureRegion[] frames = new TextureRegion[count];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = skip; j < skip+count; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        return frames;
    }
    /**
     * Get a loaded asset of type T from the loaded cache
     *
     * @param fileName The identifier for the asset
     * @param type The type to return
     * @return	The asset with the type as specified
     */
    public static <T> T getAsset(String fileName, Class<T> type) {
        return assetManager.get(fileName, type);
    }

    public static Texture getTexture(String imgName) {
        return assetManager.get(imgName);
    }

    /**
     * Returns a BitmapFont of the specified font
     *
     * @param fontName	The font name
     * @return The associated BitmapFont
     */
    public static BitmapFont getFont(String fontName, int size) { return fontCache.get(fontName).get(size); }


    /**
     * Get an animation that has already been loaded and cached
     *
     * @param animation
     * @return The given animation corresponding to the string
     */
    public static Animation getAnimation(String animation) {
        return animationList.get(animation);
    }

    /**
     * Dispose all the assets as well as the Asset Manager (not resettable)
     */


    private static Pixmap createProceduralPixmap(int width, int height, float r, float g, float b) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fill();
        return pixmap;
    }

    public static void dispose() {
        for (ArrayMap<Integer, BitmapFont> caches: fontCache.values()) for (BitmapFont font: caches.values()) font.dispose();
    }

}