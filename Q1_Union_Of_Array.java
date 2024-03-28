//Problem statement
//        Given an array ‘ARR’, partition it into two subsets (possibly empty) such that their union is the original array. Let the sum of the elements of these two subsets be ‘S1’ and ‘S2’.
//
//        Given a difference ‘D’, count the number of partitions in which ‘S1’ is greater than or equal to ‘S2’ and the difference between ‘S1’ and ‘S2’ is equal to ‘D’. Since the answer may be too large, return it modulo ‘10^9 + 7’.
//
//        If ‘Pi_Sj’ denotes the Subset ‘j’ for Partition ‘i’. Then, two partitions P1 and P2 are considered different if:
//
//        1) P1_S1 != P2_S1 i.e, at least one of the elements of P1_S1 is different from P2_S2.
//        2) P1_S1 == P2_S2, but the indices set represented by P1_S1 is not equal to the indices set of P2_S2. Here, the indices set of P1_S1 is formed by taking the indices of the elements from which the subset is formed.
//        Refer to the example below for clarification.
//        Note that the sum of the elements of an empty subset is 0.
//
//        For example :
//        If N = 4, D = 3, ARR = {5, 2, 5, 1}
//        There are only two possible partitions of this array.
//        Partition 1: {5, 2, 1}, {5}. The subset difference between subset sum is: (5 + 2 + 1) - (5) = 3
//        Partition 2: {5, 2, 1}, {5}. The subset difference between subset sum is: (5 + 2 + 1) - (5) = 3
//        These two partitions are different because, in the 1st partition, S1 contains 5 from index 0, and in the 2nd partition, S1 contains 5 from index 2.
//        Input Format :
//        The first line contains a single integer ‘T’ denoting the number of test cases, then each test case follows:
//
//        The first line of each test case contains two space-separated integers, ‘N’ and ‘D,’ denoting the number of elements in the array and the desired difference.
//
//        The following line contains N integers denoting the space-separated integers ‘ARR’.
//        Output Format :
//        For each test case, find the number of partitions satisfying the above conditions modulo 10^9 + 7.
//        Output for each test case will be printed on a separate line.
package March_27_Assignment;
import java.util.Scanner;
public class Q1_Union_Of_Array {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of test cases: ");
        int testCases = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        // Initialize arr as a 3D array
        int[][][] arr = new int[testCases][][];

        for (int i = 0; i < testCases; i++) {
            System.out.println("Test Case " + (i + 1));
            System.out.print("Enter the size of the array: ");
            int size = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            int[][] testCase = new int[size][2];

            // Input array elements and D value for each array
            for (int j = 0; j < size; j++) {
                System.out.print("Enter space-separated array elements and D value for array " + (j + 1) + ": ");
                String[] input = scanner.nextLine().split(" ");
                testCase[j][0] = Integer.parseInt(input[0]);
                testCase[j][1] = Integer.parseInt(input[1]);
            }

            // Assign testCase to arr
            arr[i] = testCase;

            // Calculate and print result for each test case
            System.out.println("Result for Test Case " + (i + 1) + ": " + countPartitions(arr[i]));
            System.out.println();
        }

        scanner.close();
    }

    // Method to count partitions for each test case
    public static int countPartitions(int[][] testCase) {
        int MOD = 1000000007;
        int totalPartitions = 0;

        int[] nums = testCase[0];
        int n = nums.length;
        int D = testCase[1][1];
        int s = 0;
        // Calculate the sum of elements in the array
        for (int num : nums) {
            s += num;
        }

        // Dynamic programming array
        int[][][] dp = new int[n + 1][s + 1][2];
        dp[0][0][0] = 1;
        dp[0][0][1] = 1;

        // Dynamic programming to calculate number of partitions
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= s; j++) {
                for (int k = 0; k < 2; k++) {
                    dp[i + 1][j][k] = dp[i][j][k];
                    if (j - nums[i] >= 0) {
                        dp[i + 1][j][k] = (dp[i + 1][j][k] + dp[i][j - nums[i]][k ^ 1]) % MOD;
                    }
                }
            }
        }

        // Count the number of partitions with difference up to D
        int count = 0;
        for (int diff = 0; diff <= D; diff++) {
            count = (count + dp[n][s][0]) % MOD;
            s++;
        }

        totalPartitions += count;
        totalPartitions %= MOD;

        return totalPartitions;
    }
}
