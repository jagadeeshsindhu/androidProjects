package com.example.gridimagesearch.data;

public class ImageUrls {
	private String thumbnail;
	private String fullscreen;
	private String title;

	public ImageUrls(String thumbnail, String fullscreen, String title) {
		this.setThumbnail(thumbnail);
		this.setFullscreen(fullscreen);
		if (title == null)
			this.title = "";
		else
			this.title = title;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(String fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return this.thumbnail;
	}

	
}
