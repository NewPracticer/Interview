import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Sort {
    /**
     * 插入排序
     * @param A
     */
    public void sort1(int[] A){
        for(int i = 1; i < A.length; i++) {
            // 将A[i] 插入在卡片0，到卡片i之间
            // j代表抓到的牌，先放到最右侧，不断交换到对应的位置
            int c = A[i];
            int j = i;
            for(; j > 0 && A[j-1] > c;j--) {
                A[j] = A[j-1];
            }
            A[j] = c;
        }
    }

    /**
     * 选择排序
     * @param A
     */
    public void sort3(int[] A) {
        for(int i = A.length - 1; i >= 0; i--) {
            // 0 - A[i]
            int j = maxIndex(A, 0, i+1);
            Helper.swap(A, i, j);
        }
    }

    static private int maxIndex(int[] A, int i, int j) {
        int max = Integer.MIN_VALUE;

        int maxIndex = j-1;
        for(int k = j-1; k >= i; k--) {
            if(max < A[k]) {
                max = A[k];
                maxIndex = k;
            }
        }
        return maxIndex;
    }

    /**
     * 冒泡排序
     * @param A
     */
    public void sort2(int[] A) {
        for(int i = A.length - 1; i >=0; i--) {
            // 找到0-i间的最大元素放到A[i]
            bubble(A, 0, i+1);
        }
    }

    private void bubble(int[] A, int i, int j) {
        for(int k = 0; k < j - 1; k++) {
            if(A[k] > A[k+1]) {
                Helper.swap(A, k, k+1);
            }
        }
    }

    /**
     * 归并排序
     * @param A
     * @param l
     * @param r
     */
    private void mergeSort(int[] A, int l, int r) {
        if(r - l <= 1) {
            return;
        }
        int mid = (l+r+1)/2;
        mergeSort(A, l, mid);
        mergeSort(A, mid, r);
        merge(A, l, mid, r);

    }

    private void merge(int[] A, int l, int mid, int r) {
        int[] B = Arrays.copyOfRange(A, l, mid+1);
        int[] C = Arrays.copyOfRange(A, mid, r+1);

        B[B.length-1] = C[C.length - 1] = Integer.MAX_VALUE;
        int i = 0, j = 0;
        for(int k = l; k < r; k++) {
            if(B[i] < C[j]) {
                A[k] = B[i++];
            } else {
                A[k] = C[j++];
            }
        }
    }

    /**
     * 快速排序
     * @param A
     * @param l
     * @param r
     */
    private void quickSort(int[] A, int l, int r) {

        if(r - l <= 1) {
            return;
        }
        // 选择最左边的元素构造子问题集合
        // 小于x的放到左边，大于x的放到右边
        // int x = A[l];
        // i代表x的位置
        int i = partition(A, l, r);
        // 省略计算x所在位置i
        // 以及将所有小于x的元素放到左边，大于x元素放到右边的
        quickSort(A, l, i);
        quickSort(A, i+1, r);

    }

    private int partition(int[] A, int l, int r) {
        int x = A[l];

        int i = l + 1;
        int j = r;

        while(i != j) {
            if(A[i] < x) {
                i++;
            } else {
                Helper.swap(A, i, --j);
            }
        }
        Helper.swap(A, i-1, l);
        return i-1;

    }

    /**
     * 桶排序
     * @param A
     * @return
     */
    public List<Integer> sort4(List<Integer> A) {
        int k = 100;
        ArrayList<LinkedList<Integer>> buckets = new ArrayList<LinkedList<Integer>>();
        ArrayList<Integer> list = new ArrayList<Integer>();

        for(int i = 0; i < k; i++) {
            buckets.add(new LinkedList<Integer>());
        }

        for(int item : A) {
            buckets.get(item % k).add(item);
        }

        for(LinkedList<Integer>  bucket : buckets) {
            list.addAll(bucket);
        }

        return list;

    }


}
