package com.haier.calllog;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by jiang on 13-6-20.
 */
public class CallLogListAdapter extends CursorAdapter{
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    public CallLogListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return null;
    }
}
