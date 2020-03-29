package topteam.com.diy_le;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpPassword extends AppCompatActivity {
    EditText newpa1_v;
    EditText newpa2_v;
    Context context;
    UserDataBase dataBase;
    SQLiteDatabase db;
    String newPa1;
    String newPa2;
    String getname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_password);
        context = UpPassword.this;
        initView();
        ActionBar b = getSupportActionBar();
        if(b!=null){
            b.hide();
        }
        dataBase = new UserDataBase(context,"user.db",null,1);
        //接受传递过来的值
        Intent intent = getIntent();
        getname = intent.getStringExtra("username");
    }


    /**
     * 初始化所有控件
     */
    private void initView(){
        newpa1_v = findViewById(R.id.newpa1);
        newpa2_v = findViewById(R.id.newps2);
    }

    /**
     * 提交修改密码
     * @param view
     */
    public void submit(View view){
        newPa1 = newpa1_v.getText().toString();
        newPa2 = newpa2_v.getText().toString();
        if(!newPa1.equals(newPa2)){
            Toast.makeText(context,"两次输入的密码不一致,请重新输入",Toast.LENGTH_SHORT).show();
            return;
        }
        db = dataBase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password",newPa1);
       int  ss =  db.update(
                "usersql",
                values,
                "name=?",
                new String[]{getname}
        );
      if(ss>-1){
          Toast.makeText(context,"密码修改成功",Toast.LENGTH_SHORT).show();

          Intent intent = new Intent(UpPassword.this,MainActivity.class);
          startActivity(intent);
      }

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
