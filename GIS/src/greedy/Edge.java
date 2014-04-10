package greedy;


class Edge implements Comparable<Edge>
{
    int v1, v2;
    int weight;

    public Edge(int vertexA, int vertexB, int weight)
    {
        this.v1 = vertexA;
        this.v2 = vertexB;
        this.weight = weight;
    }
    public int getVertexA()
    {
        return v1;
    }
    public int getVertexB()
    {
        return v2;
    }
    public int getWeight()
    {
        return weight;
    }

    public String toString()
    {
        return "(" + v1 + ", " + v2 + ") : w = " + weight;
    }
    public int compareTo(Edge edge)
    {
        return (this.weight < edge.weight) ? -1: 1;
    }
}
