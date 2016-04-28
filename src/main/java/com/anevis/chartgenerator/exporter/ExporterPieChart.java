package com.anevis.chartgenerator.exporter;

import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.anevis.chartgenerator.model.entity.PieChartEntity;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class ExporterPieChart {

	Logger logger = Logger.getLogger(ExporterPieChart.class.getName());

	private JFreeChart chart;

	private JFreeChart generatePieChart(List<PieChartEntity> chartEntites) {
		DefaultPieDataset dataSet = new DefaultPieDataset();

		for (PieChartEntity entity : chartEntites) {
			dataSet.setValue(entity.getCountry(), entity.getWeight());
		}

		chart = ChartFactory.createPieChart("ANTEIL AM FONDS VERMÖGEN", dataSet, true, false, false);

		return chart;
	}

	public JFreeChart getChart() {
		return this.chart;
	}

	private void writeChartToPDF(int width, int height, String fileName) {
		PdfWriter writer = null;

		Document document = new Document();

		try {

			logger.info("Start writing chart to PDF document...");

			writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
			document.open();
			PdfContentByte contentByte = writer.getDirectContent();
			PdfTemplate template = contentByte.createTemplate(width, height);
			PdfGraphics2D graphics2d = new PdfGraphics2D(contentByte, width, height, new DefaultFontMapper());
			Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width, height);

			chart.draw(graphics2d, rectangle2d);
			ChartUtilities.applyCurrentTheme(chart);

			graphics2d.dispose();
			contentByte.addTemplate(template, 0, 0);

			logger.info("DONE - Chart written to: " + fileName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		document.close();
	}

	public void exportPdf(List<PieChartEntity> entityList, String exportDest) {
		generatePieChart(entityList);
		writeChartToPDF(500, 500, exportDest);
	}

}
