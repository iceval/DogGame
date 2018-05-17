package info.fandroid.dog.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import info.fandroid.dog.DogGame;



public class GameOver extends State {

    private Texture background;
    private Texture gameover;
    private BitmapFont font;

    public GameOver(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, DogGame.WIDTH, DogGame.HEIGHT);
        background = new Texture("fon2v.png");
        gameover = new Texture("gameover.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }



    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new MenuState(gsm));
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
        sb.draw(gameover, camera.position.x - gameover.getWidth() / 2, camera.position.y);
        font.draw(sb,"score: " + PlayState.counterjumpbrevno, camera.position.x-25,220);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameover.dispose();
    }
}
