package day17;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        int[] xb = {150, 171}, yb = {-70, -129};
        
        solution1(xb, yb);
        solution2(xb, yb);
    }

    public static void solution1 (int[] xb, int[] yb) {
        int h = 0;
        for (int v = 128; v >= 0; v--) {
            h += v;
        }
        System.out.println(h);
    }

    public static void solution2 (int[] xb, int[] yb) {
        List<int[]> ans = new ArrayList<>();
        for (int xv = 17; xv <= 171; xv++) {
            for (int yv = 128; yv >= -129; yv--) {
                if (hitTarget(xb, yb, xv, yv)) {
                    ans.add(new int[]{xv, yv});
                }
            }
        }
        System.out.println(ans.size());
    }

    public static boolean hitTarget (int[] xb, int[] yb, int xv, int yv) {
        int x = 0, y = 0;
        while (true) {
            if (x > xb[1] || y < yb[1]) break;
            x += xv;
            y += yv;
            if (x >= xb[0] && x <= xb[1] && y >= yb[1] && y <= yb[0]) {
                return true;
            }
            xv = xv == 0 ? 0 : xv - 1;
            yv--;
        }
        return false;
    }    
}
