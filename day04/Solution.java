package day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Solution
 */
public class Solution {

    public static void main(String[] args) {
        List<Integer> inputs1 = new ArrayList<>();
        List<Board> inputs2 = new ArrayList<>();
        try {
            File f1 = new File("day04/input1.data");
            f1.exists();
            File f2 = new File("day04/input2.data");
            f2.exists();
            Scanner scanner1 = new Scanner(f1).useDelimiter(Pattern.compile("[\\n\\s,]+"));
            Scanner scanner2 = new Scanner(f2);
            while (scanner1.hasNext()) {
                inputs1.add(scanner1.nextInt());
            }
            scanner1.close();
            while (scanner2.hasNext()) {
                Board board = new Board();
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        board.addNum(scanner2.nextInt(), i, j);
                    }
                }
                inputs2.add(board);
            }
            scanner2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        solution1(inputs1, inputs2);
        solution2(inputs1, inputs2);
    }

    public static void solution1 (List<Integer> numbers, List<Board> boards) {
        for (int n : numbers) {
            for (Board board : boards) {
                if (board.mark(n)) {
                    System.out.println(n * board.unmarkedNumSum());
                    return;
                }
            }
        }
        return;
    }

    public static void solution2 (List<Integer> numbers, List<Board> boards) {
        Board lastWiner = null;
        int lastNum = -1;
        for (int n : numbers) {
            for (Board board : boards) {
                if (!board.used && board.mark(n)) {
                    lastWiner = board;
                    lastNum = n;
                }
            }
        }
        System.out.println(lastNum * lastWiner.unmarkedNumSum());
    }
}

class Board {
    public int[][] nums;
    public boolean[][] marked;
    public Map<Integer, Integer> lookupIndex;
    public boolean used;

    public Board () {
        this.nums = new int[5][5];
        this.marked = new boolean[5][5];
        this.lookupIndex = new HashMap<>();
        this.used = false;
    }

    public void addNum (int num, int i, int j) {
        this.nums[i][j] = num;
        this.lookupIndex.put(num, 5 * i + j);
    }    

    public boolean mark (int num) {
        if (this.lookupIndex.containsKey(num)) {
            int v = this.lookupIndex.get(num);
            int i = v / 5;
            int j = v % 5;
            this.marked[i][j] = true;
            boolean rowMarked = true, columnMarked = true;
            for (int k = 0; k < 5; k++) {
                if (!this.marked[i][k]) {
                    rowMarked = false;
                }
                if (!this.marked[k][j]) {
                    columnMarked = false;
                }
            }
            if (rowMarked || columnMarked) {
                this.used = true;
            }
            return rowMarked || columnMarked;
        }
        return false;
    }

    public int unmarkedNumSum () {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!this.marked[i][j]) {
                    sum += this.nums[i][j];
                }
            }
        }
        return sum;
    }
}