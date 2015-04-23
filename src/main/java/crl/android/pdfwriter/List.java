//
//  Android PDF Writer
//  http://coderesearchlabs.com/androidpdfwriter
//
//  by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com)
//

package crl.android.pdfwriter;

import java.util.ArrayList;

import crl.android.pdfwriter.utils.UtilsBytes;

public abstract class List extends Base {

	protected ArrayList<String> mList;

	public List() {
		mList = new ArrayList<String>();
	}
	
	protected String renderList() {
		StringBuilder sb = new StringBuilder();
		int x = 0;
		while (x < mList.size()) {
			sb.append(mList.get(x).toString());
			x++;
		}
		return sb.toString();
	}
	
	protected byte[] renderListBytes() {
		byte result[] = new byte[0];
		for (String item : mList) {
			result = UtilsBytes.concat(result, item.getBytes(Config.DEFAULT_CHARSET));
		}
		
		return result;
	}
	
	@Override
	public void clear() {
		mList.clear();
	}
}
