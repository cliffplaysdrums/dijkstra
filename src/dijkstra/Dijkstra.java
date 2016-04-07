package dijkstra;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An implementation of Dijkstra's Algorithm for single-source shortest path
 * Runtime goal: O(ElogV)
 * @author Cliff Chandler
 */
public class Dijkstra {
    
    public static final int DEFAULT_VALUE = -1;
    public static final int sourceVertex = 0;
    static int last = sourceVertex;

    public static void main(String[] args) {
        int numberVertices = 0, numberEdges = 0;
        int[] input;    
        int[][] vertexMatrix = null;
        Vertex[] vertexArray = null;
        String fileName = "C:\\Users\\Cliff\\Desktop\\input1.txt";
        
        try {
            FileReader in = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(in);
            String line;
            Scanner scanner = new Scanner(new File(fileName));

            //get n and m from line 1
            line = buffer.readLine();
            String[] n = line.split(" ");
            String[] m = n[1].split("=");
            numberEdges = Integer.parseInt(m[1]);
            n = n[0].split("=");
            numberVertices = Integer.parseInt(n[1]);
            vertexMatrix = new int[numberVertices][numberVertices];
            vertexArray = new Vertex[numberVertices];
            
            new initMatrix(vertexMatrix, DEFAULT_VALUE, numberVertices, numberVertices);
            new initArray(vertexArray, DEFAULT_VALUE, numberVertices);
            
            //begin reading the input file
            int u = 0;
            while ((line = buffer.readLine()) != null) {
                int counter = 0;
                scanner = new Scanner(line);
                int temp1 = 0, temp2 = 0;
                int v, w;
                
                //determine how many items are in the row and temporarily store
                if (scanner.hasNextInt() == true) {
                    counter = 1;
                    temp1 = scanner.nextInt();
                    if (scanner.hasNextInt() == true) {
                        counter = 2;
                        temp2 = scanner.nextInt();
                    }
                }
                
                if (counter == 1) {
                    u = temp1;
                }
                if (counter == 2) {
                    v = temp1;
                    w = temp2;
                    vertexMatrix[u][v] = w; //cost of edge uv
                }
            }
            
            in.close();
            buffer.close();
            scanner.close();
            
            /* At this point, the file has been read into vertexMatrix[u][v] where
             * the value at vertexMatrix[u][v] is the weight of that edge if it exists
             * The vertexArray contains each v. v.predecessor is the other vertex u
             * in edge uv that makes the current shortest path. v.cost is the current
             * cost to get to v from the source. To relax, compare v.cost against
             * vertexMatrix[u][v] + u.cost
            */
            
            //put source vertex in queue
            //while queue is not empty
            //
            //for each edge uv connecting to u
            //find all path costs from u to v
            //if the tail (v) of any of these paths is undisovered or less than the current cost
            //update its cost and predecessor, add v to a queue (binary heap)
            //
            //extract the minimum from the queue
            //repeat while loop
            
            List<Vertex> queue = new ArrayList<Vertex>();
            queue.add(vertexArray[sourceVertex]);            
            u = sourceVertex;
            
            while (!queue.isEmpty()) {
                int currentSP = vertexArray[u].cost; //current shortest path
                for (int v = 0; v < numberVertices; v++) {
                    if (vertexMatrix[u][v] != DEFAULT_VALUE) { //if edge exists
                        int uvWeight = vertexMatrix[u][v];
                        //relax
                        if (currentSP + uvWeight < vertexArray[v].cost || 
                                vertexArray[v].cost == DEFAULT_VALUE) {
                            vertexArray[v].cost = currentSP + uvWeight;
                            vertexArray[v].predecessor = u;
                            queue.add(vertexArray[v]);
                        }
                    }
                    //System.out.println(vertexMatrix[u][v]); WORKS
                }
                Heap heap = new Heap(queue);
                u = heap.getMin();
                queue = heap.removeMin();
                last = u;
            }
            
            int p = 393;
            //System.out.println(vertexArray[last].cost);
            while(p != vertexArray[p].predecessor) {
                if (vertexArray[p].predecessor == DEFAULT_VALUE) {
                    System.out.println("No path exists");
                    p = 0;
                } else {
                    System.out.println(p + " " + vertexArray[p].predecessor);
                    p = vertexArray[p].predecessor;
                }
            }

        } catch (IOException e) {
            //TODO
        }
        
    } //end method main(String [])
    
} //end class Dijkstra

class initMatrix {
    public initMatrix(int[][] matrix, final int DEFAULT_VALUE, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = DEFAULT_VALUE;
            }
        }
    }
}

class initArray {
    public initArray(Vertex[] vertexArray, final int DEFAULT_VALUE, int size) {
        for (int i = 1; i < size; i++) {
            vertexArray[i] = new Vertex();
            vertexArray[i].predecessor = DEFAULT_VALUE;
            vertexArray[i].cost = DEFAULT_VALUE;
            vertexArray[i].index = i;
        }
        
        vertexArray[0] = new Vertex();
        vertexArray[0].cost = 0;
        vertexArray[0].predecessor = 0;
        vertexArray[0].index = 0;
    }
}

class Vertex {
    int predecessor;
    int cost;
    int index;
}

