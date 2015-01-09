/*
 * Copyright 2014 Jay Soyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sawyer.advadapters.app.adapters.rolodexarrayadapter.basicdemo;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sawyer.advadapters.app.R;
import com.sawyer.advadapters.app.data.MovieContent;
import com.sawyer.advadapters.app.data.MovieItem;
import com.sawyer.advadapters.widget.RolodexArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class RemoveItemsActivity extends ExpandableListActivity {
	private static final String STATE_LIST = "State List";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remove_items);
		ButterKnife.inject(this);
		if (savedInstanceState == null) {
			setListAdapter(new DemoAdapter(this, MovieContent.ITEM_LIST));
		} else {
			ArrayList<MovieItem> list = savedInstanceState.getParcelableArrayList(STATE_LIST);
			setListAdapter(new DemoAdapter(this, list));
		}
	}

	@Override
	protected void onDestroy() {
		ButterKnife.reset(this);
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		DemoAdapter adapter = (DemoAdapter) getExpandableListAdapter();
		outState.putParcelableArrayList(STATE_LIST, adapter.getList());
	}

	private class DemoAdapter extends RolodexArrayAdapter<String, MovieItem> {
		public DemoAdapter(Context activity, List<MovieItem> movies) {
			super(activity, movies);
		}

		@Override
		public String createGroupFor(MovieItem childItem) {
			return String.valueOf(childItem.year);
		}

		@Override
		public View getChildView(LayoutInflater inflater, int groupPosition, int childPosition,
								 boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_expandable_child1, parent, false);
			}
			TextView tv = (TextView) convertView;
			tv.setText(getChild(groupPosition, childPosition).title);
			return convertView;
		}

		@Override
		public View getGroupView(LayoutInflater inflater, int groupPosition, boolean isExpanded,
								 View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_expandable_group1, parent, false);
			}
			TextView tv = (TextView) convertView;
			tv.setText(getGroup(groupPosition));
			return convertView;
		}

		@Override
		public boolean hasAutoExpandingGroups() {
			//Auto expand so user can more easily see the newly added items
			return true;
		}

		@Override
		protected boolean isChildFilteredOut(MovieItem childItem, CharSequence constraint) {
			//Not worried about filtering for this demo
			return false;
		}

		@Override
		protected boolean isGroupFilteredOut(String groupItem, CharSequence constraint) {
			//Not worried about filtering for this demo
			return false;
		}
	}
}
