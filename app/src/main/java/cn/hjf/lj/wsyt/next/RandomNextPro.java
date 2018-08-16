package cn.hjf.lj.wsyt.next;

import java.util.List;
import java.util.Random;

import cn.hjf.lj.wsyt.Pronunciation;

public class RandomNextPro implements NextPro {

	private List<Pronunciation> mPronunciationList;
	private Random mRandom = new Random();

	public RandomNextPro(List<Pronunciation> pronunciationList) {
		mPronunciationList = pronunciationList;
	}

	@Override
	public Pronunciation next() {
		return mPronunciationList.get(mRandom.nextInt(mPronunciationList.size()));
	}
}
