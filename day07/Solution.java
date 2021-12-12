package day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class Solution {
    public static void main(String[] args) {
        List<Integer> inputs = new ArrayList<>();
        try {
            File f = new File("day07/input.data");
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

    public static void solution1 (List<Integer> positions) {
        positions.sort(Comparator.naturalOrder());
        int mid = positions.get(positions.size() / 2);
        int sum = 0;
        for (int p : positions) {
            sum += Math.abs(mid - p);
        }
        System.out.println(sum);
    }

    public static void solution2 (List<Integer> positions) {
        int max = 0;
        Map<Integer, Integer> pMap = new HashMap<>();
        for (int p : positions) {
            pMap.put(p, pMap.getOrDefault(p, 0) + 1);
            max = Math.max(max, p);
        }
        int[] fuelLookup = new int[max + 1];
        fuelLookup[1] = 1;
        for (int i = 2; i < fuelLookup.length; i++) {
            fuelLookup[i] = i + fuelLookup[i - 1];
        }
        long ans = Long.MAX_VALUE;
        for (int i = 0; i <= max; i++) {
            long sum = 0L;
            for (Entry<Integer, Integer> e : pMap.entrySet()) {
                int k = e.getKey(), v = e.getValue();
                sum += v * fuelLookup[Math.abs(i - k)];
            }
            ans = Math.min(ans, sum);
        }
        System.out.println(ans);
    }

}
