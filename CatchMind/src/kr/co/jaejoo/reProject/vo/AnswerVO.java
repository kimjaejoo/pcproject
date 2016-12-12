package kr.co.jaejoo.reProject.vo;

public class AnswerVO {
	private String sol ;
	
	
	//정답을 읽어오기위한 생성자
	public AnswerVO(String sol) {
		super();
		this.sol = sol;
	}

	public String getSol() {
		return sol;
	}

	public void setSol(String sol) {
		this.sol = sol;
	}
	
	
}
