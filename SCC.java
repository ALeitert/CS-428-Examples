import java.util.*;

public class SCC
{
    public static int[][] strConComps(Graph g, int startId)
    {
        int noOfVertices = g.noOfVertices;

        int[] parIds = new int[noOfVertices];

        int[] preInd = new int[noOfVertices];
        int[] sccBuffer = new int[noOfVertices];

        int preCount = 0;
        int bufCount = 0;

        int[] low = new  int[noOfVertices];
        boolean[] ignore = new boolean[noOfVertices];

        ArrayList<int[]> output = new ArrayList<int[]>();

        // Helpers to compute DFS
        int[] neighIndex = new int[noOfVertices];
        int[] stack = new int[noOfVertices];
        int stackSize = 0;

        boolean[] visited = new boolean[noOfVertices];

        for (int vId = 0; vId < noOfVertices; vId++)
        {
            parIds[vId] = -1;
            preInd[vId] = -1;

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

                // Add v to buffer of processed vertices.
                sccBuffer[bufCount] = vId;
                bufCount++;


                // Compute lowpoint of v.
                // low(v) = min { pre(w) | w in N[u], u is descendant of v (inclusive). }
                //        = min
                //          (
                //              pre(v),
                //              min { pre(u) | u in N(v) },
                //              min { low(u) | u is child of v }
                //          )
                // Note that, due to the nature of DFS, each neighbour u of v has already been processed.

                low[vId] = preInd[vId];

                for (int i = 0; i < g.edges[vId].length; i++)
                {
                    int uId = g.edges[vId][i];
                    if (ignore[uId]) continue;

                    low[vId] = Math.min(low[vId], preInd[uId]);

                    if (parIds[uId] == vId)
                    {
                        low[vId] = Math.min(low[vId], low[uId]);
                    }
                }


                if (low[vId] == preInd[vId])
                {
                    // v is root of SCC.
                    // Vertices in buffer form that SCC.

                    for (int i = 0; i < bufCount; i++)
                    {
                        int uId = sccBuffer[i];
                        ignore[uId] = true;
                    }

                    output.add(Arrays.copyOf(sccBuffer, bufCount));

                    bufCount = 0;
                }
            }
        }

        int[][] result = new int[output.size()][];

        for (int i = 0; i < result.length; i++)
        {
            result[i] = output.get(i);
        }

        return result;
    }
}





