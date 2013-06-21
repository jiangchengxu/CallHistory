package com.haier.calllog;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import java.util.HashMap;

/**
 * Created by jiang on 13-6-20.
 */
public class CallLogListAdapter extends CursorAdapter{
    private static final String TAG = "CallLogListAdapter";

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (!(view instanceof CallLogListItemView)) {
            Log.d(TAG, "Error!!! - bindView(): view is not CallLogListItemView!");
            return;
        }
        CallLogListItemView item = (CallLogListItemView)view;
        ContactInfo contactInfo = getContactInfo(cursor);

        final PhoneCallDetails details;
        if (TextUtils.isEmpty(contactInfo.name)) {
            details = new PhoneCallDetails(contactInfo.number, contactInfo.formattedNumber,
                    contactInfo.countryIso, contactInfo.geocode,
                    contactInfo.type, contactInfo.date,
                    contactInfo.duration, contactInfo.simId,
                    contactInfo.vtCall, contactInfo.ipPrefix);
        } else {
            // We do not pass a photo id since we do not need the high-res
            // picture.
            details = new PhoneCallDetails(contactInfo.number, contactInfo.formattedNumber,
                    contactInfo.countryIso, contactInfo.geocode,
                    contactInfo.type, contactInfo.date,
                    contactInfo.duration, contactInfo.name,
                    contactInfo.nNumberTypeId, contactInfo.label,
                    contactInfo.lookupUri, null, contactInfo.simId,
                    contactInfo.vtCall, contactInfo.ipPrefix);
        }

        final boolean isHighlighted = contactInfo.isRead == 0;
        final boolean isEmergencyNumber = mPhoneNumberHelper.isEmergencyNumber(details.number);
        final boolean isVoiceMailNumber = false;
        mPhoneCallDetailsHelper.setPhoneCallDetails(item, details, isHighlighted,
                isEmergencyNumber, isVoiceMailNumber);
    }

    public CallLogListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        Resources resources = context.getResources();
        mPhoneNumberHelper = new PhoneNumberHelper(resources);
        mPhoneCallDetailsHelper = new PhoneCallDetailsHelper(context);
        mContactInfoMap = new HashMap<String, ContactInfo>();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return new CallLogListItemView(context, null);
    }

    protected ContactInfo getContactInfo(Cursor c) {
        ContactInfo contactInfo = null;
        String hashKey = c.getString(CallLogQuery.CALLS_JOIN_DATA_VIEW_NUMBER)
                + c.getInt(CallLogQuery.CALLS_JOIN_DATA_VIEW_DATE);
        contactInfo = mContactInfoMap.get(hashKey);
        if (null == contactInfo) {
            contactInfo = getContactInfoFromCallLog(c);
            mContactInfoMap.put(hashKey, contactInfo);
        }

        return contactInfo;
    }

    /** Returns the contact information as stored in the call log. */
    protected ContactInfo getContactInfoFromCallLog(Cursor c) {
        ContactInfo info = ContactInfo.fromCursor(c);
        return info;
    }

    private HashMap<String, ContactInfo> mContactInfoMap;
    private PhoneNumberHelper mPhoneNumberHelper;
    protected final PhoneCallDetailsHelper mPhoneCallDetailsHelper;
}
