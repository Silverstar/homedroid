package de.ebertp.HomeDroid.Activities.Listing.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.ebertp.HomeDroid.Communication.RefreshStateHelper;
import de.ebertp.HomeDroid.Model.HMObject;
import de.ebertp.HomeDroid.R;
import de.ebertp.HomeDroid.Utils.PrefixHelper;
import de.ebertp.HomeDroid.ViewAdapter.CursorToObjectHelper;

public class ListDataFragmentScripts extends SortableListDataFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoomId = PrefixHelper.getFullPrefix(getContext()) + SortableListDataFragment.GROUP_ID_SCRIPTS;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        shrinkList(view);
        return view;
    }

    public void initDbHelper() {
        mRelationsHelper = dbManager.programsDbAdapter;
    }

    @Override
    public ArrayList<HMObject> getHMObjects(Cursor c) {
        ArrayList<HMObject> hmObjects = CursorToObjectHelper.convertCursorToPrograms(c);
        Collections.sort(hmObjects, new SortOrderComparator());
        return hmObjects;
    }

    protected void refreshBatch(FragmentActivity fragmentActivity, List<HMObject> arrayList, Handler toastHandler) {
        RefreshStateHelper.refreshScripts(fragmentActivity, toastHandler);
    }

    public String getTitle() {
        return getResources().getString(R.string.scripts);
    }

}
