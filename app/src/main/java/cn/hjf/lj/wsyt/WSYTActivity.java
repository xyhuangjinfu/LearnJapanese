package cn.hjf.lj.wsyt;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.hjf.lj.R;
import cn.hjf.lj.wsyt.data.PronunciationDataStore;
import cn.hjf.lj.wsyt.next.NextPro;
import cn.hjf.lj.wsyt.next.RandomNextPro;
import cn.hjf.lj.wsyt.next.SequentialNextPro;

public class WSYTActivity extends AppCompatActivity {

	private Pronunciation mCurrent;

	private PronunciationDataStore mPronunciationDataStore;

	private MediaPlayer mMediaPlayer = new MediaPlayer();

	private WriteView mWriteView;

	private NextPro mNextPro;

	private Spinner mOrderSpinner;
	private Spinner mTypeSpinner;

	private List<Pronunciation> mData = new ArrayList<>();

	private TextView mTvPing;
	private TextView mTvPian;
	private TextView mTvRoma;
	private CheckBox mCbPing;
	private CheckBox mCbPian;
	private CheckBox mCbRoma;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wsyt);

		mPronunciationDataStore = new PronunciationDataStore(this);

		mWriteView = findViewById(R.id.write_view);
		mOrderSpinner = findViewById(R.id.spn_order);
		mTypeSpinner = findViewById(R.id.spn_type);

		mTvPing = findViewById(R.id.tv_ping);
		mTvPian = findViewById(R.id.tv_pian);
		mTvRoma = findViewById(R.id.tv_roma);

		mCbPing = findViewById(R.id.cb_ping);
		mCbPian = findViewById(R.id.cb_pian);
		mCbRoma = findViewById(R.id.cb_roma);

		mCbPing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mTvPing.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
			}
		});

		mCbPian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mTvPian.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
			}
		});

		mCbRoma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mTvRoma.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
			}
		});

		mOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0 && !(mNextPro instanceof SequentialNextPro)) {
					mNextPro = new SequentialNextPro(mData);
					return;
				}
				if (position == 1 && !(mNextPro instanceof RandomNextPro)) {
					mNextPro = new RandomNextPro(mData);
					return;
				}

				render();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0) {
					mData = mPronunciationDataStore.getAll();
				} else if (position == 1) {
					mData = mPronunciationDataStore.getQing();
				} else if (position == 2) {
					mData = mPronunciationDataStore.getZhuo();
				} else if (position == 3) {
					mData = mPronunciationDataStore.getAo();
				}

				mNextPro.setData(mData);

				render();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		mNextPro = new SequentialNextPro(mPronunciationDataStore.getAll());

		render();
	}

	@Override
	protected void onDestroy() {
		mMediaPlayer.stop();
		mMediaPlayer.release();

		super.onDestroy();
	}

	public void next(View view) {
		render();
	}

	private void render() {
		mCurrent = mNextPro.next();

		show();

		clearWriteView();
	}

	private void show() {
		mTvPing.setText(mCurrent.getPing());
		mTvPian.setText(mCurrent.getPian());
		mTvRoma.setText(mCurrent.getRoma());
	}

	public void play(View view) {
		try {
			mMediaPlayer.reset();

			AssetManager assetManager = getAssets();
			AssetFileDescriptor fileDescriptor = assetManager.openFd("wsyt/audio/" + mCurrent.getAudio());
			mMediaPlayer.setDataSource(
					fileDescriptor.getFileDescriptor(),
					fileDescriptor.getStartOffset(),
					fileDescriptor.getLength());
			mMediaPlayer.prepare();
			mMediaPlayer.start();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void clear(View view) {
		clearWriteView();
	}

	private void clearWriteView() {
		mWriteView.clear();
	}

}
