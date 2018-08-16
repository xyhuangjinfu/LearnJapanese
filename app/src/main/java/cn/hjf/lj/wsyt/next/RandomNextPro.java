package cn.hjf.lj.wsyt.next;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.hjf.lj.wsyt.Pronunciation;

public class RandomNextPro implements NextPro {

	private List<Pronunciation> mPronunciationList;
	private Random mRandom = new Random();

	public RandomNextPro(List<Pronunciation> pronunciationList) {
		mPronunciationList = new ArrayList<>(pronunciationList);
	}

	@Override
	public Pronunciation next() {
		return mPronunciationList.get(mRandom.nextInt(mPronunciationList.size()));
	}

	@Override
	public void setData(List<Pronunciation> list) {
		mPronunciationList.clear();
		mPronunciationList.addAll(list);
	}
}
