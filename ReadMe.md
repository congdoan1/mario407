1- Data base:
 - download and install PostgresSQL data base: https://www.postgresql.org/download/
 - setup user:
    + username: mario
    + password: mario407
 - create schema: mario407
 
 2- Messaging Server: RabbitMQ 
 - download and install RabbitMQ: https://www.rabbitmq.com/download.html
 - install management plugin: rabbitmq-plugins enable rabbitmq_management
 
 - go to page http://localhost:15672/ in order to create exchange and queues.
  + create 2 queues: NEW_POST, UNHEALTHY_POST
  + Create exchange: POST
  + bind POST->NEW_POST by routing key NEW.POST 
  + bind POST->NEW_POST by routing key NEW.POST
  
 3-Open the project and let rock.
 
  
  