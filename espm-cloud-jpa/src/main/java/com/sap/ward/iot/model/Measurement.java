package com.sap.ward.iot.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "iot_measurement")
@NamedQueries({ @NamedQuery(name = "Measurement.getAllMeasurement", query = "SELECT e FROM Measurement e"), })
public class Measurement
{
	@Id
	private Long id;

	private Long sensorId;

	private String type;

	private Date createTime;

	@Column(precision = 10, scale = 2)
	private BigDecimal value1;

	@Column(precision = 10, scale = 2)
	private BigDecimal value2;

	/**
	 * 
	 * @param id
	 * @param sensorId
	 * @param type
	 * @param createTime
	 * @param value1
	 * @param value2
	 */
	public Measurement(Long id, Long sensorId, String type, Date createTime, double value1, double value2)
	{
		this.id = id;
		this.sensorId = sensorId;
		this.type = type;
		this.createTime = createTime;
		this.value1 = BigDecimal.valueOf(value1);
		this.value2 = BigDecimal.valueOf(value2);
	}

	public Measurement()
	{

	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getSensorId()
	{
		return sensorId;
	}

	public void setSensorId(Long sensorId)
	{
		this.sensorId = sensorId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type == null ? null : type.trim();
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public BigDecimal getValue1()
	{
		return value1;
	}

	public void setValue1(BigDecimal value1)
	{
		this.value1 = value1;
	}

	public BigDecimal getValue2()
	{
		return value2;
	}

	public void setValue2(BigDecimal value2)
	{
		this.value2 = value2;
	}
}