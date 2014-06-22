package com.example.gridimagesearch.util;

import java.util.LinkedList;
import java.util.List;

import android.util.Log;

import com.example.gridimagesearch.data.ImageUrls;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {

	private static final JsonParser jsonParser = new JsonParser();

	public List<ImageUrls> getPhotoSet(String text) {
		List<ImageUrls> photoSet = new LinkedList<ImageUrls>();
		JsonObject object = getJsonObject(text);
		String parseText = object.get("photos").toString();
		JsonArray jsonMainArr = getJsonArray(parseText, "photo");
		if (jsonMainArr == null) {
			return null;
		}
		for (int i = 0; i < jsonMainArr.size(); i++) {
			JsonElement element = jsonMainArr.get(i);
			if (element == null) {
				continue;
			}
			// https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
			JsonObject jobject = element.getAsJsonObject();
			if (jobject == null || isJsonNull(jobject.get("farm"))
					|| isJsonNull(jobject.get("server"))
					|| isJsonNull(jobject.get("id"))
					|| isJsonNull(jobject.get("secret"))) {
				continue;
			}

			String farmId = jobject.get("farm").toString().replace("\"", "");
			String serverId = jobject.get("server").toString().replace("\"", "");
			String id = jobject.get("id").toString().replace("\"", "");
			String secret = jobject.get("secret").toString().replace("\"", "");
			String title = jobject.get("title").toString().replace("\"", "");
			String thumbnail = "https://farm" + farmId + ".staticflickr.com/"
					+ serverId + "/" + id + "_" + secret + "_t.jpg";
			String imageUrl = "https://farm" + farmId + ".staticflickr.com/"
					+ serverId + "/" + id + "_" + secret + "_m.jpg";
			Log.i("INFO",thumbnail);
			
			ImageUrls imageUrls = new ImageUrls(thumbnail,imageUrl,title);
			photoSet.add(imageUrls);
		}
		return photoSet;
	}

	/**
	 * @param text
	 * @param identifier
	 * @return
	 */
	protected JsonArray getJsonArray(String text, String identifier) {
		JsonObject object = getJsonObject(text);
		if (object == null || object.isJsonNull()) {
			return null;
		}
		return object.getAsJsonArray(identifier);
	}

	/**
	 * @param object
	 * @param identifier
	 * @return
	 */
	protected JsonElement getJsonElement(JsonObject object, String identifier) {
		JsonElement element = object.get(identifier);
		if (isJsonNull(element)) {
			return null;
		}
		return element;
	}

	/**
	 * @param element
	 */
	protected boolean isJsonNull(JsonElement element) {
		if (element == null || element.isJsonNull()) {
			return true;
		}
		return false;
	}

	/**
	 * @param text
	 * @return
	 */
	protected JsonObject getJsonObject(String text) {
		JsonElement element = jsonParser.parse(text);
		if (isJsonNull(element)) {
			return null;
		}
		return element.getAsJsonObject();
	}

}
