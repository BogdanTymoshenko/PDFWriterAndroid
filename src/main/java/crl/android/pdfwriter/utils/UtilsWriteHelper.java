package crl.android.pdfwriter.utils;

import java.io.IOException;
import java.io.RandomAccessFile;

public final class UtilsWriteHelper {

	public static final int write(RandomAccessFile raf, byte buffer[]) throws IOException {
		raf.write(buffer);
		return buffer.length;
	}
	
	private UtilsWriteHelper() {}
}
