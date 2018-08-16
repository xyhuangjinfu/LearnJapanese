package cn.hjf.lj.wsyt.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.hjf.lj.util.io.IOUtil;
import cn.hjf.lj.wsyt.Pronunciation;

public class PronunciationDataStore {

	private Context mContext;
	private List<Pronunciation> mAllPronunciationList;

	public PronunciationDataStore(Context context) {
		mContext = context;
		mAllPronunciationList = new ArrayList<>();
	}

	@NonNull
	public List<Pronunciation> getAll() {
		return internalGetAll();
	}

	@NonNull
	public List<Pronunciation> getQing() {
		return getByType(Pronunciation.QING);
	}

	@NonNull
	public List<Pronunciation> getZhuo() {
		return getByType(Pronunciation.ZHUO);
	}

	@NonNull
	public List<Pronunciation> getAo() {
		return getByType(Pronunciation.AO);
	}

	/**
	 * **************************************************************************************************
	 * **************************************************************************************************
	 */

	private List<Pronunciation> getByType(@NonNull String type) {
		List<Pronunciation> pronunciationList = internalGetAll();
		List<Pronunciation> typeList = new ArrayList<>();
		for (Pronunciation p : pronunciationList) {
			if (type.equals(p.getType())) {
				typeList.add(p);
			}
		}
		return typeList;
	}

	private List<Pronunciation> internalGetAll() {
		if (mAllPronunciationList.isEmpty()) {
			mAllPronunciationList.addAll(getAllDataFromAssets());
		}
		return new ArrayList<>(mAllPronunciationList);
	}

	@NonNull
	private List<Pronunciation> getAllDataFromAssets() {
		String str = readFromAssets();
		if (str == null) {
			return Collections.EMPTY_LIST;
		}
		return parse(str);
	}

	@Nullable
	private String readFromAssets() {
		AssetManager assetManager = mContext.getAssets();
		InputStream is = null;
		try {
			is = assetManager.open("wsyt/wsyt_data.txt");
			byte[] data = IOUtil.readFromInputStream(is);
			if (data == null) {
				return null;
			}
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

	private List<Pronunciation> parse(@NonNull String str) {
		List<Pronunciation> pList = new ArrayList<>();
		String[] pArray = str.split("\n");
		for (String s : pArray) {
			s = s.replace("{", "");
			s = s.replace("}", "");
			String[] sA = s.split(",");

			Pronunciation p = new Pronunciation();
			p.setType(sA[0]);
			p.setPing(sA[1]);
			p.setPian(sA[2]);
			p.setRoma(sA[3]);
			p.setAudio(sA[4]);

			pList.add(p);
		}
		return pList;
	}
}
