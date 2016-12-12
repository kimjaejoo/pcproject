package kr.co.jaejoo.reProject.client;

import kr.co.jaejoo.reProject.asset.Setting;

@SuppressWarnings({"static-access", "unused"})
public class ControlClass {

	private static MainClient mainClient;
	private static LoginClient loginClient;
	
	// main이 되는 login화면이 첫번째 화면입니다.
	public static void main(String[] args) throws Exception {
		ControlClass cc = new ControlClass();
		cc.loginClient = new LoginClient();
		cc.loginClient.setControlClass(cc);
		
		cc.loginClient.setRectangles(LoginClient.class, loginClient, Setting.class, Setting.getInstance());
	}
	
}
