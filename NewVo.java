package VomberDemo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NewVo extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;

	JLabel title_lb = new JLabel("", JLabel.CENTER);
	JLabel eng_lb = new JLabel("Vocabulary : ", JLabel.RIGHT);
	JTextField eng_tf = new JTextField();
	JPanel eng_p = new JPanel();
	JLabel kor_lb = new JLabel("			한글 뜻", JLabel.RIGHT);
	JTextField kor_tf = new JTextField();
	JPanel kor_p = new JPanel();
	JPanel center_p = new JPanel();
	
	JButton ok_bt = new JButton("입력");
	JButton exit_bt = new JButton("취소");
	JPanel south_p = new JPanel();
	String vocabulary, meaning;
	public NewVo(Frame owner, String title) {
		super(owner, title);
		init();
		super.setSize(200, 200);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2) - this.getWidth() - 210;
		int ypos = (int)(screen.getHeight()/2) - this.getHeight() + 15;
		super.setLocation(xpos, ypos);
		super.setResizable(false);
		super.setVisible(false);
	}
	
	protected void center_setting() {
		center_p.setLayout(new GridLayout(2,1));
		center_p.add(eng_p);
		eng_p.setLayout(new BorderLayout());
		eng_p.add("West", eng_lb);
		eng_p.add(eng_tf);
		center_p.add(kor_p);
		kor_p.setLayout(new BorderLayout());
		kor_p.add("West", kor_lb);
		kor_p.add(kor_tf);
	}
	
	protected void south_setting() {
		south_p.setLayout(new FlowLayout());
		south_p.add(ok_bt); ok_bt.addActionListener(this);
		south_p.add(exit_bt); exit_bt.addActionListener(this);
	}
	
	public void setClose() {
		eng_tf.setText("");
		kor_tf.setText("");
		super.setVisible(false);
	}
	
	protected void init() {
		Container con = this.getContentPane();
		this.setTitle("단어등록");
		center_setting();
		south_setting();
		con.setLayout(new BorderLayout());
		title_lb.setFont(new Font("", Font.BOLD, 20));
		title_lb.setText(getTitle());
		con.add("North", title_lb);
		con.add("Center", center_p);
		con.add("South", south_p);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == ok_bt) {
			vocabulary = eng_tf.getText().trim();
			meaning = kor_tf.getText().trim();
			//dao.insertVo(vocabulary, meaning);
			setClose();
		}else if(e.getSource() == exit_bt) {
			setClose();
		}
	}
}
