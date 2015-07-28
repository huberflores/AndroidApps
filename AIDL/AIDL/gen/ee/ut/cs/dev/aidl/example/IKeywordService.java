/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/huber/Desktop/source_code/AndroidAppsGitHub/AIDL/AIDL/src/ee/ut/cs/dev/aidl/example/IKeywordService.aidl
 */
package ee.ut.cs.dev.aidl.example;
public interface IKeywordService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements ee.ut.cs.dev.aidl.example.IKeywordService
{
private static final java.lang.String DESCRIPTOR = "ee.ut.cs.dev.aidl.example.IKeywordService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an ee.ut.cs.dev.aidl.example.IKeywordService interface,
 * generating a proxy if needed.
 */
public static ee.ut.cs.dev.aidl.example.IKeywordService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof ee.ut.cs.dev.aidl.example.IKeywordService))) {
return ((ee.ut.cs.dev.aidl.example.IKeywordService)iin);
}
return new ee.ut.cs.dev.aidl.example.IKeywordService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getWords:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<ee.ut.cs.dev.aidl.example.KeywordData> _result = this.getWords();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements ee.ut.cs.dev.aidl.example.IKeywordService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.util.List<ee.ut.cs.dev.aidl.example.KeywordData> getWords() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<ee.ut.cs.dev.aidl.example.KeywordData> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getWords, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(ee.ut.cs.dev.aidl.example.KeywordData.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getWords = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public java.util.List<ee.ut.cs.dev.aidl.example.KeywordData> getWords() throws android.os.RemoteException;
}
