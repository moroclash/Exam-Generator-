
package hci_project;

public class ExamInfo {
    private String Date,Time,ExamPlace,Examname,Exammodel,Examlvl;

    public void setExamlvl(String Examlvl) {
        this.Examlvl = Examlvl;
    }

    public String getExamlvl() {
        return Examlvl;
    }

     
    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setExamPlace(String ExamPlace) {
        this.ExamPlace = ExamPlace;
    }

    public void setExammodel(String Exammodel) {
        this.Exammodel = Exammodel;
    }

    public void setExamname(String Examname) {
        this.Examname = Examname;
    }

   

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getDate() {
        return Date;
    }

    public String getExamPlace() {
        return ExamPlace;
    }

    public String getExammodel() {
        return Exammodel;
    }

    public String getExamname() {
        return Examname;
    }

    public String getTime() {
        return Time;
    }
}
