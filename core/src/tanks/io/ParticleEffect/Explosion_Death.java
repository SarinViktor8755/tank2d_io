package tanks.io.ParticleEffect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import org.graalvm.compiler.loop.MathUtil;

public class Explosion_Death {
    private Vector2 position;
    private float time_life;
    private int nom_texture;



    public Explosion_Death() {
        position = new Vector2(-150, -151);
        nom_texture = 1;
    }

    public void setParameters(float x, float y) {
        this.position.set(x, y);
        nom_texture = 0;
        this.time_life = 1.5f;

    }

    public boolean isLife() {
        if (time_life > 0) return true;
        return false;
    }

    public void update() {
        time_life -= Gdx.graphics.getDeltaTime();
    }

    public Vector2 getPosition() {
        return position;
    }

    public String getNameTextureRegion() {
        int r = 1 + Math.abs((int) MathUtils.map(0, 1.5f, 29, 0, time_life));
        if (r < 1) r = 1;else if (r > 30) r = 30;
        System.out.println("::: - " + r);
        return String.valueOf(r);
    }


}
