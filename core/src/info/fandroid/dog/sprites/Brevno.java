package info.fandroid.dog.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;



public class Brevno {

    public static final int BREVNO_WIDTH = 61;

    private Texture brevno;
    private Vector2 posbrevno;
    private Rectangle boundsBrevno;

    public Texture getBrevno() {
        return brevno;
    }

    public Vector2 getPosbrevno() {
        return posbrevno;
    }

    public Brevno(float x){
        brevno = new Texture("brevnov2.png");

        posbrevno = new Vector2( x+837, 40);

        boundsBrevno = new Rectangle(posbrevno.x,posbrevno.y, brevno.getWidth()-31, brevno.getHeight()-45);
    }

    public void reposition(float x){
        posbrevno.set( x, 40);
        boundsBrevno.setPosition(posbrevno.x, posbrevno.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsBrevno);
    }


    public void dispose() {
        brevno.dispose();
    }
}
