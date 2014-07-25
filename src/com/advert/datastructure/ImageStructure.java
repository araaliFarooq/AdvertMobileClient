package com.advert.datastructure;

import java.io.Serializable;

public class ImageStructure implements Serializable {
	private String imageName;
	private byte [] imageByte;
	private String imageFormat;
	public ImageStructure(String imageName, byte [] imageByte, String imageFormat){
		this.setImageName(imageName);
		this.setImageByte(imageByte);
		this.setImageFormat(imageFormat);
	}
	public byte [] getImageByte() {
		return imageByte;
	}
	public void setImageByte(byte [] imageByte) {
		this.imageByte = imageByte;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageFormat() {
		return imageFormat;
	}
	public void setImageFormat(String imageFormat) {
		this.imageFormat = imageFormat;
	}
	
	

}
