package com.app.ui.backingbean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import com.app.ui.model.Question;

public class AnswerMCQBackingBean {

	private ArrayList<Question> quesList;
	private int quesNum;
	private String topic;
	
	@PostConstruct
	public void init(){
		topic="CU1 - Self Check Lesson 3 & 4";
		quesList = new ArrayList<Question>();
		initQuestions();
		initAnswers();
		initCorrectAnswers();
		Collections.shuffle(quesList);
		quesNum = 0;
	}
	
	public static void main(String[] args){
		AnswerMCQBackingBean bean = new AnswerMCQBackingBean();
		bean.setTopic("CU1 - Self Check Lesson 3 & 4");
		bean.init();
		for(int i = 0; i < bean.getQuesList().size(); i++){
			System.out.println(bean.getQuesList().get(i).getQuestionString());
			for(int j = 0; j < bean.getQuesList().get(i).getOption().size(); j++){
				System.out.println(bean.getQuesList().get(i).getOption().get(j));
			}
			for(int h = 0; h < bean.getQuesList().get(i).getAnswer().size(); h++){
				System.out.println(bean.getQuesList().get(i).getAnswer().get(h));
			}
		}
	}
	
	private void initQuestions(){
		try{

			Connection conn = null;
		    Statement stmt = null;
		    String query = "select * from question where topic = '" + this.topic +"'";
		    Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestApp?" + "user=root&password=password");
		    stmt = conn.createStatement();

		    ResultSet  rs = stmt.executeQuery(query);
		    while (rs.next()) {
		       String ques = rs.getString("question");
		       int qId = rs.getInt("q_id");
		       Question question = new Question(ques, qId+"");
		       quesList.add(question);
		    }

		    conn.close();

			}catch(SQLException | ClassNotFoundException ex){
				ex.printStackTrace();
			}
			
	}
	
	private void initAnswers(){
		try{

			Connection conn = null;
		    Statement stmt = null;
		    
		    Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestApp?" + "user=root&password=password");
		    stmt = conn.createStatement();

		    
		    
		    for(int i = 0; i < quesList.size(); i++){
		    	
		    	String query = "select * from `option` where q_id = '" + quesList.get(i).getqId() +"'";
		    	ResultSet  rs = stmt.executeQuery(query);
			    while (rs.next()) {
			    	quesList.get(i).getOption().add(rs.getString("option"));
			    }
		    }

		    conn.close();

			}catch(SQLException | ClassNotFoundException ex){
				ex.printStackTrace();
			}
	}
	
	private void initCorrectAnswers(){
		try{

			Connection conn = null;
		    Statement stmt = null;
		    
		    Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestApp?" + "user=root&password=password");
		    stmt = conn.createStatement();

		    
		    
		    for(int i = 0; i < quesList.size(); i++){
		    	
		    	String query = "select * from correct_option where q_id = '" + quesList.get(i).getqId() +"'";
		    	ResultSet  rs = stmt.executeQuery(query);
			    while (rs.next()) {
			    	quesList.get(i).getAnswer().add(rs.getString("correct_option"));
			    }
		    }

		    conn.close();

			}catch(SQLException | ClassNotFoundException ex){
				ex.printStackTrace();
			}
	}
	
	public void markQuestion(ActionEvent action){
		boolean isAnsCorrect = true;
		if(this.quesList.get(quesNum).getUserAnswer().size() == this.quesList.get(quesNum).getAnswer().size())
		{
			for(int i=0; i< this.quesList.get(quesNum).getUserAnswer().size(); i++)
			{
				if(!this.quesList.get(quesNum).getAnswer().contains(this.quesList.get(quesNum).getUserAnswer().get(i))){
					isAnsCorrect = false;
				}
			}
		}
		else{
			isAnsCorrect = false;
		}
		
		if(isAnsCorrect){
			this.quesList.get(quesNum).setMessage("Correct.");
			this.quesList.get(quesNum).setCorrect(true);
		}else{
			String message = "Wrong... Ans:";
			for(int i=0; i< this.quesList.get(quesNum).getAnswer().size(); i++) {
				message += " " + this.quesList.get(quesNum).getAnswer().get(i) + ";";
			}
			this.quesList.get(quesNum).setMessage(message);
		}
	}

	public void nextQues(ActionEvent action){
		if(!(this.quesNum + 1 > this.quesList.size()-1)){
			this.quesNum++;
		}
	}
	
	public void previousQues(ActionEvent action){
		if(!(this.quesNum - 1 < 0)){
			this.quesNum--;
		}
	}
	
	public ArrayList<Question> getQuesList() {
		return quesList;
	}

	public void setQuesList(ArrayList<Question> quesList) {
		this.quesList = quesList;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getQuesNum() {
		return quesNum;
	}

	public void setQuesNum(int quesNum) {
		this.quesNum = quesNum;
	}
	
	public int getTotalQuesNum(){
		return this.quesList.size();
	}
	
	public String getCurrentQuesNum(){
		return this.quesNum+1+"";
	}
	
	public String getNumOfCorrectAnswers(){
		int num = 0;
		for (int i = 0; i < this.quesList.size(); i++){
			if(this.quesList.get(i).isCorrect()) num++;
		}
		return num+"";
	}
}
