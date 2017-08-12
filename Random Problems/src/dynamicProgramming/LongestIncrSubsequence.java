package dynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongestIncrSubsequence {

	public static <T extends Comparable<T>> List<T> longestIncreasingSubsequence(List<T> list) {
		// Dynamic programming bottom-up:
		// --Longest increasing subsequence ending at n must also contain the longest
		// --subsequence ending at n-1, n-2, .. if element[n-1], [n-2], .. < element[n].
		return lis(list);
	}

	private static <T extends Comparable<T>> List<T> lis(List<T> list) {
		List<T>[] arr = initEachEndingSubsequence(list);
		int maxlenIndex = 0;
		for (int i = 1; i < list.size(); i++)
			for (int j = 0; j < i; j++)
				if (list.get(j).compareTo(list.get(i)) < 0 && arr[j].size() + 1 > arr[i].size()) {
					// create subsequence ending at i with elements from j
					createNewLongestSubsequenceAtIwithElementsOfJ(list, arr, i, j);
					if (arr[i].size() > arr[maxlenIndex].size())
						maxlenIndex = i;
				}
		return arr[maxlenIndex];
	}

	private static <T extends Comparable<T>> void createNewLongestSubsequenceAtIwithElementsOfJ(List<T> list,
			List<T>[] arr, int i, int j) {
		ArrayList<T> temp = new ArrayList<>(arr[j]);
		temp.add(list.get(i));
		arr[i] = temp;
	}

	private static <T> List<T>[] initEachEndingSubsequence(List<T> list) {
		List<T>[] arr = (List<T>[]) new List[list.size()];
		int i = 0;
		for (T e : list) {
			List<T> aList = new ArrayList<>();
			aList.add(e);
			arr[i++] = aList;
		}
		return arr;
	}

	public static void main(String[] args) {
		List<Integer> asList = Arrays.asList(10, 22, 9, 33, 21, 50, 41, 60);
		System.out.println(Arrays.toString(longestIncreasingSubsequence(asList).toArray()));
	}
}
