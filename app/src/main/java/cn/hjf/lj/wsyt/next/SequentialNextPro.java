package cn.hjf.lj.wsyt.next;

import android.support.annotation.Nullable;

import java.util.List;

import cn.hjf.lj.wsyt.Pronunciation;

public class SequentialNextPro extends AbsNextPro {

	private int mIndex = 0;

	@Override
	@Nullable
	public Pronunciation next() {
		if (mPronunciationList.isEmpty()) {
			return null;
		}

		Pronunciation p = mPronunciationList.get(mIndex);
		mIndex++;
		if (mIndex >= mPronunciationList.size()) {
			mIndex = 0;
		}
		return p;
	}

	@Override
	public void setData(List<Pronunciation> list) {
		super.setData(list);
		mIndex = 0;
	}
}
