// Future implementation: change BattlemMusic to start when enemy array is not empty, instead of from transition cues. Is more stable.

import java.awt.*; 
import java.applet.*; 
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Screen extends JPanel implements MouseListener, KeyListener, MouseMotionListener
{
//Variables
	public static final int screenW = 1000;
	public static final int screenH = 600;

	private BufferedImage buffered;
	private BufferedImage me;
	private BufferedImage hospitalBed;
	private BufferedImage hospitalCurtain;
	private BufferedImage hospitalCurtain2;
	private BufferedImage nurse;
	private BufferedImage nurseTalk;
	private BufferedImage openingImage1;
	private BufferedImage openingImage2;
	private BufferedImage title;
	private BufferedImage wasdKeys;
	private BufferedImage eKey;
	private BufferedImage spaceKey;
	private BufferedImage leftClick;
	private BufferedImage escKey;
	private BufferedImage shiftKey;
	private BufferedImage hospitalTable;
	private BufferedImage villagePic;
	private BufferedImage storePic;
	private BufferedImage grassPic;
	private BufferedImage gate;
	private BufferedImage keyPic;
	private BufferedImage lab;
	private BufferedImage bossPic;
	private BufferedImage serena;
	private BufferedImage serenaBlood;
	private BufferedImage serenaBed;
	
	private Clip intro;
	private Clip bossMusic;
	private Clip battle;
	private Clip plain;
	private Clip win;
	private Clip money;
	private Clip enemyHit;
	private Clip punchHit;
	private Clip punchMiss;
	
	private boolean done = false;
	private boolean endPage = false;
	private boolean endPage1 = false;
	private boolean move = false;
	private boolean hospital = false;
	private boolean startPage = true;
	private boolean talking = false;
	private boolean startNurseTalk = false;
	private boolean introXGo = false;
	private boolean introXGo2 = false;
	private boolean instructions = false;
	private boolean nurseTalkDone = false;
	private boolean punching = false;
	private boolean unFade = false;
	private boolean village = false;
	private boolean store = false;
	private boolean cashier = false;
	private boolean inventory = false;
	private boolean grass = false;
	private boolean level1Done = false;
	private boolean level2Done = false;
	private boolean cheat1 = false;
	private boolean cheat2 = false;
	private boolean cheat3 = false;
	private boolean cheat4 = false;
	private boolean key = false;
	private boolean last = false;
	public static boolean dead = false;
	private boolean startBossTalk = false;
	private boolean bossTalkDone = false;
	private boolean level3Done = false;
	
	private Input input = new Input(this); 
	private Kirito kirito;
	public static ArrayList<Button> buttons = new ArrayList<Button>();
	private ArrayList<AttackItem> attackItems = new ArrayList<AttackItem>();
	private ArrayList<DefenseItem> defenseItems= new ArrayList<DefenseItem>();
	private ArrayList<HealthItem> healthItems = new ArrayList<HealthItem>();
	private ArrayList<Items> kiritoItems = new ArrayList<Items>();
	private ArrayList<EnemyB> eB = new ArrayList<EnemyB>();
	private ArrayList<EnemyA> eA = new ArrayList<EnemyA>();
	private Boss boss = new Boss(475,300);
	
	private double stepTime = 3;
	private double stepTimeChange = .1;
	
	private String[] nurseTalkArr = new String[] {
	"Nurse: Kirito! I'm glad to see you awake now! How do you feel?",
	"Me: Okay I guess... why am I in a hospital?",
	"Nurse: You were found uncounsious outside...",
	"Nurse: The recent drone attack was so violent...",
	"Nurse: We're just glad you didn't have serious injuri-",
	"Me: Wait... Where is Serena?!",
	"Nurse: I'm sorry, who?",
	"Me: Was there anyone with me when I was found?",
	"Nurse: No, you were alone.",
	"Me: ..."};
	private String[] bossTalkArr = new String[] {
	"Kirito: Serena! What's going on?!",
	"Serena: Kirito, run!",
	"???: You should take her advice and run if you value your life, boy.",
	"Kirito: Don't worry Serena, I'll get you out of here!",
	"???: Ohh, you've come to her rescue have you?",
	"Kirito: Wh- Who are you?? Why did you take her??",
	"???: I don't take questions from kids playing hero...",
	"???: This is my final warning. Leave.. NOW!",
	"Serena: Kirito! This is related to my family's history! ...",
	"Serena: If you get any more involved, you-",
	"???: SHUT IT! ... You've said enough...",
	"???: Looks like I'm going to have to take care of this pest myself.",
	"Kirito: ...",
	"???: ..."};
	private String text = "";	
	
	private int textNum = 0;
	private int textLine = 0;
	private int fade = 0;
	private int fade2 = 255;
	private int coins = 0;
	private int attack = 5;
	private int blood2 = 0;
	
	
//Constructor
	public Screen()
    {
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		kirito = new Kirito(40,180,2);
		buttons.add(new Button(100,250,275,50));
		buttons.add(new Button(100,350,275,50));
		
		attackItems.add(new AttackItem(10,160,1,30,20," coins - Attack +20","Elucidator"));
    	attackItems.add(new AttackItem(10,250,2,20,10," coins - Attack +10","Piercer"));
    	attackItems.add(new AttackItem(10,340,3,12,2," coins - Attack +2","Old Sword"));
    	defenseItems.add(new DefenseItem(10,430,4,30,40," coins - Defense +40%","Resonator"));
    	defenseItems.add(new DefenseItem(10,520,5,20,20," coins - Defense +20%","Guard"));
    	defenseItems.add(new DefenseItem(510,160,6,12,4," coins - Defense +4%","Old Shield"));
    	healthItems.add(new HealthItem(510,250,7,30,80," coins - Max and Current Health +80","Healer"));
    	healthItems.add(new HealthItem(510,340,8,20,40," coins - Max and Current Health +40","Potion"));
    	healthItems.add(new HealthItem(510,430,9,12,8," coins - Max and Current Health +8","Old Bottle"));
		
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/Intro.wav");
            intro = AudioSystem.getClip();
            intro.open(AudioSystem.getAudioInputStream(url));
//			intro.loop(Clip.LOOP_CONTINUOUSLY); 
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/BossMusic.wav");
            bossMusic = AudioSystem.getClip();
            bossMusic.open(AudioSystem.getAudioInputStream(url));
//			bossMusic.loop(Clip.LOOP_CONTINUOUSLY);
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/Battle.wav");
            battle = AudioSystem.getClip();
            battle.open(AudioSystem.getAudioInputStream(url));
//			battle.loop(Clip.LOOP_CONTINUOUSLY); 
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/Plain.wav");
            plain = AudioSystem.getClip();
            plain.open(AudioSystem.getAudioInputStream(url));
//			plain.loop(Clip.LOOP_CONTINUOUSLY); 
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/Win.wav");
            win = AudioSystem.getClip();
            win.open(AudioSystem.getAudioInputStream(url));
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/Money.wav");
            money = AudioSystem.getClip();
            money.open(AudioSystem.getAudioInputStream(url));
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/EnemyHit.wav");
            enemyHit = AudioSystem.getClip();
            enemyHit.open(AudioSystem.getAudioInputStream(url));
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/PunchHit.wav");
            punchHit = AudioSystem.getClip();
            punchHit.open(AudioSystem.getAudioInputStream(url));
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/PunchMiss.wav");
            punchMiss = AudioSystem.getClip();
            punchMiss.open(AudioSystem.getAudioInputStream(url));
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
		
		playIntro();
		
	/*	try{xxx = ImageIO.read(getClass().getClassLoader().getResource("resources/xxx"));} 
		catch (IOException e) {}	*/
		try{gate = ImageIO.read(getClass().getClassLoader().getResource("resources/Gate.png"));} 
		catch (IOException e) {}
		try{hospitalBed = ImageIO.read(getClass().getClassLoader().getResource("resources/Hospital Bed.png"));} 
		catch (IOException e) {}
		try{hospitalCurtain = ImageIO.read(getClass().getClassLoader().getResource("resources/Curtain.png"));} 
		catch (IOException e) {}
		try{hospitalCurtain2 = ImageIO.read(getClass().getClassLoader().getResource("resources/Curtain2.png"));} 
		catch (IOException e) {}
		try{nurse = ImageIO.read(getClass().getClassLoader().getResource("resources/Nurse.png"));} 
		catch (IOException e) {}
		try{nurseTalk = ImageIO.read(getClass().getClassLoader().getResource("resources/NurseTalk.png"));} 
		catch (IOException e) {}
		try{me = ImageIO.read(getClass().getClassLoader().getResource("resources/Kirito.png"));} 
		catch (IOException e) {}
		try{openingImage1 = ImageIO.read(getClass().getClassLoader().getResource("resources/OpeningImage.png"));} 
		catch (IOException e) {}
		try{openingImage2 = ImageIO.read(getClass().getClassLoader().getResource("resources/OpeningImage2.png.jpg"));} 
		catch (IOException e) {}
		try{title = ImageIO.read(getClass().getClassLoader().getResource("resources/Title.png"));} 
		catch (IOException e) {}
		try{wasdKeys = ImageIO.read(getClass().getClassLoader().getResource("resources/WASD.png"));} 
		catch (IOException e) {}
		try{eKey = ImageIO.read(getClass().getClassLoader().getResource("resources/E.png"));} 
		catch (IOException e) {}
		try{spaceKey = ImageIO.read(getClass().getClassLoader().getResource("resources/Space Key.png"));} 
		catch (IOException e) {}
		try{leftClick = ImageIO.read(getClass().getClassLoader().getResource("resources/Left Click.png"));} 
		catch (IOException e) {}
		try{escKey = ImageIO.read(getClass().getClassLoader().getResource("resources/esc.png"));} 
		catch (IOException e) {}
		try{shiftKey = ImageIO.read(getClass().getClassLoader().getResource("resources/Shift Key.png"));} 
		catch (IOException e) {}
		try{hospitalTable = ImageIO.read(getClass().getClassLoader().getResource("resources/Hospital Table.png"));} 
		catch (IOException e) {}
		try{villagePic = ImageIO.read(getClass().getClassLoader().getResource("resources/Village.png"));} 
		catch (IOException e) {}
		try{storePic = ImageIO.read(getClass().getClassLoader().getResource("resources/Store.png"));} 
		catch (IOException e) {}
		try{grassPic = ImageIO.read(getClass().getClassLoader().getResource("resources/Grass.png"));} 
		catch (IOException e) {}
		try{keyPic = ImageIO.read(getClass().getClassLoader().getResource("resources/Key.png"));} 
		catch (IOException e) {}
		try{lab = ImageIO.read(getClass().getClassLoader().getResource("resources/Lab.png"));} 
		catch (IOException e) {}
		try{bossPic = ImageIO.read(getClass().getClassLoader().getResource("resources/BossPic.png"));} 
		catch (IOException e) {}
		try{serena = ImageIO.read(getClass().getClassLoader().getResource("resources/Serena.png"));} 
		catch (IOException e) {}
		try{serenaBed = ImageIO.read(getClass().getClassLoader().getResource("resources/SerenaBed.png"));} 
		catch (IOException e) {}
		try{serenaBlood = ImageIO.read(getClass().getClassLoader().getResource("resources/SerenaBlood.png"));} 
		catch (IOException e) {}
	}
	
//Dimension
	public Dimension getPreferredSize() 
    {
        return new Dimension(screenW,screenH);
    }
	
//Paint
	public void paintComponent(Graphics gb)
    {
        super.paintComponent(gb);
		if(buffered == null)
            buffered = (BufferedImage)(createImage(getWidth(),getHeight()));
        Graphics g = buffered.createGraphics();	
        Font font = new Font("Arial",Font.PLAIN,30);
        Font font2 = new Font("Arial",Font.PLAIN,40);
        Font font3 = new Font("Arial",Font.PLAIN,10);
        Font arial = new Font("Arial",Font.ITALIC,40);
        Font arial1 = new Font("Arial",Font.BOLD,40);
        Font arial2 = new Font("Arial",Font.BOLD,25);
        Font big = new Font("Arial",Font.BOLD,100);
        Color tan = new Color(230,200,160);
        Color grey = new Color(50,50,50);
        Color black1 = new Color(0,0,0,180);
		Color fadeImage = new Color(0,0,0,fade);
		Color fadeImage2 = new Color(0,0,0,fade2);
		Color blood = new Color(255,0,0,blood2);

		g.setColor(Color.blue);
        g.fillRect(0,0,screenW,screenH);
        
        if(unFade)
        {
        	if(fade > 10)
				fade -= 10;
			else
			{
				unFade = false;
				fade = 0;
			}
        }
        
        if(startPage)
        {
        	g.drawImage(openingImage2,0,0,screenW,screenH,null);
        	g.drawImage(openingImage1,screenW-566,screenH-400,null);
        	g.drawImage(title,50,50,900,100,null);
        	
        	buttons.get(0).drawMe(g);
			buttons.get(1).drawMe(g);
			
			g.setFont(arial);
			g.setColor(Color.white);
			g.drawString("Start",185,290);
			g.drawString("Instructions",135,390);
        }
        else if(instructions)
        {
        	g.setColor(grey);
        	g.fillRect(0,0,screenW,screenH);
        	g.setColor(Color.white);
        	g.drawLine(0,100,screenW,100);
        	g.drawLine(0,350,screenW,350);
        	g.drawLine(screenW/2,100,screenW/2,screenH);
        	g.setFont(arial1);
        	g.drawString("Instructions",650,65);
        	
        	g.setFont(font);
        	g.drawImage(wasdKeys,10,180,220,160,null);
//       	g.drawImage(shiftKey,270,252,220,88,null);
        	g.drawString("\"WASD\" keys to move",10,150);
//        	g.drawString("\"Shift\" key to run",260,230);
//        	g.drawImage(escKey,510,140,200,200,null);
//        	g.drawString("\"Esc\" key to pause",720,250);
			g.drawImage(shiftKey,510,252,220,88,null);
			g.drawString("\"Shift\" key to run",510,230);
        	g.drawImage(eKey,510,390,200,200,null);
        	g.drawString("\"E\" key to open bag",720,500);
        	g.drawImage(spaceKey,10,490,400,100,null);
        	g.drawString("\"Space\" to attack",10,450);
        	
        	buttons.get(0).drawMe(g);
        	g.setFont(arial);
        	g.drawString("Back",189,65);
        	
        	kirito.drawMe(g);
        }
        else if(hospital)
        {
			g.setColor(Color.white);
        	g.fillRect(0,0,screenW,150);
        	g.setColor(tan);
        	g.fillRect(0,150,screenW,screenH-150);
        	g.setColor(Color.black);
        	g.drawLine(0,150,screenW,150);
			g.drawImage(hospitalBed,610,150,60,60,null);
			g.drawImage(hospitalBed,610,210,60,60,null);
			g.drawImage(hospitalBed,610,270,60,60,null);
			g.setColor(grey);
			g.fillRect(screenW-20,400,20,30);
        	for(int curtain = 600;curtain < screenW;curtain += 50)
        	{
        		g.drawImage(hospitalCurtain,curtain,250,50,80,null);
        	}
        	for(int curtain2 = 200;curtain2 >= 100;curtain2 -= 50)
        	{
        		g.drawImage(hospitalCurtain2,600,curtain2,3,50,null);
        	}
        	g.drawImage(nurse,175,130,20,30,null);
        	kirito.drawMe(g);
			for(int bed = 150;bed < 570;bed += 60)
        	{
				g.drawImage(hospitalBed,30,bed,60,60,null);
				if(bed != 150)
					g.drawImage(hospitalTable,170,bed,50,30,null);
				else if(done)
					g.drawImage(serenaBed,40,150,30,20,null);
				if(kirito.getY() > bed)
					kirito.drawMe(g);
        	}
        	
        //Nurse Talk
			if(!nurseTalkDone && kirito.cc(180,160,10,05) && kirito.getF() == 1)
        	{
        		talking = true;
        		chatBox(175,130,20,g);
        	}
        	else
        		talking = false;
        	if(startNurseTalk)
        	{
        		g.setFont(font);
        		g.setColor(black1);
        		g.fillRect(0,0,screenW,screenH);
        		g.drawImage(me,50,screenH-367-200,null);
        		g.drawImage(nurseTalk,screenW-380,screenH-367-200,330,900,null);
        		g.setColor(Color.black);
        		g.fillRect(0,400,screenW,screenH);
        		g.setColor(Color.white);
        		if(textNum < nurseTalkArr[textLine].length())
        		{
        			text += nurseTalkArr[textLine].substring(textNum,textNum+1);
        			textNum ++;
        		}
        		g.drawString(text,10,440);
        		g.setFont(arial2);
        		g.drawRect(0,570,420,29);
        		g.drawString("Hit \"Enter/Return\" to continue >>>",5,595);
        	}
	//CC		
			if(nurseTalkDone && kirito.cc(screenW-20,415,20,-15) && kirito.getF() == 4)
			{
				if(fade < 245)
					fade += 10;
				else if(done)
				{
					hospital = false;
					endPage = true;
					unFade = true;
					buttons.add(new Button(363,25,275,50));
					if(done)
					{
						stopPlain();
						playIntro();
					}
				}
				else
				{
					hospital = false;
					village = true;
					if(!level1Done)
					{
						eB.add(new EnemyB(975,342));
						eB.add(new EnemyB(975,322));
						eB.add(new EnemyB(975,362));
						eB.add(new EnemyB(955,342));
						eB.add(new EnemyB(955,322));
						eB.add(new EnemyB(955,362));
						stopPlain();
						playBattle();
					}
					unFade = true;
					kirito.reset(510,560,1);
				}
			}
			else if(!unFade)
				fade = 0;
        }
        else if(village)
        {
        	g.drawImage(villagePic,0,0,screenW,screenH,null);
			if(key && level3Done && kirito.cc(813,20,87,10) && kirito.getF() == 1)
				gate = null;
			else
			{
				g.drawImage(gate,793,0,127,50,null);
				kirito.ccMove(813,20,87,10);
			}
        	kirito.drawMe(g);
        	for(int i = 0;i < eB.size();i ++)
			{
				eB.get(i).drawMe(g);
				eB.get(i).track(kirito.getX(),kirito.getY());
				if(kirito.getY()+10 > eB.get(i).getY())
					kirito.drawMe(g);
			}
			if(key)
			{
				for(int i = 0;i < eA.size();i ++)
				{
					eA.get(i).drawMe(g);
					eA.get(i).track(kirito.getX(),kirito.getY());
					if(kirito.getY()+10 > eA.get(i).getY())
						kirito.drawMe(g);
				}
			}
        	if(kirito.cc(335,305,35,-15)&& kirito.getF() == 1 && level1Done && ((level3Done && key) || (!level3Done && !key)))
        	{
        		if(fade < 245)
					fade += 10;
				else
				{
					village = false;
					store = true;
					unFade = true;
					kirito.reset(449,366,1);
				}
        	}
        	else if(kirito.cc(492,595,85,5)&& kirito.getF() == 2 && level1Done && ((level3Done && key) || (!level3Done && !key)))
        	{
        		if(fade < 245)
					fade += 10;
				else
				{
					village = false;
					hospital = true;
					unFade = true;
					kirito.reset(980,400,3);
				}
        	}
        	else if(key && kirito.cc(813,20,87,10) && kirito.getF() == 1 && ((level3Done && key) || (!level3Done && !key)))
        	{
        		if(fade < 245)
					fade += 10;
				else
				{
					village = false;
					last = true;
					unFade = true;
					kirito.reset(490,560,1);
					stopPlain();
					playBossMusic();
				}
        	}
        	else if(kirito.cc(995,320,25,65)&& kirito.getF() == 4 && level1Done && ((level3Done && key) || (!level3Done && !key)))
        	{
        		if(fade < 245)
					fade += 10;
				else
				{
					village = false;
					grass = true;
					unFade = true;
					kirito.reset(10,342,4);
					if(!level2Done)
					{
						eA.add(new EnemyA(955,342));
						eA.add(new EnemyA(455,142));
						eA.add(new EnemyA(455,542));
						eA.add(new EnemyA(155,142));
						eA.add(new EnemyA(155,542));
						stopPlain();
						playBattle();
					}
				}
        	}		
        	else if(!unFade)
        		fade = 0;
        }
        else if(store)
        {
        	g.setColor(Color.black);
        	g.fillRect(0,0,screenW,screenH);
        	g.drawImage(storePic,350,200,300,200,null);
        	kirito.drawMe(g);
        	if(kirito.cc(433,292,10,-4) && kirito.getF() == 3)
        	{
        		talking = true;
        		chatBox(382,268,22,g);
        	}
        	else
        		talking = false;
        	if(cashier)
        	{
        		g.setColor(Color.black);
        		g.fillRect(0,0,screenW,screenH);
        		g.setColor(Color.white);
        		g.drawLine(0,150,screenW,150);
        		g.drawLine(screenW/2,150,screenW/2,screenH);
        		g.setFont(arial1);
        		g.drawString("My Coins: " + coins,373,132);
        		g.setFont(arial);
        		buttons.get(0).drawMe(g);
        		g.drawString("Back",452,60);
        		g.setFont(font);
        		g.setColor(Color.red);
        		g.fillRect(10,10,50,50);
        		g.drawString("= Item Bought",70,45);
        		g.setColor(Color.blue);
        		g.fillRect(700,23,50,24);
        		g.drawString("= Item Equipped",760,45);
        		g.setColor(Color.white);
        		g.setFont(font3);
        		g.drawString("The strongest items owned will automatically be equipped",700,60);
        		
				for(int i = 0;i < 3;i ++)
				{
					if(i < attackItems.size())
						attackItems.get(i).drawMe(g);
					if(i < defenseItems.size())
						defenseItems.get(i).drawMe(g);
					if(i < healthItems.size())
						healthItems.get(i).drawMe(g);
				}
        	}
        	if(kirito.cc(433,390,54,-15)&& kirito.getF() == 2)
        	{
        		if(fade < 245)
					fade += 10;
				else
				{
					store = false;
					village = true;
					unFade = true;
					kirito.reset(340,310,2);
				}
        	}
        	else if(!unFade)
        	{
        		fade = 0;
        	}
        }
        else if(grass)
        {
			g.drawImage(grassPic,0,0,screenW,screenH,null);
			if(!key && kirito.cc(800,346,20,28))
			{
				keyPic = null;
				key = true;
			}
			else
				g.drawImage(keyPic,800,346,20,28,null);
			kirito.drawMe(g);
			for(int i = 0;i < eA.size();i ++)
			{
				eA.get(i).drawMe(g);
				eA.get(i).track(kirito.getX(),kirito.getY());
				if(kirito.getY()+10 > eA.get(i).getY())
					kirito.drawMe(g);
			}
			if(kirito.cc(0,320,5,65)&& kirito.getF() == 3 && level2Done)
        	{
        		if(fade < 245)
					fade += 10;
				else
				{
					village = true;
					grass = false;
					unFade = true;
					kirito.reset(975,342,3);
					if(!level3Done && key)
					{
						eA.add(new EnemyA(700,100));
						eA.add(new EnemyA(650,100));
						eA.add(new EnemyA(750,100));
						eA.add(new EnemyA(500,300));
						eA.add(new EnemyA(450,510));
						eA.add(new EnemyA(400,510));
						eA.add(new EnemyA(500,510));
						stopPlain();
						playBattle();
					}
				}
        	}		
        	else if(!unFade)
        		fade = 0;
        }
        else if(last)
		{
			g.drawImage(lab,0,0,screenW,screenH,null);
			g.drawImage(serenaBlood,485,190,30,20,null);
			if(done && kirito.cc(485,190,30,21) && kirito.getF() == 1)
			{
				if(fade < 245)
					fade += 10;
				else
				{
					last = false;
					hospital = true;
					unFade = true;
					kirito.reset(50,185,1);
				}
			}
			else if(!unFade)
        		fade = 0;
				
			kirito.drawMe(g);
			if(!done)
			{
				boss.drawMe(g);
				if(kirito.getY()+10 > boss.getY())
					kirito.drawMe(g);
				if(!talking)
					boss.track(kirito.getX(),kirito.getY());
			}
			
        //Boss Talk
			if(!bossTalkDone && !startBossTalk && kirito.getF() == 1)
			{
				startBossTalk = true;
				talking = true;
			}
        	if(startBossTalk)
        	{
        		g.setFont(font);
        		g.setColor(black1);
        		g.fillRect(0,0,screenW,screenH);
        		g.drawImage(me,50,screenH-367-200,null);
        		g.drawImage(serena,screenW-400+150,190,210,210,null);
        		g.drawImage(bossPic,screenW-400-250,10,550,1400,null);
        		g.setColor(Color.black);
        		g.fillRect(0,400,screenW,screenH);
        		g.setColor(Color.white);
        		if(textNum < bossTalkArr[textLine].length())
        		{
        			text += bossTalkArr[textLine].substring(textNum,textNum+1);
        			textNum ++;
        		}
        		g.drawString(text,10,440);
        		g.setFont(arial2);
        		g.drawRect(0,570,420,29);
        		g.drawString("Hit \"Enter/Return\" to continue >>>",5,595);
        	}
		}
               
		if(inventory && !dead)
        {
        	g.setColor(Color.black);
        	g.fillRect(0,0,screenW,screenH);
        	g.setColor(Color.white);
        	g.drawLine(0,150,screenW,150);
        	g.drawLine(screenW/2,150,screenW/2,screenH);
        	g.setFont(arial);
        	buttons.get(0).drawMe(g);
        	g.drawString("Back",452,60);
        	g.setFont(font);
        	g.setColor(Color.red);
        	g.fillRect(10,10,50,50);
        	g.drawString("= Item Bought",70,45);
        	g.setColor(Color.blue);
        	g.fillRect(700,23,50,24);
        	g.drawString("= Item Equipped",760,45);
        	g.setColor(Color.white);
        	g.setFont(font3);
        	g.drawString("The strongest items owned will automatically be equipped",700,60);
        	
			for(int i = 0;i < 3;i ++)
			{
				if(i < attackItems.size())
					attackItems.get(i).drawMe(g);
				if(i < defenseItems.size())
					defenseItems.get(i).drawMe(g);
				if(i < healthItems.size())
					healthItems.get(i).drawMe(g);
			}
        }
        if(!startPage && !instructions && !store && !talking && !inventory)
        {
        	int[] arrX1 = new int[] {20,20+(2*Kirito.maxHealth),40+(2*Kirito.maxHealth),40};
        	int[] arrY1 = new int[] {20,20,40,40};
        	int[] arrX2 = new int[] {20,20+(2*Kirito.health),40+(2*Kirito.health),40};
        	g.setColor(Color.red);
        	g.fillPolygon(arrX1,arrY1,4);
        	g.setColor(Color.blue);
        	if(!dead)
        		g.fillPolygon(arrX2,arrY1,4);
        	g.setColor(Color.black);
        	int[] arrX3 = new int[] {25+(2*Kirito.maxHealth),135+(2*Kirito.maxHealth),155+(2*Kirito.maxHealth),45+(2*Kirito.maxHealth)};
        	int[] arrY3 = new int[] {20,20,40,40};
        	g.fillPolygon(arrX3,arrY3,4);
        	g.setColor(Color.white);
        	g.drawPolygon(arrX3,arrY3,4);
        	g.setFont(font3);
        	g.drawString("Health: "+Kirito.health+"/"+Kirito.maxHealth,50+(2*Kirito.maxHealth),33);
        	
        	g.setColor(Color.black);
        	int[] arrX4 = new int[] {45,160+(2*Kirito.maxHealth),180+(2*Kirito.maxHealth),65};
        	int[] arrY4 = new int[] {45,45,65,65};
        	g.fillPolygon(arrX4,arrY4,4);
        	g.setColor(Color.white);
        	g.drawPolygon(arrX4,arrY4,4);
        	g.setFont(font3);
        	g.drawString("Power: "+Kirito.power+"       Defense: "+Kirito.defense+"%       Coins: "+coins,70,59);
        }
        
        if(endPage)
        {
        	g.drawImage(openingImage2,0,0,screenW,screenH,null);
        	g.setFont(arial1);
        	int totalscore = coins+kirito.getHealth();
        	g.drawString("Coins Left: "+coins+" + Health Left: "+kirito.getHealth()+" = Total Score: "+totalscore,10,320);
        	
        	buttons.get(0).drawMe(g);
        	g.setFont(arial);
        	g.drawString("Next",452,65);
        }
        else if(endPage1)
        {
        	g.setColor(Color.black);
        	g.fillRect(0,0,screenW,screenH);
        	g.drawImage(title,50,50,900,100,null);
        	g.setColor(Color.white);
        	g.setFont(big);
        	g.drawString("DEMO",360,250);
        	g.setColor(fadeImage2);
        	g.fillRect(360,150,300,110);
        	unFade = true;
        	if(fade2 > 50)
				fade2 -= 1;
			else
				System.exit(0);
        	
        	g.setColor(Color.white);
        	g.setFont(arial1);
        	g.drawString("<<<<<<<<<< CLEARED >>>>>>>>>>",200,500);
        	
        	
        }
        
        g.setColor(fadeImage);
        if(dead)
        {
        	g.setColor(blood);
        	if(blood2 < 254)
    	    	blood2 += 2;
			else
				System.exit(0);
        }
		g.fillRect(0,0,screenW,screenH);
		if(dead)
		{
			g.setFont(big);
			g.setColor(Color.white);
			g.drawString("YOU DIED",250,350);
		}
        
		gb.drawImage(buffered,0,0,getWidth(),getHeight(),null);
	}
	
//Animate
	public void animate()
	{
		while(true)
		{
			wait(10);
			
			if(!startNurseTalk && !startPage && !cashier && !inventory && !dead && !startBossTalk)
				keyListener();
            if(punching && !startNurseTalk && !startPage && !cashier && !inventory && !talking)
			{
				if(kirito.getPunchTime() <= 30)
					kirito.punch();
				if(kirito.getPunchTime() > 30)
					punching = false;
			}
	//Best Items
			
			if(attackItems.get(0).getBought())
			{
				attackItems.get(0).setUtilized(true);
				attackItems.get(1).setUtilized(false);
				attackItems.get(2).setUtilized(false);
			}
			else if(attackItems.get(1).getBought())
			{
				attackItems.get(0).setUtilized(false);
				attackItems.get(1).setUtilized(true);
				attackItems.get(2).setUtilized(false);
			}
			else if(attackItems.get(2).getBought())
			{
				attackItems.get(0).setUtilized(false);
				attackItems.get(1).setUtilized(false);
				attackItems.get(2).setUtilized(true);
			}
			else
			{
				attackItems.get(0).setUtilized(false);
				attackItems.get(1).setUtilized(false);
				attackItems.get(2).setUtilized(false);
			}
			if(defenseItems.get(0).getBought())
			{
				defenseItems.get(0).setUtilized(true);
				defenseItems.get(1).setUtilized(false);
				defenseItems.get(2).setUtilized(false);
			}
			else if(defenseItems.get(1).getBought())
			{
				defenseItems.get(0).setUtilized(false);
				defenseItems.get(1).setUtilized(true);
				defenseItems.get(2).setUtilized(false);
			}
			else if(defenseItems.get(2).getBought())
			{
				defenseItems.get(0).setUtilized(false);
				defenseItems.get(1).setUtilized(false);
				defenseItems.get(2).setUtilized(true);
			}
			else
			{
				defenseItems.get(0).setUtilized(false);
				defenseItems.get(1).setUtilized(false);
				defenseItems.get(2).setUtilized(false);
			}
			
			if(healthItems.get(0).getBought())
			{
				healthItems.get(0).setUtilized(true);
				healthItems.get(1).setUtilized(false);
				healthItems.get(2).setUtilized(false);
			}
			else if(healthItems.get(1).getBought())
			{
				healthItems.get(0).setUtilized(false);
				healthItems.get(1).setUtilized(true);
				healthItems.get(2).setUtilized(false);
			}
			else if(healthItems.get(2).getBought())
			{
				healthItems.get(0).setUtilized(false);
				healthItems.get(1).setUtilized(false);
				healthItems.get(2).setUtilized(true);
			}
			else
			{
				healthItems.get(0).setUtilized(false);
				healthItems.get(1).setUtilized(false);
				healthItems.get(2).setUtilized(false);
			}
	// CC
            if(hospital)
            {
            	for(int bed = 150;bed < 570;bed += 60)
            	{
					kirito.ccMove(30,bed+30,60,2);
					if(bed != 150)
						kirito.ccMove(173,bed+19,44,-14);
				}
				kirito.ccMove(180,130,10,10);
				kirito.ccMove(0,0,screenW,130);
				kirito.ccMove(600,0,screenW-600,310);
			}
			if(village && !key)
			{
				kirito.ccMove(220,0,305,280);
				if(key && kirito.cc(813,20,87,10) && kirito.getF() == 1)
					gate = null;
				else if(gate != null)
					kirito.ccMove(793,20,127,10);
				if(eB.size() == 0)
				{
					if(!level1Done)
					{
						stopBattle();
						playPlain();
					}
					level1Done = true;
				}
				for(int i = 0;i < eB.size();i ++)
				{
					
					if(!punching && kirito.cc(eB.get(i).getX(),eB.get(i).getY(),eB.get(i).getW(),eB.get(i).getH()))
					{
						kirito.changeHealth(-5);
						kirito.setKnockBack(1);
						playEnemyHit();
					}
					else if(cheat1 || (punching && kirito.cc(eB.get(i).getX()-5,eB.get(i).getY()-5,eB.get(i).getW()+10,eB.get(i).getH()+10)))
					{
						eB.get(i).changeHealth(0-kirito.getPower());
						eB.get(i).setKnockBack(1);
						playPunchHit();
					}
					if(kirito.getKnockBack() > 0)
					{
						if(kirito.getKnockBack() < 10)
						{
							kirito.doKnockBack(eB.get(i).getX(),eB.get(i).getY());
							wait(10);
							repaint();
							kirito.setKnockBack(kirito.getKnockBack()+1);
						}
						else
							kirito.setKnockBack(0);
					}
					if(eB.get(i).getKnockBack() > 0)
					{
						if(eB.get(i).getKnockBack() < 10)
						{
							eB.get(i).doKnockBack(kirito.getX(),kirito.getY());
							wait(10);
							repaint();
							eB.get(i).setKnockBack(eB.get(i).getKnockBack()+1);
						}
						else
							eB.get(i).setKnockBack(0);
					}
					kirito.ccMove(eB.get(i).getX(),eB.get(i).getY()+10,eB.get(i).getW(),eB.get(i).getH()-20);
					for(int j = i+1;j < eB.size();j ++)
					{
						eB.get(i).ccMove(eB.get(j).getX(),eB.get(j).getY(),eB.get(j).getW(),eB.get(j).getH());
					}
					eB.get(i).ccMove(kirito.getX(),kirito.getY()+10,kirito.getW(),kirito.getH()-20);
					eB.get(i).ccMove(220,0,305,280);
					
					if(eB.get(i).getHealth() <= 0)
					{
						eB.remove(i);
						i --;
						coins += 4;
					}
					kirito.ccMove(220,0,305,280);
				}
			}
			if(village && key)
			{
				kirito.ccMove(220,0,305,280);
				if(key && kirito.cc(813,20,87,10) && kirito.getF() == 1)
					gate = null;
				else if(gate != null)
					kirito.ccMove(793,20,127,10);
				if(eA.size() == 0)
				{
					if(!level3Done)
					{
						stopBattle();
						playPlain();
					}
					level3Done = true;
				}
				for(int i = 0;i < eA.size();i ++)
				{
					if(!punching && kirito.cc(eA.get(i).getX(),eA.get(i).getY(),eA.get(i).getW(),eA.get(i).getH()))
					{
						kirito.changeHealth(-10);
						kirito.setKnockBack(1);
						playEnemyHit();
					}
					else if(cheat4 || (punching && kirito.cc(eA.get(i).getX()-5,eA.get(i).getY()-5,eA.get(i).getW()+10,eA.get(i).getH()+10)))
					{
						eA.get(i).changeHealth(0-kirito.getPower());
						eA.get(i).setKnockBack(1);
						playPunchHit();
					}
					if(kirito.getKnockBack() > 0)
					{
						if(kirito.getKnockBack() < 10)
						{
							kirito.doKnockBack(eA.get(i).getX(),eA.get(i).getY());
							wait(10);
							repaint();
							kirito.setKnockBack(kirito.getKnockBack()+1);
						}
						else
							kirito.setKnockBack(0);
					}
					if(eA.get(i).getKnockBack() > 0)
					{
						if(eA.get(i).getKnockBack() < 10)
						{
							eA.get(i).doKnockBack(kirito.getX(),kirito.getY());
							wait(10);
							repaint();
							eA.get(i).setKnockBack(eA.get(i).getKnockBack()+1);
						}
						else
							eA.get(i).setKnockBack(0);
					}
					kirito.ccMove(eA.get(i).getX(),eA.get(i).getY()+10,eA.get(i).getW(),eA.get(i).getH()-20);
					for(int j = i+1;j < eA.size();j ++)
					{
						eA.get(i).ccMove(eA.get(j).getX(),eA.get(j).getY(),eA.get(j).getW(),eA.get(j).getH());
					}
					eA.get(i).ccMove(kirito.getX(),kirito.getY()+10,kirito.getW(),kirito.getH()-20);
					eA.get(i).ccMove(220,0,305,280);
					kirito.ccMove(220,0,305,280);
					if(eA.get(i).getHealth() <= 0)
					{
						eA.remove(i);
						i --;
						coins += 8;
					}
				}
			}
			if(grass)
			{
				if(eA.size() == 0)
				{
					if(!level2Done)
					{
						stopBattle();
						playPlain();
						if(battle.isActive())
							stopBattle();
					}
					level2Done = true;
				}
				if(level2Done && battle.isActive() && eA.size() == 0)
					stopBattle();
				for(int i = 0;i < eA.size();i ++)
				{
					if(!punching && kirito.cc(eA.get(i).getX(),eA.get(i).getY(),eA.get(i).getW(),eA.get(i).getH()))
					{
						kirito.changeHealth(-10);
						kirito.setKnockBack(1);
						playEnemyHit();
					}
					else if(cheat2 || (punching && kirito.cc(eA.get(i).getX()-5,eA.get(i).getY()-5,eA.get(i).getW()+10,eA.get(i).getH()+10)))
					{
						eA.get(i).changeHealth(0-kirito.getPower());
						eA.get(i).setKnockBack(1);
						playPunchHit();
					}
					if(kirito.getKnockBack() > 0)
					{
						if(kirito.getKnockBack() < 10)
						{
							kirito.doKnockBack(eA.get(i).getX(),eA.get(i).getY());
							wait(10);
							repaint();
							kirito.setKnockBack(kirito.getKnockBack()+1);
						}
						else
							kirito.setKnockBack(0);
					}
					if(eA.get(i).getKnockBack() > 0)
					{
						if(eA.get(i).getKnockBack() < 10)
						{
							eA.get(i).doKnockBack(kirito.getX(),kirito.getY());
							wait(10);
							repaint();
							eA.get(i).setKnockBack(eA.get(i).getKnockBack()+1);
						}
						else
							eA.get(i).setKnockBack(0);
					}
					kirito.ccMove(eA.get(i).getX(),eA.get(i).getY()+10,eA.get(i).getW(),eA.get(i).getH()-20);
					for(int j = i+1;j < eA.size();j ++)
					{
						eA.get(i).ccMove(eA.get(j).getX(),eA.get(j).getY(),eA.get(j).getW(),eA.get(j).getH());
					}
					eA.get(i).ccMove(kirito.getX(),kirito.getY()+10,kirito.getW(),kirito.getH()-20);
					eA.get(i).ccMove(220,0,305,280);
					
					if(eA.get(i).getHealth() <= 0)
					{
						eA.remove(i);
						i --;
						coins += 8;
					}
				}
			}
			if(last && done)
			{
				kirito.ccMove(0,0,123,269);
				kirito.ccMove(0,0,54,screenH);
				kirito.ccMove(0,496,134,104);
				kirito.ccMove(0,0,screenW,210);
				kirito.ccMove(877,0,123,269);
				kirito.ccMove(946,0,54,screenH);
				kirito.ccMove(866,496,134,104);
			}
			if(last && !done)
			{
				kirito.ccMove(0,0,123,269);
				kirito.ccMove(0,0,54,screenH);
				kirito.ccMove(0,496,134,104);
				kirito.ccMove(0,0,screenW,210);
				kirito.ccMove(877,0,123,269);
				kirito.ccMove(946,0,54,screenH);
				kirito.ccMove(866,496,134,104);
				if(boss != null)
				{
					if(!punching && kirito.cc(boss.getX(),boss.getY(),boss.getW(),boss.getH()))
					{
						kirito.changeHealth(-50);
						kirito.setKnockBack(1);
						playEnemyHit();
					}
					else if(cheat3 || (punching && kirito.cc(boss.getX()-5,boss.getY()-5,boss.getW()+10,boss.getH()+10)))
					{
						boss.changeHealth(0-kirito.getPower());
						boss.setKnockBack(1);
						playPunchHit();
					}
					if(kirito.getKnockBack() > 0)
					{
						if(kirito.getKnockBack() < 10)
						{
							kirito.doKnockBack(boss.getX(),boss.getY());
							wait(10);
							repaint();
							kirito.setKnockBack(kirito.getKnockBack()+1);
						}
						else
							kirito.setKnockBack(0);
					}
					if(boss.getKnockBack() > 0)
					{
						if(boss.getKnockBack() < 10)
						{
							boss.doKnockBack(kirito.getX(),kirito.getY());
							wait(10);
							repaint();
							boss.setKnockBack(boss.getKnockBack()+1);
						}
						else
							boss.setKnockBack(0);
					}
					kirito.ccMove(0,0,123,269);
					kirito.ccMove(0,0,54,screenH);
					kirito.ccMove(0,496,134,104);
					kirito.ccMove(0,0,screenW,210);
					kirito.ccMove(877,0,123,269);
					kirito.ccMove(946,0,54,screenH);
					kirito.ccMove(866,496,134,104);
					kirito.ccMove(boss.getX(),boss.getY()+10,boss.getW(),boss.getH()-20);
					boss.ccMove(kirito.getX(),kirito.getY()+10,kirito.getW(),kirito.getH()-20);
					boss.ccMove(0,0,123,269);
					boss.ccMove(0,0,54,screenH);
					boss.ccMove(0,496,134,104);
					boss.ccMove(0,0,screenW,210);
					boss.ccMove(877,0,123,269);
					boss.ccMove(946,0,54,screenH);
					boss.ccMove(866,496,134,104);
				}
				if(boss.getHealth() <= 0)
				{
					if(!done)
					{
						stopBossMusic();
						playWin();
					}
					done = true;
					coins += 100;
				}
			}
			if(store)
			{
				kirito.ccMove(0,0,434,305);
				kirito.ccMove(0,0,screenW,235);
				kirito.ccMove(515,0,300,249);
				kirito.ccMove(620,0,300,screenH);
				kirito.ccMove(0,0,353,screenH);
				kirito.ccMove(0,397,screenW,screenH);
			}
			
			if(kirito.getHealth() <= 0)
				dead = true;
						
            repaint();
		}
	}
	
//KeyListener	
	public void keyPressed(KeyEvent z)
	{		
//		System.out.println(z.getKeyCode());
		
		if(z.getKeyCode() == 10 && startNurseTalk)
		{
			if(textLine < nurseTalkArr.length-1)
			{
				text = "";
				textLine ++;
				textNum = 0;
			}
			else
			{
				text = "";
				startNurseTalk = false;
				nurseTalkDone = true;
				textLine = 0;
				textNum = 0;
			}
		}
		if(z.getKeyCode() == 10 && startBossTalk)
		{
			if(textLine < bossTalkArr.length-1)
			{
				text = "";
				textLine ++;
				textNum = 0;
			}
			else
			{
				text = "";
				startBossTalk = false;
				bossTalkDone = true;
				textLine = 0;
				textNum = 0;
				talking = false;
			}
		}
    //startTalk
    	if(z.getKeyCode() == 88 && talking)
    	{
    		if(hospital)
    			startNurseTalk = true;
    		if(store)
    		{
    			cashier = true;
    			buttons.add(new Button(363,19,275,50));
    			
//				!!!!!! WHEN DONE, IF button.get(0) hit --> REMOVE ALL ITEMS !!!!!
				
    			attackItems.get(0).addButton();
    			attackItems.get(1).addButton();
    			attackItems.get(2).addButton();
    			defenseItems.get(0).addButton();
    			defenseItems.get(1).addButton();
    			defenseItems.get(2).addButton();
    			healthItems.get(0).addButton();
    			healthItems.get(1).addButton();
    			healthItems.get(2).addButton();
    			
    			attackItems.get(0).setStore(true);
    			attackItems.get(1).setStore(true);
    			attackItems.get(2).setStore(true);
    			defenseItems.get(0).setStore(true);
    			defenseItems.get(1).setStore(true);
    			defenseItems.get(2).setStore(true);
    			healthItems.get(0).setStore(true);
    			healthItems.get(1).setStore(true);
    			healthItems.get(2).setStore(true);
    		}
    	}
// E  
    	if((hospital || village || grass || last) && z.getKeyCode() == 69)
    	{
    		inventory = true;
    		buttons.add(new Button(363,19,275,50));
    	}
// P
		if(village && !key && z.getKeyCode() == 80)
			cheat1 = true;
		if(village && key && z.getKeyCode() == 80)
			cheat4 = true;
		if(grass && z.getKeyCode() == 80)
			cheat2 = true;
		if(last && z.getKeyCode() == 80)
			cheat3 = true;
		
		repaint();
	}
	public void keyReleased(KeyEvent z){}
	public void keyTyped(KeyEvent z){}
	
//KeyListener2
	public void keyListener()
	{
/*						w - 87
				a - 65	s - 83	d - 68
				
				(left) - 37		(right) - 39
				(up) - 38		(down) - 40
				(space) - 32	(shift) - 16
				
				r - 82
				p - 80
				q - 81
				x - 88
				(enter) - 10										*/
		
		if(move)
		{
           	 if(stepTime <= 1.5)
           	 	stepTimeChange = .1;
            if(stepTime >= 3.5)
            	stepTimeChange = -.1;
            stepTime += stepTimeChange;
        }
		else
            stepTime = 2;
            
	//up
        if(Input.keyboard[87])
        {
			kirito.moveU(1);
			kirito.setF(1);
			kirito.setA((int)stepTime);
			move = true;
        }
	//down
        if(Input.keyboard[83]) 
        {
			kirito.moveD(1);
			kirito.setF(2);
			kirito.setA((int)stepTime);
			move = true;
		}
	//left
        if(Input.keyboard[65])
        {
			kirito.moveL(1);
			kirito.setF(3);
			kirito.setA((int)stepTime);
			move = true;
        }
	//right
		if(Input.keyboard[68]) 
		{
			kirito.moveR(1);
			kirito.setF(4);
			kirito.setA((int)stepTime);
			move = true;
		}
    
    //still
        if(!Input.keyboard[87] && !Input.keyboard[83] && !Input.keyboard[65] && !Input.keyboard[68])
        {
            stepTime = 3;
            kirito.setA(2);
            move = false;
        }
    //shift
        if(Input.keyboard[16])
        	kirito.setS(2);
        else
        	kirito.setS(1);
	//space
		if(Input.keyboard[32] && kirito.getCoolDown() == 0 && !dead && !talking &&(hospital || instructions || village || grass || last)) 
		{
			punching = true;
			if(kirito.getPunchTime() == 0)
				playPunchMiss();
		}
	}
	
//MouseListener
	public void mousePressed(MouseEvent z) 
	{
//		System.out.println( "X: " + z.getX() + ", Y: " + z.getY() );
		
	/*	if(z.getX() >= xxx && z.getX() <= xxx && z.getY() >= xxx && z.getY() <= xxx)
		{
			
		}	*/
		
		boolean button = false;
		for(int i = 0;i < buttons.size();i ++)
		{
			if(z.getX()*screenW/getWidth() >= buttons.get(i).getX() && z.getX()*screenW/getWidth() <= buttons.get(i).getW() && z.getY()*screenH/getHeight() >= buttons.get(i).getY() && z.getY()*screenH/getHeight() <= buttons.get(i).getH())
				button = true;
		}
		if(!button && !dead && !talking &&(hospital || instructions || village || grass || last))
		{
			punching = true;
			if(kirito.getPunchTime() == 0)
				playPunchMiss();
		}
		
		repaint();
	}
	public void mouseReleased(MouseEvent z) 
	{
		if(buttons.size() != 0)
		{
			for(int i = 0;i < buttons.size();i ++)
			{
				buttons.get(i).resetIntroXA();
				buttons.get(i).setIntroXGo(false);
			}
			if(startPage && z.getX()*screenW/getWidth() >= buttons.get(0).getX() && z.getX()*screenW/getWidth() <= buttons.get(0).getW() && z.getY()*screenH/getHeight() >= buttons.get(0).getY() && z.getY()*screenH/getHeight() <= buttons.get(0).getH())
			{
				startPage = false;
				hospital = true;
				buttons.remove(0);
				buttons.remove(0);
				kirito.reset(50,185,2);
				fade = 255;
				unFade = true;
				stopIntro();
				playPlain();
			}
			else if(startPage && z.getX()*screenW/getWidth() >= buttons.get(1).getX() && z.getX()*screenW/getWidth() <= buttons.get(1).getW() && z.getY()*screenH/getHeight() >= buttons.get(1).getY() && z.getY()*screenH/getHeight() <= buttons.get(1).getH())
			{
				startPage = false;
				instructions = true;
				buttons.add(new Button(100,25,275,50));
				buttons.remove(0);
				buttons.remove(0);
				kirito.reset(screenW/2-10,330,2);
				fade = 255;
				unFade = true;
			}
			else if(instructions && z.getX()*screenW/getWidth() >= buttons.get(0).getX() && z.getX()*screenW/getWidth() <= buttons.get(0).getW() && z.getY()*screenH/getHeight() >= buttons.get(0).getY() && z.getY()*screenH/getHeight() <= buttons.get(0).getH())
			{
				instructions = false;
				startPage = true;
				buttons.add(new Button(100,250,275,50));
				buttons.add(new Button(100,350,275,50));
				buttons.remove(0);
				fade = 255;
				unFade = true;
			}
			else if(cashier && z.getX()*screenW/getWidth() >= buttons.get(0).getX() && z.getX()*screenW/getWidth() <= buttons.get(0).getW() && z.getY()*screenH/getHeight() >= buttons.get(0).getY() && z.getY()*screenH/getHeight() <= buttons.get(0).getH())
			{
				cashier = false;
				for(int b = 0;b < 10;b ++)
					buttons.remove(0);
				attackItems.get(0).setStore(false);
    			attackItems.get(1).setStore(false);
    			attackItems.get(2).setStore(false);
    			defenseItems.get(0).setStore(false);
    			defenseItems.get(1).setStore(false);
    			defenseItems.get(2).setStore(false);
    			healthItems.get(0).setStore(false);
    			healthItems.get(1).setStore(false);
    			healthItems.get(2).setStore(false);
			}
			else if(cashier && z.getX()*screenW/getWidth() >= buttons.get(1).getX() && z.getX()*screenW/getWidth() <= buttons.get(1).getW() && z.getY()*screenH/getHeight() >= buttons.get(1).getY() && z.getY()*screenH/getHeight() <= buttons.get(1).getH())
			{
				if(!attackItems.get(0).getBought() && coins >= attackItems.get(0).getPrice())
				{
					attackItems.get(0).setBought(true);
					coins -= attackItems.get(0).getPrice();
					playMoney();
					attackItems.get(0).setPrice(attackItems.get(0).getPrice()/2);
				}
				else if(attackItems.get(0).getBought())
				{
					attackItems.get(0).setBought(false);
					coins += attackItems.get(0).getPrice();
					playMoney();
					attackItems.get(0).setPrice(attackItems.get(0).getPrice()*2);
				}
			}
			else if(cashier && z.getX()*screenW/getWidth() >= buttons.get(2).getX() && z.getX()*screenW/getWidth() <= buttons.get(2).getW() && z.getY()*screenH/getHeight() >= buttons.get(2).getY() && z.getY()*screenH/getHeight() <= buttons.get(2).getH())
			{
				if(!attackItems.get(1).getBought() && coins >= attackItems.get(1).getPrice())
				{
					attackItems.get(1).setBought(true);
					coins -= attackItems.get(1).getPrice();
					playMoney();
					attackItems.get(1).setPrice(attackItems.get(1).getPrice()/2);
				}
				else if(attackItems.get(1).getBought())
				{
					attackItems.get(1).setBought(false);
					coins += attackItems.get(1).getPrice();
					playMoney();
					attackItems.get(1).setPrice(attackItems.get(1).getPrice()*2);
				}
			}
			else if(cashier && z.getX()*screenW/getWidth() >= buttons.get(3).getX() && z.getX()*screenW/getWidth() <= buttons.get(3).getW() && z.getY()*screenH/getHeight() >= buttons.get(3).getY() && z.getY()*screenH/getHeight() <= buttons.get(3).getH())
			{
				if(!attackItems.get(2).getBought() && coins >= attackItems.get(2).getPrice())
				{
					attackItems.get(2).setBought(true);
					coins -= attackItems.get(2).getPrice();
					playMoney();
					attackItems.get(2).setPrice(attackItems.get(2).getPrice()/2);
				}
				else if(attackItems.get(2).getBought())
				{
					attackItems.get(2).setBought(false);
					coins += attackItems.get(2).getPrice();
					playMoney();
					attackItems.get(2).setPrice(attackItems.get(2).getPrice()*2);
				}
			}
			else if(cashier && z.getX()*screenW/getWidth() >= buttons.get(4).getX() && z.getX()*screenW/getWidth() <= buttons.get(4).getW() && z.getY()*screenH/getHeight() >= buttons.get(4).getY() && z.getY()*screenH/getHeight() <= buttons.get(4).getH())
			{
				if(!defenseItems.get(0).getBought() && coins >= defenseItems.get(0).getPrice())
				{
					defenseItems.get(0).setBought(true);
					coins -= defenseItems.get(0).getPrice();
					playMoney();
					defenseItems.get(0).setPrice(defenseItems.get(0).getPrice()/2);
				}
				else if(defenseItems.get(0).getBought())
				{
					defenseItems.get(0).setBought(false);
					coins += defenseItems.get(0).getPrice();
					playMoney();
					defenseItems.get(0).setPrice(defenseItems.get(0).getPrice()*2);
				}
			}
			else if(cashier && z.getX()*screenW/getWidth() >= buttons.get(5).getX() && z.getX()*screenW/getWidth() <= buttons.get(5).getW() && z.getY()*screenH/getHeight() >= buttons.get(5).getY() && z.getY()*screenH/getHeight() <= buttons.get(5).getH())
			{
				if(!defenseItems.get(1).getBought() && coins >= defenseItems.get(1).getPrice())
				{
					defenseItems.get(1).setBought(true);
					coins -= defenseItems.get(1).getPrice();
					playMoney();
					defenseItems.get(1).setPrice(defenseItems.get(1).getPrice()/2);
				}
				else if(defenseItems.get(1).getBought())
				{
					defenseItems.get(1).setBought(false);
					coins += defenseItems.get(1).getPrice();
					playMoney();
					defenseItems.get(1).setPrice(defenseItems.get(1).getPrice()*2);
				}
			}
			else if(cashier && z.getX()*screenW/getWidth() >= buttons.get(6).getX() && z.getX()*screenW/getWidth() <= buttons.get(6).getW() && z.getY()*screenH/getHeight() >= buttons.get(6).getY() && z.getY()*screenH/getHeight() <= buttons.get(6).getH())
			{
				if(!defenseItems.get(2).getBought() && coins >= defenseItems.get(2).getPrice())
				{
					defenseItems.get(2).setBought(true);
					coins -= defenseItems.get(2).getPrice();
					playMoney();
					defenseItems.get(2).setPrice(defenseItems.get(2).getPrice()/2);
				}
				else if(defenseItems.get(2).getBought())
				{
					defenseItems.get(2).setBought(false);
					coins += defenseItems.get(2).getPrice();
					playMoney();
					defenseItems.get(2).setPrice(defenseItems.get(2).getPrice()*2);
				}
			}
			else if(cashier && z.getX()*screenW/getWidth() >= buttons.get(7).getX() && z.getX()*screenW/getWidth() <= buttons.get(7).getW() && z.getY()*screenH/getHeight() >= buttons.get(7).getY() && z.getY()*screenH/getHeight() <= buttons.get(7).getH())
			{
				if(!healthItems.get(0).getBought() && coins >= healthItems.get(0).getPrice())
				{
					healthItems.get(0).setBought(true);
					coins -= healthItems.get(0).getPrice();
					playMoney();
					healthItems.get(0).setPrice(healthItems.get(0).getPrice()/2);
				}
				else if(healthItems.get(0).getBought())
				{
					healthItems.get(0).setBought(false);
					coins += healthItems.get(0).getPrice();
					playMoney();
					healthItems.get(0).setPrice(healthItems.get(0).getPrice()*2);
				}
			}
			else if(cashier && z.getX()*screenW/getWidth() >= buttons.get(8).getX() && z.getX()*screenW/getWidth() <= buttons.get(8).getW() && z.getY()*screenH/getHeight() >= buttons.get(8).getY() && z.getY()*screenH/getHeight() <= buttons.get(8).getH())
			{
				if(!healthItems.get(1).getBought() && coins >= healthItems.get(1).getPrice())
				{
					healthItems.get(1).setBought(true);
					coins -= healthItems.get(1).getPrice();
					playMoney();
					healthItems.get(1).setPrice(healthItems.get(1).getPrice()/2);
				}
				else if(healthItems.get(1).getBought())
				{
					healthItems.get(1).setBought(false);
					coins += healthItems.get(1).getPrice();
					playMoney();
					healthItems.get(1).setPrice(healthItems.get(1).getPrice()*2);
				}
			}
			else if(cashier && z.getX()*screenW/getWidth() >= buttons.get(9).getX() && z.getX()*screenW/getWidth() <= buttons.get(9).getW() && z.getY()*screenH/getHeight() >= buttons.get(9).getY() && z.getY()*screenH/getHeight() <= buttons.get(9).getH())
			{
				if(!healthItems.get(2).getBought() && coins >= healthItems.get(2).getPrice())
				{
					healthItems.get(2).setBought(true);
					coins -= healthItems.get(2).getPrice();
					playMoney();
					healthItems.get(2).setPrice(healthItems.get(2).getPrice()/2);
				}
				else if(healthItems.get(2).getBought())
				{
					healthItems.get(2).setBought(false);
					coins += healthItems.get(2).getPrice();
					playMoney();
					healthItems.get(2).setPrice(healthItems.get(2).getPrice()*2);
				}
			}
			else if(inventory && z.getX()*screenW/getWidth() >= buttons.get(0).getX() && z.getX()*screenW/getWidth() <= buttons.get(0).getW() && z.getY()*screenH/getHeight() >= buttons.get(0).getY() && z.getY()*screenH/getHeight() <= buttons.get(0).getH())
			{
				buttons.remove(0);
				inventory = false;
			}
			else if(endPage && z.getX()*screenW/getWidth() >= buttons.get(0).getX() && z.getX()*screenW/getWidth() <= buttons.get(0).getW() && z.getY()*screenH/getHeight() >= buttons.get(0).getY() && z.getY()*screenH/getHeight() <= buttons.get(0).getH())
			{
				buttons.remove(0);
				endPage = false;
				endPage1 = true;
				fade = 255;
			} 
		}
		
		repaint();
	}
	public void mouseMoved(MouseEvent z)
	{
		for(int i = 0;i < buttons.size();i ++)
		{
			if(z.getX()*screenW/getWidth() >= buttons.get(i).getX() && z.getX()*screenW/getWidth() <= buttons.get(i).getW() && z.getY()*screenH/getHeight() >= buttons.get(i).getY() && z.getY()*screenH/getHeight() <= buttons.get(i).getH())
			{
				if(!buttons.get(i).getOnce())
				{
					buttons.get(i).resetIntroXA();
					buttons.get(i).setIntroXGo(true);
					buttons.get(i).setOnce(true);
				}
			}
			else
			{
				if(buttons.get(i).getOnce())
				{
					buttons.get(i).resetIntroXA();
					buttons.get(i).setIntroXGo(false);
					buttons.get(i).setOnce(false);
				}
			}
		}
		
		repaint();
	}
    public void mouseEntered(MouseEvent z) {}
    public void mouseExited(MouseEvent z) {}
    public void mouseClicked(MouseEvent z) {}
    public void mouseDragged(MouseEvent z) {}
	
//Audio
	public void playIntro()
	{
		if(bossMusic.isActive())
			stopBossMusic();
		if(intro.isActive())
			stopIntro();
		if(battle.isActive())
			stopBattle();
		if(plain.isActive())
			stopPlain();
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/Intro.wav");
            intro = AudioSystem.getClip();
            intro.open(AudioSystem.getAudioInputStream(url));
			intro.loop(Clip.LOOP_CONTINUOUSLY); 
            intro.start();
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
	}
	public void stopIntro()
	{
		if(intro != null)
			intro.stop();
	}
	public void playBossMusic()
	{
		if(bossMusic.isActive())
			stopBossMusic();
		if(intro.isActive())
			stopIntro();
		if(battle.isActive())
			stopBattle();
		if(plain.isActive())
			stopPlain();
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/BossMusic.wav");
            bossMusic = AudioSystem.getClip();
            bossMusic.open(AudioSystem.getAudioInputStream(url));
			bossMusic.loop(Clip.LOOP_CONTINUOUSLY);
            bossMusic.start();
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
	}
	public void stopBossMusic()
	{
		if(bossMusic != null)
			bossMusic.stop();
	}
	public void playBattle()
	{
		if(bossMusic.isActive())
			stopBossMusic();
		if(intro.isActive())
			stopIntro();
		if(battle.isActive())
			stopBattle();
		if(plain.isActive())
			stopPlain();
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/Battle.wav");
            battle = AudioSystem.getClip();
            battle.open(AudioSystem.getAudioInputStream(url));
			battle.loop(Clip.LOOP_CONTINUOUSLY); 
            battle.start();
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
	}
	public void stopBattle()
	{
		if( battle != null )
			battle.stop();
	}
	public void playPlain()
	{
		if(bossMusic.isActive())
			stopBossMusic();
		if(intro.isActive())
			stopIntro();
		if(battle.isActive())
			stopBattle();
		if(plain.isActive())
			stopPlain();
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/Plain.wav");
            plain = AudioSystem.getClip();
            plain.open(AudioSystem.getAudioInputStream(url));
			plain.loop(Clip.LOOP_CONTINUOUSLY); 
            plain.start();
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
	}
	public void stopPlain()
	{
		if(plain != null)
			plain.stop();
	}
	public void playWin()
	{
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/Win.wav");
            win = AudioSystem.getClip();
            win.open(AudioSystem.getAudioInputStream(url));
            win.start();
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
	}
	public void stopWin()
	{
		if(win != null)
			win.stop();
	}
	public void playMoney()
	{
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/Money.wav");
            money = AudioSystem.getClip();
            money.open(AudioSystem.getAudioInputStream(url));
            money.start();
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
	}
	public void playEnemyHit()
	{
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/EnemyHit.wav");
            enemyHit = AudioSystem.getClip();
            enemyHit.open(AudioSystem.getAudioInputStream(url));
            enemyHit.start();
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
	}
	public void playPunchHit()
	{
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/PunchHit.wav");
            punchHit = AudioSystem.getClip();
            punchHit.open(AudioSystem.getAudioInputStream(url));
            punchHit.start();
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
	}
	public void playPunchMiss()
	{
		try
		{
			URL url = this.getClass().getClassLoader().getResource("resources/PunchMiss.wav");
            punchMiss = AudioSystem.getClip();
            punchMiss.open(AudioSystem.getAudioInputStream(url));
            punchMiss.start();
		}
        catch(Exception ex){ex.printStackTrace(System.out);}
	}
	
//ChatBox
	public void chatBox(int x,int y,int w,Graphics g)
	{
		Font arial = new Font("Arial",Font.PLAIN,10);
		Color grey = new Color(150,150,150);
		
		int[] arrX = new int[] {x+w/2,x+w/2-5,x+w/2-37,x+w/2-37,x+w/2+37,x+w/2+37,x+w/2+5};
		int[] arrY = new int[] {y,y-10,y-10,y-40,y-40,y-10,y-10};
		
		g.setColor(Color.black);
		g.fillPolygon(arrX,arrY,7);
		g.setColor(grey);
		g.drawPolygon(arrX,arrY,7);
		g.setColor(Color.white);
		g.drawString("  Hit \"x\"",x+w/2-28,y-25);
		g.drawString("to interact",x+w/2-32,y-15);
	}
	
//Wait
	public void wait(int wait)
	{
		try {Thread.sleep(wait);}
		catch(InterruptedException ex) {Thread.currentThread().interrupt();}
	}
}