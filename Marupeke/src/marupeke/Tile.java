package marupeke;

public class Tile
{
    public boolean editable;
    public State state;
    
    public Tile(boolean editable, State state)
    {
        this.editable = editable;
        this.state = state;
    }
}
