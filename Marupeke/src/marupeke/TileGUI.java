package marupeke;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

public class TileGUI
{
    public Button button;
    public Tile tile;
    
    private final int x;
    private final int y;
    
    public TileGUI(boolean isEditable, State state, int x, int y)
    {
        tile = new Tile(isEditable, state);
        button = buttonSetup();
        
        this.x = x;
        this.y = y;
    }
    
    private Button buttonSetup()
    {
        ImageView newImage = new ImageView();
        Button newButton = new Button();

        switch(tile.state)
        {
            case X ->       newImage.setImage(MarupekeGUI.RAW_IMAGE_X);
            case O ->       newImage.setImage(MarupekeGUI.RAW_IMAGE_O);
            case SOLID ->   newImage.setImage(MarupekeGUI.RAW_IMAGE_SOLID);
            case BLANK ->   newImage.setImage(MarupekeGUI.RAW_IMAGE_BLANK);
        }
        
        newImage.setFitWidth(MarupekeGUI.GRID_BUTTON_SIZE);
        newImage.setFitHeight(MarupekeGUI.GRID_BUTTON_SIZE);        
        newButton.setGraphic(newImage);
        
        newButton.setOnAction(e ->
        {
            // Play a different sound depending on if the tile is editable
            if(MarupekeGUI.soundCheckBox.isSelected())
            {
                String soundPath = (tile.isEditable) ? "file:assets/audio/click_1.mp3" : "file:assets/audio/click_2.mp3";
                AudioClip sound = new AudioClip(soundPath);
                sound.play();
            }

            if(tile.isEditable == false) return;
            
            TileGUI tileGUI = cycleButtonState();
            newButton.setGraphic(tileGUI.button.getGraphic());
            tile.state = tileGUI.tile.state;
        });
        
        return newButton;
    }
    
    private TileGUI cycleButtonState()
    {      
        State newState = switch(tile.state)
        {
            case X ->       State.O;
            case O ->       State.BLANK;
            case BLANK ->   State.X;
            default ->      tile.state;
        };

        TileGUI newTile = new TileGUI(true, newState, x, y);

        switch(newState)
        {
            case X ->       MarupekeGUI.marupekeGrid.setX(x, y, true);
            case O ->       MarupekeGUI.marupekeGrid.setO(x, y, true);
            case BLANK ->   MarupekeGUI.marupekeGrid.unmark(x, y);
        }
        
        return newTile;
    }
}
