package recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutation {

	public static <T> void permute(List<List<T>> store, List<T> elements, int start) {

		if (elements.size() - start <= 1) {
			store.add(new ArrayList<>(elements));
			return;
		}
		for (int i = start; i < elements.size(); i++) {
			swap(elements, start, i);
			permute(store, elements, start + 1);
			swap(elements, start, i);
		}
	}

	private static <T> void swap(List<T> elements, int j, int i) {
		T e1 = elements.get(j);
		T e2 = elements.get(i);
		elements.remove(j);
		elements.add(j, e2);
		elements.remove(i);
		elements.add(i, e1);
	}

	public static void main(String[] args) {
		ArrayList<List<Integer>> store = new ArrayList<>();
		permute(store, new ArrayList<>(Arrays.asList(0, 1, 2)), 0);
		for (List<Integer> list : store) {
			System.out.println(Arrays.toString(list.toArray()));
		}
	}

}
