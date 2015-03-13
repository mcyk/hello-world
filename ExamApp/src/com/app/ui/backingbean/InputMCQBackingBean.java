package com.app.ui.backingbean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

public class InputMCQBackingBean {
	private String topic;
	private String Question;

	private int numOptions=4;
	private int numAns=1;

	private ArrayList<String> opt;
	private ArrayList<String> ans;
	

	@PostConstruct
	public void init(){
		initOption();
		initAns();
	}
	
	private void initOption(){
		opt = new ArrayList<String>(numOptions);
		for(int i = 0; i < numOptions; i++){
			opt.add("");		
		}
	}
	
	private void initAns(){
		ans = new ArrayList<String>(numAns);
		for(int i = 0; i < numAns; i++){
			ans.add("");		
		}
	}
	
	public void reInit(ActionEvent action){
		initOption();
		initAns();
	}
	
	public void saveQuestion(ActionEvent action){
		try{

		Connection conn = null;
	    Statement stmt = null;
	    String query = "INSERT INTO question(topic,question,type)"+"VALUES('" + this.topic.trim().replace("'", "") + "','" + this.Question.trim() + "','MCQ')";
	    Class.forName("com.mysql.jdbc.Driver");
	    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestApp?" + "user=root&password=password");
	    stmt = conn.createStatement();

	    stmt.executeUpdate(query);
	    ResultSet  rs = stmt.executeQuery("select last_insert_id() as last_id from question");
	    int num =0 ;
	    if (rs.next()) {
	       num = new Integer(rs.getInt(1));
	    }
 
	    for(int i=0; i < opt.size(); i++){
	    	query = "INSERT INTO testapp.`option`(`option`,q_id)"+"VALUES('" + opt.get(i).trim().replace("'", "") + "',"+ num + ")";
	    	stmt.executeUpdate(query);
	    }

	    for(int i=0; i < ans.size(); i++){
	    	query = "INSERT INTO testapp.correct_option(correct_option,q_id)"+"VALUES('" + ans.get(i).trim().replace("'", "") + "',"+ num + ")";
	    	stmt.executeUpdate(query);
	    }
	    
	    query = "select * from lookup where tag = '" + this.topic +"'";
	    rs= stmt.executeQuery(query);
	    if(!rs.next()){
	    	query = "INSERT INTO lookup(tag,value)"+"VALUES('" + this.topic + "','"+ this.topic + "')";
	    	stmt.executeUpdate(query);
	    }

	    initOption();
		initAns();
	    Question="";
	    conn.close();

		
		}catch(SQLException | ClassNotFoundException ex){
			ex.printStackTrace();
		}
		
	}
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getQuestion() {
		return Question;
	}
	public void setQuestion(String question) {
		Question = question;
	}
	
	public int getNumAns() {
		return numAns;
	}
	public void setNumAns(int numAns) {
		this.numAns = numAns;
	}
	public ArrayList<String> getAns() {
		return ans;
	}
	public void setAns(ArrayList<String> ans) {
		this.ans = ans;
	}

	public ArrayList<String> getOpt() {
		return opt;
	}
	public void setOpt(ArrayList<String> opt) {
		this.opt = opt;
	}

	public int getNumOptions() {
		return numOptions;
	}

	public void setNumOptions(int numOptions) {
		this.numOptions = numOptions;
	}
}
