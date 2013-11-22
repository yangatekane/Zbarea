package com.zbearer.player;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.zbearer.biz.ResourcesManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by yanga on 2013/11/11.
 */
public abstract class Player extends AnimatedSprite{
    private Body body;

    public Player(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld) {
        super(pX, pY, ResourcesManager.getInstance().player_region, vbo);
        camera.setChaseEntity(this);
    }

    public abstract void onDistruction();

    private boolean canBomb = false;

    private void createPhysics(final Camera camera, PhysicsWorld physicsWorld){
        body = PhysicsFactory.createBoxBody(physicsWorld,this, BodyDef.BodyType.DynamicBody,PhysicsFactory.createFixtureDef(0,0,0));
        body.setFixedRotation(true);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(this,body,true,false){
            @Override
            public void onUpdate(float pSecondsElapsed) {
                super.onUpdate(pSecondsElapsed);
                camera.onUpdate(0.1f);
                if (getBody().getWorld().getContactCount()>10){
                    onDistruction();
                }
                if (canBomb){
                    body.applyForce(getX(),getY(),
                            getBody().getWorld().getBodies().next().getPosition().x,
                            getBody().getWorld().getBodies().next().getPosition().y);
                }
            }
        });

    }

    public void setShooting(){
        canBomb = true;
        final long[] PLAYER_ANIMATE = new long[]{100, 100, 100, 100};
        animate(PLAYER_ANIMATE,0,2,true);
    }

    public void bomb(){

    }
}
