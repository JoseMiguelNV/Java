package org.example;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * The type Xml reader.
 */
public class XMLReader extends DefaultHandler {

    /**
     * The constant vtInstituteDB.
     */
    protected static VTInstituteDB vtInstituteDB = new VTInstituteDB();
    /**
     * The Id card.
     */
    protected String id_card;
    /**
     * The Firstname.
     */
    protected String firstname;
    /**
     * The Lastname.
     */
    protected String lastname;
    /**
     * The Email.
     */
    protected String email;
    /**
     * The Phone.
     */
    protected String phone;
    /**
     * The Code course.
     */
    protected int code_course;
    /**
     * The Name course.
     */
    protected String name_course;
    /**
     * The Code subject.
     */
    protected int code_subject;
    /**
     * The Name subject.
     */
    protected String name_subject;
    /**
     * The Hours subject.
     */
    protected String hours_subject;
    /**
     * The Year subject.
     */
    protected String year_subject;
    /**
     * The Course subject.
     */
    protected int course_subject;
    /**
     * The Tag content.
     */
    protected StringBuilder tagContent = new StringBuilder();
    /**
     * The constant sqlSentence.
     * Stores the selections made by the insertStudent, insertCourse and insertSubject methods.
     */
    protected static  ArrayList<String> sqlSentence = new ArrayList();

    /**
     * The startElement SAX.
     * XML tag opening method, where the value of the attributes contained within the tag is assigned to the corresponding class attributes.
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        switch (qName) {
            case "student":
                id_card = String.valueOf(attributes.getValue("id"));
                break;
            case "firstname":
                tagContent.delete(0, tagContent.length());
                break;
            case "lastname":
                tagContent.delete(0, tagContent.length());
                break;
            case "email":
                tagContent.delete(0, tagContent.length());
                break;
            case "phone":
                tagContent.delete(0, tagContent.length());
                break;
        }

        switch (qName) {
            case "course":
                code_course = Integer.parseInt(attributes.getValue("id"));
                break;
            case "name":
                tagContent.delete(0, tagContent.length());
                break;
        }

        switch (qName) {
            case "subject":
                code_subject = Integer.parseInt(attributes.getValue("id"));
                course_subject = Integer.parseInt(attributes.getValue("course"));
                break;
            case "name":
                tagContent.delete(0, tagContent.length());
                break;
            case "hours":
                tagContent.delete(0, tagContent.length());
                break;
            case "year":
                tagContent.delete(0, tagContent.length());
                break;
        }
    }

    /**
     * The characters SAX.
     * Method that obtains the xml information.
     */
    public void characters( char ch[], int start, int length ) throws SAXException {
        tagContent.append( ch, start, length );
    }

    /**
     * The endElement SAX.
     * XML tag closure method, where the value obtained in characters is assigned to the corresponding class attributes.
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch(qName) {
            case "firstname":
                firstname = tagContent.toString();
                break;
            case "lastname":
                lastname = tagContent.toString();
                break;
            case "email":
                email = tagContent.toString();
                break;
            case "phone":
                phone = tagContent.toString();
                break;
            case "student":
                insertStudent();
                break;
        }

        switch(qName) {
            case "name":
                name_course = tagContent.toString();
                break;
            case "course":
                insertCourse();
                break;
        }

        switch(qName) {
            case "name":
                name_subject = tagContent.toString();
                break;
            case "hours":
                hours_subject = tagContent.toString();
                break;
            case "year":
                year_subject = tagContent.toString();
                break;
            case "subject":
                insertSubject();
                break;
        }
    }

    /**
     * The endDocument SAX.
     * Method that makes the call to the insertXML method of the VTInstituteDB class, which is in charge of performing the transaction.
     *
     */
    public void endDocument(){
        vtInstituteDB.transactionXML();
        sqlSentence.clear();
    }


    /**
     * Insert student string.
     * This method constructs the SQLSentence for inserting students using the student attributes
     *
     * @return the string
     * Returns in a String variable the SQLSentence to insert the student obtained with the data of the inserted xml.
     */
    public String insertStudent(){
        String SQLSentence = "INSERT INTO student (id_card, firstname, lastname, email, phone)" +
                " VALUES ('" + id_card + "','" + firstname + "','" + lastname + "','" + email + "','" + phone + "')";
        sqlSentence.add(SQLSentence);
        return SQLSentence;
    }

    /**
     * Insert course string.
     * This method constructs the SQLSentence for inserting courses using the student attributes
     *
     * @return the string
     * Returns in a String variable the SQLSentence to insert the course obtained with the data of the inserted xml.
     */
    public String insertCourse(){
        String SQLSentence = "INSERT INTO course (code_course, name) VALUES (" + code_course + ", '" + name_course + "')";
        sqlSentence.add(SQLSentence);
        return SQLSentence;

    }

    /**
     * Insert subject string.
     * This method constructs the SQLSentence for inserting subjects using the student attributes
     *
     * @return the string
     * Returns in a String variable the SQLSentence to insert the subject obtained with the data of the inserted xml.
     */
    public String insertSubject(){
        String SQLSentence = "INSERT INTO subjects (code_subject, name, year, courseid, hours)" +
                " VALUES (" + code_subject + ", '" + name_subject + "','" + year_subject + "', " + course_subject + ", '" + hours_subject + "')";
        sqlSentence.add(SQLSentence);
        return SQLSentence;
    }
}
