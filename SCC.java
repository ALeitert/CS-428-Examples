
public class SCC
{
    public static int[][] strConComps(Graph g, int startId)
    {
        int noOfVertices = g.noOfVertices;

        int[] parIds = new int[noOfVertices];

        int[] preInd = new int[noOfVertices];
        int preCount = 0;


        // Helpers to compute DFS
        int[] neighIndex = new int[noOfVertices];
        int[] stack = new int[noOfVertices];
        int stackSize = 0;

        boolean[] visited = new boolean[noOfVertices];

        for (int vId = 0; vId < noOfVertices; vId++)
        {
            parIds[vId] = -1;

            preInd[vId] = -1;

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
                preInd[vId] = preCount;
                preCount++;
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
            }
        }

        return null;
    }
}





