package marupeke;

public class Tile
{
    public boolean isEditable;
    public State state;
    
    public Tile(boolean isEditable, State state)
    {
        this.isEditable = isEditable;
        this.state = state;
    }
}
