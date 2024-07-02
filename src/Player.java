import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Player extends Thread {
    private int playerNumber;
    private List<Card> hand;
    private Player nextPlayer;
    private Object turnLock;
    private static final Random random = new Random();
    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.hand = new ArrayList<>();
    }
    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }
    public void setTurnLock(Object turnLock) {
        this.turnLock = turnLock;
    }
    public int getHandSize() {
        return hand.size();
    }
    public int getPlayerNumber() {
        return playerNumber;
    }
    public void addCardToHand(Card card) {
        hand.add(card);
    }
    public boolean isFinished() {
        return getHandSize() == 0;
    }
    public void showHand() {
        System.out.println(playerNumber + "'s hand: ");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i).toString());
        }
    }
    public void discardMatchingPairs() {
        for (int i = 0; i < hand.size(); i++) {
            for (int j = i + 1; j < hand.size(); j++) {
                if (hand.get(i).isMatchingPair(hand.get(j))) {
                    hand.remove(j); // Remove the card at higher index first
                    hand.remove(i); // Remove the card at lower index
                    i--; // Adjust index after removal
                    break; // Exit inner loop after a pair is removed
                }
            }
        }
    }
    private void discardMatchingPair(int j) {
        for (int i = 0; i < hand.size()-1; i++) {
            if (hand.get(i).isMatchingPair(hand.get(j))) {
                hand.remove(j);
                hand.remove(i);
                break;
            }
        }
    }
    private void takeTurn() {
        synchronized (nextPlayer) {
            if (nextPlayer.getHandSize() > 0) {
                int randomIndex = random.nextInt(nextPlayer.getHandSize());
                Card playedCard = nextPlayer.hand.remove(randomIndex);
                addCardToHand(playedCard);

                System.out.println("Player " + playerNumber + " took a card from Player " + nextPlayer.playerNumber);
                discardMatchingPair(hand.size() - 1);
                nextPlayer.isFinished();
            }
        }
    }
    private void notifyGame() {
        synchronized (turnLock) {
            turnLock.notify();
        }
    }
    // Implement run method to simulate player's turn
    @Override
    public void run() {
        while (!isFinished()) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (isFinished()) {
                break;
            }

            takeTurn();
            showHand();
            notifyGame();
        }
        System.out.println("Player " + playerNumber + " is finished-------------------------------------------------------------");
    }
}