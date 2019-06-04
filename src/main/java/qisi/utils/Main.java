package qisi.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.File;

/**
 * @author : ddv
 * @since : 2019/6/3 下午1:28
 */

public class Main {

	public static void main(String[] args) {
		File xlsFile = new File("src/main/resources/execl/员工表.xlsx");
		// 获得工作簿
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(xlsFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 获得工作表个数
		int sheetCount = workbook.getNumberOfSheets();
		// 遍历工作表
		for (int i = 0; i < sheetCount; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			// 获得行数
			int rows = sheet.getLastRowNum() + 1;
			// 获得列数，先获得一行，在得到改行列数
			Row tmp = sheet.getRow(0);
			if (tmp == null) {
				continue;
			}
			int cols = tmp.getPhysicalNumberOfCells();
			// 读取数据
			for (int row = 0; row < rows; row++) {
				Row r = sheet.getRow(row);
				for (int col = 0; col < cols; col++) {
					Cell cell = r.getCell(col);
					cell.setCellType(CellType.STRING);
					System.out.print(cell.getStringCellValue() + " ");
				}
				System.out.println();
			}
		}

	}
}
