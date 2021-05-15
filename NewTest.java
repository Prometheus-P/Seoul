package VomberDemo;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import java.net.*;
public class NewTest extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	int gr, num;
	public JButton jbt_ok = new JButton("생성하기");
	VomberDAO dao = new VomberDAO();
	String grName[] = new String[num];
	String groupName, group;
	final String id = "DEMO";
	
	//private  JCheckBox ch[] = new JCheckBox[gr];
	//private JScrollPane jsp = new JScrollPane();
	//Login lo;
	public void init() {
		Container con = this.getContentPane();
		//this.add(jsp);
		//con.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		con.setLayout(new BorderLayout());
		/*addGroup();
		for(int i=0; i<gr; ++i) {
			con.add("Center", ch[i]);
		}*/
		con.add("South",jbt_ok); jbt_ok.addActionListener(this);
	}
	
	public void file() {
		String fileName= JOptionPane.showInputDialog
				(this, "파일명 : ", "테스트 파일명 지정", 
						JOptionPane.INFORMATION_MESSAGE);
		try{
			File dir = new File("C:\\Users\\Haseong\\Desktop");
			File file = new File(dir, fileName +".txt");
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			//String id = lo.id;
			//Hashtable<String, ArrayList<VomberDTO>> hash = dao.hashVo();
			ArrayList<VomberDTO> list = dao.listVo();
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
				"생성완료", JOptionPane.INFORMATION_MESSAGE);
		int res = JOptionPane.showConfirmDialog
				(this, "전달 하시겠습니까?", 
				"파일 전송", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(res==0) send(fileName);
			
		}
		public void send(String fileName){
			File dir = new File("C:\\Users\\Haseong\\Desktop");
			File file = new File(dir, fileName +".xlsx");
			try{DatagramSocket ds = new DatagramSocket(); 
			InetAddress ia = InetAddress.getByName("localhost"); //보내고자 하는 주소
			DataInputStream dis = new DataInputStream
				(new BufferedInputStream(new FileInputStream(file)));
			byte[] by = new byte[65508];
			while(true) {
				int x = dis.read(by, 0, by.length);
				if (x==-1) break;
				DatagramPacket dp = new DatagramPacket(by, x, ia, 12345);
				ds.send(dp);
			}
			String exit = new String("exit");
			DatagramPacket dp = new DatagramPacket(exit.getBytes(), exit.getBytes().length, ia, 12345);
			ds.send(dp);
			ds.close();
			dis.close();
			JOptionPane.showMessageDialog(null, 
					"파일전송이 끝났습니다!!", "완료", 
					JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception e) {
				e.printStackTrace();
			}
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
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jbt_ok) {
			//check(); //num이랑 grName[]이 중요
			file();
			this.setVisible(false);
		}
	}
	/*public void check() {
	num=0;
	for(int i=0;i<gr;++i) {
		if(ch[i].isSelected()) {
			grName[num]= ch[i].getText(); // 체크된 group 이름 저장(시험지에 포함)
			num++; //체크된 group의 개수
		}
	}
}*/
/*public void addGroup() {
	ArrayList<VomberDTO> list = dao.listGr(); // 확인
	gr = list.size();//그룹의 개수
	for(int i=0; i<gr; ++i) {
		VomberDTO dto = list.get(gr);
		ch[i] = new JCheckBox();
		ch[i].setText(dto.getVogroup());;
	}
}*/
}