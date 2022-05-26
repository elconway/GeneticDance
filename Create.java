import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.round;

public class Create {

    public static void main (String[] args) {
        List<Style> styles = new ArrayList<Style>();
        List<Dance> danceList = new ArrayList<Dance>();
        Style style;
        Dance dance;
        Scanner s = new Scanner(System.in);
        int dances = -1, len = -1, level = -1, people = -1, move = -1, i = 0, j = 0, score = 0;
        float mutation = -1;
        boolean notDone = true;

        styles.add(getRumba());
        styles.add(getChaCha());

        for (i = 0; i < styles.size(); i++) {
            System.out.println((i + 1) + ". " + styles.get(i).getName());
        }
        dances = getInt("What style would you like to create? (1-" + styles.size() + ") ", 1, styles.size());
        style = styles.get(dances - 1);
        len = getInt("How many counts would your like your " + style + " to be? (8-128) ", 8, 128);
        while (len % 4 != 0) {
            System.out.println("Invalid entry: You must choose a number divisible by 4.");
            len = getInt("How many counts would your like your " + style + " to be? (8-128) ", 8, 128);
        }
        level = getInt("What level would you like your " + style + " to be? (0: Pre-bronze, 1: Bronze, 2: Silver, 3: Gold) ", 0, 3);
        people = getInt("How many people are contributing to your " + style + "? (1-4) ", 1, 4);
        dances = getInt("How many dances would you like to use as a basis? (0-4) ", 0, 4);
        if (dances == 0) {
            dance = new Dance(style);
            dance.addMove(style.getBasic());

        } else {
            for (i = 0; i < dances; i++) {
                dance = getDance(style, level);
                for (j = 0; j < people; j++) {
                    dance.addScore(getInt("What score does contributor " + (j + 1) + " give this dance? (1-10) ", 1, 10) - 1);
                }
                score += dance.getScore();
                danceList.add(dance);
            }
        }
        mutation = (float)getInt("What would you like your mutation rate to be? (0-5, will be divided by 10) ", 0, 5) / 10;
        System.out.println("Counts: " + len + " Level: " + level + " People: " + people + " Mutation: " + mutation + " Dances: " + dances);
        //i = 0;
        while (notDone) {
            danceList = makeDance(danceList, mutation, len, level);
            System.out.println(danceList.get(i));
            for (j = 0; j < people; j++) {
                danceList.get(i).addScore((int)Math.pow(getInt("What score does contributor " + (j + 1) + " give this dance? (1-10) ", 1, 10) - 1, 2));
            }
            System.out.println();
            if (danceList.get(i).getScore() >= people * 81) {
                notDone = false;
            }
            i++;
        }
    }

    static List<Dance> makeDance(List<Dance> danceList, float mutation, int len, int level) {
        int i = 0, j = 0, totalScore = 0, select = 0;
        Random r = new Random();
        float choice = r.nextFloat();
        Style style = danceList.get(0).getStyle();
        Dance dance, fromDance;
        List<Move> moves = style.getMoves(level);
        Move move;
        for (i = 0; i < danceList.size(); i++) {
            totalScore += danceList.get(i).getScore();
        }
        dance = new Dance(danceList.get(0).getStyle());
        while (dance.getCounts() < len) {
            if (choice < mutation) {
                select = (int) ((choice / mutation) * moves.size());
                if (dance.getCounts() + moves.get(select).getCounts() <= len) {
                    dance.addMove(moves.get(select));
                }
            } else {
                select = (int) ((choice - mutation) * totalScore / (1 - mutation));
                j = -1;
                while (select >= 0 && j < danceList.size() - 1) { //TODO
                    j++;
                    select -= danceList.get(j).getScore();
                }
                fromDance = danceList.get(j);
                choice = r.nextFloat();
                select = (int) (((float)(dance.getCounts() * fromDance.getCounts())) / len);
                j = -1;
                while (select >= 0 && j < fromDance.getMoves().size() - 1) { //TODO
                    j++;
                    select -= fromDance.getMove(j).getCounts();
                }
                if (choice < 0.25 && j != 0) {
                    if (dance.getCounts() + fromDance.getMove(j - 1).getCounts() <= len) {
                        dance.addMove(fromDance.getMove(j - 1));
                    }
                } else if (choice < 0.75) {
                    if (dance.getCounts() + fromDance.getMove(j).getCounts() <= len) {
                        dance.addMove(fromDance.getMove(j));
                    }
                } else if (j != fromDance.getMoves().size() - 1) {
                    if (dance.getCounts() + fromDance.getMove(j + 1).getCounts() <= len) {
                        dance.addMove(fromDance.getMove(j + 1));
                    }
                } else {
                    if (dance.getCounts() + fromDance.getMove(j - 1).getCounts() <= len) {
                        dance.addMove(fromDance.getMove(j - 1));
                    }
                }
            }
            choice = r.nextFloat();
        }
        danceList.add(dance);
        return danceList;
    }

    static int getInt(String question, int min, int max) {
        Scanner s;
        int res = -1;
        boolean filterMax = false, filterMin = false;
        while (res < min || res > max) {
            s = new Scanner(System.in);
            if (filterMax) {
                System.out.println("Invalid entry: You must choose " + max + " or less.");
            } else if (filterMin) {
                System.out.println("Invalid entry: You must choose " + min + " or more.");
            }
            filterMax = false;
            filterMin = false;
            System.out.print(question);
            try {
                res = s.nextInt();
                filterMax = res > max;
                filterMin = res < min;
            } catch (Exception e) {
                System.out.println("Invalid entry: Your must choose an integer.");
            }
        }
        return res;
    }

    static Dance getDance(Style style, int level) {
        int len = -1, i = 0, j = 0;
        Dance dance = new Dance(style);
        List<Move> moves = style.getMoves(level);
        len = getInt("How many counts is your demo " + style + "? (8-128) ", 8, 128);
        while (len % 4 != 0) {
            System.out.println("Invalid entry: You must choose a number divisible by 4.");
            len = getInt("How many counts would your like your " + style + " to be? (8-128) ", 8, 128);
        }
        for (i = 0; i < moves.size(); i++) {
            System.out.println((i + 1) + ". " + moves.get(i));
        }
        while (len > dance.getCounts()) {
            System.out.println(len - dance.getCounts() + " counts left.");
            i = getInt("Which move would you like to add to the " + style + " next? (1-" + moves.size() + ") " , 1, moves.size());
            if (dance.getCounts() + moves.get(i - 1).getCounts() > len) {
                System.out.println("Not enough counts left in the dance to add a " + moves.get(i - 1) + ".");
            } else {
                System.out.println(moves.get(i - 1));
                dance.addMove(moves.get(i - 1));
            }
        }
        System.out.println(dance);
        return dance;
    }

    static Style getRumba() {
        Style rumba = new Style("rumba");
        RumbaMove basic = new RumbaMove(rumba, "Basic", 4, 0, true);
        rumba.setBasic(basic);
        RumbaMove cucarachas = new RumbaMove(rumba, "Cucarachas", 4, 0, true);
        RumbaMove newYorker = new RumbaMove(rumba, "New Yorker", 4, 0, true);
        RumbaMove travNewYorker = new RumbaMove(rumba, "Traveling New Yorker", 4, 0, false);
        RumbaMove underarmTurn = new RumbaMove(rumba, "Underarm Turn", 4, 0, true);
        RumbaMove switchTurn = new RumbaMove(rumba, "Switch Turn", 4, 0, true);
        RumbaMove spotTurn = new RumbaMove(rumba, "Spot Turn", 4, 0, true);
        RumbaMove shoulderToShoulder = new RumbaMove(rumba, "Shoulder to shoulder", 4, 0, true);
        RumbaMove handToHand = new RumbaMove(rumba, "Hand to hand", 4, 0, true);
        RumbaMove travHandToHand = new RumbaMove(rumba, "Traveling hand to hand", 4, 0, false);
        RumbaMove progressiveWalks = new RumbaMove(rumba, "Progressive walks", 4, 0, true);
        RumbaMove sideSteps = new RumbaMove(rumba, "Side steps", 4, 0, true);
        RumbaMove cubanRocks = new RumbaMove(rumba, "Cuban rocks", 4, 0, true);
        rumba.addMove(basic);
        rumba.addMove(cucarachas);
        rumba.addMove(newYorker);
        rumba.addMove(travNewYorker);
        rumba.addMove(underarmTurn);
        rumba.addMove(switchTurn);
        rumba.addMove(spotTurn);
        rumba.addMove(shoulderToShoulder);
        rumba.addMove(handToHand);
        rumba.addMove(travHandToHand);
        rumba.addMove(progressiveWalks);
        rumba.addMove(sideSteps);
        rumba.addMove(cubanRocks);
        RumbaMove fan = new RumbaMove(rumba, "Fan", 4, 1, true);
        RumbaMove alemana = new RumbaMove(rumba, "Alemana", 8, 1, true);
        RumbaMove hockeyStick = new RumbaMove(rumba, "Hockey Stick", 8, 1, true);
        RumbaMove naturalTop = new RumbaMove(rumba, "Natural Top", 12, 1, true);
        RumbaMove naturalOpeningOutMovement = new RumbaMove(rumba, "Natural Opening Out Movement", 4, 1, true);
        RumbaMove closedHipTwist = new RumbaMove(rumba, "Closed Hip Twist", 8, 1, false);
        rumba.addMove(fan);
        rumba.addMove(alemana);
        rumba.addMove(hockeyStick);
        rumba.addMove(naturalTop);
        rumba.addMove(naturalOpeningOutMovement);
        rumba.addMove(closedHipTwist);
        //RumbaMove  = new RumbaMove(rumba, "", , , true); {Basic, Hand to hand, New Yorker, Natural Opening Out Movement, Natural Opening Out Movement, Natural Opening Out Movement, Cucarachas, Alemana, Switch Turn, Basic, Hand to hand}
        return rumba;
    }

    static Style getChaCha() {
        Style chaCha = new Style("cha cha");
        ChaChaMove basic = new ChaChaMove(chaCha, "Basic", 4, 0, true);
        chaCha.setBasic(basic);
        ChaChaMove newYorker = new ChaChaMove(chaCha, "New Yorker", 4, 0, true);
        ChaChaMove travNewYorker = new ChaChaMove(chaCha, "Traveling New Yorker", 4, 0, false);
        ChaChaMove underarmTurn = new ChaChaMove(chaCha, "Underarm Turn", 4, 0, true);
        ChaChaMove switchTurn = new ChaChaMove(chaCha, "Switch Turn", 4, 0, true);
        ChaChaMove spotTurn = new ChaChaMove(chaCha, "Spot Turn", 4, 0, true);
        ChaChaMove shoulderToShoulder = new ChaChaMove(chaCha, "Shoulder to shoulder", 4, 0, true);
        ChaChaMove handToHand = new ChaChaMove(chaCha, "Hand to hand", 4, 0, true);
        ChaChaMove travHandToHand = new ChaChaMove(chaCha, "Traveling hand to hand", 4, 0, false);
        ChaChaMove threeChaChaChas = new ChaChaMove(chaCha, "Three cha cha chas", 4, 0, true);
        ChaChaMove sideSteps = new ChaChaMove(chaCha, "Side steps", 4, 0, true);
        ChaChaMove thereAndBackAgainAHobitsTale = new ChaChaMove(chaCha, "There and back", 4, 0, true);
        ChaChaMove timeSteps = new ChaChaMove(chaCha, "Time steps", 4, 0, true);
        chaCha.addMove(basic);
        chaCha.addMove(newYorker);
        chaCha.addMove(travNewYorker);
        chaCha.addMove(underarmTurn);
        chaCha.addMove(switchTurn);
        chaCha.addMove(spotTurn);
        chaCha.addMove(shoulderToShoulder);
        chaCha.addMove(handToHand);
        chaCha.addMove(travHandToHand);
        chaCha.addMove(threeChaChaChas);
        chaCha.addMove(sideSteps);
        chaCha.addMove(thereAndBackAgainAHobitsTale);
        chaCha.addMove(timeSteps);
        ChaChaMove fan = new ChaChaMove(chaCha, "Fan", 4, 1, true);
        ChaChaMove alemana = new ChaChaMove(chaCha, "Alemana", 8, 1, true);
        ChaChaMove hockeyStick = new ChaChaMove(chaCha, "Hockey Stick", 8, 1, true);
        ChaChaMove naturalTop = new ChaChaMove(chaCha, "Natural Top", 12, 1, true);
        ChaChaMove naturalOpeningOutMovement = new ChaChaMove(chaCha, "Natural Opening Out Movement", 4, 1, true);
        ChaChaMove closedHipTwist = new ChaChaMove(chaCha, "Closed Hip Twist", 8, 1, false);
        chaCha.addMove(fan);
        chaCha.addMove(alemana);
        chaCha.addMove(hockeyStick);
        chaCha.addMove(naturalTop);
        chaCha.addMove(naturalOpeningOutMovement);
        chaCha.addMove(closedHipTwist);
        return chaCha;
    }
}
// Improvements: Make generalizable among ballroom styles
//               Make directed graph of moves for each ballroom style instead of just a list of moves.
//               Position changes and make sure combo doesn't change location too much.