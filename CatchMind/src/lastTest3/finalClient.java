package lastTest3;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import kr.co.jaejoo.reProject.dao.CatchDAO;
import kr.co.jaejoo.reProject.vo.CatchVO;

public class finalClient extends JFrame implements ActionListener, Runnable, KeyListener, MouseMotionListener {
	// ���� ȭ�� ũ�� ���ϱ�, ���� ����
	Toolkit tool = Toolkit.getDefaultToolkit();
	Dimension d = tool.getScreenSize();
	int w = (int) d.getWidth();
	int h = (int) d.getHeight();
	int x = w / 2 - 400 / 2;
	int y = h / 2 - 400 / 2;
	CardLayout layout;
	JPanel login, join, play, player1, player2, player3, player4;

	ImageIcon icon1, icon2, backgroundGif; // ����

	// �α��� ȭ��
	JLabel title, id, pw, copyright1;
	JTextField idIn;
	JPasswordField pwIn;
	JButton loginBtn, joinBtn;

	// ȸ������ ȭ��
	JLabel joinTit, joinName, joinId, joinPw1, joinPw2, copyright2;
	JTextField joinNameIn, joinIdIn;
	JPasswordField joinPwIn1, joinPwIn2;
	JButton joinIdCheckBtn, joinJoinBtn, joinCancelBtn;

	// �÷��� ȭ��
	String player1Id, player2Id, player3Id, player4Id;
	JLabel userImg1, playId1, playLv1, playScore1;// �÷��̾�1
	JLabel userImg2, playId2, playLv2, playScore2;// �÷��̾�2
	JLabel userImg3, playId3, playLv3, playScore3;// �÷��̾�3
	JLabel userImg4, playId4, playLv4, playScore4;// �÷��̾�4
	JTextArea chatView;// ä�� ��
	JTextField chatIn;// ä�� �Է�
	JButton sendBtn;
	JScrollPane jsp;

	// Ŭ���̾�Ʈ ����
	String ip = "192.168.0.21";
	String name;
	Socket s, paintSocket;
	PrintWriter pwW;
	BufferedReader br;

	// DB�� �����ϱ�
	CatchDAO dao = new CatchDAO();
	CatchVO login2 = null;
	CatchVO checkid = null;

	// �׸��� ����
	JLabel jAnswer, jTime;
	Random rnd = new Random();
	static String answer = "";
	// static String answerArr[] = new String[40];
	PaintCanvas pc;
	JPanel pcPanel, btns;
	JButton blackBtn, redBtn, blueBtn, greenBtn, yellowBtn, eraserBtn;

	Color col = new Color(0, 0, 0);
	int sendCol;
	Thread th;
	JButton startBtn;
	boolean startFlag = false;
	// timer �߰�

	boolean scoreFlag = true;
	Timerex timer;

	static String myId;
	String mymyId;
	JPanel pcBack;

	// �����̤̤̤̤̤̤̤̤̤̤̤̤̤̤̤̤̤̤̤�
	int score1 = 0;
	int score2 = 0;
	int score3 = 0;
	int score4 = 0;
	// int ansIndex;
	int px = 0, py = 0;
	int myNum = 0;

	public finalClient() {
		super("ĳġ ���ε�");

		// ���̾ƿ� ����
		layout = new CardLayout();
		setLayout(layout);
		setBounds(x, y, 400, 400);

		ImageIcon im = new ImageIcon("src/Image/qq.PNG");
		setIconImage(im.getImage());

		// ������Ʈ �ʱ�ȭ
		// �г�

		// �α��� ����ȭ��
		icon1 = new ImageIcon("src/Image/loginBackground.gif");
		login = new JPanel() {
			public void paintComponent(Graphics g) {
				Dimension d = getSize();
				g.drawImage(icon1.getImage(), 0, 0, d.width, d.height, this);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		// ȸ������ ����ȭ�� ����
		icon2 = new ImageIcon("src/Image/joinBackground.gif");
		join = new JPanel() { // ȸ������ ���̾ƿ�
			public void paintComponent(Graphics g) {
				Dimension d = getSize();
				g.drawImage(icon2.getImage(), 0, 0, d.width, d.height, this);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		play = new JPanel(); // ���� �÷��� ���̾ƿ�
		player1 = new JPanel(); // �÷��̾�1 ���� �г�
		player2 = new JPanel(); // �÷��̾�2 ���� �г�
		player3 = new JPanel(); // �÷��̾�3 ���� �г�
		player4 = new JPanel(); // �÷��̾�4 ���� �г�

		// �α��� ȭ��
		title = new JLabel(new ImageIcon("src/Image/loginTitle.png"));
		id = new JLabel(new ImageIcon("src/Image/loginID.png"));
		pw = new JLabel(new ImageIcon("src/Image/loginPW.png"));
		idIn = new JTextField();
		pwIn = new JPasswordField();
		loginBtn = new JButton(new ImageIcon("src/Image/loginBtn.png"));
		joinBtn = new JButton(new ImageIcon("src/Image/joinBtn.png"));
		copyright1 = new JLabel("�Ͻ�������ɳ��� ī�Ƕ���Ʈ");

		// ȸ������ ȭ��
		joinTit = new JLabel(new ImageIcon("src/Image/jointitle.png"));
		joinName = new JLabel(new ImageIcon("src/Image/joinname.png"));
		joinId = new JLabel(new ImageIcon("src/Image/joinid.png"));
		joinPw1 = new JLabel(new ImageIcon("src/Image/joinpw.png"));
		joinPw2 = new JLabel(new ImageIcon("src/Image/joinpwcheck.png"));
		joinNameIn = new JTextField();
		joinIdIn = new JTextField();
		joinIdCheckBtn = new JButton("Check");
		joinPwIn1 = new JPasswordField();
		joinPwIn2 = new JPasswordField();
		joinJoinBtn = new JButton(new ImageIcon("src/Image/joinJoin.png"));
		joinCancelBtn = new JButton(new ImageIcon("src/Image/joinCancel.png"));
		copyright2 = new JLabel("�Ͻ�������ɳ��� ī�Ƕ���Ʈ");

		// �÷��� ȭ��
		ImageIcon playerimg1 = new ImageIcon("src/Image/daehan.gif");
		userImg1 = new JLabel(playerimg1, JLabel.CENTER);
		playId1 = new JLabel("User ID", JLabel.CENTER);
		playLv1 = new JLabel("Lv.100", JLabel.CENTER);
		playScore1 = new JLabel(score1 + "��", JLabel.CENTER);
		ImageIcon playerimg2 = new ImageIcon("src/Image/mk.gif");
		userImg2 = new JLabel(playerimg2, JLabel.CENTER);
		playId2 = new JLabel("User ID", JLabel.CENTER);
		playLv2 = new JLabel("Lv.100", JLabel.CENTER);
		playScore2 = new JLabel(score2 + "��", JLabel.CENTER);
		ImageIcon playerimg3 = new ImageIcon("src/Image/ms.gif");
		userImg3 = new JLabel(playerimg3, JLabel.CENTER);
		playId3 = new JLabel("User ID", JLabel.CENTER);
		playLv3 = new JLabel("Lv.100", JLabel.CENTER);
		playScore3 = new JLabel(score3 + "��", JLabel.CENTER);
		ImageIcon playerimg4 = new ImageIcon("src/Image/zz.gif");
		userImg4 = new JLabel(playerimg4, JLabel.CENTER);
		playId4 = new JLabel("User ID", JLabel.CENTER);
		playLv4 = new JLabel("Lv.100", JLabel.CENTER);
		playScore4 = new JLabel(score4 + "��", JLabel.CENTER);
		chatView = new JTextArea();
		chatIn = new JTextField();
		sendBtn = new JButton("Send");

		// ������Ʈ ��ġ, ũ�� ����
		Font titlef = new Font("Gothic", Font.BOLD, 40);

		// �α��� ȭ��
		title.setFont(titlef);
		title.setBounds(80, 35, 230, 70);
		id.setBounds(70, 130, 70, 30); // ID Label
		idIn.setBounds(150, 130, 160, 30); // ID TextField
		pw.setBounds(70, 180, 70, 30); // PW Label
		pwIn.setBounds(150, 180, 160, 30); // PW TextField
		loginBtn.setBounds(90, 250, 100, 35); // �α��� ��ư
		joinBtn.setBounds(200, 250, 100, 35); // ȸ������ ��ư
		Font copy = new Font("Gothic", Font.ITALIC, 8);
		copyright1.setFont(copy);
		copyright1.setBounds(150, 340, 200, 20); // ī�Ƕ���Ʈ

		loginBtn.setBorderPainted(false); // ��ư �׵θ� �����
		loginBtn.setFocusPainted(false); //
		loginBtn.setContentAreaFilled(false); // ��ư ���� �����

		// ȸ������ ȭ��
		// joinTit.setFont(titlef); // ȸ������ Ÿ��Ʋ ��Ʈ
		joinTit.setBounds(90, 35, 230, 70); // ȸ������ Ÿ��Ʋ
		joinName.setBounds(70, 130, 60, 30); // ȸ������ �̸� Label
		joinId.setBounds(70, 130 + 40, 60, 30); // ȸ������ ���̵� Label
		joinPw1.setBounds(70, 130 + 80, 60, 30); // ȸ������ ��й�ȣ Label
		joinPw2.setBounds(70, 130 + 120, 60, 30); // ȸ������ ��й�ȣȮ�� Label
		joinNameIn.setBounds(150, 130, 160, 30); // ȸ������ �̸� �Է�
		joinIdIn.setBounds(150, 130 + 40, 100, 30); // ȸ������ ���̵� �Է�
		Font idCheck = new Font("Gothic", Font.PLAIN, 8);
		joinIdCheckBtn.setFont(idCheck);
		joinIdCheckBtn.setBounds(250, 130 + 40, 60, 30); // ȸ������ ���̵� �ߺ�üũ ��ư
		joinPwIn1.setBounds(150, 130 + 80, 160, 30); // ȸ������ ��й�ȣ �Է�
		joinPwIn2.setBounds(150, 130 + 120, 160, 30); // ȸ������ ��й�ȣ Ȯ�� �Է�
		joinJoinBtn.setBounds(90, 300, 100, 35); // ȸ������ ��ư
		joinCancelBtn.setBounds(200, 300, 100, 35); // ȸ������ ��� ��ư
		copyright2.setFont(copy);
		copyright2.setBounds(150, 340, 200, 20); // ī�Ƕ���Ʈ

		joinBtn.setBorderPainted(false);
		joinBtn.setFocusPainted(false);
		joinBtn.setContentAreaFilled(false);

		joinJoinBtn.setBorderPainted(false);
		joinJoinBtn.setFocusPainted(false);
		joinJoinBtn.setContentAreaFilled(false);

		joinCancelBtn.setBorderPainted(false);
		joinCancelBtn.setFocusPainted(false);
		joinCancelBtn.setContentAreaFilled(false);

		joinIdCheckBtn.setBorderPainted(false);
		joinIdCheckBtn.setFocusPainted(false);
		joinIdCheckBtn.setContentAreaFilled(false);

		// �÷��� ȭ��
		// �÷��̾� ���� �г�
		player1.setBounds(0, 0, 150, 200);
		player2.setBounds(950, 0, 150, 200);
		player3.setBounds(0, 200, 150, 200);
		player4.setBounds(950, 200, 150, 200);
		userImg1.setBounds(2, 2, 146, 150);
		playId1.setBounds(2, 150, 146, 22);
		playLv1.setBounds(2, 170, 75, 28);
		playScore1.setBounds(75, 170, 73, 28);
		userImg2.setBounds(2, 2, 146, 150);
		playId2.setBounds(2, 150, 146, 22);
		playLv2.setBounds(2, 170, 75, 28);
		playScore2.setBounds(75, 170, 73, 28);
		userImg3.setBounds(2, 2, 146, 150);
		playId3.setBounds(2, 150, 146, 22);
		playLv3.setBounds(2, 170, 75, 28);
		playScore3.setBounds(75, 170, 73, 28);
		userImg4.setBounds(2, 2, 146, 150);
		playId4.setBounds(2, 150, 146, 22);
		playLv4.setBounds(2, 170, 75, 28);
		playScore4.setBounds(75, 170, 73, 28);
		player1.setBackground(Color.white);
		player2.setBackground(Color.white);
		player3.setBackground(Color.white);
		player4.setBackground(Color.white);
		chatView.setBounds(0, 400, 1100, 280);
		chatIn.setBounds(0, 680, 1000, 30);
		sendBtn.setBounds(1000, 680, 100, 30);
		jsp = new JScrollPane(chatView, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// ����
		playId1.setBorder(new LineBorder(Color.gray, 2));
		playLv1.setBorder(new LineBorder(Color.gray, 2));
		playScore1.setBorder(new LineBorder(Color.gray, 2));
		userImg1.setBorder(new LineBorder(Color.gray, 2));
		playId2.setBorder(new LineBorder(Color.gray, 2));
		playLv2.setBorder(new LineBorder(Color.gray, 2));
		playScore2.setBorder(new LineBorder(Color.gray, 2));
		userImg2.setBorder(new LineBorder(Color.gray, 2));
		playId3.setBorder(new LineBorder(Color.gray, 2));
		playLv3.setBorder(new LineBorder(Color.gray, 2));
		playScore3.setBorder(new LineBorder(Color.gray, 2));
		userImg3.setBorder(new LineBorder(Color.gray, 2));
		playId4.setBorder(new LineBorder(Color.gray, 2));
		playLv4.setBorder(new LineBorder(Color.gray, 2));
		playScore4.setBorder(new LineBorder(Color.gray, 2));
		userImg4.setBorder(new LineBorder(Color.gray, 2));

		// add
		// �α��� ȭ��
		login.add(title);
		login.add(id);
		login.add(pw);
		login.add(idIn);
		login.add(pwIn);
		login.add(loginBtn);
		login.add(joinBtn);
		login.add(copyright1);

		// ȸ������ ȭ��
		join.add(joinTit);
		join.add(joinName);
		join.add(joinId);
		join.add(joinIdCheckBtn);
		join.add(joinPw1);
		join.add(joinPw2);
		join.add(joinNameIn);
		join.add(joinIdIn);
		join.add(joinPwIn1);
		join.add(joinPwIn2);
		join.add(joinJoinBtn);
		join.add(joinCancelBtn);
		join.add(copyright2);

		// �÷��� ȭ��
		player1.add(userImg1);
		player1.add(playId1);
		player1.add(playLv1);
		player1.add(playScore1);
		player2.add(userImg2);
		player2.add(playId2);
		player2.add(playLv2);
		player2.add(playScore2);
		player3.add(userImg3);
		player3.add(playId3);
		player3.add(playLv3);
		player3.add(playScore3);
		player4.add(userImg4);
		player4.add(playId4);
		player4.add(playLv4);
		player4.add(playScore4);
		play.add(player1);
		play.add(player2);
		play.add(player3);
		play.add(player4);
		jsp.setBounds(0, 400, 1100, 280);
		play.add(jsp);

		play.add(chatIn);
		play.add(sendBtn);

		// �г�
		add(login, "loginPanel");
		add(join, "joinPanel");
		add(play, "playPanel");

		layout.show(getContentPane(), "loginPanel");

		// �̺�Ʈ ó��
		loginBtn.addActionListener(this);
		joinBtn.addActionListener(this);
		joinJoinBtn.addActionListener(this);
		joinCancelBtn.addActionListener(this);
		chatIn.addKeyListener(this);
		joinIdCheckBtn.addActionListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		login.setLayout(null);
		join.setLayout(null);
		player1.setLayout(null);
		player2.setLayout(null);
		player3.setLayout(null);
		player4.setLayout(null);
		play.setLayout(null);

		//////////////////////////////////////////////////////////// �׸���

		ImageIcon back = new ImageIcon("src/Image/s.jpg");
		pcBack = new JPanel() {
			public void paintComponent(Graphics g) {
				Dimension d = getSize();
				g.drawImage(back.getImage(), 0, 0, d.width, d.height, null);
				setOpaque(false);// �׸��� ǥ���ϰ� ����,�����ϰ� ����
				super.paintComponent(g);
			}
		};

		Font f5 = new Font("Gothic", Font.BOLD, 15);
		jAnswer = new JLabel("�� �� : " + answer, JLabel.CENTER);
		Font f6 = new Font("Gothic", Font.BOLD, 20);
		jTime = new JLabel("      00:00  ", JLabel.CENTER);
		jAnswer.setFont(f5);
		jTime.setFont(f6);

		pcPanel = new JPanel();
		pc = new PaintCanvas(col);
		pc.setBackground(Color.white);
		pcPanel.setLayout(null);
		btns = new JPanel();

		// ��ư�ʱ�ȭ �� �̹��� �ֱ�
		btns.setBounds(-50, 340, 670, 60);
		

		blackBtn = new JButton(new ImageIcon("src/Image/blackBtn.png"));
		redBtn = new JButton(new ImageIcon("src/Image/redBtn.png"));
		blueBtn = new JButton(new ImageIcon("src/Image/blueBtn.png"));
		greenBtn = new JButton(new ImageIcon("src/Image/greenBtn.png"));
		yellowBtn = new JButton(new ImageIcon("src/Image/yellowBtn.png"));
		eraserBtn = new JButton(new ImageIcon("src/Image/���찳.png"));

		blackBtn.setSize(30, 35);
		redBtn.setSize(30, 35);
		blueBtn.setSize(30, 35);
		greenBtn.setSize(30, 35);
		yellowBtn.setSize(30, 35);
		eraserBtn.setSize(30, 35);
		
		jAnswer.setSize(230, 35);
		

		// ��ư �׵θ��� �����
		blackBtn.setBorderPainted(false); // ��ư �׵θ� �����
		blackBtn.setFocusPainted(false); //
		blackBtn.setContentAreaFilled(false); // ��ư ���� �����

		blackBtn.setMargin(new Insets(0,0,0,0));
		
		redBtn.setBorderPainted(false); // ��ư �׵θ� �����
		redBtn.setFocusPainted(false); //
		redBtn.setContentAreaFilled(false); // ��ư ���� �����
		
		redBtn.setMargin(new Insets(0,0,0,0));

		blueBtn.setBorderPainted(false); // ��ư �׵θ� �����
		blueBtn.setFocusPainted(false); //
		blueBtn.setContentAreaFilled(false); // ��ư ���� �����
		
		blueBtn.setMargin(new Insets(0,0,0,0));

		greenBtn.setBorderPainted(false); // ��ư �׵θ� �����
		greenBtn.setFocusPainted(false); //
		greenBtn.setContentAreaFilled(false); // ��ư ���� �����
		
		greenBtn.setMargin(new Insets(0,0,0,0));

		yellowBtn.setBorderPainted(false); // ��ư �׵θ� �����
		yellowBtn.setFocusPainted(false); //
		yellowBtn.setContentAreaFilled(false); // ��ư ���� �����
		
		yellowBtn.setMargin(new Insets(0,0,0,0));

		eraserBtn.setBorderPainted(false); // ��ư �׵θ� �����
		eraserBtn.setFocusPainted(false); //
		eraserBtn.setContentAreaFilled(false); // ��ư ���� �����

		eraserBtn.setMargin(new Insets(0,0,0,0));
		
		// �гο� ��ư �߰��ϱ�
		btns.add(blackBtn);
		btns.add(redBtn);
		btns.add(blueBtn);
		btns.add(greenBtn);
		btns.add(yellowBtn);
		btns.add(eraserBtn);
		btns.add(jAnswer);
		
		
		Color backCol = new Color(250, 250, 250);
		pcBack.setBounds(-30, 0, 850, 47);
		pc.setBounds(-50, -50, 850, 410);
		pc.setBackground(backCol);
		pcPanel.setBounds(150, 0, 800, 410);
		//btns.setBounds(0, 360, 610, 40);
		jTime.setBounds(600, 360, 100, 40);
		pcPanel.add(pcBack);
		pcPanel.add(pc);
		pcPanel.add(btns);
		play.add(pcPanel);
		btns.setVisible(false); // ����
		//btns.add(jAnswer, "South");
		pcPanel.add(jTime);

		blackBtn.addActionListener(this);
		redBtn.addActionListener(this);
		blueBtn.addActionListener(this);
		greenBtn.addActionListener(this);
		yellowBtn.addActionListener(this);
		eraserBtn.addActionListener(this);

		pc.addMouseMotionListener(this);

		// start ��ư
		startBtn = new JButton("start");
		startBtn.setEnabled(startFlag); // ó���� �� Ȱ��
		pcPanel.add(startBtn);
		startBtn.setBounds(700, 365, 80, 30);
		startBtn.setFont(f5);
		startBtn.addActionListener(this);

		// timer
		timer = new Timerex(jTime);

		setVisible(true);

	}// CatchMind end

	public static void main(String[] args) {
		new finalClient();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == loginBtn) {// �α��� ��ư Ŭ�� �� �̺�Ʈ ó��

			// �α��ο� �ʿ��� DB����
			login2 = dao.selectOne(idIn.getText(), pwIn.getText());

			if (idIn.getText().equals("") || pwIn.getText().equals("")) {
				System.out.println("��ĭ�Է�");
				JOptionPane.showMessageDialog(this, "        ���̵� �Ǵ� ��й�ȣ�� �Է����ּ���.", "Cancel",
						JOptionPane.DEFAULT_OPTION);
			} else if (!(login2 == null) && login2.getId().equals(idIn.getText())
					&& login2.getPw().equals(pwIn.getText())) {
				mymyId = idIn.getText();
				myId = idIn.getText();
				layout.show(getContentPane(), "playPanel");
				// �α��ο� �����ϸ� �����гο� ������.
				x = w / 2 - 1116 / 2;
				y = h / 2 - 750 / 2;
				setBounds(x, y, 1116, 750);

				vchat();
			} else {
				System.out.println("����");
				JOptionPane.showMessageDialog(this, "        ���̵� �Ǵ� ��й�ȣ�� Ʋ���ϴ�.", "Cancel", JOptionPane.DEFAULT_OPTION);
				idIn.setText("");
				pwIn.setText("");
				// Ʋ�� ���̵�� ����� �ʱ�ȭ��
			}

		} else if (obj == joinBtn) {// ȸ������ ��ư Ŭ�� �� �̺�Ʈ ó��
			layout.show(getContentPane(), "joinPanel");

		} else if (obj == joinJoinBtn) {
			// ȸ������ DB����
		/*	CatchVO vo = new CatchVO(joinNameIn.getText(), joinIdIn.getText(), joinPwIn1.getText(),
					joinPwIn2.getText(), 0);*/
			
			dao.insetjoin(vo);

			if (joinNameIn.getText().equals("") || joinIdIn.getText().equals("")
					|| !(joinPwIn1.getText().equals(joinPwIn2.getText()))) {
				JOptionPane.showMessageDialog(this, "    ȸ�������� ��Ȯ���� �ʰų� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.", "Cancel",
						JOptionPane.DEFAULT_OPTION);

			} else {
				JOptionPane.showMessageDialog(this, "     ������ �Ϸ�Ǿ����ϴ�. �α����ϼ���", "Cancel", JOptionPane.DEFAULT_OPTION);

				joinNameIn.setText("");
				joinIdIn.setText("");
				joinPwIn1.setText("");
				joinPwIn2.setText("");
				layout.show(getContentPane(), "loginPanel");

			}
		} else if (obj == joinCancelBtn) {
			layout.show(getContentPane(), "loginPanel");

		} else if (obj == blackBtn) {
			pc.col = Color.black;
			sendCol = 0;
		} else if (obj == redBtn) {
			pc.col = Color.red;
			sendCol = 1;
		} else if (obj == blueBtn) {
			pc.col = Color.blue;
			sendCol = 2;
		} else if (obj == greenBtn) {
			pc.col = Color.green;
			sendCol = 3;
		} else if (obj == yellowBtn) {
			pc.col = Color.yellow;
			sendCol = 4;
		} else if (obj == eraserBtn) {
			pc.col = Color.WHITE;
			sendCol = 5;
		} else if (obj == startBtn) {
			System.out.println(finalServer.playerCount);
			pwW.println("[started]");
			pwW.flush();
		} else if (obj == joinIdCheckBtn) {

			// �����ϱ�
			checkid = dao.selectcheck(joinIdIn.getText());

			System.out.println(checkid);
			System.out.println(joinIdIn.getText());
			if (joinIdIn.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "               ����� �Ұ����� ID�Դϴ�.", "Cancel",
						JOptionPane.DEFAULT_OPTION);
				joinIdIn.setText("");
			} else if (!(checkid == null) && checkid.getId().equals(joinIdIn.getText())) {
				JOptionPane.showMessageDialog(this, "               ����� �Ұ����� ID�Դϴ�.", "Cancel",
						JOptionPane.DEFAULT_OPTION);
				joinIdIn.setText("");// �ߺ��� ���̵� ����
			} else {
				JOptionPane.showMessageDialog(this, "                ����� ������ ID�Դϴ�.", "Cancel",
						JOptionPane.DEFAULT_OPTION);
			}
		}

	}// actionPerformed

	public void vchat() {
		// ���� Ŭ������ runnable �������̽� ������ü
		th = new Thread(this);
		// Thread ����
		th.start();

	}

	@Override
	public void run() {
		// ���� ����

		try {
			s = new Socket(ip, 5000);
			// ���ź� (������ ���� �޴� ��)
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			// �߽ź� (������ ������ ��)
			pwW = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));

			String data = null;
			String[] arr = null;

			CatchVO playerData = dao.selectOne(mymyId); // mymyID�� �������� �ϳ��� ��
														// ��������
			int score = playerData.getScore(); // id�� ������ ���� ������ �����Ѵ�
			int lev = playerData.getLev(); // id�� ������ ���� ������ �������ش�.

			pwW.println(mymyId + "���� �����ϼ̽��ϴ�. " + mymyId + " " + score + " " + lev); //////////////////////
			pwW.flush();

			dao.close();

			while (true) {
				data = br.readLine();

				arr = data.split(" ");
				if (arr[0].equals("[userInform]")) {
					/*
					 * if (Integer.parseInt(arr[1]) == 1) playId1.setText(
					 * "ID : " + myId); else if (Integer.parseInt(arr[1]) == 2)
					 * playId2.setText("ID : " + myId); else if
					 * (Integer.parseInt(arr[1]) == 3) playId3.setText("ID : " +
					 * myId); else if (Integer.parseInt(arr[1]) == 4)
					 * playId4.setText("ID : " + myId);
					 */
				} else if (arr[0].equals("[pxpy]")) {
					pc.ppaint(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
				} else if (arr[0].equals("[answer]")) {
					timer.timerStop();
					timer = new Timerex(jTime);
					timer.setSecond(90);
					timer.start();

					pc.turn(Integer.parseInt(arr[1]));

					if (Integer.parseInt(arr[1]) == 0) {// ����
						btns.setVisible(true);
						if (Integer.parseInt(arr[2]) == 1) {
							if (scoreFlag) {
							} else {
								score1 += 10;
							}
							playScore1.setText(score1 + " ��");
							playLv1.setText("Lv. " + (score1 / 70 + 1));
							/////////////////// ���� �����ϸ� ����
							pwW.println("[myscore] " + score1 + " " + mymyId);
							pwW.flush();
							/////////////////////////////////
						} else if (Integer.parseInt(arr[2]) == 2) {
							if (scoreFlag) {
							} else {
								score2 += 10;
							}
							playScore2.setText(score2 + " ��");
							playLv2.setText("Lv. " + (score2 / 70 + 1));
							/////////////////// ���� �����ϸ� ����
							pwW.println("[myscore] " + score2 + " " + mymyId);
							pwW.flush();
							///////////////////////////
						} else if (Integer.parseInt(arr[2]) == 3) {
							if (scoreFlag) {
							} else {
								score3 += 10;
							}
							playScore3.setText(score3 + " ��");
							playLv3.setText("Lv. " + (score3 / 70 + 1));
							/////////////////// ���� �����ϸ� ����
							pwW.println("[myscore] " + score3 + " " + mymyId);
							pwW.flush();
							///////////////////////////
						} else if (Integer.parseInt(arr[2]) == 4) {
							if (scoreFlag) {
							} else {
								score4 += 10;
							}
							playScore4.setText(score4 + " ��");
							playLv4.setText("Lv. " + (score4 / 70 + 1));
							/////////////////// ���� �����ϸ� ����
							pwW.println("[myscore] " + score4 + " " + mymyId);
							pwW.flush();
							///////////////////////////
						}

					} else if (Integer.parseInt(arr[1]) == 1) {
						btns.setVisible(false);
						if (scoreFlag) {
						}
					}
					scoreFlag = false;

				} else if (arr[0].equals("[chat]")) {
					if (arr.length > 2) {
						for (int i = 1; i < arr.length; i++) {
							chatView.append(" " + arr[i]);
						}
						chatView.append("\n");
					} else {
						chatView.append(" " + arr[1] + "\n");
					}
				} else if (arr[0].equals("[start]")) {
					startFlag = true;
					startBtn.setEnabled(startFlag);
				} else if (arr[0].equals("[rndValue]")) {
					answer = arr[1];
					System.out.println("���� : " + answer);
					jAnswer.setText("    �� �� : " + answer);
				} else if (arr[0].equals("[started]")) {
					startFlag = false;
					startBtn.setEnabled(startFlag);

					timer.timerStop();
					timer = new Timerex(jTime);
					timer.setSecond(90);
					timer.start();

				} else if (arr[0].equals("[playerInfo]")) {
					if (arr[1].equals("0")) {
						playId1.setText(arr[2]);
						score1 = Integer.parseInt(arr[3]);
						playScore1.setText(score1 + " ��");
						playLv1.setText("Lv." + arr[4]);
					} else if (arr[1].equals("1")) {
						playId2.setText(arr[2]);
						score2 = Integer.parseInt(arr[3]);
						playScore2.setText(score2 + " ��");
						playLv2.setText("Lv." + arr[4]);
					} else if (arr[1].equals("2")) {
						playId3.setText(arr[2]);
						score3 = Integer.parseInt(arr[3]);
						playScore3.setText(score3 + " ��");
						playLv3.setText("Lv." + arr[4]);
					} else if (arr[1].equals("3")) {
						playId4.setText(arr[2]);
						score4 = Integer.parseInt(arr[3]);
						playScore4.setText(score4 + " ��");
						playLv4.setText("Lv." + arr[4]);
					}
				} else if (arr[0].equals("[exit]")) {
					/*
					 * pwW.println("[myscore] " + score1 + " " + mymyId);
					 * pwW.flush(); pwW.println("[myscore] " + score2 + " " +
					 * mymyId); pwW.flush(); pwW.println("[myscore] " + score3 +
					 * " " + mymyId); pwW.flush(); pwW.println("[myscore] " +
					 * score4 + " " + mymyId); pwW.flush();
					 */

					////// �������� ���� �����ϱ�

					pwW.println("[last] " + score1 + " " + score2 + " " + score3 + " " + score4);
					pwW.flush();

					///////////////////////////////// ����
					JOptionPane.showMessageDialog(this, "                ������ �������ϴ�.", "Cancel",
							JOptionPane.DEFAULT_OPTION);

					startBtn.setEnabled(true);
					timer.timerStop();
					////////////////////////////////////////////////////////////////
				} else if (arr[0].equals("[num]")) {
					myNum = Integer.parseInt(arr[1]);

					if (myNum == 1) {
						player1.setBorder(new LineBorder(Color.blue, 2));
					} else if (myNum == 2) {
						player2.setBorder(new LineBorder(Color.blue, 2));
					} else if (myNum == 3) {
						player3.setBorder(new LineBorder(Color.blue, 2));
					} else {
						player4.setBorder(new LineBorder(Color.blue, 2));
					}

				} else {
					chatView.append(data + "\n");
				}
				// ���� ��ũ�� ���� �Ʒ��� �̵�
				JScrollBar sb = jsp.getVerticalScrollBar();
				int v = sb.getMaximum();
				sb.setValue(v);
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// System.out.println("key: "+key);

		if (key == 10) {
			String data = chatIn.getText();

			// �Է��� �ʱ�ȭ
			chatIn.setText("");
			if (answer.equals(data)) {
				pwW.println("\t" + myId + "��\t" + answer + "\t�����Դϴ�!!!!!!!\n");
				pwW.flush();
				pwW.println(myId + "���� �׸��� �����Դϴ�.");
				pwW.flush();
				pwW.println("�����Դϴ�!!!!!!!");
				pwW.flush();
			} else {
				// ������ ����
				pwW.println("[chat] [" + myId + "] " + data);
				pwW.flush();
			}

		} // if

	}// keyPressed end

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		px = e.getX();
		py = e.getY();
		// System.out.println("px : " + px + "py : " + py);
		pwW.println("[pxpy] " + px + " " + py + " " + sendCol);
		pwW.flush();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public class Timerex extends Thread {
		JLabel timerLabel;
		int second;
		int timeOver = 0;

		public Timerex(JLabel timerLabel) {
			this.timerLabel = timerLabel;
			second = 90;
		}

		public int getSecond() {
			return second;
		}

		public void setSecond(int second) {
			this.second = second;
		}

		@Override
		public void run() {
			for (int i = second; i >= 0; i--) {
				timerLabel.setText(second / 60 + ":" + second % 60);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				second--;
			}
			// lastFlag = true;
			result();
		} // run end

		public int getTimeOver() {
			return timeOver;
		}

		public void setTimeOver(int timeOver) {
			this.timeOver = timeOver;
		}

		public void timerStop() {
			this.stop();
		}

		public void result() {
			pwW.println("[timeout]");
			pwW.flush();
		}
	}

}// CatchMind Class end
