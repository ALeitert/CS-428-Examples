import java.util.*;

public class ShortestPath
{
    /**
     * Relexas an edge of the given graph based on the given distances.
     * @param g A weighted graph.
     * @param vId The vertex from which the edge starts.
     * @param nIdx The index of the vertex in the neighbourhood of v to
     *             which the edge points. That is, the edge goes from vId
     *             to edges[vId][nIdx].
     */
    public static boolean relax(Graph g, int vId, int nIdx, int[] distances)
    {
        int uId = g.edges[vId][nIdx];

        int uDis = distances[uId];
        int vDis = distances[vId];

        int vuWeight = g.weights[vId][nIdx];

        boolean update = false;

        // Avoid problems with overflow.
        if (vuWeight > 0)
        {
            update = vDis < uDis - vuWeight;
        }
        else
        {
            update = vDis + vuWeight < uDis;
        }

        if (update)
        {
            distances[uId] = vDis + vuWeight;
            return true;
        }

        return false;
    }

    public static int[] bellmanFord(Graph g, int startId)
    {
        int[] distances = new int[g.noOfVertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startId] = 0;

        HashSet<Integer> updatedLast = new HashSet<Integer>();
        HashSet<Integer> updateNext = new HashSet<Integer>();

        updatedLast.add(startId);

        for (int i = 1; updatedLast.size() > 0; i++)
        {
            // Avoid endless loops in case of negative cycles.
            if (i >= g.noOfVertices) return null;

            for (int vId : updatedLast)
            {
                for (int nIdx = 0; nIdx < g.edges[vId].length; nIdx++)
                {
                    if (relax(g, vId, nIdx, distances))
                    {
                        updateNext.add(g.edges[vId][nIdx]);
                    }
                }
            }

            updatedLast.clear();

            HashSet<Integer> tmp = updatedLast;
            updatedLast = updateNext;
            updateNext = tmp;
        }

        return distances;
    }

    public static int[] dijkstra(Graph g, int startId)
    {
        DijkstraHeap heap = new DijkstraHeap(g.noOfVertices);

        // Initialise
        for (int vId = 0; vId < g.noOfVertices; vId++)
        {
            heap.add(vId, Integer.MAX_VALUE);
        }

        heap.update(startId, 0);
        int[] distances = heap.getValues();

        while (heap.getSize() > 0)
        {
            int vId = heap.removeMin();

            // Unreachable vertex.
            if (distances[vId] == Integer.MAX_VALUE)
            {
                break;
            }

            for (int nIdx = 0; nIdx < g.edges[vId].length; nIdx++)
            {
                relax(g, vId, nIdx, distances);
            }
        }

        return distances;
    }
}
