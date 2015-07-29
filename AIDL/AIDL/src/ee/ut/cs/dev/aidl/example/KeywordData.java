package ee.ut.cs.dev.aidl.example;

import android.os.Parcel;
import android.os.Parcelable;

public class KeywordData implements Parcelable {

	private String value;
	private int key;
	
	
	
	public KeywordData(String value, int key) {
		this.key = key;
		this.value = value;
	}
	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(value);
		dest.writeInt(key);
		
	}
	
	public String getValue() {
		return value;
	}

	public void setValueData(String value) {
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKeyData(int key) {
		this.key = key;
	}
	
	@Override
	public String toString() {
		return "Keyword [value=" + value + ", key=" + key + "]";
	}

	/**
	 * Data is serialized using Parcelable
	 */
	public static final Parcelable.Creator<KeywordData> CREATOR = new Parcelable.Creator<KeywordData>() {

		@Override
		public KeywordData createFromParcel(Parcel source) {
			// Must read values in the same order as they were placed in
			String s = source.readString();
			int v = source.readInt();
			KeywordData myKeywords = new KeywordData(s, v);
			return myKeywords;
		}

		@Override
		public KeywordData[] newArray(int size) {
			return new KeywordData[size];
		}

	};

}
