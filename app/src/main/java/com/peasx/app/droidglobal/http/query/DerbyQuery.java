package com.peasx.app.droidglobal.http.query;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DerbyQuery {
    public DerbyQuery(){}

    public DerbyQuery(String tableName){
        this.tableName = tableName;
    }

    public DerbyQuery setDb(String dbName){
            this.dbName = dbName;
            return this;
    }

    public DerbyQuery selectAll(){
        this.select = " * ";
        return this;
    }

    public DerbyQuery select(String tableColumns){
        this.select = tableColumns;
        return this;
    }

    public DerbyQuery count(String count, String as){
        this.select = " COUNT( "+ count+" ) AS "+as;
        return this;
    }

    public DerbyQuery sum(String column, String as){
        this.select = " SUM( "+ column+" ) AS "+as;
        return this;
    }

    public DerbyQuery innerJoin(String table2, String column_1, String column_2){
        innerJoin.append(" INNER JOIN  ").append(table2)
                .append(" ON ").append(column_1)
                .append(" = ").append(column_2);
        return this;
    }

    public DerbyQuery orderBy(String column, boolean asc){
        this.orderBy = " ORDER BY " + column + (asc ? " ASC " : " DESC ");
        return this;
    }

    public DerbyQuery limit(int limit){
        this.limit = limit; return this;
    }

    public DerbyQuery offset(int offset){
        this.offset = offset; return this;
    }

    public DerbyQuery groupBy(String tableColumns){
        this.groupBy = " GROUP BY " + tableColumns;
        return this;
    }

    public DerbyQuery groupBy(String[] tableColumns){
        StringBuilder queryBuilder = new StringBuilder(" GROUP BY ");
        for(int i = 0; i < tableColumns.length ; i++){
            queryBuilder.append((i < tableColumns.length-1) ? tableColumns[i] + " , " : tableColumns[i]);
        }
        this.groupBy = queryBuilder.toString();
        return this;
    }

    public DerbyQuery where(String condition){
        where.append( " WHERE ").append(condition);
        return this;
    }

    public DerbyQuery where(String column, String value){
        where.append(" WHERE ").append(column).append(" = '").append(value).append("' ");
        return this;
    }

    public DerbyQuery where(String column, long value){
        where.append(" WHERE ").append(column).append(" = ").append(value);
        return this;
    }

    public DerbyQuery where(String column, int value){
        where.append(" WHERE ").append(column).append(" = ").append(value);
        return this;
    }

    public DerbyQuery where(String column, double value){
        where.append(" WHERE ").append(column).append(" = ").append(value);
        return this;
    }

    public DerbyQuery filter(String filter, Object[] args){
        where.append(" WHERE ").append(filter);
        paramsList  = new JSONArray();
        for(int i = 0; i < args.length ; i++){
            paramsList.put(args[i]);
        }
        return this;
    }

    public Map getParam(){
        return getMap();
    }

    public Map getParam(String query, Object[] args){
        JSONObject json = new JSONObject();
        try {
            json.put("db_name", dbName);
            json.put("query",   query);
            json.put("param",   paramsList);
        }catch (JSONException ex){
            Log.d(QUERY_BUILDER, ex.toString());}

        Map map = new HashMap();
        map.put("json", json.toString());
        return map;
    }

    public Map getMap(){
        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        queryBuilder.append(select).append(" FROM ").append(tableName).append(" ");

        queryBuilder.append(!innerJoin.toString().isEmpty() ? innerJoin : "");
        queryBuilder.append(!where.toString().isEmpty() ? where : "");
        queryBuilder.append(!groupBy.isEmpty() ? groupBy : "");
        queryBuilder.append(!orderBy.isEmpty() ? orderBy : "");
        queryBuilder.append(offset  > 0 ? " OFFSET " + offset + " ROWS " : "");
        queryBuilder.append(limit   > 0 ? " FETCH FIRST " + limit  +" ROWS ONLY" : "");

        JSONObject json = new JSONObject();

        try {
            json.put("db_name", dbName);
            json.put("query", queryBuilder.toString());
            json.put("param", paramsList);
        }catch (JSONException ex){
            Log.d(QUERY_BUILDER, ex.toString());
        }

        Map map = new HashMap();
        map.put("json", json.toString());
        return map;
    }


    private String dbName       = "";
    private String tableName    = "";
    private String select       = "";
    private int limit           = 0;
    private int offset          = 0;
    private String groupBy      = "";
    private String orderBy      = "";
    private JSONArray paramsList = new JSONArray();
    private StringBuilder innerJoin     = new StringBuilder();
    private StringBuilder where         = new StringBuilder();
    final String QUERY_BUILDER          = "QUERY_BUILDER";

}
