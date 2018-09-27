package model;
//Author: Haotian Yuan
//Purpose: This class is to check the process of the game 
import java.util.Observable;
import java.util.Random;

public class theGame extends Observable{
	 private char[][] board;//the game board
	 private int size;//the board size
	 private int hunterX;//hunter position
	 private int hunterY;
	 private char[][] copy;//the original board
	 private char[][] available;//to check if this position is available to move
	
	 public theGame() {
		 size = 12;
		 available=new char[12][12];
		 initializeBoard();	
		 setChanged();
		 notifyObservers();
	 }
	 
	 	/*Method name: initializePane()
		 *Purpose:This method is to init the board with random items
		 * 
		 */	
	 private void initializeBoard() {
		 board = new char[size][size];
		 for (int r = 0; r < size; r++) {
			 for (int c = 0; c < size; c++) {
				available[r][c]='1';
		        board[r][c] = 'X';
		      }
		 }
		 Random rand = new Random();
		 int WumpusX  = rand.nextInt(12) + 0;//random the position of wumpus
		 int WumpusY  = rand.nextInt(12) + 0;
		
		 board[WumpusX][WumpusY]='W';//the wumpus
		 if(WumpusX-1>-1) {
			 board[WumpusX-1][WumpusY]='B';//the blood
		 }
		 else {
			 board[11][WumpusY]='B';
		 }
		 if(WumpusX-2>-1) {
			 board[WumpusX-2][WumpusY]='B';//the blood
		 }
		 else {
			 if( board[11][WumpusY]=='B') {
				 board[10][WumpusY]='B';
			 }
			 else {
				 board[11][WumpusY]='B';
			 }
		 }
		 if(WumpusX+1<12) {
			 board[WumpusX+1][WumpusY]='B';//the blood
		 }
		 else {
			 board[0][WumpusY]='B';
		 }
		 if(WumpusX+2<12) {
			 board[WumpusX+2][WumpusY]='B';//the blood
		 }
		 else {
			 if( board[0][WumpusY]=='B') {
				 board[1][WumpusY]='B';
			 }
			 else{
				 board[0][WumpusY]='B';
			 }
				 
		 }
		
		 if(WumpusY-1>-1) {
			 board[WumpusX][WumpusY-1]='B';//the blood
		 }
		 else {
			 board[WumpusX][11]='B';
		 }
		 if(WumpusY-2>-1) {
			 board[WumpusX][WumpusY-2]='B';//the blood
		 }
		 else {
			 if(board[WumpusX][11]=='B') {
				 board[WumpusX][10]='B';
			 }
			 else {
				 board[WumpusX][11]='B';
			 }
		 }
		 if(WumpusY+1<12) {
			 board[WumpusX][WumpusY+1]='B';//the blood
		 }
		 else {
			 board[WumpusX][0]='B';
		 }
		 if(WumpusY+2<12) {
			 board[WumpusX][WumpusY+2]='B';//the blood
		 }
		 else {
			 if(board[WumpusX][0]=='B') {
				 board[WumpusX][1]='B';
			 }
			 else {
				 board[WumpusX][0]='B';
			 }
		 }
		 if(WumpusX-1>-1 && WumpusY-1>-1) {
			 board[WumpusX-1][WumpusY-1]='B';//the blood
		 }
		 else {
			 if(WumpusX-1<0 && WumpusY-1>-1) {
				 board[11][WumpusY-1]='B';
			 }
			 else if(WumpusX-1>-1 && WumpusY-1<0) {
				 board[WumpusX-1][11]='B';
			 }
			 else {
				 board[11][11]='B';
			 }
		 }
		 if(WumpusX+1<12 && WumpusY-1>-1) {
			 board[WumpusX+1][WumpusY-1]='B';//the blood
		 }
		 else {
			 if(WumpusX+1>11 && WumpusY-1>-1) {
				 board[0][WumpusY-1]='B';
			 }
			 else if(WumpusX+1<12 && WumpusY-1<0) {
				 board[WumpusX][11]='B';
			 }
			 else {
				 board[0][11]='B';
			 }
		 }
		 if(WumpusX-1>-1 && WumpusY+1<12) {
			 board[WumpusX-1][WumpusY+1]='B';//the blood
		 }
		 else {
			 if(WumpusX-1<0 && WumpusY+1<12) {
				 board[11][WumpusY+1]='B';
			 }
			 else if(WumpusX-1>-1 && WumpusY+1>11) {
				 board[WumpusX-1][0]='B';
			 }
			 else {
				 board[11][0]='B';
			 }
		 }
		 if(WumpusX+1<12 && WumpusY+1<12) {
			 board[WumpusX+1][WumpusY+1]='B';//the blood
		 }
		 else {
			 if(WumpusX+1>11 && WumpusY+1<12) {
				 board[0][WumpusY+1]='B';
			 }
			 else if(WumpusX+1<12 && WumpusY+1>11) {
				 board[WumpusX+1][0]='B';
			 }
			 else {
				 board[0][0]='B';
			 }
			 
		 }
		 int pits  = rand.nextInt(3) + 3;//random the number of pits
		 for(int i=0;i<pits;i++) {
			 int pitsX  = rand.nextInt(12) + 0;//random the position of pits
			 int pitsY  = rand.nextInt(12) + 0;
			
			 
			 while(board[pitsX][pitsY]=='W' || board[pitsX][pitsY]=='P') {
				 pitsX  = rand.nextInt(12) + 0;
				 pitsY  = rand.nextInt(12) + 0;
			 }
			 board[pitsX][pitsY]='P';
			 if(pitsX+1<12 && board[pitsX+1][pitsY]!='W' && board[pitsX+1][pitsY]!='P') {
				
				 if( board[pitsX+1][pitsY]=='B') {
					 board[pitsX+1][pitsY]='G';//the goop
				 }
				 else {
					 board[pitsX+1][pitsY]='S';//the Slime
				 }
			 }
			 else if(pitsX+1>11 && board[0][pitsY]!='W' && board[0][pitsY]!='P') {
				 if( board[0][pitsY]=='B') {
					 board[0][pitsY]='G';//the goop
				 }
				 else {
					 board[0][pitsY]='S';//the Slime
				 }
			 }
			 if(pitsX-1>-1 && board[pitsX-1][pitsY]!='W' && board[pitsX-1][pitsY]!='P') {			
				 if( board[pitsX-1][pitsY]=='B') {
					 board[pitsX-1][pitsY]='G';//the goop
				 }
				 else {
					 board[pitsX-1][pitsY]='S';//the Slime
				 }
			 }
			 else if(pitsX-1<0 && board[11][pitsY]!='W' && board[11][pitsY]!='P') {			
				 if( board[11][pitsY]=='B') {
					 board[11][pitsY]='G';//the goop
				 }
				 else {
					 board[11][pitsY]='S';//the Slime
				 }
			 }
			 if(pitsY+1<12 && board[pitsX][pitsY+1]!='W' && board[pitsX][pitsY+1]!='P') {
				
				 if( board[pitsX][pitsY+1]=='B') {
					 board[pitsX][pitsY+1]='G';//the goop
				 }
				 else {
					 board[pitsX][pitsY+1]='S';//the Slime
				 }
			 }
			 else if(pitsY+1>11 && board[pitsX][0]!='W' && board[pitsX][0]!='P') {
				
				 if( board[pitsX][0]=='B') {
					 board[pitsX][0]='G';//the goop
				 }
				 else {
					 board[pitsX][0]='S';//the Slime
				 }
			 }
			 if(pitsY-1>-1 && board[pitsX][pitsY-1]!='W' && board[pitsX][pitsY-1]!='P') {
				 if( board[pitsX][pitsY-1]=='B') {
					 board[pitsX][pitsY-1]='G';//the goop
				 }
				 else {
					 board[pitsX][pitsY-1]='S';//the Slime
				 }
			 }
			 else if(pitsY-1<0 && board[pitsX][11]!='W' && board[pitsX][11]!='P') {
				 if( board[pitsX][11]=='B') {
					 board[pitsX][11]='G';//the goop
				 }
				 else {
					 board[pitsX][11]='S';//the Slime
				 }
			 }
		 }
			 	hunterX  = rand.nextInt(12) + 0;//random the position of the hunter
			 	hunterY  = rand.nextInt(12) + 0;
			 while(board[hunterX][hunterY]!='X') {
				 hunterX  = rand.nextInt(12) + 0;
				 hunterY  = rand.nextInt(12) + 0;
			 }
			 board[hunterX][hunterY]='O';
			 copy=new char[size][size];
			 for(int i=0;i<12;i++) {
				 for(int j=0;j<12;j++) {
					 copy[i][j]=board[i][j];
				 }
			 }
			 copy[hunterX][hunterY]='X';
				 
	 }
	 

	 /*Method name: gethunterX,gethunterY
	 *Purpose:This method is to get the position of the hunter
	 *		  
	 */	
	 public int gethunterX() {
		 return hunterX;
	 }
	 
	 public int gethunterY() {
		 return hunterY;
	 }
	 

	 /*Method name: startNewGame
	 *Purpose:This method is to star the new game
	 *		  
	 */	
	 public void startNewGame() {
		 //System.out.println(1);
		initializeBoard();
		    // The state of this model just changed so tell any observer to update themselves
		setChanged();
		notifyObservers("startNewGame()");
		
	 }
	 

	 /*Method name: getBoard
	 *Purpose:This method is to return the board of the game
	 *		  
	 */	
	 public char[][] getBoard() {
		    return board;
	 }
	 

	 /*Method name:Move
	 *Purpose:This method is to follow the position of the hunter to move
	 *		  
	 */	
	 public void Move(int row, int col,boolean check) {
		   	if(check==false) {
		   		
		   		board[row][col]=copy[row][col];
		   		return;
		   	}
		   	hunterX=row;
		   	hunterY=col;
		    board[row][col] = 'O';
		    setChanged();
		    notifyObservers();
	 }
	 

	 /*Method name: won
	 *Purpose:This method is to check if the hunter shoots the wumpus
	 *		  
	 */	
	 public boolean won(int x,int y) {
		 if( copy[x][y]=='W' ) {
			 for(int i=0;i<12;i++) {
				 for(int j=0;j<12;j++) {
					 available[i][j]='0';//you cannot move anymore if the game is over
				 }
			 }
			 return true;
		 }
		 else {
			 return false;
		 }
	 }
	 

	 /*Method name:lose
	 *Purpose:This method is to check if the player loses the game
	 *		  
	 */	
	 public boolean lose(int x,int y) {
		
		 if( copy[x][y]=='P' || copy[x][y]=='W' || copy[x][y]=='O') {
			 for(int i=0;i<12;i++) {
				 for(int j=0;j<12;j++) {
					 available[i][j]='0';//you cannot move anymore if the game is over
				 }
			 }
			 return true;
		 }
		 else {
			 return false;
		 }
	 }
	 
	 

	 /*Method name: smile,goop,boold
	 *Purpose:This method is to get the position of items
	 *		  
	 */	
	 public boolean smile(int x,int y) {
		 if(copy[x][y]=='S') {
			 return true;
		 }
		 return false;
	 }
	 
	 public boolean goop(int x,int y) {
		 if(copy[x][y]=='G') {
			 return true;
		 }
		 return false;
	 }
	 
	 public boolean boold(int x,int y) {
		 if(copy[x][y]=='B') {
			 return true;
		 }
		 return false;
	 }
	 

	 /*Method name: getavailble
	 *Purpose:This method is to see if this positon is available to move
	 *		  
	 */	
	 public char[][] getavailable(){
		 return available;
	 }

}
