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
package com.sawyer.advadapters.app.adapters.android.arrayadapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.sawyer.advadapters.app.R;
import com.sawyer.advadapters.app.ToastHelper;
import com.sawyer.advadapters.app.adapters.AdapterBaseActivity;
import com.sawyer.advadapters.app.data.MovieContent;
import com.sawyer.advadapters.app.data.MovieItem;
import com.sawyer.advadapters.app.dialogs.AddArrayDialogFragment;
import com.sawyer.advadapters.app.dialogs.ContainsArrayDialogFragment;
import com.sawyer.advadapters.app.dialogs.InsertArrayDialogFragment;

import java.util.List;

public class AndroidAdapterActivity extends AdapterBaseActivity implements
		AddArrayDialogFragment.EventListener, ContainsArrayDialogFragment.EventListener,
		InsertArrayDialogFragment.EventListener, AndroidAdapterFragment.EventListener {
	private static final String TAG_ADD_DIALOG_FRAG = "Tag Add Dialog Frag";
	private static final String TAG_ADAPTER_FRAG = "Tag Adapter Frag";
	private static final String TAG_CONTAINS_DIALOG_FRAG = "Tag Contains Dialog Frag";
	private static final String TAG_INSERT_DIALOG_FRAG = "Tag Insert Dialog Frag";

	private AddArrayDialogFragment mAddDialogFragment;
	private ContainsArrayDialogFragment mContainsDialogFragment;
	private InsertArrayDialogFragment mInsertDialogFragment;
	private AndroidAdapterFragment mListFragment;

	@Override
	protected void clear() {
		mListFragment.getListAdapter().clear();
		updateActionBar();
	}

	@Override
	protected void clearAdapterFilter() {
		mListFragment.getListAdapter().getFilter().filter("");
	}

	@Override
	protected String getInfoDialogMessage() {
		return getString(R.string.info_android_arrayadapter_message) +
			   getString(R.string.info_android_arrayAdapter_url);
	}

	@Override
	protected String getInfoDialogTitle() {
		return getString(R.string.info_android_arrayadapter_title);
	}

	@Override
	protected int getListCount() {
		return mListFragment.getListAdapter().getCount();
	}

	@Override
	protected void initFrags() {
		super.initFrags();
		FragmentManager manager = getFragmentManager();
		mListFragment = (AndroidAdapterFragment) manager
				.findFragmentByTag(TAG_ADAPTER_FRAG);
		if (mListFragment == null) {
			mListFragment = AndroidAdapterFragment.newInstance();
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.replace(R.id.frag_container, mListFragment, TAG_ADAPTER_FRAG);
			transaction.commit();
		}

		mContainsDialogFragment = (ContainsArrayDialogFragment) manager
				.findFragmentByTag(TAG_CONTAINS_DIALOG_FRAG);
		if (mContainsDialogFragment != null) {
			mContainsDialogFragment.setEventListener(this);
		}

		mAddDialogFragment = (AddArrayDialogFragment) manager
				.findFragmentByTag(TAG_ADD_DIALOG_FRAG);
		if (mAddDialogFragment != null) {
			mAddDialogFragment.setEventListener(this);
		}

		mInsertDialogFragment = (InsertArrayDialogFragment) manager
				.findFragmentByTag(TAG_INSERT_DIALOG_FRAG);
		if (mInsertDialogFragment != null) {
			mInsertDialogFragment.setEventListener(this);
		}
	}

	@Override
	protected boolean isAddDialogEnabled() {
		return true;
	}

	@Override
	protected boolean isContainsDialogEnabled() {
		return true;
	}

	@Override
	protected boolean isInsertDialogEnabled() {
		return true;
	}

	@Override
	protected boolean isSearchViewEnabled() {
		return true;
	}

	@Override
	public void onAdapterCountUpdated() {
		updateActionBar();
	}

	@Override
	public void onAddMultipleMoviesClick(List<MovieItem> movies) {
		mListFragment.getListAdapter().addAll(movies);
		updateActionBar();
		mAddDialogFragment.dismiss();
	}

	@Override
	public void onAddSingleMovieClick(MovieItem movie) {
		mListFragment.getListAdapter().add(movie);
		updateActionBar();
		mAddDialogFragment.dismiss();
	}

	@Override
	public void onAddVarargsMovieClick(MovieItem... movies) {
		mListFragment.getListAdapter().addAll(movies);
		updateActionBar();
		mAddDialogFragment.dismiss();
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);

		if (fragment instanceof AndroidAdapterFragment) {
			mListFragment = (AndroidAdapterFragment) fragment;
		}
	}

	@Override
	public void onContainsMultipleMovieClick(List<MovieItem> movies) {
		//Not supported
	}

	@Override
	public void onContainsSingleMovieClick(MovieItem movie) {
		if (mListFragment.getListAdapter().getPosition(movie) != -1) {
			ToastHelper.showContainsTrue(this, movie.title);
		} else {
			ToastHelper.showContainsFalse(this, movie.title);
		}
		mContainsDialogFragment.dismiss();
	}

	@Override
	public void onInsertMultipleMoviesClick(List<MovieItem> movies,
											InsertArrayDialogFragment.InsertLocation location) {
		//Not supported
	}

	@Override
	public void onInsertSingleMovieClick(MovieItem movie,
										 InsertArrayDialogFragment.InsertLocation location) {
		int count = mListFragment.getListAdapter().getCount();
		mListFragment.getListAdapter().insert(movie, location.toListPosition(count));
		updateActionBar();
		mInsertDialogFragment.dismiss();
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		mListFragment.getListAdapter().getFilter().filter(newText);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		mListFragment.getListAdapter().getFilter().filter(query);
		return true;
	}

	@Override
	protected void reset() {
		AndroidArrayAdapter adapter = mListFragment.getListAdapter();
		adapter.setNotifyOnChange(false);
		adapter.clear();
		adapter.addAll(MovieContent.ITEM_LIST.subList(0, 4));
		adapter.notifyDataSetChanged();
		updateActionBar();
	}

	@Override
	protected void sort() {
		mListFragment.getListAdapter().sort(null);
	}

	@Override
	protected void startAddDialog() {
		mAddDialogFragment = AddArrayDialogFragment.newInstance();
		mAddDialogFragment.setEventListener(this);
		mAddDialogFragment.show(getFragmentManager(), TAG_ADD_DIALOG_FRAG);
	}

	@Override
	protected void startContainsDialog() {
		mContainsDialogFragment = ContainsArrayDialogFragment.newInstance();
		mContainsDialogFragment.setEventListener(this);
		mContainsDialogFragment.setEnableContainsAll(false);
		mContainsDialogFragment.show(getFragmentManager(), TAG_ADD_DIALOG_FRAG);
	}

	@Override
	protected void startInsertDialog() {
		mInsertDialogFragment = InsertArrayDialogFragment.newInstance();
		mInsertDialogFragment.setEventListener(this);
		mInsertDialogFragment.setEnableInsertAll(false);
		mInsertDialogFragment.show(getFragmentManager(), TAG_INSERT_DIALOG_FRAG);
	}
}
