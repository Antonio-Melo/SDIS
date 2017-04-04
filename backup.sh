#start client
echo "Starting client (BACKUP)..."
#java -classpath ./bin client.TestApp peer1 BACKUP VBox.log 2
#java -classpath ./bin client.TestApp peer1 BACKUP test1.pdf 2
#java -classpath ./bin client.TestApp peer1 BACKUP IMG_5764.JPG 2
#java -classpath ./bin client.TestApp peer1 BACKUP LAIG.rar 2
java -classpath ./bin client.TestApp peer1 BACKUP jit.pdf 2
