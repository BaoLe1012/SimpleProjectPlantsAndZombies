import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public abstract class GameObject {
    protected int x, y; 
    protected int width, height;
    protected Image img;

    public GameObject(int startX, int startY, int w, int h, Image imgPath) {
        x = startX;
        y = startY;
        width = w;
        height = h;
        img = imgPath;
    }

    public void draw(Graphics g, Color fallbackColor) {
        if (img != null && img.getWidth(null) > 0) {
            g.drawImage(img, x, y, width, height, null);
        } else {
            g.setColor(fallbackColor);
            g.fillRect(x, y, width, height);
        }
    }
}