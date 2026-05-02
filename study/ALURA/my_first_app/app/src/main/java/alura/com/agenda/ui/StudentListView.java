package alura.com.agenda.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import alura.com.agenda.DAO.StudentDAO;
import alura.com.agenda.model.Student;
import alura.com.agenda.ui.adapter.StudentListAdapter;

public class StudentListView {
    private final StudentListAdapter adapter;
    private final StudentDAO dao;
    private final Context context;

    public StudentListView(Context context) {
        this.context = context;
        this.adapter = new StudentListAdapter(this.context);
        dao = new StudentDAO();

    }

    public void confirmRemove(@NonNull final MenuItem item) {

        new AlertDialog
                .Builder(context)
                .setTitle("Remove Student")
                .setMessage("Are you sure you want to remove this student?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Student chosenStudent = adapter.getItem(menuInfo.position);
                    removeStudent(chosenStudent);
                })
                .setNegativeButton("No", null)
                .show();

    }
    public void refreshStudents() {
        adapter.update(dao.all());
    }

    public void removeStudent(Student student) {
        dao.remove(student);
        adapter.remove(student);
    }

    public void configureAdapter(ListView studentList) {

        studentList.setAdapter(adapter);
    }


}
