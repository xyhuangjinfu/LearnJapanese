package cn.hjf.lj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.hjf.lj.wsyt.WSYTActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void clickWSYT(View view) {
		Intent intent = new Intent(this, WSYTActivity.class);
		startActivity(intent);
	}
}
