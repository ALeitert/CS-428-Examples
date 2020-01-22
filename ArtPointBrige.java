
public class ArtPointBrige
{
    public static int[] artPoints(Graph g, int startId)
    {
        int noOfVertices = g.noOfVertices;

        int[] parIds = new int[noOfVertices];

        int[] depth = new int[noOfVertices];
        int[] low = new int[noOfVertices];

        // Helpers to compute DFS
        int[] neighIndex = new int[noOfVertices];
        int[] stack = new int[noOfVertices];
        int stackSize = 0;

        boolean[] visited = new boolean[noOfVertices];

        for (int vId = 0; vId < noOfVertices; vId++)
        {
            parIds[vId] = -1;

            depth[vId] = -1;
            low[vId] = -1;

            visited[vId] = false;
            neighIndex[vId] = 0;
        }

        // Push
        stack[stackSize] = startId;
        stackSize++;

        while (stackSize > 0)
        {
            int vId = stack[stackSize - 1];
            int nInd = neighIndex[vId];

            if (nInd == 0)
            {
                visited[vId] = true;

                // *** Pre-order for vId ***

                // Vertices on stack are ancestors of v.
                // Hence, hight of stack is depth of current vertex.
                // (We subtract 1 to ensure root has depth 0.)
                depth[vId] = stackSize - 1;
            }

            if (nInd < g.edges[vId].length)
            {
                int neighId = g.edges[vId][nInd];

                if (!visited[neighId])
                {
                    // Push
                    stack[stackSize] = neighId;
                    stackSize++;

                    parIds[neighId] = vId;
                }

                neighIndex[vId]++;
            }
            else
            {
                // All neighbours checked, backtrack.
                stackSize--; // Pop;

                // *** Post-order for vId ***

                // Compute lowpoint of v.
                // low(v) = { depth(w) | w in N[u], u is descendant of v (inclusive). }
                low[vId] = depth[vId];

                for (int i = 0; i < g.edges[vId].length; i++)
                {
                    int uId = g.edges[vId][i];

                    low[vId] = Math.min(low[vId], depth[uId]);

                    if (parIds[uId] == vId)
                    {
                        low[vId] = Math.min(low[vId], low[uId]);
                    }
                }
            }
        }

        return null;
    }
}





