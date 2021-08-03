import java.util.*;

public class DijkstraHeap
{
    // - - - - Private Variables - - - -

    // Array storing heap.
    private int[] verIds = null;

    // Values associated with vertices.
    private int[] values = null;

    // Index in heap of vertices.
    private int[] indices = null;

    private int size = 0;


    // - - - - Constructors - - - -

    public DijkstraHeap(int capacity)
    {
        verIds = new int[capacity];
        values = new int[capacity];
        indices = new int[capacity];

        for (int vId = 0; vId < capacity; vId++)
        {
            indices[vId] = -1;
        }
    }

    // - - - - Getter Functions - - - -

    public int getCapacity()
    {
        return verIds.length;
    }

    public int getSize()
    {
        return size;
    }

    public int[] getValues()
    {
        return values;
    }

    // - - - - Public Heap-Operations - - - -

    public boolean contains(int vId)
    {
        return indices[vId] >= 0;
    }

    public void add(int vId, int vVal)
    {
        if (contains(vId))
        {
            System.out.println("* * * Error: Vertex is already in heap! * * *");
            return;
        }

        if (getSize() >= getCapacity())
        {
            System.out.println("* * * Error: Heap is already full! * * *");
            return;
        }

        verIds[size] = vId;
        values[vId] = vVal;
        indices[vId] = size;

        size++;

        moveUp(size - 1);
    }

    public void update(int vId, int vVal)
    {
        if (!contains(vId))
        {
            System.out.println("* * * Error: Vertex is not in heap! * * *");
            return;
        }

        int index = indices[vId];
        values[vId] = vVal;

        if (index == 0)
        {
            heapify(index);
            return;
        }

        int parId = verIds[parent(index)];

        int parVal = values[parId];
        int indVal = values[verIds[index]];

        if (parVal <= indVal)
        {
            heapify(index);
        }
        else
        {
            moveUp(index);
        }
    }

    public int getMinId()
    {
        CeckNotEmpty();
        return verIds[0];
    }

    public int getMinValue()
    {
        CeckNotEmpty();
        return values[verIds[0]];
    }

    public int removeMin()
    {
        CeckNotEmpty();

        int minId = verIds[0];

        size--;

        verIds[0] = verIds[size];

        indices[minId] = -1;
        indices[verIds[0]] = 0;

        heapify(0);

        return minId;
    }

    private void CeckNotEmpty()
    {
        if (getSize() > 0) return;
        System.out.println("* * * Error: Heap is empty! * * *");
    }


    // - - - - Private Heap-Operations - - - -

    private void heapify(int index)
    {
        while (true)
        {
            int l = left(index);
            int r = right(index);

            if (l >= getSize())
            {
                return;
            }

            int smallInd = l;
            int smallVal = values[verIds[smallInd]];

            if (r < getSize())
            {
                int rVal = values[verIds[r]];

                if (rVal < smallVal)
                {
                    smallInd = r;
                    smallVal = rVal;
                }
            }

            int indVal = values[verIds[index]];

            if (smallVal >= indVal)
            {
                return;
            }

            swapKeys(smallInd, index);
            index = smallInd;
        }
    }

    private void moveUp(int index)
    {
        while (index > 0)
        {
            int parInd = parent(index);

            int parVal = values[verIds[parInd]];
            int indVal = values[verIds[index]];

            if (parVal <= indVal)
            {
                return;
            }

            swapKeys(parInd, index);
            index = parInd;
        }
    }


    // - - - - Helper Functions - - - -

    private int left(int index)
    {
        return 2 * index + 1;
    }

    private int right(int index)
    {
        return 2 * index + 2;
    }

    private int parent(int index)
    {
        return (index - 1) / 2;
    }

    private void swapKeys(int ind1, int ind2)
    {
        int vId1 = verIds[ind1];
        int vId2 = verIds[ind2];

        verIds[ind1] = vId2;
        verIds[ind2] = vId1;

        indices[vId1] = ind2;
        indices[vId2] = ind1;
    }
}
