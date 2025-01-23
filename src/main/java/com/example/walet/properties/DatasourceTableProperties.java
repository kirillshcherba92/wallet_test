package com.example.walet.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "datasource")
@Component
public class DatasourceTableProperties {
    private TableProperties table;

    public TableProperties getTable() {
        return table;
    }

    public void setTable(TableProperties table) {
        if (table == null) {
            throw new IllegalArgumentException("Не указан параметр с названиями таблиц");
        }
        this.table = table;
    }

    public static class TableProperties {
        private String name;
        private String logName;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            if (name.isEmpty() || name == null) {
                throw new IllegalArgumentException("Не указан параметр \"datasource.table.name\"");
            }
            this.name = name;
        }

        public String getLogName() {
            return logName;
        }

        public void setLogName(String logName) {
            if (logName.isEmpty() || logName == null) {
                throw new IllegalArgumentException("Не указан параметр \"datasource.table.logName\"");
            }
            this.logName = logName;
        }
    }
}

