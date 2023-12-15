package org.example.merkle;

import org.example.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MerkleTree
{
    private final MerkleNode root;

    public static String hashTransactions(List<String> transactions)
    {
        return new MerkleTree(transactions).root.hash;
    }

    private MerkleTree(List<String> transactions)
    {
        List<MerkleNode> upperLevel = new ArrayList<>();
        List<MerkleNode> lowerLevel = new ArrayList<>();
        for (var transactionHash : transactions)
        {
            MerkleNode node = new MerkleNode(transactionHash);
            lowerLevel.add(node);
            if (lowerLevel.size() >= 2)
            {
                String hashSum = lowerLevel.stream()
                        .map(m -> m.hash)
                        .collect(Collectors.joining());

                String hash = Utilities.hash(hashSum);
                upperLevel.add(new MerkleNode(lowerLevel, hash));
                lowerLevel.clear();
            }
        }

        while (upperLevel.size() != 1)
        {
            upperLevel = composeTree(upperLevel);
        }

        this.root = upperLevel.get(0);
    }

    private static List<MerkleNode> composeTree(List<MerkleNode> nodes)
    {
        if (nodes.size() <= 1)
        {
            return nodes;
        }

        List<MerkleNode> upperLevel = new ArrayList<>();
        List<MerkleNode> lowerLevel = new ArrayList<>();
        for (var node : nodes)
        {
            lowerLevel.add(node);
            if (lowerLevel.size() >= 2)
            {
                String hashSum = lowerLevel.stream()
                        .map(m -> m.hash)
                        .collect(Collectors.joining());

                String hash = Utilities.hash(hashSum);
                upperLevel.add(new MerkleNode(lowerLevel, hash));
                lowerLevel.clear();
            }
        }
        return composeTree(upperLevel);
    }
}