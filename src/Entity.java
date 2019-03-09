import java.util.ArrayList;

public class Entity {
	private String entityName;
	private int rf;
	private int nrAttributes;
	// pe pozitii pare se afla numele atributelor
	// pe pozitii impare se afla tipul de data al acestora
	private ArrayList<String> attribute;

	// constructor
	public Entity(String entityNume, int rf, int nrAttr, ArrayList<String> attr) {
		entityName = entityNume;
		this.rf = rf;
		nrAttributes = nrAttr;
		attribute = attr;
	}

	public String getEntityName() {
		return entityName;
	}

	public int getNrAttributes() {
		return nrAttributes;
	}

	// pozitie para
	public String getDataName(int index) {
		return attribute.get(2 * index);
	}

	// pozitie impara
	public String getDataType(int index) {
		return attribute.get(2 * index + 1);
	}

	public String getDataType(String dataName) {
		for (int i = 0; i < attribute.size(); i += 2) {
			if (attribute.get(i).equals(dataName)) {
				return attribute.get(i + 1);
			}
		}
		return null;
	}

	public Integer getIndex(String dataName) {
		for (int i = 0; i < attribute.size(); i += 2) {
			if (attribute.get(i).equals(dataName)) {
				return i / 2;
			}
		}
		return null;
	}

	public int getRf() {
		return rf;
	}
}
