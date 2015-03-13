package com.app.ui.model;

import java.util.ArrayList;

public class Question {

	private String questionString;
	private String qId;
	private ArrayList<String> userAnswer;
	private ArrayList<String> answer;
	private ArrayList<String> option;
	private String message;
	private boolean correct;
	
	public Question(String questionString, String qId){
		this.questionString = questionString;
		this.qId = qId;
		answer = new ArrayList<String>();
		option = new ArrayList<String>();
		userAnswer = new ArrayList<String>();
		this.message="";
		this.correct=false;
	}
	
	public String getQuestionString() {
		return questionString;
	}
	public void setQuestionString(String questionString) {
		this.questionString = questionString;
	}
	public ArrayList<String> getAnswer() {
		return answer;
	}
	public void setAnswer(ArrayList<String> answer) {
		this.answer = answer;
	}
	public ArrayList<String> getOption() {
		return option;
	}
	public void setOption(ArrayList<String> option) {
		this.option = option;
	}

	public String getqId() {
		return qId;
	}

	public void setqId(String qId) {
		this.qId = qId;
	}

	public ArrayList<String> getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(ArrayList<String> userAnswer) {
		this.userAnswer = userAnswer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
}
