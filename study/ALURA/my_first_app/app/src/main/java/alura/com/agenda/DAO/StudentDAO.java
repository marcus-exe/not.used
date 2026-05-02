package alura.com.agenda.DAO;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import alura.com.agenda.model.Student;

public class StudentDAO {

    private final static List<Student> students = new ArrayList<>();

    private static int idCounter = 1;


    public void save(Student student) {
        student.setId(idCounter);
        students.add(student);
        refreshIds();
    }

    private void refreshIds() {
        idCounter++;
    }

    public void edit(Student student) {
        Student foundStudent = searchStudentById(student);
        if (foundStudent != null) {
            int studentPosition = students.indexOf(foundStudent);
            students.set(studentPosition, student);
        }
    }

    @Nullable
    private Student searchStudentById(Student student) {
        for (Student s : students) {
            if (s.getId() == student.getId()) {
                return s;
            }
        }
        return null;
    }

    public List<Student> all() {
        return new ArrayList<>(students);
    }

    public void remove(Student student) {
        Student studentFound = searchStudentById(student);
        if(student != null){
            students.remove(studentFound);
        }

    }
}
