import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Button
{
	private int x;
	private int y;
	private int introX = 0;
	private int introXA = 11;
	private int w;
	private int h;
	private int xL;
	private boolean introXGo = false;
	private boolean once = false;
	
	public Button(int x,int y,int w,int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		xL = (int)(w/3.6);
	}
	public void drawMe(Graphics g)
	{
		int[] arrX1 = new int[] {x+25-introX,x+w+introX,x+w-25+introX,x-introX};
        int[] arrY1 = new int[] {y,y,y+h,y+h};
        g.setColor(Color.black);
        g.fillPolygon(arrX1,arrY1,4);
        g.setColor(Color.white);
        g.drawPolygon(arrX1,arrY1,4);
        change();
	}
	public void change()
	{
		if(introX < xL && introXGo)
        {
        	introX += introXA;
        	if(introXA > 1)
        		introXA --;
        }
        if(introX > 0 && !introXGo)
        {
        	introX -= introXA;
        	if(introXA > 1)
        		introXA --;
        }
	}
	public void setIntroXGo(boolean x)
	{
		introXGo = x;
	}
	public void resetIntroXA()
	{
		introXA = 11;
	}
	public int getX()
	{
		return x-introX;
	}
	public int getY()
	{
		return y;
	}
	public int getW()
	{
		return x+w+introX;
	}
	public int getH()
	{
		return y+h;
	}
	public boolean getOnce()
	{
		return once;
	}
	public void setOnce(boolean x)
	{
		once = x;
	}
}