package com.pizza73.webapp.model;

public class DailySalesTabsSettings {
    public String label;
    public String urlDate;
    public boolean available=false;
    public boolean defaultDate=false;
      
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUrlDate() {
		return urlDate;
	}
	public void setUrlDate(String urlDate) {
		this.urlDate = urlDate;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public boolean isDefaultDate() {
		return defaultDate;
	}
	public void setDefaultDate(boolean defaultDate) {
		this.defaultDate = defaultDate;
	}
      
}
