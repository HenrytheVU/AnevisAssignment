package com.anevis.chartgenerator.exporter;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.anevis.chartgenerator.model.entity.PieChartEntity;

public class ExporterPieChartTest {

	private final File testDir = new File(
			"src" + File.separator + "test" + File.separator + "resources" + File.separator + "export");
	private ExporterPieChart exporter;
	private List<PieChartEntity> eList;
	private File exportFile;
	private String testFileName;

	@Before
	public void setUp() {
		testFileName = "testFile.pdf";
		if (testDir.exists()) {
			try {
				FileUtils.deleteDirectory(testDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		testDir.mkdirs();
		exportFile = new File(testDir, testFileName);
		eList = new ArrayList<PieChartEntity>();
		exporter = new ExporterPieChart();
	}

	@Test
	public void test() {
		eList.add(new PieChartEntity("test1", 2.1));
		eList.add(new PieChartEntity("test2", 1.2));
		eList.add(new PieChartEntity("test3", 3.45));

		exporter.exportPdf(eList, exportFile.getPath());
		assertTrue(exportFile.exists());
	}

}
