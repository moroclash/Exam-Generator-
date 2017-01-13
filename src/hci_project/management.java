/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hci_project;

import GUI.GnerateFrame;
import com.itextpdf.text.Paragraph;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.security.auth.Subject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import de.nixosoft.jlr.*;

/**
 *
 * @author moroclash
 */
public class management {
    
    //Delete Subject
public static int Delete(String SID,String pathname) throws ParserConfigurationException{
    
        
       try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();   
        Document doc = builder.parse(pathname);
        NodeList list=   doc.getElementsByTagName("Subject");
        NodeList list1=  doc.getElementsByTagName("Exam");
        Element exam=(Element) list1.item(0);
        for(int i=0;i<list.getLength();i++)
        {
            Element e = (Element)list.item(i);
            if(e.getAttribute("ID").equals(SID))
            {
                exam.removeChild(e);
            }
     }
        Document doc2 = builder.parse("data/paths.xml");
        NodeList lis = doc2.getElementsByTagName("subject");
        for(int i=0;i<lis.getLength();i++)
        {
            Element e = (Element) lis.item(i);
            if(e.getAttribute("ID").equalsIgnoreCase(SID))
            {
                NodeList lista = e.getElementsByTagName("Exam");
                for(int j=0;j<lista.getLength();j++)
                {
                    Element ee = (Element) lista.item(j);
                    File file = new File(ee.getAttribute("Correct"));
                    file.delete();
                    file = new File("data/"+ee.getAttribute("Examname")+"_StudentAnswer.xml");
                    file.delete();
                }
                e.getParentNode().removeChild(e);
            }
        }
    
       TransformerFactory transform = TransformerFactory.newInstance();
        Transformer trans = transform.newTransformer();
        
        DOMSource dom = new DOMSource(doc);
        
        StreamResult streem = new StreamResult(pathname);
        
        trans.transform(new DOMSource(doc2),new StreamResult("data/paths.xml"));
        trans.transform(dom, streem);
        } catch (Exception ex) {
           System.err.println(ex.getMessage());
        return 0;    }

       return 1;
}


//Delete Question
public static int Delete(String SID,String QID,String pathname) throws ParserConfigurationException{

        
       try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();   
        Document doc = builder.parse(pathname);
        NodeList list=   doc.getElementsByTagName("Subject");
        
        for(int i=0;i<list.getLength();i++)
        {
         Element e = (Element)list.item(i);
         
         if(e.getAttribute("ID").equalsIgnoreCase(SID))
         {
             NodeList list1=e.getElementsByTagName("Question");
            for(int j=0;j<list1.getLength();j++)
            {
                Element Q=(Element)list1.item(j);
                
                if(Q.getAttribute("id").equalsIgnoreCase(QID))
                {
                    int i12=Integer.parseInt(e.getAttribute("num"));
                    i12--;
                    e.getAttributeNode("num").setNodeValue(String.valueOf(i12));
                    e.removeChild(Q);
                    for(int k=0;k<list1.getLength();k++)
                    {
                        Element ee = (Element) list1.item(k);
                        if(ee.getAttribute("id").equals(Integer.toString(i12+1)))
                        {
                            ee.removeAttribute("id");
                            ee.setAttribute("id", QID);
                        }
                    }
                    
                    break;
                }
            }
           }
        }
        
        TransformerFactory transform = TransformerFactory.newInstance();
        Transformer trans = transform.newTransformer();
        DOMSource dom = new DOMSource(doc);
        StreamResult streem = new StreamResult(pathname);
        trans.transform(dom, streem);
        } catch (Exception ex) {
           System.err.println("Error *~*");
        return 0;    }

       return 1;
}


public static ArrayList<Question> ExamModles(String subjectname , String examname , String model)
{
        ArrayList<Question> a = new ArrayList<>();
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("data/paths.xml");
            NodeList list = doc.getElementsByTagName("subject");
            int num;
            for(int i=0;i<list.getLength();i++)
            {
                Element e = (Element) list.item(i);
                if(e.getAttribute("ID").equalsIgnoreCase(subjectname))
                {
                   NodeList list2 = e.getElementsByTagName("Exam");
                   for(int k =0;k<list2.getLength();k++)
                   {
                       Element ee = (Element) list2.item(k);
                       if(ee.getAttribute("Examname").equalsIgnoreCase(examname))
                       {
                           num = Integer.parseInt(ee.getAttribute("QuestionNum"));
                           NodeList list3 = ee.getElementsByTagName("model");
                           for(int j=0;j<list3.getLength();j++)
                           {
                               Element eee = (Element) list3.item(j);
                               if(eee.getAttribute("id").equalsIgnoreCase(model))
                                {
                                    NodeList nods = eee.getElementsByTagName("Question");
                                    for(int w=0;w<nods.getLength();w++)
                                    {
                                        Element r = (Element) nods.item(w);
                                        Question q = new Question();
                                            q.setQbody(r.getElementsByTagName("Qbody").item(0).getTextContent());
                                            NodeList Ans = r.getElementsByTagName("Answer");
                                            String mcq[] = new String[5];
                                            String ans[] = new String[3];
                                            int y =0;
                                            for(int m=0;m<Ans.getLength();m++)
                                            {
                                                Element eeee = (Element) Ans.item(m);
                                                mcq[m]= eeee.getTextContent();
                                                if(eeee.getAttribute("check").equalsIgnoreCase("1"))
                                                {
                                                    ans[y] = eeee.getTextContent();
                                                    y++;
                                                }
                                            }
                                            q.setAnswers(ans);
                                            q.setMSQ(mcq);
                                            q.setQlevel(Integer.parseInt(r.getAttribute("lvl")));
                                            q.setSubjctID(subjectname);
                                            q.setId(Integer.parseInt(r.getAttribute("id")));
                                            a.add(q);
                                    }
                           }}
                       }
                   }
                }
            } 
        }
        catch(Exception ex)
        {
            System.out.println("error en function QuestionModel in management *~*");
        }
        return a;
    }






   //Searcg for Question
  public static Question search(String SID,String QID,String pathname) throws ParserConfigurationException 
  {
      String []arr=new String[5];int r=0,wq=0;String []arr1=new String[5];
      Question v=new Question();
        try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();   
        Document doc = builder.parse(pathname);
     NodeList list=   doc.getElementsByTagName("Subject");
     for(int i=0;i<list.getLength();i++)
     {
         
          Element e = (Element)list.item(i);
         if(e.getAttribute("ID").equalsIgnoreCase(SID))
         {
             NodeList list1=e.getElementsByTagName("Question");
            for(int j=0;j<list1.getLength();j++)
            {
                Element Q=(Element)list1.item(j);
                if(Q.getAttribute("id").equalsIgnoreCase(QID))
                {
               v.setSubjctID(e.getAttribute("ID"));
               v.setId(Integer.parseInt(Q.getAttribute("id")));
               v.setQlevel(Integer.parseInt(Q.getAttribute("lvl")));
               v.setQbody(Q.getElementsByTagName("Qbody").item(0).getTextContent());
             NodeList listanswer= Q.getElementsByTagName("Answer");
             for(int an=0;an<listanswer.getLength();an++)
             {
                 Element answe=(Element)listanswer.item(an);
              if(answe.getAttribute("check").equals("1"))
              {
          
               arr[r]=answe.getTextContent();
                  r++;
               
              }
              
               arr1[wq]= answe.getTextContent();
                wq++;
             }
             v.setAnswers(arr);
              v.setMSQ(arr1);
           }
       }
         }
     }
        TransformerFactory transform = TransformerFactory.newInstance();
        Transformer trans = transform.newTransformer();
        DOMSource dom = new DOMSource(doc);
        StreamResult streem = new StreamResult(pathname);        
        trans.transform(dom, streem);
        
        } catch (Exception ex) {
           System.err.println(ex.getMessage());
        return null;    }

       return v;
    
}
            //search FOr Subjesct
  public static Question[] search(String SID,String pathname) throws ParserConfigurationException 
  {
      String []arr=new String[5];int r=0,wq=0;String []arr1=new String[5];
      Question v=new Question();
      Question varr[]=new Question[15];
      try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();   
      Document doc = builder.parse(pathname);
      NodeList list=   doc.getElementsByTagName("Subject");
     for(int i=0;i<list.getLength();i++)
     {
         
        Element e = (Element)list.item(i);
         if(e.getAttribute("ID").equalsIgnoreCase(SID))
         {
             NodeList list1=e.getElementsByTagName("Question");
       for(int j=0;j<list1.getLength();j++)
       {
           Element Q=(Element)list1.item(j);
         
           
                 v.setSubjctID(e.getAttribute("ID"));
              v.setId(Integer.parseInt(Q.getAttribute("id")));
              v.setQlevel(Integer.parseInt(Q.getAttribute("lvl")));
              v.setQbody(Q.getElementsByTagName("Qbody").item(0).getTextContent());
             NodeList listanswer= Q.getElementsByTagName("Answer");
             for(int an=0;an<listanswer.getLength();an++)
             {
                 Element answe=(Element)listanswer.item(an);
              if(answe.getAttribute("check").equals("1"))
              {
          
                 
               arr[r]=answe.getTextContent();
                  r++;
               
              }
              
               arr1[wq]= answe.getTextContent();
                wq++;
             }
             v.setAnswers(arr);
              v.setMSQ(arr1);
           varr[j]=v;
           v=new Question();
           r=0;
           wq=0;
           arr = new String[5];
           arr1=new  String[5];
         
       }
         }
     }
         TransformerFactory transform = TransformerFactory.newInstance();
        Transformer trans = transform.newTransformer();
        
        DOMSource dom = new DOMSource(doc);
        
        StreamResult streem = new StreamResult(pathname);
        
        
        trans.transform(dom, streem);
        } catch (Exception ex) {
           System.err.println(ex.getMessage());
        return null;    }

       return varr;
    
}
  
  
 /*return question numper in spacifec subject*/  
 public static int QuestionsNumber(String subj,String pathname) throws SAXException, IOException
{
    
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();   
        Document doc = builder.parse(pathname);
     NodeList list=   doc.getElementsByTagName("Subject");
     for(int i=0;i<list.getLength();i++)
     {
         
   Element e = (Element)list.item(i);
    if(e.getAttribute("ID").equalsIgnoreCase(subj))
         {
             int i12=Integer.parseInt(e.getAttribute("num"));
         return i12;
    
}
     }
         TransformerFactory transform = TransformerFactory.newInstance();
        Transformer trans = transform.newTransformer();
        
        DOMSource dom = new DOMSource(doc);
        
        StreamResult streem = new StreamResult(pathname);
        
        
        trans.transform(dom, streem);
        } catch (Exception ex) {
           System.err.println(ex.getMessage());
        return 0;    }
    return 0;
} 


    
 
 
 
 
 public static void Make3Array(int Questionnumper,int Modelnumper,int Examtype,String Examname,String SubjName,String filename,ExamInfo examinfo,String loca){
    
     try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();   
        Document doc = builder.parse("data//Data.xml");
        String []arr;int r=0,wq=0;String []arr1;
        ArrayList<Question> easy=new ArrayList();
        ArrayList<Question> medium=new ArrayList();
        ArrayList<Question> hard=new ArrayList();
        
        Question v;
        NodeList listS=doc.getElementsByTagName("Subject");
        Element dsl = null;
        for(int y=0;y<listS.getLength();y++)
        {
             dsl=(Element)listS.item(y); 
            if(dsl.getAttribute("ID").equals(SubjName))
            { 
            break;
            }
        }
        NodeList list=   dsl.getElementsByTagName("Question");
        for(int i=0;i<list.getLength();i++)
        {
              v=new Question();
              r=0;
              wq=0;
              arr = new String[5];
              arr1=new  String[5];
              Element Q=(Element)list.item(i);    
              v.setSubjctID(Q.getParentNode().getAttributes().getNamedItem("ID").getNodeValue());
              v.setId(Integer.parseInt(Q.getAttribute("id")));
              v.setQlevel(Integer.parseInt(Q.getAttribute("lvl")));
              v.setQbody(Q.getElementsByTagName("Qbody").item(0).getTextContent());
              NodeList listanswer= Q.getElementsByTagName("Answer");
             for(int an=0;an<listanswer.getLength();an++)
             {
              Element answe=(Element)listanswer.item(an);
              if(answe.getAttribute("check").equals("1"))
              {
               arr[r]=answe.getTextContent();
                  r++;
              }
               arr1[wq]= answe.getTextContent();
                wq++;
             }
             v.setAnswers(arr);
             v.setMSQ(arr1);
             switch (Q.getAttribute("lvl")) {
                case "1":
                    easy.add(v);
                    break;
                case "2":
                    medium.add(v);
                    break;
                default:
                    hard.add(v);
                    break;
            }
     }
    
            
        
        
        int numeasy,nummediam,numhard;
        if(Examtype == 1)
        {
            numeasy = Questionnumper / 2 ;
            nummediam = Questionnumper / 3 ;
            numhard = Questionnumper-(numeasy+nummediam);
        }
        else if(Examtype == 2)
        {
            numeasy = Questionnumper / 3 ;
            nummediam = Questionnumper / 2 ;
            numhard = Questionnumper-(numeasy+nummediam);
        }
        else
        {
            numhard = Questionnumper / 2 ;
            nummediam = Questionnumper / 3 ;
            numeasy = Questionnumper-(numhard+nummediam);
        }
         int[] En = Randome(numeasy,easy.size()); 
         int[] Mn = Randome(nummediam,medium.size()); 
         int[] Hn = Randome(numhard,hard.size()); 
         
         ArrayList<Question> Exam = new ArrayList<>();
         for(int i : En) /*easy Question*/
         {
           Exam.add((Question) easy.get(i-1));
         }
         for(int i : Mn) /*easy Question*/
         {
           Exam.add((Question) medium.get(i-1));
         }
         for(int i : Hn) /*easy Question*/
         {
           Exam.add((Question) hard.get(i-1));
         }
         
                int num =0 , modleid = 0;
                Document ansxml = builder.newDocument();
                Element Answer = ansxml.createElement("Answer");
                Answer.setAttribute("num", Integer.toString(num));
                Answer.setAttribute("NumberOfQuestions", Integer.toString(Questionnumper));
                ansxml.appendChild(Answer);
                
                int mod = 65;
                Document StudentAnsdoc = builder.newDocument();
                
                Element Examstudent = StudentAnsdoc.createElement("Examstudent");
                String StudenFile = "data/"+filename+"_StudentAnswer.xml";
                Examstudent.setAttribute("FileOfCorect", StudenFile);
                Examstudent.setAttribute("QuestionNumper", Integer.toString(Questionnumper));
                StudentAnsdoc.appendChild(Examstudent);
                
                Document path = builder.parse("data/paths.xml");
                NodeList sub = path.getElementsByTagName("subject");
                Element subj=null;
                for(int k =0;k<sub.getLength();k++)
                {
                     subj = (Element) sub.item(k);
                    if(subj.getAttribute("ID").equalsIgnoreCase(SubjName))
                    {
                        Attr attr = subj.getAttributeNode("num");
                        attr.setValue(Integer.toString(Integer.parseInt(subj.getAttribute("num"))+1));
                        break;
                    }
                }
                Element ee = path.createElement("Exam");
                ee.setAttribute("Examname", filename);
                ee.setAttribute("ExamData", examinfo.getDate());
                ee.setAttribute("ExamTime", examinfo.getTime());
                ee.setAttribute("ExamLvl", examinfo.getExamlvl());
                ee.setAttribute("ExamPlace", examinfo.getExamPlace());
                ee.setAttribute("ExamModels", examinfo.getExammodel());
                ee.setAttribute("ExamStudentGrade", "data/"+examinfo.getExammodel()+"_StudentAnswer.xml");
                ee.setAttribute("QuestionNum",Integer.toString(Questionnumper) );
                ee.setAttribute("Correct", ("data/"+filename+"Answer.xml"));
                subj.appendChild(ee);
                
                
                File tt = new File("latex");
                File templet = new File("latex/form.tex");
                File fil ;//= new File(Examname);
                JLRConverter convert = new JLRConverter(tt);
                JLRGenerator generator = new JLRGenerator();
                ArrayList<ArrayList<String>> exam;
                ArrayList<String> subexam;
                
            for(int f=0;f<Modelnumper;f++ )
              {
                int[] ExamN = Randome(Exam.size(), Exam.size());
                ArrayList<Question> newExam = new ArrayList<>();
                
                Element e = StudentAnsdoc.createElement("model");
                e.setAttribute("ID", Character.toString((char)mod));
                e.setAttribute("num", "0");
                Examstudent.appendChild(e);
                for(int b : ExamN)
                {
                    newExam.add(Exam.get(b-1));
                }
                
                Element ea = path.createElement("model");
                ea.setAttribute("id", Character.toString((char)mod));
                ee.appendChild(ea);
                
                //com.itextpdf.text.Document pdf = new com.itextpdf.text.Document();
                com.itextpdf.text.Document pdfAns = new com.itextpdf.text.Document();
                String model = Examname;
                String modelAns = Examname+"Answer.pdf";
                FileOutputStream filepdf ;
                FileOutputStream filepdfAns ;
                File file ;
                int i=1;
                String removing = filename;
                while(true)
                {
                   file = new File(modelAns);
                   if(!file.exists())
                   {
                 //      filepdf = new FileOutputStream(model);
                       filepdfAns = new FileOutputStream(modelAns);
                       break;
                   }
                   else
                   {
                       removing =filename+"("+i+")";
                       model = Examname+"("+i+")";
                       modelAns = Examname+"Answer("+i+").pdf";
                       i++;
                   }
                }
                String ou = "latex/"+removing+".tex";
                File output=new File(ou);
                //com.itextpdf.text.pdf.PdfWriter.getInstance(pdf,filepdf);
                com.itextpdf.text.pdf.PdfWriter.getInstance(pdfAns, filepdfAns);
                //pdf.open();
                pdfAns.open();
                
                //com.itextpdf.text.Paragraph prag ;
                com.itextpdf.text.Paragraph pragAns ;
                convert.replace("model", (char)mod);
                convert.replace("subject", examinfo.getExamname());
                convert.replace("date", examinfo.getDate());
                convert.replace("time", examinfo.getTime());
                convert.replace("place", examinfo.getExamPlace());
                
                
                Paragraph  prag = new Paragraph();
               /* prag.add("____________________________"+"Exam Model : ("+(char)mod+")"+"____________________________________\n"+
                         " Exam Date : "+examinfo.getDate()+"                                    "+"Exam Time : "+examinfo.getTime()+"\n"+
                         " Subject : "+examinfo.getExamname()+"                                                 "+"Place : "+examinfo.getExamPlace()+"\n"+
                         " Student Name : "+"                                             "+"Student ID :                                    "+"\n");
                         prag.add("______________________________________________________________________________\n");
                         pdf.add(prag);*/
                         
                prag = new Paragraph();
                prag.add("____________________________"+"Answer Model : ("+(char)mod+")"+"_________________________________\n"+
                         " Exam Date : "+examinfo.getDate()+"                                    "+"Exam Time : "+examinfo.getTime()+"\n"+
                         " Subject : "+examinfo.getExamname()+"                                                 "+"Place : "+examinfo.getExamPlace()+"\n");
                         prag.add("______________________________________________________________________________\n");
                         pdfAns.add(prag);
                mod++;
                int k=1;
                    num++;
                   Attr attr = Answer.getAttributeNode("num");
                   attr.setValue(Integer.toString(num));
                   Element Model = ansxml.createElement("model");
                   modleid++;
                   Model.setAttribute("id", Integer.toString(modleid));
                   Answer.appendChild(Model);
                   
                   exam = new ArrayList<>();
                
                for(Question x : newExam)
                {
                    
                  /* prag = new Paragraph();
                   prag.add(k +") "+x.getQbody()+"\n"+"a. "+x.getMSQ()[0]+"              b. "+x.getMSQ()[1]+"              c. "+x.getMSQ()[2]+"              d. "+x.getMSQ()[3]+"              e. "+x.getMSQ()[4]+"\n\n"); 
                   pdf.add(prag);*/
                   
                   Element eea = path.createElement("Question");
                   eea.setAttribute("lvl",Integer.toString(x.getQlevel()));
                   eea.setAttribute("id",Integer.toString(x.getId()));
                   ea.appendChild(eea);
                   
                   /*Start Question body Tag*/
                   Element Qbody = path.createElement("Qbody");
                   Qbody.setTextContent(x.getQbody());
                   eea.appendChild(Qbody);
                   /*End Question Tag*/
                   
                   
                    subexam= new ArrayList<>();
                    subexam.add(x.getQbody());
                   
                   /*Loop to set Answers Tag*/
                        for(int j = 0 ; j < 5 ; j++)
                        {
                            Element answer = path.createElement("Answer");
                            answer.setTextContent(x.getMSQ()[j]);
                            /*to check if Answer is Wright or not */
                            subexam.add(x.getMSQ()[j]);
                            if(x.getMSQ()[j].equals(x.getAnswers()[0]) || x.getMSQ()[j].equals(x.getAnswers()[1]) || x.getMSQ()[j].equals(x.getAnswers()[2]))
                            {
                                answer.setAttribute("check", "1");
                                
                            }
                            else
                            {
                                answer.setAttribute("check", "0"); /*fuke u*/
                            }
                            answer.setAttribute("class", Integer.toString(j+1));
                            eea.appendChild(answer);
                        }
                        /*End Answers loop*/
                   
                   pragAns = new Paragraph();
                   String a1=x.getAnswers()[0], a2=x.getAnswers()[1], a3=x.getAnswers()[2];
                   if(a2 == null)
                   {
                       a2 = " ";
                   }
                   if(a3 == null)
                   {
                       a3 = " ";
                   }
                   pragAns.add(k+")  a."+a1 +"       b." + a2+ "       c."+a3+"\n\n");
                   pdfAns.add(pragAns);
                   k++;
                   
                   Element Quest = ansxml.createElement("Question");
                   Quest.setAttribute("Qid", Integer.toString(x.getId()));
                   Quest.setAttribute("Sid", x.getSubjctID());
                   Model.appendChild(Quest);
                   
                   for(int t=0;t<3;t++)
                   {
                        Element Ans = ansxml.createElement("Ans");
                        Ans.setTextContent(x.getAnswers()[t]);
                        Quest.appendChild(Ans);
                   }
                   
                   exam.add(subexam);
                }
              //  pdf.close();
                convert.replace("question", exam);
                pdfAns.close();
                convert.parse(templet, output);
                generator.generate(output,tt, tt);
                
                fil = new File("latex/"+removing+".pdf");
                fil.renameTo(new File(loca+"/"+removing+".pdf"));
                
                fil = new File("latex/"+removing+".aux");
                fil.delete();
                fil = new File("latex/"+removing+".log");
                fil.delete();
                fil = new File("latex/"+removing+".tex");
                fil.delete();
            }
            
            fil = new File("latex/home");
            delete(fil);
            TransformerFactory transform = TransformerFactory.newInstance();
            Transformer trans = transform.newTransformer();     
            trans.transform(new DOMSource(ansxml), new StreamResult("data/"+filename+"Answer"+".xml"));
            trans.transform(new DOMSource(doc), new StreamResult("data//Data.xml"));
            trans.transform(new DOMSource(StudentAnsdoc), new StreamResult(StudenFile));
            trans.transform(new DOMSource(path), new StreamResult("data/paths.xml"));
        
        } catch (Exception ex) {
           ex.printStackTrace();
        } 
}
 
private static boolean delete(File file)
            {
                if(file.isDirectory())
                {
                    String []ch = file.list();
                    for(int i=0;i<ch.length;i++)
                    {
                        delete(new File(file, ch[i]));
                    }
                }
                return file.delete();
            }
 


 /*Randome*/
 private static int[] Randome(int num , int Qnum )
 {
        int n = num , k = Qnum;  
        int key = 1 , now = 1 ,i = 1 ;
        int a[] = new int[n];
        int m =(new Random().nextInt(k)+1);
        int x = m;
        a[0] = key;
        for( i=1;i<n;i++)
        {
            if(now+x>k)
            {
                key++;
                now=key;
                a[i] = now;
                continue;
            }
             now+=x;
            a[i] =now;
        }
        return a;
 }
 
 
 
 
public static String ExmeQuestionNumper(String subjectname,String Examname)
{

try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("data/paths.xml");
            NodeList list = doc.getElementsByTagName("subject");
            for(int i=0;i<list.getLength();i++)
            {
                Element e = (Element) list.item(i);
                if(e.getAttribute("ID").equalsIgnoreCase(subjectname))
                {
                    NodeList list2 = e.getElementsByTagName("Exam");
                    for(int k=0 ;k<list2.getLength();k++)
                    {
                        Element ee = (Element) list2.item(k);
                        if(ee.getAttribute("Examname").equalsIgnoreCase(Examname))
                        {
                           return ee.getAttribute("QuestionNum");
                        }
                    }
                }
            }
            TransformerFactory traFact = TransformerFactory.newInstance();
            Transformer trans = traFact.newTransformer();
            trans.transform(new DOMSource(doc),new StreamResult("data/paths.xml"));
        }
        catch(Exception ex)
        {
            System.out.println("error en function ExamQuestionNumber in management *~*");
        }
       return null;
}
 
 
 
 
 /*to Add question*/
    public static void add_question(Question question , String subjectID,String pathname) 
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //make factory
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
             try {
                    //if file exist
                    doc = builder.parse(pathname);
                    System.out.println("File is exist ^-^");
                 } catch (SAXException|IOException  ex) {
                    //if file not exist
                    doc = builder.newDocument();
                    System.out.println("File is Created -_-");
                    Element exam = doc.createElement("Exam");
                    doc.appendChild(exam);
                }
                //list will content all subjects
                NodeList list = doc.getElementsByTagName("Subject"); 
                //to loop on all subjects
                for(int i = 0 ; i < list.getLength() ; i++)
                {
                    Element e = (Element) list.item(i);
                    /*to check if supjectID == Subjectid that you want*/ 
                    if(e.getAttribute("ID").equalsIgnoreCase(subjectID))
                    {
                        /******test ****/
                        int num = Integer.parseInt(e.getAttribute("num"));
                        num++;
                        Attr attr = e.getAttributeNode("num");
                        attr.setValue(Integer.toString(num));
                        System.out.println(e.getAttribute("num"));
                        
                        System.out.println("Subject name :  "+ e.getAttribute("ID"));
                        String ans[] = question.getMSQ();
                        String right[] = question.getAnswers();
                        String a1 =right[0] ,a2 =right[1] ,a3 =right[2];
                        /*******test *****/
                        
                        
                        /*Start Question Tag*/
                        Element subject = doc.createElement("Question");
                        subject.setAttribute("id", Integer.toString(num));
                        subject.setAttribute("lvl",Integer.toString(question.getQlevel()));
                        /*End Question Tag*/
                        /*xxxxx ID xxxxx*/
                        
                        /*Start Question body Tag*/
                        Element Qbody = doc.createElement("Qbody");
                        Qbody.appendChild(doc.createTextNode(question.getQbody()));
                        subject.appendChild(Qbody);
                        /*End Question Tag*/
                        
                        /*Loop to set Answers Tag*/
                        for(int j = 0 ; j < 5 ; j++)
                        {
                            Element answer = doc.createElement("Answer");
                            answer.appendChild(doc.createTextNode(ans[j]));
                            /*to check if Answer is Wright or not */
                            if(ans[j].equals(a1) || ans[j].equals(a2) || ans[j].equals(a3))
                            {
                                answer.setAttribute("check", "1");
                            }
                            else
                            {
                                answer.setAttribute("check", "0");
                            }
                            answer.setAttribute("class", Integer.toString(j+1));
                            subject.appendChild(answer);
                        }
                        /*End Answers loop*/
                        
                        e.appendChild(subject);
                    }/*end if*/
                }
                /*saving file*/
                TransformerFactory transFactory = TransformerFactory.newInstance();
                Transformer trans;
                try {
                        trans = transFactory.newTransformer();
                        trans.transform(new DOMSource(doc), new StreamResult(pathname));
                        System.out.println("file is saved");
                    } catch (Exception ex1) {
                        System.err.println("error in transformer");
                    }
                /*end saving*/
                
        }
        catch(ParserConfigurationException ex)
        {
            System.err.println("error in builder");
        }
    }

 
 
    public static ArrayList<ExamInfo> SubjectExams(String subject)
    {
        ArrayList<ExamInfo> a = new ArrayList<>();
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("data/paths.xml");
            NodeList list = doc.getElementsByTagName("subject");
            for(int i=0;i<list.getLength();i++)
            {
                Element e = (Element) list.item(i);
                if(e.getAttribute("ID").equalsIgnoreCase(subject))
                {
                    NodeList list2 = e.getElementsByTagName("Exam");
                    for(int k=0;k<list2.getLength();k++)
                    {
                        Element ee=(Element) list2.item(k);
                        ExamInfo x = new ExamInfo();
                        x.setDate(ee.getAttribute("ExamData"));
                        x.setExamPlace(ee.getAttribute("ExamPlace"));
                        x.setTime(ee.getAttribute("ExamTime"));
                        x.setExammodel(ee.getAttribute("ExamModels"));
                        x.setExamname(ee.getAttribute("Examname"));
                        x.setExamlvl((ee.getAttribute("ExamLvl")));
                        a.add(x);
                    }
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println("error en function SubjectExams in management *~*");
        }
        return a;
    }
 
 
 
    public static void DeleteExam(String subject , String Examname)
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("data/paths.xml");
            NodeList list = doc.getElementsByTagName("subject");
            for(int i=0;i<list.getLength();i++)
            {
                Element e = (Element) list.item(i);
                if(e.getAttribute("ID").equalsIgnoreCase(subject))
                {
                    NodeList list2 = e.getElementsByTagName("Exam");
                    for(int k=0 ;k<list2.getLength();k++)
                    {
                        Element ee = (Element) list2.item(k);
                        if(ee.getAttribute("Examname").equalsIgnoreCase(Examname))
                        {
                            Attr attr = e.getAttributeNode("num");
                            int x = Integer.parseInt(e.getAttribute("num"))-1;
                            attr.setValue(Integer.toString(x));
                            File file = new File(ee.getAttribute("Correct"));
                            file.delete();
                            file = new File("data/"+Examname+"_StudentAnswer.xml");
                            file.delete();
                            e.removeChild(ee);
                        }
                    }
                }
            }
            TransformerFactory traFact = TransformerFactory.newInstance();
            Transformer trans = traFact.newTransformer();
            trans.transform(new DOMSource(doc),new StreamResult("data/paths.xml"));
        }
        catch(Exception ex)
        {
            System.out.println("error en function DeleteExam in management *~*");
        }
        
    }
    
    
 
  
    
    /*updat function*/
    public static int updateQuestion(Question newQ,String subjectID,int QID,String pathname)
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(pathname);
            
            NodeList list = doc.getElementsByTagName("Subject");
            for(int i = 0; i< list.getLength() ; i ++)
            {
                Element sub = (Element) list.item(i);
                if(sub.getAttribute("ID").equalsIgnoreCase(subjectID))
                {
                    NodeList Qlist = sub.getElementsByTagName("Question");
                    for(int j = 0 ; j < Qlist.getLength() ; j++)
                    {
                        Element Qe = (Element) Qlist.item(j);
                        if(Qe.getAttribute("id").equals(Integer.toString(QID)))
                        {
                          String MSQ[] = newQ.getMSQ();
                          String ans[] = newQ.getAnswers();
                          String a1 = ans[0] ,a2 = ans[1] ,a3 = ans[2] ;
                          Qe.getElementsByTagName("Qbody").item(0).setTextContent(newQ.getQbody());
                          Attr attr = Qe.getAttributeNode("lvl");
                            
                          attr.setValue(Integer.toString(newQ.getQlevel()));
                          NodeList Anslist = Qe.getElementsByTagName("Answer");
                          for(int k = 0 ;k<5 ; k++)
                          {
                              Element Answar = (Element) Anslist.item(k);
                              Answar.setTextContent(MSQ[k]);
                              attr = Answar.getAttributeNode("check");
                              if(MSQ[k].equals(a1) || MSQ[k].equals(a2) || MSQ[k].equals(a3))
                              {
                                attr.setValue("1");
                              }
                              else
                              {
                                attr.setValue("0");
                              }
                          }
                          TransformerFactory transFact =TransformerFactory.newInstance();
                          Transformer trans = transFact.newTransformer();
                          trans.transform(new DOMSource(doc),new StreamResult(pathname));
                          System.out.println("Function is Work *_*");
                          return 1;
                        }//End if check question
                    }//end for that looping on questions
                }//end if check supject 
            }
        } catch (Exception ex) { 
            System.out.println("error in function *~*");
        } 
        System.out.println("not found");
        return 0; 
    }
    
    
    /*update subject*/
    public static boolean updateSubject(String oldSubject,String newSubject,String pathname)
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(pathname);
            NodeList list = doc.getElementsByTagName("Subject");
            for(int i = 0; i< list.getLength() ; i ++)
            {
                Element e = (Element) list.item(i);
                if(e.getAttribute("ID").equalsIgnoreCase(oldSubject))
                {
                    Attr attr = e.getAttributeNode("ID");
                    attr.setValue(newSubject);
                    break;
                }
            }
            
            Document doc2 =builder.parse("data/paths.xml");
            NodeList list2 = doc2.getElementsByTagName("subject");
            for(int i = 0; i< list2.getLength() ; i ++)
            {
                Element ee = (Element) list2.item(i);
                if(ee.getAttribute("ID").equals(oldSubject))
                {
                    Attr attr = ee.getAttributeNode("ID");
                    attr.setValue(newSubject);
                    break;
                }
            }
            
            TransformerFactory TransFact = TransformerFactory.newInstance();
            Transformer trans = TransFact.newTransformer();
            trans.transform(new DOMSource(doc), new StreamResult(pathname));
            trans.transform(new DOMSource(doc2), new StreamResult("data/paths.xml"));
            return true;
        }
        catch(Exception ex)
        {
            System.out.println("Error in function updateSupject");
        }
        return false;
    }
    
    

    
    
    
    
    
    /*to Add Subject*/
    public static boolean add_subject(String SubjectName,String pathname)
    {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //make factory
         try{
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc ,paths;
                try {
                        //if file exist
                        doc = builder.parse(pathname);
                        paths = builder.parse("data/paths.xml");
                      /**/  System.out.println("the Folder is Exist ^-^");
                    } catch (SAXException|IOException  ex) {
                        doc = builder.newDocument();
                        paths = builder.newDocument();
                     /**/   System.out.println("the Folder is Created ^-^");
                        Element exam = doc.createElement("Exam");
                        doc.appendChild(exam);
                     /**/   System.out.println("Add Exam -_-");
                        Element path = paths.createElement("paths");
                        paths.appendChild(path);
                    }
                NodeList list = doc.getElementsByTagName("Subject");
                for(int i =0;i<list.getLength();i++)
                {
                    Element e = (Element) list.item(i);
                    if(e.getAttribute("ID").equals(SubjectName))
                    {
                        return false; 
                    }
                }
                Element Subject = doc.createElement("Subject");
                Subject.setAttribute("ID", SubjectName);
                Subject.setAttribute("num", "0");
                doc.getElementsByTagName("Exam").item(0).appendChild(Subject);
                Element path = paths.createElement("subject");
                path.setAttribute("num", "0");
                path.setAttribute("ID", SubjectName);
                paths.getElementsByTagName("paths").item(0).appendChild(path);
                TransformerFactory transFactory = TransformerFactory.newInstance();
                Transformer trans;
                try {
                      trans = transFactory.newTransformer();
                      trans.transform(new DOMSource(doc), new StreamResult(pathname));
                      trans.transform(new DOMSource(paths), new StreamResult("data/paths.xml"));
                    } catch (Exception ex1) {
                       System.err.println("10");
                    }
             }
        catch(ParserConfigurationException ex)
        {
            System.err.println("Error in builder *~*");
        }
         return true;
    }
 
 
 
 
 
    
    
    /*Rename dataFile*/
    public static boolean RenamSubject(String oldSubject,String newSubject,String pathname)
    {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();   
            Document doc = builder.parse(pathname);
            NodeList list=   doc.getElementsByTagName("Subject");
            for(int i=0;i<list.getLength();i++)
            {
                Element e = (Element)list.item(i);
                if(e.getAttribute("ID").equalsIgnoreCase(oldSubject))
                {
                    Attr  attr = e.getAttributeNode("ID");
                    attr.setValue(newSubject);
                    TransformerFactory transf = TransformerFactory.newInstance();
                    Transformer trans = transf.newTransformer();
                    trans.transform(new DOMSource(doc),new StreamResult(pathname));
                    return true;
                }
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error in function *~*");
        }
        return false;
    }
    
    
    /*Rename pdf*/
    public static boolean RenamePdf(String oldFile,String newFile)
    {
        File old = new File(oldFile);
        if(old.exists())
        {
            old.renameTo(new File(newFile));
            /*AbaasAnswer.pff*/
            String oldpathans = "";
            for(int i=0;i<oldFile.length();i++)
            {
                if(oldFile.charAt(i)!='.')
                    oldpathans += oldFile.charAt(i);
                else
                    break;
            }
            
            File OldAnswer = new File(oldpathans+"Answer.pdf");
            
            String newpathans = "";
            for(int i=0;i<newFile.length();i++)
            {
                if(newFile.charAt(i)!='.')
                    newpathans += newFile.charAt(i);
                else
                    break;
            }
            
            OldAnswer.renameTo(new File(newpathans+"Answer.pdf"));
            
            String Ansoldpath = old.getName();
            String Ansoldpath1 = "";
            for(int i=0;i<Ansoldpath.length();i++)
            {
                if(Ansoldpath.charAt(i)!='.')
                    Ansoldpath1 += Ansoldpath.charAt(i);
                else
                    break;
            }
            
            File OldAns = new File("data/"+Ansoldpath1+".xml");
            OldAns.renameTo(new File("data/"+newpathans+".xml"));
            
            return true;
        }
        return false;
    }
    
    
    
    
    
    public static  ArrayList<String> subjectnum(String pathname)
    {
        ArrayList<String>arr=new ArrayList<>();
        try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();   
        Document doc = builder.parse(pathname);
        int o=0;
        NodeList list=doc.getElementsByTagName("Subject");
        for(o=0;o<list.getLength();)
        {
          Element e=(Element)list.item(o);
          arr.add(e.getAttribute("ID"));
          o++;
        }
        TransformerFactory transform = TransformerFactory.newInstance();
        Transformer trans = transform.newTransformer();
        DOMSource dom = new DOMSource(doc);
        StreamResult streem = new StreamResult(pathname);
        trans.transform(dom, streem);
        } catch (Exception ex) {
           System.err.println(ex.getMessage());
          }
        return arr;
        }
 
        /*nspet el 2s2la el s7*/
        public static double precExam(String pathName,String Examname,String Subjectname)
        {
            ArrayList<Studentinfo>array=new ArrayList<>(5);
            Studentinfo stu=new Studentinfo();
            HashMap<String,Double> precenOFONEQueis=new  HashMap<>();
            array=getStudentinfo(pathName);
            Answer ans=new Answer();
            Answer ans1=new Answer();
            ArrayList <Answer> arrAn1=new ArrayList<>(5);
            ArrayList <Answer> arrAn=new ArrayList<>(5);
            arrAn1=array.get(0).getAnswer();
            int stn=0,allQue=0;
            int Qn=Integer.parseInt(ExmeQuestionNumper(Subjectname,Examname));
            for(int r=0;r<Qn;r++)
            {   
                ans1=arrAn1.get(r);
                for(Studentinfo i:array)
                {   arrAn=i.getAnswer();
                     for(int con=0;con<arrAn.size();con++)
                    {    ans=arrAn.get(con);

                    if(ans.getQuestionID().equals(ans1.getQuestionID()))
                     {
                         stn++;
                         if(ans.getCheek()==1)
                         { 
                             allQue++;
                         }
                     }

                }
            }   
        }
         double jk=(allQue/(double) stn)*100;

            return jk;
    }
 
        
     
        
        
    public static ArrayList<Studentinfo> getStudentinfo(String Filepath)
    {
     try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder = factory.newDocumentBuilder();
            ArrayList<Studentinfo> array=new ArrayList<>(10);
            ArrayList<Answer> Ans=new ArrayList<>();
            Answer answe=new Answer();
            Studentinfo as;
            Document doc = builder.parse(Filepath);
            NodeList list=  doc.getElementsByTagName("model");
             String[] truAn;
            for(int i=0;i<list.getLength();i++)
            {   
                Element e= (Element)list.item(i);
                NodeList list2=e.getElementsByTagName("student");
              for(int  j=0;j<list2.getLength();j++)
              {
                  Ans=new ArrayList<>();
                 as=new Studentinfo();
                Element S=(Element)list2.item(j);
                as.setExamModel(e.getAttribute("ID"));
                as.setCorrectAnswerNumber( Integer.parseInt(S.getAttribute("numberOFQeusionTrue")));
                as.setStudentName(S.getAttribute("studentName"));
                as.setStudentID(Integer.parseInt(S.getAttribute("studentID")));
                NodeList list3 = S.getElementsByTagName("Question");
                   for(int con=0;con<list3.getLength();con++)
                   {
                        truAn=new String[3];
                       Element Q=(Element)list3.item(con);
                       answe= new Answer();
                       answe.setCheek(Integer.parseInt(Q.getAttribute("Cheek")));
                       answe.setQuestionID(Q.getAttribute("QuestionID"));
                       NodeList list4=Q.getElementsByTagName("Answer");
                      for(int cont1=0;cont1 < list4.getLength();cont1++)                    
                      {
                          Element Answ=(Element)list4.item(cont1);
                          truAn[cont1]=Answ.getTextContent();
                          
                      }
           
                      answe.setAnswer(truAn);
                      Ans.add(answe);
                      answe= new Answer();  
                       as.setAnswer(Ans);
                   }
                  
                   
                   array.add(as);
              }
            }
               
                    TransformerFactory transform = TransformerFactory.newInstance();
                    Transformer trans = transform.newTransformer();
                    DOMSource dom = new DOMSource(doc);
                    StreamResult streem = new StreamResult("data//StudentPerformance.xml");
                    trans.transform(dom, streem);
                    return array;

        } 
       catch (Exception ex) {
       ex.printStackTrace();
       return null;
       }
    }
        
        
 
    
    
    
    
    
    public static int StudentPerformance(Studentinfo e,String FileName) 
    {   
          Element model;
    try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse("data//StudentPerformance.xml");
            NodeList list=  doc.getElementsByTagName("model");
            String ssa;
              for(int i=0;i<list.getLength();i++)
              {
                  if(e.getExamModel().equalsIgnoreCase(list.item(i).getAttributes().getNamedItem("ID").getNodeValue()))      
                 {
                       Element student=doc.createElement("student");
                       NodeList studentchec=doc.getElementsByTagName("student");
                      for(int sd=0;sd<studentchec.getLength();sd++)
                      {
                          
                          if(studentchec.item(sd).getAttributes().getNamedItem("studentID").getNodeValue().equalsIgnoreCase(Integer.toString(e.getStudentID())))
                          return 5;//fond this student
                      }           
                       student.setAttribute("numberOFQeusionTrue", Integer.toString(e.getCorrectAnswerNumber()));
                       student.setAttribute("studentID", Integer.toString(e.getStudentID()));
                       student.setAttribute("studentName",e.getStudentName());     
                       list.item(i).appendChild(student);
                       for(Answer sa:e.getAnswer())
                       {
                           Element Qeu=doc.createElement("Question");
                           Qeu.setAttribute("QuestionID", sa.getQuestionID());
                          // Qeu.setAttribute("SubjectID", sa.getSubjectID());
                           Qeu.setAttribute("Cheek", Integer.toString(sa.getCheek()));  
                           student.appendChild(Qeu);
                             for(int con=0;con<sa.getAnswer().length;con++)
                               {   
                                  Element Answer=doc.createElement("Answer");
                                  Answer.setTextContent(sa.getAnswer()[con]);
                                  Qeu.appendChild(Answer);
                               }
                       }              
                       break;
                 }
    
                }
                   TransformerFactory transform = TransformerFactory.newInstance();
                    Transformer trans = transform.newTransformer();
                    DOMSource dom = new DOMSource(doc);
                    StreamResult streem = new StreamResult("data//StudentPerformance.xml");
                    trans.transform(dom, streem);

        } 
       catch (Exception ex) {
       ex.printStackTrace();
       return 0;
    }
    return 1;
 }
    
    
    
public static double precenOFSuccess(String pathName )
        {   
            double result;
            ArrayList<Studentinfo>array=new ArrayList<>(5);
            array=getStudentinfo(pathName);
            Answer ans=new Answer();
            ArrayList <Answer> arrAn=new ArrayList<>(5);
            int stn=0,allQue=0,mo=0;
            ArrayList<Integer>jks=new ArrayList<>();  
            int k=array.size();      
                for(Studentinfo i:array)
                {   arrAn=i.getAnswer();
                    mo= arrAn.size();
                    for(int con=0;con<arrAn.size();con++)
                    {    ans=arrAn.get(con);



                             stn++;
                             if(ans.getCheek()==1)
                             { 
                                 allQue++;
                             }


                    }
                    if((mo/2)+1<=allQue)
                    {
                        jks.add(allQue);
                    }
                    allQue=0;
                    mo=0;
                }
               result=(jks.size()/(double)k)*100;
                return result;

        }
    

        
public static HashMap<String,Double> precQeusion(String pathName)
{
    String s = "";
    ArrayList<Studentinfo>array=new ArrayList<>(5);
    Studentinfo stu=new Studentinfo();
     HashMap<String,Double> precenOFONEQueis=new  HashMap<>();
    array=getStudentinfo(pathName);
    Answer ans=new Answer();
    Answer ans1=new Answer();
    ArrayList <Answer> arrAn1=new ArrayList<>(5);
    ArrayList <Answer> arrAn=new ArrayList<>(5);
    arrAn1=array.get(0).getAnswer();
    int Qn=3,stn=0,cheetru=0;
  //  int Qn=ExameQuestionNumper(String array.get(0).getStudentName(),String filename);
    for(int r=0;r<Qn;r++)
    {  
        ans1=arrAn1.get(r);
        for(Studentinfo i:array)
        {   arrAn=i.getAnswer();
            for(int con=0;con<arrAn.size();con++)
            {    ans=arrAn.get(con);
                
                if(ans.getQuestionID().equals(ans1.getQuestionID()))
                 {
                     stn++;
                     if(ans.getCheek()==1)
                     { 
                         cheetru++;
                        
                     }
                 }
                
            }
        }
                double jk=(cheetru/(double)stn)*100;
                s=ans1.getQuestionID();
                precenOFONEQueis.put(s, jk); 
                System.err.println(precenOFONEQueis.get(s));
                cheetru=0;
                stn=0;
                jk=0;
       
    }
           return precenOFONEQueis;
}



public static int Studentnumper_in_Spasfic_model(String fillpath ,String modelID)
 {         
    try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(fillpath);
            NodeList list=  doc.getElementsByTagName("model");
            NodeList list2;int is;
            for(int i=0;i<list.getLength();i++)
            {
                Element e=(Element)list.item(i);
                if(modelID.equalsIgnoreCase(e.getAttribute("ID")))
                {
                    list2=e.getElementsByTagName("student");
                    is=list2.getLength();
                    return is;
                }
                    
                
            }
                TransformerFactory transform = TransformerFactory.newInstance();
                Transformer trans = transform.newTransformer();
                DOMSource dom = new DOMSource(doc);
                StreamResult streem = new StreamResult("data//StudentPerformance.xml");
                trans.transform(dom, streem);    
    }
           catch (Exception ex) 
           {
               ex.printStackTrace();
               return 0;
           }
   return 0;    
}



}
