import java.util.*;
import java.io.*;

/**
 * DatabaseConfig - Soft-coded database configuration system
 * Provides advanced database features with configurable parameters
 */
public class DatabaseConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Soft-coded database schema configurations
    public static class TableSchema {
        private String tableName;
        private Map<String, ColumnConfig> columns;
        private List<String> primaryKeys;
        private Map<String, String> constraints;
        private Map<String, Object> defaultValues;
        
        public TableSchema(String tableName) {
            this.tableName = tableName;
            this.columns = new LinkedHashMap<>();
            this.primaryKeys = new ArrayList<>();
            this.constraints = new HashMap<>();
            this.defaultValues = new HashMap<>();
        }
        
        public TableSchema addColumn(String name, String type, boolean required) {
            columns.put(name, new ColumnConfig(name, type, required));
            return this;
        }
        
        public TableSchema setPrimaryKey(String... keys) {
            primaryKeys.addAll(Arrays.asList(keys));
            return this;
        }
        
        public TableSchema addConstraint(String column, String constraint) {
            constraints.put(column, constraint);
            return this;
        }
        
        public TableSchema setDefault(String column, Object value) {
            defaultValues.put(column, value);
            return this;
        }
        
        // Getters
        public String getTableName() { return tableName; }
        public Map<String, ColumnConfig> getColumns() { return columns; }
        public List<String> getPrimaryKeys() { return primaryKeys; }
        public Map<String, String> getConstraints() { return constraints; }
        public Map<String, Object> getDefaultValues() { return defaultValues; }
    }
    
    public static class ColumnConfig {
        private String name;
        private String dataType;
        private boolean required;
        private String validationPattern;
        private Object minValue;
        private Object maxValue;
        
        public ColumnConfig(String name, String dataType, boolean required) {
            this.name = name;
            this.dataType = dataType;
            this.required = required;
        }
        
        public ColumnConfig setValidation(String pattern) {
            this.validationPattern = pattern;
            return this;
        }
        
        public ColumnConfig setRange(Object min, Object max) {
            this.minValue = min;
            this.maxValue = max;
            return this;
        }
        
        // Getters
        public String getName() { return name; }
        public String getDataType() { return dataType; }
        public boolean isRequired() { return required; }
        public String getValidationPattern() { return validationPattern; }
        public Object getMinValue() { return minValue; }
        public Object getMaxValue() { return maxValue; }
    }
    
    // Advanced database features configuration
    public static class DatabaseFeatures {
        private boolean enableVersioning = true;
        private boolean enableAuditing = true;
        private boolean enableBackup = true;
        private boolean enableEncryption = false;
        private boolean enableCompression = true;
        private boolean enableCaching = true;
        private int maxCacheSize = 1000;
        private int backupRetentionDays = 30;
        private String compressionAlgorithm = "GZIP";
        
        // Getters and setters
        public boolean isVersioningEnabled() { return enableVersioning; }
        public boolean isAuditingEnabled() { return enableAuditing; }
        public boolean isBackupEnabled() { return enableBackup; }
        public boolean isEncryptionEnabled() { return enableEncryption; }
        public boolean isCompressionEnabled() { return enableCompression; }
        public boolean isCachingEnabled() { return enableCaching; }
        public int getMaxCacheSize() { return maxCacheSize; }
        public int getBackupRetentionDays() { return backupRetentionDays; }
        public String getCompressionAlgorithm() { return compressionAlgorithm; }
        
        public DatabaseFeatures setVersioning(boolean enable) { this.enableVersioning = enable; return this; }
        public DatabaseFeatures setAuditing(boolean enable) { this.enableAuditing = enable; return this; }
        public DatabaseFeatures setBackup(boolean enable) { this.enableBackup = enable; return this; }
        public DatabaseFeatures setEncryption(boolean enable) { this.enableEncryption = enable; return this; }
        public DatabaseFeatures setCompression(boolean enable) { this.enableCompression = enable; return this; }
        public DatabaseFeatures setCaching(boolean enable) { this.enableCaching = enable; return this; }
        public DatabaseFeatures setMaxCacheSize(int size) { this.maxCacheSize = size; return this; }
        public DatabaseFeatures setBackupRetention(int days) { this.backupRetentionDays = days; return this; }
        public DatabaseFeatures setCompressionAlgorithm(String algorithm) { this.compressionAlgorithm = algorithm; return this; }
    }
    
    // Query builder for soft-coded database operations
    public static class QueryBuilder {
        private String operation;
        private String table;
        private Map<String, Object> conditions;
        private Map<String, Object> values;
        private List<String> orderBy;
        private int limit;
        
        public QueryBuilder(String operation) {
            this.operation = operation;
            this.conditions = new HashMap<>();
            this.values = new HashMap<>();
            this.orderBy = new ArrayList<>();
            this.limit = -1;
        }
        
        public QueryBuilder from(String table) {
            this.table = table;
            return this;
        }
        
        public QueryBuilder where(String column, Object value) {
            conditions.put(column, value);
            return this;
        }
        
        public QueryBuilder set(String column, Object value) {
            values.put(column, value);
            return this;
        }
        
        public QueryBuilder orderBy(String column) {
            orderBy.add(column);
            return this;
        }
        
        public QueryBuilder limit(int count) {
            this.limit = count;
            return this;
        }
        
        public String build() {
            StringBuilder query = new StringBuilder();
            query.append(operation.toUpperCase()).append(" ");
            
            switch (operation.toLowerCase()) {
                case "select":
                    query.append("* FROM ").append(table);
                    break;
                case "insert":
                    query.append("INTO ").append(table);
                    break;
                case "update":
                    query.append(table).append(" SET ");
                    break;
                case "delete":
                    query.append("FROM ").append(table);
                    break;
            }
            
            if (!conditions.isEmpty()) {
                query.append(" WHERE ");
                conditions.forEach((k, v) -> query.append(k).append("=").append(v).append(" AND "));
                query.setLength(query.length() - 5); // Remove last " AND "
            }
            
            if (!orderBy.isEmpty()) {
                query.append(" ORDER BY ").append(String.join(", ", orderBy));
            }
            
            if (limit > 0) {
                query.append(" LIMIT ").append(limit);
            }
            
            return query.toString();
        }
        
        // Getters
        public String getOperation() { return operation; }
        public String getTable() { return table; }
        public Map<String, Object> getConditions() { return conditions; }
        public Map<String, Object> getValues() { return values; }
        public List<String> getOrderBy() { return orderBy; }
        public int getLimit() { return limit; }
    }
    
    // Database statistics and monitoring
    public static class DatabaseStats {
        private long totalRecords;
        private long totalSize;
        private Map<String, Long> tableRecordCounts;
        private Map<String, Long> tableSizes;
        private Date lastBackup;
        private Date lastOptimization;
        private List<String> recentQueries;
        
        public DatabaseStats() {
            this.tableRecordCounts = new HashMap<>();
            this.tableSizes = new HashMap<>();
            this.recentQueries = new ArrayList<>();
        }
        
        public void updateTableStats(String table, long records, long size) {
            tableRecordCounts.put(table, records);
            tableSizes.put(table, size);
            recalculateTotals();
        }
        
        private void recalculateTotals() {
            totalRecords = tableRecordCounts.values().stream().mapToLong(Long::longValue).sum();
            totalSize = tableSizes.values().stream().mapToLong(Long::longValue).sum();
        }
        
        public void addQuery(String query) {
            recentQueries.add(query);
            if (recentQueries.size() > 100) {
                recentQueries.remove(0);
            }
        }
        
        // Getters
        public long getTotalRecords() { return totalRecords; }
        public long getTotalSize() { return totalSize; }
        public Map<String, Long> getTableRecordCounts() { return tableRecordCounts; }
        public Map<String, Long> getTableSizes() { return tableSizes; }
        public Date getLastBackup() { return lastBackup; }
        public Date getLastOptimization() { return lastOptimization; }
        public List<String> getRecentQueries() { return recentQueries; }
        
        public void setLastBackup(Date date) { this.lastBackup = date; }
        public void setLastOptimization(Date date) { this.lastOptimization = date; }
    }
    
    // Configuration presets for different use cases
    public static class ConfigurationPresets {
        public static DatabaseFeatures getProductionConfig() {
            return new DatabaseFeatures()
                .setVersioning(true)
                .setAuditing(true)
                .setBackup(true)
                .setEncryption(true)
                .setCompression(true)
                .setCaching(true)
                .setMaxCacheSize(2000)
                .setBackupRetention(90);
        }
        
        public static DatabaseFeatures getDevelopmentConfig() {
            return new DatabaseFeatures()
                .setVersioning(false)
                .setAuditing(false)
                .setBackup(false)
                .setEncryption(false)
                .setCompression(false)
                .setCaching(true)
                .setMaxCacheSize(500)
                .setBackupRetention(7);
        }
        
        public static DatabaseFeatures getPerformanceConfig() {
            return new DatabaseFeatures()
                .setVersioning(false)
                .setAuditing(false)
                .setBackup(true)
                .setEncryption(false)
                .setCompression(true)
                .setCaching(true)
                .setMaxCacheSize(5000)
                .setBackupRetention(30);
        }
    }
    
    // Default schema definitions
    public static Map<String, TableSchema> getDefaultSchemas() {
        Map<String, TableSchema> schemas = new HashMap<>();
        
        // Materials table schema
        schemas.put("materials", new TableSchema("materials")
            .addColumn("id", "INTEGER", true)
            .addColumn("name", "VARCHAR(100)", true)
            .addColumn("thickness", "DECIMAL(5,2)", true)
            .addColumn("hardness", "VARCHAR(50)", true)
            .addColumn("dotDepth", "DECIMAL(5,2)", true)
            .addColumn("markingSpeed", "VARCHAR(20)", true)
            .addColumn("notes", "TEXT", false)
            .addColumn("createdDate", "DATETIME", true)
            .addColumn("modifiedDate", "DATETIME", false)
            .setPrimaryKey("id")
            .addConstraint("thickness", "POSITIVE")
            .addConstraint("dotDepth", "POSITIVE")
            .setDefault("markingSpeed", "Medium")
            .setDefault("createdDate", "CURRENT_TIMESTAMP"));
        
        // Projects table schema
        schemas.put("projects", new TableSchema("projects")
            .addColumn("id", "INTEGER", true)
            .addColumn("name", "VARCHAR(100)", true)
            .addColumn("description", "TEXT", false)
            .addColumn("projectData", "BLOB", true)
            .addColumn("fileSize", "BIGINT", true)
            .addColumn("createdDate", "DATETIME", true)
            .addColumn("modifiedDate", "DATETIME", false)
            .addColumn("version", "INTEGER", false)
            .setPrimaryKey("id")
            .setDefault("version", 1)
            .setDefault("createdDate", "CURRENT_TIMESTAMP"));
        
        // Templates table schema
        schemas.put("templates", new TableSchema("templates")
            .addColumn("id", "INTEGER", true)
            .addColumn("name", "VARCHAR(100)", true)
            .addColumn("category", "VARCHAR(50)", true)
            .addColumn("templateData", "TEXT", true)
            .addColumn("usageCount", "INTEGER", false)
            .addColumn("createdDate", "DATETIME", true)
            .addColumn("tags", "VARCHAR(200)", false)
            .setPrimaryKey("id")
            .setDefault("usageCount", 0)
            .setDefault("category", "General")
            .setDefault("createdDate", "CURRENT_TIMESTAMP"));
        
        // Settings table schema
        schemas.put("settings", new TableSchema("settings")
            .addColumn("id", "INTEGER", true)
            .addColumn("profileName", "VARCHAR(50)", true)
            .addColumn("settingType", "VARCHAR(50)", true)
            .addColumn("settingValue", "TEXT", true)
            .addColumn("description", "TEXT", false)
            .addColumn("createdDate", "DATETIME", true)
            .addColumn("isActive", "BOOLEAN", false)
            .setPrimaryKey("id")
            .setDefault("isActive", true)
            .setDefault("createdDate", "CURRENT_TIMESTAMP"));
        
        return schemas;
    }
}
