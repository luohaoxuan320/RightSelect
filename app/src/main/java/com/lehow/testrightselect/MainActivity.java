package com.lehow.testrightselect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.lehow.rightselect.RightSelectActivity;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  public void btnClick(View view) {
    RightSelectActivity.jumpWithFragmentContent(this,BlankFragment.class,null);
  }
}
