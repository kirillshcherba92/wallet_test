###### Задача:
Сделать пополнение или списание средств с кошелька,
а так же отобращать историю оперций.

###### Инструкция по:
Локально проект запускает через настройку Environment variable
(--spring.config.location=classpath:./application.properties, file:./src/main/resources/application-dev.yml),
так есть разделение настроечных файлов. Концепция настроечных файлов хороша тем,
что они более гибчи, чем профилирование. И так же может гибко подставляться в docker-compose.

###### примерами запросов к API:
POST http://localhost:8080/api/v1/wallet/
Content-Type: application/problem+json

{
"walletId": "ee28d8f4-aeb9-4e3a-ba18-ca2713cb9626",
"operationType": "DEPOSIT",
"amount": 2000
}

GET http://localhost:8080/api/v1/wallets/{{WALLET_UUID}} 
или
GET http://localhost:8080/api/v1/wallets/ee28d8f4-aeb9-4e3a-ba18-ca2713cb9626


###### Немного о своём выборе:
В коде использовал для работы с БД Jdbc API. Так как на подовляющих
проектах я не видел, что бы использовали Hibernate, может быть как маппинг с сущностями.
В целом есть много статей "за и против" Hibernate.
В настроечный файл application.yml для docker-compose лежит
в папке "docker_voums".
Ошибки в контроллере перехватываю при помощи ExceptionHandler для единообразия.
Так же старался соблюсти принцип "fail fast".



Что нужно доделать, но не успел:
- Добавть логирование
- Дописать заполнение БД для флага "liquibase.test_data: true"
- Добавить кэш
- Дорисать Junit тесты
- Дописать интеграционные тесты.