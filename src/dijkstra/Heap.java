package dijkstra;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cliff Chandler
 * 
 * A class implementing the binary heap (max heap) data structure
 */

/* The constructor takes an array to use as a maximum heap, but does not check that it
 * adheres the heap format. Call the buildHeap method to properly convert an array 
 * into a binary heap
 */
public class Heap {
    
    private Vertex[] heap;
    public int length;
     
    public Heap(List<Vertex> q) {
        heap = q.toArray(new Vertex[q.size()]);
        length = heap.length;
        buildMinHeap();
    }
    
    public void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = i;
        
        if (l < length / 2) {
            if (heap[l].cost < heap[i].cost) {
                smallest = l;
            }
        }
        if (r < length / 2) {    
            if (heap[r].cost < heap[smallest].cost) {
                smallest = r;
            }
        } 
        
        if (smallest != i) {
            Vertex temp = heap[i];
            heap[i] = heap[smallest];
            heap[smallest] = temp;
            if (smallest <= length/2) {
                heapify(smallest);
            }
        }
    }
    
    public void buildMinHeap() {
        for(int i = length / 2 - 1; i >= 0; i--) {
            heapify(i);
        }
    }
    
    public int left(int i) {
        i = (i+1) * 2 - 1;
        return i;
    }
    
    public int right(int i) {        
        i = (i+1) * 2;

        return i;
    }
    
    public int getMin() {
        return heap[0].index;
    }
    
    public List<Vertex> removeMin() {
        List<Vertex> queue = new ArrayList<Vertex>();
        for (int i = 1; i < heap.length; i++) {
            queue.add(heap[i]);
        }
        
        return queue;
    }
    
}