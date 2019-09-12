import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Font;

public class Items
{
	private int x;
	private int y;
	private int index;
	private int price;
	private int value;
	private int xL = 0;
	private int xL2 = 0;
	private boolean bought = false;
	private boolean store = false;
	private boolean utilized = false;
	private String text;
	private String name;
	private BufferedImage pic;
	
	public Items(int x,int y,int index,int price,int value,String text,String name)
	{
		this.x = x;
		this.y = y;
		this.index = index;
		this.price = price;
		this.value = value;
		this.text = text;
		this.name = name;
		try{pic = ImageIO.read(getClass().getClassLoader().getResource("resources/"+name+".png"));} 
		catch (IOException e) {}
	}
	public void drawMe(Graphics g)
	{
		Font arialI = new Font("Arial",Font.ITALIC,40);
		g.setColor(Color.red);
		g.fillRect(x,y,xL,75);
		g.setColor(Color.blue);
		g.fillRect(x+480-xL2,y+13,xL2,24);
		if(bought)
		{
			if(xL <= 450)
				xL += 30;
		}
		else if(xL >= 30)
			xL -= 30;
		if(utilized)
		{
			if(xL2 <= 450)
				xL2 += 30;
		}
		else if(xL2 >= 30)
			xL2 -= 30;
		Color grey = new Color(150,150,150);
		g.setColor(grey);
		g.fillRect(x,y,50,50);
		g.drawImage(pic,x,y,50,50,null);
		if(store)
			Screen.buttons.get(index).drawMe(g);
		g.setColor(Color.white);
		Font arial = new Font("Arial",Font.PLAIN,20);
		g.setFont(arial);
		g.drawString(name+" - "+price+text,x,y+75);
		g.setFont(arialI);
		if(!bought && store)
			g.drawString("Buy",x+222,y+38);
		else if(store)
			g.drawString("Sell",x+220,y+38);
		g.drawLine(x,y+80,x+480,y+80);
	}
	public boolean getBought()
	{
		return bought;
	}
	public void setBought(boolean z)
	{
		bought = z;
	}
	public int getPrice()
	{
		return price;
	}
	public void setPrice(int z)
	{
		price = z;
	}
	public int getIndex()
	{
		return index;
	}
	public int getY()
	{
		return y;
	}
	public int getX()
	{
		return x;
	}
	public int getValue()
	{
		return value;
	}
	public void changeIndex(int z)
	{
		index += z;
	}
	public void moveY(int z)
	{
		y += z;
	}
	public void moveX(int z)
	{
		y += z;
	}
	public void addButton()
	{
		Screen.buttons.add(new Button(x+150,y,225,50));
	}
	public void setStore(boolean z)
	{
		store = z;
	}
	public void setUtilized(boolean z)
	{
		utilized = z;
	}
	public boolean getUtilized()
	{
		return utilized;
	}
}