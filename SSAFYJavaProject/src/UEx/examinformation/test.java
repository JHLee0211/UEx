import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class test{
    public static void main(String[] args) throws Exception{
    	String URL = "jdbc:mysql://localhost:3306/ssafyproject?useUnicode=true&characterEncoding=UTF8&jdbcCompliantTruncation=false&useOldUTF8Behavior&serverTimezone=UTC";
		String username = "root";
		String password = "";
		//int jmcd = 1320;
		//String jmNm = "정보처리기사";
		//String url = "http://q-net.or.kr/crf005.do?id=crf00503s02&gSite=Q&gId=&jmCd="+jmcd+"&jmInfoDivCcd=B0&jmNm="+jmNm;
		Document doc = null;
		

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, username, password);
			
			String init="delete from examinformation";
			pstmt=con.prepareStatement(init); //기존에 있는 정보들 제거(중복배체)
			pstmt.executeUpdate();
			
			
			String init2="delete from examinformation_sub";
			pstmt=con.prepareStatement(init2);
			pstmt.executeUpdate();
			
			String sql="select * from short_examination";
			pstmt=con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()){ //DB에 있는 값들 가져오기
				String jmcd=rs.getString("jmcd");
				String jmNm=rs.getString("name");
			//	System.out.println(jmcd+", "+name);
				String url = "http://q-net.or.kr/crf005.do?id=crf00503s02&gSite=Q&gId=&jmCd="+jmcd+"&jmInfoDivCcd=B0&jmNm="+jmNm;
                int jmcd_int=0; //String으로 가져온 jmcd를 int형으로 변환
                int jmcd_int_sub=0;
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
				int savecount = 0;
				while(ie1.hasNext()) {
					String sqls = "insert into examinformation values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
					pstmt = con.prepareStatement(sqls);
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
					
					jmcd_int=Integer.parseInt(jmcd);
					jmcd_int_sub=Integer.parseInt(jmcd);

					pstmt.setInt(1, jmcd_int);
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
				
				String sqlss = "insert into examinformation_sub values (?, ?, ?)";
				pstmt = con.prepareStatement(sqlss);
				
				pstmt.setInt(1, jmcd_int_sub);
				pstmt.setString(2, caution);
				pstmt.setString(3, price);
				
				pstmt.executeUpdate();
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

    }
    

}