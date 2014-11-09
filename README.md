<p align="center"><img src="https://raw.githubusercontent.com/JaySoyer/Advanced-Adapters/master/app/src/main/logo.png" width="256px" height="256px"/></p>
# Advanced-Adapters
Advanced-Adapters is a suite of adapters designed to provide additional alternatives to Android's provided adapters. Originally starting as a means to replace the flawed filtering logic for Android's ArrayAdapter; it has now expanded to provided a slew of different adapters backed by various data structures.

Some of the advantages to using this suite:
- Customizable filtering logic
- Active filtering support
- Easily obtain internal data for use with `onSavedInstanceState()`
- `LayoutInflater` passed to all view creation methods.

Currently, this suite supports three different data structures with a filterable and non-filterable (NF) version.  More will be coming in time. Head on over to the [wiki](https://github.com/JaySoyer/Advanced-Adapters/wiki/) for referencing more detailed information for each one.

**ArrayList Backed**
- [AbsArrayAdapter]()
- [NFArrayAdapter]()

**SparseArray Backed**
- [SparseAdapter]()
- [NFSparseAdapter]()

**JSONArray Backed**
- [JSONAdapter]()
- [NFJSONAdapter]()

### Supports
- Ice Cream Sandwich
- Jelly Bean
- Kit Kat
- Lollipop

There are no plans to support Gingerbread at this time.  In fact, I'm purposely attempting to avoid using any android support library. 

### Example
Of course for those just skimming through, here's a quick example of using the AbsArrayAdapter.  Every adapter is just an abstract class that requires subclassing. It's more or less just like using Android's ArrayAdapter.

```java
public class MovieAdapter extends AbsArrayAdapter<MovieItem> {
	MovieAdapter(Context activity) {
		super(activity);
	}
	
	@Override
	public View getView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			//Inflate your view
		}

		//Fill your view with data
		return convertView;
	}
	
	//Easily customize your filtered results here.  Too easy!
	@Override
	public boolean isFilteredOut(MovieItem movie, CharSequence constraint) {
		return !movie.title.toLowerCase(Locale.US).contains(constraint.toString().toLowerCase(Locale.US));
	}
}
```
Thats it!

### Adding To Your Project
All adapters are found within the `lib` directory.  Each adapter is a standalone class file which you can copy and paste into your code.  If using Android Studio, you can import the module directly into your app instead.

Everything found within `app` is the source code for the Google Play store [demo app](https://play.google.com/store/apps/details?id=com.sawyer.advadapters.app&hl=en "Advanced-Adapters Demo App"). You can also use it for seeing how to use the adapters.
	
	
### License

    Copyright 2014 Jay Soyer

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
