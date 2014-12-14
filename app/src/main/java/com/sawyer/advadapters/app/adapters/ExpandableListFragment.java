/**
 * Copyright 2014 Jay Soyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sawyer.advadapters.app.adapters;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.sawyer.advadapters.app.R;
import com.sawyer.advadapters.widget.BaseRolodexAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A quick implementation of a fragment that wraps around an {@link ExpandableListView}, very
 * similar to a {@link android.app.ListFragment} but far less capable. It'll more or les suffice for
 * our needs.
 */
public class ExpandableListFragment extends Fragment implements
		ExpandableListView.OnGroupClickListener, ExpandableListView.OnChildClickListener {
	private static final String STATE_EXPANDABLE_LISTVIEW = "State Expandable ListView";

	@InjectView(android.R.id.list) ExpandableListView mExpandableListView;
	private ExpandableListAdapter mAdapter;

	public ExpandableListFragment() {
	}

	public ExpandableListView getExpandableListView() {
		return mExpandableListView;
	}

	/**
	 * Get the ListAdapter associated with this activity's ListView.
	 */
	public ExpandableListAdapter getListAdapter() {
		return mAdapter;
	}

	/**
	 * Provide the cursor for the list view.
	 */
	public void setListAdapter(ExpandableListAdapter adapter) {
		mAdapter = adapter;
		if (mExpandableListView != null) mExpandableListView.setAdapter(adapter);
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
								int childPosition, long id) {
		return false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_expandable_list, container, false);
		ButterKnife.inject(this, v);
		return v;
	}

	@Override
	public void onDestroyView() {
		ButterKnife.reset(this);
		super.onDestroyView();
	}

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
		return false;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (getListAdapter() instanceof BaseRolodexAdapter) {
			BaseRolodexAdapter adapter = (BaseRolodexAdapter) getListAdapter();
			//TODO: For now just enable for modal...will need to test other modes first
			if (adapter.getChoiceMode() == BaseRolodexAdapter.CHOICE_MODE_MULTIPLE_MODAL) {
				Parcelable parcel = adapter.onSaveExpandableListViewState();
				outState.putParcelable(STATE_EXPANDABLE_LISTVIEW, parcel);
			}
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//In case adapter set before ListView inflated
		if (mAdapter != null) mExpandableListView.setAdapter(mAdapter);
		if (mAdapter instanceof BaseRolodexAdapter) {
			BaseRolodexAdapter adapter = (BaseRolodexAdapter) mAdapter;
			adapter.setOnGroupClickListener(this);
			adapter.setOnChildClickListener(this);
			if (savedInstanceState != null) {
				Parcelable parcel = savedInstanceState.getParcelable(STATE_EXPANDABLE_LISTVIEW);
				adapter.onRestoreExpandableListViewState(parcel);
			}
		} else {
			mExpandableListView.setOnGroupClickListener(this);
			mExpandableListView.setOnChildClickListener(this);
		}
	}
}
