
public class Value {
	private Integer x;
	private Float y;
	private String z;
	private String dataType;

	public Value(Integer x) {
		this.x = x;
		dataType = "Integer";
	}

	public Value(Float y) {
		this.y = y;
		dataType = "Float";
	}

	public Value(String z) {
		this.z = z;
		dataType = "String";
	}

	public String getDataType() {
		return dataType;
	}

	public Object getValue() {
		switch (dataType) {

		case "Integer":
			return x;
		case "Float":
			return y;
		case "String":
			return z;
		}
		return null;
	}

	// construim un obiect de tip value
	public static Value buildValue(String dataValue, String dataType) {
		// in functie de tipul datei
		switch (dataType) {
		case "Integer":
			Integer x = Integer.valueOf(dataValue);
			return new Value(x);

		case "Float":
			Float y = Float.valueOf(dataValue);
			return new Value(y);

		case "String":
			return new Value(dataValue);
		}
		return null;
	}
}
