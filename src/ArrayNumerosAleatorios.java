import java.io.*;

public class ArrayNumerosAleatorios {
    // Bubble sort
    static class BubbleSort { // Hacer la clase estática para poder acceder a sus métodos desde el main
        // An optimized version of Bubble Sort
        static void bubbleSort(int arr[], int n) {
            int i, j, temp;
            boolean swapped;
            for (i = 0; i < n - 1; i++) {
                swapped = false;
                for (j = 0; j < n - i - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        // Swap arr[j] and arr[j+1]
                        temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                        swapped = true;
                    }
                }
                // If no two elements were swapped by inner loop, then break
                if (swapped == false)
                    break;
            }
        }
    }

    static class QuickSort {
        static int partition(int arr[], int low, int high) {
            int pivot = arr[high];
            int i = (low - 1); // index of smaller element
            for (int j = low; j < high; j++) {
                // If current element is smaller than or equal to pivot
                if (arr[j] <= pivot) {
                    i++;
                    // swap arr[i] and arr[j]
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            // swap arr[i+1] and arr[high] (or pivot)
            int temp = arr[i + 1];
            arr[i + 1] = arr[high];
            arr[high] = temp;
            return i + 1;
        }
        static void sort(int arr[], int low, int high) {
            if (low < high) {
                // pi is partitioning index, arr[pi] is now at right place
                int pi = partition(arr, low, high);
                // Recursively sort elements before partition and after partition
                sort(arr, low, pi - 1);
                sort(arr, pi + 1, high);
            }
        }
    }

    static class AVLTree {
        class Node {
            int key, height;
            Node left, right;
            Node(int d) {
                key = d;
                height = 1;
            }
        }
        Node root;
        // A utility function to get height of the tree
        int height(Node N) {
            if (N == null)
                return 0;
            return N.height;
        }
        // A utility function to right rotate subtree rooted with y
        Node rightRotate(Node y) {
            Node x = y.left;
            Node T2 = x.right;
            // Perform rotation
            x.right = y;
            y.left = T2;
            // Update heights
            y.height = Math.max(height(y.left), height(y.right)) + 1;
            x.height = Math.max(height(x.left), height(x.right)) + 1;
            // Return new root
            return x;
        }
        // A utility function to left rotate subtree rooted with x
        Node leftRotate(Node x) {
            Node y = x.right;
            Node T2 = y.left;
            // Perform rotation
            y.left = x;
            x.right = T2;
            // Update heights
            x.height = Math.max(height(x.left), height(x.right)) + 1;
            y.height = Math.max(height(y.left), height(y.right)) + 1;
            // Return new root
            return y;
        }
        // Get Balance factor of node N
        int getBalance(Node N) {
            if (N == null)
                return 0;
            return height(N.left) - height(N.right);
        }
        Node insert(Node node, int key) {
            /* 1. Perform the normal BST rotation */
            if (node == null)
                return (new Node(key));
            if (key < node.key)
                node.left = insert(node.left, key);
            else if (key > node.key)
                node.right = insert(node.right, key);
            else // Equal keys not allowed
                return node;
            /* 2. Update height of this ancestor node */
            node.height = 1 + Math.max(height(node.left), height(node.right));
        /* 3. Get the balance factor of this ancestor node to check whether
           this node became Wunbalanced */
            int balance = getBalance(node);
            // If this node becomes unbalanced, then there are 4 cases
            // Left Left Case
            if (balance > 1 && key < node.left.key)
                return rightRotate(node);
            // Right Right Case
            if (balance < -1 && key > node.right.key)
                return leftRotate(node);
            // Left Right Case
            if (balance > 1 && key > node.left.key) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
            // Right Left Case
            if (balance < -1 && key < node.right.key) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
            /* return the (unchanged) node pointer */
            return node;
        }
        // A utility function to print inorder traversal of the tree.
        // The function also populates nextOrderIndex with inorder traversal of the tree
        void inOrder(Node root, int[] nextOrderIndex, int[] resultArr) {
            if (root != null) {
                inOrder(root.left, nextOrderIndex, resultArr);
                resultArr[nextOrderIndex[0]++] = root.key;
                inOrder(root.right, nextOrderIndex, resultArr);
            }
        }
        // The main function that sorts the given array using AVL sort
        void avlTreeSort(int[] arr) {
            root = null;
            int n = arr.length;
            // Construct AVL tree from the array elements
            for (int i = 0; i < n; i++)
                root = insert(root, arr[i]);
            // Store the inOrder traversal of the AVL tree back to arr
            int[] nextOrderIndex = { 0 };
            inOrder(root, nextOrderIndex, arr);
        }
    }


    static class MergeSort {
        // Merge two subarrays L and M into arr
        static void merge(int arr[], int p, int q, int r) {
            // Create L ← A[p..q] and M ← A[q+1..r]
            int n1 = q - p + 1;
            int n2 = r - q;
            int L[] = new int[n1];
            int M[] = new int[n2];
            for (int i = 0; i < n1; i++)
                L[i] = arr[p + i];
            for (int j = 0; j < n2; j++)
                M[j] = arr[q + 1 + j];
            // Maintain current index of sub-arrays and main array
            int i, j, k;
            i = 0;
            j = 0;
            k = p;
            // Until we reach either end of either L or M, pick larger among
            // elements L and M and place them in the correct position at A[p..r]
            while (i < n1 && j < n2) {
                if (L[i] <= M[j]) {
                    arr[k] = L[i];
                    i++;
                } else {
                    arr[k] = M[j];
                    j++;
                }
                k++;
            }
            // When we run out of elements in either L or M,
            // pick up the remaining elements and put in A[p..r]
            while (i < n1) {
                arr[k] = L[i];
                i++;
                k++;
            }
            while (j < n2) {
                arr[k] = M[j];
                j++;
                k++;
            }
        }
        // Divide the array into two subarrays, sort them and merge them
        static void mergeSort(int arr[], int l, int r) {
            if (l < r) {

                // m is the point where the array is divided into two subarrays
                int m = (l + r) / 2;

                mergeSort(arr, l, m);
                mergeSort(arr, m + 1, r);

                // Merge the sorted subarrays
                merge(arr, l, m, r);
            }
        }
    }

    // Function to print an array
    public static void printArray(int arr[], int size) {
        int i;
        for (i = 0; i < size; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
    }



    public static void main(String[] args) {
        int[] numerosAleatorios = {
                587, 254, 503, 818, 237, 215, 122, 653, 774, 202, 890, 657, 109, 859, 305, 789, 141, 788, 147, 598,
                227, 133, 423, 947, 969, 27, 792, 572, 908, 339, 746, 243, 181, 279, 428, 96, 647, 545, 872, 174,
                479, 934, 25, 931, 922, 678, 110, 292, 617, 104, 741, 726, 823, 503, 736, 581, 63, 852, 470, 121,
                419, 866, 283, 328, 336, 454, 196, 461, 473, 77, 764, 612, 468, 855, 962, 798, 287, 25, 394, 204,
                989, 293, 305, 361, 307, 154, 319, 803, 108, 538, 396, 227, 262, 949, 69, 123, 18, 255, 979, 493,
                657, 581, 71
        };

        System.out.println("Números aleatorios:");
        for (int i = 0; i < numerosAleatorios.length; i++) {
            System.out.println(numerosAleatorios[i]);
        }

        // Ordenar el arreglo usando BubbleSort
        long startTime = System.nanoTime();
        BubbleSort.bubbleSort(numerosAleatorios, numerosAleatorios.length);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("\nNúmeros ordenados con BubbleSort:");
        System.out.println("Tiempo de ejecución BubbleSort: " + duration + " nanosegundos");
        printArray(numerosAleatorios, numerosAleatorios.length);

        // Crear una copia del arreglo original
        int[] copiaNumeros = numerosAleatorios.clone();

        // Ordenar la copia del arreglo usando MergeSort
        startTime = System.nanoTime();
        MergeSort.mergeSort(copiaNumeros, 0, copiaNumeros.length - 1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("\nNúmeros ordenados con MergeSort:");
        System.out.println("Tiempo de ejecución MergeSort: " + duration + " nanosegundos");
        printArray(copiaNumeros, copiaNumeros.length);

        //Crear copia del arreglo original para usar el algoritmo QuickSort
        int[] copiaNumerosQS = numerosAleatorios.clone();
        // Ordenar el arreglo usando QuickSort
        startTime = System.nanoTime();
        QuickSort.sort(copiaNumerosQS, 0, copiaNumerosQS.length - 1);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("\nNúmeros ordenados con QuickSort:");
        System.out.println("Tiempo de ejecución QuickSort: " + duration + " nanosegundos");
        printArray(copiaNumerosQS, copiaNumerosQS.length);

        // Crear copia del arreglo original para usar el algoritmo AVL Tree Sort
        int[] copiaNumerosAVL = numerosAleatorios.clone();

        // Crear objeto de AVLTree y llamar al método avlTreeSort
        startTime = System.nanoTime();
        AVLTree avlTree = new AVLTree();
        avlTree.avlTreeSort(copiaNumerosAVL);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        System.out.println("\nNúmeros ordenados con AVL Tree Sort:");
        System.out.println("Tiempo de ejecución AVL Tree Sort: " + duration + " nanosegundos");
        printArray(copiaNumerosAVL, copiaNumerosAVL.length);


    }
}



