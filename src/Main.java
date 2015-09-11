import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by qux on 9/12/15.
 */
class Main {

    public static void main(String args[]) {
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void start() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer token;
        String line;
        int caseCount = 0;
        while ((line = br.readLine()) != null) {
            ++caseCount;
            System.out.println("Case " + caseCount + ":");
            int numberOfHouses = Integer.parseInt(line);
            int[] xs = new int[numberOfHouses];
            int[] ys = new int[numberOfHouses];
            int num = 0;
            while (num < numberOfHouses) {
                token = new StringTokenizer(br.readLine());
                xs[num] = Integer.parseInt(token.nextToken());
                ys[num] = Integer.parseInt(token.nextToken());
                ++num;
            }
            token = new StringTokenizer(br.readLine());
            Round a = new Round(Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()));
            Round b = new Round(Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()));
            int query = Integer.parseInt(token.nextToken());

            int qNum = 0;
            while (qNum < query) {
                token = new StringTokenizer(br.readLine());
                int r1 = Integer.parseInt(token.nextToken());
                int r2 = Integer.parseInt(token.nextToken());
                a.setR(r1);
                b.setR(r2);
                System.out.println(solution(xs, ys, a, b));
                ++qNum;
            }
            br.readLine();
        }

    }

    public static int solution(int[] xs, int[] ys, Round a, Round b) {
        double Dab = distance(a.x, a.y, b.x, b.y);
        int result;
        if (Dab > (a.r + b.r)) {
            result = calcNoInterSect(xs, ys, xs.length, a, b);
        } else if (Dab < Math.abs(a.r - b.r)) {
            result = calcContain(xs, ys, xs.length, a.r < b.r ? a : b, a.r > b.r ? a : b);
        } else {
            result = calcInterSect(xs, ys, xs.length, a, b);
        }
        return result > 0 ? result : 0;
    }

    public static int calcNoInterSect(int[] xs, int[] ys, int size, Round a, Round b) {
        int result = 0;
        for (int i = 0; i < size; ++i) {
            if ((!a.contain(xs[i], ys[i])) && (!(b.contain(xs[i], ys[i])))) {
                ++result;
            }
        }
        return result;
    }

    public static int calcContain(int[] xs, int[] ys, int size, Round small, Round big) {
        int bothContain = 0;
        int outSide = 0;

        for (int i = 0; i < size; ++i) {
            if (small.contain(xs[i], ys[i])) {
                ++bothContain;
            } else if (!big.contain(xs[i], ys[i])) {
                ++outSide;
            }
        }
        return outSide - bothContain;
    }

    public static int calcInterSect(int[] xs, int[] ys, int size, Round a, Round b) {
        int bothContain = 0;
        int outSide = 0;

        for (int i = 0; i < size; ++i) {
            boolean aContain = a.contain(xs[i], ys[i]);
            boolean bContain = b.contain(xs[i], ys[i]);
            if (aContain && bContain) {
                ++bothContain;
            } else if (!aContain && !bContain) {
                ++outSide;
            }
        }

        return outSide - bothContain;
    }


    public static double distance(int ax, int ay, int bx, int by) {
        return Math.sqrt(Math.pow(ax - bx, 2) + Math.pow(ay - by, 2));
    }

    static class Round {
        int x;
        int y;
        int r;
        int left;
        int right;
        int top;
        int bottom;
        long powR;

        public Round(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setR(int r) {
            this.r = r;
            this.left = x - r;
            this.right = x + r;
            this.top = y + r;
            this.bottom = y - r;
            powR = r * r;
        }

        boolean contain(int x, int y) {
            if (x <= left || x >= right || y >= top || y <= bottom) {
                return false;
            }

            if (Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2) <= powR) {
                return true;
            }

            return false;
        }

        @Override
        public String toString() {
            return "Round{" +
                    "x=" + x +
                    ", y=" + y +
                    ", r=" + r +
                    ", left=" + left +
                    ", right=" + right +
                    ", top=" + top +
                    ", bottom=" + bottom +
                    ", powR=" + powR +
                    '}';
        }
    }
}
