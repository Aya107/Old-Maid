package enums;

public enum CardValue {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, Joker;

    public static CardValue fromNumber(int number) {
        if (number >= 1 && number <= 13) {
            return CardValue.values()[number - 1];
        } else {
            throw new IllegalArgumentException("Invalid number for enums.CardValue");
        }
    }
}
