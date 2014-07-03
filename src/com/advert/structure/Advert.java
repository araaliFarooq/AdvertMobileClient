package com.advert.structure;

public class Advert {

	private String subject;
	private String description;
	private String timeslot;
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

	public String getTimeSlot() {
		return timeslot;
	}

	public void setTimeSlot(String timeslot) {
		this.timeslot = timeslot;
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
		return "Advert [subject=" + subject + ", description=" + description + ", timeslot="
				+ timeslot +",targetArea="+targetArea+",imageUrl="+imageUrl+ "]";
	}
}
