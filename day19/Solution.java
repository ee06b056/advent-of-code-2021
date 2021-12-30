package day19;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {
    public static void main(String[] args) throws Exception {
        List<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("day19/input.data"))) {
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pattern pattern1 = Pattern.compile("--- scanner (\\d+) ---");
        Pattern pattern2 = Pattern.compile("(-?\\d+),(-?\\d+),(-?\\d+)");

        Map<Integer, List<int[]>> scannerMap = new HashMap<>();
        List<int[]> coords = null;
        int scannerID = -1;
        for (String l : list) {
            Matcher m1 = pattern1.matcher(l);
            if (m1.find()) {
                scannerID = Integer.valueOf(m1.group(1));
                coords = new ArrayList<>();
            }
            Matcher m2 = pattern2.matcher(l);
            if (m2.find()) {
                int[] coord = new int[3];
                for (int i = 0; i < 3; i++) {
                    // System.out.println(m2.group(i + 1));
                    coord[i] = Integer.valueOf(m2.group(i + 1));
                }
                coords.add(coord);
            }
            if (l.length() == 0) {
                if (scannerID < 0) throw new Exception();
                scannerMap.put(scannerID, coords);
            }
        }

        // System.out.println(scannerMap.get(38).get(1)[2]);

        // List<int[]> l1 = scannerMap.get(0);
        // List<int[]> l2 = scannerMap.get(1);
        // System.out.println(isOverlapping(l1, l2));
        // System.out.println(l1.get(0)[0]);
        // System.out.println(l2.size());


        // for (int i = 0; i < 38; i++) {
        //     for (int j = i + 1; j < 39; j++) {
        //         System.out.println(isOverlapping(scannerMap.get(i), scannerMap.get(j)));
        //     }
        // }
        rotateX(new int[]{1,2,3}, 90);
    }

    public static void solution1 (Map<Integer, List<int[]>> scannerMap) {

    }

    public static boolean isOverlapping (List<int[]> l1, List<int[]> l2) {
        Set<String> l2Set = new HashSet<>();
        for (int[] coor : l2) {
            l2Set.add(getString(coor));
        }
        // System.out.println("size: " + l2Set.size());
        for (int i = 0; i < l1.size(); i++) {
            for (int j = 0; j < l2.size(); j++) {
                int count = 0;
                int[] ic = l1.get(i), jc = l2.get(j);
                int[] v = minus(l2.get(j), l1.get(i));
                for (int k = 0; k < l1.size(); k++) {
                    int[] nic = add(l1.get(k), v);
                    if (l2Set.contains(getString(nic))) {
                        count++;
                    }
                }
                if (count >= 12) return true;
                // System.out.println("count: " + count);

            }
        }
        return false;
    }

    public static int[] minus (int[] a, int[] b) {
        int[] res = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i] - b[i];
        }
        return res;
    }

    public static int[] add (int[] a, int[] b) {
        int[] res = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i] + b[i];
        }
        return res;
    }

    public static String getString (int[] a) {
        StringBuilder sb = new StringBuilder();
        sb.append(a[0]);
        sb.append("x");
        sb.append(a[1]);
        sb.append("y");
        sb.append(a[2]);
        sb.append("z");
        return sb.toString();
    }

    public static int[] rotateX (int[] a, int degree) {
        int[] res = new int[a.length];
        int c = -sin(90);
        System.out.println(c);
        return res;
    }

    public static int sin (int degree) {
        switch (degree) {
            case 90:
                return 1;
                
        
            default:
                break;
        }
        return 0;
    }


}

class Node {

}
