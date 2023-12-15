package org.example.blockchain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.example.Utilities;
import org.example.merkle.MerkleTree;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class Block {
    private final int index;
    private final String hash;
    private final String previousHash;
    private final long timeStamp;
    private final BigInteger nonce;

    public Block(int index, String previousHash, long timeStamp, BigInteger nonce, List<String> transactions) {
        this.index = index;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.hash = calculateBlockHash(previousHash, timeStamp, nonce, transactions);
    }

    /**
     * Creates genesis block.
     */
    public Block() {
        this.index = 0;
        this.hash = "0".repeat(64);
        this.previousHash = "0";
        this.timeStamp = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        this.nonce = BigInteger.ZERO;
    }

    public static String calculateBlockHash(String previousHash, long timeStamp, BigInteger nonce, List<String> transactionHashes) {
        String data = MerkleTree.hashTransactions(transactionHashes);
        String dataToHash = previousHash
                + timeStamp
                + nonce
                + data;
        return Utilities.hash(dataToHash);
    }

    @JsonIgnore
    public int getIndex() {
        return index;
    }

    public String getHash() {
        return hash;
    }

    public BigInteger getNonce() {
        return nonce;
    }
}