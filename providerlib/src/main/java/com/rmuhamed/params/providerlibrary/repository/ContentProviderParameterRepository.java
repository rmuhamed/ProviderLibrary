package com.rmuhamed.params.providerlibrary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.rmuhamed.params.providerlibrary.Configuration;
import com.rmuhamed.params.providerlibrary.Param;
import com.rmuhamed.params.providerlibrary.ParamContract;
import com.rmuhamed.params.providerlibrary.QueryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public final class ContentProviderParameterRepository implements ParameterRepository {
    private final ExecutorService executorService;
    private Uri contentUri;
    private Context context;

    public ContentProviderParameterRepository(Context context, ExecutorService executorService, Configuration configuration) {
        this.contentUri = configuration.getAuthorityUri();
        this.context = context;
        this.executorService = executorService;
    }

    @Override
    public Future<Boolean> add(Param newParam) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ParamContract.NAME_PARAM_KEY, newParam.getName());
        contentValues.put(ParamContract.FORMAT_PARAM_KEY, newParam.getFormat());

        Callable<Boolean> call = () ->
                context.getContentResolver().insert(contentUri, contentValues) != null;

        return executorService.submit(call);
    }

    @Override
    public Future<Boolean> update(int paramId, Param updatedParam) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ParamContract.NAME_PARAM_KEY, updatedParam.getName());
        contentValues.put(ParamContract.FORMAT_PARAM_KEY, updatedParam.getFormat());

        String selectionClause = String.format("%s=?", ParamContract.ID_PARAM_KEY);
        String[] selectionArgs = new String[1];
        selectionArgs[0] = Integer.valueOf(paramId).toString();

        Callable<Boolean> call = () ->
                context.getContentResolver().update(contentUri, contentValues, selectionClause, selectionArgs) > 0;

        return this.executorService.submit(call);
    }

    @Override
    public Future<List<Param>> all() {
        return query(null);
    }

    @Override
    public Future<List<Param>> query(@Nullable final QueryObject queryObject) {
        Callable<List<Param>> call = () -> {
            Cursor cursor = context.getContentResolver()
                    .query(
                            contentUri
                            , null
                            , getSelectionClause(queryObject)
                            , getSelectionArgs(queryObject)
                            , ""
                    );

            if (cursor == null) {
                throw new RuntimeException("Could not be performed");
            } else {
                List<Param> result = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(ParamContract.ID_PARAM_KEY));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(ParamContract.NAME_PARAM_KEY));
                    String format = cursor.getString(cursor.getColumnIndexOrThrow(ParamContract.FORMAT_PARAM_KEY));

                    result.add(ParamFactory.createFromDB(id, name, format));
                }

                cursor.close();
                return result;
            }
        };

        return this.executorService.submit(call);
    }

    private String[] getSelectionArgs(@Nullable final QueryObject queryObject) {
        String[] selectionArgs = null;
        if (queryObject != null) {
            selectionArgs = new String[1];
            selectionArgs[0] = queryObject.getQueryValue();
        }

        return selectionArgs;
    }

    private String getSelectionClause(@Nullable final QueryObject queryObject) {
        String selectionClause = null;

        if (queryObject != null) {
            selectionClause = String.format("%s=?", queryObject.getQueryName());
        }

        return selectionClause;
    }
}