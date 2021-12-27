package day18;

import java.io.File;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Solution
 */
public class Solution {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        try {
            File f = new File("day18/input.data");
            f.exists();
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Pair> pairList = new ArrayList<>();
        for (String s : list) {
            pairList.add(parsePairString(s));
        }


        System.out.println(list.size());
        System.out.println(pairList.size());
        Pair p = parsePairString("[[[[9,8],1],2],3]");
        System.out.println(shouldExplode(p, 0));
        p.leftPair.leftPair.leftPair.value = 14;
        System.out.println(shouldSplit(p));


        // Pair p = parsePairString("[[0,[[0,9],[6,5]]],[[[5,3],[0,4]],[8,3]]]");
        // System.out.println(p.isLeaf);
        // System.out.println(p.leftPair.isLeaf);
        // System.out.println(p.rightPair.isLeaf);
        // System.out.println(p.leftPair.value);
        // System.out.println(p.rightPair.value);


    }

    public static Pair parsePairString (String pairString) {
        String trimedS = pairString.trim().replace("\\s+", "");
        Deque<Pair> pairStack = new LinkedList<>();
        Deque<Short> lrStack = new LinkedList<>();
        Pair currentPair = null;
        short lr = 0;
        for (char c : trimedS.toCharArray()) {
            switch (c) {
                case '[':
                    if (currentPair != null) {
                        pairStack.offerLast(currentPair);
                        lrStack.offerLast(lr);
                    }
                    currentPair = new Pair();
                    lr = 0;
                    break;
                case ']':
                    if (pairStack.size() != 0) {
                        Pair previousPair = pairStack.pollLast();
                        short previousLR = lrStack.pollLast();
                        if (previousLR == 0) {
                            previousPair.leftPair = currentPair;
                        } else {
                            previousPair.rightPair = currentPair;
                        }
                        currentPair = previousPair;
                        lr = previousLR;
                    } else {
                        return currentPair;
                    }
                    break;
                case ',':
                    lr = 1;
                    break;
                default:
                    int n = c - 48;
                    if (lr == 0) {
                        currentPair.leftPair = new Pair(n);
                    } else {
                        currentPair.rightPair = new Pair(n);
                    }
                    break;
            }
        }
        return currentPair;
    }

    public static boolean shouldExplode (Pair p, int level) {
        if (p == null) return false;
        if (level >= 5) return true;
        if (p.isLeaf) return false;
        return shouldExplode(p.leftPair, level + 1) || shouldExplode(p.rightPair, level + 1);
    }

    public static boolean shouldSplit (Pair p) {
        if (p == null) return false;
        if (p.value >= 10) return true;
        return shouldSplit(p.leftPair) || shouldSplit(p.rightPair);
        
    }

    public static Pair add (Pair p1, Pair p2) {
        Pair newP = new Pair();
        newP.leftPair = p1;
        newP.rightPair = p2;
        return newP;
    }

    public static void reduce (Pair p) {
        boolean shouldExplode = false, shouldSplit = false;
        while (true) {
            shouldExplode = shouldExplode(p, 0);
            if (shouldExplode) {
                explode(p);
            }
            shouldSplit = shouldSplit(p);
            if (shouldSplit) {
                split(p);
            }
            if (!shouldExplode && !shouldSplit) break;
        }
    }

    public static void explode (Pair p) {
        
    }

    public static void split (Pair p) {

    }




}

class Pair {
    boolean isLeaf;
    int value;
    Pair leftPair, rightPair;
    
    public Pair () {
        this.isLeaf = false;
        this.value = 0;
        this.leftPair = null;
        this.rightPair = null;
    }

    public Pair (int leafValue) {
        this.isLeaf = true;
        this.value = leafValue;
        this.leftPair = this.rightPair = null;
    }
}
