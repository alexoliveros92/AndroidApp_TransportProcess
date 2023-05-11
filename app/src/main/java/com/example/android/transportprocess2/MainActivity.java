package com.example.android.transportprocess2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.transportprocess2.deliver.DeliverActivity;
import com.example.android.transportprocess2.profiles.SignInActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        FloatingActionButton actionButton = findViewById(R.id.btnGoRequestList);
        actionButton.setOnClickListener(e -> {
            startActivity(new Intent(this, DeliverActivity.class));
        });


        TabLayout tabLayout = findViewById(R.id.tabLayout_id);
        ViewPager viewPager = findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        //Add items in tabLayout
        adapter.addFragment(new FragmentOne(), getString(R.string.line_one));
        adapter.addFragment(new FragmentTwo(), getString(R.string.line_two));
        adapter.addFragment(new FragmentThree(),getString(R.string.line_three));
        adapter.addFragment(new FragmentFourth(), getString(R.string.line_four));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        // getout shadow in ActionBar
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_action_bar, menu);
        MenuItem item = menu.findItem(R.id.MainSignOut);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int position = item.getItemId();
        if (position == R.id.MainSignOut) {
            startActivity(new Intent(this, MenuActivity.class));
        }
        finish();
        return true;
    }
}
