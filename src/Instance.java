import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class Instance implements Cloneable {
	private String entityName;
	// o instanta este un vector de valori
	private ArrayList<Value> valueArray;
	private long timeStamp;

	// constructor
	public Instance(String entityName, ArrayList<Value> valueArray) {
		this.entityName = entityName;
		this.valueArray = valueArray;
		timeStamp = System.nanoTime();
	}

	@Override
	public Object clone() {
		return new Instance(entityName, valueArray);
	}

	public String getEntityName() {
		return entityName;
	}

	// returnam valoarea de pe prima pozitie din vector
	public Object getPrimaryKey() {
		return valueArray.get(0).getValue();
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	// afisam continutul unei instante
	public void displayInstance(Entity entity) {
		int i = 0;
		// pentru fiecare valoare din instanta
		for (Value value : valueArray) {
			// obtinem numele atributului corespunzator
			System.out.print(" " + entity.getDataName(i) + ":");
			// in functie de tipul de data
			// daca e float respectam formatul
			if (entity.getDataType(i).equals("Float")) {
				DecimalFormat decimal = new DecimalFormat("#.##");
				String output = decimal.format((Float) value.getValue());
				System.out.print(output);
			} else {
				System.out.print(String.valueOf(value.getValue()));
			}
			i++;
		}
		System.out.println();
	}

	// updatam valorile
	public void update(Entity entity, List<String> newValues) {
		// parcurgem lista cu valorile noi
		for (int i = 0; i < newValues.size(); i += 2) {
			// pentru fiecare valoare determinam
			// numele si valoarea atributului
			String dataName = newValues.get(i);
			String dataValue = newValues.get(i + 1);
			// identificam tipul de data
			String dataType = entity.getDataType(dataName);
			// identificam pozitia in vectorul de valori
			Integer index = entity.getIndex(dataName);
			// construim noua valoare
			Value newValue = Value.buildValue(dataValue, dataType);

			// adaugam valoarea la pozitia index
			valueArray.set(index, newValue);
		}
	}
}
