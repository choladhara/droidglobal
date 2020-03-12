package com.peasx.app.droidglobal.http.query;

import android.util.Log;

import com.peasx.app.droidglobal.http.connect.JSONResponse;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class MySQLUpdater {

    public MySQLUpdater(String tableName){
        this.tableName = tableName;
    }

    public MySQLUpdater setDb(String db){
        this.dbName= db; return this;
    }

    public MySQLUpdater setColumns(Map map){
        this.columns = new JSONObject(map);
        return this;
    }

    public MySQLUpdater setParams(Map map){
        this.params = new JSONObject(map);
        return this;
    }

    public HashMap<String, String> getParams(){
        return getMap();
    }

    public HashMap<String, String> getMap(){
        HashMap<String, String> map = new HashMap<>();
        map.put("db_name", dbName);
        map.put("table_name", tableName);
        map.put("columns", columns.toString());
        map.put("params", params.toString());

        return map;
    }


    JSONObject params, columns;
    String tableName    = "";
    String dbName       = "";

}