import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class LifeGame {
    private ArrayList<ArrayList<mNode>> data = new ArrayList<>();
    private int lifeCircles;

    LifeGame(File fp) {
        try (FileInputStream fs = new FileInputStream(fp)) {
            String in = new Scanner(fs).next();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    LifeGame(String s) {
        this.init(s);
    }

    public static void main(String[] args) {
        String test = " \t \t \nx\tx\tx\n \t \t ";
        LifeGame testE = new LifeGame(test);
        while (true) {
            testE.shift();
            System.out.println(testE);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private void init(String s) {
        String[] line = s.split("\n");
        int n = -1;
        ArrayList<mNode> temp;
        for (String i : line) {
            data.add(new ArrayList<>());
            temp = data.get(++n);
            for (char j : i.toCharArray()) {
                temp.add(new mNode(j));
            }
        }
        lifeCircles = 0;
    }

    void shift() {
        ArrayList<ArrayList<mNode>> dataNew = new ArrayList<ArrayList<mNode>>();
        for (int i = 0; i < data.size() + 2; i++) {
            dataNew.add(new ArrayList<>());
            for (int j = 0; j < data.get(0).size() + 2; j++) {
                if (this.isAlive(i, j)) {
                    dataNew.get(i).add(new mNode('x'));
                } else {
                    dataNew.get(i).add(new mNode(' '));
                }
            }
        }
        data = dataNew;
        strip();
        strip();
        lifeCircles++;
    }

    private void strip() {
        boolean flag = false;
        for (int i = 0; i < data.size(); ) {
            flag = true;
            for (int j = 0; j < data.get(i).size(); j++) {
                if (data.get(i).get(j).isAlive()) {
                    flag = false;
                    i += data.size() - 1;
                    break;
                }
            }
            if (flag) {
                data.remove(i);
            }
        }
        for (int i = 0; i < data.get(0).size(); ) {
            flag = true;
            for (ArrayList<mNode> j : data) {
                if (j.get(i).isAlive()) {
                    flag = false;
                    i += data.get(0).size() - 1;
                    break;
                }
            }
            if (flag) {
                for (ArrayList<mNode> tmp : (ArrayList<ArrayList<mNode>>) data.clone()) {
                    tmp.remove(i);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (ArrayList<mNode> i : data) {
            for (mNode j : i) {
                s.append(j.toString());
            }
            s.append("\n");
        }
        return s.toString();
    }


    //    public boolean isAliveNow(int x, int y){
//        try {
//            return data.get(x).get(y).isAlive();
//        }
//        catch(Exception e){
//            return false;
//        }
//    }
    private int isAliveNow(int x, int y) {
        x--;
        y--;
        try {
            if (data.get(x).get(y).isAlive()) {
                return 1;
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private boolean isAlive(int x, int y) {
        int count = this.isAliveNow(x - 1, y - 1) +
                this.isAliveNow(x - 1, y + 1) +
                this.isAliveNow(x + 1, y - 1) +
                this.isAliveNow(x, y + 1) +
                this.isAliveNow(x - 1, y) +
                this.isAliveNow(x, y - 1) +
                this.isAliveNow(x + 1, y + 1) +
                this.isAliveNow(x + 1, y);
        if (isAliveNow(x, y) == 0 && count == 3) {
            return true;
        } else return isAliveNow(x, y) == 1 && (count == 2 || count == 3);
    }


    int getLifeCircles() {
        return lifeCircles;
    }

    class mNode {
        Boolean alive;

        mNode(char s) {
            if (s == ' ') {
                alive = false;
            } else if (s == 'x') {
                alive = true;
            }
        }

        boolean isAlive() {
            return alive;
        }

        @Override
        public String toString() {
            return isAlive() ? "x" : " ";
        }
    }
}