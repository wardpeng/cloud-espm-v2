package com.sap.espm.model.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.espm.model.Customer;
import com.sap.espm.model.CustomerReview;
import com.sap.espm.model.Product;
import com.sap.espm.model.ProductCategory;
import com.sap.espm.model.Stock;
import com.sap.espm.model.Supplier;
import com.sap.espm.model.util.Utility;
import com.sap.ward.iot.model.Measurement;
import com.sap.ward.iot.model.Sensor;
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

	/**
	 * This method is used to the parse the contents of the
	 * "com/sap/espm/model/data/Products.xml" file and populate a list of
	 * {@link Product} that will eventually be persisted in the Data Source.
	 * 
	 * @param suppliers
	 *            - The List of {@link Supplier}.
	 * @return - The list of {@link Product} that will be persisted in the
	 *         Database.
	 */
	public List<Product> loadProducts(List<Supplier> suppliers)
	{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Product> queryProd;
		List<Product> resProd = null;
		try {
			em.getTransaction().begin();
			queryProd = em.createNamedQuery("Product.getAllProducts", Product.class);
			resProd = queryProd.getResultList();
			if (resProd.size() > 5) {
				logger.info(resProd.size() + " Products already available in the db");
			} else {
				new XMLParser().readProduct(em, "com/sap/espm/model/data/Products.xml", suppliers);
				em.getTransaction().commit();
				queryProd = em.createNamedQuery("Product.getAllProducts", Product.class);
				resProd = queryProd.getResultList();
				logger.info(resProd.size() + " Products loaded into the db");
			}
		}
		catch (Exception e) {
			logger.error("Exception occured", e);
		}
		finally {
			em.close();
		}
		return resProd;
	}

	/**
	 * This method is used to parse the list of Customer Reviews (pre-populated
	 * in the "com/sap/espm/model/data/CustomerReviews.xml" file) and return a
	 * list of {@link CustomerReview}.
	 * 
	 * @param products
	 *            - The List of {@link Product}
	 * @return - The list of {@link CustomerReview}
	 */
	public List<CustomerReview> loadCustomerReviews(List<Product> products)
	{
		EntityManager em = emf.createEntityManager();
		TypedQuery<CustomerReview> queryReviews;
		List<CustomerReview> resReview = null;
		try {
			em.getTransaction().begin();
			queryReviews = em.createNamedQuery("CustomerReview.getAllCustomerReviews", CustomerReview.class);
			resReview = queryReviews.getResultList();
			if (resReview.size() > 5) {
				logger.info(resReview.size() + " Customer Reviews already available in the db");
			} else {
				new XMLParser().readCustomerReview(em, "com/sap/espm/model/data/CustomerReviews.xml", products);
				em.getTransaction().commit();
				queryReviews = em.createNamedQuery("CustomerReview.getAllCustomerReviews", CustomerReview.class);
				resReview = queryReviews.getResultList();
				logger.info(resReview.size() + " Products loaded into the db");
			}
		}
		catch (Exception e) {
			logger.error("Exception occured", e);
		}
		finally {
			em.close();
		}
		return resReview;
	}

	/**
	 * This method is used to populate the {@link Customer} in the Data Source.
	 * The list of Business Partners are stored in the
	 * "com/sap/espm/model/data/Business_Partners.xml" file.
	 */
	public void loadCustomers()
	{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Customer> queryBP;
		List<Customer> resBP;
		try {
			em.getTransaction().begin();
			queryBP = em.createNamedQuery("Customer.getAllCustomers", Customer.class);
			resBP = queryBP.getResultList();
			if (resBP.size() > 5) {
				logger.info(resBP.size() + " Customers already available in the db");
			} else {
				new XMLParser().readCustomers(em, "com/sap/espm/model/data/Business_Partners.xml");
				em.getTransaction().commit();
				queryBP = em.createNamedQuery("Customer.getAllCustomers", Customer.class);
				resBP = queryBP.getResultList();
				logger.info(resBP.size() + " customers loaded into the db");
			}
		}
		catch (Exception e) {
			logger.error("Exception occured", e);
		}
		finally {
			em.close();
		}
	}

	/**
	 * Load Customers to db from Business_Partners.xml.
	 */
	public List<Supplier> loadSuppliers()
	{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Supplier> queryBP;
		List<Supplier> resBP = null;
		try {
			em.getTransaction().begin();
			queryBP = em.createNamedQuery("Supplier.getAllSuppliers", Supplier.class);
			resBP = queryBP.getResultList();
			if (resBP.size() > 5) {
				logger.info(resBP.size() + " Suppliers already available in the db");
			} else {
				new XMLParser().readSuppliers(em, "com/sap/espm/model/data/Business_Partners.xml");
				em.getTransaction().commit();
				queryBP = em.createNamedQuery("Supplier.getAllSuppliers", Supplier.class);
				resBP = queryBP.getResultList();
				logger.info(resBP.size() + " suppliers loaded into the db");
			}
		}
		catch (Exception e) {
			logger.error("Exception occured", e);
		}
		finally {
			em.close();
		}
		return resBP;
	}

	/**
	 * This method is used to populate the Data Source with the list of
	 * {@link ProductCategory}. This list is defined in the resource file
	 * "com/sap/espm/model/data/Product_Categories.xml".
	 * 
	 * @param products
	 *            - The list of the {@link Product}.
	 */
	public void loadProductCategories(List<Product> products)
	{
		EntityManager em = emf.createEntityManager();
		TypedQuery<ProductCategory> queryPC;
		List<ProductCategory> resPC;
		try {
			em.getTransaction().begin();
			queryPC = em.createNamedQuery("ProductCategory.getAllProductCategories", ProductCategory.class);
			resPC = queryPC.getResultList();
			if (resPC.size() > 5) {
				logger.info(resPC.size() + " Product Categories already available in the db");
			} else {
				new XMLParser().readProductCategory(em, "com/sap/espm/model/data/Product_Categories.xml", products);
				em.getTransaction().commit();
				queryPC = em.createNamedQuery("ProductCategory.getAllProductCategories", ProductCategory.class);
				resPC = queryPC.getResultList();
				logger.info(resPC.size() + " Product Categories loaded into the db");
			}
		}
		catch (Exception e) {
			logger.error("Exception occured", e);
		}
		finally {
			em.close();
		}
	}

	/**
	 * This method is used to populate the {@link Stock} tables based on the
	 * list of {@link Product}.
	 * 
	 * @param products
	 *            - The list of {@link Product}.
	 */
	public void loadStock(List<Product> products)
	{
		EntityManager em = emf.createEntityManager();
		TypedQuery<Stock> queryStock;
		List<Stock> resStock;
		BigDecimal quantity;
		BigDecimal minStock;
		BigDecimal lotSize;
		Product prod = null;
		int lenProdName;
		try {
			em.getTransaction().begin();
			queryStock = em.createNamedQuery("Stock.getAllStocks", Stock.class);
			resStock = queryStock.getResultList();
			if (resStock.size() > 5) {
				logger.info(resStock.size() + " Stock already available in the db");
			} else {
				Stock st = null;
				for (int count = 0; count < products.size(); count++) {
					st = new Stock();
					st.setProductId(products.get(count).getProductId());
					lenProdName = products.get(count).getName().length();
					quantity = BigDecimal.valueOf(lenProdName * 5);
					if (lenProdName % 17 == 0) {
						minStock = BigDecimal.valueOf(17);
					} else {
						minStock = BigDecimal.valueOf(lenProdName - 1);
					}
					lotSize = minStock.multiply(BigDecimal.valueOf(3));
					st.setQuantity(quantity);
					st.setMinStock(minStock);
					st.setLotSize(lotSize);
					prod = em.find(Product.class, products.get(count).getProductId());
					st.setProduct(prod);
					em.persist(st);
				}

				em.getTransaction().commit();
				queryStock = em.createNamedQuery("Stock.getAllStocks", Stock.class);
				resStock = queryStock.getResultList();
				logger.info(resStock.size() + " Stocks loaded into the db");
			}
		}
		catch (Exception e) {
			logger.error("Exception occured", e);
		}
		finally {
			em.close();
		}
	}

	/**
	 * This is the entry point method to the class. This method is used to read
	 * the respective XML present in the resource folder and pre-populate the
	 * data source with the Product related data. This is a one time operation
	 * and is done only once when ESPM is loaded for the first time.
	 */
	public void loadData()
	{
		System.out.println("loadData():###########################");
		loadTemperatures();// 加载测试数据
		createSensorData();// 加载测试数据
		createMeasurementData();// 加载测试数据

		loadCustomers();
		List<Supplier> suppliers = loadSuppliers();
		List<Product> products = loadProducts(suppliers);
		List<CustomerReview> customerReviews = loadCustomerReviews(products);
		loadStock(products);
		loadProductCategories(products);
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
		Sensor sensor = new Sensor((long) 1, "device_1", "type_1", "description_1");
		em.persist(sensor);

		// create sensor 2
		sensor = new Sensor((long) 2, "device_2", "type_2", "description_1");
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

		// create sensor 1
		Measurement measurement = new Measurement((long) 1, (long) 1, "dht11", new Date(123456), 37.50, 75.25);
		em.persist(measurement);

		// create sensor 2
		measurement = new Measurement((long) 2, (long) 1, "dht11", new Date(123457), 35.50, 74.25);
		em.persist(measurement);

		// create sensor 3
		measurement = new Measurement((long) 3, (long) 1, "dht11", new Date(123458), 39.01, 80.25);
		em.persist(measurement);

		em.getTransaction().commit();

		TypedQuery<Measurement> query = em.createNamedQuery("Measurement.getAllMeasurement", Measurement.class);
		resMeasurement = query.getResultList();

		// System.out.println("All sensors:***************************");
		// for (Sensor t : resSensor) {
		// System.out.println(t);
		// }

		return resMeasurement;
	}

}
