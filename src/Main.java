import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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
            int numberOfHouses = Integer.parseInt(line);
            if (numberOfHouses == 0) {
                break;
            }
            ++caseCount;
            System.out.println("Case " + caseCount + ":");
            int[] xs = new int[numberOfHouses];
            int[] ys = new int[numberOfHouses];
            for (int i = 0; i < numberOfHouses; ++i) {
                token = new StringTokenizer(br.readLine());
                xs[i] = Integer.parseInt(token.nextToken());
                ys[i] = Integer.parseInt(token.nextToken());
            }
            token = new StringTokenizer(br.readLine());
            int ax = Integer.parseInt(token.nextToken());
            int ay = Integer.parseInt(token.nextToken());
            int bx = Integer.parseInt(token.nextToken());
            int by = Integer.parseInt(token.nextToken());
            int query = Integer.parseInt(token.nextToken());

            int[] distanceCountA = new int[13001];
            int[] distanceCountB = new int[13001];

            for (int i = 0; i < numberOfHouses; ++i) {
                int x = xs[i];
                int y = ys[i];
                int index = (int) Math.ceil(Math.sqrt((ax - x) * (ax - x) + (ay - y) * (ay - y)));
                if (index <= 13000) {
                    distanceCountA[index] = distanceCountA[index] + 1;
                }
                index = (int) Math.ceil(Math.sqrt((bx - x) * (bx - x) + (by - y) * (by - y)));
                if (index <= 13000) {
                    distanceCountB[index] = distanceCountB[index] + 1;
                }
            }

            int[] Ra = new int[query];
            int[] Rb = new int[query];
            for (int i = 0; i < query; ++i) {
                token = new StringTokenizer(br.readLine());
                Ra[i] = Integer.parseInt(token.nextToken());
                Rb[i] = Integer.parseInt(token.nextToken());
            }
            int[] sortedA = Arrays.copyOf(Ra, query);
            Arrays.sort(sortedA);
            int[] sortedB = Arrays.copyOf(Rb, query);
            Arrays.sort(sortedB);

            HashMap<Integer, Integer> countMapA = calCount(distanceCountA, sortedA);
            HashMap<Integer, Integer> countMapB = calCount(distanceCountB, sortedB);

            for (int i = 0; i < query; ++i) {
                int result = numberOfHouses - countMapA.get(Ra[i]) - countMapB.get(Rb[i]);
                if (result > 0) {
                    System.out.println(result);
                } else {
                    System.out.println(0);
                }
            }

        }
    }

    public static HashMap<Integer, Integer> calCount(int[] distanceCount, int[] sortedR) {
        int length = sortedR.length;
        int distanceStartIndex = 0;
        int sum = 0;
        HashMap<Integer, Integer> result = new HashMap<>();
        for (int i = 0; i < length; ++i) {
            int end = sortedR[i];
            for (int j = distanceStartIndex; j <= end; ++j) {
                sum += distanceCount[j];
            }
            result.put(end, sum);
            distanceStartIndex = end;
        }
        return result;
    }
}
