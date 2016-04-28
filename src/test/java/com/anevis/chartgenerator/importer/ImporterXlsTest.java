package com.anevis.chartgenerator.importer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;

import com.anevis.chartgenerator.model.entity.PieChartEntity;

public class ImporterXlsTest {

	private final File testDir = new File(
			"src" + File.separator + "test" + File.separator + "resources" + File.separator + "importer");
	private final String testFileName = "testFile.xls";
	private File testFile;
	private ImporterXls importer;
	private List<PieChartEntity> eList;
	private static final double DELTA = 1e-15;

	@Before
	public void setUp() {
		testFile = new File(testDir, testFileName);
		if (!testDir.exists()) {
			testDir.mkdirs();
		}
		createTestExcelFile();
		assertTrue(testFile.exists());
		System.out.println(testFile.getPath());
		importer = new ImporterXls();
	}

	private void createTestExcelFile() {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("test sheet");

		Row row0 = sheet.createRow(0);
		Row row1 = sheet.createRow(1);
		Row row2 = sheet.createRow(2);
		Row row3 = sheet.createRow(3);

		row0.createCell(0).setCellValue("Country");
		row0.createCell(1).setCellValue("Weight");
		row1.createCell(0).setCellValue("Test1");
		row1.createCell(1).setCellValue(1.0);
		row2.createCell(0).setCellValue("Test2");
		row2.createCell(1).setCellValue(2.0);
		row3.createCell(0).setCellValue("Test3");
		row3.createCell(1).setCellValue(3.0);

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(testDir + File.separator + testFileName);
			wb.write(fos);
			fos.close();
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {

		importer.importFile(testFile);

		eList = importer.getEntityList();
		assertTrue(eList.size() == 3);
		assertEquals("Test1", eList.get(0).getCountry());
		assertEquals("Test2", eList.get(1).getCountry());
		assertEquals("Test3", eList.get(2).getCountry());
		assertEquals(1.0, eList.get(0).getWeight(), DELTA);
		assertEquals(2.0, eList.get(1).getWeight(), DELTA);
		assertEquals(3.0, eList.get(2).getWeight(), DELTA);
		

	}

}
