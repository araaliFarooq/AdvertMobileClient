package com.advert.structure;

public class Advert {

	private String subject;
	private String description;
	private String startTime;
	private String endTime;
	private String targetArea;
	private String imageUrl;
	
		
		
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTargetArea() {
		return targetArea;
	}

	public void setTargetArea(String targetArea) {
		this.targetArea = targetArea;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Override
	public String toString() {
		return "Advert [subject=" + subject + ", description=" + description + ", startTime="
				+ startTime + ", endTime="+ endTime+",targetArea="+targetArea+",imageUrl="+imageUrl+ "]";
	}
}
