import java.util.ArrayList;
import java.util.List;

public class Game extends Thread{
    private List<Player> players;
    protected Deck deck;
    private final Object turnLock;

    public Game(int numPlayers) {
        players = new ArrayList<>();
        turnLock = new Object();
        deck = new Deck();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player(i));
        }
        initializePlayers(numPlayers);
    }
    private void initializePlayers(int numPlayers) {
        for (int i = 0; i < numPlayers; i++) {
            Player currentPlayer = players.get(i);
            Player nextPlayer = players.get((i + 1) % numPlayers);
            currentPlayer.setNextPlayer(nextPlayer);
            currentPlayer.setTurnLock(turnLock);
        }
    }
    private void dealCards(){
        while (!deck.isEmpty()) {
            for (Player player : players) {
                if (deck.isEmpty()) {
                    break;
                }
                player.addCardToHand(deck.drawCard());
            }
        }
    }
    private void removeMatchingPairsForAllPlayers() {
        for (Player player : players) {
            player.discardMatchingPairs();
        }
    }
    private void removePlayersWithEmptyHands() {
        players.removeIf(player -> player.getHandSize() == 0);
    }
    private void startingPlayerThreads() {
        for (Player player : players) {
            player.start();
        }
    }
    @Override
    public void run() {
        dealCards();
        removeMatchingPairsForAllPlayers();
        removePlayersWithEmptyHands();
        int ind = 0;

        startingPlayerThreads();
        while (players.size() > 1) {
            ind%= players.size();
            Player player = players.get(ind);

            // Notify the player thread to take its turn
            synchronized (player) {
                player.notify();
            }

            if (!player.isFinished()) {
                synchronized (turnLock) {
                    try {
                        turnLock.wait(); // Wait for player's turn to finish
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (player.isFinished()) {
                if (players.size() > 2) {
                    int previousPlayerIndex = (ind - 1 + players.size()) % players.size();
                    int nextPlayerIndex = (ind + 1) % players.size();
                    players.get(previousPlayerIndex).setNextPlayer(players.get(nextPlayerIndex));
                }
                players.remove(ind);
                continue;
            }
            ind++;
        }
        System.out.println("Player " + players.get(0).getPlayerNumber() + "  is the loser of the game!!");
    }
}