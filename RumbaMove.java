public class RumbaMove extends Move {

    private int minimumLevel = 0;
    private boolean switchFoot = false;
    private boolean onlyOnRight = false;

    public RumbaMove(Style style, String name, int counts, int minimumLevel, boolean switchFoot) {
        super(style, name, counts, minimumLevel);
        this.switchFoot = switchFoot;
    }

    public RumbaMove(Style style, String name, int counts, int minimumLevel, boolean switchFoot, boolean onlyOnRight) {
        super(style, name, counts, minimumLevel);
        this.switchFoot = switchFoot;
        this.onlyOnRight = onlyOnRight;
    }

    public boolean getSwitchFoot() {
        return switchFoot;
    }

    public boolean getOnlyOnRight() {
        return switchFoot;
    }
}
