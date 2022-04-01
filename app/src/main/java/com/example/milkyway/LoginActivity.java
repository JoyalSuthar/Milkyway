package com.example.milkyway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email_login,name,password;
    private Button Loginbtn;
    private Spinner spinner,spinner1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email_login = findViewById(R.id.email_login);
        password = findViewById(R.id.pass_login);
        Loginbtn = findViewById(R.id.Loginbtn);
        spinner = findViewById(R.id.spinner);
        spinner1 = findViewById(R.id.spinner2);

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        List<String> Roles1 = new ArrayList<>();
        Roles1.add(0,"Login");
        Roles1.add("Customer");
        Roles1.add("Retailer");
        Roles1.add("Delivery Boy");

        ArrayAdapter<String> dataadapter1;
        dataadapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Roles1);

        dataadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataadapter1);

        List<String> Roles = new ArrayList<>();
        Roles.add(0,"Sign up");
        Roles.add("Customer");
        Roles.add("Retailer");
        Roles.add("Delivery Boy");

        //Styler and populate spinner
        ArrayAdapter<String> dataadapter;
        dataadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,Roles);

        //dropdown layout stlye
        dataadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching sipnner to adapter
        spinner.setAdapter(dataadapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                if(parent.getItemAtPosition(position).equals("Sign up")){

                    //do nothing
                }
                else{
                    //on selecting a spinner item
                    String item = parent.getItemAtPosition(position).toString();
                    //show the selected
//                    Toast.makeText(parent.getContext(), "Selected"+item, Toast.LENGTH_SHORT).show();
                    //anything other action do here
                    if(parent.getItemAtPosition(position).equals("Customer")){

                        Intent intent = new Intent(LoginActivity.this,Signup_Customer.class);
                        startActivity(intent);
                    }
                    if(parent.getItemAtPosition(position).equals("Retailer")){
                        Intent intent = new Intent(LoginActivity.this,Signup_Retailer.class);
                        startActivity(intent);

                    }
                   if(parent.getItemAtPosition(position).equals("Delivery Boy"))
                    {
                        Intent intent = new Intent(LoginActivity.this,Signup_DeliveryBoy.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                    //TO-DO autogenerated
            }
        });
    }

    private void login(){
        String email = email_login.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String item = spinner1.getSelectedItem().toString();
        if(email.isEmpty())
        {
            email_login.setError("Email can not be empty");
            email_login.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            email_login.setError("please provide valid email!!");
            email_login.requestFocus();
        }
        if(pass.isEmpty())
        {
            password.setError("password can not be empty");
            password.requestFocus();
        }
       else{
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful() && item.equals("Customer"))
                    {

                        Toast.makeText(LoginActivity.this, "Login Successful!!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,CustomerHome.class));
                    }else if(task.isSuccessful() && item.equals("Retailer")){

                        Toast.makeText(LoginActivity.this, "Login Successful!!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,RetailerHome.class));

                    }else if(task.isSuccessful() && item.equals("Delivery Boy")){

                        Toast.makeText(LoginActivity.this, "Login Successful!!!", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(LoginActivity.this,demohome.class));
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}