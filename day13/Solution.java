package day13;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();
        try {
            File f1 = new File("day13/input1.data");
            File f2 = new File("day13/input2.data");
            f1.exists();
            f2.exists();
            Scanner scanner1 = new Scanner(f1);
            Scanner scanner2 = new Scanner(f2);
            while (scanner1.hasNext()) {
                l1.add(scanner1.nextLine());
            }
            scanner1.close();
            while (scanner2.hasNext()) {
                l2.add(scanner2.nextLine());
            }
            scanner2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int[][] map = new int[l1.size()][2];
        int maxX = 0, maxY = 0;
        for (int i = 0; i < map.length; i++) {
            String[] tempArr = l1.get(i).split(",");
            map[i][0] = Integer.valueOf(tempArr[0]);
            map[i][1] = Integer.valueOf(tempArr[1]);
            maxX = Math.max(maxX, map[i][0]);
            maxY = Math.max(maxY, map[i][1]);
        }
        Set<Integer> dotSet = new HashSet<>();
        int lengthX = maxX +1, lengthY = maxY + 1;
        for (int[] d : map) {
            dotSet.add(d[0] + d[1] * lengthX);
        }
        solution1(dotSet, lengthX, lengthY);
        solution2(dotSet, lengthX, lengthY);
        

    }

    public static void solution1 (Set<Integer> map, int lengthX, int lengthY) {
        char line = 'x';
        int foldIndex = 655;
        int newLengthX = 655, newLengthY = lengthY;
        Set<Integer> newMap = new HashSet<>();
        Iterator<Integer> i = map.iterator();
        while (i.hasNext()) {
            int index = i.next();
            int x = index % lengthX, y = index / lengthX;
            if (x > foldIndex) {
                int newX = 2 * foldIndex - x, newY = y;
                newMap.add(newX + newY * newLengthX);
            } else if (x < foldIndex) {
                newMap.add(x + y * newLengthX);
            }
        }
        System.out.println("new set size: " + newMap.size());
    }

    public static void solution2 (Set<Integer> dotSet, int XL, int YL) {
        char[] foldCor = new char[]{'x', 'y', 'x', 'y', 'x', 'y', 'x', 'y', 'x', 'y', 'y', 'y'};
        int[] foldIndex = new int[]{655, 447, 327, 223, 163, 111, 81, 55, 40, 27, 13, 6};

        for (int i = 0; i < 12; i++) {
            char cor = foldCor[i];
            int index = foldIndex[i];
            if (cor == 'x') {
                dotSet = foldX(dotSet, XL, YL, index);
                XL = index;
            } else if (cor == 'y') {
                dotSet = foldY(dotSet, XL, YL, index);
                YL = index;
            }
        }

        for (int j = 0; j < YL; j++) {
            System.out.println();
            for (int i = 0; i < XL; i++) {
                int index = i + j * XL;
                if (dotSet.contains(index)) {
                    System.out.print("#");
                } else {
                    System.out.print("_");
                }
            }
        }
    }

    public static Set<Integer> foldX (Set<Integer> dotSet, int XL, int YL, int foldIndex) {
        Set<Integer> newDotSet = new HashSet<>();
        Iterator<Integer> i = dotSet.iterator();
        int newXL = foldIndex, newYL = YL;
        while (i.hasNext()) {
            int index = i.next();
            int x = index % XL, y = index / XL;
            if (x > foldIndex) {
                int newX = 2 * foldIndex - x, newY = y;
                newDotSet.add(newX + newY * newXL);
            } else if (x < foldIndex) {
                newDotSet.add(x + y * newXL);
            }
        }
        return newDotSet;
    }

    public static Set<Integer> foldY (Set<Integer> dotSet, int XL, int YL, int foldIndex) {
        Set<Integer> newDotSet = new HashSet<>();
        Iterator<Integer> i = dotSet.iterator();
        int newXL = XL, newYL = foldIndex;
        while (i.hasNext()) {
            int index = i.next();
            int x = index % XL, y = index / XL;
            if (y > foldIndex) {
                int newX = x, newY = 2 * foldIndex - y;
                newDotSet.add(newX + newY * newXL);
            } else if (y < foldIndex) {
                newDotSet.add(x + y * newXL);
            }
        }
        return newDotSet;
    }
}
