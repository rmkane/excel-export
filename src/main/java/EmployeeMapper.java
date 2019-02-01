import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class EmployeeMapper extends GenericMapper<Employee> {
	private static final Map<String, String> columnMap;
	static {
		columnMap = new HashMap<>();
		columnMap.put("id", "ID"); // 		
		columnMap.put("name", "Name");
		columnMap.put("dob", "Date of Birth");
	}

	@Override
	protected Map<String, String> getColumnMap() {
		return columnMap;
	}

	private LocalDate fromDate(Object date) {
		return ((Date) date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	@Override
	protected Employee map(Vector<Object> record, Vector<String> columns) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
		Employee emp = new Employee();
		//emp.setId(asLong("id", record, columns));
		//emp.setName(asString("name", record, columns));
		//emp.setDob(asDate("dob", record, columns, dateFormatter));
		emp.setId((long) record.get(getColumnByName(columns, "id")));
		emp.setName((String) record.get(getColumnByName(columns, "name")));
		emp.setDob(fromDate(record.get(getColumnByName(columns, "dob"))));
		
		return emp;
	}
}
