package day09;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        int[][] heights = new int[100][100];
        try {
            File f = new File("day09/input.data");
            f.exists();
            Scanner scanner = new Scanner(f);
            int rowNum = 0;
            while (scanner.hasNext()) {
                String row = scanner.nextLine();
                char[] rowCharArr = row.toCharArray();
                for (int i = 0; i < 100; i++) {
                    heights[rowNum][i] = (int) rowCharArr[i] - 48;
                }
                rowNum++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        solution1(heights);
        solution2(heights);

    }

    public static void solution1 (int[][] heights) {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (isLowP(heights, i, j)) {
                    sum = sum + heights[i][j] + 1;
                }
            }
        }
        System.out.println(sum);
    }

    public static boolean isLowP (int[][] heights, int i, int j) {
        int[][] directrions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : directrions) {
            int nextRowNum = i + direction[0], nextColumnNum = j + direction[1];
            if (nextRowNum >= 0 && nextRowNum < 100 && nextColumnNum >= 0 && nextColumnNum < 100 && heights[nextRowNum][nextColumnNum] <= heights[i][j]) {
                return false;
            }
        }
        return true;
    }

    public static void solution2 (int[][] heights) {
        
        int[][] markedMap = new int[100][100];
        int basionID = 1;
        PriorityQueue<Integer> pq = new PriorityQueue<>(3, Collections.reverseOrder());
        for (int n = 0; n < 100 * 100; n++) {
            int i = n % 100, j = n / 100;
            if (heights[i][j] == 9) {
                markedMap[i][j] = -1;
            }else if (markedMap[i][j] == 0) {
                int count = markBasion(heights, markedMap, i, j, basionID);
                pq.add(count);
                basionID++;
            }
        }
        int a = pq.poll(), b = pq.poll(), c = pq.poll();
        System.out.println(a * b * c);
    }

    public static int markBasion (int[][] heights, int[][] markedMap, int i, int j, int basionID) {
        int count = 0;
        Deque<int[]> adjacentPs = new LinkedList<>();
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        adjacentPs.addLast(new int[]{i, j});
        markedMap[i][j] = basionID;
        while (adjacentPs.size() > 0) {
            int[] currentCor = adjacentPs.pollFirst();
            if (heights[currentCor[0]][currentCor[1]] != 9) count++;
            for (int[] d : directions) {
                int nextI = currentCor[0] + d[0], nextJ = currentCor[1] + d[1];
                if (nextI >= 0 && nextI < 100 && nextJ >= 0 && nextJ < 100 && markedMap[nextI][nextJ] == 0) {
                    if (heights[nextI][nextJ] != 9) {
                        adjacentPs.addLast(new int[]{nextI, nextJ});
                        markedMap[nextI][nextJ] = basionID;
                    } else {
                        markedMap[nextI][nextJ] = -1;
                    }
                }
            }
        }
        return count;
    }
}
