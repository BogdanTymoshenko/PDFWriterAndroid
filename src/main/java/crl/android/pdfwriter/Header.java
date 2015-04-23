//
//  Android PDF Writer
//  http://coderesearchlabs.com/androidpdfwriter
//
//  by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com)
//

package crl.android.pdfwriter;

import crl.android.pdfwriter.utils.UtilsBytes;

public class Header extends Base {

	private static final byte FOUR_MAGIC_BYTES[] = new byte[]{(byte)0xC7, (byte)0xEC, (byte)0x8F, (byte)0xA2, (byte)0x0A};
	
	private String mVersion;
	private String mRenderedHeader;
	
	public Header() {
		clear();
	}
	
	public void setVersion(int Major, int Minor) {
		mVersion = Integer.toString(Major) + "." + Integer.toString(Minor);
		render();
	}
	
	public int getPDFStringSize() {
		return mRenderedHeader.length();
	}
	
	private void render() {
		mRenderedHeader = "%PDF-" + mVersion;// + "\n%����\n";
	}
	
	@Override
	public String toPDFString() {
		return mRenderedHeader;
	}
	
	@Override
	public byte[] toPDFBytes() {
		byte headerBytes[] = ("%PDF-" + mVersion+ "\n").getBytes(Config.DEFAULT_CHARSET);
		return UtilsBytes.concat(headerBytes, FOUR_MAGIC_BYTES);
	}

	@Override
	public void clear() {
		setVersion(1, 4);
	}

}
