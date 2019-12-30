package com.rmuhamed.params.providerlibrary.repository;

import com.rmuhamed.params.providerlibrary.Param;
import com.rmuhamed.params.providerlibrary.QueryObject;

import java.util.List;

public interface ParameterRepository {
    boolean add(Param newParam);
    boolean update(int paramId, Param updatedParam);
    List<Param> query(QueryObject queryObject);
    List<Param> all();
}
