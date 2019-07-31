package game;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 
 * Main. 
 * Call and render the chessboard.
 * Call and render the chess pieces and gamecomponents. 
 * 
 * @author Eunhak Lee A01026056 Set2B
 * @version Feb 09, 2018
 */
public class Main extends Application {
	
	
	/**
	 * Connection to the server. 
	 */
	private ConnectionManager connectionManager;
    
    /**
     * Contain all groups of rendering. 
     */
    private Group root;
    
    private Text title;
    
    private ImageView iv;
    
    private TextField ipAddressField;
    private TextField portField;
    
    /**
     * Text to Indiate the Session Info
     */
    private Text sessionIndicator;
    
    /**
     * Game Component object. 
     */
    protected GameLogic gameMain = new GameLogic();
    
    /**
     * Nodes to display the current game. 
     */
    private Group gameComponent;
    
    /**
     * Button to start the game. 
     */
    private Button startButton;
    
    private Button saveButton;
    
    //private Button openButton;
    
    /**
     * Action when the user clicks on something. 
     * @param event of clicks. 
     */
    public void processMousePress(MouseEvent event) {
        gameComponent = gameMain.click(event.getX(), event.getY());
        root.getChildren().remove(gameComponent);
        root.getChildren().add(gameComponent);
    }
    
    /**
     * What happens when the start button is pressed. 
     * @param event of pressing button. 
     * @throws Exception 
     * @throws UnknownHostException 
     * @throws NumberFormatException 
     */
    public void startButtonHandler(ActionEvent event) {
    	
    	sessionIndicator.setText("Connecting to Server...");

            try {
				connectionManager = new ConnectionManager(
				        InetAddress.getByName(ipAddressField.getText()), 
				        Integer.parseInt(portField.getText())
				        );
			} catch (Exception e) {
				// TODO Auto-generated catch block
		    	sessionIndicator.setText("Server Not Found!");
			}
        root.getChildren().remove(ipAddressField);
        root.getChildren().remove(portField);
        root.getChildren().add(sessionIndicator);
        connectionManager.findSession();
    	
        if (connectionManager.getSessionNumber() != -1) {
            root.getChildren().remove(gameComponent);
            gameMain = new GameLogic();
            gameComponent = gameMain.play();
            root.getChildren().remove(gameComponent);
            root.getChildren().add(gameComponent);
        }
        
     }
    
    public void saveButtonHandler(ActionEvent event) throws IOException{
        
        saveObject so = new saveObject(gameMain.getPieces(), gameMain.getTurn(), gameMain.getGameStarted());
        
        try {
            FileOutputStream fileOut = new FileOutputStream("gameMain.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(so);
            out.flush();
            out.close();
            Text saved = new Text("Saved");
            gameComponent.getChildren().add(saved);
            saved.setLayoutX(10);
            saved.setLayoutY(30);
            saved.setFont(Font.font("Courier New", FontWeight.BOLD, 30));
            saved.setFill(Color.RED);
        }catch(Exception e){
            Text saved = new Text("Can not save.");
            root.getChildren().add(saved);
        }
    }
    
    public void openButtonHandler(ActionEvent event) throws IOException, ClassNotFoundException {
        saveObject so = new saveObject();
        try {
            FileInputStream f2 = new FileInputStream("gameMain.ser");
            ObjectInputStream in = new ObjectInputStream(f2);
            so = (saveObject) in.readObject();
            in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        root.getChildren().remove(gameComponent);
        gameMain = new GameLogic(so.getPieces(), so.getTurn(), so.getGamestarted());
        gameComponent = gameMain.play(1);
        root.getChildren().remove(gameComponent);
        root.getChildren().add(gameComponent);
    }
    
    @Override
    public void start(Stage mainStage) throws Exception {
        // TODO Auto-generated method stub
        final int appWidth = 900;
        final int appHeight = 800;
        final int titlePosition = 100;
        
        gameMain = new GameLogic();
        
        title = new Text(titlePosition,
                titlePosition, "David Lee Chess Game");
        title.setFont(Font.font("Courier New", FontWeight.BOLD, 55));
        title.setFill(Color.BLACK);
        
        ipAddressField = new TextField ();
        ipAddressField.setLayoutX(70);
        ipAddressField.setLayoutY(140);
        
        portField = new TextField();
        portField.setLayoutX(70);
        portField.setLayoutY(170);
        
        startButton = new Button("Find Session");
        startButton.setLayoutX(70);
        startButton.setLayoutY(220);
        startButton.setOnAction(event -> {
				startButtonHandler(event);
			
		});
        
        sessionIndicator = new Text();
        sessionIndicator.setFont(Font.font("Verdana", 10));
        sessionIndicator.setFill(Color.BLACK);
        sessionIndicator.setLayoutX(70);
        sessionIndicator.setLayoutY(270);
        
        saveButton = new Button("Save Current State");
        saveButton.setLayoutX(60);
        saveButton.setLayoutY(670);

            saveButton.setOnAction(arg0 -> {
                try {
                    saveButtonHandler(arg0);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        
        /**
        openButton = new Button("Open Last Saved Game");
        openButton.setLayoutX(60);
        openButton.setLayoutY(710);
        openButton.setOnAction(arg0 -> {
            try {
                openButtonHandler(arg0);
            } catch (ClassNotFoundException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    	*/
        
        root = new Group();
        mainStage = new Stage();
        Board b = new Board();
        
        iv = new ImageView(new Image("file:./ImageFiles/519553256.jpg"));
        iv.setX(0);
        iv.setY(0);
        iv.setFitHeight(appHeight);
        iv.setFitWidth(appWidth);
        
        
        //then you set to your node
        root.getChildren().add(iv);
        root.getChildren().add(b.returnBoard());
        root.getChildren().add(title);
        root.getChildren().add(startButton);
        root.getChildren().add(saveButton);
        root.getChildren().add(ipAddressField);
        root.getChildren().add(portField);
        //root.getChildren().add(openButton);
        
        Scene scene = new Scene(root, appWidth, appHeight, Color.GREY);
        scene.setOnMousePressed(this::processMousePress);
        mainStage.setTitle("David Lee Chess Game");
        mainStage.setScene(scene);
        mainStage.show();
        
    }
    
    /**
     * Drives the program. 
     * @param args unused. 
     * @throws Exception Node 
     */
    public static void main(String[] args) throws Exception {
        launch(args);
         
    }

}

