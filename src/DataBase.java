import java.util.ArrayList;
import java.util.List;

public class DataBase {
	private String dbName;
	private int nrNodes;
	private int maxCapacity;
	// retinem entitatile in interiorul bazei de date
	private ArrayList<Entity> entityArray;
	// vector de noduri
	private ArrayList<Node> nodeArray;

	// constructor
	public DataBase(String dbName, int nrNodes, int maxCapacity) {
		this.dbName = dbName;
		this.nrNodes = nrNodes;
		this.maxCapacity = maxCapacity;
		entityArray = new ArrayList<Entity>();
		nodeArray = new ArrayList<Node>(nrNodes);

		// construim si adaugam nodurile in baza de date
		for (int i = 1; i <= nrNodes; i++) {
			Node nod = new Node(i);
			nodeArray.add(nod);
		}
	}

	// adaugam o entitate in vectorul de entitati
	public void addEntity(Entity entity) {
		entityArray.add(entity);
	}

	// cautam o entitate in vectorul de entitati si o returnam
	public Entity getEntity(String entityName) {
		for (int i = 0; i < entityArray.size(); i++) {
			if (entityName.equals(entityArray.get(i).getEntityName())) {
				return entityArray.get(i);
			}
		}
		return null;
	}

	// inseram o instanta
	public void insert(Instance instance, Entity entity) {
		// citim rf din entitatea de care apartine instanta
		int rf = entity.getRf();
		// cream un vector in care copiem toate nodurile
		// (pastram astfel ordinea in vectorul original)
		// il vom folosi la sortarea nodurilor
		ArrayList<Node> sortedNodes = new ArrayList<Node>();
		for (Node node : nodeArray) {
			sortedNodes.add(node);
		}
		// sortam vectorul conform cerintelor folosind un comparator
		nodeArray.sort(new NodeComparator());

		// adaugam instanta in primele rf noduri libere
		for (int i = 0; i < rf; i++) {
			// daca nodul este lier
			if (sortedNodes.get(i).getSize() < maxCapacity) {
				// clonam instanta si o adaugam in nod
				Instance instanceCopy = (Instance) instance.clone();
				sortedNodes.get(i).addInstance(instanceCopy);
			} else {
				// daca nodul este plin
				if (rf < sortedNodes.size()) {
					// daca mai exista noduri in baza de date
					// incrementam rf pentru a nu lua in considerare nodul curent
					rf++;
				} else {
					// daca nu mai exista noduri in baza de date
					// adaugam un nod si inseram copia instante in acesta
					nrNodes++;
					Node node = new Node(nrNodes);
					Instance instanceCopy = (Instance) instance.clone();
					node.addInstance(instanceCopy);
					nodeArray.add(node);
				}
			}
		}
	}

	// stergem o instanta
	public void delete(String entityName, String primaryKey) {
		boolean foundInstance = false;
		// parcurgem vectorul de noduri
		for (Node node : nodeArray) {
			// stergem instanta din noduri
			if (node.delete(entityName, primaryKey) == true) {
				// daca gasim instanta in macar un nod
				// setam foundInstance la true
				foundInstance = true;
			}
		}
		// daca ramane la false inseamna ca nu am gasit instanta
		if (foundInstance == false) {
			System.out.println("NO INSTANCE TO DELETE");
		}
	}

	// afisam noduri
	public void snapshotDB() {
		// isEmpty retine daca baza este goala
		boolean isEmpty = true;
		// parcurgem nodurile
		for (Node node : nodeArray) {
			// daca nodul nu este gol
			// il afisam
			if (node.getSize() != 0) {
				node.displayNode(entityArray);
				isEmpty = false;
			}
		}
		// daca baza e goala
		// afisam mesajul corespunzator
		if (isEmpty == true) {
			System.out.println("EMPTY DB");
		}
	}

	// afisam nodurile in care se afla o instata
	// plus continutul instantei
	public void get(String entityName, String primaryKey) {
		// retinem daca am gasit instanta in baza
		boolean foundInstance = false;
		// parcurgem nodurile
		for (Node node : nodeArray) {
			// daca instanta exista in nodul curent
			// afisam nodul
			if (node.exists(entityName, primaryKey) == true) {
				foundInstance = true;
				System.out.print("Nod" + node.getIndex() + " ");
			}
		}
		// daca nu gasim instanta
		// afisam mesajul corespunaztor
		if (foundInstance == false) {
			System.out.println("NO INSTANCE FOUND");
			return;
		}
		// daca exista instanta
		Instance instance;
		// cautam instanta in baza de date
		for (Node node : nodeArray) {
			instance = node.getInstance(entityName, primaryKey);
			// daca gasim instanta in nodul curent
			if (instance != null) {
				// afisam entitatea care ii corespunde
				// plus continutul instantei
				Entity entity = getEntity(instance.getEntityName());
				System.out.print(instance.getEntityName());
				instance.displayInstance(entity);
				return;
			}
		}
	}

	// updatam valorile unei instante
	public void update(String entityName, String primaryKey, List<String> newValues) {
		Entity entity = getEntity(entityName);
		// parcurgem nodurile
		for (Node node : nodeArray) {
			// cautam instanta in nodul curent
			Instance instance = node.getInstance(entityName, primaryKey);
			if (instance != null) {
				// daca exista
				// updatam valorile si o punem prima in nod
				instance.update(entity, newValues);
				node.addFirst(instance);
			}
		}
	}

	// parcurgem nodurile si stergem instantele care au timestamp < time
	public void cleanup(Long time) {
		for (Node node : nodeArray) {
			node.clean(time);
		}

	}
}
