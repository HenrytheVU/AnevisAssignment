package com.anevis.chartgenerator.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.anevis.chartgenerator.model.entity.PieChartEntity;

public class PieChartDao {

	private Logger logger = Logger.getLogger(PieChartDao.class.getName());
	private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	private Session session;

	public void save(PieChartEntity entity) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		logger.info("Saving: " + entity + " to database");
		session.save(entity);
		session.getTransaction().commit();
		session.close();
	}

	public void saveAll(List<PieChartEntity> eList) {
		session = sessionFactory.openSession();
		session.beginTransaction();
		for (PieChartEntity e : eList) {
			logger.info("Saving: " + e + " to database");
			session.save(e);
		}
		session.getTransaction().commit();
		session.close();
	}

	public PieChartEntity getPieChartEntityById(int id) {
		session = sessionFactory.openSession();
		PieChartEntity entity;
		entity = (PieChartEntity) session.get(PieChartEntity.class, id);
		session.close();
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<PieChartEntity> getAllPieChartEntities() {
		List<PieChartEntity> eList = new ArrayList<PieChartEntity>();
		session = sessionFactory.openSession();
		eList = session.createCriteria(PieChartEntity.class).list();
		session.close();
		return eList;
	}

	public Session getSession() {
		return this.session;
	}

}
