package UEx.examinformation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
			
			String url = "http://www.q-net.or.kr/crf005.do?id=crf00501&gSite=Q&gId=";
			Document doc = null;
			
			doc = Jsoup.connect(url).get();
			
			Elements element = doc.select("li");
			
			Iterator<Element> ie1 = element.select("a").iterator();
			
			int count = 0;
			firstloop : while(ie1.hasNext()) {
				Element temp = ie1.next();
				if(temp.hasAttr("onclick")) {
					if(temp.attr("onclick").contains("getList")) {
						String temp2[] = temp.attr("onclick").split("'");
						//System.out.println(temp2[1] + " " + temp2[5]);
						
						temp2[1] = temp2[1].equals("1") ? temp2[1] + "&obligFldCd" : temp2[1] + "&examInstiCd" ; 

						url = "http://q-net.or.kr/crf005.do?id=crf00501s01&Site=Q&div="+temp2[1]+"="+temp2[5];
						
						doc = Jsoup.connect(url).get();
						
						Elements tempelement = doc.select("ul");
						
						Iterator<Element> tempie1 = tempelement.select("a").iterator();
						while(tempie1.hasNext()) { 
							Element temptemp = tempie1.next();
							if(temptemp.hasAttr("href")) { 
								//System.out.println(temptemp.attr("href").split("'")[1]);
								//System.out.println(temptemp.text().split(" ")[1]);
								String jmCd = temptemp.attr("href").split("'")[1];
								String jmNm = temptemp.text().split(" ")[1];
								sql="insert into short_examination values (?, ?)";
								pstmt=con.prepareStatement(sql);
								pstmt.setString(1, jmCd);
								pstmt.setString(2, jmNm);
								pstmt.executeUpdate();
							}
//							count++;
//							if(count == 100)
//								break firstloop;
						}
					}
				}
			}

			System.out.println("테이블 초기화 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}