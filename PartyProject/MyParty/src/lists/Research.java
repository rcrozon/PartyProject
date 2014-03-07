package lists;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.example.myparty.R;

public class Research extends LinearLayout {

	private Adapter adapter;
	private Adapter adapter2;
	private ImageView mCloseButton;

	public Research(final Context context, final List list) {
		super(context);
		LayoutParams paramsSearchView = new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		this.adapter = (Adapter) list.getAdapter();
		// this.researchButton.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Log.i("EDIT","VISIBLE");
		// researchText.setVisibility(VISIBLE);
		// }
		// });
		final SearchView search = new SearchView(context);
		search.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				final InputMethodManager imm = (InputMethodManager) context
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				try {
					adapter2 = adapter.clone();
					for (int i = 0; i < adapter.getItemsList().size(); ++i) {
						Items item = (Items) adapter.getItemsList().get(i);
						Log.i("RESEARCH", "item : "
								+ item.toString().toUpperCase() + " seq : "
								+ newText.toUpperCase());
						if (!item.toString().toUpperCase()
								.contains(newText.toUpperCase())) {
							adapter2.getItemsList().remove(item);
						}
					}
					list.setAdapter(adapter2);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				return false;
			}
		});
		search.setLayoutParams(paramsSearchView);
		// SearchManager searchManager = (SearchManager)
		// getSystemService(Context.SEARCH_SERVICE);

		// search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

		// int searchPlateId =
		// search.getContext().getResources().getIdentifier("android:id/search_plate",
		// null, null);
		// search.findViewById(searchPlateId).setBackgroundResource(R.drawable.apptheme_textfield_activated_holo_dark);

		// int voiceSearchPlateId =
		// search.getContext().getResources().getIdentifier("android:id/submit_area",
		// null, null);
		// search.findViewById(voiceSearchPlateId).setBackgroundResource(R.drawable.apptheme_textfield_activated_holo_dark);
		try {
			mCloseButton = (ImageView) search.findViewById(search.getContext()
					.getResources()
					.getIdentifier("android:id/search_close_btn", null, null));
			Field searchField = SearchView.class
					.getDeclaredField("mCloseButton");
			searchField.setAccessible(true);
			ImageView closeBtn = (ImageView) searchField.get(search);
			closeBtn.setImageResource(R.drawable.ic_action_cancel);
		} catch (NoSuchFieldException e) {
			Log.e("SearchView", e.getMessage(), e);
		} catch (IllegalAccessException e) {
			Log.e("SearchView", e.getMessage(), e);
		}
		// change hint color
		int searchTextViewId = search.getContext().getResources()
				.getIdentifier("android:id/search_src_text", null, null);
		TextView searchTextView = (TextView) search
				.findViewById(searchTextViewId);
		searchTextView.setHintTextColor(getResources().getColor(R.color.white));
		searchTextView.setTextColor(getResources().getColor(R.color.white));
		searchTextView.setBackgroundResource(R.drawable.edit_text_design);

		// search.setBackgroundColor(getResources().getColor(R.color.));
		this.addView(search);
		// this.addView(this.researchText);
		// this.addView(this.researchButton);
	}

	public void setAdapter(Adapter adapter) {
		this.adapter = adapter;
	}
}
