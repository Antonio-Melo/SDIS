#start client
echo "Starting peer..."
java -classpath ./bin server.main.Peer 2.0 localhost..4501 peer1 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003
