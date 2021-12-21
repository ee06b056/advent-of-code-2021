package day12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        Map<String, List<String>> g = new HashMap<>();
        try {
            File f = new File("day12/input.data");
            Scanner scanner = new Scanner(f);
            f.exists();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] ns = line.split("-");
                List<String> l1 = g.putIfAbsent(ns[0], new ArrayList<>());
                List<String> l2 = g.putIfAbsent(ns[1], new ArrayList<>());
                l1 = g.get(ns[0]);
                l2 = g.get(ns[1]);
                l1.add(ns[1]);
                l2.add(ns[0]);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        solution1_BFS(g);
        solution1_DFS(g);
        solution2(g);
        
    }

    public static void solution1_BFS (Map<String, List<String>> g) {
        Set<String> visitedSmall = new HashSet<>();
        Deque<List<String>> q = new LinkedList<>();
        List<List<String>> paths = new ArrayList<>();
        List<String> l = new ArrayList<>();
        l.add("start");
        q.addLast(l);
        while (q.size() > 0) {
            visitedSmall.clear();
            List<String> p = q.pollFirst();
            for (String s : p) {
                if (s.charAt(0) >= 97) {
                    visitedSmall.add(s);
                }
            }
            String n = p.get(p.size() - 1);
            for (String nextN : g.get(n)) {   
                if (nextN.equals("end")) {
                    List<String> path = copyList(p);
                    path.add("end");
                    paths.add(path);
                } else if (nextN.charAt(0) <= 90 || !visitedSmall.contains(nextN)) {
                    List<String> newList = copyList(p);
                    newList.add(nextN);
                    q.addLast(newList);
                    if (nextN.charAt(0) >= 97) {
                        visitedSmall.add(nextN);
                    }
                }
            }
        }
        System.out.println(paths.size());
    }

    public static void solution1_DFS (Map<String, List<String>> g) {
        Set<String> visitedSmall = new HashSet<>();
        List<List<String>> paths = new ArrayList<>();
        List<String> tempPath = new ArrayList<>();
        dfs(g, paths, visitedSmall, tempPath, "start");
        System.out.println(paths.size());
    }

    public static void dfs (Map<String, List<String>> g, List<List<String>> paths, Set<String> visitedSmall, List<String> tempPath, String currentNode) {
        if (currentNode.charAt(0) >= 97) {
            visitedSmall.add(currentNode);
        }
        tempPath.add(currentNode);
        if (currentNode.equals("end")) {
            paths.add(tempPath);
        } else {
            for (String nextNode : g.get(currentNode)) {
                if (!visitedSmall.contains(nextNode)) {
                    dfs(g, paths, visitedSmall, tempPath, nextNode);
                }
            }
        }
        tempPath.remove(tempPath.size() - 1);
        visitedSmall.remove(currentNode);
    }

    public static <T> List<T> copyList (List<T> list) {
        List<T> res =  new ArrayList<>();
        for (T e : list) {
            res.add(e);
        }
        return res;
    }

    public static void solution2 (Map<String, List<String>> g) {
        Set<String> visitedSmall = new HashSet<>();
        List<String> paths = new ArrayList<>();
        Deque<String> tempPath = new LinkedList<>();
        dfs_2(g, paths, visitedSmall, tempPath, null, "start");
        System.out.println(paths.size());
    }

    public static void dfs_2 (Map<String, List<String>> g, List<String> paths, Set<String> visitedSmall, Deque<String> tempPath, String doubleVisitedNode, String currentNode) {
        tempPath.addLast(currentNode);
        if (currentNode.charAt(0) >= 97) {
            visitedSmall.add(currentNode);
        }
        if (currentNode.equals("end")) {
            paths.add(getPathString(tempPath));
        } else {
            for (String nextNode : g.get(currentNode)) {
                if (nextNode.equals("start")) {
                    continue;
                } else if (visitedSmall.contains(nextNode) && doubleVisitedNode == null) {
                    dfs_2(g, paths, visitedSmall, tempPath, nextNode, nextNode);
                } else if (!visitedSmall.contains(nextNode)) {
                    dfs_2(g, paths, visitedSmall, tempPath, doubleVisitedNode, nextNode);
                }
            }
        }
        tempPath.pollLast();
        if (!currentNode.equals(doubleVisitedNode)) {
            visitedSmall.remove(currentNode);
        }
    }

    public static String getPathString (Deque<String> tempPath) {
        StringBuilder sb = new StringBuilder();
        for (String node : tempPath) {
            sb.append(node);
            sb.append("->");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
}
