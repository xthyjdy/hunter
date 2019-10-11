
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Time;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class HunterAction extends JPanel{
	private final int TABLE_STATE_FOR_HIT = 1;
	private final int TABLE_STATE_FOR_MISSED = -1;
	private final int GAME_SPEED_INCREASE = -10;
	private final int SCORE_FOR_LEVEL_UP = 2;
	private final int SCORE_FOR_START = 0;
	private final int SIZE_OF_TABLE = 320;
	private final int SIZE_OF_CELL = 16;
	private final int TIME_START = 15;
	private final int TIME_END = 0;
	private final Image VICTIM_IMAGE = new ImageIcon("images/victim.png").getImage();
	private final Image AIM_IMAGE = new ImageIcon("images/aim.png").getImage();
	private ActionListener aL;
	private Timer timer;
	private int gameSpeed = 500;
	private int aimX = SIZE_OF_TABLE / 2;
	private int aimY = SIZE_OF_TABLE / 2;
	private int victimX;
	private int victimY;
	private int score;
	private int gameTime;
	private boolean gameState = true;
	private Color color = null;
	
	public HunterAction () {
		this.initPanel();
		this.init();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(VICTIM_IMAGE, victimX, victimY, this);
		g.drawImage(AIM_IMAGE, aimX, aimY, this);
	}
	
	private void init() {
		score = SCORE_FOR_START;
		gameTime = TIME_START;
		initVictim();
		aL = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameState) {
					if (TIME_END == gameTime) {
						gameTimeFinifhed();
					} else {
						gameTime--;
					}
					repaint();
				} else {
					timer.stop();
				}
			}
		};
		setTimer();
	}
	
	private void setTablePhoneBackground(int tableState) {
		int backgroundColorChangeSpeed = 100;

		switch (tableState) {
			case TABLE_STATE_FOR_HIT:
				color = Color.GREEN;
				break;
			case TABLE_STATE_FOR_MISSED:
				color = Color.RED;
				break;
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				setBackground(color);
				try {
					Thread.sleep(backgroundColorChangeSpeed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				setBackground(Color.BLACK);
			}
		}).start();		
	}
	
	private void setTimer() {
		timer = new Timer(gameSpeed, aL);
		timer.start();
	}
	
	private void levelUp() {
		score = SCORE_FOR_START;
		gameSpeed += GAME_SPEED_INCREASE;
		setTimer();
	}
	
	private void gameTimeFinifhed() {
		initVictim();
		gameTime = TIME_START;
	}
	
	private void hit() {
		if (SCORE_FOR_LEVEL_UP == score) {
			levelUp();
		} else {
			score++;
			initVictim();
			gameTime = TIME_START;
			setTablePhoneBackground(TABLE_STATE_FOR_HIT);
		}
	}
	
	private void miss() {
		if (SCORE_FOR_START != score) {
			score--;
		}
		setTablePhoneBackground(TABLE_STATE_FOR_MISSED);
	}
	
	private void initVictim() {
		Random random = new Random();
		victimX = random.nextInt(SIZE_OF_TABLE / SIZE_OF_CELL) * SIZE_OF_CELL;
		victimY = random.nextInt(SIZE_OF_TABLE / SIZE_OF_CELL) * SIZE_OF_CELL;
	}
	
	private void initPanel() {
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
//		setSize(64, 64);
		addKeyListener(new KeyAdapter() {
			private final int BUTTON_UP = 38;
			private final int BUTTON_DOWN = 40;
			private final int BUTTON_LEFT = 37;
			private final int BUTTON_RIGHT = 39;
			private final int BUTTON_FIRE = 32;
			private int key;
			
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				
				key = e.getKeyCode();
				
				if (BUTTON_UP == key) {
					aimY -= SIZE_OF_CELL;
				} else if (BUTTON_DOWN == key) {
					aimY += SIZE_OF_CELL;
				} else if (BUTTON_RIGHT == key) {
					aimX += SIZE_OF_CELL;
				} else if (BUTTON_LEFT == key) {
					aimX -= SIZE_OF_CELL;
				} else if (BUTTON_FIRE == key) {
					if (aimX == victimX && aimY == victimY) {
						hit();
					} else {
						miss();
					}
				}
				repaint();
			}
		});
		setFocusable(true);
	}
}