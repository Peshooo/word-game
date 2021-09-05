package com.wordgame.recordsstorage.model;

import java.util.Map;

public class SqlParameterQuery {
    private String query;
    private Map<String, Object> parameters;

    public SqlParameterQuery(String query, Map<String, Object> parameters) {
        this.query = query;
        this.parameters = parameters;
    }

    public String getQuery() {
        return query;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
