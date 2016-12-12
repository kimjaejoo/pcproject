package kr.co.jaejoo.reProject.vo;

public class CatchVO {
	private int userNo;
	private String name;
	private String id;
	private String pw;
	private String pw2;
	private int lev;
	private int score;

	public CatchVO() {
	}
	
	public CatchVO(int userNo, String name, String id, String pw, String pw2, int lev, int score) {
		super();
		this.userNo = userNo;
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.pw2 = pw2;
		this.lev = lev;
		this.score = score;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPw2() {
		return pw2;
	}

	public void setPw2(String pw2) {
		this.pw2 = pw2;
	}

	public int getLev() {
		return lev;
	}

	public void setLev(int lev) {
		this.lev = lev;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	
	
}