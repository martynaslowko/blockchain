package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.blockchain.Blockchain;

import java.util.List;

import static org.example.Utilities.generateTransactions;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        List<String> transactions = generateTransactions(2);
        for (int j = 1; j < 30; j++) {
            new Blockchain(transactions, j, 1);
        }
    }
}