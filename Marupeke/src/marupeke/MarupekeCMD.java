package marupeke;
import java.util.Scanner;

public class MarupekeCMD
{
    static Scanner reader = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        boolean running = true;
        MarupekeGrid game = new MarupekeGrid(5);
     
        while(running)
        {
            String command = readInput("Please enter a command: ");
            
            if(command.equals("quit"))
            {
                running = false;
            }
            
            else if(command.equals("new"))
            {
                int size = Integer.parseInt(readInput("Game Size: "));
                game = MarupekeGrid.randomPuzzle(size, 5, 3, 3);
                
                System.out.print(game.toString());
            }
            
            else if(command.equals("xmark"))
            { 
                int x = Integer.parseInt(readInput("Row Position: "));
                int y = Integer.parseInt(readInput("Column Position: "));
                
                game.setX(x, y, true);
                System.out.print(game.toString());
            }
            
            else if(command.equals("omark"))
            {
                int x = Integer.parseInt(readInput("Row Position: "));
                int y = Integer.parseInt(readInput("Column Position: "));
                
                game.setO(x, y, true);
                System.out.print(game.toString());
            }
                        
            else if(command.equals("unmark"))
            {
                int x = Integer.parseInt(readInput("Row Position: "));
                int y = Integer.parseInt(readInput("Column Position: "));
                
                game.unmark(x, y);
                System.out.print(game.toString());
            }
        }   
        System.out.println("END");
    }
    
    private static String readInput(String prompt)
    {
        System.out.print(prompt);
        return reader.next().toLowerCase();
    }
}
