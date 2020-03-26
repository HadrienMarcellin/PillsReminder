package com.example.pillsreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.pillsreminder.R;
import com.example.pillsreminder.fragments.FoodTabFragment;
import com.example.pillsreminder.fragments.PillTabFragment;
import com.example.pillsreminder.fragments.SportTabFragment;
import com.example.pillsreminder.tabAdapters.SITabAdapter;
import com.example.pillsreminder.viewModels.PillViewModel;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private SITabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PillViewModel mPillViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new SITabAdapter(getSupportFragmentManager());
        adapter.addFragment(new PillTabFragment(), PillTabFragment.title);
        adapter.addFragment(new SportTabFragment(), SportTabFragment.title);
        adapter.addFragment(new FoodTabFragment(), FoodTabFragment.title);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}
