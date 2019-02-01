import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

public abstract class GenericMapper<T> {
	public List<T> map(ExcelTableModel model) {
		return model.getData().stream().map(record -> map(record, model.getColumnNames())).collect(Collectors.toList());
	}

	protected int getColumnByName(Vector<String> columns, String field) {
		return columns.indexOf(getColumnMap().get(field));
	} 

	protected String asString(String field, Vector<Object> record, Vector<String> columns) {
		return record.get(getColumnByName(columns, field)).toString();
	}

	protected int asInt(String field, Vector<Object> record, Vector<String> columns) {
		return Integer.parseInt(asString(field, record, columns), 10);
	}

	protected long asLong(String field, Vector<Object> record, Vector<String> columns) {
		return Long.parseLong(asString(field, record, columns), 10);
	}

	protected boolean asBoolean(String field, Vector<Object> record, Vector<String> columns) {
		return Boolean.parseBoolean(asString(field, record, columns));
	}

	protected float asFloat(String field, Vector<Object> record, Vector<String> columns) {
		return Float.parseFloat(asString(field, record, columns));
	}

	protected double asDouble(String field, Vector<Object> record, Vector<String> columns) {
		return Double.parseDouble(asString(field, record, columns));
	}

	protected Date asDate(String field, Vector<Object> record, Vector<String> columns, DateFormat formatter) throws ParseException {
		return formatter.parse(asString(field, record, columns));
	}

	protected LocalDate asDate(String field, Vector<Object> record, Vector<String> columns, DateTimeFormatter formatter) {
		return LocalDate.parse(asString(field, record, columns), formatter);
	}

	abstract protected Map<String, String> getColumnMap();

	abstract protected T map(Vector<Object> record, Vector<String> columns);
}
