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
			pstmt.setString(2, "정보처리기사");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "2290");
			pstmt.setString(2, "정보처리산업기사");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "1104");
			pstmt.setString(2, "금속재료기사");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "1297");
			pstmt.setString(2, "섬유기사");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "1350");
			pstmt.setString(2, "도시계획기사");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "1611");
			pstmt.setString(2, "방사선비파괴검사기사");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "9546");
			pstmt.setString(2, "스포츠경엉관리사");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "7910");
			pstmt.setString(2, "한식조리기능사");
			pstmt.executeUpdate();
			
			/*
			 * sql="insert into short_examination values (?, ?)";
			 * pstmt=con.prepareStatement(sql); pstmt.setString(1, "6120");
			 * pstmt.setString(2, "정밀측정기능사"); pstmt.executeUpdate();
			 */
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "0210");
			pstmt.setString(2, "화공기술사");
			pstmt.executeUpdate();
			
			sql="insert into short_examination values (?, ?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "7893");
			pstmt.setString(2, "제빵기능사");
			pstmt.executeUpdate();

			System.out.println("테이블 초기화 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}