package com.example.android.transportprocess2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.transportprocess2.profiles.SignInActivity;
import com.example.android.transportprocess2.sausages.MainSausageActivity;

public class MenuActivity extends AppCompatActivity {

    private ImageView imageView;
    private LinearLayout linearLayout;
    private LinearLayout linearLayoutChorizos;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        connect();

        setSupportActionBar(toolbar);

        linearLayout.setOnClickListener(v -> {
            startActivity(new Intent(this, MainSausageActivity.class));
            finish();
        });

        linearLayoutChorizos.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        });
    }

    private void connect(){
        imageView = findViewById(R.id.goChorizo);
        linearLayout = findViewById(R.id.sausagesPlace);
        linearLayoutChorizos = findViewById(R.id.chorizosPlace);
        toolbar = findViewById(R.id.menu_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SignOut:
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
