
package hci_project;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.String;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.transform.Transform;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class HCI_Project {
    

    
    /*this is use to genrat Question ID use it to increas ID*/
    public static int imp_getID()
    {
        File file = new File("data/ID.bin");
        int num = 0;
        if(!file.exists())
        {
            try {
                file.createNewFile();
                System.out.println("file is Created -_-");
            } catch (IOException ex) {
                System.out.println("Error in File location");
            }
            num = 1;
            try {
                ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file));
                write.writeObject(num);
                write.close();
            } catch (Exception ex) {
                System.out.println("Error in file *~*");
            }
        }
        else
        {
            try {
                System.out.println("File is exist ^_^");
                ObjectInputStream read = new ObjectInputStream(new FileInputStream(file));
                num = (int) read.readObject();
                num++;
                read.close();
                ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file));
                write.writeObject(num);
                write.close();
            } catch (IOException|ClassNotFoundException ex) {
                System.out.println("Error in file *~*");
            } 
        }
        System.out.println("ID : " + num);
        return num ;
    }
    

    
    /*to Subtract 1 from ID*/
    public static void imp_decreasesID()
    {
        File file = new File("data/ID.bin");
        try {
            ObjectInputStream read = new ObjectInputStream(new FileInputStream(file));
            int num = (int) read.readObject();
            read.close();
            
            ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file));
            num--;
            write.writeObject(num);
            write.close();
            System.out.println("ID : "+num);
        } catch (IOException|ClassNotFoundException ex) {
            System.out.println("Error in file in function decreaseID");
        } 
    
    }

 
    
    
   public static void main(String[] args) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        /*imp_decreasesID();
        System.out.println(imp_getID());
        
        Question q = new Question();
        q.setQbody("rdwwwwwaaaaaaaaaaaaaaan  ^_^ ?");
        String s[] = {"rdwaan","fun","nice","fod","omar"};
        q.setMSQ(s);
        String ss[]={"omar","rdwaan",""};
        q.setAnswers(ss);
        q.setQlevel(3);
        
        ArrayList<Question> a=management.ExamModles("x++", "test", "A");
        for(Question n : a)
        {
            System.out.println(n.getQbody());
            
        }*/
        
        double a= management.precExam("data/lll_StudentAnswer.xml", "lll","xxx");
        System.out.print(a);
       // management.updateQuestion(q, "Javardwan", 3, "data/Data.xml");
        //ArrayList<ExamInfo> a = management.SubjectExams("c++");
        //System.out.println(a.get(0).getExamname());
     //>>     management.add_question(q, "Java");
      // >>   management.Delete("Java", "1");
      //>>   management.update(q, "java", 1); 
     //>>   management.add_subject("rdwaan");
     //>>       management.Delete("rdwaan");
     //>>         System.out.println(management.QuestionsNumber("java"));
     //>Question ee = management.search("Java", "1");
    /* ExamInfo a  = new ExamInfo();
     a.setDate("6/9/1234");
     a.setExamPlace("El Wraaaak");
     a.setExamname("C++");
     a.setTime("5:00 am");
        management.Make3Array(10 , 3 , 1 , "rdwaan","C++","data/Data.xml",a);
       //management.RenamePdf("omar.pdf","rdwaan.pdf");
       
       /*<Students num="3dd el tolaap" examQuestnum="3dd el 2s2laa fe el emt7aan"> 
            <Student RightnumQuestion = "3dd el 2s2la el s7" wrong="3dd el 2s2la el 8lt" Exammodel ="model el 2mt7aan" >
                     <Question  QuestionID="" Subjectid="" check="">
                             <Ans >el 2gaba rkm 1</Ans>
                     </Question>   
                     <answer>el 2gaba rkm 2</answer>   
                     <answer>el 2gaba rkm 3</answer>   
                     <answer>el 2gaba rkm 4</answer>   
                     <answer>el 2gaba rkm 5</answer> 
                           
            </Student>
       
       </Students>*/
        
       
        
        //management.add_subject("C#");
        //management.add_subject("C++");*/
        
  }
    
}
