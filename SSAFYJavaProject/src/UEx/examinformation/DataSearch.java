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

public class DataSearch {
	public static void main(String[] args) {
		/*-- insert into short_examination values(6120,"정밀측정기능사");
		-- insert into short_examination values(0210,"화공기술사");
		-- insert into short_examination values(7893,"제빵기능사");*/
		int jmcd = 6990;
		String jmNm = "석공기능사";
		String url = "http://q-net.or.kr/crf005.do?id=crf00503s02&gSite=Q&gId=&jmCd="+jmcd+"&jmInfoDivCcd=B0&jmNm="+jmNm;
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Elements element = doc.select("div");
		System.out.println(element);
		
		System.out.println("======================================");
		System.out.println("======================================");
		System.out.println("======================================");
		System.out.println("======================================");
		
		Iterator<Element> ie1 = element.select("td").iterator();
		Iterator<Element> ie2 = element.select("li").iterator();
		Iterator<Element> ie3 = element.select("dd").iterator();
		
		while(ie2.hasNext()) {
			System.out.println("ie2 = " + ie2.next().text());
		}
		
		while(ie3.hasNext()) {
			System.out.println("ie3 = " + ie3.next().text());
		}
		
		while(ie1.hasNext()) {
			System.out.println("ie1 = " + ie1.next().text());
		}
	}
}