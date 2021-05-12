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
				(this, "회원이신가요?", 
				"회원확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(res==0) {
			id = JOptionPane.showInputDialog
					(this, "id : ", "입력", 
							JOptionPane.INFORMATION_MESSAGE).trim();
			boolean ok = dao.checkId(id);
			if(ok) {
				dto.setId(id);
				vf = new VomberFrame("단어폭격기");
				return;
			}else {
					JOptionPane.showMessageDialog
					(this, "다시 입력해주세요!", 
							"없는 ID", JOptionPane.ERROR_MESSAGE);
					login();
				}
			}else userInsert();
		}
	public void userInsert() {
		 String user= JOptionPane.showInputDialog
				(this, "id : ", "사용자 등록", 
						JOptionPane.INFORMATION_MESSAGE).trim();
		 dao = new VomberDAO();
		 boolean ok = dao.checkId(user);
		 if(!ok) {
			boolean done = dao.setUser(user);
			if(done) {
				JOptionPane.showMessageDialog
				(this, "등록되었습니다.", 
						"확인", JOptionPane.INFORMATION_MESSAGE);
				dto.setId(id);
				vf = new VomberFrame("단어폭격기");
				vf.setVisible(true);
				return;
			}else {
				JOptionPane.showMessageDialog
				(this, "다시 가입해주세요!", 
					  "오류", JOptionPane.ERROR_MESSAGE);
				userInsert();
			}
		}else {
			JOptionPane.showMessageDialog
			(this, "같은 id를 사용하는 사용자가 있습니다.", 
				  "오류", JOptionPane.ERROR_MESSAGE);
			userInsert();
		}
	}
}
