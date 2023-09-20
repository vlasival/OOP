
package task;

import java.util.Scanner;
/**
 * Класс для сортировки массива методом пирамиды (heap sort).
 */
public class App {
    /**
     * Сортирует массив методом пирамиды (heap sort).
     *
     * @param arr Массив, который требуется отсортировать.
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Построение max-кучи
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Извлечение элементов из кучи и построение отсортированного массива
        for (int i = n - 1; i >= 0; i--) {
            // Перемещаем текущий корень в конец массива
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Вызываем heapify для уменьшенной кучи
            heapify(arr, i, 0);
        }
    }

    /**
     * Преобразует поддерево с корнем в элементе i в max-кучу.
     *
     * @param arr Массив, содержащий элементы кучи.
     * @param n   Размер кучи.
     * @param i   Индекс элемента, с которого начинается преобразование max-кучи.
     */
    public static void heapify(int[] arr, int n, int i) {
        int largest = i; 
        int leftChild = 2 * i + 1; 
        int rightChild = 2 * i + 2; 

        // Если левый дочерний элемент больше корня
        if (leftChild < n && arr[leftChild] > arr[largest]) {
            largest = leftChild;
        }

        // Если правый дочерний элемент больше, чем наибольший элемент на данный момент
        if (rightChild < n && arr[rightChild] > arr[largest]) {
            largest = rightChild;
        }

        // Если наибольший элемент не корень
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Рекурсивно проходим по поддереву
            heapify(arr, n, largest);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите размер массива: ");
        int size = scanner.nextInt();

        int[] array = new int[size];

        System.out.println("Введите элементы массива:");


        for (int i = 0; i < size; i++) {
            System.out.print("Элемент " + (i + 1) + ": ");
            array[i] = scanner.nextInt();
        }

        heapSort(array);

        for (int number : array) {
            System.out.print(number + " ");
        }
        System.out.print("\n");
    }
}
