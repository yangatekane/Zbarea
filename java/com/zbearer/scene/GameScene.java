package com.zbearer.scene;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.zbearer.biz.SceneManager;
import com.zbearer.player.Player;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.handler.runnable.RunnableHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.particle.BlendFunctionParticleSystem;
import org.andengine.entity.particle.Particle;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.initializer.AlphaParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ColorParticleModifier;
import org.andengine.entity.particle.modifier.ExpireParticleInitializer;
import org.andengine.entity.particle.modifier.IParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by yanga on 2013/11/10.
 */
public class GameScene extends BaseScene implements IOnSceneTouchListener{

    private HUD gameHUD;
    private PhysicsWorld physicsWorld;
    private Sprite ship;
    private ParticleSystem particleSystem;
    private CircleParticleEmitter particleEmitter;
    private BlendFunctionParticleSystem blendFunctionParticleSystem;

    @Override
    public void createScene() {
        createBackground();
        createHUD();
        createPhysics();
        setOnSceneTouchListener(this);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene() {

    }

    private void createBackground(){
        attachChild(new Sprite(0, 0, resourcesManager.game_background_region, vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        });
    }

    private void createHUD(){
        gameHUD = new HUD();
        camera.setHUD(gameHUD);
    }

    private void createPhysics(){
        physicsWorld = new FixedStepPhysicsWorld(5,new Vector2(0,0), false);
        registerUpdateHandler((IUpdateHandler) physicsWorld);
        mediashower(200, 650);
        mediashower(200, 650);
        mediashower(200, 650);
        player(200, 650);
    }


    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (blendFunctionParticleSystem!=null){
            blendFunctionParticleSystem.setParticlesSpawnEnabled(true);
        }
        if (pSceneTouchEvent.isActionMove()){
            detachChild(ship);
            player(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());
        }
        return false;
    }
    private void mediashower(float x, float y) {
        int i=0;
        int j=0;
        int k=0;
        int g=0;
        int m=0;
        Sprite strods;
        for (ITextureRegion astroid :resourcesManager.astroids){
            if (i<8){
                strods = new Sprite(j, 60, astroid, vbom);
                final Body body = PhysicsFactory.createBoxBody(physicsWorld, strods, BodyDef.BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 1000, 0));
                body.setFixedRotation(true);
                body.setAngularVelocity(50);
                body.setAngularDamping(i);
                body.setLinearDamping(100);
                //body.setLinearVelocity(1,-1);
                physicsWorld.registerPhysicsConnector(new PhysicsConnector(strods, body, true, true));
                j+=60;
            }else if (i>=8 && i<16){
                strods = new Sprite(k, 120, astroid, vbom);
                final Body body = PhysicsFactory.createBoxBody(physicsWorld,strods, BodyDef.BodyType.DynamicBody,PhysicsFactory.createFixtureDef(0,1000,0));
                body.setFixedRotation(true);
                body.setAngularVelocity(-5);
                body.setLinearDamping(100);
                physicsWorld.registerPhysicsConnector(new PhysicsConnector(strods, body, true, true));
                k+=60;
            }else if (i>=16 && i<24){
                strods = new Sprite(g, 180, astroid, vbom);
                final Body body = PhysicsFactory.createBoxBody(physicsWorld,strods, BodyDef.BodyType.DynamicBody,PhysicsFactory.createFixtureDef(0,1000,0));
                body.setFixedRotation(true);
                body.setAngularVelocity(-3);
                body.setLinearDamping(100);
                physicsWorld.registerPhysicsConnector(new PhysicsConnector(strods, body, true, true));
                g+=60;
            }else {
                strods = new Sprite(m, 0, astroid, vbom);
                final Body body = PhysicsFactory.createBoxBody(physicsWorld,strods, BodyDef.BodyType.DynamicBody,PhysicsFactory.createFixtureDef(0,1000,0));
                body.setFixedRotation(true);
                body.setAngularVelocity(3);
                body.setLinearDamping(100);
                physicsWorld.registerPhysicsConnector(new PhysicsConnector(strods, body, true, true));
                m+=60;
            }
            i++;

            attachChild(strods);
        }
    }

    private void player(float x, float y) {
        ship = new Sprite(x,y,resourcesManager.ship,vbom);
        ship.setFlipped(false,true);
        physicsWorld.setGravity(new Vector2(0,0));
        final Body shipBody = PhysicsFactory.createBoxBody(physicsWorld, ship, BodyDef.BodyType.StaticBody, PhysicsFactory.createFixtureDef(100f, 0, 1000f));
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(ship, shipBody, true, false) {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                super.onUpdate(pSecondsElapsed);
                camera.onUpdate(0.1f);
                shipBody.applyForce(getX(), getY(),
                        getBody().getWorld().getBodies().next().getPosition().x,
                        getBody().getWorld().getBodies().next().getPosition().y);
            }
        });

        SpriteParticleSystem shape = lazerBeam(x, y);
        attachChild(ship);
        getLastChild().attachChild(blendFunctionParticleSystem);
        shape.setParticlesSpawnEnabled(true);
        blendFunctionParticleSystem.setParticlesSpawnEnabled(true);
        attachChild(explosion(x+300,y-40));
    }

    private SpriteParticleSystem lazerBeam(float x, float y) {
        particleEmitter = new CircleParticleEmitter(
                x-200 , y - 250, 0, 40);
        SpriteParticleSystem shape = new SpriteParticleSystem(x,y,particleEmitter,300,500,20,
                resourcesManager.particle,vbom);
        blendFunctionParticleSystem =
                new BlendFunctionParticleSystem(x,y,shape.getParticleFactory(),particleEmitter,
                        300,500,20);
        blendFunctionParticleSystem.addParticleInitializer(
                new ColorParticleInitializer(0, 1, 0));
        blendFunctionParticleSystem.addParticleInitializer(
                new AlphaParticleInitializer(0));
        blendFunctionParticleSystem.setBlendFunction(GL10.GL_SRC_ALPHA,
                GL10.GL_ONE);
        blendFunctionParticleSystem.addParticleInitializer(
                new VelocityParticleInitializer(2, 2, 10, -1000));
        blendFunctionParticleSystem.addParticleInitializer(
                new RotationParticleInitializer(0, 0));
        blendFunctionParticleSystem.addParticleModifier(
                new ScaleParticleModifier(10.0f, 100.0f, 1,1,3,20));
        blendFunctionParticleSystem.addParticleModifier(
                 new ColorParticleModifier(0, 5, 0, 1, 2, 1, 5, 10));
        blendFunctionParticleSystem.addParticleModifier(
                new AlphaParticleModifier(2, 5, 0, -8));
        blendFunctionParticleSystem.addParticleInitializer(
                new ExpireParticleInitializer(2,4));
        return shape;
    }
    private BlendFunctionParticleSystem explosion(float x, float y) {
        CircleParticleEmitter particleEmitter = new CircleParticleEmitter(
                x-200 , y - 250, 0, 40);
        SpriteParticleSystem shape = new SpriteParticleSystem(x,y,particleEmitter,300,500,20,
                resourcesManager.explosion,vbom);
        BlendFunctionParticleSystem blendFunctionParticleSystem =
                new BlendFunctionParticleSystem(x,y,shape.getParticleFactory(),particleEmitter,
                        300,500,20);
        blendFunctionParticleSystem.addParticleInitializer(
                new ColorParticleInitializer(1, 0, 0));
        blendFunctionParticleSystem.addParticleInitializer(
                new AlphaParticleInitializer(0));
        blendFunctionParticleSystem.setBlendFunction(GL10.GL_SRC_ALPHA,
                GL10.GL_ONE);
        blendFunctionParticleSystem.addParticleInitializer(
                new VelocityParticleInitializer(-2, 2, -2, -2));
        blendFunctionParticleSystem.addParticleInitializer(
                new RotationParticleInitializer(0.0f, 360.0f));
        blendFunctionParticleSystem.addParticleModifier(
                new ScaleParticleModifier(1.0f, 2.0f, 0, 5));
        blendFunctionParticleSystem.addParticleModifier(
                new ColorParticleModifier(10, 100, 3, 0.5f, 0, 0, 0, 3));
        blendFunctionParticleSystem.addParticleModifier(
                new ColorParticleModifier(1, 1, 3.5f, 1, 0, 1, 2, 4));
        blendFunctionParticleSystem.addParticleModifier(
                new AlphaParticleModifier(0, 1, 0, 1));
        blendFunctionParticleSystem.addParticleModifier(
                new AlphaParticleModifier(1, 0, 3, 4));
        blendFunctionParticleSystem.addParticleInitializer(
                new ExpireParticleInitializer(2,4));
        shape.setParticlesSpawnEnabled(true);
        blendFunctionParticleSystem.setParticlesSpawnEnabled(true);
        return blendFunctionParticleSystem;
    }

}
