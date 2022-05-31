package org.example;

import org.xml.sax.SAXException;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.lang.Integer.parseInt;

/**
 * The type Vti form.
 */
public class vtiForm {
    private JTabbedPane Panel;
    private JPanel Students;
    private JPanel Enrollments;
    private JPanel Reports;
    private JPanel Utilities;
    private JTextField fieldFirstNameStudent;
    private JTextField fieldLastNameStudent;
    private JButton addButtonStudent;
    private JTextField fieldIdCardStudent;
    private JTextField fieldEmailStudent;
    private JTextField fieldPhoneStudent;
    private JComboBox<ArrayList<String>> cmbEnrollmentStudent;
    private JComboBox<ArrayList<String>> cmbEnrollmentCourse;
    private JButton enrollButton;
    private JButton importXMLButton;
    private JButton printReportButton;
    private JComboBox<ArrayList<String>> cmbReportStudent;
    private JTextArea textAreaReportStudent;
    private JPanel formPanel;
    private JTextField enterStudent;
    private JTextField showStudent;
    private JButton Search;

    /**
     * The constant vtInstituteDB.
     * Instance to the VTInstituteDB class.
     */
    protected static VTInstituteDB vtInstituteDB = new VTInstituteDB();

    /**
     * Set message. Displays a message of the result of the transaction performed in the insertXML method of the VTInstituteDB class.
     *
     * @param message The message is received from the transactionXML method of the VTInstitute class.
     */
    public static void setMessage(String message){
        if (message.equals("XML inserted correctly")){
            JOptionPane.showMessageDialog(null, message, "Show information", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, message, "Show information", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Refresh Show lists in combo boxes.
     * Method to update the list of students and courses after a new insertion, to show them in the enrollment combo boxes.
     */
    public void refreshShowLists() {
        cmbEnrollmentStudent.setModel(new DefaultComboBoxModel(vtInstituteDB.getStudentList().toArray()));
        cmbEnrollmentCourse.setModel(new DefaultComboBoxModel(vtInstituteDB.getCourseList().toArray()));
        cmbReportStudent.setModel(new DefaultComboBoxModel(vtInstituteDB.getStudentList().toArray()));
    }

    /**
     * Clean fields.
     * Method to clean fields on tab 1.
     */
    public void cleanFields(){
        fieldFirstNameStudent.setText(null);
        fieldLastNameStudent.setText(null);
        fieldIdCardStudent.setText(null);
        fieldEmailStudent.setText(null);
        fieldPhoneStudent.setText(null);
    }

    /**
     * Instantiates a new Vti form.
     * Contains all action listeners of the project.
     */
    public vtiForm() {

        refreshShowLists();

        /**
         * Tab 1 Student button event.
         * Event registering student in the database.
         *
         */
        //QUESTION 3 EXAM
        addButtonStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phone_regex = "^[0-9]{9}$";
                String firstName = fieldFirstNameStudent.getText();
                String lastName = fieldLastNameStudent.getText();
                String idCard = fieldIdCardStudent.getText();
                String email = fieldEmailStudent.getText();
                String phone = fieldPhoneStudent.getText();

                if (firstName.isEmpty() || lastName.isEmpty() || idCard.isEmpty() || firstName.isBlank() || lastName.isBlank() || idCard.isBlank()) {
                    JOptionPane.showMessageDialog(null, "The fields first name, surname, idCard, " +
                            "and email cannot be empty", "Error Message Box", JOptionPane.ERROR_MESSAGE);
                } else if (!email.contains("@") || !email.contains(".")) {
                    JOptionPane.showMessageDialog(null, "The email entered is not correct", "Error Message Box", JOptionPane.ERROR_MESSAGE);
                } else if (!phone.matches(phone_regex)) {
                    JOptionPane.showMessageDialog(null, "The phone number is not correct", "Error Message Box", JOptionPane.ERROR_MESSAGE);
                }else{
                    try {
                        if (!vtInstituteDB.ifStudentExists(idCard)) {
                            JOptionPane.showMessageDialog(Students, "The student Id Card exists in the data base",
                                    "Error Message Box", JOptionPane.ERROR_MESSAGE);
                        } else {
                            vtInstituteDB.addStudentToDB(firstName, lastName, idCard, email, phone);
                            JOptionPane.showMessageDialog(Students, "Student inserted correctly");
                            refreshShowLists();
                            cleanFields();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        /**
         * Tab 2 Enrollment button event.
         * Event enrolling the selected student in the selected course.
         *
         */
        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codeStudent = cmbEnrollmentStudent.getSelectedItem().toString();
                String courseAndCode = cmbEnrollmentCourse.getSelectedItem().toString();
                String[] codeCourse = courseAndCode.split("  -  ");
                vtInstituteDB.setCodeStudent(codeStudent);
                vtInstituteDB.setCodeCourse(parseInt(codeCourse[0]));
                if(vtInstituteDB.checkStudentEnrollment()) {
                    JOptionPane.showMessageDialog(null, "The student is already enrolled in this course",
                            "Error Message Box", JOptionPane.ERROR_MESSAGE);
                }
                else if(vtInstituteDB.checkCourseEnrollment()) {
                    JOptionPane.showMessageDialog(null, "The student is enrolled in a course that has not yet passed.",
                            "Error Message Box", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    vtInstituteDB.newEnrollment();
                    JOptionPane.showMessageDialog(null, "The student is enrolled correctly", "Show information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        /**
         * Tab 3 Reports button event.
         * Event that shows the grades of the subjects of the courses where the student is enrolled and saved it on the file.
         *
         */
        printReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codeStudent = cmbReportStudent.getSelectedItem().toString();
                vtInstituteDB.setCodeStudent(codeStudent);
                ArrayList<String> scores = vtInstituteDB.getCheckScoresToStudent();
                String nameStudent = vtInstituteDB.getNameStudent(codeStudent);
                JFileChooser save = new JFileChooser();
                save.showSaveDialog(null);
                save.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                File file = save.getSelectedFile();

                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
                    bw.write("Student: " + nameStudent);
                    bw.newLine();
                    for (int i = 0; i < scores.size(); i++) {
                        textAreaReportStudent.append(scores.get(i) + '\r' + '\n');
                        bw.write(scores.get(i));
                        bw.newLine();
                    }
                    bw.flush();
                    bw.close();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                textAreaReportStudent.setText(null);
                JOptionPane.showMessageDialog(null, "The Report is printed correctly", "Show information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        /**
         * Tab 3 Select Student of comboBox button event.
         * Event that selects item the student code from the combobox.
         *
         */
        cmbReportStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codeStudent = cmbReportStudent.getSelectedItem().toString();
                vtInstituteDB.setCodeStudent(codeStudent);
                ArrayList<String> scores = vtInstituteDB.getCheckScoresToStudent();
                textAreaReportStudent.setText(null);
                for (int i = 0; i < scores.size(); i++) {
                    textAreaReportStudent.append(scores.get(i) + '\r' + '\n');
                }
            }
        });

        /**
         * Tab 4 importXML button event.
         * Event that selects an xml file and inserts it into the database.
         *
         */
        importXMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "";
                JFileChooser selectFile = new JFileChooser();
                selectFile.showOpenDialog(selectFile);
                String filePath = selectFile.getSelectedFile().getAbsolutePath();
                XMLReader readerXML = new XMLReader();
                try {
                    SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
                    saxParser.parse(filePath, readerXML);
                    refreshShowLists();
                } catch (ParserConfigurationException | IOException | SAXException ex) {
                    message = ex.getMessage();
                    JOptionPane.showMessageDialog(null, message, "Show information", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //QUESTION 1 EXAM
        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codeStudent = enterStudent.getText();
                String student = vtInstituteDB.getNameStudent(codeStudent);
                if(codeStudent.isBlank() || codeStudent.isEmpty() || !vtInstituteDB.ifStudentExists(codeStudent)){
                    showStudent.setText(student);
                }else{
                    JOptionPane.showMessageDialog(Students, "The student Id Card not exists in the data base",
                            "Error Message Box", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws SQLException the sql exception
     */
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("vtiForm");
        frame.setContentPane(new vtiForm().formPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        //With setLocationRelativeTo we center the JFrame on the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
