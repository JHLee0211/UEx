package com.example.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo_94.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;

public class WithdrawActivity extends AppCompatActivity {
    private Button withdraw_btn_withdraw;
    private EditText withdraw_edit_id,withdraw_edit_password,withdraw_edit_name;
    private String curip = new getIP().getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        Intent intent = getIntent();
        Log.d("ACTIVITY_LC", intent.getStringExtra("message"));


        withdraw_edit_id = (EditText)findViewById(R.id.withdraw_edit_id);
        withdraw_edit_password = (EditText)findViewById(R.id.withdraw_edit_password);
        withdraw_edit_name = (EditText)findViewById(R.id.withdraw_edit_name);
        withdraw_btn_withdraw=(Button)findViewById(R.id.withdraw_btn_withdraw);

        withdraw_btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    WithdrawTest withdrawTest = new WithdrawTest("http://"+curip+"/SSAFYProject/customerinformation_withdraw.php");
                    String curid=String.valueOf(withdraw_edit_id.getText());
                    String curpassword=String.valueOf(withdraw_edit_password.getText());
                    String curname=String.valueOf(withdraw_edit_name.getText());

                    String result = withdrawTest.withdrawtest(curid, curpassword, curname);
                    if(result.equals("1")){
                        Toast.makeText(getApplication(),"회원탈퇴에 성공하셨습니다.",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(WithdrawActivity.this);
                        alert.setTitle("Success");
                        alert.setMessage("회원탈퇴에 성공하셨습니다.");
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });

                        alert.show();
                    }
                    else {
                        Toast.makeText(getApplication(),"회원탈퇴에 실패했슴 ㅎㅎ"+result,Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(WithdrawActivity.this);
                        alert.setTitle("Error");
                        alert.setMessage(" "+result);
                        alert.setMessage("회원탈퇴에 실패했음 ㅎㅎ");
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        alert.show();
                    }



                } catch (MalformedURLException e) {
                    Log.i("LoginActivity", e.getMessage());
                }

            }
        });
    }
}
