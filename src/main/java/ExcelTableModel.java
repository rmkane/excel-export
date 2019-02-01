import java.text.DecimalFormat;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelTableModel {
	private Vector<String> columnNames;
	private Vector<Vector<Object>> data;
	private DataFormatter dataFormatter = new DataFormatter();
	

	public ExcelTableModel(Sheet sheet) {
		this.columnNames = new Vector<String>();
		this.data = new Vector<Vector<Object>>();

		FormulaEvaluator evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
		int firstRow = sheet.getFirstRowNum();
		Row colNamesRow = sheet.getRow(firstRow);
		int firstCol = colNamesRow.getFirstCellNum();
		int lastCol = colNamesRow.getLastCellNum();

		for (int col = firstCol; col < lastCol; col++) {
			Cell cell = colNamesRow.getCell(col);
			String columnName = dataFormatter.formatCellValue(cell);
			this.columnNames.add(columnName);
		}

		int lastRow = sheet.getLastRowNum();
		for (int r = firstRow + 1; r < lastRow + 1; r++) {
			Vector<Object> cells = new Vector<Object>();
			Row row = sheet.getRow(r);
			if (row == null) {
				row = sheet.createRow(r);
			}
			for (int c = firstCol; c < lastCol; c++) {
				Cell cell = row.getCell(c);
				//String cellValue = dataFormatter.formatCellValue(cell);
				//cells.add(cellValue);
				cells.add(getCellValue(cell, evaluator));
			}
			this.data.add(cells);
		}
	}

	private Object getCellValue(Cell cell, FormulaEvaluator evaluator) {
		String cellValue = dataFormatter.formatCellValue(cell);
		DecimalFormat fint = new DecimalFormat("0");
	    DecimalFormat fdouble = new DecimalFormat("0.00");
	    if (cell != null) {
			switch (cell.getCellType()) {
				case STRING:
					return cell.getStringCellValue();
				case NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						return cell.getDateCellValue();
					}
					Double value = new Double(cell.getNumericCellValue());
					if ("General".equals(cell.getCellStyle().getDataFormatString())) {
		                //return fint.format(cell.getNumericCellValue());
						return value.longValue();
		            } else {
		                //return fdouble.format(cell.getNumericCellValue());
		            	return value.doubleValue();
		            }
				case BOOLEAN:
					return cell.getBooleanCellValue();
				case ERROR:
					return cell.getErrorCellValue();
				case BLANK:
					return "";
				default:
					return null;
			}
		}
		return null;
	}

	public int getColumnCount() {
		return this.columnNames.size();
	}

	public int getRowCount() {
		return this.data.size();
	}

	public String getColumnName(int columnIndex) {
		return this.columnNames.get(columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.data.get(rowIndex).get(columnIndex);
	}

	public Vector<String> getColumnNames() {
		return this.columnNames;
	}

	public Vector<Vector<Object>> getData() {
		return this.data;
	}

	// More getters and setters...
}
