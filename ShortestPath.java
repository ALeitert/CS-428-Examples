
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
}