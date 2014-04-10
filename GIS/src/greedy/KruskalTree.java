package greedy;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.Vector;

class KruskalTree
{
    Vector<HashSet<Integer>> groups = new Vector<HashSet<Integer>>();
    TreeSet<Edge> kruskalTree = new TreeSet<Edge>();

    public TreeSet<Edge> getEdges()
    {
        return kruskalTree;
    }
    HashSet<Integer> getGroup(int vertex)
    {
        for (HashSet<Integer> vertexGroup : groups) {
            if (vertexGroup.contains(vertex)) {
                return vertexGroup;
            }
        }
        return null;
    }
    public void insertEdge(Edge edge)
    {
        int vertexA = edge.getVertexA();
        int vertexB = edge.getVertexB();

        HashSet<Integer> gropuA = getGroup(vertexA);
        HashSet<Integer> groupB = getGroup(vertexB);

        // sprawdzenie czy wierzcholek znajduje sie w grupie A i jesli nie znajduje sie to dodajemy do drzewa
        if (gropuA == null) {
            kruskalTree.add(edge);
            
            // jesli dodatkowo nie znajduje sie w grupie B to tworzymy now¹ grupê dla tych dwóch wierzcho³kow
            if (groupB == null) {
                HashSet<Integer> hashGroup = new HashSet<Integer>();
                hashGroup.add(vertexA);
                hashGroup.add(vertexB);
                groups.add(hashGroup);
            }
            else {
                groupB.add(vertexA);           
            }
        }
        else {
            if (groupB == null) {
                gropuA.add(vertexB);
                kruskalTree.add(edge);
            }
            else if (gropuA != groupB) {
                gropuA.addAll(groupB);
                groups.remove(groupB);
                kruskalTree.add(edge);
            }
        }
    }
}