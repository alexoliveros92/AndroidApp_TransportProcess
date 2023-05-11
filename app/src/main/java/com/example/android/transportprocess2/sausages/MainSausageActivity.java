package com.example.android.transportprocess2.sausages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.transportprocess2.MenuActivity;
import com.example.android.transportprocess2.R;
import com.example.android.transportprocess2.ViewPagerAdapter;
import com.example.android.transportprocess2.deliver.DeliverActivity;
import com.example.android.transportprocess2.profiles.SignInActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainSausageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sausage);

        Toolbar mainToolbar = findViewById(R.id.mainSausage_toolbar);
        setSupportActionBar(mainToolbar);

        FloatingActionButton actionButton = findViewById(R.id.btnGoRequestListSausage);
        actionButton.setOnClickListener(e -> {
            startActivity(new Intent(this, DeliverActivity.class));
        });


        TabLayout tabLayout = findViewById(R.id.tabLayoutSausage_id);
        ViewPager viewPager = findViewById(R.id.viewpagerSausage_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        //Add items in tabLayout
        adapter.addFragment(new FragmentOneSausage(), getString(R.string.line_seydelmann));
        adapter.addFragment(new FragmentTwoSausage(), getString(R.string.line_weiler));
        adapter.addFragment(new FragmentThreeSausage(),getString(R.string.line_cutter));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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
