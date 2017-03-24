#compile server peer and client
sh ./compile_all.sh

#start rmiregistry
cd bin
start rmiregistry
cd ..

#sleep to allow rmi registry to start
sleep 8

#run peer(s)
echo "Starting peer(s)..."
java -classpath ./bin server.main.Peer 1 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 data1 peer1 &
java -classpath ./bin server.main.Peer 2 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 data2 peer2 &
java -classpath ./bin server.main.Peer 3 224.0.0.1 9001 224.0.0.2 9002 224.0.0.3 9003 data3 peer3 &

#run client (uncomment to use one of the scripts)
sh ./backup.sh
#sh ./restore.sh
#sh ./delete.sh
#sh ./reclaim.sh
#sh ./state.sh
#java -classpath ./bin client.TestApp peer1 BACKUP test1.pdf 2