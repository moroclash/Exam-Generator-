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
public class Studentinfo {
   private int correctAnswerNumber,StudentID;
   private String StudentName;
   private ArrayList <Answer> Answer;
   private String ExamModel;
   private int Questionnumber;

    public void setQuestionnumber(int Questionnumber) {
        this.Questionnumber = Questionnumber;
    }

    public int getQuestionnumber() {
        return Questionnumber;
    }
   

    public String getExamModel() {
        return ExamModel;
    }

    public void setCorrectAnswerNumber(int correctAnswerNumber) {
        this.correctAnswerNumber = correctAnswerNumber;
    }

    public void setExamModel(String ExamModel) {
        this.ExamModel = ExamModel;
    }
   

    public void setAnswer(ArrayList<Answer> Answer) {
        this.Answer = Answer;
    }

    public ArrayList<Answer> getAnswer() {
        return Answer;
    }
            
   
   
   
    public int getCorrectAnswerNumber() {
        return correctAnswerNumber;
    }

    public void setStudentName(String StudentName) {
        this.StudentName = StudentName;
    }
 
    public String getStudentName() {
        return StudentName;
    }

    public void setStudentID(int StudentID) {
        this.StudentID = StudentID;
    }

    public int getStudentID() {
        return StudentID;
    }

 
    public double getPercentage(int QesNum) { 
      return (this.correctAnswerNumber/(double)QesNum)*100;        
    }

}
