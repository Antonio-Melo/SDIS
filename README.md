# SDIS


Tarefa  | Init-Peer | Peer | Peso
------------- | ------------- | ------------- | -------------
Backup  |  |  | 40%
Restore  |  |  | 10%
Delete  | X | X | 05%
Space Reclaim  |  |  | 15%
Melhoramento 1  |  |  | 05%
Melhoramento 2  |  |  | 05%
Melhoramento 3  |  |  | 05%
Melhoramento 4  |  |  | 05%
Cliente RMI  | X | X | 05%
Demo  | X | X | 05%
Total |  |  | 05%


server side
1 a escutar o mdb a espera de um putchunk (pode criar outro thread para guardar chunk e eventualmente mandar stored em MC)
1 a escutar o mc a espera de um getchunk (pode criar outro thread para buscar chunk e eventualmente mandar chunk por MDR)
  a escutar o mc tambem a espera de um delete (pode criar para apagar chunk)
  a escutar o mc tambem a espera de um removed ( pode criar outro thread para dar update do replicationdegree se tiver o chunk, eventualmente pefir putchunk se o replication degree estiver abaixo do desejado)
1 a esscutar pedidos da client app por rmi
  
protocol package
high-level, i.e. a class backup divide o ficheiro e tenta enviar o ficheiro fazendo recurso da class putchunk dentro de um ciclo ate ter enviado todo o ficheiro


task package
low-level, i.e. a class putchunk tenta enviar um chunk varias vezes e aguarda a resposta para esse chunk 
