# for UNIX/Linux/MacOSX
javac -cp target/myapp-1-jar-with-dependencies.jar:. $1.java
java -cp target/myapp-1-jar-with-dependencies.jar:. $1