package com.rmuhamed.params.providerlibrary.repository;

import com.rmuhamed.params.providerlibrary.Param;

public class ParamFactory {

    public static Param create(String name, String format) {
        return new Param.Builder()
                .name(name)
                .format(format)
                .build();
    }

    static Param createFromDB(int id, String name, String format) {
        return new Param.Builder()
                .id(id)
                .name(name)
                .format(format)
                .build();
    }
}
