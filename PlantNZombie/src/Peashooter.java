import java.awt.Image;

public class Peashooter extends GameObject {
    long lastShotTime;
    int attackSpeed;

    public Peashooter(int startX, int startY, Image imgPath) {
        super(startX, startY, 40, 40, imgPath);
        lastShotTime = System.currentTimeMillis();
        attackSpeed = 2000; 
    }


    public boolean canShoot(long currentTime) {
        if (currentTime - lastShotTime >= attackSpeed) {
            lastShotTime = currentTime; 
            return true;
        }
        return false;
    }
}
