package UEx.examinformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InitSql {
	public static void main(String[] args) {
		String URL = "jdbc:mysql://localhost:3306/ssafyproject?useUnicode=true&characterEncoding=UTF8&jdbcCompliantTruncation=false&useOldUTF8Behavior&serverTimezone=UTC";
		String username = "root";
		String password = "";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, username, password);
			
			String sql="delete from customerinformation";
			pstmt=con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql="delete from customerinterest";
			pstmt=con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql="delete from examinformation";
			pstmt=con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql="delete from examinformation_sub";
			pstmt=con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql="delete from short_examination";
			pstmt=con.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "1320");
			pstmt.setString(2, "����ó�����");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "2290");
			pstmt.setString(2, "����ó��������");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "1104");
			pstmt.setString(2, "�ݼ������");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "1297");
			pstmt.setString(2, "�������");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "1350");
			pstmt.setString(2, "���ð�ȹ���");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "1611");
			pstmt.setString(2, "��缱���ı��˻���");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "9546");
			pstmt.setString(2, "���������������");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "7910");
			pstmt.setString(2, "�ѽ�������ɻ�");
			pstmt.executeUpdate();
			
			/*
			 * sql="insert into short_examination values (?, ?)";
			 * pstmt=con.prepareStatement(sql); pstmt.setString(1, "6120");
			 * pstmt.setString(2, "����������ɻ�"); pstmt.executeUpdate();
			 */
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "0210");
			pstmt.setString(2, "ȭ�������");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "7893");
			pstmt.setString(2, "������ɻ�");
			pstmt.executeUpdate();

			System.out.println("���̺� �ʱ�ȭ �Ϸ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}