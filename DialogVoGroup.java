package Vomber;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

class DialogVoGroup extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	int gr;
	private  Choice ch[] = new  Choice[gr];
	public JButton jbt_ok = new JButton("선택확인");
	public JButton jbt_new = new JButton("그룹생성");
	private JScrollPane jsp = new JScrollPane();
	private JPanel pl = new JPanel();
	private VomberDAO dao;
	String groupName, group;
	//private Login lo;
	
	public void init() {
		Container con = this.getContentPane();
		jsp.add(con);
		con.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		con.setLayout(new BorderLayout());
		addGroup();
		for(int i=0; i<gr; ++i) {
			con.add("Center", ch[i]);
		}
		con.add("South",pl);
		pl.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 10));
		pl.add(jbt_ok); jbt_ok.addActionListener(this);
		pl.add(jbt_new); jbt_new.addActionListener(this);
	}
	public void addGroup() {
		dao = new VomberDAO();
		ArrayList<VomberDTO> list = dao.listGr();
		gr = list.size();//그룹의 개수
		for(int i=0; i<gr; ++i) {
			VomberDTO dto = list.get(gr);
			ch[i] = new Choice();
			ch[i].setName(dto.getVogroup());
		}
	}
	public DialogVoGroup(Frame owner, String title) {
		super(owner, title);
		init();
		super.setSize(300, 300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2) - this.getWidth() - 210;
		int ypos = (int)(screen.getHeight()/2) - this.getHeight() + 50;
		super.setLocation(xpos, ypos);
		super.setResizable(false);
		super.setVisible(false);
		//this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jbt_ok) {
			for(Choice choice : ch) {//골라진 그룹의 이름을 받는다
				group = choice.getSelectedItem().trim();
			}
			super.setVisible(false);
		}else if(e.getSource() == jbt_new) {
			groupName = JOptionPane.showInputDialog
				(this, "그룹 명 : ", "새 그룹 생성", 
					JOptionPane.INFORMATION_MESSAGE).trim();
		}
	}
}