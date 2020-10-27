package cs636.music.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cs636.music.dao.UserDAO;
import cs636.music.dao.DbDAO;
import cs636.music.dao.DownloadDAO;
import cs636.music.dao.InvoiceDAO;
import cs636.music.dao.LineItemDAO;
import cs636.music.dao.ProductDAO;
import cs636.music.service.data.CartItemData;
import cs636.music.service.data.DownloadData;
import cs636.music.service.data.InvoiceData;
import cs636.music.service.data.UserData;
import cs636.music.domain.Cart;
import cs636.music.domain.CartItem;
import cs636.music.domain.Download;
import cs636.music.domain.Invoice;
import cs636.music.domain.LineItem;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;


/**
 * 
 * Provide admin level services to user app through accessing DAOs 
 * @author Chung-Hsien (Jacky) Yu
 */
public class UserService {
	
	private DbDAO db;
	private UserDAO userdb;
	private ProductDAO proddb;
	private DownloadDAO dldb;
	private InvoiceDAO invoicedb;
	private LineItemDAO lineItemdb;
	/**
	 * construct a admin service provider 
	 * @param dbDao
	 */
	public UserService(DbDAO dbDao, UserDAO UserDao, ProductDAO productDao, DownloadDAO downloadDAO, 
						LineItemDAO lineItemDao, InvoiceDAO invoiceDAO) {
		db = dbDao;	
		userdb = UserDao;
		proddb = productDao;
		dldb = downloadDAO;
		lineItemdb = lineItemDao;
		invoicedb = invoiceDAO;
	}
	
	/**
	 * Clean all user table, not product and system table to empty
	 * and then set the index numbers back to 1
	 * @throws ServiceException
	 */
	public void initializeDB()throws ServiceException {
		try {
			db.initializeDb();
		} catch (SQLException e)
		{
			throw new ServiceException("Can't initialize DB: ", e);
		}	
	}

	public void registerUser(String first, String last, String email) throws ServiceException {
		try {
			User user = new User(first, last, email); //cs636.music.domain.User;
			userdb.insertUser(user);
		} catch (Exception e) {
			throw new ServiceException("Error in registerUser", e);
		}
	}

	public Set<Product> findAllProducts() throws ServiceException {
		Set<Product> prodList = null;
		try {
			prodList = proddb.findAllProducts();
		} catch (Exception e) {
			throw new ServiceException("Error in findAllProducts", e);
		}
		return prodList;
	}

	public UserData findUserByEmail(String email) throws ServiceException {
		User user = null;
		try {
			user = userdb.findUserByEmail(email);
		} catch (Exception e) {
			throw new ServiceException("Error in findUserByEmail", e);
		}
		if (user != null)
			return new UserData(user);
		else
			return null;
	}

	public Product findProductByCode(String productCode) throws ServiceException {
		Product p = null;
		try {
			p = proddb.findProductByCode(productCode);
		} catch (Exception e) {
			throw new ServiceException("Error in findProductByCode", e);
		}
		return p;
	}

	public void download(String userEmail, Track track) throws ServiceException {
		try {
			Download d = new Download();
			User user = userdb.findUserByEmail(userEmail);
			d.setUser(user);
			d.setDownloadDate(new Date());
			d.setTrack(track);
			dldb.insertDownload(d);
		} catch (Exception e) {
			throw new ServiceException("Error in download", e);
		}
	}

	public Cart newCart() {
		return new Cart();
	}

	public Set<CartItemData> cartInfo(Cart cart) throws ServiceException {
		Set<CartItemData> items = new HashSet<CartItemData>();
		try {
			for (CartItem item : cart.getItems()) {
				Product p = proddb.findProductByPID(item.getProductId());
				CartItemData itemData = 
				new CartItemData(item.getProductId(), item.getQuantity(), 
				p.getCode(), p.getDescription(), p.getPrice());

				items.add(itemData);
			}

		} catch (Exception e) {
			throw new ServiceException("No product in cart", e);
		}
		return items;
	}

	public InvoiceData checkOut(Cart cart, UserData userData) throws ServiceException{
		Invoice invoice = null;
		BigDecimal total = new BigDecimal(0);
		BigDecimal price = new BigDecimal(0);
		BigDecimal quantity = new BigDecimal(0);
		try {
			invoice = new Invoice();
			new Invoice(1, null, new Date(), false, null, null);
			Set<LineItem> items = new HashSet<LineItem>();
			
			for (CartItem CartItem : cart.getItems())
			{
				Product p = proddb.findProductByPID(CartItem.getProductId());
				LineItem item = new LineItem(p, invoice, CartItem.getQuantity());
				items.add(item);
				price = p.getPrice();
				quantity = new BigDecimal(item.getQuantity());
				total = total.add(price.multiply(quantity));	
			}
			invoice.setTotalAmount(total);
			invoice.setLineItems(items);
			User user = userdb.findUserByID(userData.getId());
			if (user != null) {
				invoice.setUser(user);
				invoicedb.insertInvoice(invoice);
				cart.clear();
			}
		} catch (Exception e) {
			throw new ServiceException("Error in checkOut", e);
		}
		return new InvoiceData(invoice);
	}

	public void addToCart(Product product, Cart cart) throws ServiceException {
		CartItem item = cart.findItem(product);
		if (item != null) { // in cart, add extra
			item.setQuantity(item.getQuantity() + 1);
		}
		else { // new item into cart
			item = new CartItem(product.getId(), 1);
			cart.addItem(item);
		}
	}

	public void processInvoice(long invoiceId) throws ServiceException {
		try {
			Invoice invoice = invoicedb.findInvoice(invoiceId);
			invoice.setProcessed(true);
			invoicedb.updateInvoice(invoice);
		} catch (Exception e) {
			throw new ServiceException("Error in processInvoice", e);
		}
	}

	public Set<InvoiceData> listInvoices() throws ServiceException {
		Set<Invoice> invoices = null;
		try {
			invoices = invoicedb.findAllInvoices();
		} catch (Exception e) {
			throw new ServiceException("Error in listInvoices", e);
		}
		// embedded invoices -> Data Object
		Set<InvoiceData> invoiceDatas = new HashSet<InvoiceData>();
		for (Invoice invoice : invoices) {
			invoiceDatas.add(new InvoiceData(invoice));
		}
		return invoiceDatas;
	}

	public Set<DownloadData> listDownloads() throws ServiceException {
		Set<Download> downloads = null;
		try {
			downloads = dldb.findAllDownloads();
		} catch (Exception e) {
			throw new ServiceException("Error in listDownloads", e);
		}
		// embedded downloads -> Data Object
		Set<DownloadData> downloadDatas = new HashSet<DownloadData>();
		for (Download dl : downloads) {
			downloadDatas.add(new DownloadData(dl));
		}
		return downloadDatas;
	}

	public User findUserById(long id) throws ServiceException{
		User user = null;
		try {
			user = userdb.findUserByID(id);
		} catch (Exception e) {
			throw new ServiceException("Error in findUserById", e);
		}
		return user;
	}

	

}