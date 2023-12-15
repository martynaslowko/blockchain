package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.blockchain.Block;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Utilities {
    public static final ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private static final Random random = new Random();
    private static final Map<String, String> digiMap = new HashMap<>();

    static {
        digiMap.put("0", "0000");
        digiMap.put("1", "0001");
        digiMap.put("2", "0010");
        digiMap.put("3", "0011");
        digiMap.put("4", "0100");
        digiMap.put("5", "0101");
        digiMap.put("6", "0110");
        digiMap.put("7", "0111");
        digiMap.put("8", "1000");
        digiMap.put("9", "1001");
        digiMap.put("a", "1010");
        digiMap.put("b", "1011");
        digiMap.put("c", "1100");
        digiMap.put("d", "1101");
        digiMap.put("e", "1110");
        digiMap.put("f", "1111");
    }

    public static String hexToBinary(String s) {
        char[] hex = s.toCharArray();
        StringBuilder binaryString = new StringBuilder();
        for (char h : hex) {
            binaryString.append(digiMap.get(String.valueOf(h)));
        }
        return binaryString.toString();
    }

    public static String hash(String value)
    {
        try {
            MessageDigest digest;
            byte[] bytes;
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(value.getBytes(UTF_8));
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes) {
                buffer.append(String.format("%02x", b));
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayMiningResult(long nanoseconds, Block block, int difficulty) throws JsonProcessingException {
        double seconds = (double) nanoseconds / 1_000_000_000.0;
        String delimiter = "=".repeat(90);
        System.out.println(delimiter);
        System.out.println("DIFFICULTY: " + difficulty);
        System.out.printf("Mining took: %f seconds%n", seconds);
        System.out.println(writer.writeValueAsString(block));
        System.out.println(delimiter);
        System.out.println();
    }

    public static List<String> generateTransactions(int qty) {
        return IntStream.range(0, qty).mapToObj(i -> randomTransaction()).collect(Collectors.toList());
    }

    public static String randomTransaction() {
        byte[] r = new byte[16];
        random.nextBytes(r);
        return new String(r);
    }
}
