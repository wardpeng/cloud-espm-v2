package com.sap.espm.model.data;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.espm.model.util.Utility;
import com.sap.ward.iot.model.Measurement;
import com.sap.ward.iot.model.Sensor;
import com.sap.ward.iot.model.Switch;
import com.sap.ward.iot.model.Temperature;

/**
 * Data Loader tool for loading business partners and products into the db.
 * 
 */
public class DataLoader
{

	/**
	 * The {@link Logger} instance used for logging.
	 */
	private static Logger logger = LoggerFactory.getLogger(DataLoader.class);

	/**
	 * The {@link EntityManagerFactory} used for persisting data into the Data
	 * Source. Refer to the {@link Utility} class for details on how the
	 * {@link EntityManagerFactory} is configured.
	 */
	private EntityManagerFactory emf;

	/**
	 * The Overloaded constructor of the DataLoader that takes an instance of
	 * the {@link EntityManagerFactory} instance.
	 * 
	 * @param emf
	 */
	public DataLoader(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	public void loadData()
	{
		System.out.println("loadData():###########################");
		loadTemperatures();// 加载测试数据
		createSensorData();// 加载测试数据
		createMeasurementData();// 加载测试数据
		createSwitch();// Switch测试方法

		System.out.println("loadData():###########################");

	}

	/**
	 * 加载温度测试数据
	 * 
	 * @author I327500
	 * 
	 * @return
	 */
	private List<Temperature> loadTemperatures()
	{
		EntityManager em = emf.createEntityManager();
		List<Temperature> resTemp = null;

		// create and persist an employee
		em.getTransaction().begin();

		// create T1
		Temperature temp = new Temperature(1, System.currentTimeMillis(), "T1", "30.1C");
		em.persist(temp);

		// create H1
		temp = new Temperature(2, System.currentTimeMillis(), "H1", "70%");
		em.persist(temp);

		em.getTransaction().commit();

		TypedQuery<Temperature> query = em.createNamedQuery("Temperature.getAllTemperature", Temperature.class);
		resTemp = query.getResultList();

		// System.out.println("All employees:***************************");
		// for (Temperature t : resTemp) {
		// System.out.println(t);
		// }

		return resTemp;

	}

	/**
	 * 创建Sensor测试数据，并插入到数据库
	 */
	private List<Sensor> createSensorData()
	{
		EntityManager em = emf.createEntityManager();
		List<Sensor> resSensor = null;

		// create and persist an employee
		em.getTransaction().begin();

		// create sensor 1
		Sensor sensor = new Sensor("1", "device_1", "type_1", "description_1");
		em.persist(sensor);

		// create sensor 2
		sensor = new Sensor("2", "device_2", "type_2", "description_1");
		em.persist(sensor);

		em.getTransaction().commit();

		TypedQuery<Sensor> query = em.createNamedQuery("Sensor.getAllSensor", Sensor.class);
		resSensor = query.getResultList();

		// System.out.println("All sensors:***************************");
		// for (Sensor t : resSensor) {
		// System.out.println(t);
		// }

		return resSensor;
	}

	/**
	 * 创建Measurement测试数据，并插入到数据库
	 */
	private List<Measurement> createMeasurementData()
	{

		EntityManager em = emf.createEntityManager();
		List<Measurement> resMeasurement = null;

		// create and persist an employee
		em.getTransaction().begin();

		// create sensor 1-measurement1
		Measurement measurement = new Measurement("1", "1", "dht11", new Date(1511241780342L), 37.50, 75.25);
		Sensor sensor = em.find(Sensor.class, "1");
		measurement.setSensor(sensor);
		sensor.addMeasurement(measurement);
		em.persist(measurement);

		// create sensor 1-measurement2
		measurement = new Measurement("2", "1", "dht11", new Date(1511241787342L), 35.50, 74.25);
		measurement.setSensor(sensor);
		sensor.addMeasurement(measurement);
		em.persist(measurement);

		// create sensor 1-measurement3
		measurement = new Measurement("3", "1", "dht11", new Date(1511241794342L), 39.01, 80.25);
		measurement.setSensor(sensor);
		sensor.addMeasurement(measurement);
		em.persist(measurement);

		em.persist(sensor);

		em.getTransaction().commit();

		TypedQuery<Measurement> query = em.createNamedQuery("Measurement.getAllMeasurement", Measurement.class);
		resMeasurement = query.getResultList();

		System.out.println("All Measurement:-------------------------------------");
		for (Measurement t : resMeasurement) {
			System.out.println(t);
		}

		return resMeasurement;
	}

	/**
	 * 创建Sensor测试数据，并插入到数据库
	 */
	private List<Switch> createSwitch()
	{
		EntityManager em = emf.createEntityManager();
		List<Switch> resSwitch = null;

		// create and persist an employee
		em.getTransaction().begin();

		// create sensor 1
		Switch sw = new Switch("4", (double) 0.0);
		em.persist(sw);

		em.getTransaction().commit();

		return null;
	}

}
