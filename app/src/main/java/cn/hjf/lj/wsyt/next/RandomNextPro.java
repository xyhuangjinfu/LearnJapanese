package cn.hjf.lj.wsyt.next;

import android.support.annotation.Nullable;

import java.util.Random;

import cn.hjf.lj.wsyt.Pronunciation;

public class RandomNextPro extends AbsNextPro {

	private Random mRandom = new Random();

	@Override
	@Nullable
	public Pronunciation next() {
		if (mPronunciationList.isEmpty()) {
			return null;
		}

		return mPronunciationList.get(mRandom.nextInt(mPronunciationList.size()));
	}
}
