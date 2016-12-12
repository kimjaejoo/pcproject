package kr.co.jaejoo.reProject.client;

import java.awt.Component;
import java.awt.Rectangle;
import java.lang.reflect.Field;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import kr.co.jaejoo.reProject.asset.Setting;

@SuppressWarnings({"unused", "serial"})
public class MainClient extends JFrame {

	public static void main(String[] args) {
		new MainClient();
	}
	
	public JLayeredPane layeredPane = new JLayeredPane();
	
	public MainClient() {
		setLayout(null);
		setVisible(true);
		setTitle("mainView");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(Setting.locationX, Setting.locationY);
		add(layeredPane);
	}
	
	

	// Setting inner Methods

	private JComponent setJLayered(Component... components) {

		int i = 0;

		for (Component component : components)

			layeredPane.add(component, new Integer(i++));

		return layeredPane;

	}

	// Reflection Practice 다른 클래스의 정보를 가지고 와서 사용한다. 변수의 이름을 같게하면 된다

	public void setRectangles(Class<?> clazz, Object instance, Class<?> targetClass, Object target) throws Exception {

		Object tempObject = null;

		for (Field field : clazz.getDeclaredFields()) {

			if ((tempObject = field.get(instance)) instanceof JComponent) {

				((JComponent) tempObject)

						.setBounds((Rectangle) targetClass.getDeclaredField(field.getName()).get(target));

				((JComponent) tempObject).setOpaque(false);

				((JComponent) tempObject).setLayout(null);

			}

			if (tempObject instanceof Runnable)

				new Thread((Runnable) tempObject).start();

		}

	}
	
	
	

}
