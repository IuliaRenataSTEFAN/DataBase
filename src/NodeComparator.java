import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

	// ajuta la sortarea descrescatoare dupa marime
	// crescatoare dupa index in caz de egalitate
	@Override
	public int compare(Node nod1, Node nod2) {

		if (nod1.getSize() < nod2.getSize()) {
			return 1;
		} else if (nod1.getSize() > nod2.getSize()) {
			return -1;
		} else {
			if (nod1.getIndex() < nod2.getIndex()) {
				return -1;
			} else if (nod1.getIndex() > nod2.getIndex()) {
				return 1;
			}
		}
		return 0;
	}

}
