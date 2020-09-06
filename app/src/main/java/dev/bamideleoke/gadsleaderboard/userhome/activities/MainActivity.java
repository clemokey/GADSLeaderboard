package dev.bamideleoke.gadsleaderboard.userhome.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import dev.bamideleoke.gadsleaderboard.R;
import dev.bamideleoke.gadsleaderboard.userhome.fragments.FragmentLearning;
import dev.bamideleoke.gadsleaderboard.userhome.fragments.FragmentSkill;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;

    MaterialButton submitBtn;
    Fragment skillFragment, learningFragment;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        skillFragment = new FragmentSkill(this, tabLayout);
        learningFragment = new FragmentLearning(this, tabLayout);
        submitBtn = findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SubmitActivity.class));
            }
        });
        viewPager = findViewById(R.id.view_pager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return learningFragment;
                case 1:
                    return skillFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Learning Leaders";
                case 1:
                    return "Skill IQ Leaders";
                default:
                    return null;
            }
        }
    }
}