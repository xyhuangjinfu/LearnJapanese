package cn.hjf.lj.wsyt.next;

import android.support.annotation.Nullable;

import java.util.List;

import cn.hjf.lj.wsyt.Pronunciation;

public interface NextPro {

	@Nullable
	Pronunciation next();

	void setData(List<Pronunciation> list);
}
