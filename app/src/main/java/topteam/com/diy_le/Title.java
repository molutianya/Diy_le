package topteam.com.diy_le;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import topteam.com.diy_le.fragment.Title_four;
import topteam.com.diy_le.fragment.Title_one;
import topteam.com.diy_le.fragment.Title_three;
import topteam.com.diy_le.fragment.Title_two;

public class Title extends AppCompatActivity {
    ViewPager viewPager;
    List<Fragment> list = new ArrayList<>();
    LinearLayout layout;
    TextView[] arr;
    Button input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.hide();
        }

        viewPager = findViewById(R.id.viewpage);
        input = findViewById(R.id.input);
        input.setVisibility(View.GONE);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Title.this,MainActivity.class);
                startActivity(intent);
            }
        });
        list.add(new Title_one());
        list.add(new Title_two());
        list.add(new Title_three());
        list.add(new Title_four());

        layout = findViewById(R.id.ly);
        int cout = layout.getChildCount();
        arr = new TextView[cout];
        for (int i = 0; i < cout; i++) {
            arr[i] = (TextView) layout.getChildAt(i);
            arr[i].setTag(i);
        }
        arr[0].setBackgroundColor(Color.GREEN);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        arr[1].setBackgroundColor(Color.WHITE);
                        arr[2].setBackgroundColor(Color.WHITE);
                        arr[3].setBackgroundColor(Color.WHITE);
                        arr[0].setBackgroundColor(Color.GREEN);
                        input.setVisibility(View.GONE);
                        break;
                    case 1:
                        arr[0].setBackgroundColor(Color.WHITE);
                        arr[2].setBackgroundColor(Color.WHITE);
                        arr[3].setBackgroundColor(Color.WHITE);
                        arr[1].setBackgroundColor(Color.GREEN);
                        input.setVisibility(View.GONE);
                        break;
                    case 2:
                        arr[0].setBackgroundColor(Color.WHITE);
                        arr[1].setBackgroundColor(Color.WHITE);
                        arr[3].setBackgroundColor(Color.WHITE);
                        arr[2].setBackgroundColor(Color.GREEN);
                        input.setVisibility(View.GONE);
                        break;
                    case 3:
                        arr[0].setBackgroundColor(Color.WHITE);
                        arr[1].setBackgroundColor(Color.WHITE);
                        arr[2].setBackgroundColor(Color.WHITE);
                        arr[3].setBackgroundColor(Color.GREEN);
                        input.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class MyAdapter extends FragmentStatePagerAdapter {

        List<Fragment> fList;

        public MyAdapter(FragmentManager fm, List<Fragment> fList) {
            super(fm);
            this.fList = fList;
        }

        @Override
        public Fragment getItem(int position) {
            return fList.get(position);

        }

        @Override
        public int getCount() {
            return fList.size();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
