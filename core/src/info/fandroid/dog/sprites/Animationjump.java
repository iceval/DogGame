package info.fandroid.dog.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;



public class Animationjump {
    private Array<TextureRegion> frames;


    public Animationjump(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i=0;i < frameCount;i++){
            frames.add(new TextureRegion(region,i * frameWidth,0, frameWidth, region.getRegionHeight()));
        }

    }

    public TextureRegion getFrame1(){ return frames.get(0);}
    public TextureRegion getFrame2(){ return frames.get(1);}
    public TextureRegion getFrame3(){ return frames.get(2);}
}
