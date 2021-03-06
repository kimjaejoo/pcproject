package lastTest3;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import kr.co.jaejoo.reProject.dao.AnswerDAO;
import kr.co.jaejoo.reProject.dao.CatchDAO;
import kr.co.jaejoo.reProject.vo.AnswerVO;
import kr.co.jaejoo.reProject.vo.CatchVO;

@SuppressWarnings("serial")
public class finalServer extends JFrame implements ActionListener {

	ArrayList<MServer> list = new ArrayList<MServer>();
	JTextArea jta;
	JButton jbtn;
	JScrollPane jsp;
	ServerSocket ss;
	PrintWriter pw;
	BufferedReader br;
	Random rnd = new Random();
	int va = rnd.nextInt(40);
	boolean startFlag = true;
	static int playerCount = 0;
	static boolean lastFlag = true;
	
	// player������ ������ ..
	ArrayList<Player> players = new ArrayList<>();
	
	//���ο� DB����
	AnswerDAO dao = new AnswerDAO();
	ArrayList<AnswerVO> list2 =null;
	static int index = 0;
	static int count = 0;
	static String[] answer=null;

	public finalServer() {
		super("ä�ü��� ");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		jta = new JTextArea();
		jta.setFont(new Font("Gothic", Font.BOLD, 15));

		jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		jbtn = new JButton("����");

		add(jsp, "Center");
		add(jbtn, "South");
		jbtn.addActionListener(this);

		setBounds(100, 100, 800, 600);
		setVisible(true);

		// ���� �޾ƿ���
		// �����ִ� ���̺� �����ؼ� answerArr�� ���
		int i = 0;
		
		list2 = dao.Solution(); // ��� �����ؼ� ���� �����´�
		Random rnd = new Random();
		answer = new String[list2.size()]; // ��� ����Ǿ��ִ� ���� ���� ũ�⸸ŭ���� ũ�⸦ ������.
		for(AnswerVO vo : list2){
			answer[i] = vo.getSol();
			i++;
		}
		
		// ��񿡼� �ҷ��� ���� �����ֱ�
		for (int k = 0; k < answer.length; k++) {
			int rndNum = rnd.nextInt(list2.size());
			String temp = answer[k];
			answer[k] = answer[rndNum];
			answer[rndNum] = temp;
		} 
		
		vchat();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("�����մϴ�");
		System.exit(0);

	}

	private void vchat() {
		// ����� ���� ���� ����
		try {
			ss = new ServerSocket(5000);

			while (true) {
				/*if(list.size() >= 5){
					System.out.println("������ �� �ʰ��߽��ϴ�.");
					break;
				}*/
				// ����� ���� ���
				Socket client = ss.accept();
				MServer ms = new MServer(client);
				list.add(ms);
				ms.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			list.remove(this);
		}

	}

	class MServer extends Thread {
		Socket client;
		String ip;
		PrintWriter pw;
		BufferedReader br;
		
		public MServer(Socket client) {
			this.client = client;
			InetAddress inet = client.getInetAddress();
			ip = inet.getHostAddress();

			try {
				br = new BufferedReader(new InputStreamReader(client.getInputStream()));

				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void run() {
			playerCount++;
			// System.out.println("���� count : " + playerCount);
			pw.println("[num] " + playerCount);
			if ((playerCount >= 2)) {
				String msg = "[start]";
				broadcast(msg);
			}
			
			// �а� ���� ���
			String msg = null;

			try {
				
				while (true) {
					
					msg = br.readLine();
					
					String[] arr = msg.split(" ");
					
					if (msg.equals("[started]")) {
						
						lastFlag = true;
						/*broadcast("[rndValue] " + answer[index]);
						index++;
						broadcast(msg); //////// [started]������
						broadcastTurn(ip);*/ 

						///////////////
						if (count > 10) {
							broadcast("[exit]");
						}else{
							broadcast("[rndValue] " + answer[index]);
							index++;
							broadcast(msg); //////// [started]������
							broadcastTurn(ip);
							count++;
						}
						
					} else if (msg.equals("�����Դϴ�!!!!!!!")) {
						lastFlag = true;

						if (count > 10) {
							broadcast("[rndValue] " + answer[index]);
							index++;
							broadcastTurn(ip);
							count++;
							broadcast("[exit]");
						} else {
							broadcast("[rndValue] " + answer[index]);
							index++;
							broadcastTurn(ip);
							count++;
						}
						
					}else if(arr.length>2 && arr[1].equals("�����ϼ̽��ϴ�.")){ // �α����� ��..
						Player p = new Player();
						broadcast("  ##  [ " + arr[2] + " ] ���� �����ϼ̽��ϴ�.  ##");
						p.id = arr[2];
						p.score = Integer.parseInt(arr[3]);
						p.lev = Integer.parseInt(arr[4]);
						players.add(p); // �α��� �ϸ� players ArrayList�� �߰��ȴ�
						
						// ȸ�� ������ ��ο��� �ѷ��ֱ�
						StringBuffer st = new StringBuffer();
						for (int m = 0; m < players.size(); m++) {
							st.setLength(0); // ���ڿ� �����
							st.append("[playerInfo] ");
							st.append( m + " ");
							st.append(players.get(m).id + " ");
							st.append(players.get(m).score + " ");
							st.append(players.get(m).lev + " ");
							
							broadcast(st.toString());
						}
					}else if(arr.length>2 && arr[0].equals("[myscore]")){ // ���� �����ָ�...//////////////////////////////
						for (int i = 0; i < players.size(); i++) { // �÷��� �ο� ����ŭ�� .. for�� ����
							// ���� ������ ���� �ٲ��ֱ�
							if(arr[2].equals(players.get(i).id)){
								players.get(i).score = Integer.parseInt(arr[1]);
								players.get(i).lev = players.get(i).score/70 + 1;
							}
						}
						
						// ȸ�� ������ ��ο��� �ѷ��ֱ�
						StringBuffer st = new StringBuffer();
						for (int m = 0; m < players.size(); m++) {
							st.setLength(0); // ���ڿ� �����
							st.append("[playerInfo] ");
							st.append( m + " ");
							st.append(players.get(m).id + " ");
							st.append(players.get(m).score + " ");
							st.append(players.get(m).lev + " ");
							
							broadcast(st.toString());
						}
						////////////////////////////////////////////////////////////////////////////////////////////////////////
					}else if(arr[0].equals("[timeout]") && lastFlag){
							broadcast("�ð��� ����Ǿ����ϴ�.");
							lastFlag = false;
							broadcast("[start]");
							
					}else if(arr[0].equals("[timeout]")) {
						
					}else if(arr[0].equals("[last]")){

						for (int i = 0; i < players.size(); i++) { // �÷��� �ο�
							// ���� ������ ���� �ٲ��ֱ�
							players.get(i).score = Integer.parseInt(arr[i+1]);
							players.get(i).lev = players.get(i).score/70 + 1;
						}
						CatchDAO dao2 = new CatchDAO();

						for (int i = 0; i < players.size(); i++) {
						//	CatchVO vo0 = new CatchVO(players.get(i).id, players.get(i).score, players.get(i).lev);
							dao2.update(vo0);
						}
						///////////////
						dao.close();
						dao2.close();
						///////////// ����
					}else{
						jta.append("[" + ip + "] : " + msg + "\n");

						broadcast(msg);
					}
					// ���� ��ũ�� ���� �Ʒ��� �̵�
					JScrollBar sb = jsp.getVerticalScrollBar(); // ��ũ�ѹ� ���� ���
					int v = sb.getMaximum();// ��ũ�� �ִ밪 ������
					sb.setValue(v);// �ִ밪 �־���
				}
			} catch (IOException e) {
				list.remove(this);
			}
		}// run() end

		public void broadcast(String data) {
			// ArrayList �ȿ� �ִ� ��� ����ڿ��� �� ������ ����
			for (MServer x : list) {
				x.pw.println(data);
				x.pw.flush();
			}

		}

		public void broadcastTurn(String ip) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).ip == ip && i == 0) {
					list.get(i).pw.println("[answer] " + "0 " + "1");// 1������ ����
					list.get(i).pw.flush();
				} else if (list.get(i).ip == ip && i == 1) {
					list.get(i).pw.println("[answer] " + "0 " + "2");// 2������ ����
					list.get(i).pw.flush();
				} else if (list.get(i).ip == ip && i == 2) {
					list.get(i).pw.println("[answer] " + "0 " + "3");// 3������ ����
					list.get(i).pw.flush();
				} else if (list.get(i).ip == ip && i == 3) {
					list.get(i).pw.println("[answer] " + "0 " + "4");// 4������ ����
					list.get(i).pw.flush();
				} else {
					list.get(i).pw.println("[answer] " + "1");// Ʋ��
					list.get(i).pw.flush();
				}
			}
		}


	}// MServer class end

	public static void main(String[] args) {
		new finalServer();
	}
}// ChatServer class end;
