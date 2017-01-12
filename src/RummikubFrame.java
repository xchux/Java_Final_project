
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.*;
import java.awt.Container.*;
import javax.swing.*;
import java.applet.*;


public class RummikubFrame extends JFrame {
	private int choice = 1;
	private JPanel handPanel;
	private JPanel handPanelforrun;
	private JPanel borderPanel;
	private JPanel buttenPanel;
	private JButton[] hand;
	private JButton[][] border;
	private JButton rulesButton;
	private JButton endButton;
	private JButton drawButton;
	
	//private JButton testcardendButton;///////////////// 測透明PANEL回歸出牌
	Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();/////////////// 視窗大小
	private int screenhight;/////////////// 視窗大小
	private int screenwidth;/////////////// 視窗大小
	private JLayeredPane lp =null;///////////////// 容器化
	private JButton win;
	private JButton excheck;
	//private JButton testbutton; //////// 限制出牌按鈕
	//private JButton testbutton2; //////// 限制出牌按鈕
	private JLayeredPane layeredPane;
	private BorderLayout layout;
	private int count = 0;
	private Icon[] card;
	Object[] data;
	private RummikubPlayerHand playerHand = new RummikubPlayerHand();
	private RummikubBorder boarder = new RummikubBorder();
	private RummikubCard rummikubCard = new RummikubCard();///////////// 測值
	JLabel test = new JLabel("",JLabel.LEFT);///// 測值
	AudioClip clip;
	//JLabel testround = new JLabel("");
	//JPanel testPanel = new JPanel(new BorderLayout());
	
	public RummikubFrame() {
		super("RummikubGame");
		File musicFile=new File("D:\\workspace\\javafinal\\music.wav");
		URI uri=musicFile.toURI();
		URL url = null;
		try {
			url = uri.toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip =Applet.newAudioClip(url);
		clip.play();
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		card = new Icon[55];
		JLabel space = new JLabel("");
		for (int count = 1; count < card.length; count++) {
			card[count] = new ImageIcon(getClass().getResource(count + ".jpg"));
		}

		Icon mark1 = new ImageIcon(getClass().getResource("mark3.png"));
		Icon mark2 = new ImageIcon(getClass().getResource("mark4.png"));
		((ImageIcon) mark1).setImage(((ImageIcon) mark1).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		((ImageIcon) mark2).setImage(((ImageIcon) mark2).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		rulesButton = new JButton(mark1);
		rulesButton.setRolloverIcon(mark2);
		endButton = new JButton("結束");
		drawButton = new JButton("抽一張牌");
		excheck = new JButton("檢查幫手");
		win = new JButton("測試勝利");
		layout = new BorderLayout(3,3);
		setLayout(layout);
		handPanel = new JPanel();
		borderPanel = new JPanel();
		buttenPanel= new JPanel();
		handPanel.setBackground(null);
		borderPanel.setBackground(null);
		hand = new JButton[100];
		border = new JButton[10][20];
		buttenPanel.setLayout(new GridLayout(6,1));
		handPanel.setLayout(new GridLayout(1,hand.length));
		borderPanel.setLayout(new GridLayout(10,20));
		rulesButton.addActionListener(new RulesButtonHandler());
		endButton.addActionListener(new EndButtonHandler());
		drawButton.addActionListener(new DrawButtonHandler());
		excheck.addActionListener(new excheckButtonHandler());
		win.addActionListener(new WinButtonHandler());
		this.addWindowStateListener(new WindowStateListener() { 
			public void windowStateChanged(WindowEvent state) {
				clip.stop();
				clip.play();
			}
		});
		buttenPanel.add(rulesButton);
		buttenPanel.add(test);
		buttenPanel.add(excheck);
		buttenPanel.add(drawButton);
		buttenPanel.add(endButton);
		buttenPanel.add(win);
		add(buttenPanel,BorderLayout.EAST);
		add(handPanel,BorderLayout.SOUTH);
		add(borderPanel,BorderLayout.CENTER);
	}
	public void setText(int player){
		test.setText("回合玩家：" + player);
		test.setFont(new Font("標楷體", Font.BOLD, 20));
	}
	public void getboarder(RummikubBorder input) {
		boarder = input;
	}

	public RummikubBorder returnboarder() {
		return boarder;
	}

	public void getplayerHand(RummikubPlayerHand input) {
		playerHand = input;
	}

	public RummikubPlayerHand returnplayerHand() {
		return playerHand;
	}
	
	public void addboardButton(RummikubBorder boarder) {
		// System.out.println("以下");
		// boarder.paintRummikubBorder();
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				if (boarder.getRummikubBorder(x, y) != null) {
					border[x][y] = new JButton(card[boarder.getRummikubBorder(x, y).getWeight()]); // 改牌桌排的卡片
				} else {
					border[x][y] = new JButton();
				}
				borderPanel.add(border[x][y]);

				border[x][y].addActionListener(new ChanegeButtonHandler());

			}
		}
	}
	public void enableboardButton(int x, int y) {
		//border[x][y].setBackground(null);
		border[x][y].setEnabled(true);
		
		//border[x][y].setBackground(Color.BLUE);
		/*Color c = new Color(0, 0, 255, 0);
		border[x][y].setRGB(50, 50, c.getRGB());
		border[x][y].setBackgroundColor(Color.argb(0, 0, 255, 0));;*/
	}
	public void changeMyChoice(){
		choice = 1;
	}
	/*
	 * public void addhandButton(int w) { hand[count] = new JButton(card[w]);
	 * handPanel.add(hand[count]); hand[count].addActionListener(new
	 * ChanegeButtonHandler()); count++; }
	 */
	public void addhandButton(int w, int number) {

		hand[number] = new JButton(card[w]);
		handPanel.add(hand[number]);
		hand[number].addActionListener(new ChanegeButtonHandler());
		// handPanel.revalidate();
		// add(handPanel, BorderLayout.SOUTH);
	}

	int check = -1, checkx = -1, checky = -1, clickcount = -1;// clickcount
																// -1:還未點牌
																// 1:點第二張排

	Icon temp1 = null;
	Icon temp2;
	RummikubCard tempcard1, tempcard2;
	public class excheckButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			choice =5;
			File musicFileenddraw =new File("D:\\workspace\\javafinal\\enddraw.wav");
			URI urienddraw=musicFileenddraw.toURI();
			URL urlenddraw = null;
			try {
				urlenddraw = urienddraw.toURL();
			} catch (MalformedURLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			AudioClip clipenddraw =Applet.newAudioClip(urlenddraw);
			clipenddraw.play();
		}
	}
	public class WinButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			choice =4;
			
		}
	}
	public class ChanegeButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			File musicFilebutton =new File("D:\\workspace\\javafinal\\handborder.wav");
			URI uributton=musicFilebutton.toURI();
			URL urlbutton = null;
			try {
				urlbutton = uributton.toURL();
			} catch (MalformedURLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			AudioClip clipbutton =Applet.newAudioClip(urlbutton);
			clipbutton.play();
			
			choice = 1;
			
			JButton btn = (JButton) e.getSource();
			JButton notnull = (JButton) e.getSource();

			for (int i = 0; i < card.length; i++) {
				if (btn == hand[i]) {
					notnull = hand[i];
					// System.out.println(playerHand.getplayCard(i).getNumber());
					//test.setText(Integer.toString(playerHand.getplayCard(i).getNumber()));
				}
			}
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 20; y++) {
					if (btn == border[x][y]) {
						notnull = border[x][y];
						if (boarder.getRummikubBorder(x, y) != null)
							;//test.setText(Integer.toString(boarder.getRummikubBorder(x, y).getNumber()));
					}
				}
			}

			if (notnull.getIcon() != null && clickcount == -1) {
				for (int x = 0; x < 10; x++) {
					for (int y = 0; y < 20; y++) {
						if (btn == border[x][y]) {
							temp1 = border[x][y].getIcon();
							border[x][y].setIcon(null);
							tempcard1 = boarder.getRummikubBorder(x, y);
							checkx = x;
							checky = y;
						}
					}
				}

				for (int i = 0; i < card.length; i++) {
					if (btn == hand[i]) {
						temp1 = hand[i].getIcon();
						hand[i].setIcon(null);
						tempcard1 = playerHand.getCard(i);
						check = i;
					}
				}
				clickcount = 1;
			} // if end
			else if (clickcount == 1) {
				for (int x = 0; x < 10; x++) {
					for (int y = 0; y < 20; y++) {
						if (btn == border[x][y]) {
							if (checkx == x && checky == y)
								border[x][y].setIcon(temp1);
							else {
								temp2 = border[x][y].getIcon();
								border[x][y].setIcon(temp1);
								tempcard2 = boarder.getRummikubBorder(x, y);
								boarder.setRummikubBorder(tempcard1, x, y);
								if (checkx != -1 && checky != -1) {
									border[checkx][checky].setIcon(temp2);
									boarder.setRummikubBorder(tempcard2, checkx, checky);
									if (check != -1) {// 手排減少
										playerHand.removeplayCard(check);
										System.out.println("1111");

									}
								} else {
									hand[check].setIcon(temp2);
									playerHand.setplayCard(tempcard2, check);
								}
							}

						}
					}
				}
				for (int i = 0; i < card.length; i++) {
					if (btn == hand[i]) {
						if (check == i)
							hand[i].setIcon(temp1);
						else {
							temp2 = hand[i].getIcon();
							hand[i].setIcon(temp1);
							tempcard2 = playerHand.getCard(i);
							playerHand.setplayCard(tempcard1, i);
							if (checkx != -1 && checky != -1) {
								border[checkx][checky].setIcon(temp2);
								boarder.setRummikubBorder(tempcard2, checkx, checky);
								if (check != -1) {// 手排減少
									playerHand.removeplayCard(check);
									System.out.println("0000");
								}
							} else {
								hand[check].setIcon(temp2);
								playerHand.setplayCard(tempcard2, check);
							}
						}

					}
				}
				clickcount = -1;
				checkx = -1;
				checky = -1;
				check = -1;
				temp1 = null;
				temp2 = null;
			} // else if end
			notnull = null;
		}
	}

	private class RulesButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			File musicFileenddraw =new File("D:\\workspace\\javafinal\\handborder.wav");
			URI urienddraw=musicFileenddraw.toURI();
			URL urlenddraw = null;
			try {
				urlenddraw = urienddraw.toURL();
			} catch (MalformedURLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			AudioClip clipenddraw =Applet.newAudioClip(urlenddraw);
			clipenddraw.play();
		   
		    Container cp=getContentPane();
		    //cp.setSize(500,500);
		    cp.setLayout(null);
		    JDialog.setDefaultLookAndFeelDecorated(true);
		    ImageIcon image=new ImageIcon("D:\\workspace\\javafinal\\src\\rules.jpg");
	        image.setImage(image.getImage().getScaledInstance(1100,650,Image.SCALE_DEFAULT));
	        
			JOptionPane.showMessageDialog(cp,"", "Rummikub Rule", JOptionPane.PLAIN_MESSAGE,image);
                   
			//Icon rule = new ImageIcon(getClass().getResource("rules.jpg"));
			//JOptionPane.showMessageDialog(RummikubFrame.this, "", "Rules Of Rummikub", 3, rule);
		}
	}

	public int getChoice() {
		return choice;
	}

	private class EndButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			File musicFileenddraw =new File("D:\\workspace\\javafinal\\enddraw.wav");
			URI urienddraw=musicFileenddraw.toURI();
			URL urlenddraw = null;
			try {
				urlenddraw = urienddraw.toURL();
			} catch (MalformedURLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			AudioClip clipenddraw =Applet.newAudioClip(urlenddraw);
			clipenddraw.play();
			//lp.setLayer(testbutton, new Integer(200)); ///////////////// 測透明PANEL回歸出牌
			//lp.setLayer(testbutton2, new Integer(200)); ///////////////// 測透明PANEL回歸出牌
			//lp.setLayer(borderPanel, new Integer(100));///////////////// 測透明PANEL回歸出牌
			//lp.setLayer(handPanel, new Integer(100));///////////////// 測透明PANEL回歸出牌
			/*if(boarder.check() == true){// 合法
				System.out.println("//////////合法結束//////////");
				//System.out.printf("測試"+playerHand[p].getLastCards());
			}
			else{// 不合法
				System.out.println("//////////不合法結束//////////");
				//boarder=returnboarder;
				//playerHand=returnplayerHand;
				//returnplayerHand.showHand();
				ClearHandPanel();
				addboardButton(boarder);
				for(int counter =0;counter<playerHand.getLastCards();counter++){
					try{
					addhandButton(playerHand.getCard(counter).getWeight(), counter);
					}
					catch(Exception ex){
						
					}
				}
			}*/
			choice = 3;

		}
	}

	private class DrawButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			
			File musicFileenddraw =new File("D:\\workspace\\javafinal\\enddraw.wav");
			URI urienddraw=musicFileenddraw.toURI();
			URL urlenddraw = null;
			try {
				urlenddraw = urienddraw.toURL();
			} catch (MalformedURLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			AudioClip clipenddraw =Applet.newAudioClip(urlenddraw);
			clipenddraw.play();
			//lp.setLayer(testbutton, new Integer(200)); ///////////////// 測透明PANEL回歸出牌
			//lp.setLayer(testbutton2, new Integer(200)); ///////////////// 測透明PANEL回歸出牌
			//lp.setLayer(borderPanel, new Integer(100));///////////////// 測透明PANEL回歸出牌
			//lp.setLayer(handPanel, new Integer(100));///////////////// 測透明PANEL回歸出牌
			choice = 2;
			/*
			 * count = 0; handPanel=new JPanel();; for (int counter = 0; counter
			 * <playerHand.getLastCards(); counter++) {
			 * System.out.println(counter+"///0000"+playerHand.getCard(counter).
			 * getWeight());
			 * addhandButton(playerHand.getCard(counter).getWeight()); }
			 */
		}
	}

	//public void ReturnToMyRound() {
		//lp.setLayer(testbutton, new Integer(100)); ///////////////// 測透明PANEL回歸出牌
		//lp.setLayer(testbutton2, new Integer(100)); ///////////////// 測透明PANEL回歸出牌
		//lp.setLayer(borderPanel, new Integer(200));///////////////// 測透明PANEL回歸出牌
		//lp.setLayer(handPanel, new Integer(200));///////////////// 測透明PANEL回歸出牌
	//}

	public void ClearHandPanel() {
		handPanel.removeAll();
		handPanel.repaint();
		borderPanel.removeAll();
		borderPanel.repaint();
	}
	public void excheckok(boolean b,int p){
		
		Container cp=getContentPane();
		JDialog.setDefaultLookAndFeelDecorated(true);
	    cp.setLayout(null);
	    
        //clip.stop();
	    if(b == true){
	    	 ImageIcon image=new ImageIcon("D:\\workspace\\javafinal\\src\\正確.png");
	         image.setImage(image.getImage().getScaledInstance(100,80,Image.SCALE_DEFAULT));
	    	File musicFileenddraw =new File("D:\\workspace\\javafinal\\正確.wav");
			URI urienddraw=musicFileenddraw.toURI();
			URL urlenddraw = null;
			try {
				urlenddraw = urienddraw.toURL();
			} catch (MalformedURLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			AudioClip clipenddraw =Applet.newAudioClip(urlenddraw);
			clipenddraw.play();
	    	JOptionPane.showMessageDialog(cp,"出牌正確!!" + "\n"+"你剩下" + p + "次檢查機會", "check", JOptionPane.PLAIN_MESSAGE,image);
	    }
	    else{
	    	 ImageIcon image=new ImageIcon("D:\\workspace\\javafinal\\src\\錯誤.png");
	         image.setImage(image.getImage().getScaledInstance(100,80,Image.SCALE_DEFAULT));
	    	File musicFileenddraw =new File("D:\\workspace\\javafinal\\錯誤.wav");
			URI urienddraw=musicFileenddraw.toURI();
			URL urlenddraw = null;
			try {
				urlenddraw = urienddraw.toURL();
			} catch (MalformedURLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			AudioClip clipenddraw =Applet.newAudioClip(urlenddraw);
			clipenddraw.play();
	    	JOptionPane.showMessageDialog(cp,"出牌錯誤!!" + "\n"+"你剩下" + p + "次檢查機會", "check", JOptionPane.PLAIN_MESSAGE,image);
	    }
	    choice = 1;
	    
	}
	public void unexcheckok(){
		 ImageIcon image=new ImageIcon("D:\\workspace\\javafinal\\src\\禁止.png");
         image.setImage(image.getImage().getScaledInstance(100,80,Image.SCALE_DEFAULT));
    	File musicFileenddraw =new File("D:\\workspace\\javafinal\\禁止.wav");
		URI urienddraw=musicFileenddraw.toURI();
		URL urlenddraw = null;
		try {
			urlenddraw = urienddraw.toURL();
		} catch (MalformedURLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		AudioClip clipenddraw =Applet.newAudioClip(urlenddraw);
		clipenddraw.play();
  
		Container cp=getContentPane();
		JDialog.setDefaultLookAndFeelDecorated(true);
	    cp.setLayout(null);
        //clip.stop();
	    	JOptionPane.showMessageDialog(cp,"您的檢查機會已用盡", "check", JOptionPane.PLAIN_MESSAGE,image);
	    choice = 1;
	    
	}
	public void winner(JFrame a , int p){
		File musicFileenddraw =new File("D:\\workspace\\javafinal\\enddraw.wav");
		URI urienddraw=musicFileenddraw.toURI();
		URL urlenddraw = null;
		try {
			urlenddraw = urienddraw.toURL();
		} catch (MalformedURLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		AudioClip clipenddraw =Applet.newAudioClip(urlenddraw);
		clipenddraw.play();
		
		File musicFileenddraw1 =new File("D:\\workspace\\javafinal\\WIN.wav");
		URI urienddraw1=musicFileenddraw1.toURI();
		URL urlenddraw1 = null;
		try {
			urlenddraw1 = urienddraw1.toURL();
		} catch (MalformedURLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		AudioClip clipenddraw1 =Applet.newAudioClip(urlenddraw1);
		clipenddraw1.play();
		
		Container cp=getContentPane();
		JDialog.setDefaultLookAndFeelDecorated(true);
	    cp.setLayout(null);
	    ImageIcon image=new ImageIcon("D:\\workspace\\javafinal\\src\\win.png");
        image.setImage(image.getImage().getScaledInstance(300,200,Image.SCALE_DEFAULT));
        //image
        clip.stop();
	    JOptionPane.showMessageDialog(cp,"玩家 "+p+" 勝利!!!", "WIN", JOptionPane.PLAIN_MESSAGE,image);
	    a.setVisible(false);
	}

}