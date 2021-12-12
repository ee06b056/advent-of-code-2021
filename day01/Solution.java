package day01;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        List<Integer> reports = new ArrayList<Integer>();
        try {
            File f = new File("day01/input.data");
            f.exists();
            System.out.println(f.getPath());
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                reports.add(Integer.parseInt(scanner.nextLine()));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(solution1(reports));
        System.out.println(solution2(reports));
    }

    public static int solution1 (List<Integer> reports) {
        int res = 0;
        for (int i = 1; i < reports.size(); i++) {
            if (reports.get(i) > reports.get(i - 1)) {
                res++;
            }
        }
        return res;
    }

    public static int solution2 (List<Integer> reports) {
        int res = 0;
        for (int i = 3; i < reports.size(); i++) {
            int sum = reports.get(i - 2) + reports.get(i - 1) + reports.get(i);
            int previousSum = reports.get(i - 3) + reports.get(i - 2) + reports.get(i - 1);
            if (sum > previousSum) res++;
        }
        return res;
    }
}
