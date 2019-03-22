

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // イベントを取り扱うために必要
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection; // データベースアクセス用
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;

// AWTコンポーネントを使用するために必要


public class AddressSearch extends JFrame implements ActionListener, ItemListener {
	  java.awt.List codeList;            // AWTのリストを入れる変数
	  TextField codeField , prefField, prefReadingField , cityField, cityReadingField, areaField, areaReadingField;// テキストフィールドを入れる変数
	  Button codeButton, prefButton, prefReadingButton, cityButton, cityReadingButton, areaButton, areaReadingButton; // ボタンを入れる変数
	  Panel rightPanel;      // パネルを入れる変数
	  String searchCommand ="番号検索" ,search2Command="県名検索", search3Command="県名読み検索",
			  search4Command = "市区町村名検索",search5Command = "市区町村名読み検索", search6Command = "町域名検索" ,search7Command = "町域名読み検索";  // ボタンのコマンド文字列
	  String code;
	    String driverClassName = "org.postgresql.Driver";
	    String url = "jdbc:postgresql://localhost/test";
	    String user = "dbpuser";
	    String password = "hogehoge";
	    Connection connection;
	    ResultSet resultSet;

	    PreparedStatement prepStmt; // SELECT name 用 (リスト表示)
	    PreparedStatement prepStmt_S; // SELECT用
	    PreparedStatement prepStmt_P;
	    PreparedStatement prepStmt_Pr;
	    PreparedStatement prepStmt_C;
	    PreparedStatement prepStmt_Cr;
	    PreparedStatement prepStmt_A;
	    PreparedStatement prepStmt_Ar;

	    String selectStr = "SELECT number FROM postalcode";
	    String strPrepSQL_S = "SELECT * FROM postalcode WHERE code = ? ";
	    String strPrepSQL_P = "SELECT * FROM postalcode WHERE prefecture = ? ";
	    String strPrepSQL_Pr = "SELECT * FROM postalcode WHERE pref_reading = ? ";
	    String strPrepSQL_C = "SELECT * FROM postalcode WHERE city = ? ";
	    String strPrepSQL_Cr = "SELECT * FROM postalcode WHERE city_reading = ? ";
	    String strPrepSQL_A = "SELECT * FROM postalcode WHERE area = ? ";
	    String strPrepSQL_Ar = "SELECT * FROM postalcode WHERE area_reading = ? ";

	  AddressSearch() {            // コンストラクタ
		  setSize(600, 200);                 // フレームのサイズ設定
		    setTitle("郵便番号検索");   // フレームのタイトル設定
		    rightPanel = new Panel();                   // 右のパネルの生成
		    rightPanel.setLayout(new GridLayout(4, 3)); // 右のパネルのレイアウト設定
		    add(rightPanel);                            // フレームに右のパネルを追加
		    rightPanel.add(new Label("郵便番号"));     // 右のパネルにcodeラベル追加
		    codeField = new TextField(10);         // codeフィールドの生成
		    rightPanel.add(codeField);             // 右のパネルにcodeフィールド追加
		    codeButton = new Button(searchCommand);   // displayボタンの生成
			  codeButton.addActionListener(this);        // フレームをリスナにする
			    rightPanel.add(codeButton);            // 右のパネルにdisplayボタン追加


		    rightPanel.add(new Label("都道府県名"));     // 右のパネルにcodeラベル追加
		    prefField = new TextField(10);         // codeフィールドの生成
		    rightPanel.add(prefField);             // 右のパネルにcodeフィールド追加
		    prefButton = new Button(search2Command);   // displayボタンの生成
			   prefButton.addActionListener(this);        // フレームをリスナにする
			    rightPanel.add(prefButton);            // 右のパネルにdisplayボタン追加

			rightPanel.add(new Label("都道府県名の読み"));     // 右のパネルにcodeラベル追加
		    prefReadingField = new TextField(10);         // codeフィールドの生成
			rightPanel.add(prefReadingField);             // 右のパネルにcodeフィールド追加
			prefReadingButton = new Button(search3Command);   // displayボタンの生成
		       prefReadingButton.addActionListener(this);        // フレームをリスナにする
				rightPanel.add(prefReadingButton);            // 右のパネルにdisplayボタン追加

		    rightPanel.add(new Label("市区町村名"));     // 右のパネルにcodeラベル追加
		    cityField = new TextField(10);         // codeフィールドの生成
		    rightPanel.add(cityField);             // 右のパネルにcodeフィールド追加
		    cityButton = new Button(search4Command);   // displayボタンの生成
			   cityButton.addActionListener(this);        // フレームをリスナにする
			    rightPanel.add(cityButton);            // 右のパネルにdisplayボタン追加

		    rightPanel.add(new Label("市区町村名の読み"));     // 右のパネルにcodeラベル追加
			cityReadingField = new TextField(10);         // codeフィールドの生成
			rightPanel.add(cityReadingField);             // 右のパネルにcodeフィールド追加
			cityReadingButton = new Button(search5Command);   // displayボタンの生成
			   cityReadingButton.addActionListener(this);        // フレームをリスナにする
			    rightPanel.add(cityReadingButton);            // 右のパネルにdisplayボタン追加

		    rightPanel.add(new Label("町域名"));     // 右のパネルにcodeラベル追加
		    areaField = new TextField(10);         // codeフィールドの生成
		    rightPanel.add(areaField);             // 右のパネルにcodeフィールド追加
		   areaButton = new Button(search6Command);   // displayボタンの生成
		   areaButton.addActionListener(this);        // フレームをリスナにする
		    rightPanel.add(areaButton);            // 右のパネルにdisplayボタン追加

		    rightPanel.add(new Label("町域名の読み"));     // 右のパネルにcodeラベル追加
		    areaReadingField = new TextField(10);         // codeフィールドの生成
		    rightPanel.add(areaReadingField);             // 右のパネルにcodeフィールド追加
		   areaReadingButton = new Button(search7Command);   // displayボタンの生成
		   areaReadingButton.addActionListener(this);        // フレームをリスナにする
		    rightPanel.add(areaReadingButton);            // 右のパネルにdisplayボタン追加

	    addWindowListener ( new WindowAdapter() {
		    public void windowClosing(WindowEvent we) {
			try { // 後処理
			    prepStmt.close();
			    prepStmt_S.close();
			    prepStmt_C.close();
			    prepStmt_Cr.close();
			    prepStmt_P.close();
			    prepStmt_Pr.close();
			    prepStmt_A.close();
			    prepStmt_Ar.close();
			    connection.close();
			} catch (Exception e) {
			    e.printStackTrace();
			}
			System.exit(0);
		    }
		} ) ; // ウィンドウを閉じる処理

	        try { // ドライバマネージャとコネクション
	            Class.forName(driverClassName);
	            connection = DriverManager.getConnection(url, user, password);

	            prepStmt = connection.prepareStatement(selectStr);
	            prepStmt_S = connection.prepareStatement(strPrepSQL_S);
	            prepStmt_P = connection.prepareStatement(strPrepSQL_P);
	            prepStmt_Pr = connection.prepareStatement(strPrepSQL_Pr);
	            prepStmt_C = connection.prepareStatement(strPrepSQL_C);
	            prepStmt_Cr = connection.prepareStatement(strPrepSQL_Cr);
	            prepStmt_A = connection.prepareStatement(strPrepSQL_A);
	            prepStmt_Ar = connection.prepareStatement(strPrepSQL_Ar);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	  }


	  public void searchCode() {
		  String code = codeField.getText(),
				  pref = "", prefReading = "", city = "", cityReading = "", area = "", areaReading = "";

	      try {
		  prepStmt_S.setString(1, code);
		  resultSet = prepStmt_S.executeQuery();
		  while (resultSet.next()) {
		      code = resultSet.getString("code");
		      pref = resultSet.getString("prefecture");
		      prefReading = resultSet.getString("pref_reading");
		      city = resultSet.getString("city");
		      cityReading = resultSet.getString("city_reading");
		      area = resultSet.getString("area");
		      areaReading = resultSet.getString("area_reading");

		      System.out.println("〒"+code + "\t"
	                  + pref + "\t" + "("+ prefReading + ")" +"\t" + city + "\t" + "("+ cityReading + ")"+"\t"+
		    		  area +"\t"+"("+ areaReading +")");

		  }
		  resultSet.close();
	      } catch (Exception e) {
		  e.printStackTrace();
	      }
	     }

	  public void searchPref() {
		  String pref = prefField.getText(),
				  code = "", prefReading = "", city = "", cityReading = "", area = "", areaReading = "";

	      try {
		  prepStmt_P.setString(1, pref);
		  resultSet = prepStmt_P.executeQuery();
		  while (resultSet.next()) {
		      code = resultSet.getString("code");
		      pref = resultSet.getString("prefecture");
		      prefReading = resultSet.getString("pref_reading");
		      cityReading = resultSet.getString("city_reading");
		      city = resultSet.getString("city");
		      area = resultSet.getString("area");
		      areaReading = resultSet.getString("area_reading");

		      System.out.println("〒"+code + "\t"
	                  + pref + "\t" + "("+ prefReading + ")" +"\t" + city + "\t" + "("+ cityReading + ")"+"\t"+
		    		  area +"\t"+"("+ areaReading +")");

		  }
		  resultSet.close();
	      } catch (Exception e) {
		  e.printStackTrace();
	      }

	  }

	  public void searchPrefReading() {
		  String prefReading = prefReadingField.getText(),
				  code = "", pref = "", city = "", cityReading = "", area = "", areaReading = "";

	      try {
		  prepStmt_Pr.setString(1, prefReading);
		  resultSet = prepStmt_Pr.executeQuery();
		  while (resultSet.next()) {
		      code = resultSet.getString("code");
		      pref = resultSet.getString("prefecture");
		      prefReading = resultSet.getString("pref_reading");
		      cityReading = resultSet.getString("city_reading");
		      city = resultSet.getString("city");
		      area = resultSet.getString("area");
		      areaReading = resultSet.getString("area_reading");

		      System.out.println("〒"+code + "\t"
	                  + pref + "\t" + "("+ prefReading + ")" +"\t" + city + "\t" + "("+ cityReading + ")"+"\t"+
		    		  area +"\t"+"("+ areaReading +")");

		  }
		  resultSet.close();
	      } catch (Exception e) {
		  e.printStackTrace();
	      }

	  }

	  public void searchCity() {
		  String city = cityField.getText(),
				  pref = "", prefReading ="", code = "", cityReading = "", area = "" , areaReading = "";

	      try {
		  prepStmt_C.setString(1, city);
		  resultSet = prepStmt_C.executeQuery();
		  while (resultSet.next()) {
		      code = resultSet.getString("code");
		      pref = resultSet.getString("prefecture");
		      prefReading = resultSet.getString("pref_reading");
		      cityReading = resultSet.getString("city_reading");
		      city = resultSet.getString("city");
		      area = resultSet.getString("area");
		      areaReading = resultSet.getString("area_reading");

		      System.out.println("〒"+code + "\t"
	                  + pref + "\t" + "("+ prefReading + ")" +"\t" + city + "\t" + "("+ cityReading + ")"+"\t"+
		    		  area +"\t"+"("+ areaReading +")");
		  }
		  resultSet.close();
	      } catch (Exception e) {
		  e.printStackTrace();
	      }


	  }

	  public void searchCityReading() {
		  String cityReading = cityReadingField.getText(),
				  pref = "", prefReading ="", code = "", city = "", area = "" , areaReading = "";

	      try {
		  prepStmt_Cr.setString(1, cityReading);
		  resultSet = prepStmt_Cr.executeQuery();
		  while (resultSet.next()) {
		      code = resultSet.getString("code");
		      pref = resultSet.getString("prefecture");
		      prefReading = resultSet.getString("pref_reading");
		      cityReading = resultSet.getString("city_reading");
		      city = resultSet.getString("city");
		      area = resultSet.getString("area");
		      areaReading = resultSet.getString("area_reading");

		      System.out.println("〒"+code + "\t"
	                  + pref + "\t" + "("+ prefReading + ")" +"\t" + city + "\t" + "("+ cityReading + ")"+"\t"+
		    		  area +"\t"+"("+ areaReading +")");
		  }
		  resultSet.close();
	      } catch (Exception e) {
		  e.printStackTrace();
	      }


	  }

	  public void searchArea() {
		  String area = areaField.getText(),
				  pref = "", prefReading ="", code = "", city = "", cityReading = "" , areaReading = "";

	      try {
		  prepStmt_A.setString(1, area);
		  resultSet = prepStmt_A.executeQuery();
		  while (resultSet.next()) {
		      code = resultSet.getString("code");
		      pref = resultSet.getString("prefecture");
		      prefReading = resultSet.getString("pref_reading");
		      cityReading = resultSet.getString("city_reading");
		      city = resultSet.getString("city");
		      area = resultSet.getString("area");
		      areaReading = resultSet.getString("area_reading");

		      System.out.println("〒"+code + "\t"
	                  + pref + "\t" + "("+ prefReading + ")" +"\t" + city + "\t" + "("+ cityReading + ")"+"\t"+
		    		  area +"\t"+"("+ areaReading +")");
		  }
		  resultSet.close();
	      } catch (Exception e) {
		  e.printStackTrace();
	      }
	  }

	  public void searchAreaReading() {
		  String areaReading = areaField.getText(),
				  pref = "", prefReading ="", code = "", city = "", cityReading = "" , area = "";

	      try {
		  prepStmt_Ar.setString(1, areaReading);
		  resultSet = prepStmt_Ar.executeQuery();
		  while (resultSet.next()) {
		      code = resultSet.getString("code");
		      pref = resultSet.getString("prefecture");
		      prefReading = resultSet.getString("pref_reading");
		      cityReading = resultSet.getString("city_reading");
		      city = resultSet.getString("city");
		      area = resultSet.getString("area");
		      areaReading = resultSet.getString("area_reading");

		      System.out.println("〒"+code + "\t"
	                  + pref + "\t" + "("+ prefReading + ")" +"\t" + city + "\t" + "("+ cityReading + ")"+"\t"+
		    		  area +"\t"+"("+ areaReading +")" );
		  }
		  resultSet.close();
	      } catch (Exception e) {
		  e.printStackTrace();
	      }
	  }

	  public void actionPerformed(ActionEvent ae) { // ボタンが押された時に行う処理
	    String command = ae.getActionCommand();   // イベントからアクションコマンドを得る
	    if (command.equals(searchCommand)) {
	       searchCode();
	    } else if (command.equals(search2Command)) {
	       searchPref();
	    } else if (command.equals(search3Command)) {
	       searchPrefReading();
	    } else if (command.equals(search4Command)) {
		   searchCity();
	    } else if (command.equals(search5Command)) {
	       searchCityReading();
	    } else if (command.equals(search6Command)) {
		   searchArea();
	    } else if (command.equals(search7Command)) {
		   searchAreaReading();
	    }
	  }
	  public static void main(String[] argv) {
	    Kadai myFrame = new Kadai(); // フレームの生成
	    myFrame.setVisible(true); // フレームの可視化
	  }


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
	}