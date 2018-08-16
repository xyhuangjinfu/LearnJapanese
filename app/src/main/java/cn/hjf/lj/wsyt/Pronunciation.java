package cn.hjf.lj.wsyt;

public class Pronunciation {
	public static final String QING = "qing";
	public static final String ZHUO = "zhuo";
	public static final String AO = "ao";

	private String type;
	private String ping;
	private String pian;
	private String roma;
	private String audio;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPing() {
		return ping;
	}

	public void setPing(String ping) {
		this.ping = ping;
	}

	public String getPian() {
		return pian;
	}

	public void setPian(String pian) {
		this.pian = pian;
	}

	public String getRoma() {
		return roma;
	}

	public void setRoma(String roma) {
		this.roma = roma;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}
}
