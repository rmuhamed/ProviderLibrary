package com.rmuhamed.params.providerlibrary;

import android.net.Uri;

import androidx.annotation.NonNull;

public final class Configuration {
    private String authority;
    private String tableName;

    private Configuration(Builder builder) {
        if (builder.authority == null) {
            throw new RuntimeException("Provider authority was not provided");
        }

        if (builder.tableName == null) {
            throw new RuntimeException("Table name was not provided");
        }

        this.authority = builder.authority;
        this.tableName = builder.tableName;
    }

    public Uri getAuthorityUri() {
        return Uri.parse(String.format("content://%s/%s", this.authority, this.tableName));
    }

    public static class Builder {
        private String authority;
        private String tableName;

        public Builder tableName(@NonNull String tableName) {
            this.tableName = tableName;
            return this;
        }

        public Builder authority(@NonNull String authority) {
            this.authority = authority;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
