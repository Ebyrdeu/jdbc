package dev.ebyrdeu.database;

public class SqlBuilder {

    private final StringBuilder query;


    public SqlBuilder() {
        this.query = new StringBuilder();
    }

    public SqlBuilder select(String... columns) {
        query.append("SELECT ");
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i < columns.length - 1) query.append(", ");
        }
        return this;
    }

    public SqlBuilder select() {
        query.append("SELECT * ");
        return this;
    }

    public SqlBuilder from(String table) {
        query.append("FROM ").append(table);
        return this;
    }

    public <T> SqlBuilder where(String column, T value) {
        query.append(" WHERE ").append(column).append(" = ").append(value);

        return this;
    }

    public SqlBuilder where(String column) {
        query.append(" WHERE ").append(column).append(" = ?");

        return this;
    }

    public SqlBuilder insert(String table, String... columns) {
        query.append("INSERT INTO ").append(table);
        query.append(" (");

        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i < columns.length - 1) query.append(", ");
        }
        query.append(") ");
        query.append("VALUES");
        query.append("(");

        for (int i = 0; i < columns.length; i++) {
            query.append("?");
            if (i < columns.length - 1) query.append(", ");
        }

        query.append(")");
        return this;
    }

    public SqlBuilder update(String table, String... columns) {
        query.append("UPDATE ").append(table).append(" SET ");

        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]).append(" = ?");
            if (i < columns.length - 1) query.append(", ");
        }

        return this;
    }

    public SqlBuilder delete(String column) {
        query.append("DELETE FROM ").append(column);
        return this;
    }

    public SqlBuilder count(String table, String column, boolean distinct) {
        query.append("SELECT ").append("COUNT(");
        if (distinct) query.append("DISTINCT ");
        query.append(column).append(") ");
        query.append("FROM ").append(table);
        return this;
    }

    public SqlBuilder join(String table, String join, String on) {
        query.append("SELECT * FROM ").append(table);
        query.append(" LEFT JOIN ").append(join);
        query.append(" ON ").append(table).append(".").append(on).append(" = ").append(join).append(".").append(on);
        return this;
    }

    public String execute() {

        return query.toString();
    }
}
