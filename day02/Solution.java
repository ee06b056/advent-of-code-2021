package day02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        List<String> ops = new ArrayList<String>();
        try {
            File f = new File("day02/input.data");
            f.exists();
            System.out.println(f.getPath());
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                ops.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(solution1(ops));
        System.out.println(solution2(ops));
    }

    private static long solution1 (List<String> ops) {
        long h = 0, d = 0;
        for (String op : ops) {
            String[] opArr = op.trim().split("\\s+");
            int num = Integer.parseInt(opArr[1]);
            switch (opArr[0]) {
                case "forward":
                    h += num;
                    break;
                case "up":
                    d -= num;
                    if (d < 0) d = 0;
                    break;
                case "down":
                    d += num;
                    break;
                default:
                    break;
            }
        }
        return h * d;
    }

    private static long solution2 (List<String> ops) {
        long h = 0, d = 0, a = 0;
        for (String op : ops) {
            String[] opArr = op.trim().split("\\s+");
            int num = Integer.parseInt(opArr[1]);
            switch (opArr[0]) {
                case "forward":
                    h += num;
                    d += a * num;
                    break;
                case "up":
                    a -= num;
                    break;
                case "down":
                    a += num;
                    break;
                default:
                    break;
            }
        }
        return h * d;
    }
}
