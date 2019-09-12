import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Enemy
{
	private int x;
	private int y;
	private int w;
	private int h;
	private int lastX;
	private int lastY;
	private int s;
	private String name;
	private int health;
	private final int maxHealth;
	private int f = 2;
	private int knockBack = 0;
	
	public Enemy(int x,int y,int w,int h,int s,int health,String name)
	{
		this.x = x;
		lastX = x;
		this.y = y;
		lastY = y;
		this.w = w;
		this.h = h;
		this.s = s;
		this.health = health;
		maxHealth = health;
		this.name = name;
	}
	public void drawMe(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(x+w/2-health,y-10,maxHealth*2,5);
		g.setColor(Color.blue);
		g.fillRect(x+w/2-health,y-10,health*2,5);
	}
	public void reset(int x,int y)
	{
		this.x = x;
		this.y = y;
		lastX = x;
		lastY = y;
	}

	public void moveR()
	{
		f = 4;
		lastX = x;
		if(x < Screen.screenW - w)
			x += s;
	}
	public void moveL()
	{
		f = 3;
		lastX = x;
		if(x > 0)
			x -= s;
	}
	public void moveU()
	{
		f = 1;
		lastY = y;
		if(y > 0)
			y -= s;
	}
	public void moveD()
	{
		f = 2;
		lastY = y;
		if(y < Screen.screenH - h)
			y += s;
	}
	public void moveRKB()
	{
		lastX = x;
		if(x < Screen.screenW - w)
			x += 12;
	}
	public void moveLKB()
	{
		lastX = x;
		if(x > 0)
			x -= 12;
	}
	public void moveUKB()
	{
		lastY = y;
		if(y > 0)
			y -= 12;
	}
	public void moveDKB()
	{
		lastY = y;
		if(y < Screen.screenH - h)
			y += 12;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getLastX()
	{
		return lastX;
	}
	public int getLastY()
	{
		return lastY;
	}
	public int getW()
	{
		return w;
	}
	public int getH()
	{
		return h;
	}
	public int getF()
	{
		return f;
	}
	public String getName()
	{
		return name;
	}
	public int getHealth()
	{
		return health;
	}
	public void changeHealth(int z)
	{
		health += z;
	}
	public int getKnockBack()
	{
		return knockBack;
	}
	public void setKnockBack(int z)
	{
		knockBack = z;
	}
	public void doKnockBack(int x1,int y1)
	{
		if(y-y1 > 0)
			moveDKB();
		else if(y-y1 < 0)
			moveUKB();
		if(x-x1 > 0)
			moveRKB();
		else if(x-x1 < 0)
			moveLKB();
	}
	public void track(int x1,int y1)
	{
		if(x-x1 > 1)
			moveL();
		else if(x-x1 < -1)
			moveR();
		if(y-y1 > 1)
			moveU();
		else if(y-y1 < -1)
			moveD();
	}
	public boolean cc(int x1,int y1,int w1,int h1)
	{
		if(x+w >= x1 && x <= x1 + w1)
		{
			if(y + h >= y1 && y <= y1 + h1)
			{
				return true;
			}
		}
		return false;
	}
	public void ccMove(int x1,int y1,int w1,int h1)
	{
		if(x+w >= x1 && x <= x1 + w1)
		{
			if(y + h >= y1 && y <= y1 + h1)
			{
				x = lastX;
				y = lastY;
			}
		}
	}
}