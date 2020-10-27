package cs636.music.presentation.web;

// TODO: pa2: add methods here to service URLs listed below
// which involve forwarding to JSPs listed below as VIEWs or in some cases
// to URLs

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import cs636.music.dao.DownloadDAO;
import cs636.music.domain.Cart;
import cs636.music.domain.CartItem;
import cs636.music.domain.Download;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.User;
import cs636.music.presentation.client.PresentationUtils;
import cs636.music.service.ServiceException;
import cs636.music.service.UserService;
import cs636.music.service.data.CartItemData;
import cs636.music.service.data.UserData;

@Controller
public class CatalogController {
	@Autowired
	private UserService userService;

	// String constants for URL's : please use these!
	private static final String WELCOME_URL = "/welcome.html";
	private static final String WELCOME_VIEW = "/welcome";
	private static final String USER_WELCOME_URL = "/userWelcome.html";
	private static final String CATALOG_URL = "/catalog.html";
	private static final String CATALOG_VIEW = "/WEB-INF/jsp/catalog";
	private static final String CART_URL = "/cart.html";
	private static final String CART_VIEW = "/WEB-INF/jsp/cart";
	private static final String PRODUCT_URL = "/product.html";
	private static final String PRODUCT_VIEW = "/WEB-INF/jsp/product";
	private static final String LISTEN_URL = "/listen.html";
	private static final String SOUND_VIEW = "/WEB-INF/jsp/sound";
	private static final String DOWNLOAD_URL = "/download.html"; // download.html didn't work
	private static final String REGISTER_FORM_VIEW = "/WEB-INF/jsp/registerForm";

	@RequestMapping(WELCOME_URL)
	public String handleWelcome() {
		return WELCOME_VIEW;
	}
	@RequestMapping("")
	public String handleWelcomeBlank() {
		return WELCOME_VIEW;
	}

	@RequestMapping(CATALOG_URL)
	public String catalog(HttpServletRequest request) {
		if (!checkUser(request))
			return "forward:" + USER_WELCOME_URL;
		
		// Here with userBean object available, get it
		HttpSession session = request.getSession();
		request.getSession().setAttribute("variable", variable);
		UserBean userBean = (UserBean) session.getAttribute("user");
		UserData user = userBean.getUser();  // may be null: user needs to register
		assert(user != null);
		
		session.setAttribute("user", userBean);
		return CATALOG_VIEW;
	}

	@RequestMapping(CART_URL)
	public String cart(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		if (!checkUser(request))
			return "forward:" + USER_WELCOME_URL;
			
		// Here with userBean object available, get it
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		Cart cart = null;
		Set<CartItemData> cdata = null;
		try {
			cart = userBean.getCart();
			cdata = userService.getCartInfo(cart);
			if (cart != null && cart.getItems().size() > 0 || cdata != null && cdata.size() > 0) {
				session.setAttribute("cart", cart);
				session.setAttribute("cdata", cdata);
			}
			else {
				session.setAttribute("cart", null);
				session.setAttribute("cdata", null);
			}
		} catch (ServiceException e) {
			throw new ServletException(e);
		}
		UserData user = userBean.getUser();  // may be null: user needs to register
		assert(user != null);
		
		session.setAttribute("user", userBean);
		return user != null? CART_VIEW : REGISTER_FORM_VIEW;
	}

	@RequestMapping(PRODUCT_URL)
	public String product(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		if (!checkUser(request))
			return "forward:" + USER_WELCOME_URL;

		// Here with userBean object available, get it
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		String prodCode = userBean.getProductCode();
		UserData user = userBean.getUser();  // may be null: user needs to register
		assert(user != null);
		Product product = null;
		try {
			product = userService.getProduct(prodCode);
		} catch (ServiceException e) {
			System.out.println("ProductController: " + e);
			throw new ServletException(e);
		}
		session.setAttribute("productCode", prodCode);
		session.setAttribute("product", product);
		session.setAttribute("user", userBean);
		return user != null? PRODUCT_VIEW : REGISTER_FORM_VIEW;
	}

	@RequestMapping(LISTEN_URL)
	public String listen(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		if (!checkUser(request))
			return "forward:" + USER_WELCOME_URL;

		// Here with userBean object available, get it
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		UserData user = userBean.getUser();  // may be null: user needs to register
		session.setAttribute("user", userBean);
		return user != null? SOUND_VIEW : REGISTER_FORM_VIEW;
	}

	// check for user bean, meaning user has already visited the user welcome
	// page and obtained the user bean, so known to the system
	// package scope to let SalesController use this too
	static boolean checkUser(HttpServletRequest request) {
		System.out.print("checkUser: ");
		boolean hasBean = (request.getSession().getAttribute("user") != null);
		if (hasBean) {
			UserBean bean = (UserBean) request.getSession().getAttribute("user");
			if (bean.getUser() == null)
				System.out.println("bean has no user");
			else
				System.out.println("bean's user:" + bean.getUser().getEmailAddress());
			if (bean.getCart() == null)
				System.out.println("bean has no cart");
			else
				System.out.println("bean's cart itemcount = " + bean.getCart().getItems().size());
		} else {
			System.out.println("no user bean in session");
		}
		return hasBean;
	}
}
