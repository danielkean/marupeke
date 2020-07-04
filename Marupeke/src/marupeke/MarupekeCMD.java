package marupeke;
import java.util.Scanner;

public class MarupekeCMD
{
    static Scanner reader = new Scanner(System.in);
    static MarupekeGrid game = null;
    
    public static void main(String[] args)
    {
        boolean running = true;

        System.out.println("MARUPEKE");
        System.out.println("-------------------------------------------");
     
        while(running)
        {
            String command = readInput("Please enter a command: ");

            switch(command)
            {
                case "quit" ->
                {
                    running = false;
                }
                case "new" ->
                {
                    int size = Integer.parseInt(readInput("Game Size: "));
                    game = MarupekeGrid.randomPuzzle(size, 5, 3, 3);

                    System.out.print(game.toString());
                }
                case "x" ->
                {
                    if(game == null) break;

                    int[] coords = getUserCoordinates();
                    if(coords == null) break;

                    game.setX(coords[0], coords[1], true);

                    System.out.print(game.toString());
                }
                case "o" ->
                {
                    if(game == null) break;

                    int[] coords = getUserCoordinates();
                    if(coords == null) break;

                    game.setO(coords[0], coords[1], true);

                    System.out.print(game.toString());
                }
                case "unmark" ->
                {
                    if(game == null) break;

                    int[] coords = getUserCoordinates();
                    if(coords == null) break;

                    game.unmark(coords[0], coords[1]);

                    System.out.print(game.toString());
                }
                case "help" ->
                {
                    System.out.println("Commands ----------------------------------");
                    System.out.println("'new' - Create a new game");
                    System.out.println("'quit' - Quit the game");
                    System.out.println("'x' - Place an 'X' mark on the grid");
                    System.out.println("'o' - Place an 'O' mark on the grid");
                    System.out.println("'unmark' - Un-mark a tile on the grid");
                    System.out.println("-------------------------------------------");
                }
            }
        }

        System.out.println("Thanks for playing!");
    }
    
    private static String readInput(String prompt)
    {
        System.out.print(prompt);
        return reader.next().toLowerCase();
    }

    private static int[] getUserCoordinates()
    {
        int x = Integer.parseInt(readInput("Row Position: "));
        int y = Integer.parseInt(readInput("Column Position: "));

        if(x < 0 || y < 0 || x > game.getGrid().length-1 || y > game.getGrid().length-1)
        {
            System.out.println("Please enter values that are within the game bounds: " + "(0-" + (game.getGrid().length - 1) + ")");
            return null;
        }

        return new int[]{x,y};
    }
}
