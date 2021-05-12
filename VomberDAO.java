package Vomber;

import java.sql.*;
import java.util.*;

public class VomberDAO {//user ��� ���̺��� ���� �Ǿ� �ִ� ����
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	String id, url, user, pass;
	
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
	public boolean setUser(String id) { // �� ȸ�� table�� ����� �� �����ϸ�, true ��ȯ
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
	}
	public boolean checkId(String id) { 
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
	}
	
	public boolean insertVo(String id, String vogroup, String vocabulary, String meaning) {
		int res = 0;
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "insert into "+id+" values(?,?,?,?)";
			ps = con.prepareStatement(sql);
			/*if(test) ps.setInt(1, 1); // 1�̸� ������ ������ ����
			else ps.setInt(1, 0);*/
			ps.setString(1, vogroup);
			ps.setString(2, vocabulary);
			ps.setString(3, meaning);
			res = ps.executeUpdate();
			if(res>0) return true;
			else return false;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteVo(String id, String vocabulary) {
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
	public ArrayList<VomberDTO> listGr(String id) { // vogroup�� listȭ
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
	}
	public ArrayList<VomberDTO> listVo(String id) { // vogroup�� listȭ
		ArrayList<VomberDTO> list = new ArrayList<>();
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "select distinct vocabulary from "+id; // �ߺ� �ȵǰ�
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				VomberDTO dto = new VomberDTO();
				dto.setVogroup(rs.getString("vocabulary"));
				list.add(dto);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list; // �׷��� ���� �ߺ��� �ȵǹǷ� list.size()�� �ٷ� �׷��� ����
	}
	public Hashtable<String,ArrayList<VomberDTO>> hashVo(String id) {
		Hashtable<String,ArrayList<VomberDTO>> hash = new Hashtable<>();
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "select * from "+id; // id ���̺�� ������
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				ArrayList<VomberDTO> list = new ArrayList<>();
				VomberDTO dto = new VomberDTO();
				//dto.setTest(rs.getInt("test"));
				dto.setVogroup(rs.getString("vogroup"));
				dto.setVo(rs.getString("vocabulary"));
				dto.setMeaning(rs.getString("meaning"));
				list.add(dto);
				hash.put(id, list);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return hash;
	}
	public boolean save() {
		int res = 0;
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "commit()";
			ps = con.prepareStatement(sql);
			res = ps.executeUpdate();
			if(res>0) return true;
			else return false;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
