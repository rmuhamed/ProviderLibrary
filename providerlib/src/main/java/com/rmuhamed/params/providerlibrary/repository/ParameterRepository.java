package com.rmuhamed.params.providerlibrary.repository;

import com.rmuhamed.params.providerlibrary.Param;
import com.rmuhamed.params.providerlibrary.QueryObject;

import java.util.List;
import java.util.concurrent.Future;

public interface ParameterRepository {
    Future<Boolean> add(Param newParam);
    Future<Boolean> update(int paramId, Param updatedParam);
    Future<List<Param>> query(QueryObject queryObject);
    Future<List<Param>> all();
}
