package main.java.com.Units.Bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Bullet implements Pool.Poolable {
    // fields for bullets position and direction
    // поля для определения положения и направления пуль
    private static int BULLET_SPEED = 400;

    public Vector2 position = new Vector2(0, 0);
    public Vector2 direction = new Vector2(0, 0);
    private int nom;


    @Override
    public void reset() {
        //вызывается при выстреле пули
        this.position.set(0, 0);
        this.direction.set(BULLET_SPEED, 0);
        nom = 0;
       // System.out.println("Bullet is reset");
    }

    // метод, который мы можем вызвать для обновления нашей логики маркеров

    public void update(float dt) {
        this.direction.clamp(BULLET_SPEED,BULLET_SPEED);
       // System.out.println((dt * .001f)+ " ms");
       // System.out.println( "   SPEED buul :" + position.cpy().add(direction.cpy().scl(dt * .001f)).sub(position).len() / (dt * .001f) * 1000);
        position.add(direction.cpy().scl(dt * .001f)); //ЗА секунду пролетает 400 пикселей пуля
    }

    // способ задания положения и направления пуль (стрельбы)
    public void fireBullet(float x, float xpos, float ypos, float xvel, float yvel) {
        this.position.set(xpos, ypos);
        this.direction.set(xvel, yvel).clamp(BULLET_SPEED,BULLET_SPEED);
    }

    // то же, что и вышеописанный метод с векторами
    public void fireBullet(Vector2 pos, Vector2 dir) {
        this.position = pos;
        this.direction = dir;
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "position=" + position.x + "  " + position.y +
                ", nom=" + nom +
                '}';
    }
}