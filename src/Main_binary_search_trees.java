import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
}

public class Main_binary_search_trees {

	public static void main(String[] args) {

		Leaf leaf = Leaf.createFromFile("data_examples_07/input_10a.txt");
		
	}
}
