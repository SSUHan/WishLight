package com.example.thewishlight;

public class WLB {

	private String id, title, content, popuptime, startdate, finishdate;
	private int wlbid, shape, dayinterval, secret;

	public WLB(String id, int wlbid ,int shape, String title, String content, String popuptime,
			String startdate, String finishdate, int dayinterval,
			int secret) {
		this.id = id;
		this.wlbid = wlbid;
		this.title = title;
		this.content = content;
		this.popuptime = popuptime;
		this.startdate = startdate;
		this.finishdate = finishdate;
		this.shape = shape;
		this.dayinterval = dayinterval;
		this.secret = secret;

	}

	public int getWlbid() {
		return wlbid;
	}

	public void setWlbid(int wlbid) {
		this.wlbid = wlbid;
	}

	public String getId() {
		return id;
	}

	public void setId(String iD) {
		id = iD;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPopuptime() {
		return popuptime;
	}

	public void setPopuptime(String popuptime) {
		this.popuptime = popuptime;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getFinishdate() {
		return finishdate;
	}

	public void setFinishdate(String finishdate) {
		this.finishdate = finishdate;
	}

	public int getShape() {
		return shape;
	}

	public void setShape(int shape) {
		this.shape = shape;
	}

	public int getDayinterval() {
		return dayinterval;
	}

	public void setDayinterval(int dayinterval) {
		this.dayinterval = dayinterval;
	}

	public int getSecret() {
		return secret;
	}

	public void setSecret(int secret) {
		this.secret = secret;
	}

}
