package tests;
/*Author:Haotian Yuan
 * 
 * This class is to test the game
 */
import static org.junit.Assert.*;

import org.junit.Test;

import model.theGame;

public class WumpusTest {
	//test the init board
  @Test
  public void test1() {
	  for(int k=0;k<1000;k++) {
    theGame game=new theGame();
    char[][] temp=game.getBoard();
    int wumpus=0;
    int hunter=0;
    int p=0;
    for(int i=0;i<12;i++) {
    		for(int j=0;j<12;j++) {
    			if(temp[i][j]=='W') {
    				wumpus++;
    			}
    			if(temp[i][j]=='O') {
    				hunter++;
    			}
    			if(temp[i][j]=='P') {
    				p++;
    			}
    		}
    		System.out.println();
    		
    }
    assertNotEquals(p,2);// 3 4 5
    assertNotEquals(p,6);
    assertEquals(wumpus, 1);//check the init of wumpus and hunter
    assertEquals(hunter, 1);
	  }
  }
  
  //test the init items in the board
  @Test
  public void test2() {
	  for(int l=0;l<1000;l++) {
	  theGame game=new theGame();
	    char[][] temp=game.getBoard();
	    boolean blood=false;
	    boolean smile=false;
	    boolean goop=false;
	    
	    for(int i=0;i<12;i++) {
    		for(int j=0;j<12;j++) {
    			if(temp[i][j]=='S') {
    				smile=game.smile(i, j);//test smile position
    				assertTrue(smile);
    			}
    			else {
    				smile=game.smile(i, j);;
    				assertFalse(smile);
    			}
    			if(temp[i][j]=='G') {
    				goop=game.goop(i,j);//test goop positoin
    				assertTrue(goop);
    			}
    			else {
    				goop=game.goop(i,j);
    				assertFalse(goop);
    			}
    			if(temp[i][j]=='B') {
    				blood=game.boold(i, j);//test blood position
    				assertTrue(blood);
    			}
    			else {
    				blood=game.boold(i, j);
    				assertFalse(blood);
    			}
    			blood=false;
    			smile=false;
    			goop=false;
    		}
    		
	    }
	  }
  }
  
  //test if the available move is correct
  @Test
  public void test3() {
	  theGame game=new theGame();
	   
	    char[][] available=game.getavailable();//check the available
	    boolean check=false;
	    for(int i=0;i<12;i++) {
	    		for(int j=0;j<12;j++) {
	    			if(available[i][j]=='1') {
	    				check=true;
	    			}
	    			else {
	    				check=false;
	    			}
	    	}
	    }
	    assertTrue(check);
	    
  }
  
  //test the follow of hunter's position
  @Test
  public void test4() {
	  theGame game=new theGame();
	  char[][] temp=game.getBoard();
	  int Hx=0;//the hunter position
	  int Hy=0;
	  for(int i=0;i<12;i++) {
		  for(int j=0;j<12;j++) {
			  if(temp[i][j]=='O') {
				  Hx=i;
				  Hy=j;
			  }
		  }
	  }
	  assertEquals(Hx,game.gethunterX());
	  assertEquals(Hy,game.gethunterY());
  }
//test the win and lose
  @Test
  public void test5() {
	  theGame game=new theGame();
	  char[][] temp=game.getBoard();
	  int Hx=0;
	  int Hy=0;
	  int Wx=0;
	  int Wy=0;
	  for(int i=0;i<12;i++) {
		  for(int j=0;j<12;j++) {
			  if(temp[i][j]=='O') {
				  Hx=i;
				  Hy=j;
			  }
			  if(temp[i][j]=='W') {
				  Wx=i;
				  Wy=j;
			  }
		  }
	  }
	  assertTrue(game.lose(Wx, Wy));
	  assertTrue(game.won(Wx, Wy));
  }
  
  
}
