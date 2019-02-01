import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

public class Application {
	public static void main(String[] args) throws Exception {
		Workbook workbook = load("ExcelFile.xlsx");
		Sheet sheet = workbook.getSheetAt(0);
		ExcelTableModel model = new ExcelTableModel(sheet);
		EmployeeMapper mapper = new EmployeeMapper();
		List<Employee> employees = mapper.map(model);

		System.out.println(model.getColumnCount());
		System.out.println(model.getRowCount());

		System.out.println(model.getColumnNames());
		System.out.println(model.getData());

		System.out.println(model.getColumnName(1));
		System.out.println(model.getValueAt(2, 1));

		workbook.close();

		print(employees);
	}

	private static <E> void print(List<E> list) {
		System.out.println(join(list, System.lineSeparator()));
	}

	private static <E> String join(List<E> list, String delimiter) {
		return list.stream().map(Object::toString).collect(Collectors.joining(delimiter));
	}

	private static Workbook load(String filename) throws EncryptedDocumentException, IOException {
		ClassLoader loader = Application.class.getClassLoader();
		InputStream is = loader.getResourceAsStream(filename);
		return WorkbookFactory.create(is);
	}
}