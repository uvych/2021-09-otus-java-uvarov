package ru.otus;

import org.flywaydb.core.*;
import org.slf4j.*;
import ru.otus.core.repository.executor.*;
import ru.otus.core.sessionmanager.*;
import ru.otus.crm.datasource.*;
import ru.otus.crm.model.*;
import ru.otus.crm.service.*;
import ru.otus.mapper.*;
import ru.otus.mapper.mapper.*;

import javax.sql.*;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

// Работа с клиентом
        var entityClassMetaDataClient  = new EntityClassMetaDataImpl<>(Client.class);

        var entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var instanceMapperClient = new InstanceHelper<>(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataClient, instanceMapperClient); //реализация DataTemplate, универсальная

//Client with cache
        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        dbServiceClient.saveClient(new Client("dbServiceFirst"));

        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));

        long startTimeClientGet = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        }
        long endTimeClientGet = System.currentTimeMillis() - startTimeClientGet;


// Manager without cache

        var entityClassMetaDataManager =  new EntityClassMetaDataImpl<>(Manager.class);
        var entitySQLMetaDataManager =  new EntitySQLMetaDataImpl(entityClassMetaDataManager);
        var instanceHelperManager = new InstanceHelper<>(entityClassMetaDataManager);
        var dataTemplateManager = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataManager, instanceHelperManager);

        var dbServiceManager = new DbServiceManagerImpl(transactionRunner, dataTemplateManager);
        dbServiceManager.saveManager(new Manager("ManagerFirst"));

        var managerSecond = dbServiceManager.saveManager(new Manager("ManagerSecond"));

        long startTimeManagerGet = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            var managerSecondSelected = dbServiceManager.getManager(managerSecond.getNo())
                .orElseThrow(() -> new RuntimeException("Manager not found, id:" + managerSecond.getNo()));
        }
        long endTimeManagerGet = System.currentTimeMillis() - startTimeManagerGet;
        log.info("TIME FOR GET MANAGER: {}", endTimeManagerGet);
        log.info("TIME FOR GET Client: {}", endTimeClientGet);

        /*TIME FOR GET MANAGER: 241
        TIME FOR GET Client: 2*/
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
