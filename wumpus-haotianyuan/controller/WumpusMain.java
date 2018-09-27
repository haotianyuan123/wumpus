package controller;

//Author: Haotian Yuan
//Purpose: This class contains the main method of the project, it is to set the outline of the game 
import java.util.Observer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import model.theGame;
import views.TextView;
import views.ImageView;



public class WumpusMain extends Application {

  public static void main(String[] args) {
   launch(args);

  }
  private theGame theGame;
  private BorderPane window;
  private MenuBar menuBar;
  private Observer textView;
  private Observer DrawingView;
  private Observer currentView;
  @Override
  public void start(Stage stage) throws Exception {
	  	stage.setTitle("Wumpus");
	    window = new BorderPane();
	    Scene scene = new Scene(window, 240, 480);
	   
	   
	    setupMenus();
	    window.setTop(menuBar);
	    initializeGameForTheFirstTime();

	    // Set up the views
	   // buttonView = new ButtonView(theGame);
	    textView = new TextView(theGame);
	    DrawingView=new ImageView(theGame);
	    theGame.addObserver(textView);
	    //theGame.addObserver(textAreaView);
	    theGame.addObserver(DrawingView);
	    setViewTo(textView);
	    stage.setScene(scene);
	    stage.show();
    
  }
  
  private void initializeGameForTheFirstTime() {
	
	  theGame = new theGame();
  }


	 /*Method name: setupMenus
	 *Purpose:This method is to set up menus
	 *		  
	 */	
private void setupMenus() {
	  	MenuItem newGame = new MenuItem("New Game");// the menu to star a new game
	  	Menu SwitchView = new Menu("SwitchView");// the menu to switch two versions
	    MenuItem Text = new MenuItem("TextView");//textview
	    MenuItem Draw = new MenuItem("DrawingView");//drawingview
	    SwitchView.getItems().addAll(Text, Draw);//add textview and drawingview into switchview
	    menuBar = new MenuBar();
	    Menu options = new Menu("Options");
	    options.getItems().addAll(newGame,SwitchView);

	    menuBar.getMenus().addAll(options);
	    MenuItemListener menuListener = new MenuItemListener();
	    newGame.setOnAction(menuListener);
	    Text.setOnAction(menuListener);
	    Draw.setOnAction(menuListener);
	   // setViewTo(textView);
	   
	  }

/*Method name: setViewTo
*Purpose:This method is to change the view
*		  
*/	
  private void setViewTo(Observer newView) {
	    window.setCenter(null);
	    currentView = newView;
	    window.setCenter((Node) currentView);
	  }
  
  private class MenuItemListener implements EventHandler<ActionEvent> {

	    @Override
	    public void handle(ActionEvent e) {
	      // Find out the text of the JMenuItem that was just clicked
	      String text = ((MenuItem) e.getSource()).getText();
	      if (text.equals("TextView"))
	        setViewTo(textView);
	      else if (text.equals("DrawingView"))
	        setViewTo(DrawingView);
	      else if (text.equals("New Game"))
	        theGame.startNewGame(); // The computer player has been set and should not change.
	    }
	  }
}
