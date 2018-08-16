package cn.hjf.lj.wsyt;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.hjf.lj.R;
import cn.hjf.lj.wsyt.data.PronunciationDataStore;

public class WSYTActivity extends AppCompatActivity {

	private Pronunciation mCurrent;
	private List<Pronunciation> mData = new ArrayList<>();

	private PronunciationDataStore mPronunciationDataStore;

	private Random mRandom = new Random();

	private TextView mTextView;

	private MediaPlayer mMediaPlayer = new MediaPlayer();

	private WriteView mWriteView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wsyt);

		mPronunciationDataStore = new PronunciationDataStore(this);

		mTextView = findViewById(R.id.tv);
		mWriteView = findViewById(R.id.write_view);

		mData = mPronunciationDataStore.getAll();

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
		mCurrent = mData.get(mRandom.nextInt(mData.size()));

		show();

		clearWriteView();
	}

	private void show() {
		mTextView.setText(mCurrent.getPing() + "\n" + mCurrent.getPian() + "\n" + mCurrent.getRoma());
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
