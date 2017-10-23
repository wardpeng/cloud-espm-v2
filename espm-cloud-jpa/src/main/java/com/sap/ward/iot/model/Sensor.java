package com.sap.ward.iot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "iot_sensor")
@NamedQueries({ @NamedQuery(name = "Sensor.getAllSensor", query = "SELECT e FROM Sensor e"), })
public class Sensor
{
	@Id
	private Long id;

	private String device;

	private String type;

	private String description;

	public Sensor()
	{
	}

	/**
	 * 
	 * @param id
	 *            ID
	 * @param device
	 *            设备
	 * @param type
	 *            类型
	 * @param description
	 *            描述
	 */
	public Sensor(Long id, String device, String type, String description)
	{
		this.id = id;
		this.device = device;
		this.type = type;
		this.description = description;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDevice()
	{
		return device;
	}

	public void setDevice(String device)
	{
		this.device = device == null ? null : device.trim();
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type == null ? null : type.trim();
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description == null ? null : description.trim();
	}
}
