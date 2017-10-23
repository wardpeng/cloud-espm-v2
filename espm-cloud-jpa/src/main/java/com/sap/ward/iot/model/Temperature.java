package com.sap.ward.iot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "iot_temperature")
@NamedQueries({ @NamedQuery(name = "Temperature.getAllTemperature", query = "SELECT e FROM Temperature e"), })
public class Temperature
{
	@Id
	private int recordId;
	private long time;
	private String sensorName;
	private String value;

	public Temperature()
	{
	}

	public Temperature(int iId, long tTime, String sSenName, String sVal)
	{
		this.recordId = iId;
		this.time = tTime;
		this.sensorName = sSenName;
		this.value = sVal;
	}

	public Temperature(int iId)
	{
		this.recordId = iId;
	}

	public int getRecordId()
	{
		return recordId;
	}

	public void setRecordId(int iId)
	{
		this.recordId = iId;
	}

	public long getTime()
	{
		return time;
	}

	public void setTime(long lTime)
	{
		this.time = lTime;
	}

	public String getSensorName()
	{
		return sensorName;
	}

	public void setSensorName(String sSenName)
	{
		this.sensorName = sSenName;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String sValue)
	{
		this.value = sValue;
	}
}