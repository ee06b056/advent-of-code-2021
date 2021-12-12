package day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        List<Integer> inputs = new ArrayList<>();
        try {
            File f = new File("day06/input.data");
            f.exists();
            Scanner scanner = new Scanner(f).useDelimiter(",");
            while (scanner.hasNext()) {
                inputs.add(scanner.nextInt());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        solution1(inputs);
        solution2(inputs);

    }

    public static void solution1 (List<Integer> fish) {
        Map<Integer, Integer> fishMap = new HashMap<>();
        for (int f : fish) {
            fishMap.put(f, fishMap.getOrDefault(f, 0) + 1);
        }
        for (int i = 0; i < 80; i++) {
            int preCount = 0, tempCount = 0;
            for (int j = 8; j >= 0; j--) {
                tempCount = fishMap.getOrDefault(j, 0);
                fishMap.put(j, preCount);
                preCount = tempCount;
            }
            fishMap.put(6, fishMap.get(6) + preCount);
            fishMap.put(8, preCount);
        }
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += fishMap.getOrDefault(i, 0);
        }
        System.out.println(sum);
    }
    
    public static void solution2 (List<Integer> fish) {
        Map<Integer, Long> fishMap = new HashMap<>();
        for (int f : fish) {
            fishMap.put(f, fishMap.getOrDefault(f, 0L) + 1L);
        }
        for (int i = 0; i < 256; i++) {
            long preCount = 0L, tempCount = 0L;
            for (int j = 8; j >= 0; j--) {
                tempCount = fishMap.getOrDefault(j, 0L);
                fishMap.put(j, preCount);
                preCount = tempCount;
            }
            fishMap.put(6, fishMap.get(6) + preCount);
            fishMap.put(8, preCount);
        }
        long sum = 0L;
        for (int i = 0; i < 9; i++) {
            sum += fishMap.getOrDefault(i, 0L);
        }
        System.out.println(sum);
    }

    
}
