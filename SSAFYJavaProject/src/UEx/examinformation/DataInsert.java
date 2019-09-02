package UEx.examinformation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class DataInsert{
    public static void main(String[] args) throws Exception{
    	String URL = "jdbc:mysql://localhost:3306/ssafyproject?useUnicode=true&characterEncoding=UTF8&jdbcCompliantTruncation=false&useOldUTF8Behavior&serverTimezone=UTC";
		String username = "root";
		String password = "";
		Document doc = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		Calendar cal = Calendar.getInstance();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, username, password);

			String sql="select * from short_examination";
			pstmt=con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){ //DB에 있는 값들 가져오기
				String jmcd=rs.getString("jmcd");
				String jmNm=rs.getString("name");
				String url = "http://q-net.or.kr/crf005.do?id=crf00503s02&gSite=Q&gId=&jmCd="+jmcd+"&jmInfoDivCcd=B0&jmNm="+jmNm;
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
				
				while(ie1.hasNext()) {
					String sqls = "insert into examinformation values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					pstmt = con.prepareStatement(sqls);
					String round = ie1.next().text();
					if(round.length() <= 4 || !round.substring(0, 4).equals(cal.get(Calendar.YEAR)+"")) {
						continue;
					}
					
					String w_recepts[] = ie1.next().text().split("~");
					for(int a=0; a<w_recepts.length; a++) {
						System.out.println(w_recepts[a] + " " + a + " " + jmNm);
					}
					Date w_recept_start = Date.valueOf(w_recepts[0].replace(".", "-").trim());
					Date w_recept_end = Date.valueOf(w_recepts[1].replace(".", "-").trim());
					String w_exams[] = ie1.next().text().split("~");
					Date w_exam_start = Date.valueOf(w_exams[0].replace(".", "-").trim());
					Date w_exam_end = Date.valueOf(w_exams[0].replace(".", "-").trim());
					if(w_exams.length != 1) {
						w_exam_end = Date.valueOf(w_exams[1].replace(".", "-").trim());
					}
					Date w_presentation = Date.valueOf(ie1.next().text().replace(".", "-"));
					
					String p_recepts[] = ie1.next().text().split("~");
					Date p_recept_start = Date.valueOf(p_recepts[0].replace(".", "-").trim());
					Date p_recept_end = Date.valueOf(p_recepts[1].replace(".", "-").trim());
					String p_exams[] = ie1.next().text().split("~");
					Date p_exam_start = Date.valueOf(p_exams[0].replace(".", "-").trim());
					Date p_exam_end = Date.valueOf(p_exams[1].replace(".", "-").trim());
					Date p_presentation = Date.valueOf(ie1.next().text().replace(".", "-"));
					
					pstmt.setString(1, jmcd);
					pstmt.setString(2, round);
					pstmt.setDate(3, w_recept_start);
					pstmt.setDate(4, w_recept_end);
					pstmt.setDate(5, w_exam_start);
					pstmt.setDate(6, w_exam_end);
					pstmt.setDate(7, w_presentation);
					pstmt.setDate(8, p_recept_start);
					pstmt.setDate(9, p_recept_end);
					pstmt.setDate(10, p_exam_start);
					pstmt.setDate(11, p_exam_end);
					pstmt.setDate(12, p_presentation);
					
					pstmt.executeUpdate();
				}
				
				String sqlss = "insert into examinformation_sub values (?, ?, ?)";
				pstmt = con.prepareStatement(sqlss);
				
				pstmt.setString(1, jmcd);
				pstmt.setString(2, caution);
				pstmt.setString(3, price);
				
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}