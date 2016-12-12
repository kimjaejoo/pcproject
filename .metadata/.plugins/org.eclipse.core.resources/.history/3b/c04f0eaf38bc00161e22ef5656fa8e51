package lastTest3;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
//캔버스 붙일 때, WinEx7(d20160601)이랑 TCanvas 참고하기
public class PaintCanvas extends Canvas implements MouseMotionListener, MouseListener, Serializable{
	/**
	 * 
	 */
	Color backCol = new Color(250, 250, 250);
	Color col = new Color(0,0,0);
	boolean flag = false;
	boolean clearFlag = false;
	int x = -50, y = -50;
	public PaintCanvas(Color col){
		this.col = col;
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	public Color getCol() {
		return col;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void ppaint(int x, int y, int z){
		
		this.x = x;
		this.y = y;
		if(z == 0)col = Color.BLACK;
		else if(z == 1)col =Color.red;
		else if(z == 2)col =Color.blue;
		else if(z == 3)col =Color.green;
		else if(z == 4)col =Color.yellow;
		else if(z == 5)col =backCol;
		repaint();
		
	}

	@Override
	public void paint(Graphics g) {
		setEnabled(flag);

		g.setColor(col);
		if(col==backCol){
		g.fillRect(x-15, y-15, 30, 30);	
		}else{
		g.fillOval(x, y, 10, 10);
		}

		
		
	}
	public void turn(int a){
		clearFlag = true;
		if(a == 0){
			flag = true;
			repaint();
		}else if(a == 1){
			flag = false;
			repaint();
		}
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
		if(clearFlag){
			g.clearRect(-63, -60, 1000, 800);
			clearFlag = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//System.out.println("마우스 드래그 중");
		x = e.getX();
		y = e.getY();
		repaint(); // repaint가 update를 부름
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
