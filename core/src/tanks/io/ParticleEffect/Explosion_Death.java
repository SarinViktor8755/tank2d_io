package tanks.io.ParticleEffect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Explosion_Death {
    private Vector2 position;
    private float rotate;
    private float time_life;

    public void setParameters(float x, float y){
        this.position.set(x,y);
    };

    public boolean isLife() {
        if (time_life > 0) return true;
        return false;
    }


    public int getTexture(){

        return 1;
    }

    public void update(ParticleCustum particleCustum){
        time_life = time_life - Gdx.graphics.getDeltaTime();

    }

}
