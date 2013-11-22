package com.zbearer.scene;

import com.zbearer.biz.SceneManager;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

/**
 * Created by yanga on 2013/11/10.
 */
public class LoadingScene extends BaseScene {
    @Override
    public void createScene() {
        setBackground(new Background(Color.WHITE));
        attachChild(new Text(200,740/2,resourcesManager.font,"Loading...",vbom));
    }

    @Override
    public void onBackKeyPressed() {
        return;
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_LOADING;
    }

    @Override
    public void disposeScene() {

    }
}
