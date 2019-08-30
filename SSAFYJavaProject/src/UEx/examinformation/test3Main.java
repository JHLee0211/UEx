package UEx.examinformation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class test3Main {
	public static void main(String[] args) {
		String URL = "jdbc:mysql://localhost:3306/ssafyproject?useUnicode=true&characterEncoding=UTF8&jdbcCompliantTruncation=false&useOldUTF8Behavior&serverTimezone=UTC";
		String username = "root";
		String password = "";
		int jmcd = 1320;
		String jmNm = "정보처리기사";
		String url = "http://q-net.or.kr/crf005.do?id=crf00503s02&gSite=Q&gId=&jmCd="+jmcd+"&jmInfoDivCcd=B0&jmNm="+jmNm;
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Elements element = doc.select("div");
		
		Iterator<Element> ie1 = element.select("td").iterator();
		Iterator<Element> ie2 = element.select("li").iterator();
		Iterator<Element> ie3 = element.select("dd").iterator();
		
		String caution = "";
		while(ie2.hasNext()) {
			caution = caution + ie2.next().text();
		}
		
		String price = "";
		while(ie3.hasNext()) {
			price = ie3.next().text();
			break;
		}
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(URL, username, password);
			int savecount = 0;
			while(ie1.hasNext()) {
				String sql = "insert into examinformation values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(sql);
				String round = ie1.next().text();
				String w_recepts[] = ie1.next().text().split(" ~ ");
				Date w_recept_start = Date.valueOf(w_recepts[0].replace(".", "-"));
				Date w_recept_end = Date.valueOf(w_recepts[1].replace(".", "-"));
				Date w_exam = Date.valueOf(ie1.next().text().replace(".", "-"));
				Date w_presentation = Date.valueOf(ie1.next().text().replace(".", "-"));
				String p_recepts[] = ie1.next().text().split(" ~ ");
				Date p_recept_start = Date.valueOf(p_recepts[0].replace(".", "-"));
				Date p_recept_end = Date.valueOf(p_recepts[1].replace(".", "-"));
				String p_exams[] = ie1.next().text().split("~");
				Date p_exam_start = Date.valueOf(p_exams[0].replace(".", "-"));
				Date p_exam_end = Date.valueOf(p_exams[1].replace(".", "-"));
				Date p_presentation = Date.valueOf(ie1.next().text().replace(".", "-"));
				savecount++;

				pstmt.setInt(1, jmcd);
				pstmt.setString(2, round);
				pstmt.setDate(3, w_recept_start);
				pstmt.setDate(4, w_recept_end);
				pstmt.setDate(5, w_exam);
				pstmt.setDate(6, w_presentation);
				pstmt.setDate(7, p_recept_start);
				pstmt.setDate(8, p_recept_end);
				pstmt.setDate(9, p_exam_start);
				pstmt.setDate(10, p_exam_end);
				pstmt.setDate(11, p_presentation);
				
				pstmt.executeUpdate();
				
				if(savecount == 3)
					break;
			}
			
			String sql = "insert into examinformation_sub values (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, jmcd);
			pstmt.setString(2, caution);
			pstmt.setString(3, price);
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}