package day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

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
        
        solution1(inputs);
        solution2(inputs);
    }

    public static void solution1 (List<String> inputs) {
         int ans = 0;
         for (String input : inputs) {
             String[] segs = input.split("\\s\\|\\s");
            
             String[] displays = segs[1].trim().split("\\s+");
             for (String display : displays) {
                 int l = display.length();
                 if (l == 2 || l == 4 || l == 3 || l == 7) {
                     
                     ans++;
                 }
             }
         }
         System.out.println(ans);
    }

    public static void solution2 (List<String> inputs) {
        Map<String, Integer> m = new HashMap<>();
        m.put("abcefg", 0);
        m.put("cf", 1);
        m.put("acdeg", 2);
        m.put("acdfg", 3);
        m.put("bcdf", 4);
        m.put("abdfg", 5);
        m.put("abdefg", 6);
        m.put("acf", 7);
        m.put("abcdefg", 8);
        m.put("abcdfg", 9);
        long sum = 0L;
        for (String input: inputs) {
            String[] temp = input.split("\\s\\|\\s");
            String[] digits = temp[0].trim().split("\\s+");
            String[] output = temp[1].trim().split("\\s+");
            Map<Character, Character> decoder = decode(digits);
            sum += decodeOutput(output, decoder, m);
        }
        System.out.println(sum);
    }

    public static int decodeOutput (String[] digits, Map<Character, Character> decoder, Map<String, Integer> m) {
        int num = 0;
        for (String digit : digits) {
            char[] digitArr = digit.toCharArray();
            for (int i = 0; i < digitArr.length; i++) {
                char c = digitArr[i];
                digitArr[i] = decoder.get(c);
            }
            Arrays.sort(digitArr);
            String s = new String(digitArr);
            num = num * 10 + m.get(s);
        }
        return num;
    }

    public static Map<Character, Character> decode (String[] digits) {
        Map<Integer, List<Set<Character>>> m1 = new HashMap<>();
        Map<Character, Integer> m2 = new HashMap<>();
        Map<Character, Character> connection = new HashMap<>();
        for (String d : digits) {
            List<Set<Character>> l = m1.getOrDefault(d.length(), new ArrayList<>());
            Set<Character> s1 = d.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
            l.add(s1);
            m1.put(d.length(), l);
            for (Character c : d.toCharArray()) {
                m2.put(c, m2.getOrDefault(c, 0) + 1);
            }
        }


        // find A connection
        Set<Character> tempSet = new HashSet<>(m1.get(3).get(0));
        tempSet.removeAll(m1.get(2).get(0));
        connection.put(tempSet.iterator().next(), 'a');
        
        // find C F connection
        Iterator<Character> i = m1.get(2).get(0).iterator();
        char c1 = i.next(), c2 = i.next();
        if (m2.get(c1) == 8) {
            connection.put(c1, 'c');
            connection.put(c2, 'f');
        }  else {
            connection.put(c2, 'c');
            connection.put(c1, 'f');
        }

        // find B D connection
        tempSet = new HashSet<>(m1.get(4).get(0));
        tempSet.removeAll(m1.get(2).get(0));
        i = tempSet.iterator();
        c1 = i.next();
        c2 = i.next();
        if (m2.get(c1) == 6) {
            connection.put(c1, 'b');
            connection.put(c2, 'd');
        } else {
            connection.put(c2, 'b');
            connection.put(c1, 'd');
        }

        // find E G connection
        tempSet = new HashSet<>(m1.get(7).get(0));
        tempSet.removeAll(connection.keySet());
        i = tempSet.iterator();
        c1 = i.next();
        c2 = i.next();
        if (m2.get(c1) == 4) {
            connection.put(c1, 'e');
            connection.put(c2, 'g');
        } else {
            connection.put(c2, 'e');
            connection.put(c1, 'g');
        }
        return connection;
    }

    
}
