import java.awt.Image;

public class Zombie extends GameObject {
    int hp;
    int speed;

    public Zombie(int startX, int startY, Image imgPath) {

        super(startX, startY, 45, 65, imgPath);
        hp = 100;   
        speed = 1;  
    }

     public void move() {
        x -= speed;
    }
}