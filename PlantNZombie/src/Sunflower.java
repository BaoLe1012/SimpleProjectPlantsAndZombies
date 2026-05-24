import java.awt.Image;

public class Sunflower extends GameObject {
    long lastSunTime;     
    int sunGenerateSpeed;  

    public Sunflower(int startX, int startY, Image imgPath) {
        super(startX, startY, 40, 40, imgPath);
        lastSunTime = System.currentTimeMillis();
        sunGenerateSpeed = 5000; 
    }

 
    public boolean canGenerateSun(long currentTime) {
        if (currentTime - lastSunTime >= sunGenerateSpeed) {
            lastSunTime = currentTime;
            return true;
        }
        return false;
    }
}