package kr.co.jaejoo.reProject.client.panel;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.jaejoo.reProject.asset.Setting;
import kr.co.jaejoo.reProject.dao.CatchDAO;
import kr.co.jaejoo.reProject.vo.CatchVO;

@SuppressWarnings({"serial"})
public class LoginPanel extends JPanel implements ActionListener{

	private JTextField loginId;
	private JPasswordField loginPw;
	private JButton joinBtn, loginBtn;
	
	private Pattern patternEmail;
	private Matcher matcherEmail;
	
	private JOptionPane jOptionPane;
	
	private String emailregex = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
	private String idregex = "^[a-zA-Z0-9]*$";
	private String telregex = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
	
	// 사용자의 정보에 접근한 객체를 선언합니다.
	private CatchDAO dao = new CatchDAO();
	private CatchVO vo;
	
	public LoginPanel() {
		setLayout(null);
		
		loginId = new JTextField();
		loginPw = new JPasswordField();
		joinBtn = new JButton("회원가입");
		loginBtn = new JButton("로그인");
		
		setLable(loginId).setBounds(Setting.loginId);
		setLable(loginPw).setBounds(Setting.loginPw);
		setLable(loginBtn).setBounds(Setting.loginBtn);
		setLable(joinBtn).setBounds(Setting.joinBtn);
		
		add(loginId);

		add(loginPw);

		add(loginBtn);

		add(joinBtn);

		loginBtn.addActionListener(this);
		
	}

	private JComponent setLable(JComponent component) {

		component.setForeground(Setting.bColor);

		component.setFont(Setting.bFont);

		return component;

	}

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
	
		if (obj == loginBtn) {

			// System.out.println("로그인 버튼눌림");

			// 제어문을 사용하여 아이디와 비번이 맞으면 로그인을 시도합니다.

			String id = loginId.getText();

			String pw = loginPw.getText();

			vo = dao.login(id, pw);

			if (vo != null) {

				// userno를 넘기기위해서 사용한다.
				int playerNo = vo.getUserNo();
				vo.setUserNo(vo.getUserNo());

				// System.out.println(playerNo);

				// 아이디는 정규표현식을 사용하여 작성합니다.

				patternEmail = Pattern.compile(emailregex);

				matcherEmail = patternEmail.matcher(email);

				// System.out.println(matcherEmail.matches());

				Boolean match = matcherEmail.matches();

				// System.out.println();



				// 사용자의 권한을 설정합니다 system = 1 , user = 0

				// 권한들 두개로 나누어 1이면 시스템UI으로 접속하고 0이면 사용자UI로 접속됩니다.

				// test 시스템계정은 kjj0710@naver.com 사용자계정은 test@test.com 입니다.



				if (match.booleanValue() == true) {

					// System.out.println("형식이 일치"); // 형식이 일치하면 아래의 제어문을 실행함

					// admin의 값을 가지고오는 로그인형식을 추가하자!! 아이디와 비번을 입력했을 때 조회되는

					// admin값을

					// 이용하여 어느창을 띄울지 정한다.



					if (email.equals(dto.getEmail()) && pw.equals(dto.getPassword())) {

						// System.out.println("아이디와 비밀번호가 일치하여 실행합니다.");



						if (dto.getAdmin() == 1) {

							System.out.println(dto.getAdmin());

							System.out.println(dto.getEmail() + " / " + dto.getPassword());

							System.out.println("관리자 계정으로 로그인을 시도합니다.");



							// 사용자가 로그인을 시도하면 다른 클래스에 적정한 값을 보내야합니다.

							// 다른클래스로 보내는 값은 사용자의 번호입니다 값을 넘기기 위해서는 이 클래스안에

							// setter,

							// getter가 필요합니다.



							try {

								main.mainFrame(playerNo);

							} catch (Exception e1) {

								e1.printStackTrace();

							}



						} else if (dto.getAdmin() == 0) {

							// System.out.println(dto.getEmail() + " / " +

							// dto.getPassword());



							// System.out.println(dto.getAdmin());

							System.out.println("사용자 계정으로 로그인을 시도합니다.");

							System.out.println("넘겨주어야할 사용자의 번호 : " + dto.getMembernum());



							try {

								main.userFrame(playerNo);

								dto.setMembernum(playerNo);

							} catch (Exception e1) {

								e1.printStackTrace();

							}



						}

						// 사용자의 정보가 일치하지 않거나 없으면 경고창을 대신 띄운다.

					} else {

						System.out.println("실행");

					}

				} else if (match.booleanValue() == false || dto.getEmail().equals(null)

						|| dto.getPassword().equals(null)) {

					System.out.println("틀림");

					jOptionPane.showMessageDialog(this, "이메일 형식에 맞지않습니다");

				}

			} else if (obj == joinBtn) {

				System.out.println("회원가입 버튼을 누름");

				main.joinFrame();

			} else {

				System.out.println("실행");

				jOptionPane.showMessageDialog(this, "아아디 또는 비밀번호가 일치하지 않습니다");

			}



		}
	}
	
}
