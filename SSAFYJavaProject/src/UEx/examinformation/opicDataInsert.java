package UEx.examinformation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class opicDataInsert{
    public static void main(String[] args) throws Exception{
    	String URL = "jdbc:mysql://localhost:3306/ssafyproject?useUnicode=true&characterEncoding=UTF8&jdbcCompliantTruncation=false&useOldUTF8Behavior&serverTimezone=UTC";
		String username = "root";
		String password = "";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, username, password);
			
			String url = "http://www.opic.or.kr/opics/servlet/controller.opic.site.receipt.ExamReceiptServlet?p_process=select-list&p_nav=1_1";
			Document doc = null;

			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Elements element = doc.select(".pExam > table > tbody");			
			Iterator<Element> ie1 = element.select("td").iterator();
			
			String sql = "delete from examinformation where jmcd = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "1");
			pstmt.executeUpdate();
			
			sql = "delete from examinformation_sub where jmcd = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "1");
			pstmt.executeUpdate();
			
			sql = "delete from short_examination where jmcd = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "1");
			pstmt.executeUpdate();

			String jmcd = "1";
			while(ie1.hasNext()) {
				String sqls = "insert into examinformation values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(sqls);

				Date p_exam_start = Date.valueOf(ie1.next().text());
				Date p_exam_end = p_exam_start;
				ie1.next().text(); // 시험종류
				String p_recepts[] = ie1.next().text().split("~");
				Date p_recept_start = Date.valueOf(p_recepts[0].replace(".", "-").trim());
				Date p_recept_end = Date.valueOf(p_recepts[1].replace(".", "-").trim());
				ie1.next().text(); // 마감일
				Date p_presentation = Date.valueOf(ie1.next().text().split(" ")[0]);
				ie1.next().text(); // 접수
				
				pstmt.setString(1, jmcd);
				pstmt.setString(2, null);
				pstmt.setDate(3, null);
				pstmt.setDate(4, null);
				pstmt.setDate(5, null);
				pstmt.setDate(6, null);
				pstmt.setDate(7, null);
				pstmt.setDate(8, p_recept_start);
				pstmt.setDate(9, p_recept_end);
				pstmt.setDate(10, p_exam_start);
				pstmt.setDate(11, p_exam_end);
				pstmt.setDate(12, p_presentation);
				pstmt.setString(13, null);
				pstmt.setString(14, "http://www.opic.or.kr/opics/servlet/controller.opic.site.receipt.ExamReceiptServlet?p_process=select-list&p_nav=1_1");
				
				pstmt.executeUpdate();
			}
			
			url = "http://www.opic.or.kr/opics/servlet/controller.opic.site.about.AboutServlet?p_process=move-page-introduce&p_nav=3_1";
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			element = doc.select(".pAbout");			
			ie1 = element.select("td").iterator();
			String caution = "";
			String price = "";
			while(ie1.hasNext()) {
				ie1.next().text();
				caution = ie1.next().text();
				price = ie1.next().text();
				caution = caution + "\n" + ie1.next().text();
				break;
			}
			
			sql = "insert into examinformation_sub values (?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, jmcd);
			pstmt.setString(2, caution);
			pstmt.setString(3, price);
			pstmt.executeUpdate();
			
			sql = "insert into short_examination values (?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, jmcd);
			pstmt.setString(2, "OPIc");
			pstmt.executeUpdate();

			System.out.println("데이터 추가 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}