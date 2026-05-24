import java.awt.Image;

public class Projectile extends GameObject {
    int speed;
    int damage;

    public Projectile(int startX, int startY, Image imgPath) {

        super(startX, startY, 15, 15, imgPath);
        speed = 5;     
        damage = 35;    
    }

    public void move() {
        x += speed;
    }
}