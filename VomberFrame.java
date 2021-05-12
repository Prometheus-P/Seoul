package Vomber;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;
class VomberFrame1 extends JFrame implements ActionListener, Runnable{
	private static final long serialVersionUID = 1L;
	private JButton jbt_newVo = new JButton("�ܾ� ���");
	private JButton jbt_delVo = new JButton("�ܾ� ����");
	private JButton jbt_newTe = new JButton("���� ����");
	private JButton jbt_user = new JButton("����� ��ȯ");
	private JButton jbt_exit = new JButton("����");
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
	
	private DialogVoGroup dl_newGr = new DialogVoGroup(this, "�׷� ����");
	private DialogNewVo dl_newVo = new DialogNewVo(this, "�ܾ� �Է�");
	private JDialog dlg = new JDialog();
	private Label lb = new Label("����Ǿ����ϴ�!");
	private VomberDAO dao = new VomberDAO();
	private NewTest nt;// = new NewTest(this, "�׽�Ʈ ����");
	private Login lo;
	
	public void init() {
		Container con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add("West", pl);
		con.add("Center", jtp);

		pl.setLayout(new GridLayout(5,1));
		pl.add(jbt_newVo); jbt_newVo.setToolTipText("���ο� �ܾ�׷�� �ܾ ����");
		jbt_newVo.addActionListener(this);
		pl.add(jbt_delVo); jbt_delVo.setToolTipText("�ܾ�׷� �Ǵ� �ܾ ����");
		jbt_delVo.addActionListener(this);
		pl.add(jbt_newTe); jbt_newTe.setToolTipText("���ο� �������� ����");
		jbt_newTe.addActionListener(this);
		pl.add(jbt_user); jbt_user.setToolTipText("�ٸ� ����ڷ� ��ȯ");
		jbt_user.addActionListener(this);
		pl.add(jbt_exit); jbt_exit.setToolTipText("���α׷� ����");
		jbt_exit.addActionListener(this);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void delVo() {
		String vocab = JOptionPane.showInputDialog
				(this, "Vocabulary : ", "���� �� �ܾ� �Է�", 
						JOptionPane.INFORMATION_MESSAGE);
		dao.deleteVo(id, vocab.trim());
	}
	public void exit() {
		int res = JOptionPane.showConfirmDialog
				(this, "���� �� �����Ͻðڽ��ϱ�?", 
				"����", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
					(this, "������ �ȵƽ��ϴ�!", 
							"����", JOptionPane.ERROR_MESSAGE);
		}
	}
	public VomberFrame1(String title) {
		super(title);
		//id = lo.id;
		id="haseong";
		run();
		this.setTitle(id+"�� �ܾ���");
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
			if(e.getSource() == jbt_newVo) {//�ܾ� �Է� ��ư
				dl_newVo.setVisible(true); 
				if(e.getSource() == dl_newVo.ok_bt) { // �ܾ� �Է� â���� Ȯ�� ��ư
					String vocabulary = dl_newVo.vocabulary;
					String meaning = dl_newVo.meaning;
					dl_newGr.setVisible(true);
					if(e.getSource()==dl_newGr.jbt_ok) {
						String group = dl_newGr.group;
						dao.insertVo(id, group, vocabulary, meaning);
						run();// �ٷ� �⺻ �ܾ�â ����
					}
					else if(e.getSource() == dl_newGr.jbt_new){
						String vogroup = dl_newGr.groupName;//���ο�
						dao.insertVo(id, vogroup, vocabulary, meaning);
						//dl_newGr.addGroup();
						run();
					}
				}
			}else if(e.getSource() == jbt_newTe) {
				//newTe();
				nt = new NewTest(this, "�׽�Ʈ ����");
				nt.setVisible(true);
			}else if(e.getSource() == jbt_delVo) {
				delVo();
			}else if(e.getSource()==jbt_exit) {
				exit();
		}
	}
	@Override
	public void run() { // �⺻ �ܾ�â ����
		// TODO Auto-generated method stub
		ArrayList<VomberDTO> list = dao.listGr(id);
		group_num = 0;
			for(VomberDTO dto : list) {
				jtp.addTab(dto.getVogroup().trim(), jp[group_num]); //�� ����
				jp[group_num].setLayout(new BorderLayout()); // ���ܾ�� ���� ���� ���� �и�
				jp[group_num].add("West", pl1);
				jp[group_num].add("East", pl2);
				pl1.setLayout(new BorderLayout()); //�¿�(���ܾ� : ��)
				pl1.setLayout(new FlowLayout()); //���Ʒ�
				pl2.setLayout(new BorderLayout());
				pl2.setLayout(new FlowLayout());
				jp[group_num].add(jsp);//��ũ�� ����
				++group_num;
				}
			
			ArrayList<VomberDTO> list1= dao.listVo(id);
			for(VomberDTO dtoVo : list1) {
				jlb_vo[group_num] = new JLabel(dtoVo.getVo().trim()); // ���ܾ� �� ����
				jlb_meaning[group_num] = new JLabel(dtoVo.getMeaning().trim()); // �ѱ� �� �� ����
				pl1.add("West", jlb_vo[group_num]); //����� �ѱ� �� ��ġ
				pl1.add("East", jlb_meaning[group_num]); 
				pl2.add("West",  jlb_vo[group_num]);
				pl2.add("East",  jlb_meaning[group_num]);
				}
	}
}
public class VomberFrame{
	public static void main(String[] args) {
		VomberFrame1 vf = new VomberFrame1("�ܾ� ���ݱ�");
	}
}
