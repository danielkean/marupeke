package marupeke;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MarupekeGUI extends Application
{    
    protected static final int WINDOW_WIDTH = 700;
    protected static final int WINDOW_HEIGHT = 700;
    protected static final String WINDOW_TITLE = "Marupeke";
    protected static final boolean RESIZABLE = false;
    protected static final String BACKGROUND_COLOUR = "#1b1b1b";
    protected static final int GRID_BUTTON_SIZE = 48;
    protected static final int GRID_BUTTON_SPACING = 0;
    
    public static final Image RAW_IMAGE_X = new Image("file:assets/images/x.png");
    public static final Image RAW_IMAGE_O = new Image("file:assets/images/o.png");
    public static final Image RAW_IMAGE_SOLID = new Image("file:assets/images/solid.png");
    public static final Image RAW_IMAGE_BLANK = new Image("file:assets/images/blank.png");
    
    public static MarupekeGrid marupekeGrid;
    public static BorderPane root = new BorderPane();
    public static HBox topBorder = new HBox();
    public static GridPane gameGrid = new GridPane();
    
    public static TextField gameSize = new TextField("6");
    public static ComboBox<Difficulty> difficultyDropdown = new ComboBox<>();
    public static Button createButton = new Button("Create Puzzle");
    public static Button checkButton = new Button("Check Puzzle");
    public static CheckBox soundCheckBox = new CheckBox("Sound?");
    
    public static void main(String[] args) { launch(args); }
    
    @Override
    public void start(Stage window)
    {   
        //TOP BORDER
        difficultyDropdown.getItems().addAll(Difficulty.Easy, Difficulty.Medium, Difficulty.Hard);
        difficultyDropdown.getSelectionModel().selectFirst();
        soundCheckBox.setSelected(true);
        
        //Set Button Actions
        createButton.setOnAction(e ->
        {   
            try
            {
                gameGrid = createGrid();
            }
            catch(NumberFormatException exception)
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Game size isn't a number");
                alert.setContentText("The value passed in as the game width isn't number");
                alert.showAndWait();
            }
        });
        
        checkButton.setOnAction(e ->
        {
            checkGrid();
        });
        
        //Add controls to the top border
        topBorder.getChildren().add(gameSize);
        topBorder.getChildren().add(difficultyDropdown);
        topBorder.getChildren().add(createButton);
        topBorder.getChildren().add(checkButton);
        topBorder.getChildren().add(soundCheckBox);
        topBorder.setSpacing(10);
        
        gameGrid = createGrid();
        root.setTop(topBorder);
        
        //Window Settings
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setTitle(WINDOW_TITLE);
        window.setScene(scene);
        window.setResizable(RESIZABLE);
        window.show();
    }
    
    private GridPane createGrid()
    {
        int gameSizeNum = Integer.parseInt(gameSize.getText());
        
        if(gameSizeNum < 6 || gameSizeNum > 10)
        {   
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Game dimensions not supported");
            alert.setHeaderText(null);
            alert.setContentText("The dimensions of the game that you're trying to create aren't supported. Select a size between 6 and 10.");
            alert.showAndWait();
            
            return null;
        }
        
        marupekeGrid = MarupekeGrid.randomPuzzle(gameSizeNum, difficultyDropdown.getValue());
        GridPane gamePane = gameToGrid();
        
        paneSetup(gamePane);
        root.setCenter(gamePane);
        
        return gamePane;
    }
    
    private GridPane gameToGrid()
    {
        GridPane grid = new GridPane();
        
        for(int i = 0; i < marupekeGrid.getGrid().length; i++)
        {
            for(int j = 0; j < marupekeGrid.getGrid()[0].length; j++)
            {
                TileGUI tileGUI = new TileGUI(marupekeGrid.getGrid()[i][j].isEditable, marupekeGrid.getGrid()[i][j].state, i, j);
                grid.add(tileGUI.button, j, i);         
            }
        }
        
        return grid;
    }
    
    private void paneSetup(GridPane pane)
    {
        pane.setHgap(GRID_BUTTON_SPACING);
        pane.setVgap(GRID_BUTTON_SPACING);
        pane.setAlignment(Pos.CENTER);
        pane.setStyle("-fx-background-color:" + BACKGROUND_COLOUR + ";");
    }
    
    private boolean checkGrid()
    {
        boolean isComplete = marupekeGrid.isPuzzleComplete();

        // Show alert on screen when the user wants to check the grid
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle((isComplete) ? "Congratulations!" : "Unlucky!");
        alert.setHeaderText(null);
        alert.setContentText((isComplete) ? "Congratulations, You have completed the puzzle!" : "Unlucky, the puzzle has not been completed.");
        alert.showAndWait();

        if(isComplete) createGrid();

        return isComplete;
    }
}
