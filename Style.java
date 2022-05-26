import java.util.ArrayList;
import java.util.List;

public class Style {

    private String name = "";
    private Move basic;
    private List<Move> moves = new ArrayList<Move>();

    public Style(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public Move getBasic() {
        return basic;
    }

    public List<Move> getMoves(int level) {
        int i = 0;
        List<Move> levelList = new ArrayList<Move>();
        Move move;
        for (i = 0; i < moves.size(); i++) {
            move = moves.get(i);
            if (move.getMinimumLevel() <= level) {
                levelList.add(move);
            }
        }
        return levelList;
    }

    public void setBasic(Move basic) {
        this.basic = basic;
    }

    public void addMove(Move toAdd) {
        moves.add(toAdd);
    }
}
