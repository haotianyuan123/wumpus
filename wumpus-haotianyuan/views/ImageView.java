package views;
//Author: Haotian Yuan
//Purpose:This classs to draw the imageview when the player switch into imageview
import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.theGame;


public class ImageView extends BorderPane implements Observer{
	Image Blood,Goop,Ground,Slime,SlimePit,TheHunter,Wumpus;// images
	GraphicsContext gc;//the context
	private theGame theGame;//to trace the game
	private Canvas canvas;//draw
	private char[][] temp;
	private int x;//hunter's  position
	private int y;
	private char[][] current;//the current map
	private char[][] move;//the current map
	private String text;
	private Button N;//four buttons
	private Button W;
	private Button S;
	private Button E;
	private GridPane out;//the gridpane to hold buttons and canvas
	private int SX;//the shooting position
	private int SY;//the shooting position
	private char[][] available;
	public ImageView(theGame game){
		Blood=new Image("images/Blood.png",false);
		Goop=new Image("images/Goop.png",false);
		Ground=new Image("images/Ground.png",false);
		Slime=new Image("images/Slime.png",false);
		SlimePit=new Image("images/SlimePit.png",false);
		TheHunter=new Image("images/TheHunter.png",false);
		Wumpus=new Image("images/Wumpus.png",false);
		out= new GridPane();//the most outside of the screen is the gridpane, this is to add the buttons
		theGame=game;//read the game
		temp=game.getBoard();//read the init game
		available=game.getavailable();
		text="MOVE NOW";
		current = new char[12][12];
		move=new char[12][12];//to trace the movement of the hunter
		N=new Button("N");
		W=new Button("W");
		S=new Button("S");
		E=new Button("E");
		N.setFocusTraversable(false);//the keyboard will not go to the button
		W.setFocusTraversable(false);
		S.setFocusTraversable(false);
		E.setFocusTraversable(false);
		
		for(int i=0;i<12;i++) {
			for(int j=0;j<12;j++) {
				move[i][j]='0';
				if(temp[i][j]=='O') {
					current[i][j]='O';
					move[i][j]='1';
				}
				else {
				current[i][j]='X';
				}
			}
		}
		
		canvas=new Canvas(240,320);	
		out.add(canvas, 0, 0);//add four buttons
		out.add(N, 0, 1);
		out.add(S, 0, 2);
		out.add(E, 0, 3);
		out.add(W, 0, 4);
		gc=canvas.getGraphicsContext2D();		
		this.setCenter(out);
		canvas.setFocusTraversable(true);//the keyboard press
		x=theGame.gethunterX();
		y=theGame.gethunterY();
	
		SX=x;
		SY=y;
		initializePane();
		
	}
	
	/*Method name: initializePane()
	 *Purpose:This method is to init the pane,set the board and draw the starting map
	 * 
	 */	
	public void initializePane() {
		
		gc.fillText(text, 80, 320);//add the label text
		
		for(int i=0;i<12;i++) {//init the map with all 'X' and the hunter
			for(int j=0;j<12;j++) {	
					if(current[i][j]=='O') {
					//	System.out.println(i+" "+j);
						
						//gc.drawImage(Ground, i*20, j*20+20,12,12);
						gc.drawImage(TheHunter, i*20, j*20+20,12,12);
						//gc.drawImage(Ground, i*20, j*20);
					}
					else {
						gc.drawImage(Ground, i*20, j*20+20,12,12);
					}
					
				}
			
		}
		KeyboardListener keyboard=new KeyboardListener();//set the keyboard
		canvas.setOnKeyPressed(keyboard);
		ButtonListener handler = new ButtonListener();//set four buttons
		N.setOnAction(handler);
		W.setOnAction(handler);
		E.setOnAction(handler);
		S.setOnAction(handler);
	}
	
	/*Method name: update()
	 *Purpose:This method is to update the game, to check if the game is over or we start a new game
	 * 
	 */
	@Override
	public void update(Observable o, Object arg) {
		 theGame = (theGame) o;
		 //System.out.println(1);
		 updatemap();
		 if(theGame.lose(x, y)==true) {
			// gc.clearRect(0, 0, 250, 250);
			 text="YOU LOSE!";
			// gc.fillText(text, 0, 280);
			 
		 }
		 else if(theGame.won(SX, SY)) {
			 text="YOU WIN!";
		 }
		 if(x!=theGame.gethunterX() || y!=theGame.gethunterY()) {//if the hunter position is different, which means it stars a new game
			 temp=theGame.getBoard();//init everything like a new game
			 move=new char[12][12];
			 current = new char[12][12];
			 for(int i=0;i<12;i++) {
				 	
					for(int j=0;j<12;j++) {
						move[i][j]='0';
						if(temp[i][j]=='O') {
							//System.out.println(i+" "+j);
							x=i;
							y=j;
							current[i][j]='O';
							move[i][j]='1';
						}
						else {
						current[i][j]='X';
						}
					}
				}
			 updatemap();
		 }	
	}
	
	/*Method name: updatemap()
	 *Purpose:This method is to follow the process of the game and update the map
	 * 
	 */
	public void updatemap() {
		gc.clearRect(0, 0, 240, 320);//clear the screen firstly
		gc.fillText(text, 80, 320);//add the label
		SX=x;//the shooting position is following the hunter's position
		SY=y;
		text="Make move";
		for(int i=0;i<12;i++) {
			for(int j=0;j<12;j++) {		
					if(move[i][j]=='1') {
						current[i][j]=temp[i][j];
						if(current[i][j]=='X') {
							current[i][j]=' ';
						}
					}
					if(current[i][j]=='S') {
						gc.drawImage(Slime, i*20, j*20+20,12,12);
					}
					else if(current[i][j]=='G'){
						gc.drawImage(Goop, i*20, j*20+20,12,12);
					}
					else if(current[i][j]=='O'){
						gc.drawImage(TheHunter, i*20, j*20+20,12,12);
					}
					else if(current[i][j]=='B'){
						gc.drawImage(Blood, i*20, j*20+20,12,12);
					}
					else if(current[i][j]=='X'){
						gc.drawImage(Ground, i*20, j*20+20,12,12);
					}
					else if(current[i][j]=='W'){
						gc.drawImage(Wumpus, i*20, j*20+20,12,12);
					}
					else if(current[i][j]=='P'){
						gc.drawImage(SlimePit, i*20, j*20+20,12,12);
					}
					//gc.fillText(String.valueOf(current[i][j]), i*20, j*20+20);
					
				}
			
			}
	
	}
	// this is keyboardlisterner class, to set up the keyboard 
	private class KeyboardListener implements EventHandler<KeyEvent>{
		
		
		@Override
		public void handle(KeyEvent event) {
			// TODO Auto-generated method stub
			KeyCode temp1 = null;
			temp1=event.getCode();
			String a=temp1.name();
			
			if(a.compareTo("RIGHT")==0) {//if we press right, the hunter go right
				if(available[x][y]=='1' ) {
					
					theGame.Move(x, y,false);
					if(x+1>11) {
						x=0;
					}
					else {
					x++;
					}
					theGame.Move(x, y,true);
					move[x][y]='1';
					if(theGame.boold(x, y)==true) {
						//System.out.println(x+" "+y);
						text="I saw blood!";
					}
					else if(theGame.smile(x, y)==true) {
						text="That is slime!";
					}
					else if(theGame.goop(x, y)==true) {
						text="That is goop!";
					}
					else if(theGame.lose(x, y)==true) {
						text="You Lose!";
					}
					else {
						text="Make move";
					}
					updatemap();
				}
				
				
			}
			else if(a.compareTo("LEFT")==0) {//if we press left, the hunter go left
				if(available[x][y]=='1') {
					theGame.Move(x, y,false);
					if(x-1<0) {
						x=11;
					}
					else {
					x--;
					}
					theGame.Move(x, y,true);
					move[x][y]='1';
					if(theGame.boold(x, y)==true) {
						//System.out.println(x+" "+y);
						text="I saw blood!";
					}
					else if(theGame.smile(x, y)==true) {
						text="That is slime!";
					}
					else if(theGame.goop(x, y)==true) {
						text="That is goop!";
					}
					else if(theGame.lose(x, y)==true) {
						text="You Lose!";
					}
					else  {
						text="Make move";
					}
					updatemap();
				}
			}
			else if(a.compareTo("UP")==0) {//if we press up, the hunter go up
				if(available[x][y]=='1') {
					theGame.Move(x, y,false);
					if(y-1<0) {
						y=11;
					}
					else {
					y--;
					}
				
					theGame.Move(x, y,true);
					move[x][y]='1';
					if(theGame.boold(x, y)==true) {
						//System.out.println(x+" "+y);
						text="I saw blood!";
					}
					else if(theGame.smile(x, y)==true) {
						text="That is slime!";
					}
					else if(theGame.goop(x, y)==true) {
						text="That is goop!";
					}
					else if(theGame.lose(x, y)==true) {
						text="You Lose!";
					}
					else  {
						text="Make move";
					}
					updatemap();
				}
			}
			else if(a.compareTo("DOWN")==0) {//if we press down, the hunter go down
				if(available[x][y]=='1') {
					theGame.Move(x, y,false);
					if(y+1>11) {
						y=0;
					}
					else {
					y++;
					}
					theGame.Move(x, y,true);
					move[x][y]='1';
					if(theGame.boold(x, y)==true) {
						//System.out.println(x+" "+y);
						text="I saw blood!";
					}
					else if(theGame.smile(x, y)==true) {
						text="That is slime!";
					}
					else if(theGame.goop(x, y)==true) {
						text="That is goop!";
					}
					else if(theGame.lose(x, y)==true) {
						text="You Lose!";
					}
					else  {
						text="Make move";
					}
					updatemap();
				}
			}
			
		}
		
	}
	
	//this class is to set up the buttonlistener
	private class ButtonListener implements EventHandler<ActionEvent> {

	    @Override
	    public void handle(ActionEvent arg0) {
	    Button buttonClicked = (Button) arg0.getSource();
	    
	          if (N == buttonClicked) {//check the shooting position, then check if the hunter shoots the wumpus successfully
	        	  		
	        	  		SY--;
	        	  		int temp=SY;
	        	  		for(int i=temp;i>-1;i--) {
	        	  		 if(theGame.won(SX, SY)) {
	        				 text="YOU WIN!";
	        				 updatemap();
	        				 break;
	        			 }
	        	 
	        	  		 SY--;
	        	  		}
	        	  		SY=11;
	        	  		for(int i=11;i>temp;i--) {
		        	  		 if(theGame.won(SX, SY)) {
		        				 text="YOU WIN!";
		        				 updatemap();
		        				 break;
		        			 }
		        
		        	  		 SY--;
		        	  	}
	        	  		if(available[0][0]=='1') {
	        	  		text="You Lose!";
	        	  		for(int i=0;i<12;i++) {
	        	  			for(int j=0;j<12;j++) {
	        	  				available[i][j]='0';
	        	  			}
	        	  		}
	        	  		updatemap();
	        	  		}
	          } 
	          else if(S==buttonClicked) {
	        	  	 SY++;
	        	  	int temp=SY;
        	  		for(int i=temp;i<12;i++) {
        	  		 if(theGame.won(SX, SY)) {
        				 text="YOU WIN!";
        				 updatemap();
        				 break;
        			 }
        	  		 SY++;
        	  		}
        	  		SY=0;
        	  		for(int i=0;i<temp;i++) {
        	  			if(theGame.won(SX, SY)) {
           				 text="YOU WIN!";
           				 updatemap();
           				 break;
           			 }
           	  		 SY++;
        	  		}
        	  		
        	  		if(available[0][0]=='1') {
	        	  		text="You Lose!";
	        	  		for(int i=0;i<12;i++) {
	        	  			for(int j=0;j<12;j++) {
	        	  				available[i][j]='0';
	        	  			}
	        	  		}
	        	  		updatemap();
	        	  	}
	          }
	          else if(E==buttonClicked) {
	        	  	SX++;
	        	  	int temp=SX;
        	  		for(int i=temp;i<12;i++) {
        	  		 if(theGame.won(SX, SY)) {
        				 text="YOU WIN!";
        				 updatemap();
        				 break;
        			 }
        	  		 SX++;
        	  		}
        	  		SX=0;
        	  		
        	  		for(int i=0;i<temp;i++) {
        	  		 if(theGame.won(SX, SY)) {
        				 text="YOU WIN!";
        				 updatemap();
        				 break;
        			 }
        	  		 SX++;
        	  		}
        	  		
        	  		if(available[0][0]=='1') {
	        	  		text="You Lose!";
	        	  		for(int i=0;i<12;i++) {
	        	  			for(int j=0;j<12;j++) {
	        	  				available[i][j]='0';
	        	  			}
	        	  		}
	        	  		updatemap();
	        	  	}
	          }
	          else if(W==buttonClicked) {
	        	  	SX--;
	        	  	int temp=SX;
        	  		for(int i=temp;i>-1;i--) {
        	  		 if(theGame.won(SX, SY)) {
        				 text="YOU WIN!";
        				 updatemap();
        				 break;
        			 }
        	  		 SX--;
        	  		}
        	  		
        	  		SX=11;
        	  		for(int i=11;i>temp;i--) {
           	  		 if(theGame.won(SX, SY)) {
           				 text="YOU WIN!";
           				 updatemap();
           				 break;
           			 }
           	  		 SX--;
           	  		}
        	  		if(available[0][0]=='1') {
	        	  		text="You Lose!";
	        	  		for(int i=0;i<12;i++) {
	        	  			for(int j=0;j<12;j++) {
	        	  				available[i][j]='0';
	        	  			}
	        	  		}
	        	  		updatemap();
	        	  	}
	          }
	    
	  }
	}
}
