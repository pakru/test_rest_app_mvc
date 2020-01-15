BCS CLIENT
-
Клиентская часть, посылающая даннные об аккаунтах

## Сборка

```bash
mvn clean package
```

Артефактом сборки является jar-файл, расположенный в дириктории _target_

## Запуск

Клиент запускается в виде обычного java приложения:
```bash
java -jar client-1.0-SNAPSHOT.jar
```

После запуска проивзодится отправка на сервер данных из файла data.csv, либо любого другого файла, указанного в параметре _client.datafile_

Так же можно указывать параметры запуска приложения. 
Ниже приведены настраиваемые параметры приложения. Значения по умолчанию прописаны в application.properties:  

```
client.host=localhost
client.port=8090
client.proto=http

client.login=admin
client.password=password
client.datafile=classpath:data.csv
```

Пример запуска с параметрами:
```bash
java -jar client-1.0-SNAPSHOT.jar --client.host=192.168.1.10 --client.login=user --client.password=1234 --client.datafile=file:bcs/client/src/main/resources/test_data.csv
```