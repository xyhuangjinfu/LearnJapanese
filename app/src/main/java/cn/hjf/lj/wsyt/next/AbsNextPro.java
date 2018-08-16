package cn.hjf.lj.wsyt.next;

import java.util.ArrayList;
import java.util.List;

import cn.hjf.lj.wsyt.Pronunciation;

public abstract class AbsNextPro implements NextPro {
	protected List<Pronunciation> mPronunciationList;

	public AbsNextPro() {
		mPronunciationList = new ArrayList<>();
	}

	@Override
	public void setData(List<Pronunciation> list) {
		mPronunciationList.clear();
		mPronunciationList.addAll(list);
	}
}
