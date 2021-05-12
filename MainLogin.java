package Vomber;

import javax.swing.*;

public class MainLogin{
	public static void main(String[] args) {
		/*Login lo = new Login();
		lo.login();*/
	}
}
class Login extends JFrame{
	private static final long serialVersionUID = 1L;
	String id;
	VomberDAO dao;
	VomberDTO dto;
	VomberFrame vf;
	
	public Login() {}
	
	public void login() {
		dao = new VomberDAO();
		int res = JOptionPane.showConfirmDialog
				(this, "ȸ���̽Ű���?", 
				"ȸ��Ȯ��", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(res==0) {
			id = JOptionPane.showInputDialog
					(this, "id : ", "�Է�", 
							JOptionPane.INFORMATION_MESSAGE).trim();
			boolean ok = dao.checkId(id);
			if(ok) {
				dto.setId(id);
				vf = new VomberFrame("�ܾ����ݱ�");
				return;
			}else {
					JOptionPane.showMessageDialog
					(this, "�ٽ� �Է����ּ���!", 
							"���� ID", JOptionPane.ERROR_MESSAGE);
					login();
				}
			}else userInsert();
		}
	public void userInsert() {
		 String user= JOptionPane.showInputDialog
				(this, "id : ", "����� ���", 
						JOptionPane.INFORMATION_MESSAGE).trim();
		 dao = new VomberDAO();
		 boolean ok = dao.checkId(user);
		 if(!ok) {
			boolean done = dao.setUser(user);
			if(done) {
				JOptionPane.showMessageDialog
				(this, "��ϵǾ����ϴ�.", 
						"Ȯ��", JOptionPane.INFORMATION_MESSAGE);
				dto.setId(id);
				vf = new VomberFrame("�ܾ����ݱ�");
				vf.setVisible(true);
				return;
			}else {
				JOptionPane.showMessageDialog
				(this, "�ٽ� �������ּ���!", 
					  "����", JOptionPane.ERROR_MESSAGE);
				userInsert();
			}
		}else {
			JOptionPane.showMessageDialog
			(this, "���� id�� ����ϴ� ����ڰ� �ֽ��ϴ�.", 
				  "����", JOptionPane.ERROR_MESSAGE);
			userInsert();
		}
	}
}
