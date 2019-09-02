package UEx.examinformation;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class domtest {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		int jmcd = 7910;
		String jmNm = "한식조리기능사";
		String url = "http://q-net.or.kr/crf005.do?id=crf00503s02&gSite=Q&gId=&jmCd="+jmcd+"&jmInfoDivCcd=B0&jmNm="+jmNm;
		org.jsoup.nodes.Document doc1 = null;

		try {
			doc1 = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String elements = doc1.select("div").text();
		String temp[] = elements.split("\n");
		
		String target = "";
		for(int i=1; i<temp.length; i++) {
			target = target + temp[i] + "\n";
		}
		
		System.out.println(target);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(target)));
		
		Element root = doc.getDocumentElement();
		
		List<String> names = new ArrayList<String>();
		NodeList nodes = root.getElementsByTagName("p");
		for(int i=0; i<nodes.getLength(); i++) {
			names.add(nodes.item(i).getTextContent());
		}
		
		System.out.println(names);
	}
}