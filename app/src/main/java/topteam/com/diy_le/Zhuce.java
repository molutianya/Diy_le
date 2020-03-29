package topteam.com.diy_le;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Zhuce extends AppCompatActivity implements View.OnClickListener {

    EditText username_v;
    EditText password_v;
    EditText password2_v;
    EditText answer_v;
    Button submit_v;
    Spinner spinner_v;
    ArrayAdapter adapter;
    Context context;
    String wt;
    String userName;
    String password;
    String password2;
    String answer;
    String[] arr = {"-请选择一个问题-", "你的小学老师叫什么名字？", "你的中学老师叫什么名字？", "你的高中老师加什么名字？"};
    UserDataBase dataBase;
    SQLiteDatabase db;
    List<String> nameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        context = Zhuce.this;
        inintView();
        //创建数据库
        dataBase = new UserDataBase(context, "user.db", null, 1);
        adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_v.setAdapter(adapter);
        spinner_v.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wt = spinner_v.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 初始化所有控件
     */
    private void inintView() {
        spinner_v = (Spinner) findViewById(R.id.sp);
        username_v = findViewById(R.id.username);
        password_v = findViewById(R.id.userpassword);
        password2_v = findViewById(R.id.userpassword2);
        answer_v = findViewById(R.id.answer);
        submit_v = findViewById(R.id.submit);
        submit_v.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                userName = username_v.getText().toString();
                password = password_v.getText().toString();
                password2 = password2_v.getText().toString();
                answer = answer_v.getText().toString();
                if (userName.equals("")) {
                    Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals("")) {
                    Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(password2)) {
                    Toast.makeText(context, "两次输入的密码不一样", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (wt == null || wt.equals("-请选择一个问题-")) {
                    Toast.makeText(context, "请选择一个问题", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (answer.equals("")) {
                    Toast.makeText(context, "还未回答问题", Toast.LENGTH_SHORT).show();
                    return;
                }
                db = dataBase.getWritableDatabase();
                //Cursor 是一个游标
                Cursor cursor = db.query(
                        "usersql", //表名
                        null, //选择的列的列名  String[]   null相当于*  查询所有
                        null, // where后面的语句  比如：id=?
                        null,  //行条件的参数 String[]  比如：and name = ?
                        null, // groupby后面的语句 分组
                        null,  //having 后面的语句 包含某个值
                        null, //orderby 后面的语句，排序
                        null  //limit 后面的语句，限定多少页
                );
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        nameList.add(name);
                    } while (cursor.moveToNext());
                }
                cursor.close();

                for (String str : nameList) {
                    if (userName.equals(str)) {
                        Toast.makeText(context, "该用户名已被注册", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                ContentValues values1 = new ContentValues();
                values1.put("name", userName);
                values1.put("password", password);
                values1.put("wenti", wt);
                values1.put("answer", answer);
                //插入数据库  1表名 2空值Null 3 ContentValues--map数据类型
                long a = db.insert("usersql", null, values1);
                if (a != -1) {
                    Toast.makeText(context, "注册成功" + a, Toast.LENGTH_SHORT).show();
                }
                db.close();
                nameList.clear();
                break;
            default:
                break;
        }
    }
}
