rem for Windows
rem be sure to load mysql before using this the first time
rem Usage: runOnMysql SystemTest|TakeOrder|ShopAdmin
rem Depends on env vars MYSQL_SITE, MYSQL_USER, and MYSQL_PW
java -cp target/pizza1-1-jar-with-dependencies.jar cs636.pizza.presentation.%1 jdbc:mysql://%MYSQL_SITE%/%MYSQL_USER%db %MYSQL_USER% %MYSQL_PW%
