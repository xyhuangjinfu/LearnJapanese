package cn.hjf.lj.wsyt.next;

import java.util.List;

import cn.hjf.lj.wsyt.Pronunciation;

public interface NextPro {

	Pronunciation next();

	void setData(List<Pronunciation> list);
}
