package org.example;

import java.sql.*;
import java.util.ArrayList;

/**
 * The type Vt institute db.
 */
public class VTInstituteDB {

    /**
     * The Callable statement.
     */
    protected CallableStatement callableStatement;
    /**
     * The Preparedstatement.
     */
    protected PreparedStatement preparedstatement;
    /**
     * The Resulset.
     */
    protected ResultSet resulset;
    /**
     * The Conn.
     */
    protected Connection conn;
    /**
     * The Code student.
     */
    protected String codeStudent;
    /**
     * The Code course.
     */
    protected int codeCourse;
    /**
     * The Vti form.
     */
    protected vtiForm vtiForm;


    /**
     * Set code student.
     *
     * @param codeStudent the code student
     */
    public void setCodeStudent(String codeStudent){
        this.codeStudent = codeStudent;
    }

    /**
     * Set code course.
     *
     * @param codeCourse the code course
     */
    public void setCodeCourse(int codeCourse){
        this.codeCourse = codeCourse;
    }


    /**
     * Open connection db.
     */
    public void openConnectionDB(){
        try {
            String url = "jdbc:postgresql://localhost:5432/VTInstitute";
            String user = "postgres";
            String password = "4342";
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close connection db.
     */
    public void closeConnectionDB(){
        try{
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Check student enrollment boolean.
     * Method to check if the student is already enrolled in the course.
     *
     * @return the boolean
     * Checks using a boolean whether the student exists in a course.
     */
    public boolean checkStudentEnrollment(){
        boolean ifExists = true;
        openConnectionDB();
        try{
            String ifStudentIsEnrollment = "SELECT ifstudentisenrollment('" + codeStudent + "', " + codeCourse + ")";
            preparedstatement = conn.prepareStatement(ifStudentIsEnrollment);
            resulset = preparedstatement.executeQuery();
            resulset.next();
            if (resulset.getBoolean(1)) {
                ifExists = true;
            }else{
                ifExists = false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeConnectionDB();
        return ifExists;
    }

    /**
     * Check course enrollment boolean.
     * Method to check if the student has outstanding courses.
     *
     * @return the boolean
     * Checks using a boolean if exists the code student on the course
     */
    public boolean checkCourseEnrollment() {
        boolean ifExists = false;
        openConnectionDB();
        try {
            String ifStudentIsInCourse = "SELECT ifstudentisincourse('" + codeStudent + "')";
            callableStatement = conn.prepareCall(ifStudentIsInCourse);
            resulset = callableStatement.executeQuery();
            while (resulset.next()) {
                ifExists = true;
            }
        } catch(SQLException throwables){
            throwables.printStackTrace();
        }
        closeConnectionDB();
        return ifExists;
    }

    /**
     * New enrollment.
     * Method of enrolling the student and the subjects in the course.
     */
    public void newEnrollment(){
        openConnectionDB();
        try {
            conn.setAutoCommit(false);
            try{
                String enrollmentAndInsertScores = ("CALL enrollmentinsert('" + codeStudent + "', " + codeCourse + ")");
                preparedstatement = conn.prepareStatement(enrollmentAndInsertScores);
                preparedstatement.executeUpdate();
                conn.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                conn.rollback();
            }
            closeConnectionDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add student to db.
     * Method for adding student to the database.
     *
     * @param firstName the id card
     * Receives the firstName student from the method insert student Tab 1
     * @param lastName the id card
     * Receives the lastName student from the method insert student Tab 1
     * @param idCard the id card
     * Receives the idCard student from the method insert student Tab 1
     * @param email the id card
     * Receives the email student from the method insert student Tab 1
     * @param phone the id card
     * Receives the phone student from the method insert student Tab 1
     *
     * @throws SQLException the sql exception
     */
    public void addStudentToDB(String firstName, String lastName, String idCard, String email, String phone) throws SQLException {
        openConnectionDB();
        String SQLSentence = "INSERT INTO student ( firstName, lastname, id_card, email, phone) " +
                "VALUES ('" + firstName + "','" + lastName +"','" + idCard + "','" + email + "','" + phone + "')";
        preparedstatement = conn.prepareStatement(SQLSentence);
        preparedstatement.executeUpdate();
        closeConnectionDB();
    }

    /**
     * If student exists boolean.
     * Method to check if the student to be inserted exists in the database.
     *
     * @param idCard the id card
     * Received from actionPerformed from Tab 1 Student button event.
     *
     * @return the boolean
     * Checks using a boolean if exists or not exists, the student in the database.
     */
    public boolean ifStudentExists (String idCard) {
        boolean ifExists = false;
        openConnectionDB();
        try {
            String SQLSentence = "select id_card from student where id_card = '" + idCard + "'";
            preparedstatement = conn.prepareStatement(SQLSentence);
            resulset = preparedstatement.executeQuery();
            if(!resulset.next()){
                ifExists = true;
            }else{
                ifExists = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnectionDB();
        return ifExists;
    }

    /**
     * Get name student String.
     * Method that returns the full name of the student using the student's code.
     *
     * @param codeStudent the student code
     * Receives the selected student code from the Tab 3 Reports button event method.
     *
     * @return the array list
     * Returns the full name of the student in a String variable.
     */
    public String getNameStudent(String codeStudent){
        String nameStudent = "";
        openConnectionDB();
        try {
            String SQLSentence1 = "select firstname, lastname from student where id_card = '" + codeStudent + "'";
            preparedstatement = conn.prepareStatement(SQLSentence1);
            resulset = preparedstatement.executeQuery();
            if(resulset.next()){
                nameStudent = resulset.getString(1) + " " + resulset.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnectionDB();
        return nameStudent;
    }

    /**
     * Get student list array list.
     * Method for obtaining the list of student codes.
     *
     * @return the array list
     * Returns the codes of all students in a list.
     */
    public ArrayList<String> getStudentList (){
        ArrayList<String> student = new ArrayList<>();
        openConnectionDB();
        try {
            String SQLSentence = "Select id_card From student";
            preparedstatement = conn.prepareStatement(SQLSentence);
            resulset = preparedstatement.executeQuery();
            while(resulset.next()){
                student.add(resulset.getString(1));
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
        closeConnectionDB();
        return student;
    }


    /**
     * Get course list array list.
     * Method to obtain the code-name list of courses.
     *
     * @return the array list
     * Returns the list of courses with code and name.
     */
    public ArrayList<String> getCourseList (){
        ArrayList<String> course = new ArrayList<>();
        openConnectionDB();
        try {
            String SQLSentence = "Select code_course, name From course";
            preparedstatement = conn.prepareStatement(SQLSentence);
            resulset = preparedstatement.executeQuery();
            while(resulset.next()){
                course.add(resulset.getString(1) + "  -  " + resulset.getString(2));
            }
        } catch (SQLException e) {
            e.getErrorCode();
        }
        closeConnectionDB();
        return course;
    }

    /**
     * Get check scores to student array list.
     * Method for obtaining subject scores for students courses (tab 3).
     *
     * @return the array list
     * Returns in strings the score information of the student's course subjects.
     */
    //QUESTION 2 EXAM
    public ArrayList<String> getCheckScoresToStudent(){
        ArrayList<String> scores = new ArrayList<>();
        String courseSubjectScore;
        int count = 0;
        int score = 0;
        int average = 0;
        String courseSubjectAverage = " ";
        openConnectionDB();
        try{
            String SQLSentence = "SELECT c.name, sub.name, s.score " +
            " FROM enrollment e INNER JOIN scores s ON e.code_enrollment = s.enrollmentid " +
            " INNER JOIN subjects sub ON s.subjectid = sub.code_subject " +
            " INNER JOIN course c ON sub.courseid = c.code_course " +
            " WHERE '" + codeStudent + "' = e.student ";

            preparedstatement = conn.prepareStatement(SQLSentence);
            resulset = preparedstatement.executeQuery();
            while(resulset.next()){
                courseSubjectScore = (resulset.getString(1) + " - " + resulset.getString(2) + ": " + resulset.getInt(3));
                scores.add(courseSubjectScore);
                score = resulset.getInt(3);
                score += score;
                count ++;
            }
            average = score / count;
            courseSubjectAverage = "Average Score: " + average;
            scores.add(courseSubjectAverage);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeConnectionDB();
        return scores;
    }

    /**
     * Transaction xml.
     * Method to insert the SQLSentences from the SAX.
     *
     */
    public void transactionXML() {
        String message = null;
        openConnectionDB();
        try {
            conn.setAutoCommit(false);
            for (int i = 0; i < XMLReader.sqlSentence.size(); i ++){
                preparedstatement = conn.prepareStatement(XMLReader.sqlSentence.get(i));
                preparedstatement.executeUpdate();
            }
            conn.commit();
            message = "XML inserted correctly";
            vtiForm.setMessage(message);
        } catch (SQLException e) {
            message = e.getMessage();
            vtiForm.setMessage(message);
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        closeConnectionDB();
    }
}
