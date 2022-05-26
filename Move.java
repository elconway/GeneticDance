public abstract class Move {

    private Style style;
    private String name = "";
    private int counts = 0;
    private int minimumLevel = 0;


    public Move(Style style, String name, int counts, int minimumLevel) {
        this.style = style;
        this.name = name;
        this.counts = counts;
        this.minimumLevel = minimumLevel;
    }

    @Override
    public String toString() {
        return name;
    }

    public Style getStyle() {
        return style;
    }

    public String getName() {
        return name;
    }

    public int getCounts() {
        return counts;
    }

    public int getMinimumLevel() { return minimumLevel; }
}
