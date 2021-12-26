package day15;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        int[][] map = new int[100][100];
        try {
            int row = 0;
            File f = new File("day15/input.data");
            f.exists();
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                char[] lineCharArr = line.toCharArray();
                for (int i = 0; i < 100; i++) {
                    map[row][i] = lineCharArr[i] - 48;
                }
                row++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        solution1_dp(map);
        solution2_a_star(map);
    }

    public static void solution1 (int[][] map) {
        Set<Integer> visited = new HashSet<>();
        int res = dfs(map, visited, 0, 0, 0);
        System.out.println(res);
    }

    public static int dfs (int[][] map, Set<Integer> visited, int r, int c, int tempSum) {
        int[][] directions = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        tempSum += map[r][c];
        if (r == 99 && c == 99) {
            return tempSum;
        }
        int index = r + c * 100;
        visited.add(index);
        int tempMinSum = Integer.MAX_VALUE;
        for (int[] d : directions) {
            int nextR = r + d[0], nextC = c + d[1];
            int nextIndex = nextR + nextC * 100;
            if (nextR >= 0 && nextR < 100 && nextC >= 0 && nextC < 100 && !visited.contains(nextIndex)) {
                tempMinSum = Math.min(tempMinSum, dfs(map, visited, nextR, nextC, tempSum));
            }
        }
        visited.remove(index);
        return tempMinSum;
    }

    public static void solution1_dp (int[][] map) {
        int[] dp = new int[100];
        for (int r = 0; r < 100; r++) {
            for (int c = 0; c < 100; c++) {
                if (r == 0 && c == 0) {
                    dp[c] = 0;
                } else if (r == 0) {
                    dp[c] = dp[c - 1] + map[r][c];
                } else if (c == 0) {
                    dp[c] = dp[c] + map[r][c];
                } else {
                    int leftSum = dp[c - 1] + map[r][c];
                    int upSum = dp[c] + map[r][c];
                    dp[c] = Math.min(leftSum, upSum);
                }
            }
        }
        System.out.println(dp[99]);
    }

    public static int getV (int[][] origMap, int r, int c) {
        int newR = r % 100, newC = c % 100;
        int newV = (origMap[newR][newC] - 1 + r / 100 + c / 100) % 9 + 1;
        return newV;
    }

    public static void solution2_a_star (int[][] map) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.totalDis - o2.totalDis;
            }
        });

        int[][] directions = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        pq.offer(new Node(0, 0, 0));
        Set<Integer> visited = new HashSet<>();
        int minCost = Integer.MAX_VALUE;
        while (pq.size() > 0) {
            Node currentNode = pq.poll();
            if (currentNode.r == 499 && currentNode.c == 499) {
                minCost = Math.min(minCost, currentNode.totalDis);
                break;
            } else if (visited.contains(currentNode.index)) {
                continue;
            } else {
                for (int[] direction : directions) {
                    int nextR = currentNode.r + direction[0], nextC = currentNode.c + direction[1];
                    int nextIndex = nextR + nextC * 500;
                    if (nextR >= 0 && nextR < 500 && nextC >= 0 && nextC < 500 && !visited.contains(nextIndex)) {
                        int nextCost = currentNode.distanceFromStart + getV(map, nextR, nextC);
                        pq.offer(new Node(nextR, nextC, nextCost));
                    }
                }
            }
            visited.add(currentNode.index);
        }
        System.out.println("minCost: " + minCost);
    }
}

class Node {
    int r, c, index;
    int distanceFromStart, manhattanDisToEnd, totalDis;
    public Node (int r, int c, int distanceFromStart) {
        this.r = r;
        this.c = c;
        this.index = r + c * 500;
        this.distanceFromStart = distanceFromStart;
        this.manhattanDisToEnd = 499 * 2 - r - c;
        this.totalDis = this.distanceFromStart + this.manhattanDisToEnd;
    }
}
