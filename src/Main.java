import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();

        Game game = new Game(numPlayers);
        game.start();

        try {
            game.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Game Over!");
        System.exit(0);
    }
}