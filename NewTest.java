package Vomber;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.swing.*;

public class NewTest extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	int gr, num;
	private  JCheckBox ch[] = new JCheckBox[gr];
	public JButton jbt_ok = new JButton("생성");
	private JScrollPane jsp = new JScrollPane();
	private JPanel pl = new JPanel();
	VomberDAO dao = new VomberDAO();
	String grName[] = new String[num];
	Login lo;
	String groupName, group;
	
	public void init() {
		Container con = this.getContentPane();
		jsp.add(con);
		con.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		con.setLayout(new BorderLayout());
		addGroup();
		for(int i=0; i<gr; ++i) {
			con.add("Center", ch[i]);
		}
		con.add("South",jbt_ok);
		pl.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 10));
		pl.add(jbt_ok); jbt_ok.addActionListener(this);
	}
	public void checked() {
		num=0;
		for(int i=0;i<gr;++i) {
			if(ch[i].isSelected()) {
				grName[num]= ch[i].getText(); // 체크된 group 이름 저장(시험지에 포함)
				num++; //체크된 group의 개수
			}
		}
	}
	public void addGroup() {
		String id = "haseong";
		ArrayList<VomberDTO> list = dao.listGr(id); // 확인
		gr = list.size();//그룹의 개수
		for(int i=0; i<gr; ++i) {
			VomberDTO dto = list.get(gr);
			ch[i] = new JCheckBox();
			ch[i].setText(dto.getVogroup());;
		}
	}
	public void file() {
		String fileName= JOptionPane.showInputDialog
				(this, "id : ", "새 테스트 명", 
						JOptionPane.INFORMATION_MESSAGE);
		try{
			File dir = new File("바탕 화면");
		
			File file = new File(dir, fileName +".xlsx");
		
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			String id = lo.id;
			Hashtable<String, ArrayList<VomberDTO>> hash = dao.hashVo(id);
			ArrayList<VomberDTO> list = hash.get(id);
			pw.println("\t");
			for(VomberDTO dto : list) {
				Random rd = new Random();
				if(rd.nextBoolean()) pw.println(dto.getVo());
				else 				 pw.println("\t"+dto.getMeaning());
			}
			pw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog
		(this, "바탕화면에 생성되었습니다!", 
				"생성완료", JOptionPane.ERROR_MESSAGE);
		}
	
	public NewTest(Frame owner, String title) {
		super(owner, title);
		
		init();
		
		super.setSize(500, 400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2) - this.getWidth() - 310;
		int ypos = (int)(screen.getHeight()/2) - this.getHeight() + 140;
		super.setLocation(xpos, ypos);
		super.setResizable(false);
		super.setVisible(false);
		//this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jbt_ok) {
			checked(); //num이랑 grName[]이 중요
			this.setVisible(false);
			file();
		}
	}
}