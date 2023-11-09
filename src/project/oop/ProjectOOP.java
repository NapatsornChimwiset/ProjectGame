/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package project.oop;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

class EleButton extends JButton{


    public EleButton(String title,int size,int x,int y,int width,int height) {
	super(title);
	this.setBackground(new Color(2, 117, 216));
	this.setForeground(Color.white);
	this.setFont(Element.getFont(size));
	this.setBounds(x, y, width, height);
    }
		
}


class Element {

    public static Font getFont(int size){
	Font font = null;
	try {
            font = Font.createFont(Font.TRUETYPE_FONT,new File("font\\Mali-Bold.ttf"));
            return font.deriveFont((float)size);
	} catch (FontFormatException | IOException e) {
	}
	return font;
    }
}


class Event {
    public static boolean checkHit(Goofy goofy,SnakeLava snakeLava,int goofySize,int snakeLavaHeight){
	if(goofy.x+goofySize > snakeLava.x && goofy.x < snakeLava.x) {
		if(goofy.y + goofySize >= snakeLava.y - snakeLavaHeight) {
                    return true;
		}
	}
	return false;
    }
    
    public static boolean checkHit(Goofy goofy,Barnacle barnacle,int goofySize,int barnacleHeight){
	if(goofy.x+goofySize > barnacle.x && goofy.x < barnacle.x) {
		if(goofy.y + goofySize >= barnacle.y - barnacleHeight) {
                    return true;
		}
	}
	return false;
    }
    		
    

    static boolean checkHit(Goofy goofy, Coin coin, int goofySize, int coinHeight, int coinWidth) {
        if (goofy.x + goofySize > coin.x && goofy.x < coin.x + coinWidth ) {
            if (goofy.y + goofySize > coin.y && goofy.y < coin.y + coinHeight) {
                return true;
            }
        }
        return false;
    }

    static boolean checkHit(Goofy goofy, Fly fly, int goofySize, int flyHeight) {
        if(goofy.x+goofySize > fly.x && goofy.x < fly.x) {
		if(goofy.y + goofySize >= fly.y - flyHeight) {
                    return true;
		}
	}
	return false;
    }

}

class Fly {
    int speed;
    int x;
    int y;
    Thread timeMove;
    
    public Fly(int x,int y,int speed,JPanel game) {
	this.x = x;
	this.y = y;
	this.speed = speed;
	move(game);
    }
		
    private void move(JPanel game) {
        Thread moveThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if(x<=0) {
                        x = (int) (1000+(300+Math.random()*1000));
                    }
                    x -= 30;
                    game.repaint();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
	moveThread.start();
    }
    
    public BufferedImage getImage() {
	BufferedImage image = null;
	try {
            image = ImageIO.read(new File("data\\fly_fly.png"));
            return image;
	} catch (IOException e) {
	}
	return image;
    }
}



class Coin {
    int x, y, value, speed;
    Thread moveThread;

    public Coin(int x, int y, int speed, int value, JPanel game) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.value = value;
        move(game);
    }
    
    private void move(JPanel game) {
        Thread moveThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    x -= speed;
                    game.repaint();
                    if (x < 0) {
                        x = (int) (1000 + (300 + Math.random() * 1000));
                    }
                    try {
                        Thread.sleep(50); 
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        moveThread.start();
    }


    public int getValue() {
        return value;
    }
    
    public BufferedImage getImage() {
	BufferedImage image = null;
	try {
            image = ImageIO.read(new File("data\\coinGold.png"));
            return image;
	} catch (IOException e) {
	}
	return image;
    }
}
  


class Cloud {
    int x;
    int y;
    int startX;
    int speed;
    int eType;
    static int CLOUD1 = 0,CLOUD2=1,CLOUD3=2;
    Thread moveThread; 

    
    public Cloud(int x,int y,JPanel game,int eType,int speed) {
	this.x = x;
	this.y = y;
        this.startX = x;
	this.speed = speed;
	this.eType = eType;
        move(game); // Create the move thread in the constructor
    }
		
    private void move(JPanel game) {
        Thread moveThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.interrupted()) {
                    if (x + 400 < 0) {
                        x = startX;
                    }
                    x -= speed;
                    game.repaint();
                    try {
                        Thread.sleep(20); 
                    } catch (InterruptedException e) {
                        // Handle the interruption, e.g., break out of the loo
                        break;
                    }
                }
            }
        });
        moveThread.start();
    }

		
    public void stop() {
	// Instead of using `stop`, you can interrupt the thread when needed.
        moveThread.interrupt();
    }

		
    public String getEvType(int eType){
	String[] name = new String[] {"cloud1.png","cloud2.png","cloud3.png"};
	return name[eType];
    }
		
    public BufferedImage getImage() {
	BufferedImage image = null;
	try {
            image = ImageIO.read(new File("data\\"+getEvType(this.eType)));
            return image;
	} catch (IOException e) {
	}
	return image;
    }
}


class Barnacle {
    int speed;
    int x;
    int y;
    Thread timeMove;
    
    public Barnacle(int x,int y,int speed,JPanel game) {
	this.x = x;
	this.y = y;
	this.speed = speed;
	move(game);
    }
		
    private void move(JPanel game) {
        Thread moveThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if(x<=0) {
                        x = (int) (1000+(300+Math.random()*1000));
                    }
                    x -= 30;
                    game.repaint();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
	moveThread.start();
    }
    
    public BufferedImage getImage() {
	BufferedImage image = null;
	try {
            image = ImageIO.read(new File("data\\barnacle.png"));
            return image;
	} catch (IOException e) {
	}
	return image;
    }
}

class SnakeLava {
    int speed;
    int x;
    int y;
    Thread timeMove;
    
    public SnakeLava(int x, int y, int speed, JPanel game) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        move(game);
    }

    private void move(JPanel game) {
        Thread moveThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (x <= 0) {
                        x = (int) (1000 + (300 + Math.random() * 1000));
                    }
                    x -= 30;
                    game.repaint();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        moveThread.start();
    }

		
    public BufferedImage getImage() {
	BufferedImage image = null;
	try {
            image = ImageIO.read(new File("data\\pokerMad.png"));
            return image;
	} catch (IOException e) {
	}
	return image;
    }
}


class Goofy{
    int x ;
    int y;
    int health=180;
    int speed=90;
    boolean isJumping = false;
    Thread jumpThread;

    public Goofy() {
		
    }
	
    public Goofy(int x,int y) {
	this.x=x;
	this.y=y;
    }
	
    public void jump(JPanel game) {
        if (!isJumping) { // Check if the goofy is already jumping
            isJumping = true;
            this.y -= speed;
            game.repaint();

            Thread jumpThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(450); //jump and fall in this time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    y += speed;
                    isJumping = false; // Reset the jumping flag when the goofy falls
                    game.repaint();
            }
            });
            jumpThread.start();// start jump work
        }
    }

    public BufferedImage getImage() {//method is designed to load an image from a specific location 
	BufferedImage image = null;
	try {
            image = ImageIO.read(new File("data\\goofy.png"));
            return image;
	} catch (IOException e) {
	}
	return image;
    }
}


public class ProjectOOP extends JFrame implements ActionListener{
    
    
    ProjectOOP(){
        add(new Game());
        add(new Menu());
    }
    
    private void removeContent() {
        this.getContentPane().removeAll();
	this.getContentPane().repaint();
    }
	
    public void endGame(long collectedCoins) {
	removeContent();
	this.getContentPane().add(new Menu(collectedCoins,this));
    }
	
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand().equals("Play again")) {
            removeContent();
            Game game = new Game();
            this.getContentPane().add(game);
            game.requestFocus();
	}
    }
    
    public class Game extends JPanel implements KeyListener{
	
        int speed = 50,goofySize = 70 ,snakeLavaHeight = 60,barnacleHeight = 40,coinWidth= 55,coinHeight = 40,flyHeight = 20;
        int base=400,xStart = 1000;
        long times = 0,lastPress=0,collectedCoins=0;
	static ProjectOOP pp;
        
        Goofy goofy = new Goofy(100,base-50);
        
        
        //------------------SnakeLava SIze ----------------------------
        Barnacle[] barnacleSet = makeBarnacle(1);
        //------------------SnakeLava SIze ----------------------------
        SnakeLava[] snakeLavaSet = makeSnakeLava(2);
        //--------------------Cloud SIze--------------------------------
        Cloud[] cloud1Set = makeCloud(2,Cloud.CLOUD1);
        Cloud cloud2 = new Cloud(xStart-100,base-150,this,Cloud.CLOUD2,4);
        Cloud cloud3 = new Cloud(xStart-100,base-150,this,Cloud.CLOUD3,8);
        //--------------------Coin SIze--------------------------------
        Coin[] coinSet = makeCoin(2);
        //--------------------Bee SIze--------------------------------
        Fly[] flySet = makeFly(1);
	
        Game(){
            this.setBounds(0,0,1000,600);
            this.addKeyListener(this);
            this.setLayout(null);
            this.setFocusable(true);
	}
	
	@Override
	public void paint(Graphics g) {
            try {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		this.drawBackground(g2);
                //---coins----
                g2.setFont(Element.getFont(30));
                g2.setColor(Color.black);
                g2.drawString("Coins : "+collectedCoins,750,80);
		//---POINT----
		g2.setFont(Element.getFont(30));
		g2.setColor(Color.black);
		g2.drawString("Times : "+times,753,40);
		//--- goofy --
		g2.setColor(Color.RED);
		drawGoofyHealth(g2);
		g2.drawImage(goofy.getImage(),goofy.x,goofy.y-78,goofySize,goofySize, null);
		//----SnakeLava----
		for(SnakeLava item : snakeLavaSet) {
                    drawSnakeLava(item,g2);
		}
                if (goofy.health <= 0) {
                endGame(collectedCoins);
                }
		this.times+=1;
                //-----Barnacle---
                for(Barnacle item1 : barnacleSet){
                    drawBarnacle(item1,g2);
                }
		this.times+=1;
                //-----Coin---
                for(Coin item2 : coinSet){
                    drawCoin(item2,g2);
                }
		this.times+=1;
                //-----Bee---
                for(Fly item3 : flySet){
                    drawFly(item3,g2);
                }
		this.times+=1;
            } catch (IOException e) {
            }
	}
	private void drawBackground(Graphics2D g2) throws IOException {
            g2.drawImage(ImageIO.read(new File("data\\sky.png")),0,0,1000,900, null);
            g2.drawImage(cloud3.getImage(),cloud3.x,120,150,80,null);
            g2.drawImage(cloud2.getImage(),cloud2.x,220,190,90,null);
            g2.drawImage(ImageIO.read(new File("data\\floor.png")),-100,-33,1138,800, null);
            g2.drawImage(ImageIO.read(new File("data\\water.png")),-100,162,1158,700, null);
            for(Cloud item:cloud1Set) {
		g2.drawImage(item.getImage(),item.x,item.y,160,80, null);
            }
	}
	
	private void drawGoofyHealth(Graphics2D g2) {
            try {
		g2.drawImage(ImageIO.read(new File("data\\heart.png")),10,20, 20,20,null);
		g2.setStroke(new BasicStroke(18.0f));
		g2.setColor(new Color(241, 98, 69));
		g2.drawLine(60, 30,60+goofy.health,30);	
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(6.0f));
		g2.drawRect(50,20, 200,20);
            } catch (IOException e) {
            }
	}
	
	private SnakeLava[] makeSnakeLava(int size) {
            SnakeLava[] snakeLavaSet = new SnakeLava[size];
            int far = 500;
            for(int i=0;i<size;i++) {
		snakeLavaSet[i] = new SnakeLava(xStart+far,base,speed,this);
		far+=500;
            }
            return snakeLavaSet;
	}
        
        private Barnacle[] makeBarnacle(int size) {
            Barnacle[] barnacleSet = new Barnacle[size];
            int far = 800;
            for(int i=0;i<size;i++) {
		barnacleSet[i] = new Barnacle(xStart+far,base,speed,this);
		far+=800;
            }
            return barnacleSet;
	}
	
	private Cloud[] makeCloud(int size,int eType){
            Cloud[] envSet = new Cloud[size];
            int far = 0;
            for(int i=0;i<size;i++) {
                envSet[i] = new Cloud(xStart+far,20,this,eType,10);
		far+=600;
            }
            return envSet;
	}
        
        private Coin[] makeCoin(int size) {
            Coin[] coinSet = new Coin[size];
            int far = 1100;
            for(int i=0;i<size;i++) {
                int value = (int) (1 + Math.floor(Math.random() * 5)); // Assign a random value to the coin
		coinSet[i] = new Coin(xStart+far,base,speed,value,this);
		far+=1100;
            }
            return coinSet;
	}
        
        private Fly[] makeFly(int size) {
            Fly[] flySet = new Fly[size];
            int far = 1400;
            for(int i=0;i<size;i++) {
		flySet[i] = new Fly(xStart+far,base,speed,this);
		far+=1600;
            }
            return flySet;
	}

	
	private void drawSnakeLava(SnakeLava snakeLava,Graphics2D g2) {
            g2.drawImage(snakeLava.getImage(),snakeLava.x ,(snakeLava.y-138),40,snakeLavaHeight+20,null);
            if(Event.checkHit(goofy,snakeLava,goofySize,snakeLavaHeight)){
                g2.setColor(new Color(241, 98, 69));
                g2.fillRect(0, 0,1000,1000);			
                goofy.health-=10;
		if(goofy.health<=0) {
                    pp.endGame(this.collectedCoins);
                    goofy.health = new Goofy().health;
                    this.times = 0;	 
		}
            }
	}
        
        private void drawBarnacle(Barnacle barnacle,Graphics2D g2) {
            g2.drawImage(barnacle.getImage(),barnacle.x ,(barnacle.y-108),50,barnacleHeight+10,null);
            if(Event.checkHit(goofy,barnacle,goofySize,barnacleHeight)){
                g2.setColor(new Color(241, 98, 69));
                g2.fillRect(0, 0,1000,1000);			
                goofy.health-=5;
		if(goofy.health<=0) {
                    pp.endGame(this.collectedCoins);
                    goofy.health = new Goofy().health;
                    this.times = 0;	 
		}
            }
	}
        
        private void drawCoin(Coin coin,Graphics2D g2) {
            g2.drawImage(coin.getImage(),coin.x ,(coin.y-116),40,coinHeight+10,null);
            if(Event.checkHit(goofy,coin,goofySize,coinHeight,coinWidth)){
		collectedCoins += 5;  // Collect the coin
                coin.x = -1000;  // Move the collected coin off-screen	 	
            }
	}
        
        private void drawFly(Fly fly,Graphics2D g2) {
            g2.drawImage(fly.getImage(),fly.x ,(fly.y-120),50,flyHeight+10,null);
            if(Event.checkHit(goofy,fly,goofySize,flyHeight)){
                g2.setColor(new Color(241, 98, 69));
                g2.fillRect(0, 0,1000,1000);			
                collectedCoins -= 2;
		if(goofy.health<=0) {
                    pp.endGame(this.collectedCoins);
                    goofy.health = new Goofy().health;
                    this.times = 0;	 
		}
            }
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
            if(System.currentTimeMillis() - lastPress > 600) {
		if(e.getKeyCode()==32||e.getKeyCode()==38) {
                    goofy.jump(this);
                    lastPress = System.currentTimeMillis();
		}
            }
	}

	@Override
	public void keyTyped(KeyEvent e) {
            //---
	}

	@Override
	public void keyReleased(KeyEvent e) {
            //---
	}
    }
    
    public class Menu extends JPanel {

        public long collectedCoins;
        
        
        Menu() {
            //----
        }
		
        public Menu(long collectedCoins,ActionListener main) {
            try {
                this.collectedCoins = collectedCoins;
		this.setBackground(new Color(255, 99, 216));
		this.setBounds(0,0,1000,600);
		this.setFocusable(true);
		this.setLayout(null);
					
		EleLabel status = new EleLabel("You Died!",40,400,100,200,100);
		status.setForeground(Color.white);
					
		EleLabel showCoins = new EleLabel("Total : "+this.collectedCoins,30,400,200,200,100);
		showCoins.setForeground(Color.white);
										
		EleButton restart = new EleButton("Play again",15,380,300,200,50);
		restart.addActionListener(main);		
					
		this.add(showCoins);
		this.add(status);
		this.add(restart);
            } catch (Exception e) {
            }
			
	}

            
    }    
    
    public class EleLabel extends JLabel {

        public EleLabel(String title,int size,int x,int y,int w,int h) {
            super(title);
            setFont(Element.getFont(size));
            setBounds(x,y,w,h);
        }          
    }
    
    public static void main(String[] args) {
        ProjectOOP oop = new ProjectOOP();
        oop.setTitle("Goofy coins" );
        oop.setSize(1000,600);
        oop.setDefaultCloseOperation(EXIT_ON_CLOSE);
        oop.setLocationRelativeTo(null);
        oop.setVisible(true);
    }
    
}
