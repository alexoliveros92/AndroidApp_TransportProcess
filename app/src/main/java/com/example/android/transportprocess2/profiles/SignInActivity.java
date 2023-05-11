package com.example.android.transportprocess2.profiles;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android.transportprocess2.MainActivity;
import com.example.android.transportprocess2.MenuActivity;
import com.example.android.transportprocess2.R;
import com.example.android.transportprocess2.storehouse.StoreHouseActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText userSignIn;
    private TextInputEditText passwordSignIn;
    private Button btnSignIn;
    private FirebaseFirestore db;
    private RadioButton radioProduction;
    private RadioButton radioStorehouse;
    //private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        connect();
        db=FirebaseFirestore.getInstance();

        /*alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.app_name));
        alertDialog.setMessage("Loading...");
        alertDialog.setIcon(R.drawable.toledo);*/

        btnSignIn.setOnClickListener(v -> {
            String area=selectArea();
            if (!area.equals("")){
                String userSign, password;
                userSign=userSignIn.getText().toString();
                password=passwordSignIn.getText().toString();
                signIn(userSign,password,area);
            }else
            Toast.makeText(this,"Please, select an area",Toast.LENGTH_LONG).show();
            userSignIn.getText().clear();
            passwordSignIn.getText().clear();
            userSignIn.requestFocus();
        });
    }

    private void signIn(String userSignIn, String password, String area){
        db.collection("Profiles")
                .document(area)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot=task.getResult();
                        String user = documentSnapshot.getData().get("user").toString();
                        String code = documentSnapshot.getData().get("password").toString();
                        final boolean key = user.equals(userSignIn) && code.equals(password);
                        if(area.equals("Production")){
                            if(key){
                                //alertDialog.show();
                                Intent intent= new Intent(SignInActivity.this, MenuActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(SignInActivity.this,"Incorrect user",Toast.LENGTH_LONG).show();
                            }
                        }else if (area.equals("Storehouse")){
                            if(key){
                                //alertDialog.show();
                                Intent intent= new Intent(SignInActivity.this, StoreHouseActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(SignInActivity.this,"Incorrect user",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void connect() {
        userSignIn = findViewById(R.id.EdtEmailSignIn_id);
        passwordSignIn = findViewById(R.id.EdtSignInPassword_id);
        btnSignIn = findViewById(R.id.btnSingIn_id);
        radioProduction=findViewById(R.id.radioProduction);
        radioStorehouse=findViewById(R.id.radioStorehouse);
    }

    private String selectArea(){
        if (radioProduction.isChecked()){
            return "Production";
        } else if (radioStorehouse.isChecked()){
            return "Storehouse";
        }return "";
    }
}
