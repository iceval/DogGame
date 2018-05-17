package info.fandroid.dog.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import info.fandroid.dog.DogGame;



public class MenuState extends State {

    private Texture background;
    private Texture playBtn;
    Sprite playBtnsprite;
    private Texture soundvolumecontrolpng;
    Sprite soundvolumecontrolpngsprite;
    private Texture soundvolumecontrol2png;
    Sprite soundvolumecontrol2pngsprite;
    public static int soundvolume;
    private BitmapFont font;
    private int maxresult;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, DogGame.WIDTH, DogGame.HEIGHT);
        background = new Texture("fon2v.png");
        playBtn = new Texture("play2.png");
        playBtnsprite = new Sprite(playBtn);
        soundvolumecontrolpng = new Texture("volumeon.png");
        soundvolumecontrolpngsprite = new Sprite(soundvolumecontrolpng);
        soundvolumecontrol2png = new Texture("volumeoff.png");
        soundvolumecontrol2pngsprite = new Sprite(soundvolumecontrol2png);
        soundvolume = 1;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        Preferences prefs = Gdx.app.getPreferences("Doggameresult");
        maxresult = prefs.getInteger("highscore");
        if (PlayState.counterjumpbrevno > maxresult){
            maxresult = PlayState.counterjumpbrevno;
            prefs.putInteger("highscore", maxresult);
            prefs.flush();
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(tmp);
            Rectangle playBtnBounds=new Rectangle(camera.position.x -(playBtn.getWidth() / 2), camera.position.y,playBtn.getWidth(),playBtn.getHeight());
            if(playBtnBounds.contains(tmp.x, tmp.y)) {
                gsm.set(new PlayState(gsm));
            }
        }
        if (Gdx.input.justTouched()) {
            Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(tmp);
            Rectangle soundvolumecontrolpngBounds=new Rectangle(camera.position.x -(camera.viewportWidth / 2)+710, 400,soundvolumecontrolpng.getWidth(),soundvolumecontrolpng.getHeight());
            if(soundvolumecontrolpngBounds.contains(tmp.x, tmp.y)) {
                if (soundvolume == 0){
                    soundvolume = 1;
                }
                else{
                    soundvolume = 0;
                }
            }
        }
    }
    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, 0, 0, DogGame.WIDTH, DogGame.HEIGHT);
        playBtnsprite.setPosition(camera.position.x -(playBtn.getWidth() / 2),camera.position.y);
        playBtnsprite.draw(sb);
        font.draw(sb,"highscore: " + maxresult, camera.position.x-38,200);
        if (soundvolume == 1) {
            soundvolumecontrolpngsprite.setPosition(camera.position.x - (camera.viewportWidth / 2) + 680, 400);
            soundvolumecontrolpngsprite.draw(sb);
        }
        else{
            soundvolumecontrol2pngsprite.setPosition(camera.position.x - (camera.viewportWidth / 2) + 680, 400);
            soundvolumecontrol2pngsprite.draw(sb);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        soundvolumecontrolpng.dispose();
        soundvolumecontrol2png.dispose();
    }
}
