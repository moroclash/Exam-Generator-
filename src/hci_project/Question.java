
package hci_project;

public class Question {
    
    private String Qbody;
    private String []MSQ;
    private String[] Answers;
    private int id;
    /*
        1 : easy;
        2 : Mediam;
        3 : Hard;
    */
    private int Qlevel;
    private String subjctID;
    
    public Question()
    {
        Qbody = "";
        MSQ= new String[5];  
        Answers = new String[3];
    }

    public void setQbody(String Qbody) {
        this.Qbody = Qbody;
    }

    public String getQbody() {
        return Qbody;
    }

    public void setAnswers(String[] Answers) {
        this.Answers = Answers;
    }

    public String[] getAnswers() {
        return Answers;
    }

    public void setMSQ(String[] MSQ) {
        this.MSQ = MSQ;
    }

    public String[] getMSQ() {
        return MSQ;
    }

    public void setQlevel(int Qlevel) {
        this.Qlevel = Qlevel;
    }   

    public int getQlevel() {
        return Qlevel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSubjctID(String subjctID) {
        this.subjctID = subjctID;
    }

    public String getSubjctID() {
        return subjctID;
    }
}
