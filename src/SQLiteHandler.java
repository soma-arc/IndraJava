import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public abstract class SQLiteHandler {
	public static Connection sqliteConnection;
	public static Statement sqliteStatement;
	
	public static void init(){
		try{
			boolean fileExists = false;
			File dbFile = new File("fractal.db");
			if(dbFile.exists()){
				fileExists = true;
			}
			Class.forName("org.sqlite.JDBC");
			sqliteConnection = DriverManager.getConnection("jdbc:sqlite:fractal.db");
			sqliteStatement = sqliteConnection.createStatement();
			if(!fileExists){
				createTables();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static final String FRACTAL_TABLE = "fractal_id  integer primary key autoincrement, "
											  + "recipe_name text, "
											  + "file_name   text, "
											  + "path        text, "
											  + "org_date    text, "
											  + "millis      integer,"
											  + "year integer, month integer, day integer,"
											  + "hour integer, minute intger, second integer,"
											  + "param_csv text, "
											  + "max_level integer, epsilon real, expansion integer";
	private static void createTables(){
		try{
			sqliteStatement.execute("create table if not exists fractal_table("+ FRACTAL_TABLE +")");
			sqliteStatement.execute("create table if not exists conjugation_table(fractal_id integer, conjugation_order integer, word text, param text)");
			//sqliteStatement.execute("create table recipe_table(recipe_id integer primary key autoincrement, recipe_name text)");
			//sqliteStatement.execute("create table word_table(word_id integer primary key autoincrement, word_name text)");
			//insertRecipeData();
			//insertConjugationWordData();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
	
	private static void insertRecipeData(){
		try{
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('granmas_special')");
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('jorgensen')");
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('riley')");
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('granmas_good_at')");
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('maskit')");
			sqliteStatement.execute("insert into recipe_table (recipe_name) values ('triplet')");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static void insertConjugationWordData(){
		try{
			sqliteStatement.execute("insert into word_table (word_name) values ('T')");
			sqliteStatement.execute("insert into word_table (word_name) values ('R')");
			sqliteStatement.execute("insert into word_table (word_name) values ('A')");
			sqliteStatement.execute("insert into word_table (word_name) values ('I')");
			sqliteStatement.execute("insert into word_table (word_name) values ('C')");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void insertFractalData(Calendar c, String recipeName, String paramCSV, ArrayList<Conjugation> conjugationList, int maxLevel, double epsilon, int expansion)throws SQLException{
		long millis = c.getTimeInMillis();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		String orgDate = getFormattedData(c.getTime());
		String fileName = orgDate +".jpg";
		String path = recipeName +"/"+ fileName;
		String insertQuery = "insert into fractal_table (recipe_name, file_name, path, org_date, millis, year, month, day, hour, minute, second, param_csv,"
														+ "max_level, epsilon, expansion)"
											 + " values ('"+ recipeName +"', '"+ fileName +"', '"+ path +"','"+ orgDate +"', "+ millis +", "+ year +", "+ month +","+ day +","
											 			+ hour +", "+ minute +", "+ second +", '"+ paramCSV +"', "+ maxLevel +", "+ epsilon +", "+ expansion +")";
		System.out.println(insertQuery);
		sqliteStatement.execute(insertQuery);
		if(conjugationList.size() == 0) return;
		ResultSet rs = sqliteStatement.executeQuery("select fractal_id, millis from fractal_table where millis = "+ millis +"");
		rs.next();
		int id = rs.getInt("fractal_id");
		for(int order = 1 ; order <= conjugationList.size() ; order ++){
			Conjugation conjugation = conjugationList.get(order - 1);
			sqliteStatement.execute("insert into conjugation_table(fractal_id, conjugation_order, word, param)"
												     	+ " values("+ id +", "+ order +", '"+ conjugation.getWord() +"', '"+ conjugation.getParamCSV() +"')");
		}
	}
	
	public static String getFormattedData(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    	String orgDate = sdf.format(d);
    	return orgDate;
	}
	
	public static void query(String query)throws SQLException{
		sqliteStatement.executeQuery(query);
	}
}
