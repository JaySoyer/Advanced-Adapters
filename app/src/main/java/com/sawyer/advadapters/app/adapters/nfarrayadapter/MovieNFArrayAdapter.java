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
package com.sawyer.advadapters.app.adapters.nfarrayadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sawyer.advadapters.app.R;
import com.sawyer.advadapters.app.adapters.MovieViewHolder;
import com.sawyer.advadapters.app.data.MovieItem;
import com.sawyer.advadapters.widget.NFArrayAdapter;

import java.util.List;

class MovieNFArrayAdapter extends NFArrayAdapter<MovieItem> {

	MovieNFArrayAdapter(Context activity) {
		super(activity);
	}

	MovieNFArrayAdapter(Context activity, List<MovieItem> list) {
		super(activity, list);
	}

	@Override
	public View getView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
		MovieViewHolder vh;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_movie1, parent, false);
			vh = new MovieViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (MovieViewHolder) convertView.getTag();
		}

		MovieItem movie = getItem(position);
		vh.title.setText(movie.title);
		vh.subtitle.setText(String.valueOf(movie.year));
		vh.icon.setImageResource(
				movie.isRecommended ? R.drawable.ic_rating_good : R.drawable.ic_rating_bad);

		return convertView;
	}
}