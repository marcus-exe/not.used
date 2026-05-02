package alura.com.agenda;

import android.app.Application;

import alura.com.agenda.DAO.StudentDAO;
import alura.com.agenda.model.Student;

public class AgendaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createExampleStudents();
    }

    private void createExampleStudents() {
        StudentDAO dao = new StudentDAO();
        dao.save(new Student("Marcus", "12341234", "marcus@oceanbrasil.com"));
        dao.save(new Student("Kaori", "12341234", "kaori@gmail.com"));
    }
}
