package com.rmuhamed.params.providerlibrary;

public final class QueryObject {
    private final String queryValue;
    private final String queryName;

    public QueryObject(String queryName, String queryValue) {
        this.queryName = queryName;
        this.queryValue = queryValue;
    }

    public String getQueryName() {
        return queryName;
    }

    public String getQueryValue() {
        return queryValue;
    }
}
