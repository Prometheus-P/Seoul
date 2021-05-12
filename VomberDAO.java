package VomberDemo;

import java.sql.*;
import java.util.*;

public class VomberDAO {//user ��� ���̺��� ���� �Ǿ� �ִ� ����
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	String url, user, pass;
	final static String id = "DEMO";
	public VomberDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			url = "jdbc:oracle:thin:@localhost:1521:xe";
			user = "bigdata3";
			pass = "bigdata3";
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public boolean insertVo(String vocabulary, String meaning) {
		int res = 0;
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "insert into "+id+ " values('?','?')";
			ps = con.prepareStatement(sql);
			//ps.setString(1, vogroup);
			ps.setString(1, vocabulary);
			ps.setString(2, meaning);
			res = ps.executeUpdate();
			if(res>0) return true;
			else return false;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean deleteVo(String vocabulary) {
		int res = 0;
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "delete from "+ id +" where vocabulary = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, vocabulary);
			res = ps.executeUpdate();
			if(res>0) return true;
			else return false;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public ArrayList<VomberDTO> listVo() {
		ArrayList<VomberDTO> list = new ArrayList<>();
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "select distinct vocabulary from "+id; // �ߺ� �ȵǰ�
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				VomberDTO dto = new VomberDTO();
				dto.setVo(rs.getString("vocabulary"));
				list.add(dto);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list; // �׷��� ���� �ߺ��� �ȵǹǷ� list.size()�� �ٷ� �׷��� ����
	}
	public ArrayList<VomberDTO> listMeaning() {
		ArrayList<VomberDTO> list = new ArrayList<>();
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "select distinct meaning from "+id; // �ߺ� �ȵǰ�
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				VomberDTO dto = new VomberDTO();
				dto.setMeaning(rs.getString("meaning"));
				list.add(dto);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list; // �׷��� ���� �ߺ��� �ȵǹǷ� list.size()�� �ٷ� �׷��� ����
	}
	public void save() {
		//int res = 0;
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "commit()";
			ps = con.prepareStatement(sql);
			/*res = ps.executeUpdate();
			if(res>0) return true;
			else return false;*/
		}catch(SQLException e) {
			e.printStackTrace();
			//return false;
		}
	}
	/*public boolean setUser(String id) { // �� ȸ�� table�� ����� �� �����ϸ�, true ��ȯ
	//int res = 0;
	this.id = id;
	try {
		con = DriverManager.getConnection(url, user, pass);
		String sql = "CREATE TABLE "+id+"( ";
		sql+="vogroup varchar(70),";
		sql+="vocabulary varchar(80),";
		sql+="meaning varchar(80) )";
		
		Statement stmt = con.createStatement();
		boolean b = stmt.execute(sql);
		//if(stmt!=null) stmt.close();
		//if(con!=null) con.close();
		if(!b) return true;
		else return false;
	}catch(SQLException e) {
		e.printStackTrace();
		return false;
	}
}*/
/*public boolean checkId(String id) { 
	try {
		con = DriverManager.getConnection(url, user, pass);
		String sql = "SELECT TABLE_NAME";
			sql+=" FROM USER_TABLES";
			sql+=" WHERE TABLE_NAME = UPPER('"+id+"')";
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next()) {
			if(id.equals(rs.getString("TABLE_NAME").trim())) return true;
		}
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return false; // �׷��� ���� �ߺ��� �ȵǹǷ� list.size()�� �ٷ� �׷��� ����
}*/
	/*public ArrayList<VomberDTO> listGr() { // vogroup�� listȭ
	ArrayList<VomberDTO> list = new ArrayList<>();
	try {
		con = DriverManager.getConnection(url, user, pass);
		String sql = "select distinct vogroup from "+id; // �ߺ� �ȵǰ�
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next()) {
			VomberDTO dto = new VomberDTO();
			dto.setVogroup(rs.getString("vogroup"));
			list.add(dto);
		}
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return list; // �׷��� ���� �ߺ��� �ȵǹǷ� list.size()�� �ٷ� �׷��� ����
}*/
	/*public Hashtable<String,ArrayList<VomberDTO>> hashVo() {
	Hashtable<String,ArrayList<VomberDTO>> hash = new Hashtable<>();
	try {
		con = DriverManager.getConnection(url, user, pass);
		String sql = "select * from "+id; // id ���̺�� ������
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next()) {
			ArrayList<VomberDTO> list = new ArrayList<>();
			VomberDTO dto = new VomberDTO();
			//dto.setVogroup(rs.getString("vogroup"));
			dto.setVo(rs.getString("vocabulary"));
			dto.setMeaning(rs.getString("meaning"));
			list.add(dto);
			hash.put(id, list);
		}
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return hash;
}*/
}
