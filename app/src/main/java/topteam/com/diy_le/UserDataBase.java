package topteam.com.diy_le;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class UserDataBase extends SQLiteOpenHelper {

    final static int VERSION = 1;
    final static String userSQL = "CREATE TABLE usersql(id INTEGER PRIMARY KEY AUTOINCREMENT,name text,password text,wenti text,answer text)";
    Context context;

    public UserDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    /**
     * 数据库第一次被创建时使用
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL(userSQL);
        Toast.makeText(context,"创建成功",Toast.LENGTH_SHORT).show();
    }

    /**
     * 数据库升级的时候使用
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           if(newVersion==2 && oldVersion == 1){
               //更改表结构
               //1.删除表
               db.execSQL("drop table if exists usersql");
               //2.创建一个新结构的表
               db.execSQL("CREATE TABLE usersql(id INTEGER PRIMARY KEY AUTOINCREMENT,name text,password text,sex text,wenti text,answer text)");
           }
    }
}
