package cs636.music.domain;

/**
 * LineItem: like Murach, pg. 649, except:
 * --The Product field is called "product", not "item"
 * --The database id is exposed with getter/setter
 * --getTotal in Murach is called calculateItemTotal here
 * to signify it is not a table attribute.
 *
 */
public class LineItem {

	private long id;
	private Product product;
	private Invoice invoice;
	private int quantity;
	// no-args constructor, to be proper JavaBean
	public LineItem() {}
	
	// for DAO use: LineItem from DB
	public LineItem(long id, Product product, Invoice invoice, int quantity) {
		this.id = id;
		this.invoice = invoice;
		this.product = product;
		this.quantity = quantity;
	}
	
	// for service layer
	public LineItem(Product product, Invoice invoice, int quantity) {
		this.product = product;
		this.invoice = invoice;
		this.quantity = quantity;		
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getId() {
		return id;
	}

	public void setId(long lineitem_id) {
		this.id = lineitem_id;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
