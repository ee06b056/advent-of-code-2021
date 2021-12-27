package day16;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        List<Character> codes = new ArrayList<>();
        try {
            File f = new File("day16/input.data");
            f.exists();
            Scanner scanner = new Scanner(f).useDelimiter("");
            while (scanner.hasNext()) {
                String hexC = scanner.next();
                String binaryS = Integer.toBinaryString(Integer.parseInt(hexC, 16));
                String binaryS4 = String.format("%4s", binaryS).replace(' ', '0');
                for (char c : binaryS4.toCharArray()) {
                    codes.add(c);
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        solution1(codes);
        solution2(codes);

    }

    public static void solution1 (List<Character> codes) {
        Packet rootPacket = decode (codes, 0);
        int versionSum = addVersion(rootPacket);
        System.out.println("version sum: " + versionSum);
    }

    public static int addVersion (Packet p) {
        int res = p.version;
        if (p.ID == 4) {
            return res;
        }
        for (Packet subP : p.subPackets) {
            res += addVersion(subP);
        }
        return res;
    }

    public static Packet decode (List<Character> codes, int beginIndex) {
        int version = getVersion(codes, beginIndex);
        int ID = getID(codes, beginIndex);
        char lengthTypeID = codes.get(beginIndex + 6);
        
        Packet packet = new Packet(version, ID);

        if (ID == 4) {
            long[] numberPair = parse_literal_numbers(codes, beginIndex);
            packet.literalValue = numberPair[0];
            packet.length = 6 + 5 * ((int) numberPair[1] + 1);
        } else if (lengthTypeID == '0') {
            packet.subPackets = new ArrayList<>();
            int subPacketsLength = getSubPacketsLength(codes, beginIndex);
            packet.length = 22 + subPacketsLength;
            int lengthSum = 0;
            beginIndex += 22;
            while (lengthSum < subPacketsLength) {
                Packet subPacket = decode(codes, beginIndex);
                beginIndex += subPacket.length;
                lengthSum += subPacket.length;
                packet.subPackets.add(subPacket);
            }
        } else {
            packet.subPackets = new ArrayList<>();
            int subPacketsCount = getSubPacketsCount(codes, beginIndex);
            beginIndex += 18;
            int subPacketsLength = 0;
            for (int i = 0; i < subPacketsCount; i++) {
                Packet subPacket = decode(codes, beginIndex);
                beginIndex += subPacket.length;
                subPacketsLength += subPacket.length;
                packet.subPackets.add(subPacket);
            }
            packet.length = 18 + subPacketsLength;
        }
        return packet;
    }

    public static long[] parse_literal_numbers (List<Character> codes, int beginIndex) {
        long[] numberPair = new long[2];
        int n = 0;
        StringBuilder sb = new StringBuilder();
        while (true) {
            char numberPrefix = codes.get(beginIndex + 6 + (5 * n));
            for (int i = 1; i <= 4; i++) {
                sb.appendCodePoint(codes.get(beginIndex + 6 + (5 * n) + i));
            }
            if (numberPrefix == '0') {   
                break;
            }
            n++;
        }
        long number = Long.valueOf(sb.toString(), 2);
        numberPair[0] = number;
        numberPair[1] = n;
        return numberPair;
    }

    private static int getID (List<Character> codes, int beginIndex) {
        String IDS = "" + codes.get(beginIndex + 3) + codes.get(beginIndex + 4) + codes.get(beginIndex + 5);
        int ID = Integer.valueOf(IDS, 2);
        return ID;
    }

    public static int getVersion (List<Character> codes, int beginIndex) {
        String versionS = "" + codes.get(beginIndex) + codes.get(beginIndex + 1) + codes.get(beginIndex + 2);
        int version = Integer.valueOf(versionS, 2);
        return version;
    }

    public static int getNumber (List<Character> codes, int beginIndex, int n) {
        String numberS = "" + codes.get(beginIndex + 6 + (5 * n) + 1) + codes.get(beginIndex + 6 + (5 * n) + 2) + codes.get(beginIndex + 6 + (5 * n) + 3) + codes.get(beginIndex + 6 + (5 * n) + 4);
        int number = Integer.valueOf(numberS, 2);
        return number;
    }

    public static int getSubPacketsLength (List<Character> codes, int beginIndex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            sb.append(codes.get(beginIndex + 7 + i));
        }
        int length = Integer.valueOf(sb.toString(), 2);
        return length;
    }

    public static int getSubPacketsCount (List<Character> codes, int beginIndex) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            sb.append(codes.get(beginIndex + 7 + i));
        }
        int count = Integer.valueOf(sb.toString(), 2);
        return count;
    }

    public static void solution2 (List<Character> codes) {
        Packet rootPacket = decode (codes, 0);
        long res = calculate(rootPacket);
        System.out.println("calculate res: " + res);
    }

    public static long calculate (Packet p) {
        long res;
        switch (p.ID) {
            case 4:
                return p.literalValue;
            case 0:
                res = 0L;
                for (Packet subP : p.subPackets) {
                    res += calculate(subP);
                }
                return res;
            case 1:
                res = 1L;
                for (Packet subP : p.subPackets) {
                    res *= calculate(subP);
                }
                return res;
            case 2:
                res = Integer.MAX_VALUE;
                for (Packet subP : p.subPackets) {
                    res = Math.min(res, calculate(subP));
                }
                return res;
            case 3:
                res = Integer.MIN_VALUE;
                for (Packet subP : p.subPackets) {
                    res = Math.max(res, calculate(subP));
                }
                return res;
            case 5:
                return calculate(p.subPackets.get(0)) > calculate(p.subPackets.get(1)) ? 1L : 0L;
            case 6:
                return calculate(p.subPackets.get(0)) < calculate(p.subPackets.get(1)) ? 1L : 0L;
            case 7:
                return calculate(p.subPackets.get(0)) == calculate(p.subPackets.get(1)) ? 1L : 0L;
            default:
                break;
        }
        return 0L;
    }

}

class Packet {
    public int version, ID, length;
    public long literalValue;
    public List<Packet> subPackets;

    public Packet (int version, int ID) {
        this.version = version;
        this.ID = ID;

        this.length = 0;
        this.literalValue = 0;
        this.subPackets = null;
    }
}
