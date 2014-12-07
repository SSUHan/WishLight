package com.example.thewishlight;

public class Client 
{
	private String id;
	private String pw;
	private int _id;
	private int shapepermission;
	private int star;
	private int trophypermission;
	
	Client(){
		
	}
	
	
	Client(int _id,String id,String pw, int shapepermission)
	{
		this.set_id(_id);
		this.setId(id);
		this.setPw(pw);
		this.shapepermission = shapepermission;
	}
	Client(int _id,String id,String pw, int shapepermission,int star)
	{
		this.set_id(_id);
		this.setId(id);
		this.setPw(pw);
		this.shapepermission = shapepermission;
		this.star = star;
	}
	
	Client(int _id,String id,String pw, int shapepermission,int trophy, int star )
	{
		this.set_id(_id);
		this.setId(id);
		this.setPw(pw);
		this.shapepermission = shapepermission;
		this.star = star;
		this.trophypermission = trophy;
	}
	
	

	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public int getTrophypermission() {
		return trophypermission;
	}
	public void setTrophypermission(int trophypermission) {
		this.trophypermission = trophypermission;
	}
	public int getShapepermission() {
		return shapepermission;
	}
	public void setShapepermission(int shapepermission) {
		this.shapepermission = shapepermission;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

}
