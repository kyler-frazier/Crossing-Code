import java.awt.Graphics;
public class DefenseItem extends Items
{
	public DefenseItem(int x,int y,int index,int price,int value,String text,String name)
	{
		super(x,y,index,price,value,text,name);
	}
	public void drawMe(Graphics g)
	{
		super.drawMe(g);
	}
	public void setUtilized(boolean z)
	{
		if(z && !super.getUtilized())
			Kirito.defense += (double)(super.getValue());
		else if(!z && super.getUtilized())
			Kirito.defense -= (double)(super.getValue());
		super.setUtilized(z);
	}
}