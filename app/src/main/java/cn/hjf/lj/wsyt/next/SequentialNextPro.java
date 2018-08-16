package cn.hjf.lj.wsyt.next;

import java.util.List;
import java.util.Random;

import cn.hjf.lj.wsyt.Pronunciation;

public class SequentialNextPro implements NextPro {

	private List<Pronunciation> mPronunciationList;
	private int mIndex = 0;

	public SequentialNextPro(List<Pronunciation> pronunciationList) {
		mPronunciationList = pronunciationList;
	}

	@Override
	public Pronunciation next() {
		Pronunciation p = mPronunciationList.get(mIndex);
		mIndex++;
		if (mIndex >= mPronunciationList.size()) {
			mIndex = 0;
		}
		return p;
	}
}
