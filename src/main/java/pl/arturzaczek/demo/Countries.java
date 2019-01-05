package pl.arturzaczek.demo;

public enum Countries {
    USA("Stany zjednoczone", "US"),
    POLAND("Polska", "PL"),
    UKRAINE("Ukraina", "UA"),
    FRANCE("Francja", "FR");

    private String polishTranslation;
    private String symbol;

    Countries(String polishTranslation, String symbol) {
        this.polishTranslation = polishTranslation;
        this.symbol = symbol;
    }
}
