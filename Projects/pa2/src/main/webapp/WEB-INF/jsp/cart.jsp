<!DOCTYPE HTML>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Cart Page</title>
<link href="../../styles/main.css" rel="stylesheet">
</head>
<body>
    <h1>Your cart</h1>
  <c:choose>
      <c:when test="${cart == null || cdata == null}">
          <p>Your cart is empty.</p>
      </c:when>
      <c:otherwise>
        <table>
           <tr>
            <th>Qty</th>
            <th>Description</th>
            <th>Amount</th>
            <th>Price</th>
            <th>&nbsp;</th>
         </tr>
          <c:forEach var="item" items="${cdata}">
            <tr class="cart_row">
              <td>
                <form method="POST" action="updateCart.html">
                  <input type="hidden" name="productCode"
                         value="<c:out value='${item.code}'/>">
                  <input type=text name="quantity"
                         value="<c:out value='${item.quantity}'/>" id="quantity">
                  <input type="submit" value="Update">
                </form>
              </td>
              <td>${item.description}</td>
              <td>${item.quantity}</td>
              <td>${item.price*item.quantity}</td>
              <td>
                <form method="POST" action="removeItem.html">
                  <input type="hidden" name="productCode"
                         value="<c:out value='${item.code}'/>">
                  <input type="submit" value="Remove">
                </form>
              </td>
            </tr>
          </c:forEach>
            <tr>
              <td colspan="2">
                <p><b>To change the quantity for an item</b>, enter the new quantity 
                      and click on the Update button.</p>
                <p><b>To remove an item</b>, click on the Remove button.</p>
              </td>
              <td colspan="3">&nbsp;</td>
            </tr>
          </table>
      </c:otherwise>
  </c:choose>

  
  <form method="GET" action="product.html">
    <input type="submit" value="Return to Product Page">
  </form>

  <form method="GET" action="userWelcome.html">
    <input type="submit" value="Return to Main Page">
  </form>

  <!-- Connection is NOT SECURE.  For testing only. -->
  <form method="GET" action="beginCheckout.html">
		<input type="submit" value="Checkout">
	</form>
    
</body>
</html>
