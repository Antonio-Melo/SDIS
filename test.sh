rm -rf 1 2 3
pkill rmiregistry
pkill java
sh compile_all.sh
sh rmi_linux.sh
sh run.sh
sleep 4
sh backup.sh
