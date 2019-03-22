package myXML;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

public class Mashup {
	public static void main(String[] args) {
		Mashup viewer = new Mashup();
		try {
			// InputStreamの用意
			URL url = new URL("http://rss.rssad.jp/rss/mainichi/flash.rss");
			URLConnection connection = url.openConnection();
			connection.connect();
			InputStream inputStream = connection.getInputStream();
			// DOMツリーの構築
			Document document = viewer.buildDocument(inputStream, "utf-8");
			// ↑↑↑  ここまでは DocumentViewer と同じ (クラス名とURL以外は)

			// XPath の表現を扱う XPath オブジェクトを生成
			XPath xPath = XPathFactory.newInstance().newXPath();

			String word = "カメラ" ;
			// 子ノードである title 要素の内容に word を含む item要素のリストを得る
			NodeList itemList = (NodeList)xPath.evaluate("/RDF/item[contains(title, '" + word + "')]",
					document, XPathConstants.NODESET);

			for(int i = 0; i < itemList.getLength(); i++) {	// 各item要素について
				Node itemNode= itemList.item(i);

				// item要素(itemNode) の子ノードである title 要素の内容を得る
				String title = ((NodeList)xPath.evaluate("title", itemNode, XPathConstants.NODESET)).item(0).getTextContent();
				// title 要素が複数あることはないので Node で受けてもよい
				//String title = ((Node)xPath.evaluate("title", itemNode, XPathConstants.NODE)).getTextContent();
				//String title = xPath.evaluate("title", itemNode);

				// item要素(itemNode) の子ノードである description 要素の内容を得る
				String description = xPath.evaluate("description", itemNode);

				// 表示
				System.out.println(title);
				System.out.println("description: " + description);
				System.out.println();
			}
			// InputStreamの用意
			URL url2 = new URL("http://trendy.nikkeibp.co.jp/rss/digital.rdf");
			URLConnection connection2 = url2.openConnection();
			connection.connect();
			InputStream inputStream2 = connection2.getInputStream();
			// DOMツリーの構築
			Document document2 = viewer.buildDocument(inputStream2, "utf-8");
			// ↑↑↑  ここまでは DocumentViewer と同じ (クラス名とURL以外は)

			// XPath の表現を扱う XPath オブジェクトを生成
			XPath xPath2 = XPathFactory.newInstance().newXPath();

			String word2 = "カメラ";
			// 子ノードである title 要素の内容に word を含む item要素のリストを得る
			NodeList itemList2 = (NodeList)xPath.evaluate("/RDF/item[contains(title, '" + word2 + "')]",
					document2, XPathConstants.NODESET);

			for(int i = 0; i < itemList2.getLength(); i++) {	// 各item要素について
				Node itemNode2= itemList2.item(i);

				// item要素(itemNode) の子ノードである title 要素の内容を得る
				String title = ((NodeList)xPath2.evaluate("title", itemNode2, XPathConstants.NODESET)).item(0).getTextContent();
				// title 要素が複数あることはないので Node で受けてもよい
				//String title = ((Node)xPath.evaluate("title", itemNode, XPathConstants.NODE)).getTextContent();
				//String title = xPath.evaluate("title", itemNode);

				// item要素(itemNode) の子ノードである description 要素の内容を得る
				String description2 = xPath2.evaluate("description", itemNode2);

				// 表示
				System.out.println(title);
				System.out.println("description2: " + description2);
				System.out.println();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** DOM Tree の構築 */
	public Document buildDocument(InputStream inputStream, String encoding) {
		Document document = null;
		try {
			// DOM実装(implementation)の用意 (Load and Save用)
			DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			DOMImplementationLS implementation = (DOMImplementationLS)registry.getDOMImplementation("XML 1.0");
			// 読み込み対象の用意
			LSInput input = implementation.createLSInput();
			input.setByteStream(inputStream);
			input.setEncoding(encoding);
			// 構文解析器(parser)の用意
			LSParser parser = implementation.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
			parser.getDomConfig().setParameter("namespaces", false);
			// DOMの構築
			document = parser.parse(input);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}
}
