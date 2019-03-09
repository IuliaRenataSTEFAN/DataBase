import java.util.ArrayList;

public class Node {
	// un nod este un vector de instante
	private ArrayList<Instance> instanceArray;
	private int index;

	// constructor
	public Node(int index) {
		this.index = index;
		instanceArray = new ArrayList<Instance>();
	}

	public int getSize() {
		return instanceArray.size();
	}

	public int getIndex() {
		return index;
	}

	public void addInstance(Instance instanceCopy) {
		instanceArray.add(instanceCopy);

	}

	// stergem instanta cautata din nod, daca exista
	public boolean delete(String entityName, String primaryKey) {
		// parcurgem instantele
		for (Instance instance : instanceArray) {
			// daca entitatea instante cautate are acelasi nume cu entitatea instantei
			// curente
			if (entityName.equals(instance.getEntityName())) {
				// calculam primary key a instantei curente
				String instancePrimaryKey = String.valueOf(instance.getPrimaryKey());
				// comparam cele doua chei
				if (primaryKey.equals(instancePrimaryKey)) {
					// daca sunt egale stergem instanta
					instanceArray.remove(instance);
					return true;
				}
			}
		}
		return false;
	}

	// afiseaza continutul unui nod
	// numele atributelor este stocat doar in entitati
	// deci avem nevoie de vectorul de entitati
	public void displayNode(ArrayList<Entity> entityArray) {
		System.out.println("Nod" + index);
		// afisarea se face de la coada la cap
		// primul element afisat trebuie sa fie ultimul element adaugat
		for (int i = instanceArray.size() - 1; i >= 0; i--) {
			System.out.print(instanceArray.get(i).getEntityName());

			// cautam entitatea corespunzatoare instantei pe care vrem sa o afisam
			Entity entity = null;
			for (Entity e : entityArray) {
				// cand o gasim facem break
				if (instanceArray.get(i).getEntityName().equals(e.getEntityName())) {
					entity = e;
					break;
				}
			}
			// afisam instanta
			instanceArray.get(i).displayInstance(entity);
		}
	}

	public boolean exists(String entityName, String primaryKey) {
		for (Instance instance : instanceArray) {
			if (entityName.equals(instance.getEntityName())) {
				String instancePrimaryKey = String.valueOf(instance.getPrimaryKey());
				if (primaryKey.equals(instancePrimaryKey)) {
					return true;
				}
			}
		}
		return false;
	}

	public Instance getInstance(String entityName, String primaryKey) {
		for (Instance instance : instanceArray) {
			if (entityName.equals(instance.getEntityName())) {
				String instancePrimaryKey = String.valueOf(instance.getPrimaryKey());
				if (primaryKey.equals(instancePrimaryKey)) {
					return instance;
				}
			}
		}
		return null;
	}

	public void addFirst(Instance instance) {
		instanceArray.remove(instance);
		instanceArray.add(instance);

	}

	// daca o instanta din nod are timestamp < time
	// o stergem
	public void clean(Long time) {
		for (int i = 0; i < instanceArray.size(); i++) {
			if (instanceArray.get(i).getTimeStamp() < time) {
				instanceArray.remove(i);
				// din cauza stergerii
				// indexii se decaleaza
				i--;
			}
		}
	}
}
