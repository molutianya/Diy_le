package topteam.com.diy_le;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView zc_v;
    TextView wjmm_v;
    Context context;
    EditText userName_v;
    EditText password_v;
    CheckBox ck_v;
    Button login_v;
    private long exitTime = 0;
    static String name = "";
    static String name1;
    static String password = "";
    static String password1;
    UserDataBase dataBase;
    SQLiteDatabase db;

    boolean flg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }
        initView();
        context = MainActivity.this;
        dataBase = new UserDataBase(context, "user.db", null, 1);


        ck_v.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flg = isChecked;
            }
        });

    }

    /**
     * 初始化所有控件
     */
    private void initView() {
        zc_v = (TextView) findViewById(R.id.zc);
        wjmm_v = findViewById(R.id.wjmm);
        wjmm_v.setOnClickListener(this);
        zc_v.setOnClickListener(this);
        userName_v = findViewById(R.id.username);
        password_v = findViewById(R.id.password);
        ck_v = findViewById(R.id.ck);
        login_v = findViewById(R.id.login);
        login_v.setOnClickListener(this);
    }

    /**
     * 保存用户名和用户密码
     */
    private void saveUserData(String name, String password) {
        /**
         *  获取SharedPreferences 对象
         *  data 保存的文件名称
         *  MODE_PRIVATE 保存的模式，默认  会以覆盖的形式保存
         */
        SharedPreferences.Editor editor = getSharedPreferences("userData", MODE_PRIVATE).edit();
        editor.putString("username", name); //存入用户名
        editor.putString("password", password);
        editor.apply(); //提交数据
    }

    /**
     * 读取用户名和用户密码
     */
    private void getUserData() {
        /**
         * 读取保存的数据
         * data 从哪个文件读取
         * MODE_PRIVATE 读取的形式
         */
        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        name = preferences.getString("username","");
        password = preferences.getString("password","");
        userName_v.setText(name);
        password_v.setText(password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zc:
                Intent intent = new Intent(this, Zhuce.class);
                startActivity(intent);
                break;
            case R.id.wjmm:
                Intent intent1 = new Intent(this, Wjmm.class);
                startActivity(intent1);
                break;
            case R.id.login:
                name = userName_v.getText().toString();
                password = password_v.getText().toString();
                db = dataBase.getWritableDatabase();
                Cursor cursor = db.query(
                        "usersql",
                        new String[]{"name", "password"},
                        "name=?",
                        new String[]{name},
                        null,
                        null,
                        null
                );
                if (cursor.moveToFirst()) {
                    do {
                        name1 = cursor.getString(cursor.getColumnIndex("name"));
                        password1 = cursor.getString(cursor.getColumnIndex("password"));
                    } while (cursor.moveToNext());
                }
                if (!name.equals(name1)) {
                    Toast.makeText(context, "用户不存在", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(password1)) {
                    Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
                db.close();
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
               /* Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);*/
            }
            return true;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Main", "onStart");
       getUserData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Main", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Main", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Main", "onStop");
        name = userName_v.getText().toString();
        password = password_v.getText().toString();
        if (flg && !name.equals("") && !password.equals("")) {
            saveUserData(name, password);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Main", "onDestroy");
    }


}
