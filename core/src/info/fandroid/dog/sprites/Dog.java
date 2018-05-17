package info.fandroid.dog.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;



public class Dog {
    public static final int MOVEMENT = 200;
    public static final int Gravity = -15;
    private Vector3 position;
    private Vector3 velosity;
    private Rectangle bounds;
    private Animation dogAnimation;
    private Animationjump dogAnimationjump;
    public int dogpos = 1;

    private Texture texture;
    private Texture texture2;

    public Dog(int x, int y) {
        position = new Vector3(x, y, 0);
        velosity = new Vector3(0, 0, 0);
        texture = new Texture("dogrun.png");
        texture2 = new Texture("dogjump.png");
        dogAnimation = new Animation(new TextureRegion(texture),4,0.5f);
        bounds = new Rectangle(x,y, texture.getWidth()/3-60, texture.getHeight());
        dogAnimationjump = new Animationjump(new TextureRegion(texture2),3,0.5f);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getDog() {
        if (position.y == 60){
            dogpos = 1;
            return dogAnimation.getFrame();
        }
        if ((position.y >60) && (position.y <= 180) && (dogpos == 1)){
            return dogAnimationjump.getFrame1();
        }
        if (position.y > 180){
            dogpos = 0;
            return dogAnimationjump.getFrame2();
        }
        if ((position.y >60) && (position.y <= 180) && (dogpos == 0)){
            return dogAnimationjump.getFrame3();
        }
        return null;
    }

    public void update(float dt) {
        if (dt==0){
            dt=0.01f;
        }
        dogAnimation.update(dt);
        /*dogAnimationjump.update(dt);*/
        if (position.y > 60)
            velosity.add(0, Gravity, 0);
        velosity.scl(dt);
        position.add(MOVEMENT * dt, velosity.y, 0);
        if (position.y < 60) {
            position.y = 60;
        }
        velosity.scl(1 / dt);
        bounds.setPosition(position.x,position.y);
    }
    public void jump() {
       if (position.y==60)
           velosity.y = 450;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose() {
        texture.dispose();
        texture2.dispose();
    }
}