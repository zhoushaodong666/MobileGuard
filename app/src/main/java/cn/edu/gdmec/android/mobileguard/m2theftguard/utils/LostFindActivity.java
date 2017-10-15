package cn.edu.gdmec.android.mobileguard.m2theftguard.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by 11388 on 2017/10/12.
 */

public class LostFindActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);
        startSetup1Activity();
}


    private void startSetup1Activity(){
        Intent intent = new Intent(LostFindActivity.this,Setup1Activity.class);
        startActivity(intent);
        finish();
    }



}
