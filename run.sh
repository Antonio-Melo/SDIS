echo "Starting peer(s)..."
java -classpath ./bin server.main.Peer 1.0 1 peer1 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &
java -classpath ./bin server.main.Peer 1.0 2 peer2 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &
java -classpath ./bin server.main.Peer 1.0 3 peer3 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &
#java -classpath ./bin server.main.Peer 1.0 4 peer4 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &
#java -classpath ./bin server.main.Peer 1.0 5 peer5 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &
#java -classpath ./bin server.main.Peer 1.0 6 peer6 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 &

