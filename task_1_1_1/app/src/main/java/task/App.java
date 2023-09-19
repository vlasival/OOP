
package task;

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
        int largest = i; // Инициализируем наибольший элемент как корень
        int left = 2 * i + 1; // Левый дочерний элемент
        int right = 2 * i + 2; // Правый дочерний элемент

        // Если левый дочерний элемент больше корня
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // Если правый дочерний элемент больше, чем наибольший элемент на данный момент
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
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
}
