package com.sap.ward.iot.model;

import java.util.Date;

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

	private Long value1;

	private Long value2;

	/**
	 * 
	 * @param id
	 * @param sensorId
	 * @param type
	 * @param createTime
	 * @param value1
	 * @param value2
	 */
	public Measurement(Long id, Long sensorId, String type, Date createTime, Long value1, Long value2)
	{
		this.id = id;
		this.sensorId = sensorId;
		this.type = type;
		this.createTime = createTime;
		this.value1 = value1;
		this.value2 = value2;
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

	public Long getValue1()
	{
		return value1;
	}

	public void setValue1(Long value1)
	{
		this.value1 = value1;
	}

	public Long getValue2()
	{
		return value2;
	}

	public void setValue2(Long value2)
	{
		this.value2 = value2;
	}
}