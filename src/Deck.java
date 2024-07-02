import enums.CardSuit;
import enums.CardValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Deck {
    private List<Card> cards;
    public Deck() {
        initializeDeck();
    }
    private void initializeDeck() {
        cards = new ArrayList<>();
        for (CardSuit suit : CardSuit.values()) {
            if(suit == CardSuit.Joker)
            {
                break;
            }
            for (int value = 1; value <= 13; value++) {
                cards.add(new Card(suit, CardValue.fromNumber(value)));
            }
        }
        cards.add(new Card(CardSuit.Joker, CardValue.Joker));
        shuffleDeck();
    }
    private void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size()-1);
        }
        return null;
    }
}
