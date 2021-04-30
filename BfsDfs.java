
public class BfsDfs
{
    public static int[][] dfs(Graph g, int startId)
    {
        // Output data
        // 0: parent-IDs of DFS-tree
        // 1: pre-order
        // 2: post-order

        int noOfVertices = g.noOfVertices;

        int[] parIds = new int[noOfVertices];

        int[] preOrder = new int[noOfVertices];
        int[] postOrder = new int[noOfVertices];

        int preCount = 0;
        int postCount = 0;


        // Helpers to compute DFS
        int[] neighIndex = new int[noOfVertices];
        int[] stack = new int[noOfVertices];
        int stackSize = 0;

        boolean[] visited = new boolean[noOfVertices];

        for (int vId = 0; vId < noOfVertices; vId++)
        {
            parIds[vId] = -1;

            preOrder[vId] = -1;
            postOrder[vId] = -1;

            visited[vId] = false;
            neighIndex[vId] = 0;
        }

        // Push
        stack[stackSize] = startId;
        stackSize++;

        while (stackSize > 0)
        {
            int vId = stack[stackSize - 1];
            int nIdx = neighIndex[vId];

            if (nIdx == 0)
            {
                visited[vId] = true;

                // *** Pre-order for vId ***
                preOrder[preCount] = vId;
                preCount++;
            }

            if (nIdx < g.edges[vId].length)
            {
                int neighId = g.edges[vId][nIdx];

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
                postOrder[postCount] =  vId;
                postCount++;
            }
        }

        return new int[][]
        {
            parIds,
            preOrder,
            postOrder
        };
    }

    public static int[][] bfs(Graph g, int startId)
    {
        // Output data
        // 0: distances from start vertex
        // 1: BFS-order
        // 2: parent-IDs

        int noOfVertices = g.noOfVertices;

        int[][] bfsResult = new int[3][noOfVertices];

        int[] distances = bfsResult[0];
        int[] q = bfsResult[1];
        int[] parents = bfsResult[2];

        for (int i = 0; i < noOfVertices; i++)
        {
            distances[i] = Integer.MAX_VALUE;
            q[i] = -1;
            parents[i] = -1;
        }


        // Set start vertex
        distances[startId] = 0;
        q[0] = startId;
        int qSize = 1;

        for (int qIdx = 0; qIdx < qSize; qIdx++)
        {
            int vId = q[qIdx];
            int nDis = distances[vId] + 1;

            for (int nIdx = 0; nIdx < g.edges[vId].length; nIdx++)
            {
                int uId = g.edges[vId][nIdx];

                if (nDis < distances[uId])
                {
                    distances[uId] = nDis;
                    parents[uId] = vId;

                    q[qSize] = uId;
                    qSize++;
                }
            }
        }

        return bfsResult;
    }
}





