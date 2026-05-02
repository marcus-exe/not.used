package alura.com.agenda.ui.activity;

import static alura.com.agenda.ui.activity.ConstantActivities.KEY_STUDENT;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import alura.com.agenda.DAO.StudentDAO;
import alura.com.agenda.R;
import alura.com.agenda.model.Student;
import alura.com.agenda.ui.StudentListView;
import alura.com.agenda.ui.adapter.StudentListAdapter;

public class StudentListActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Student List";

    private final StudentDAO dao = new StudentDAO();
    private StudentListAdapter adapter;
    private final StudentListView studentListView = new StudentListView(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        setTitle(TITLE_APPBAR);
        configureNewStudentFAB();
        configureList();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.add("Remove");
        getMenuInflater()
                .inflate(R.menu.activity_list_students_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_list_student_menu_remove) {
            studentListView.confirmRemove(item);
        }
        return super.onContextItemSelected(item);
    }



    //Here are the extracted stuff for onCreate
    private void configureNewStudentFAB() {
        FloatingActionButton buttonNewStudent = findViewById(R.id.activity_student_list_fab_new_student);
        buttonNewStudent.setOnClickListener(view -> openFormModeInsertStudent());
    }

    private void openFormModeInsertStudent() {
        startActivity(new Intent(this,
                StudentFormActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        studentListView.refreshStudents();
    }



    //Here are the extracted stuff for onResume
    private void configureList() {
        ListView studentList = findViewById(R.id.activity_student_list_lview);
        studentListView.configureAdapter(studentList);
        configureItemClickListener(studentList);
        // configureItemLongClickListener(studentList);
        registerForContextMenu(studentList);
    }

    private void configureItemLongClickListener(ListView studentList) {
        studentList.setOnItemLongClickListener((adapterView, view, position, id) -> {
            Student chosenStudent = (Student) adapterView.getItemAtPosition(position);
            studentListView.removeStudent(chosenStudent);
            return false;
        });
    }



    private void configureItemClickListener(ListView studentList) {
        studentList.setOnItemClickListener((adapterView, view, position, id) -> {
            Student chosenStudent = (Student) adapterView.getItemAtPosition(position);
            openFormModeEditStudent(chosenStudent);
        });
    }

    private void openFormModeEditStudent(Student student) {
        Intent go2ActivtyForm = new Intent(StudentListActivity.this, StudentFormActivity.class);
        go2ActivtyForm.putExtra(KEY_STUDENT, student);
        startActivity(go2ActivtyForm);
    }


}