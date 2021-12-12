package day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<String>();
        try {
            File f = new File("day05/input.data");
            f.exists();
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                inputs.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<int[]> lines = new ArrayList<>();
        for (String s : inputs) {
            String[] temp = s.split("\\s->\\s|,");
            int[] line = new int[temp.length];
            for (int i = 0; i < temp.length; i++) {
                line[i] = Integer.valueOf(temp[i]);
            }
            lines.add(line);
        }
        solution1(lines);
        solution2(lines);
    }

    public static void solution1 (List<int[]> lines) {
        int[][] map = new int[1000][1000];
        int count = 0;
        for (int[] line : lines) {
            if (line[0] == line[2] || line[1] == line[3]) {
                for (int i = Math.min(line[0], line[2]); i <= Math.max(line[0], line[2]); i++) {
                    for (int j = Math.min(line[1], line[3]); j <= Math.max(line[1], line[3]); j++) {
                        map[i][j]++;
                        if (map[i][j] == 2) count++;
                    }
                }
            }
        }
        System.out.println("count: " + count);
    }

    public static void solution2 (List<int[]> lines) {
        int[][] map = new int[1000][1000];
        int count = 0;
        for (int[] line : lines) {
            if (line[0] == line[2] || line[1] == line[3]) {
                for (int i = Math.min(line[0], line[2]); i <= Math.max(line[0], line[2]); i++) {
                    for (int j = Math.min(line[1], line[3]); j <= Math.max(line[1], line[3]); j++) {
                        map[i][j]++;
                        if (map[i][j] == 2) count++;
                    }
                }
            } else {
                int[] step = new int[2];
                step[0] = line[0] < line[2] ? 1 : -1;
                step[1] = line[1] < line[3] ? 1 : -1;
                for (int i = 0; i <= Math.abs(line[2] - line[0]); i++) {
                    int newI = line[0] + i * step[0];
                    int newJ = line[1] + i * step[1];
                    map[newI][newJ]++;
                    if (map[newI][newJ] == 2) count++;
                }
            }
        }
        System.out.println("count: " + count);
    }
}
