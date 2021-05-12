package Vomber;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;
class VomberFrame1 extends JFrame implements ActionListener, Runnable{
	private static final long serialVersionUID = 1L;
	private JButton jbt_newVo = new JButton("단어 등록");
	private JButton jbt_delVo = new JButton("단어 삭제");
	private JButton jbt_newTe = new JButton("시험 생성");
	private JButton jbt_user = new JButton("사용자 전환");
	private JButton jbt_exit = new JButton("종료");
	private JPanel pl = new JPanel();
	String id;
	int group_num;
	private JTabbedPane jtp = new JTabbedPane();
	private JPanel jp[] = new JPanel[group_num];
	private JLabel jlb_vo[] = new JLabel[group_num];
	private JLabel jlb_meaning[] = new JLabel[group_num];
	private JPanel pl1 = new JPanel();
	private JPanel pl2 = new JPanel();
	private JScrollPane jsp = new JScrollPane();
	
	private DialogVoGroup dl_newGr = new DialogVoGroup(this, "그룹 지정");
	private DialogNewVo dl_newVo = new DialogNewVo(this, "단어 입력");
	private JDialog dlg = new JDialog();
	private Label lb = new Label("저장되었습니다!");
	private VomberDAO dao = new VomberDAO();
	private NewTest nt;// = new NewTest(this, "테스트 생성");
	private Login lo;
	
	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("West", pl);
		con.add("Center", jtp);

		pl.setLayout(new GridLayout(5,1));
		pl.add(jbt_newVo); jbt_newVo.setToolTipText("새로운 단어그룹과 단어를 생성");
		jbt_newVo.addActionListener(this);
		pl.add(jbt_delVo); jbt_delVo.setToolTipText("단어그룹 또는 단어를 삭제");
		jbt_delVo.addActionListener(this);
		pl.add(jbt_newTe); jbt_newTe.setToolTipText("새로운 시험지를 생성");
		jbt_newTe.addActionListener(this);
		pl.add(jbt_user); jbt_user.setToolTipText("다른 사용자로 전환");
		jbt_user.addActionListener(this);
		pl.add(jbt_exit); jbt_exit.setToolTipText("프로그램 종료");
		jbt_exit.addActionListener(this);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void delVo() {
		String vocab = JOptionPane.showInputDialog
				(this, "Vocabulary : ", "삭제 할 단어 입력", 
						JOptionPane.INFORMATION_MESSAGE);
		dao.deleteVo(id, vocab.trim());
	}
	public void exit() {
		int res = JOptionPane.showConfirmDialog
				(this, "저장 후 종료하시겠습니까?", 
				"종료", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(res==0) {
			boolean ok = dao.save();
				if(ok) {
					dlg.setLayout(new BorderLayout());
					dlg.add("Center", lb);
					dlg.setVisible(true);
					try {
						Thread.sleep(3000);
					}catch(InterruptedException e) {}
					System.exit(0);
					}
				}else {
					JOptionPane.showMessageDialog
					(this, "저장이 안됐습니다!", 
							"오류", JOptionPane.ERROR_MESSAGE);
		}
	}
	public VomberFrame1(String title) {
		super(title);
		//id = lo.id;
		id="haseong";
		run();
		this.setTitle(id+"의 단어장");
		//super (title);
		init();
		
		this.setSize(600,500);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2) - this.getWidth()/2;
		int ypos = (int)(screen.getHeight()/2) - this.getHeight()/2;
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		this.setVisible(true);
		dlg.setBounds(xpos, ypos, 200, 150);
		dlg.setVisible(false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			if(e.getSource() == jbt_newVo) {//단어 입력 버튼
				dl_newVo.setVisible(true); 
				if(e.getSource() == dl_newVo.ok_bt) { // 단어 입력 창에서 확인 버튼
					String vocabulary = dl_newVo.vocabulary;
					String meaning = dl_newVo.meaning;
					dl_newGr.setVisible(true);
					if(e.getSource()==dl_newGr.jbt_ok) {
						String group = dl_newGr.group;
						dao.insertVo(id, group, vocabulary, meaning);
						run();// 바로 기본 단어창 갱신
					}
					else if(e.getSource() == dl_newGr.jbt_new){
						String vogroup = dl_newGr.groupName;//새로운
						dao.insertVo(id, vogroup, vocabulary, meaning);
						//dl_newGr.addGroup();
						run();
					}
				}
			}else if(e.getSource() == jbt_newTe) {
				//newTe();
				nt = new NewTest(this, "테스트 생성");
				nt.setVisible(true);
			}else if(e.getSource() == jbt_delVo) {
				delVo();
			}else if(e.getSource()==jbt_exit) {
				exit();
		}
	}
	@Override
	public void run() { // 기본 단어창 정렬
		// TODO Auto-generated method stub
		ArrayList<VomberDTO> list = dao.listGr(id);
		group_num = 0;
			for(VomberDTO dto : list) {
				jtp.addTab(dto.getVogroup().trim(), jp[group_num]); //탭 생성
				jp[group_num].setLayout(new BorderLayout()); // 영단어와 뜻이 쓰일 공간 분리
				jp[group_num].add("West", pl1);
				jp[group_num].add("East", pl2);
				pl1.setLayout(new BorderLayout()); //좌우(영단어 : 뜻)
				pl1.setLayout(new FlowLayout()); //위아래
				pl2.setLayout(new BorderLayout());
				pl2.setLayout(new FlowLayout());
				jp[group_num].add(jsp);//스크롤 가능
				++group_num;
				}
			
			ArrayList<VomberDTO> list1= dao.listVo(id);
			for(VomberDTO dtoVo : list1) {
				jlb_vo[group_num] = new JLabel(dtoVo.getVo().trim()); // 영단어 라벨 생성
				jlb_meaning[group_num] = new JLabel(dtoVo.getMeaning().trim()); // 한글 뜻 라벨 생성
				pl1.add("West", jlb_vo[group_num]); //영어와 한글 뜻 배치
				pl1.add("East", jlb_meaning[group_num]); 
				pl2.add("West",  jlb_vo[group_num]);
				pl2.add("East",  jlb_meaning[group_num]);
				}
	}
}
public class VomberFrame{
	public static void main(String[] args) {
		VomberFrame1 vf = new VomberFrame1("단어 폭격기");
	}
}
