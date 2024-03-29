package com.zbearer.scene;

import com.zbearer.biz.SceneManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * Created by yanga on 2013/11/10.
 */
public class MainMenuScene extends BaseScene {
    private MenuScene menuChildScene;
    private final int MENU_PLAY = 0;
    private final int MENU_OPTIONS = 1;

    @Override
    public void createScene() {
        createBackground();
        createMenuChildScene();
    }

    @Override
    public void onBackKeyPressed() {
        System.exit(0);
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_MENU;
    }

    @Override
    public void disposeScene() {

    }

    private void createBackground(){
        attachChild(new Sprite(0,0,resourcesManager.menu_background_region,vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        });
    }

    private void createMenuChildScene(){
        menuChildScene = new MenuScene(camera);
        menuChildScene.setPosition(0,0);
        final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY,resourcesManager.play_region,vbom),1.2f,1);
        final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS,resourcesManager.options_region,vbom),1.2f,1);
        menuChildScene.addMenuItem(playMenuItem);
        menuChildScene.addMenuItem(optionsMenuItem);
        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);
        playMenuItem.setPosition(playMenuItem.getX()-30,
                playMenuItem.getY()-20);
        optionsMenuItem.setPosition(optionsMenuItem.getX()-30,
                optionsMenuItem.getY()+40);
        menuChildScene.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()){
                    case MENU_PLAY:
                        SceneManager.getInstance().loadGameScene(engine);
                        return true;
                    case MENU_OPTIONS:
                        return true;
                    default:
                        return false;
                }
            }
        });
        setChildScene(menuChildScene);
    }
}
