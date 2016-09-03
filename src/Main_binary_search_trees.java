import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class Leaf {
	Leaf parent = null;
	Leaf left = null;
	Leaf right = null;
	int value;

	Leaf() {

	}

	Leaf(int i) {
		value = i;
	}

	void addLeft(int i) {
		left = new Leaf(i);
		left.parent = this;
	}

	void addRight(int i) {
		right = new Leaf(i);
		right.parent = this;
	}

	void fixTree() {
		ArrayList<Integer> list = Main_binary_search_trees.sort(inorderTreeWalk());
		fixTreeUtil(list, this);
	}

	void fixTreeUtil(List<Integer> list, Leaf m) {
		if (m != null) {
			fixTreeUtil(list, m.left);
			m.value = list.remove(0);
			fixTreeUtil(list, m.right);
		}
	}

	static Leaf createFromFile(String file) {
		String str = null;
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			str = in.readLine();
		} catch (IOException e) {
			System.out.println("IOExc " + e);
			Leaf l = new Leaf(); // need fix
			return l;
		}
		Leaf m = null;
		try (Scanner sc = new Scanner(str)) {
			Leaf cur = null;
			if (sc.hasNextInt())
				cur = m = new Leaf(sc.nextInt());
			boolean b = false;
			while (sc.hasNextInt()) {
				int i;
				if (!b && (i = sc.nextInt()) != 0) {
					cur.addLeft(i);
					cur = cur.left;
				} else if ((i = sc.nextInt()) != 0) {
					cur.addRight(i);
					cur = cur.right;
					b = false;
				} else {
					Leaf top = cur.parent;
					while (top != null && top.left != cur) {
						cur = cur.parent;
						top = cur.parent;
					}
					cur = top;
					b = true;
				}
			}
		}
		return m;
	}

	ArrayList<Integer> inorderTreeWalk() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		inorderTreeWalkUtil(res, this);
		return res;
	}

	ArrayList<Integer> inorderTreeWalkMod() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		inorderTreeWalkUtilMod(res, this);
		return res;
	}

	void inorderTreeWalkUtil(List<Integer> list, Leaf m) {
		if (m != null) {
			inorderTreeWalkUtil(list, m.left);
			list.add(m.value);
			inorderTreeWalkUtil(list, m.right);
		}
	}

	void inorderTreeWalkUtilMod(List<Integer> list, Leaf m) {
		if (m != null) {
			inorderTreeWalkUtilMod(list, m.left);
			if (m.left == null && m.right == null)
				list.add(m.value);
			inorderTreeWalkUtilMod(list, m.right);
		}
	}

	class Integ {
		int i;

		Integ(int i) {
			this.i = i;
		}
	}

	List<Integer> inorderTreeWalkWays(int searched) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		int i = inorderTreeWalkUtilWays(list, this, searched);
		List<Integer> res = list.subList(list.size() - i, list.size());
		return res;
	}

	int inorderTreeWalkUtilWays(LinkedList<Integer> list, Leaf m, int searched) {
		if (m != null) {
			list.add(m.value);
			int i = evaluate(searched, list);
			if (i != -1) {
				return i;
			}
			i = inorderTreeWalkUtilWays(list, m.left, searched);
			if (i != -1)
				return i;
			i = inorderTreeWalkUtilWays(list, m.right, searched);
			if (i != -1)
				return i;
			list.removeLast();
			return -1;
		}
		return -1;
	}

	static int evaluate(int searched, LinkedList<Integer> list) {
		int sum = 0;
		int i = list.size() - 1;
		while (sum < searched && i >= 0) {
			sum += list.get(i);
			i--;
		}
		if (sum == searched)
			return list.size() - i - 1;
		else
			return -1;
	}
}

public class Main_binary_search_trees {
	static ArrayList<Integer> sort(ArrayList<Integer> list) {
		for (int i = 1; i < list.size(); i++)
			for (int j = i - 1; j >= 0; j--) {
				if (list.get(j + 1) < list.get(j)) {
					int temp = list.get(j + 1);
					list.set(j + 1, list.get(j));
					list.set(j, temp);
				}
			}
		return list;
	}

	public static void main(String[] args) {

		Leaf leaf = Leaf.createFromFile("data_examples_07/input_1000a.txt");
		leaf.fixTree();
		System.out.println(leaf.inorderTreeWalkWays(1940));
	}
}
