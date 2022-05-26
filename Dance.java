import java.util.ArrayList;
import java.util.List;

public class Dance {

    private Style style;
    private int counts = 0;
    private int score = 0;
    private List<Move> moves = new ArrayList<Move>();

    public Dance(Style style) {
        this.style = style;
    }

    public void addMove(Move move) {
        if (move.getStyle() == style) {
            counts += move.getCounts();
            moves.add(move);
        }
        else {
            System.err.println("Style of move and dance are not the same.");
        }
    }

    @Override
    public String toString() {
        int i = 0;
        String s = "{";
        for (i = 0; i < moves.size(); i++) {
            s += moves.get(i).getName();
            if (i != moves.size() - 1) {
                s += ", ";
            }
        }
        return s + "}";
    }

    public void addScore(int score) { this.score += score; }

    public Style getStyle() { return this.style; }

    public int getCounts() {
        return counts;
    }

    public int getScore() {
        return score;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public Move getMove(int i) {
        return moves.get(i);
    }
}