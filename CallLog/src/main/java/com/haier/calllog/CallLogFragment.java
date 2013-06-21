package com.haier.calllog;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jiang on 13-6-20.
 */
public class CallLogFragment extends ListFragment implements CallLogQueryHandler.Listener{
    private static final String TAG = "CallLogFragment";
    CallLogQueryHandler mCallLogQueryHandler;
    CallLogListAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallLogQueryHandler = new CallLogQueryHandler(getActivity().getContentResolver(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calllog_fragment, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new CallLogListAdapter(this.getActivity(), null, 0);
        setListAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshDate();
    }

    @Override
    public void onCallFetched(Cursor cursor){
        Log.d(TAG, "onCallFetched cursor count("+cursor.getCount()+")");
        mAdapter.swapCursor(cursor);
    }

    private void refreshDate(){
        startQueryCalls();
    }

    private void startQueryCalls(){
        mCallLogQueryHandler.fetchAllCallLogs();
    }
}
