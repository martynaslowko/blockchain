package org.example.merkle;

import java.util.List;
import java.util.Objects;

class MerkleNode
{
    protected MerkleNode parent;
    protected String hash;
    protected MerkleNode left;
    protected MerkleNode right;

    public MerkleNode(String hash)
    {
        this.hash = hash;
    }

    public MerkleNode(List<MerkleNode> nodes, String hash)
    {
        this.hash = hash;
        this.left = nodes.get(0);
        this.right = nodes.get(1);
        nodes.forEach(c -> c.parent = this);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        else if (o == null || getClass() != o.getClass())
            return false;
        else
            return Objects.equals(hash, ((MerkleNode) o).hash);
    }

    @Override
    public int hashCode()
    {
        return hash != null ? hash.hashCode() : 0;
    }
}