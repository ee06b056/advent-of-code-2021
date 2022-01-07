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

        
        solution1(scannerMap);
        

        
        
    }

    public static void solution1 (Map<Integer, List<int[]>> scannerMap) {
        List<int[][]> matrixs = getAllRotationMatrix();
        List<int[]> coor0 = scannerMap.get(1);
        List<int[]> coor1 = scannerMap.get(4);
        Dif dif01 = findDif(coor0, coor1, matrixs);
        int[] v = dif01.v;
        for (int vv : v) {
            System.out.print(vv + ",");
        }
        System.out.println();
        int[][] rm = dif01.rm;
        for (int[] r : rm) {
            for (int c : r) {
                System.out.print(c + ",");
            }
            System.out.println();
        }
        List<int[]> newCoor1 = multiply(coor1, rm);
        // for (int[] c : newCoor1) {
        //     for (int nc : c) {
        //         System.out.print(nc + ",");
        //     }
        //     System.out.println();
        // }
        
    }

    public static Dif findDif (List<int[]> l1, List<int[]> l2, List<int[][]> rotateMatrixs) {
        for (int[][] rotateMatrix : rotateMatrixs) {
            List<int[]> newL2 = multiply(l2, rotateMatrix);
            int[] v = isOverlapping(l1, newL2);
            if (v != null) return new Dif(rotateMatrix, v);
        }
        return null;
    }

    public static int[] isOverlapping (List<int[]> l1, List<int[]> l2) {
        Set<String> l1Set = new HashSet<>();
        for (int[] coor : l1) {
            l1Set.add(getString(coor));
        }
        for (int i = 0; i < l1.size(); i++) {
            for (int j = 0; j < l2.size(); j++) {
                int count = 0;
                int[] ic = l1.get(i), jc = l2.get(j);
                int[] v = minus(ic, jc);
                for (int k = 0; k < l2.size(); k++) {
                    int[] nic = add(l2.get(k), v);
                    if (l1Set.contains(getString(nic))) {
                        count++;
                    }
                }
                
                if (count >= 12) return v;

            }
        }
        return null;
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

    public static int[][] multi (int[][] a, int[][] b) {
        int[][] c = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        } 
        return c;
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
        int[] degrees = new int[]{0, 90, 180, 270};
        for (int zdegree : degrees) {
            for (int xdegree : degrees) {
                matrixs.add(multi(multi(initM, rotateAxisZ(zdegree)), rotateAxisX(xdegree)));
            }
        }
        for (int degree : degrees) {
            matrixs.add(multi(multi(initM, rotateAxisY(90)), rotateAxisX(degree)));
        }
        for (int degree : degrees) {
            matrixs.add(multi(multi(initM, rotateAxisY(270)), rotateAxisX(degree)));
        }
        return matrixs;
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

class Dif {
    int[][] rm;
    int[] v;
    public Dif (int[][] rm, int[] v) {
        this.rm = rm;
        this.v = v;
    }
}
