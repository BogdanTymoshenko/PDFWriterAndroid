package crl.android.pdfwriter.utils;

public final class UtilsBytes {

	public static final byte[] concat(byte[]... byteArrays) {
		int fullLength = 0;
		for (byte byteArray[] : byteArrays) {
			fullLength += byteArray.length;
		}
		
		byte result[] = new byte[fullLength];
		int pos = 0;
		for (byte byteArray[] : byteArrays) {
			System.arraycopy(byteArray, 0, result, pos, byteArray.length);
			pos += byteArray.length;
		}
		
		return result;		
	}
	
	private UtilsBytes() {}
}
