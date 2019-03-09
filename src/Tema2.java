import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Tema2 {

	public static void main(String[] args) throws FileNotFoundException {
		// deschidem fisier intrare
		File file = new File(args[0]);
		Scanner scanner = new Scanner(file);
		// scriem in fisier iesire
		PrintStream out = new PrintStream(new FileOutputStream(args[0] + "_out"));
		System.setOut(out);
		DataBase dataBase = null;
		// cat timp exista o comanda de citit
		while (scanner.hasNext()) {
			// citim tipul comenzii
			String command = scanner.next();
			switch (command) {

			// cream baza de date
			case "CREATEDB":
				String dbName = scanner.next();
				int nrNodes = scanner.nextInt();
				int maxCapacity = scanner.nextInt();
				dataBase = new DataBase(dbName, nrNodes, maxCapacity);
				break;

			// cream entitati
			case "CREATE":
				String entityName = scanner.next();
				int rf = scanner.nextInt();
				int nrAttributes = scanner.nextInt();
				ArrayList<String> attribute = new ArrayList<String>(2 * nrAttributes);
				// stocam numele variabilelor si tipul acestora in vector(una dupa alta)
				for (int i = 0; i < nrAttributes; i++) {
					String attributeName = scanner.next();
					String attributeType = scanner.next();
					// adaugam numele si tipul atributelor
					attribute.add(attributeName);
					attribute.add(attributeType);
				}
				// cream o entitate si o adaugam in baza de date
				Entity entity = new Entity(entityName, rf, nrAttributes, attribute);
				dataBase.addEntity(entity);
				break;

			case "INSERT":
				entityName = scanner.next();
				entity = dataBase.getEntity(entityName);
				nrAttributes = entity.getNrAttributes();
				// construim vectorul de valori ale atributelor
				ArrayList<Value> valueArray = new ArrayList<Value>();
				for (int i = 0; i < nrAttributes; i++) {
					String valAttribute = scanner.next();
					// identificam tipul de data al atributului "i"
					String dataType = entity.getDataType(i);
					// construim valoarea si o adaugam in vector
					Value value = Value.buildValue(valAttribute, dataType);
					valueArray.add(value);
				}
				// inseram instanta
				Instance instance = new Instance(entityName, valueArray);
				dataBase.insert(instance, entity);
				break;

			case "DELETE":
				entityName = scanner.next();
				String primaryKey = scanner.next();
				dataBase.delete(entityName, primaryKey);
				break;

			case "UPDATE":
				entityName = scanner.next();
				primaryKey = scanner.next();
				// citim toate noile valori intr-un string
				String newValuesString = scanner.nextLine();
				// rupem stringul dupa spatiu, il convertim in lista
				// cream un ArrayList din lista respectiva
				List<String> newValues = new ArrayList<String>(Arrays.asList(newValuesString.split(" ")));
				// deoarece aparea un element vid la inceputul vectorului, l-am eliminat
				newValues.remove(0);
				dataBase.update(entityName, primaryKey, newValues);
				break;

			case "GET":
				entityName = scanner.next();
				primaryKey = scanner.next();
				dataBase.get(entityName, primaryKey);
				break;

			case "SNAPSHOTDB":
				dataBase.snapshotDB();
				break;

			case "CLEANUP":
				dbName = scanner.next();
				Long time = scanner.nextLong();
				dataBase.cleanup(time);
				break;
			}
		}
		scanner.close();
	}
}
