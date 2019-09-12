import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class EnemyA extends Enemy
{
	private BufferedImage[] me = new BufferedImage[3];
	private BufferedImage hit;
	private int a = 0;
	
	public EnemyA(int x,int y)
	{
		super(x,y,50,25,2,30,"SpinnerA");
		
		try{me[0] = ImageIO.read(getClass().getClassLoader().getResource("resources/SpinnerA1.png"));} 
		catch (IOException e) {}
		try{me[1] = ImageIO.read(getClass().getClassLoader().getResource("resources/SpinnerA2.png"));} 
		catch (IOException e) {}
		try{me[2] = ImageIO.read(getClass().getClassLoader().getResource("resources/SpinnerA3.png"));} 
		catch (IOException e) {}
		try{hit = ImageIO.read(getClass().getClassLoader().getResource("resources/SpinnerHit.png"));} 
		catch (IOException e) {}
	}
	public void drawMe(Graphics g)
	{
		g.drawImage(me[a],super.getX(),super.getY(),super.getW(),super.getH(),null);
		super.drawMe(g);
		
		if(a < 2)
			a ++;
		else
			a = 0;
	}
}