rem for Windows
rem be sure to load Oracle before using this the first time
rem Usage: runOnOracle SystemTest|TakeOrder|ShopAdmin
rem Depends on env vars ORACLE_SITE, ORACLE_USER, and ORACLE_PW
java -jar target/pizza2-1.jar %1 jdbc:oracle:thin:@%ORACLE_SITE% %ORACLE_USER% %ORACLE_PW% 
