package com.rmuhamed.params.providerlibrary;

import android.net.Uri;

import androidx.annotation.NonNull;

public final class Configuration {
    private String authority;
    private String tableName;

    private Configuration(ProviderBuilder builder) {
        if (builder.authority == null) {
            throw new RuntimeException("Provider authority was not provided");
        }

        if (builder.tableName == null) {
            throw new RuntimeException("Table name was not provided");
        }

        this.authority = builder.authority;
        this.tableName = builder.tableName;
    }

    private Configuration(ClientBuilder builder) {
        this.tableName = builder.tableName;
        this.authority = builder.authority;
    }

    public Uri getAuthorityUri() {
        return Uri.parse(String.format("content://%s/%s", this.authority, this.tableName));
    }

    public static class ProviderBuilder {
        private String authority;
        private String tableName;

        public ProviderBuilder tableName(@NonNull String tableName) {
            this.tableName = tableName;
            return this;
        }

        public ProviderBuilder authority(@NonNull String authority) {
            this.authority = authority;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }

    public static class ClientBuilder {
        private String authority = ParamContract.AUTHORITY;
        private String tableName = ParamContract.TABLE_NAME;

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
