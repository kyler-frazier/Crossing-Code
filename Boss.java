import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Boss extends Enemy
{
	private BufferedImage[] me = new BufferedImage[3];
	private BufferedImage[] me1 = new BufferedImage[4];
	private BufferedImage hit;
	private int a = 0;
	
	public Boss(int x,int y)
	{
		super(x,y,50,25,3,250,"Boss");
		
		try{me[0] = ImageIO.read(getClass().getClassLoader().getResource("resources/Boss1.png"));} 
		catch (IOException e) {}
		try{me[1] = ImageIO.read(getClass().getClassLoader().getResource("resources/Boss2.png"));} 
		catch (IOException e) {}
		try{me[2] = ImageIO.read(getClass().getClassLoader().getResource("resources/Boss3.png"));} 
		catch (IOException e) {}
		
		try{me1[0] = ImageIO.read(getClass().getClassLoader().getResource("resources/BossUp.png"));} 
		catch (IOException e) {}
		try{me1[1] = ImageIO.read(getClass().getClassLoader().getResource("resources/BossDown.png"));} 
		catch (IOException e) {}
		try{me1[2] = ImageIO.read(getClass().getClassLoader().getResource("resources/BossLeft.png"));} 
		catch (IOException e) {}
		try{me1[3] = ImageIO.read(getClass().getClassLoader().getResource("resources/BossRight.png"));} 
		catch (IOException e) {}
		
		try{hit = ImageIO.read(getClass().getClassLoader().getResource("resources/SpinnerHit.png"));} 
		catch (IOException e) {}
	}
	public void drawMe(Graphics g)
	{
		g.drawImage(me[a],super.getX(),super.getY(),super.getW(),super.getH(),null);
		super.drawMe(g);
		g.drawImage(me1[super.getF()-1],super.getX()+15,super.getY()-23,20,30,null);
		
		if(a < 2)
			a ++;
		else
			a = 0;
	}
}