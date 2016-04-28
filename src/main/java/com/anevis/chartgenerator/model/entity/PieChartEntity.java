package com.anevis.chartgenerator.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pie_chart")
public class PieChartEntity {

	private int id;
	private String country;
	private double weight;

	public PieChartEntity() {
		// Why Hibernate, why???
	}

	public PieChartEntity(String country, double weight) {
		this.country = country;
		this.weight = weight;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "country", nullable = false)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "weight", nullable = false)
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "id=" + id + ", country=" + country + ", weight=" + weight;
	}

}
