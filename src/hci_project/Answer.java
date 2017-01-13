/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hci_project;

import java.util.ArrayList;

/**
 *
 * @author moahmed A.Radwan
 */
public class Answer {
    private String[] Answer;
    private   String QuestionID;
    private int cheek;

    public int getCheek() {
        return cheek;
    }

    public void setCheek(int cheek) {
        this.cheek = cheek;
    }
 


    public String[] getAnswer() {
        return Answer;
    }

    public void setAnswer(String[] Answer) {
        this.Answer = Answer;
    }

    public String getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(String QuestionID) {
        this.QuestionID = QuestionID;
    }
  
}
