package cn.hjf.lj.util.io;

import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class IOUtil {

	@Nullable
	public static byte[] readFromInputStream(InputStream inputStream) {
		if (inputStream == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		try {
			int readCount = 0;
			do {
				baos.write(buffer, 0, readCount);
				readCount = inputStream.read(buffer);
			} while (readCount != -1);

			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			buffer = null;
			try {
				baos.close();
			} catch (IOException e) {
			}
		}
		return null;
	}
}
