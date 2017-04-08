echo "Starting peer(s)..."
java -classpath ./bin server.main.Peer 2.0 localhost..4501 peer1 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &
java -classpath ./bin server.main.Peer 2.0 localhost..4502 peer2 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &
java -classpath ./bin server.main.Peer 2.0 localhost..4503 peer3 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &
java -classpath ./bin server.main.Peer 2.0 localhost..4504 peer4 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &
java -classpath ./bin server.main.Peer 2.0 localhost..4505 peer5 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &
java -classpath ./bin server.main.Peer 2.0 localhost..4506 peer6 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &