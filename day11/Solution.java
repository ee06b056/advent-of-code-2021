package day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        int[][] os = new int[10][10];
        try {
            File f = new File("day11/input.data");
            f.exists();
            Scanner scanner = new Scanner(f);
            int i = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                char[] ca = line.toCharArray();
                for (int j = 0; j < 10; j++) {
                    os[i][j] = ca[j] - 48;
                }
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // solution1(os);

        solution2(os);
    }

    public static void solution1 (int[][] os) {
        int ans = 0;
        Set<Integer> flashSet = new HashSet<>();
        Deque<Integer> toFlash = new LinkedList<>();
        for (int r = 0; r < 100; r++) {
            flashSet.clear();
            toFlash.clear();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    os[i][j]++;
                    if (os[i][j] > 9) {
                        int id = i * 10 + j;
                        toFlash.addLast(id);
                        flashSet.add(id);
                    }
                }
            }
            while (toFlash.size() > 0) {
                int id = toFlash.pollFirst();
                flash(os, id, flashSet, toFlash);
            }
            ans += flashSet.size();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (os[i][j] > 9) {
                        os[i][j] = 0;
                    }
                }
            }
        }
        System.out.println("ans: " + ans);
    }

    public static void flash (int[][] os, int id, Set<Integer> flashSet, Deque<Integer> toFlash) {
        int[] adjacent = {-1, 0, 1};
        int i = id / 10, j = id % 10;
        for (int dI : adjacent) {
            for (int dJ : adjacent) {
                int nextI = i + dI, nextJ = j + dJ;
                if (nextI >= 0 && nextI < 10 && nextJ >= 0 && nextJ < 10) {
                    os[nextI][nextJ]++;
                    int nextId = nextI * 10 + nextJ;
                    if (os[nextI][nextJ] > 9 && !flashSet.contains(nextId)) {
                        toFlash.addLast(nextId);
                        flashSet.add(nextId);
                    }
                }
            }
        }
    }

    public static void solution2 (int[][] os) {
        int ans = 0;
        Set<Integer> flashSet = new HashSet<>();
        Deque<Integer> toFlash = new LinkedList<>();
        int round = 0;
        while (ans != 100) {
            round++;
            ans = 0;
            flashSet.clear();
            toFlash.clear();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    os[i][j]++;
                    if (os[i][j] > 9) {
                        int id = i * 10 + j;
                        toFlash.addLast(id);
                        flashSet.add(id);
                    }
                }
            }
            while (toFlash.size() > 0) {
                int id = toFlash.pollFirst();
                flash(os, id, flashSet, toFlash);
            }
            ans = flashSet.size();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (os[i][j] > 9) {
                        os[i][j] = 0;
                    }
                }
            }
        }
        System.out.println("round: " + round);
    }
}
