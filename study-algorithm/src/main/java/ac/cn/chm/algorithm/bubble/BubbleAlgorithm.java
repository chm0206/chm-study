package ac.cn.chm.algorithm.bubble;

/**
 * 冒泡算法
 */
public class BubbleAlgorithm {
    public static void main(String[] args) {
        Integer[] arr = {1, 4, 3, 5, 8, 6, 12, 7, 13, 63, 2, 45, 14};
        bubble3(arr);
        for (Integer a : arr
        ) {
            System.out.print(a + "-->");
        }
        System.out.println();
    }

    public static void bubble1(Integer[] arr) {
        System.out.println("arr.length=" + arr.length);
        int count = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1; j++) {
                count++;
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j+1);
                }
            }
        }
        System.out.println("count=" + count);
    }

    //{1, 4, 3, 5, 8, 6, 12, 7, 13, 63, 2, 45, 14}
    //i=13-1=12,pos
    public static void bubble2(Integer[] arr) {
        System.out.println("arr.length=" + arr.length);
        int count = 0;
        int i = arr.length - 1;
        while (i > 0) {
            int pos = 0;
            for (int j = 0; j < i; j++) {
                count++;
                if (arr[j] > arr[j + 1]) {
                    pos = j;
                    swap(arr, j, j+1);
                }
            }
            i = pos;
        }
        System.out.println("count=" + count);
    }

    public static void bubble3(Integer[] array) {
        int arrayLength = array.length;
        Integer count = 0;
        int preIndex = 0;
        int backIndex = arrayLength - 1;
        while (preIndex < backIndex) {
            for (int i = preIndex + 1; i < arrayLength; i++) {
                count++;
                if (array[preIndex] > array[i]) {
                    swap(array, preIndex, i);
                }
            }
            preIndex++;

            if (preIndex >= backIndex) {
                break;
            }
            for (int i = backIndex - 1; i >= 0; i--) {
                count++;
                if (array[i] > array[backIndex]) {
                    swap(array, i, backIndex);
                }
            }
            backIndex--;
        }
        System.out.println("count=" + count);
    }

    /**
     * 我还有好多好多的东西要学习
     * @param arr 数组啊
     * @param index 交换的参数1
     * @param index1 交换的参数2
     */
    public static void swap(Integer[] arr, int index, int index1) {
        int tmp = arr[index];
        arr[index] = arr[index1];
        arr[index1] = tmp;
    }
}
