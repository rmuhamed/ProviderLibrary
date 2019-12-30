package com.rmuhamed.params.providerlibrary;

public final class Param {
    private int id;
    private String name;
    private String format;

    Param(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.format = builder.format;
    }

    public int getId() {
        return id;
    }

    public String getFormat() {
        return format;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private int id;
        private String name;
        private String format;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder format(String format) {
            this.format = format;
            return this;
        }

        public Param build() {
            return new Param(this);
        }
    }
}

