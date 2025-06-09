public enum Seed {
    NO_SEED(" "),
    CROSS("X"),
    NOUGHT("O");

    private final String icon;

    Seed(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
