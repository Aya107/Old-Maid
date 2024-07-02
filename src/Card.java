import enums.CardColor;
import enums.CardSuit;
import enums.CardValue;
class Card {
    private final CardColor color;
    private final CardSuit suit;
    private final CardValue value;

    public Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
        this.color = (suit == CardSuit.HEARTS || suit == CardSuit.DIAMONDS) ? CardColor.RED : CardColor.BLACK;
    }
    public CardSuit getSuit() {
        return suit;
    }
    public CardValue getValue() {
        return value;
    }
    public CardColor getColor() {
        return color;
    }
    public boolean isMatchingPair(Card other) {
        return this.value == other.value && this.color == other.color;
    }
    @Override
    public String toString() {
        return value + " " + suit;
    }
}