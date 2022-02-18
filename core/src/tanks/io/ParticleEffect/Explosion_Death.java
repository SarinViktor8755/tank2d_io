package tanks.io.ParticleEffect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Explosion_Death {
    private Vector2 position;
    //    private float rotate;
    private float time_life;
    private int nom_texture;

    public Explosion_Death() {
        position = new Vector2();
    }

    public void setParameters(float x, float y) {

        this.position.set(x, y);
        nom_texture = 0;
        this.time_life = 10;

    }

    public boolean isLife() {
        if (time_life > 0) return true;
        return false;
    }

    public void updateTexture() {

        this.nom_texture = (int)(time_life / 30);
    }

    public void update(ParticleCustum particleCustum) {
        time_life = time_life - Gdx.graphics.getDeltaTime();
        this.updateTexture();
    }

    public Vector2 getPosition() {
        return position;
    }

    public String getNameTextureRegion(){
        return "ed"+ nom_texture;
    }



}
