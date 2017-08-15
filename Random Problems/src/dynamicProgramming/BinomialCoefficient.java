package dynamicProgramming;

public class BinomialCoefficient {

	static int count = 0;

	public static int binomialCoefficient(int n, int k) {

		// binomial coefficients are given by: c(n, k)
		// C(n, k) = C(n - 1, k - 1) + C(n - 1, k)
		// C(0, n) = 1
		// C(n, n) = 1
		// C(n, 0) = 1
		int[][] store = new int[n + 1][k + 1];
		count = 0;
//		 return bcRecursive(n, k, store);
		return bc(n, k, store);
	}

	private static int bcRecursive(int n, int k, int[][] store) {
		System.out.println("Recursive call: " + ++count);
		if (n == k || n == 0 || k == 0)
			return 1;
		return bcRecursive(n - 1, k - 1, store) + bcRecursive(n - 1, k, store);
	}

	private static int bc(int n, int k, int[][] store) {
		System.out.println(" call: " + ++count);
		if (cnkFromArray(n, k, store) == 0) {
//			System.out.println("Eval: " + n +" " + k);
			if (n == k || n == 0 || k == 0) {
				store[n][k] = 1;
			} else {
				if (cnkFromArray(n - 1, k - 1, store) == 0 || cnkFromArray(n - 1, k, store) == 0) {
					store[n][k] = bc(n - 1, k - 1, store) + bc(n - 1, k, store);
				}
			}
		}
		return cnkFromArray(n, k, store);
	}

	private static int cnkFromArray(int n, int k, int[][] store) {
		return store[n][k];
	}

	public static void main(String[] args) {
		System.out.println("Result-> " + binomialCoefficient(8, 5));
		/*System.out.println("Result-> " + binomialCoefficient(3, 3));
		System.out.println("Result-> " + binomialCoefficient(4, 3));
		System.out.println("Result-> " + binomialCoefficient(2, 1));*/
	}
}
