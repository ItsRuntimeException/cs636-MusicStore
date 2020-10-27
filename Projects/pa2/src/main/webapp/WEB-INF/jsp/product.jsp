<!DOCTYPE HTML>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Product Page</title>
</head>
<body>
    <h1>Our Products</h1>

    <h2>86 (the band)</h2>
    <p><a href="../../sound/8601/sound.html">True Life Songs and Pictures</a></p>
    <td>
        <form method="POST" action="addItem.html">
          <input type="hidden" name="productCode"
                  value="8601">
          <input type="submit" value="Add to Cart">
        </form>    
    </td>

    <br>

    <h2>Paddlefoot</h2>
    <p><a href="../../sound/pf01/sound.html">Paddlefoot (the first album)</a></p>
    <td>
        <form method="POST" action="addItem.html">
          <input type="hidden" name="productCode"
                  value="pf01">
          <input type="submit" value="Add to Cart">
        </form>
    </td>
    <p><a href="../../sound/pf02/sound.html">Paddlefoot (the second album)</a></p>
    <td>
        <form method="POST" action="addItem.html">
          <input type="hidden" name="productCode"
                  value="pf02">
          <input type="submit" value="Add to Cart">
        </form>    
    </td>

    <br>

    <h2>Joe Rut</h2>
    <p><a href="../../sound/jr01/sound.html">Genuine Wood Grained Finish</a></p>
    <td>
        <form method="POST" action="addItem.html">
          <input type="hidden" name="productCode"
                  value="jr01">
          <input type="submit" value="Add to Cart">
        </form>
    </td>

</body>
</html>
