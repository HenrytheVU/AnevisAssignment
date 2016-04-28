package com.anevis.chartgenerator.model.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.anevis.chartgenerator.model.entity.PieChartEntity;

public class PieChartDaoTest {

	Logger logger = Logger.getLogger(PieChartDaoTest.class.getName());

	private PieChartDao dao;
	private List<PieChartEntity> eList;

	@Before
	public void setUp() {
		eList = new ArrayList<PieChartEntity>();
		dao = new PieChartDao();
	}

	@Test
	public void test() {
		PieChartEntity e1 = new PieChartEntity("Test1", 1.0);
		PieChartEntity e2 = new PieChartEntity("Test2", 2.0);
		PieChartEntity e3 = new PieChartEntity("Test3", 3.0);

		eList.add(e1);
		eList.add(e2);
		eList.add(e3);

		dao.saveAll(eList);

		assertTrue(isEqual(e1, dao.getPieChartEntityById(e1.getId())));
		assertTrue(isEqual(e2, dao.getPieChartEntityById(e2.getId())));
		assertTrue(isEqual(e3, dao.getPieChartEntityById(e3.getId())));
		assertTrue(dao.getAllPieChartEntities().size() == eList.size());

		PieChartEntity e4 = new PieChartEntity("Test4", 12.6764);
		dao.save(e4);
		assertTrue(isEqual(e4, dao.getPieChartEntityById(e4.getId())));
		eList.add(e4);
		assertTrue(dao.getAllPieChartEntities().size() == eList.size());

	}

	public boolean isEqual(PieChartEntity expected, PieChartEntity actual) {
		return expected.getId() == actual.getId() && expected.getCountry().equals(actual.getCountry())
				&& expected.getWeight() == actual.getWeight();
	}

}
