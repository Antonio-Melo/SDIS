#compile server peer
mkdir -p ./bin
javac -d ./bin ./src/server/main/*.java ./src/server/protocol/*.java ./src/server/task/commonPeer/*.java ./src/server/task/initiatorPeer/*.java ./src/utils/*.java
