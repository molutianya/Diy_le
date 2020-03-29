package topteam.com.diy_le;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Wjmm extends AppCompatActivity {
    EditText username_v;
    Spinner spinner_v;
    EditText answer_v;
    Context context;
    String name;
    String wt;
    String answer;
    String[] arr = {"-请选择一个问题-", "你的小学老师叫什么名字？", "你的中学老师叫什么名字？", "你的高中老师加什么名字？"};
    ArrayAdapter adapter;
    SQLiteDatabase db;
    UserDataBase dataBase;
    String name1;
    String wenti1;
    String answer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wjmm);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        initView();
        context = Wjmm.this;
        dataBase = new UserDataBase(context, "user.db", null, 1);
        adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, arr);
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
    private void initView() {
        username_v = findViewById(R.id.username);
        spinner_v = findViewById(R.id.sp);
        answer_v = findViewById(R.id.answer);
    }

    /**
     * 提交修改密码的资料
     *
     * @param view
     */
    public void submit(View view) {
        name = username_v.getText().toString();
        answer = answer_v.getText().toString();
        Log.d("jsfogjiyh", name + "   " + wt);
        if (name.equals("")) {
            Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (wt.equals(arr[0])) {
            Toast.makeText(context, "请选择一个注册时设定的问题", Toast.LENGTH_SHORT).show();
            return;
        }
        if (answer.equals("")) {
            Toast.makeText(context, "还未回答问题", Toast.LENGTH_SHORT).show();
            return;
        }
        db = dataBase.getWritableDatabase();
        Cursor cursor = db.query(
                "usersql",
                new String[]{"name", "wenti", "answer"},
                "name=?",
                new String[]{name},
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                name1 = cursor.getString(cursor.getColumnIndex("name"));
                wenti1 = cursor.getString(cursor.getColumnIndex("wenti"));
                answer1 = cursor.getString(cursor.getColumnIndex("answer"));
                Log.d("jfsiofjergsd", name1 + "    " + wenti1 + "    " + answer1);

            } while (cursor.moveToNext());
        }
        cursor.close();
        if (name.equals(name1) && wt.equals(wenti1) && answer.equals(answer1)) {
            Intent intent = new Intent(Wjmm.this, UpPassword.class);
            intent.putExtra("username", name);
            startActivity(intent);
        } else {
            Toast.makeText(context, "回答错误", Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ofjdscds", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("ofjdscds", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ofjdscds", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ofjdscds", "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ofjdscds", "onDestroy");
    }
}
