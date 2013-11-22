package com.zbearer.biz;

import android.util.Log;

import org.andengine.engine.camera.Camera;

import com.zbearer.GameActivity;

import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by yanga on 2013/11/09.
 */
public class ResourcesManager {
    private static final String TAG = ResourcesManager.class.getName();
    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public Engine engine;
    public GameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;

    public ITextureRegion splash_region;
    private BitmapTextureAtlas splashTextureAtlas;

    public ITextureRegion menu_background_region;
    public ITextureRegion play_region;
    public ITextureRegion options_region;
    private BitmapTextureAtlas menuTextureAtlas;
    private BitmapTextureAtlas menuBackgroundAtlas;

    public BitmapTextureAtlas gameTextureAtlas;
    public BitmapTextureAtlas gameBackgroundTextureAtlas;
    public ITextureRegion game_background_region;
    public List<ITextureRegion> astroids = new LinkedList<ITextureRegion>();
    public ITextureRegion ship;

    public Font font;

    public BitmapTextureAtlas particleTextureAtlas;
    public ITiledTextureRegion player_region;
    public ITextureRegion particle;

    public BitmapTextureAtlas explosionTextureAtlas;
    public ITextureRegion explosion;

    public void loadMenuResources(){
        loadMenuGraphics();
        loadMenuAudio();
        loadingMenuFonts();
    }

    public void loadGameResources(){
        loadGameGraphics();
        loadGameAudio();
        loadGameFonts();
    }

    private void loadMenuGraphics(){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(),
                               1024,1024,
                               TextureOptions.BILINEAR);
        menuBackgroundAtlas = new BitmapTextureAtlas(activity.getTextureManager(),
                1024,1024,
                TextureOptions.BILINEAR);
        menu_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundAtlas,
                                activity,"menu_background.png",0,0);
        menuBackgroundAtlas.load();
        play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas,
                activity,"play.png",200,200);
        options_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuTextureAtlas,
                activity,"options.png",200,100);

        // menuTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,1,0));
        menuTextureAtlas.load();
    }

    private void loadMenuAudio(){

    }

    private void loadingMenuFonts(){
        FontFactory.setAssetBasePath("font/");
        final ITexture mainfontTexture = new BitmapTextureAtlas(activity.getTextureManager(),
                       256,256,
                       TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        font = FontFactory.createStrokeFromAsset(activity.getFontManager(),
                mainfontTexture,activity.getAssets(),
                "Roboto-MediumItalic.ttf",50,true, Color.WHITE_ABGR_PACKED_INT,2,Color.BLACK_ABGR_PACKED_INT);
        font.load();
    }

    private void loadGameGraphics(){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
        gameTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(),1024,1024, TextureOptions.BILINEAR);
        gameBackgroundTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(),1024,1024, TextureOptions.BILINEAR);
        particleTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(),1024,1024, TextureOptions.BILINEAR);
        explosionTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(),1024,1024, TextureOptions.BILINEAR);

        game_background_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameBackgroundTextureAtlas,activity,"Space.png",0,0);
        gameBackgroundTextureAtlas.load();

        ship = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,activity,"Raptor.png",0,0);
        particle = BitmapTextureAtlasTextureRegionFactory.createFromAsset(particleTextureAtlas,activity,"ship1.png",0,0);
        particleTextureAtlas.load();

        explosion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(explosionTextureAtlas,activity,"fireball1.png",0,0);
        explosionTextureAtlas.load();

        player_region = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gameTextureAtlas,activity,"Raptor.png",0,0,3,1);
        for(int i=0; i < 30; i++){
            ITextureRegion astroid;
            if (i<10){
                astroid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,activity,"astroid1_000"+String.valueOf(i)+".png",100,100);
            }else {
                astroid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(gameTextureAtlas,activity,"astroid1_00"+String.valueOf(i)+".png",100,100);
            }
            astroids.add(astroid);
        }
        gameTextureAtlas.load();
    }

    private void loadGameFonts(){

    }

    private void loadGameAudio(){

    }

    public void loadSplashScreen(){
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        splashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(),
                492,740, TextureOptions.BILINEAR);
        splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,
                activity,"splash.png",0,0);
        splashTextureAtlas.load();
    }

    public void unloadSplashScreen(){
        splashTextureAtlas.unload();
        splash_region = null;
    }

    public void unloadMenuTextures(){
        menuBackgroundAtlas.unload();
        menuTextureAtlas.unload();
    }

    public void loadMenuTextures(){
        menuBackgroundAtlas.load();
        menuTextureAtlas.load();
    }

    public void unloadGameTextures(){

    }

    public static void prepareManager(Engine engine, GameActivity activity,
                                      Camera camera, VertexBufferObjectManager vbom){
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }

    public static ResourcesManager getInstance(){
        return INSTANCE;
    }
}
