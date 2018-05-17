package info.fandroid.dog.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import info.fandroid.dog.DogGame;
import info.fandroid.dog.sprites.Brevno;
import info.fandroid.dog.sprites.Dog;

public class PlayState extends State {

    public static final int BREVNO_SPACING = 200;
    public static final int BREVNO_COUNT = 2;



    private Dog dog;
    private Texture background;
    private Texture jumppng;
    Sprite jumppngsprite;
    private Array<Brevno> brevnos;
    private int hearts = 3;
    private Texture heartpng;
    public static int counterjumpbrevno;
    private BitmapFont font;
    public float way;
    public int timedogresistance=0;
    private Music music;
    private Sound crash;

    public PlayState(GameStateManager gsm){

        super(gsm);
        dog = new Dog(50,200);
        camera.setToOrtho(false, DogGame.WIDTH, DogGame.HEIGHT);
        background = new Texture("fon2v.png");
        jumppng = new Texture("jumpv2.png");
        heartpng = new Texture("heart6.png");
        jumppngsprite = new Sprite(jumppng);
        crash = Gdx.audio.newSound(Gdx.files.internal("treskbrevna.mp3"));
        brevnos = new Array<Brevno>();
        counterjumpbrevno  = 0;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        music = Gdx.audio.newMusic(Gdx.files.internal("muzikadog.mp3"));
        if ( MenuState.soundvolume == 1){
            music.setLooping(true);
            music.setVolume(0.1f);
            music.play();}
        else{
            music.stop();
        }


        for (int i=0;i <BREVNO_COUNT;i++){
            brevnos.add(new Brevno(i * ((BREVNO_COUNT + Brevno.BREVNO_WIDTH) + 400)));

        }
    }
    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()){
            Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            camera.unproject(tmp);
            Rectangle jumppngBounds=new Rectangle(camera.position.x -(camera.viewportWidth / 2)+710,30,jumppng.getWidth(),jumppng.getHeight());
            if(jumppngBounds.contains(tmp.x, tmp.y)) {
                dog.jump();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        dog.update(dt);
        camera.position.x = dog.getPosition().x +300;
        for (int i=0; i <brevnos.size; i++){
            Brevno brevno = brevnos.get(i);
            if (camera.position.x - camera.viewportWidth / 2 > brevno.getPosbrevno().x
                    + brevno.getBrevno().getWidth()) {
                brevno.reposition(brevno.getPosbrevno().x + ((Brevno.BREVNO_WIDTH + BREVNO_SPACING) * BREVNO_COUNT + 378));
                counterjumpbrevno++;
            }
            timedogresistance++;
            if ((brevno.collides(dog.getBounds())) && (timedogresistance>50)) {
                if ( MenuState.soundvolume == 1) {
                    crash.play();
                }
                else{
                crash.stop();
                }
                hearts--;
                timedogresistance=0;
            }
            if (hearts == 0){
                gsm.set(new GameOver(gsm));
            }
        }
        camera.update();
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        way=-100;
        while (way<10000000) {
            sb.draw(background, way, 0);
            way+=800;
        }
        for (int i = 0;i < hearts; i++){
            sb.draw(heartpng,camera.position.x-(camera.viewportWidth / 2)+i*65 ,430);
        }
        for (Brevno brevno : brevnos) {
            sb.draw(brevno.getBrevno(), brevno.getPosbrevno().x, 40);
        }
        jumppngsprite.setAlpha(0.5f);
        jumppngsprite.setPosition(camera.position.x -(camera.viewportWidth / 2)+680,30);
        jumppngsprite.draw(sb);
        sb.draw(dog.getDog(),dog.getPosition().x,dog.getPosition().y);
        font.draw(sb,"score: " + counterjumpbrevno, camera.position.x+200,470);
        sb.end();
    }


    @Override
    public void dispose() {
        background.dispose();
        crash.dispose();
        dog.dispose();
        music.dispose();
        jumppng.dispose();
        heartpng.dispose();
        for (Brevno brevno : brevnos)
            brevno.dispose();
    }
}
