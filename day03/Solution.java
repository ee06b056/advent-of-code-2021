package day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<String>();
        try {
            File f = new File("day03/input.data");
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

    private static void solution1 (List<String> inputs) {
        int l = inputs.get(0).length();
        int[] frequencies = new int[l];
        for (String input : inputs) {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '1') {
                    frequencies[i]++;
                }
            }
        } 
        char[] gammaRateArr = new char[l];
        char[] epsilonRateArr = new char[l];
        for (int i = 0; i < l; i++) {
            if (frequencies[i] * 2 > inputs.size()) {
                gammaRateArr[i] = '1';
                epsilonRateArr[i] ='0';
            } else {
                gammaRateArr[i] = '0';
                epsilonRateArr[i] = '1';
            }
        }
        int gammaRate = Integer.parseUnsignedInt(String.valueOf(gammaRateArr), 2);
        int epsilonRate = Integer.parseUnsignedInt(String.valueOf(epsilonRateArr), 2);
        System.out.println(gammaRate * epsilonRate);
    }

    private static void solution2 (List<String> inputs) {
        class Node {
            public int zeroCount = 0, oneCount = 0;
            public Node left, right;
            public Node (int zeroCount, int oneCount) {
                this.zeroCount = zeroCount;
                this.oneCount = oneCount;
                this.left = null;
                this.right = null;
            }
        }
        Node root = new Node(0, 0);
        for (String input : inputs) {
            Node p = root;
            for (char c : input.toCharArray()) {
                switch (c) {
                    case '0':
                        p.zeroCount++;
                        if (p.left == null) {
                            p.left = new Node(0, 0);
                        }
                        p = p.left;
                        break;
                    case '1':
                        p.oneCount++;
                        if (p.right == null) {
                            p.right = new Node(0, 0);
                        }
                        p = p.right;
                        break;
                    default:
                        break;
                }
            }
        }
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        Node p = root, q = root;
        while (p.left != null || p.right != null || q.left != null || q.right != null) {
            if (p.zeroCount > p.oneCount) {
                sb1.append('0');
                p = p.left;
            } else {
                sb1.append('1');
                p = p.right;
            }
            if (q.zeroCount == 0) {
                sb2.append('1');
                q = q.right;
            } else if (q.oneCount == 0) {
                sb2.append('0');
                q = q.left;
            } else if (q.zeroCount > q.oneCount) {
                sb2.append('1');
                q = q.right;
            } else {
                sb2.append('0');
                q = q.left;
            }
        }
        System.out.println(Integer.parseInt(sb1.toString(), 2)  * Integer.parseInt(sb2.toString(), 2));
    }
}
