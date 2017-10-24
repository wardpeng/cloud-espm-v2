package com.sap.ward.iot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "iot_sensor")
@NamedQueries({ @NamedQuery(name = "Sensor.getAllSensor", query = "SELECT e FROM Sensor e"), })
public class Sensor
{
	@Id
	private String sensorId;

	private String device;

	private String type;

	private String description;

	@OneToMany(mappedBy = "sensor", targetEntity = Measurement.class, fetch = FetchType.EAGER)
	private List<Measurement> measurement;

	public Sensor()
	{
		this.measurement = new ArrayList<Measurement>();
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
	public Sensor(String id, String device, String type, String description)
	{
		this();
		this.setSensorId(id);
		this.device = device;
		this.type = type;
		this.description = description;
	}

	public String getSensorId()
	{
		return sensorId;
	}

	public void setSensorId(String sensorId)
	{
		this.sensorId = sensorId;
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

	public List<Measurement> getMeasurement()
	{
		return measurement;
	}

	public void addMeasurement(Measurement measurement)
	{
		this.measurement.add(measurement);
	}

}
