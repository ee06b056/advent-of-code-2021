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

        
        List<int[]> scanner0coor = scannerMap.get(0);
        List<int[]> scanner0coorR = rotateX(scanner0coor, 180);
        for (int[] coor : scanner0coorR) {
            for (int c : coor) {
                System.out.print(c + ",");
            }
            System.out.println();
        }
    }

    public static void solution1 (Map<Integer, List<int[]>> scannerMap) {
        int[][] matrix = new int[3][3];
        for (int[] m : matrix) {
            
        }
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

    public static int[] multiplySingle (int[] a, int[][] transforMatrix) {
        int[] res = new int[a.length];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res[i] += a[j] * transforMatrix[j][i];
            }
        }
        return res;
    }

    public static List<int[]> multiply (List<int[]> coors, int[][] transforMatrix) {
        List<int[]> res = new ArrayList<>();
        for (int[] coor : coors) {
            int[] resCoor = multiplySingle(coor, transforMatrix);
            res.add(resCoor);
        }
        return res;
    }

    public static List<int[]> rotateX (List<int[]> a, int degree) {
        int[][] transforM = new int[][]{{1,            0,           0},
                                        {0,  cos(degree), sin(degree)},
                                        {0, -sin(degree), cos(degree)}};
        
        return multiply(a, transforM);
    }

    public static int[][] rotateAxisX (int degree) {
        return new int[][]{{1,            0,           0},
                           {0,  cos(degree), sin(degree)},
                           {0, -sin(degree), cos(degree)}};
    }

    public static int[][] rotateAxisY (int degree) {
        return new int[][]{{ cos(degree), 0, sin(degree)},
                           {           0, 1,           0},
                           {-sin(degree), 0, cos(degree)}};
    }

    public static int[][] rotateAxisZ (int degree) {
        return new int[][]{{ cos(degree), sin(degree), 0},
                           {-sin(degree), cos(degree), 0},
                           {           0,           0, 1}};
    }

    public static List<int[][]> getAllRotationMatrix () {
        List<int[][]> matrixs = new ArrayList<>();
        int[][] initM = new int[][]{{1, 0, 0},
                                    {0, 1, 0},
                                    {0, 0, 1}};
        return null;
    }

    public static int sin (int degree) {
        switch (degree) {
            case 0:
                return 0;
            case 90:
                return 1;
            case 180:
                return 0;
            case 270:
                return -1;
        }
        return 2;
    }

    public static int cos (int degreee) {
        switch (degreee) {
            case 0:
                return 1;
            case 90:
                return 0;
            case 180:
                return -1;
            case 270:
                return 0;
        }
        return 2;
    }


}

class Node {

}
