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

        solution1(pairList);

        
    }

    public static void solution1 (List<Pair> pairList) {
        Pair p1 = pairList.get(0);
        Pair p2 = pairList.get(1);
        Pair fp = add(p1, p2);
        reduce(fp);
        for (int i = 2; i < pairList.size(); i++) {
            fp = add(fp, pairList.get(i));
            reduce(fp);
        }
        System.out.println("ans1: " + fp.magnitude());
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
                continue;
            }
            shouldSplit = shouldSplit(p);
            if (shouldSplit) {
                split(p);
            }
            if (!shouldExplode && !shouldSplit) break;
        }
    }

    public static void explode (Pair p) {
        explodeDfs(p, 0);
    }

    public static int[] explodeDfs (Pair p, int level) {
        if (p.isLeaf) return null;
        if (p.leftPair.isLeaf && p.rightPair.isLeaf) {
            if (level < 4) return null;
            else {
                p.isLeaf = true;
                p.value = 0;
                int leftValue = p.leftPair.value, rightValue = p.rightPair.value;
                p.leftPair = null;
                p.rightPair = null;
                return new int[]{leftValue, rightValue};
            }
        }
        int[] leftRes = explodeDfs(p.leftPair, level + 1);

        if (leftRes != null) {
            if (leftRes[1] > 0) {
                addToLeftMost(p.rightPair, leftRes[1]);
            }
            return new int[]{leftRes[0], -1};
        }

        int[] rightRes = explodeDfs(p.rightPair, level + 1);
        if (rightRes != null) {
            if (rightRes[0] > 0) {
                addToRightMost(p.leftPair, rightRes[0]);
            }
            return new int[]{-1, rightRes[1]};
        }

        return null;
    }

    public static void addToRightMost (Pair p, int n) {
        if (p.isLeaf) {
            p.value += n;
        } else {
            addToRightMost(p.rightPair, n);
        }
    }

    public static void addToLeftMost (Pair p, int n) {
        if (p.isLeaf) {
            p.value += n;
        } else {
            addToLeftMost(p.leftPair, n);
        }
    }

    public static boolean split (Pair p) {
        if (p.isLeaf) {
            if (p.value >= 10) {
                p.leftPair = new Pair(p.value / 2);
                p.rightPair = new Pair(p.value / 2 + p.value % 2);
                p.isLeaf = false;
                p.value = 0;
                return true;
            } else {
                return false;
            }
        }
        boolean leftRes = split(p.leftPair);
        if (leftRes) return true;
        return split(p.rightPair);
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

    public void print () {
        if (this.isLeaf) {
            System.out.print(this.value);
        } else {
            System.out.print("[");
            this.leftPair.print();
            System.out.print(",");
            this.rightPair.print();
            System.out.print("]");
        }
    }

    public int magnitude () {
        if (this.isLeaf) return this.value;
        return 3 * this.leftPair.magnitude() + 2 * this.rightPair.magnitude();
    }
}
