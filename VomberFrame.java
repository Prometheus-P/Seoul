package VomberDemo;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;
class VomberFrame extends JFrame implements ActionListener, Runnable{
	private static final long serialVersionUID = 1L;
	private JButton jbt_newVo = new JButton("단어 등록");
	private JButton jbt_delVo = new JButton("단어 삭제");
	private JButton jbt_newTe = new JButton("시험 생성");
	//private JButton jbt_user = new JButton("사용자 전환");
	private JButton jbt_exit = new JButton("종료");
	private JPanel pl = new JPanel();
	final String id = "DEMO";
	//int group_num;
	private JTabbedPane jtp = new JTabbedPane();
	//private JPanel jp[] = new JPanel[group_num];
	private JPanel jp_vo = new JPanel();
	private JLabel jlb_vo[];
	private JLabel jlb_meaning[];
	private JPanel pl1 = new JPanel();
	private JPanel pl2 = new JPanel();
	private JScrollPane jsp = new JScrollPane();
	
	//private CheckGroup dl_newGr;
	private NewVo dl_newVo = new NewVo(this, "단어 입력");
	private JDialog dlg = new JDialog();
	//private Label lb = new Label("저장되었습니다!");
	private VomberDAO dao = new VomberDAO();
	private NewTest nt;
	//private Login lo;
	
	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("West", pl);
		con.add("Center", jtp);
		jtp.add(jp_vo);
		jtp.add(jsp);
		jp_vo.setLayout(new BorderLayout());
		jp_vo.add("West", pl1);
		jp_vo.add("East", pl2);
		pl1.setLayout(new BorderLayout());
		pl1.setLayout(new FlowLayout());
		pl2.setLayout(new BorderLayout());
		pl2.setLayout(new FlowLayout());
		
		pl.setLayout(new GridLayout(4,1));
		pl.add(jbt_newVo); jbt_newVo.setToolTipText("새로운 단어그룹과 단어를 생성");
		jbt_newVo.addActionListener(this);
		pl.add(jbt_delVo); jbt_delVo.setToolTipText("단어그룹 또는 단어를 삭제");
		jbt_delVo.addActionListener(this);
		pl.add(jbt_newTe); jbt_newTe.setToolTipText("새로운 시험지를 생성");
		jbt_newTe.addActionListener(this);
		//pl.add(jbt_user); jbt_user.setToolTipText("다른 사용자로 전환");
		//jbt_user.addActionListener(this);
		pl.add(jbt_exit); jbt_exit.setToolTipText("프로그램 종료");
		jbt_exit.addActionListener(this);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void loginDemo() {}
	public void delVo() {
		String vocab = JOptionPane.showInputDialog
				(this, "Vocabulary : ", "삭제 할 단어 입력", 
						JOptionPane.INFORMATION_MESSAGE);
		dao.deleteVo(vocab.trim());
	}
	public void exit() {
		int res = JOptionPane.showConfirmDialog
				(this, "저장 후 종료하시겠습니까?", 
				"종료", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(res==0) {
			try {
				dao.save();
				Thread.sleep(3000);
			}catch(InterruptedException e) {}
			System.exit(0);
			}
			//boolean ok = dao.save();
				/*if(ok) {
					dlg.setLayout(new BorderLayout());
					dlg.add("Center", lb);
					dlg.setVisible(true);
					try {
						dao.save();
						Thread.sleep(3000);
					}catch(InterruptedException e) {}
					System.exit(0);
					}
				/*}else {
					JOptionPane.showMessageDialog
					(this, "저장이 안됐습니다!", 
							"오류", JOptionPane.ERROR_MESSAGE);
				}*/
			}
	public VomberFrame(String title) {
		super(title);
		JOptionPane.showMessageDialog
		(this, "DEMO 버전입니다", 
				"알림", JOptionPane.INFORMATION_MESSAGE);
		//id = lo.id;
		this.setTitle("DEMO Version");
		init();
		run();
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
					//dl_newGr.setVisible(true);
					boolean ok = dao.insertVo(vocabulary, meaning);
					if(ok) {
						JOptionPane.showMessageDialog
						(this, "저장됐습니다!", 
								"완료", JOptionPane.INFORMATION_MESSAGE);
						run();// 바로 기본 단어창 갱신
					}else {
						JOptionPane.showMessageDialog
						(this, "저장에 실패했습니다!", 
								"오류", JOptionPane.ERROR_MESSAGE);
						dl_newVo.setVisible(true); 
					}
					
					//dl_newGr.addGroup();
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
		/*ArrayList<VomberDTO> list = dao.listGr();
		group_num = 0;
			for(VomberDTO dto : list) {// 생성한 영단어 그룹별로 탭 생성
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
				}*/
			
			ArrayList<VomberDTO> list1 = dao.listVo();
			int num = list1.size();
			jlb_vo = new JLabel[num];
			jlb_meaning = new JLabel[num];
			int su=0;
			for(VomberDTO dtoVo : list1) {
				jlb_vo[su] = new JLabel(dtoVo.getVo().trim()); // 영단어 라벨 생성
				if(su%2==0) pl1.add("West", jlb_vo[su]); //영어와 한글 뜻 배치
				else pl2.add("West", jlb_vo[su]);
				su++;
				}
			ArrayList<VomberDTO> list2 = dao.listMeaning();
			su=0;
			for(VomberDTO dtoMean : list2) {
				jlb_meaning[su] = new JLabel(dtoMean.getMeaning().trim()); // 한글 뜻 라벨 생성
				if(su%2==0) pl1.add("East", jlb_meaning[su]);
				else pl2.add("East", jlb_meaning[su]);
				su++;
			}
		}
}