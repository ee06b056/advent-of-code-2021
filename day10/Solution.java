package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<String>();
        try {
            File f = new File("day10/input.data");
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

    public static void solution1 (List<String> lines) {
        int sum = 0;
        for (String line : lines) {
            Deque<Character> stack = new LinkedList<>();
            boolean isCorrupted = false;
            for (char c : line.toCharArray()) {
                switch (c) {
                    case '{':
                    case '[':
                    case '(':
                    case '<':
                        stack.addLast(c);
                        break;
                    case '}':
                        if (stack.peekLast() == '{') {
                            stack.removeLast();
                        } else {
                            sum += 1197;
                            isCorrupted = true;
                        }
                        break;
                    case ']':
                        if (stack.peekLast() == '[') {
                            stack.removeLast();
                        } else {
                            sum += 57;
                            isCorrupted = true;
                        }
                        break;
                    case ')':
                        if (stack.peekLast() == '(') {
                            stack.removeLast();
                        } else {
                            sum += 3;
                            isCorrupted = true;
                        }
                        break;
                    case '>':
                        if (stack.peekLast() == '<') {
                            stack.removeLast();
                        } else {
                            sum += 25137;
                            isCorrupted = true;
                        }
                        break;
                    default:
                        break;
                }
                if (isCorrupted) {
                    break;
                }
            }
        }
        System.out.println(sum);
    }

    public static void solution2 (List<String> lines) {
        List<Long> sums = new ArrayList<>();
        for (String line : lines) {
            boolean isCorrupted = false;
            Deque<Character> stack = new LinkedList<>();
            for (char c : line.toCharArray()) {
                switch (c) {
                    case '(':
                    case '[':
                    case '{':
                    case '<':
                        stack.addLast(c);
                        break;
                    case '}':
                        if (stack.peekLast() == '{') {
                            stack.removeLast();
                        } else {
                            isCorrupted = true;
                        }
                        break;
                    case ']':
                        if (stack.peekLast() == '[') {
                            stack.removeLast();
                        } else {
                            isCorrupted = true;
                        }
                        break;
                    case ')':
                        if (stack.peekLast() == '(') {
                            stack.removeLast();
                        } else {
                            isCorrupted = true;
                        }
                        break;
                    case '>':
                        if (stack.peekLast() == '<') {
                            stack.removeLast();
                        } else {
                            isCorrupted = true;
                        }
                        break;
                    default:
                        break;
                }
                if (isCorrupted) {
                    break;
                }
            }
            if (!isCorrupted) {
                sums.add(compute(stack));
            }
        }
        sums.sort(null);
        System.out.println(sums.get(sums.size() / 2));
        
    }

    public static long compute (Deque<Character> stack) {
        long res = 0L;
        while (stack.size() > 0) {
            char c = stack.pollLast();
            switch (c) {
                case '(':
                    res = res * 5 + 1;
                    break;
                case '[':
                    res = res * 5 + 2;
                    break;
                case '{':
                    res = res * 5 + 3;
                    break;
                case '<':
                    res = res * 5 + 4;
                default:
                    break;
            }
        }
        return res;
    }

    
}
