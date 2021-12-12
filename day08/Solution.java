package day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<String>();
        try {
            File f = new File("day08/input.data");
            f.exists();
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                inputs.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(inputs.size());
        solution1(inputs);
    }

    public static void solution1 (List<String> inputs) {
         int ans = 0;
         for (String input : inputs) {
             String[] segs = input.split("\\s\\|\\s");
             System.out.println("seg1: " + segs[1]);
             String[] displays = segs[1].trim().split("\\s+");
             for (String display : displays) {
                 int l = display.length();
                 if (l == 2 || l == 4 || l == 3 || l == 7) {
                     System.out.println(display);
                     ans++;
                 }
             }
         }
         System.out.println(ans);
    }

    public static void solution2 (List<String> inputs) {

    }

    public static Map<Character, Character> decode (String[] digits) {
        Map<Integer, List<String>> m1 = new HashMap<>();
        Map<Character, Character> connection = new HashMap<>();
        for (String d : digits) {
            List<String> list = m1.getOrDefault(d.length(), new ArrayList<>());
            list.add(d);
            
        }
        String[] numbers = new String[10];
        numbers[1] = m1.get(2).get(0);
        numbers[4] = m1.get(4).get(0);
        numbers[7] = m1.get(3).get(0);
        numbers[8] = m1.get(7).get(0);

        // find a connection
        
        return null;
    }
}
