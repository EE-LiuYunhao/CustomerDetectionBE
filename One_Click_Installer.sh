sudo apt-get install openjdk-11-jdk
javacPath="`which javac`"
echo "Javac Path: $javacPath"
javaFile1="`file $javacPath | cut -d ' ' -f5`"
echo "file $javacPath => $javaFile1"
javaFile2="`file $javaFile1 | cut -d ' ' -f5`"
echo "file $javaFile1 => $javaFile2"
javaFile3="`file $javaFile2`"
echo "file $javaFile2 => $javaFile3"
javaTruePath="`echo $javaFile3 | cut -d : -f1`"
sudo echo export JAVA_HOME="$javaTruePath" >> ~/.bashrc
source ~/.bashrc
cat ~/.bashrc | grep JAVA_HOME

# install gradle
echo "Installing gradle"
sudo apt-get install gradle

# install MySQL
echo "Installing MySQL client"
sudo apt-get install mysql-client
echo "Installing MySQL server"
sudo apt-get install mysql-server libmysql-java

echo "CLASSPATH=\$CLASSPATH:/usr/share/java/mysql.jar"
source ~/.bashrc
echo $CLASSPATH
