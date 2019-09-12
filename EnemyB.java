import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class EnemyB extends Enemy
{
	private BufferedImage me;
	
	public EnemyB(int x,int y)
	{
		super(x,y,20,20,1,10,"SpinnerB");
		
		try{me = ImageIO.read(getClass().getClassLoader().getResource("resources/"+super.getName()+".png"));} 
		catch (IOException e) {}
	}
	public void drawMe(Graphics g)
	{
		g.drawImage(me,super.getX(),super.getY(),super.getW(),super.getH(),null);
		super.drawMe(g);
	}
}