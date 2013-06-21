package com.haier.calllog;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import java.lang.ref.WeakReference;

/**
 * Created by jiang on 13-6-20.
 */
public class CallLogQueryHandler extends AsyncQueryHandler {
    private WeakReference<Listener> mListener;

    public interface Listener{
        void onCallFetched(Cursor cursor);
    }
    public CallLogQueryHandler(ContentResolver cr, Listener listener) {
        super(cr);
        mListener = new WeakReference<Listener>(listener);
    }


    public void fetchAllCallLogs(){
        cancelFetch();
        fetchCalls(QUERY_ALL_CALLS, 0, 0, 0);
    }

    private void fetchCalls(int token, int requestId, int simFilter, int typeFilter){
        Uri queryUri;
        String[] queryProjection;
        String selection;

        queryUri = CallLog.Calls.CONTENT_URI;
        queryProjection = CallLogQuery._PROJECTION;
        if(token == QUERY_ALL_CALLS_JOIN_DATA_VIEW_TOKEN){
            queryUri = Uri.parse("content://call_log/callsjoindataview");
            queryProjection = CallLogQuery.PROJECTION_CALLS_JOIN_DATAVIEW;
        }
        selection = getSelection(simFilter, typeFilter);

        startQuery(token, requestId, queryUri, queryProjection, selection, null, CallLog.Calls.DEFAULT_SORT_ORDER);
    }

    private String getSelection(int simFilter, int typeFilter){
        return null;
    }
    private void cancelFetch(){
        cancelOperation(QUERY_ALL_CALLS_JOIN_DATA_VIEW_TOKEN);
        cancelOperation(QUERY_ALL_CALLS);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        notifyQueryComplete(cursor);
    }

    private void notifyQueryComplete(Cursor cursor){
        Listener listener = mListener.get();
        if(listener != null)
            listener.onCallFetched(cursor);
    }
    private static final int QUERY_ALL_CALLS_JOIN_DATA_VIEW_TOKEN = 101;
    private static final int QUERY_ALL_CALLS = 102;
}
