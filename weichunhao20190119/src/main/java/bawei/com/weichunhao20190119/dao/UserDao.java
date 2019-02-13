package bawei.com.weichunhao20190119.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import bawei.com.weichunhao20190119.sqlite.MySQlite;

public class UserDao {
    private SQLiteDatabase database;

    public UserDao(Context context){

        MySQlite mySQlite = new MySQlite(context);
        database = mySQlite.getWritableDatabase();
    }
    //插入的方法
    public void add(String url, String data) {
        //先删除相同url地址的数据
        database.delete("user","url=?",new String[]{url});
        ContentValues values = new ContentValues();
        values.put("url", url);//k值一定是数据库的字段值
        values.put("jsonData", data);
        //返回行号
        database.insert("user", null, values);
    }
    //查询
    public String queryData(String url){
        String data="";
        Cursor cursor = database.query("user", null, "url=?", new String[]{url}, null, null, null);
        while (cursor.moveToNext()){
            //获得jsonData字段的内容
            data= cursor.getString(cursor.getColumnIndex("jsonData"));
        }
        return data;
    }


}
