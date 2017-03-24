#compile server peer
mkdir -p ./bin_server
javac -d ./bin_server ./src/server/main/*.java ./src/server/protocol/*.java ./src/server/task/commonPeer/*.java ./src/server/task/initiatorPeer/*.java ./src/utils/*.java