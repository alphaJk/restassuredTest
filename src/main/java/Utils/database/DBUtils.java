package Utils.database;


import io.ebean.*;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    private DatabaseConfig databaseConfig;
    private Database database;

    public DBUtils(DBConfig dbConfig) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername(dbConfig.getUsername());
        dataSourceConfig.setPassword(dbConfig.getPassword());
        dataSourceConfig.setUrl(dbConfig.getUrl());
        dataSourceConfig.setDriver("com.mysql.cj.jdbc.Driver");

        databaseConfig = new DatabaseConfig();
        databaseConfig.setDataSourceConfig(dataSourceConfig);
        this.database = DatabaseFactory.create(databaseConfig);

    }


    public Map<String, Object> queryOne(String sql, Map<String, Object> parameters) {
        SqlQuery query = database.sqlQuery(sql);
        parameters.forEach(query::setParameter);
        return sqlRowToMap(query.findOne());

    }

    public Map<String, Object> queryOne(String sql, List<Object> values) {
        SqlQuery query = database.sqlQuery(sql);
        for (int i = 0; i < values.size(); i++) {
            query.setParameter(i + 1, values.get(i));
        }
        return sqlRowToMap(query.findOne());

    }

    public List<Map<String, Object>> query(String sql, Map<String, Object> parameters) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        SqlQuery query = database.sqlQuery(sql);
        parameters.forEach(query::setParameter);
        query.findEach(sqlRow -> resultList.add(sqlRowToMap(sqlRow)));
        return resultList;
    }

    public List<Map<String, Object>> query(String sql, List<Object> values) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        SqlQuery query = database.sqlQuery(sql);
        for (int i = 0; i < values.size(); i++) {
            query.setParameter(i + 1, values.get(i));
        }
        query.findEach(sqlRow -> resultList.add(sqlRowToMap(sqlRow)));
        return resultList;
    }

    public int update(String sql, Map<String, Object> parameters) {
        SqlUpdate update = database.sqlUpdate(sql);
        parameters.forEach(update::setParameter);
        return update.execute();
    }

    public int update(String sql, List<Object> values) {
        SqlUpdate update = database.sqlUpdate(sql);
        for (int i = 0; i < values.size(); i++) {
            update.setParameter(i + 1, values.get(i));
        }
        return update.execute();
    }

    public void insertBatch(String sql, List<Map<String, Object>> paramsList) {
        SqlUpdate insert = database.sqlUpdate(sql);
        try (Transaction txn = database.beginTransaction()) {
            paramsList.forEach(params -> {
                params.forEach(insert::setParameter);
                insert.addBatch();
            });
            insert.executeBatch();
            txn.commit();
        }
    }

    public void insertBatchWithValue(String sql, List<List<Object>> paramsList) {
        SqlUpdate insert = database.sqlUpdate(sql);
        try (Transaction txn = database.beginTransaction()) {
            paramsList.forEach(params -> {
                for (int i = 0; i < params.size(); i++) {
                    insert.setParameter(i + 1, params.get(i));
                }
                insert.addBatch();
            });
            insert.executeBatch();
            txn.commit();
        }
    }


    private Map<String, Object> sqlRowToMap(SqlRow sqlRow) {
        Map<String, Object> resultMap = new HashMap<>();
        if (sqlRow != null)
            sqlRow.forEach(resultMap::put);
        return resultMap;
    }
}
