import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Kirito
{
	private int x;
	private int y;
	private int w = 20;
	private int h = 30;
	private int f; // up - 1, down - 2, left - 3, right - 4
	private int lastX;
	private int lastY;
	private int a = 2;
	private int s = 1;
	private int punchTime = 0;
	private int coolDown = 0;
	public static int health = 100;
	public static int maxHealth = 100;
	public static int power = 4;
	public static double defense = 0;
	private int knockBack = 0;
	
	private boolean punching = false;
	
	private BufferedImage up1;
	private BufferedImage up2;
	private BufferedImage up3;
	private BufferedImage up11;
	private BufferedImage up22;
	private BufferedImage up33;
	private BufferedImage down1;
	private BufferedImage down2;
	private BufferedImage down3;
	private BufferedImage down11;
	private BufferedImage down22;
	private BufferedImage down33;
	private BufferedImage left1;
	private BufferedImage left2;
	private BufferedImage left3;
	private BufferedImage left11;
	private BufferedImage left22;
	private BufferedImage left33;
	private BufferedImage right1;
	private BufferedImage right2;
	private BufferedImage right3;
	private BufferedImage right11;
	private BufferedImage right22;
	private BufferedImage right33;
	private BufferedImage pose;
	private BufferedImage punchUp1;
	private BufferedImage punchUp2;
	private BufferedImage punchDown1;
	private BufferedImage punchDown2;
	private BufferedImage punchLeft1;
	private BufferedImage punchLeft2;
	private BufferedImage punchRight1;
	private BufferedImage punchRight2;
	
	public Kirito(int x,int y,int f)
	{
		this.x = x;
		this.y = y;
		this.f = f;
		lastX = x;
		lastY = y;
		
		try{up1 = ImageIO.read(getClass().getClassLoader().getResource("resources/Up 1.png"));} 
		catch (IOException e) {}
		try{up2 = ImageIO.read(getClass().getClassLoader().getResource("resources/Up 2.png"));} 
		catch (IOException e) {}
		try{up3 = ImageIO.read(getClass().getClassLoader().getResource("resources/Up 3.png"));} 
		catch (IOException e) {}
		try{up11 = ImageIO.read(getClass().getClassLoader().getResource("resources/Up 11.png"));} 
		catch (IOException e) {}
		try{up22 = ImageIO.read(getClass().getClassLoader().getResource("resources/Up 22.png"));} 
		catch (IOException e) {}
		try{up33 = ImageIO.read(getClass().getClassLoader().getResource("resources/Up 33.png"));} 
		catch (IOException e) {}
		try{down1 = ImageIO.read(getClass().getClassLoader().getResource("resources/Down 1.png"));} 
		catch (IOException e) {}
		try{down2 = ImageIO.read(getClass().getClassLoader().getResource("resources/Down 2.png"));} 
		catch (IOException e) {}
		try{down3 = ImageIO.read(getClass().getClassLoader().getResource("resources/Down 3.png"));} 
		catch (IOException e) {}
		try{down11 = ImageIO.read(getClass().getClassLoader().getResource("resources/Down 11.png"));} 
		catch (IOException e) {}
		try{down22 = ImageIO.read(getClass().getClassLoader().getResource("resources/Down 22.png"));} 
		catch (IOException e) {}
		try{down33 = ImageIO.read(getClass().getClassLoader().getResource("resources/Down 33.png"));} 
		catch (IOException e) {}
		try{left1 = ImageIO.read(getClass().getClassLoader().getResource("resources/Left 1.png"));} 
		catch (IOException e) {}
		try{left2 = ImageIO.read(getClass().getClassLoader().getResource("resources/Left 2.png"));} 
		catch (IOException e) {}
		try{left3 = ImageIO.read(getClass().getClassLoader().getResource("resources/Left 3.png"));} 
		catch (IOException e) {}
		try{left11 = ImageIO.read(getClass().getClassLoader().getResource("resources/Left 11.png"));} 
		catch (IOException e) {}
		try{left22 = ImageIO.read(getClass().getClassLoader().getResource("resources/Left 22.png"));} 
		catch (IOException e) {}
		try{left33 = ImageIO.read(getClass().getClassLoader().getResource("resources/Left 33.png"));} 
		catch (IOException e) {}
		try{right1 = ImageIO.read(getClass().getClassLoader().getResource("resources/Right 1.png"));} 
		catch (IOException e) {}
		try{right2 = ImageIO.read(getClass().getClassLoader().getResource("resources/Right 2.png"));} 
		catch (IOException e) {}
		try{right3 = ImageIO.read(getClass().getClassLoader().getResource("resources/Right 3.png"));} 
		catch (IOException e) {}
		try{right11 = ImageIO.read(getClass().getClassLoader().getResource("resources/Right 11.png"));} 
		catch (IOException e) {}
		try{right22 = ImageIO.read(getClass().getClassLoader().getResource("resources/Right 22.png"));} 
		catch (IOException e) {}
		try{right33 = ImageIO.read(getClass().getClassLoader().getResource("resources/Right 33.png"));} 
		catch (IOException e) {}
		try{pose = ImageIO.read(getClass().getClassLoader().getResource("resources/Pose.png"));} 
		catch (IOException e) {}
		try{punchUp1 = ImageIO.read(getClass().getClassLoader().getResource("resources/Punch Up 1.png"));} 
		catch (IOException e) {}
		try{punchUp2 = ImageIO.read(getClass().getClassLoader().getResource("resources/Punch Up 2.png"));} 
		catch (IOException e) {}
		try{punchDown1 = ImageIO.read(getClass().getClassLoader().getResource("resources/Punch Down 1.png"));} 
		catch (IOException e) {}
		try{punchDown2 = ImageIO.read(getClass().getClassLoader().getResource("resources/Punch Down 2.png"));} 
		catch (IOException e) {}
		try{punchLeft1 = ImageIO.read(getClass().getClassLoader().getResource("resources/Punch Left 1.png"));} 
		catch (IOException e) {}
		try{punchLeft2 = ImageIO.read(getClass().getClassLoader().getResource("resources/Punch Left 2.png"));} 
		catch (IOException e) {}
		try{punchRight1 = ImageIO.read(getClass().getClassLoader().getResource("resources/Punch Right 1.png"));} 
		catch (IOException e) {}
		try{punchRight2 = ImageIO.read(getClass().getClassLoader().getResource("resources/Punch Right 2.png"));} 
		catch (IOException e) {}
	}
	public void reset(int x,int y,int f)
	{
		this.x = x;
		this.y = y;
		this.f = f;
		lastX = x;
		lastY = y;
	}

	public void moveR(int z)
	{
		lastX = x;
		if(x < Screen.screenW - w)
		{
			if(s == 1)
				x += z;
			if(s == 2)
				x += 2*z;
		}
	}
	public void moveL(int z)
	{
		lastX = x;
		if(x > 0)
		{
			if(s == 1)
				x -= z;
			if(s == 2)
				x -= 2*z;
		}
	}
	public void moveU(int z)
	{
		lastY = y;
		if(y > 0)
		{
			if(s == 1)
				y -= z;
			if(s == 2)
				y -= 2*z;
		}
	}
	public void moveD(int z)
	{
		lastY = y;
		if(y < Screen.screenH - h)
		{
			if(s == 1)
				y += z;
			if(s == 2)
				y += 2*z;
		}
	}
	
	public void drawMe(Graphics g)
	{
		if(f == 1)
		{
			if(punching)
			{
				if(punchTime >= 0 && punchTime <= 9)
					g.drawImage(punchDown1,x,y,null);
				else if(punchTime >= 10 && punchTime <= 19)
					g.drawImage(punchRight1,x,y,null);
				else if(punchTime >= 20 && punchTime <= 30)
					g.drawImage(punchUp1,x,y,null);
				else
				{
					if(punchTime == 31)
						coolDown = 1;
					g.drawImage(up2,x,y,null);
					f = 1;
					punching = false;
					punchTime = 0;
				}
			}
			if(!punching)
			{
				if(s == 1)
				{
					if(a == 1)
						g.drawImage(up1,x,y,null);
					if(a == 2)
						g.drawImage(up2,x,y,null);
					if(a == 3)
						g.drawImage(up3,x,y,null);
				}
				if(s == 2)
				{
					if(a == 1)
						g.drawImage(up11,x,y,null);
					if(a == 2)
						g.drawImage(up22,x,y,null);
					if(a == 3)
						g.drawImage(up33,x,y,null);
				}
			}
		}
		else if(f == 2)
		{
			if(punching)
			{
				if(punchTime >= 0 && punchTime <= 9)
					g.drawImage(punchUp1,x,y,null);
				else if(punchTime >= 10 && punchTime <= 19)
					g.drawImage(punchLeft1,x,y,null);
				else if(punchTime >= 20 && punchTime <= 30)
					g.drawImage(punchDown1,x,y,null);
				else
				{
					if(punchTime == 31)
						coolDown = 1;
					g.drawImage(down2,x,y,null);
					f = 2;
					punching = false;
					punchTime = 0;
				}
			}
			if(!punching)
			{
				if(s == 1)
				{
					if(a == 1)
						g.drawImage(down1,x,y,null);
					if(a == 2)
						g.drawImage(down2,x,y,null);
					if(a == 3)
						g.drawImage(down3,x,y,null);
				}
				if(s == 2)
				{
					if(a == 1)
						g.drawImage(down11,x,y,null);
					if(a == 2)
						g.drawImage(down22,x,y,null);
					if(a == 3)
						g.drawImage(down33,x,y,null);
				}
			}
		}
		else if(f == 3)
		{
			if(punching)
			{
				if(punchTime >= 0 && punchTime <= 9)
					g.drawImage(punchRight1,x,y,null);
				else if(punchTime >= 10 && punchTime <= 19)
					g.drawImage(punchUp1,x,y,null);
				else if(punchTime >= 20 && punchTime <= 30)
					g.drawImage(punchLeft1,x,y,null);
				else
				{
					if(punchTime == 31)
						coolDown = 1;
					g.drawImage(left2,x,y,null);
					f = 3;
					punching = false;
					punchTime = 0;
				}
			}
			if(!punching)
			{
				if(s == 1)
				{
					if(a == 1)
						g.drawImage(left1,x,y,null);
					if(a == 2)
						g.drawImage(left2,x,y,null);
					if(a == 3)
						g.drawImage(left3,x,y,null);
				}
				if(s == 2)
				{
					if(a == 1)
						g.drawImage(left11,x,y,null);
					if(a == 2)
						g.drawImage(left22,x,y,null);
					if(a == 3)
						g.drawImage(left33,x,y,null);
				}
			}
		}
		else if(f == 4)
		{
			if(punching)
			{
				if(punchTime >= 0 && punchTime <= 9)
					g.drawImage(punchLeft1,x,y,null);
				else if(punchTime >= 10 && punchTime <= 19)
					g.drawImage(punchDown1,x,y,null);
				else if(punchTime >= 20 && punchTime <= 30)
					g.drawImage(punchRight1,x,y,null);
				else
				{
					if(punchTime == 31)
						coolDown = 1;
					g.drawImage(right2,x,y,null);
					f = 4;
					punching = false;
					punchTime = 0;
					
				}
			}
			if(!punching)
			{
				if(s == 1)
				{
					if(a == 1)
						g.drawImage(right1,x,y,null);
					if(a == 2)
						g.drawImage(right2,x,y,null);
					if(a == 3)
						g.drawImage(right3,x,y,null);
				}
				if(s == 2)
				{
					if(a == 1)
						g.drawImage(right11,x,y,null);
					if(a == 2)
						g.drawImage(right22,x,y,null);
					if(a == 3)
						g.drawImage(right33,x,y,null);
				}
			}
		}
		else
			g.drawImage(pose,x,y,null);
		if(coolDown > 0)
			coolDown ++;
		if(coolDown == 30)
			coolDown = 0;
	}
	public void setF(int f)
	{
		this.f = f;
	}
	public int getF()
	{
		return f;
	}
	public void setA(int a)
	{
		this.a = a;
	}
	public void setS(int s)
	{
		this.s = s;
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
	public int getPunchTime()
	{
		return punchTime;
	}
	public int getCoolDown()
	{
		return coolDown;
	}
	public int getHealth()
	{
		return health;
	}
	public void changeHealth(int z)
	{
		health += z;
		health += -(int)((defense/100)*z);
//		System.out.println("Health: "+health+"\nMaxHealth: "+maxHealth+"\nPower: "+power+"\nDefense: "+defense+"\nDef2: "+(int)((defense/100)*z)+"\n-----");
	}
	public int getPower()
	{
		return power;
	}
	public void changePower(int z)
	{
		power += z;
	}
// ----------------------------------------
	
	
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
// ----------------------------------------

	public void punch()
	{
/*		System.out.print(punchTime + ", ");
		if(punchTime == 30)
			System.out.println("\n");		*/
		
		punching = true;
		punchTime ++;
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