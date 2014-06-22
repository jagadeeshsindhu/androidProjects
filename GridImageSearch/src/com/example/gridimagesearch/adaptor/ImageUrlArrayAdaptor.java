package com.example.gridimagesearch.adaptor;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.gridimagesearch.R;
import com.example.gridimagesearch.data.ImageUrls;
import com.loopj.android.image.SmartImageView;

public class ImageUrlArrayAdaptor extends ArrayAdapter<ImageUrls> {

	public ImageUrlArrayAdaptor(Context context, List<ImageUrls> images) {
		super(context, R.layout.image_view_list,images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageUrls imageUrls = this.getItem(position);
		SmartImageView siView;
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			siView = (SmartImageView) inflater.inflate(R.layout.image_view_list, parent,false);
		} else {
			siView = (SmartImageView) convertView;
			siView.setImageResource(android.R.color.transparent);
		}
		siView.setImageUrl(imageUrls.getThumbnail());
		return siView;
	}

}
