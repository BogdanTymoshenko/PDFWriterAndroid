//
//  Android PDF Writer
//  http://coderesearchlabs.com/androidpdfwriter
//
//  by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com)
//

package crl.android.pdfwriter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PDFDocument extends Base {

	private Header mHeader;
	private Body mBody;
	private CrossReferenceTable mCRT;
	private Trailer mTrailer;
	
	public PDFDocument() {
		mHeader = new Header();
		mBody = new Body();
		mBody.setByteOffsetStart(mHeader.getPDFStringSize());
		mBody.setObjectNumberStart(0);
		mCRT = new CrossReferenceTable();
		mTrailer = new Trailer();
	}
	
	public IndirectObject newIndirectObject() {
		return mBody.getNewIndirectObject();
	}
	
	public IndirectObject newRawObject(String content) {
		IndirectObject iobj = mBody.getNewIndirectObject();
		iobj.setContent(content);
		return iobj;
	}
	
	public IndirectObject newDictionaryObject(String dictionaryContent) {
		IndirectObject iobj = mBody.getNewIndirectObject();
		iobj.setDictionaryContent(dictionaryContent);
		return iobj;
	}
	
	public IndirectObject newStreamObject(String streamContent) {
		IndirectObject iobj = mBody.getNewIndirectObject();
		iobj.setDictionaryContent("  /Length " + Integer.toString(streamContent.length()) + "\n");
		iobj.setStreamContent(streamContent);
		return iobj;
	}
	
	public void includeIndirectObject(IndirectObject iobj) {
		mBody.includeIndirectObject(iobj);
	}
	
	@Override
	public String toPDFString() {
		StringBuilder sb = new StringBuilder();
		sb.append(mHeader.toPDFString());
		sb.append(mBody.toPDFString());
		mCRT.setObjectNumberStart(mBody.getObjectNumberStart());
		int x = 0;
		while (x < mBody.getObjectsCount()) {
			IndirectObject iobj = mBody.getObjectByNumberID(++x);
			if (iobj != null) {
				mCRT.addObjectXRefInfo(iobj.getByteOffset(), iobj.getGeneration(), iobj.getInUse());
			}
		}
		mTrailer.setObjectsCount(mBody.getObjectsCount());
		mTrailer.setCrossReferenceTableByteOffset(sb.length());
		mTrailer.setId(Indentifiers.generateId());
		return sb.toString() + mCRT.toPDFString() + mTrailer.toPDFString();
	}
	
	protected void writeToFile(File file) throws IOException {
		RandomAccessFile pdfFile = null;
		try {
			pdfFile = new RandomAccessFile(file, "rw");
				
			pdfFile.write(mHeader.toPDFBytes());
			mBody.writeToFile(pdfFile);
			
			int offset = (int) pdfFile.getFilePointer();
			
			mCRT.setObjectNumberStart(mBody.getObjectNumberStart());
			
			int x = 0;
			while (x < mBody.getObjectsCount()) {
				IndirectObject iobj = mBody.getObjectByNumberID(++x);
				if (iobj != null) {
					mCRT.addObjectXRefInfo(iobj.getByteOffset(), iobj.getGeneration(), iobj.getInUse());
				}
			}
			mTrailer.setObjectsCount(mBody.getObjectsCount());
			mTrailer.setCrossReferenceTableByteOffset(offset);
			mTrailer.setId(Indentifiers.generateId());
			
			pdfFile.write(mCRT.toPDFBytes());
			pdfFile.write(mTrailer.toPDFBytes());
		}
		finally {
			if (pdfFile != null) 
				pdfFile.close();
		}
	}
	
	@Override
	public void clear() {
		mHeader.clear();
		mBody.clear();
		mCRT.clear();
		mTrailer.clear();
	}
}
