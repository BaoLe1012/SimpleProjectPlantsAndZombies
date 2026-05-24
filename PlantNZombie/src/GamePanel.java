import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel {

    int currentSun = 300; 
    String selectedPlant = "NONE"; 
    
    ArrayList<Peashooter> peashooters = new ArrayList<>();
    ArrayList<Sunflower> sunflowers = new ArrayList<>();
    ArrayList<Zombie> zombies = new ArrayList<>();
    ArrayList<Projectile> projectiles = new ArrayList<>();

    Image bgImg = new ImageIcon("src/mainBG.png").getImage();
    Image peashooterImg = new ImageIcon("src/peashooter.gif").getImage();
    Image sunflowerImg = new ImageIcon("src/sunflower.gif").getImage();
    Image zombieImg = new ImageIcon("src/zombie1.png").getImage();
    Image peaImg = new ImageIcon("src/pea.png").getImage();

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        
         MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickX = e.getX();
                int clickY = e.getY();
                
                if (clickY < 50) {
                    if (clickX < 120) {
                        selectedPlant = "PEASHOOTER";
                    } else if (clickX > 130 && clickX < 250) {
                        selectedPlant = "SUNFLOWER";
                    }
                } else {

                	if (selectedPlant.equals("PEASHOOTER") && currentSun >= 100) {
                        peashooters.add(new Peashooter(clickX - 20, clickY - 20, peashooterImg));
                        currentSun -= 100; 
                        selectedPlant = "NONE"; 
                    } else if (selectedPlant.equals("SUNFLOWER") && currentSun >= 50) {
                        sunflowers.add(new Sunflower(clickX - 20, clickY - 20, sunflowerImg));
                        currentSun -= 50; 
                        selectedPlant = "NONE";
                    }
                }
            }
        };
        addMouseListener(mouseHandler);
    }

    public void startGame() {
        Runnable gameLoop = new Runnable() {
            @Override
            public void run() {
                long lastZombieSpawn = System.currentTimeMillis();
                
                while (true) {
                    long currentTime = System.currentTimeMillis();
                    

                    if (currentTime - lastZombieSpawn > 4000) {
                        int randomY = 100 + (int)(Math.random() * 400); // Random hàng dọc
                        zombies.add(new Zombie(800, randomY, zombieImg));
                        lastZombieSpawn = currentTime;
                    }

                    updateGameEntities(currentTime);
                    repaint();

                    try { Thread.sleep(16); } catch (Exception ex) {} // Khoảng 60 FPS
                }
            }
        };
        Thread thread = new Thread(gameLoop);
        thread.start();
    }

    public void updateGameEntities(long currentTime) {
        for (Sunflower s : sunflowers) {
            if (s.canGenerateSun(currentTime)) {
                currentSun += 25; 
            }
        }

        for (Peashooter p : peashooters) {
            if (p.canShoot(currentTime)) {
                projectiles.add(new Projectile(p.x + 40, p.y + 10, peaImg));
            }
        }

        for (Projectile proj : projectiles) proj.move();
        for (Zombie z : zombies) z.move();

        Iterator<Projectile> projIter = projectiles.iterator();
        while (projIter.hasNext()) {
            Projectile proj = projIter.next();
            Iterator<Zombie> zombieIter = zombies.iterator();
            boolean hit = false;
            
            while (zombieIter.hasNext()) {
                Zombie z = zombieIter.next();
                Rectangle rectProj = new Rectangle(proj.x, proj.y, proj.width, proj.height);
                Rectangle rectZombie = new Rectangle(z.x, z.y, z.width, z.height);
                
                if (rectProj.intersects(rectZombie)) {
                    z.hp -= proj.damage;
                    hit = true;
                    if (z.hp <= 0) {
                        zombieIter.remove(); 
                    }
                    break;
                }
            }
            
            if (hit) {
                projIter.remove(); 
            } else if (proj.x > 800) {
                projIter.remove(); 
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
 
        if (bgImg != null && bgImg.getWidth(null) > 0) {
            g.drawImage(bgImg, 0, 0, 800, 600, null);
        } else {
            g.setColor(new Color(34, 139, 34)); 
            g.fillRect(0, 0, 800, 600);
        }

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 800, 50);
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("SUN POINTS: " + currentSun, 600, 32);
        
        g.setColor(selectedPlant.equals("PEASHOOTER") ? Color.YELLOW : Color.WHITE);
        g.fillRect(10, 5, 110, 40);

        if (peashooterImg != null) {
            g.drawImage(peashooterImg, 15, 10, 30, 30, null);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("100", 50, 30);

        g.setColor(selectedPlant.equals("SUNFLOWER") ? Color.YELLOW : Color.WHITE);
        g.fillRect(130, 5, 110, 40);

        if (sunflowerImg != null) {
            g.drawImage(sunflowerImg, 135, 10, 30, 30, null);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("50", 170, 30);

        for (Sunflower s : sunflowers) s.draw(g, Color.ORANGE);
        for (Peashooter p : peashooters) p.draw(g, Color.GREEN);
        for (Projectile proj : projectiles) proj.draw(g, Color.CYAN);
        for (Zombie z : zombies) z.draw(g, Color.DARK_GRAY);
    }
}