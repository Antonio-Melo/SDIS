#compile client
echo "Compiling client..."
mkdir -p ./bin
javac -d ./bin ./src/client/TestApp.java ./src/server/protocol/ClientInterface.java