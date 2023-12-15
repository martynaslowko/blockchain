package org.example.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Stopwatch;


import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.example.Utilities.*;

public class Blockchain {
    private List<Block> list;
    private final SecureRandom random = new SecureRandom();

    public Blockchain(List<String> transactions, int difficulty, int size) throws JsonProcessingException {
        list = new LinkedList<>();
        Block genesis = new Block();
        list.add(genesis);
        Block prev = genesis;
        for (int i = 0; i < size; i++) {
            Block curr = mine(prev, difficulty, transactions);
            list.add(curr);
            prev = curr;
        }
    }

    private Block mine(Block prev, int difficulty, List<String> transactions) throws JsonProcessingException {
        int index = prev.getIndex() + 1;
        long timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        BigInteger nonce = BigInteger.probablePrime(256, random);
        String hashPrefix = "0".repeat(difficulty);
        Stopwatch stopwatch = Stopwatch.createStarted();
        while (true) {
            String attempt = Block.calculateBlockHash(prev.getHash(), timestamp, nonce, transactions);
            String binaryAttempt = hexToBinary(attempt);
            if (binaryAttempt.startsWith(hashPrefix)) {
                Block result = new Block(index, prev.getHash(), timestamp, nonce, transactions);
                displayMiningResult(stopwatch.elapsed(TimeUnit.NANOSECONDS), result, difficulty);
                stopwatch.reset();
                return result;
            }
            nonce = nonce.add(BigInteger.ONE);
        }
    }
}
