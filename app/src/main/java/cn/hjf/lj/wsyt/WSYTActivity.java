package cn.hjf.lj.wsyt;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.hjf.lj.R;

public class WSYTActivity extends AppCompatActivity {

	private Pronunciation mCurrent;
	private List<Pronunciation> mData = new ArrayList<>();

	private Random mRandom = new Random();

	private TextView mTextView;

	private MediaPlayer mMediaPlayer = new MediaPlayer();

	private WriteView mWriteView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wsyt);

		mTextView = findViewById(R.id.tv);
		mWriteView = findViewById(R.id.write_view);

		mData = parse(readData());

		render();
	}

	@Override
	protected void onDestroy() {
		mMediaPlayer.stop();
		mMediaPlayer.release();

		super.onDestroy();
	}

	private List<Pronunciation> parse(String str) {
		List<Pronunciation> pList = new ArrayList<>();
		String[] pArray = str.split("\n");
		for (String s : pArray) {
			s = s.replace("{", "");
			s = s.replace("}", "");
			String[] sA = s.split(",");

			Pronunciation p = new Pronunciation();
			p.setPing(sA[0]);
			p.setPian(sA[1]);
			p.setRoma(sA[2]);
			p.setAudio(sA[3]);

			pList.add(p);
		}
		return pList;
	}

	private String readData() {
		AssetManager assetManager = getAssets();
		InputStream is = null;
		try {
			is = assetManager.open("wsyt/wsyt_data.txt");
			byte[] data = readFromInputStream(is);
			String str = new String(data);
			return str;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	public static byte[] readFromInputStream(InputStream inputStream) {
		if (inputStream == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		try {
			int readCount = 0;
			do {
				baos.write(buffer, 0, readCount);
				readCount = inputStream.read(buffer);
			} while (readCount != -1);

			byte[] result = baos.toByteArray();
			baos.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
