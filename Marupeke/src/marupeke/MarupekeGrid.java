package marupeke;

import java.util.ArrayList;
import java.util.Collections;

public class MarupekeGrid
{
    private Tile[][] grid;
    private ArrayList<String> illegalities = new ArrayList();
    
    public MarupekeGrid(int width)
    {
        if(width >= 10) width = 10;
        else if(width < 1) width = 1;
        
        grid = new Tile[width][width];
        
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < width; j++)
            {
                //Set all tiles to be editable and blank
                grid[i][j] = new Tile(true, State.BLANK);
            }
        }
    }
    
    public static MarupekeGrid randomPuzzle(int size, Difficulty difficulty)
    {
        int numFill = 0;
        int numX = 0;
        int numO = 0;

        switch(difficulty)
        {
            case Easy ->    { numFill = 8; numX = 2; numO = 2; }
            case Medium ->  { numFill = 5; numX = 4; numO = 4; }
            case Hard ->    { numFill = 3; numX = 5; numO = 5; }
        }

        int total = numFill + numX + numO;
        if(total > (size * size) / 2)
        {   
            System.err.println("The total exceeds the number of playable cells");
            return null;
        }
        
        boolean isLegal = false;
        MarupekeGrid finalGrid = new MarupekeGrid(size);
        
        while(isLegal == false)
        {
            MarupekeGrid newGrid = new MarupekeGrid(size);
            ArrayList<int[]> cells = new ArrayList<>();
            
            for(int i = 0; i < size; i++)
            {   
                for(int j = 0; j < size; j++)
                {
                    int[] cell = new int[2];
                    cell[0] = i;
                    cell[1] = j;

                    cells.add(cell);
                }
            }

            Collections.shuffle(cells);

            for(int i = 0; i < numFill; i++)
            {
                newGrid.setSolid(cells.get(0)[0], cells.get(0)[1]);
                cells.remove(0);
            }

            for(int i = 0; i < numX; i++)
            {            
                newGrid.setX(cells.get(0)[0], cells.get(0)[1], false);
                cells.remove(0);
            }

            for(int i = 0; i < numO; i++)
            {            
                newGrid.setO(cells.get(0)[0], cells.get(0)[1], false);
                cells.remove(0);
            }
            
            isLegal = newGrid.isLegal();
            if(isLegal) finalGrid = newGrid;
        }
        
        return finalGrid;
    }
    
    public boolean setSolid(int x, int y)
    {
        Tile tile = grid[x][y];
        boolean wasEditable = (tile.isEditable == true);
        
        if(wasEditable)
        {
            tile.state = State.SOLID;
            tile.isEditable = false;
        }
        
        return wasEditable;
    }
    
    public boolean setX(int x, int y, boolean canEdit)
    {
        Tile tile = grid[x][y];
        boolean wasEditable = (tile.isEditable == true);
        
        if(wasEditable)
        {
            tile.state = State.X;
            tile.isEditable = canEdit;
        }
        
        return wasEditable;
    }
    
    public boolean setO(int x, int y, boolean canEdit)
    {
        Tile tile = grid[x][y];
        boolean wasEditable = (tile.isEditable == true);
        
        if(wasEditable)
        {
            tile.state = State.O;
            tile.isEditable = canEdit;
        }
        
        return wasEditable;
    }

    public boolean unmark(int x, int y)
    {
        Tile tile = grid[x][y];

        if(tile.isEditable)
        {
            tile.state = State.BLANK;
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[0].length; j++)
            {
                switch(grid[i][j].state)
                {
                    case X -> builder.append(" X ");
                    case O -> builder.append(" O ");
                    case SOLID -> builder.append(" # ");
                    case BLANK -> builder.append(" _ ");
                }
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    public Tile[][] getGrid()
    {
        return grid;
    }

    public boolean isLegal()
    {
        boolean legal = true;

        //Neighbour Check
        for(int x = 0; x < grid.length; x++)
        {
            for(int y = 0; y < grid.length; y++)
            {
                Tile tile = grid[x][y];

                for(Direction direction : Direction.values())
                {
                    try
                    {
                        if(tile.state == State.X || tile.state == State.O)
                        {
                            if(grid[x + direction.x][y + direction.y].state == tile.state)
                            {
                                if(grid[x + direction.x * 2][y + direction.y * 2].state == tile.state)
                                {
                                    illegalities.add("There is a row of 3 " + tile.state.toString() + "'s");
                                    legal = false;
                                }
                            }
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException e) {}
                }
            }
        }

        if(legal == false) printIllegalities();

        return legal;
    }

    private void printIllegalities()
    {
        System.out.println("All illegalities in the grid:");
        for(String message : illegalities) { System.out.println("- " + message); }
    }

    public boolean isPuzzleComplete()
    {
        int blankCount = 0;

        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid.length; j++)
            {
                if(grid[i][j].state == State.BLANK)
                {
                    blankCount++;
                }
            }
        }

        return (blankCount == 0 && isLegal());
    }
}
