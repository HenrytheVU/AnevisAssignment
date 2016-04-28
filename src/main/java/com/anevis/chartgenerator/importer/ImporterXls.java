package com.anevis.chartgenerator.importer;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.anevis.chartgenerator.model.entity.PieChartEntity;

public class ImporterXls implements Importer {

	Logger logger = Logger.getLogger(ImporterXls.class.getName());

	private List<PieChartEntity> entityList = new ArrayList<PieChartEntity>();

	@Override
	public void importFile(File file) {
		logger.info("importing: " + file.getPath());
		HSSFWorkbook wb;
		try {
			wb = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			int rows = sheet.getPhysicalNumberOfRows();
			int cols = 0;
			int tmp = 0;

			for (int i = 0; i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
				}
			}

			String country = null;
			double weight = 0;
			for (int r = 1; r < rows; r++) {
				row = sheet.getRow(r);
				if (row != null) {
					for (int c = 0; c < cols; c++) {
						cell = row.getCell(c);
						if (cell != null) {
							if (cell.getColumnIndex() == 0) {
								country = cell.getStringCellValue();
							} else {
								weight = cell.getNumericCellValue();
							}
						}
					}
					// populate list for generating pdf
					populateEntityList(country, weight);
				}
			}
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void populateEntityList(String country, double weight) {
		logger.info("adding: " + country + " " + weight + " to entityList");
		entityList.add(new PieChartEntity(country, weight));
	}

	public List<PieChartEntity> getEntityList() {
		return this.entityList;
	}

	public static void main(String[] args) {
		ImporterXls importer = new ImporterXls();
		String filePath = "data/piechart-data.xls";
		importer.importFile(new File(filePath));

		System.out.println(importer.getEntityList());
	}

}
