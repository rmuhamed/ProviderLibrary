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

public final class ContentProviderParameterRepository implements ParameterRepository {
    private Uri contentUri;
    private Context context;

    public ContentProviderParameterRepository(Context context, Configuration configuration) {
        this.contentUri = configuration.getAuthorityUri();
        this.context = context;
    }

    @Override
    public boolean add(Param newParam) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ParamContract.NAME_PARAM_KEY, newParam.getName());
        contentValues.put(ParamContract.FORMAT_PARAM_KEY, newParam.getFormat());

        return context.getContentResolver().insert(contentUri, contentValues) != null;
    }

    @Override
    public boolean update(int paramId, Param updatedParam) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ParamContract.NAME_PARAM_KEY, updatedParam.getName());
        contentValues.put(ParamContract.FORMAT_PARAM_KEY, updatedParam.getFormat());

        String selectionClause = String.format("%s=?", ParamContract.ID_PARAM_KEY);
        String[] selectionArgs = new String[1];
        selectionArgs[0] = Integer.valueOf(paramId).toString();

        return context.getContentResolver()
                .update(contentUri, contentValues, selectionClause, selectionArgs) > 0;
    }

    @Override
    public List<Param> all() {
        return query(null);
    }

    @Override
    public List<Param> query(@Nullable QueryObject queryObject) {
        String selectionClause = null;
        String[] selectionArgs = null;

        if (queryObject != null) {
            selectionClause = String.format("%s=?", queryObject.getQueryName());
            selectionArgs = new String[1];
            selectionArgs[0] = queryObject.getQueryValue();
        }

        Cursor cursor = context.getContentResolver()
            .query(
                contentUri
                , null
                , selectionClause
                , selectionArgs
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
    }
}