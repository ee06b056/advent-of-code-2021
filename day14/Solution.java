package day14;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        String s = "OHFNNCKCVOBHSSHONBNF";
        List<String> ops = new ArrayList<>();
        try {
            File f = new File("day14/input.data");
            f.exists();
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                ops.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        solution1(s, ops);
        solution2(s, ops);
    }

    public static void solution1 (String s, List<String> ops) {
        Map<String, String> opsM = new HashMap<>();
        for (String op : ops) {
            String[] tempArr = op.split("\\s->\\s");
            opsM.put(tempArr[0], tempArr[1]);
        }
        
        for (int i = 0; i < 10; i++) {
            s = insert(s, opsM);
        }
        int[] f = new int[26];
        for (char c : s.toCharArray()) {
            int i = c - 65;
            f[i]++;
        }
        int maxF = 0, minF = Integer.MAX_VALUE;
        for (int count : f) {
            maxF = Math.max(maxF, count);
            if (count > 0) {
                minF = Math.min(minF, count);
            }
        }
        System.out.println(maxF - minF);
    }

    public static String insert (String s, Map<String, String> opsM) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length() - 1; i++) {
            String lookupS = "" + s.charAt(i) + s.charAt(i + 1);
            sb.append(s.charAt(i));
            if (opsM.containsKey(lookupS)) {
                sb.append(opsM.get(lookupS));
            }
        }
        sb.append(s.charAt(s.length() - 1));
        
        return sb.toString();
    }

    public static void solution2(String s, List<String> ops) {
        long[] fs = new long[26];
        Map<String, String> opsM = new HashMap<>();
        Map<String, long[]> dpM = new HashMap<>();
        for (String op : ops) {
            String[] tempArr = op.split("\\s->\\s");
            opsM.put(tempArr[0], tempArr[1]);
        }
        for (int i = 0; i < s.length() - 1; i++) {
            String pair = "" + s.charAt(i) + s.charAt(i + 1);
            long[] subFs = dfs(pair, opsM, dpM, 0);
            for (int j = 0; j < 26; j++) {
                fs[j] += subFs[j];
            }
        }
        for (int i = 1; i < s.length() - 1; i++) {
            fs[s.charAt(i) - 65]--;
        }
        long maxF = 0L, minF = Long.MAX_VALUE;
        for (long count : fs) {
            maxF = Math.max(maxF, count);
            if (count > 0) {
                minF = Math.min(minF, count);
            }
        }
        System.out.println(maxF - minF);
    }

    public static long[] dfs (String s, Map<String, String> opsM, Map<String, long[]> dpM, int insertCount) {
        long[] fs = new long[26];
        if (insertCount == 40 || !opsM.containsKey(s)) {
            fs[s.charAt(0) - 65]++;
            fs[s.charAt(1) - 65]++;
            return fs;
        }
        String dpS = s + insertCount;
        if (dpM.containsKey(dpS)) {
            return dpM.get(dpS);
        }
        char insertChar = opsM.get(s).charAt(0);
        fs[insertChar - 65]--;
        String leftS = "" + s.charAt(0) + insertChar, rightS = "" + insertChar + s.charAt(1);
        long[] leftFs = dfs(leftS, opsM, dpM, insertCount + 1);
        long[] rightFs = dfs(rightS, opsM, dpM, insertCount + 1);
        for (int i = 0; i < 26; i++) {
            fs[i] = fs[i] + leftFs[i] + rightFs[i];
        }
        dpM.put(dpS, fs);
        return fs;
    }
}
