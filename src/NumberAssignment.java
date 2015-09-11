import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * Created by qux on 9/3/15.
 */
class NumberAssignment {

    public static void main(String args[]) {
        start();
    }

    public static void start() {
        InputReader in = new InputReader(System.in);

        int count = in.readInt();
        int caseNum = 1;
        StringBuilder sb = new StringBuilder("");

        while (caseNum <= count) {
            int[] nums = readIntArray(in, 2);
            int[] data = readIntArray(in, nums[0]);
            sb.append("Case #" + caseNum + ": " + minCost(nums[0], nums[1], data) + "\n");
            caseNum++;
        }

        System.out.print(sb.toString());
    }

    /**
     * main algorithm
     * complexity:
     *  N: the number of data
     *  M: the number of groups
     *  complexity: max {O(N*M), O(N*LgN)}
     *  runtime: 0.122s
     *
     *
     * precondition:
     *   1. It can be proved that the number of the minimum cost must be ordered,
     *      each group just contain a sequential part of the numbers.
     *   2. The difference between the largest and smallest number in a sequence equals
     *      to the sum of the difference of the neighbour number.
     *
     * process:
     *   1. sort the array
     *   2. calc the difference number array of the sorted array, called subArray here
     *   3. find the maximal (m-1) numbers in the subArray
     *   4. the sum of the rest the subArray numbers except the maximal (m-1) ones will be the answer
     *
     * @param n array size
     * @param m group size
     * @param arrays provided data
     * @return
     */
    public static int minCost(int n, int m, int[] arrays) {
        if (n <= m) {
            return 0;
        }

        if (m == 1) {
            return findMaxSub(arrays, n);
        }

        Arrays.sort(arrays);
        int[] subArray = new int[n - 1];
        int subSum = 0;
        for (int i = 0; i < n - 1; i++) {
            subArray[i] = arrays[i + 1] - arrays[i];
            subSum += subArray[i];
        }

        int[] maxNums = new int[m - 1];
        for (int i = 0; i < m - 1; i++) {
            maxNums[i] = subArray[i];
        }

        int minIndex = findMinIndex(maxNums, m - 1);
        for (int i = m - 1; i < n - 1; i++) {
            if (maxNums[minIndex] < subArray[i]) {
                maxNums[minIndex] = subArray[i];
            }
            minIndex = findMinIndex(maxNums, m - 1);
        }
        int maxNumsSum = 0;
        for (int i = 0; i < m - 1; i++) {
            maxNumsSum += maxNums[i];
        }

        return subSum - maxNumsSum;
    }

    /**
     * find the smallest element's index in an array
     * @param array
     * @param size
     * @return
     */
    public static int findMinIndex(int[] array, int size) {
        if (size == 1) {
            return 0;
        }
        int minIndex = 0;
        for (int i = 1; i < size; i++) {
            if (array[minIndex] > array[i]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * calc the difference between the largest and smallest element
     * @param array
     * @param size
     * @return
     */
    public static int findMaxSub(int[] array, int size) {
        int min = array[0];
        int max = array[0];

        for (int i = 0; i < size; i++) {
            if (array[i] < min) {
                min = array[i];
            } else if (array[i] > max) {
                max = array[i];
            }
        }
        return max - min;
    }


    /**
     * input and output tool class copy from network
     * ignore them
     */
    static class InputReader {

        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1)
                throw new InputMismatchException();
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do {
                res.appendCodePoint(c);
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() {
            return readString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);
        }
    }

    public static int[] readIntArray(InputReader in, int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
            array[i] = in.readInt();
        return array;
    }
}
