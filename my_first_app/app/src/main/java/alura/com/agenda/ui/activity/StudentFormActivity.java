package alura.com.agenda.ui.activity;

import static alura.com.agenda.ui.activity.ConstantActivities.KEY_STUDENT;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import alura.com.agenda.DAO.StudentDAO;
import alura.com.agenda.R;
import alura.com.agenda.model.Student;

public class StudentFormActivity extends AppCompatActivity {

    private static final String TITLE_APPBAR_NEW_STUDENT = "New Student";
    private static final String TITLE_APPBAR_EDIT_STUDENT = "Edit Student";
    private EditText inputName;
    private EditText inputPhone;
    private EditText inputEmail;
    private final StudentDAO dao = new StudentDAO();
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        initializingVariables();
        loadStudent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_form_list_student_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_student_form_menu_save){
            finishForm();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadStudent() {
        Intent data = getIntent();
        if (data.hasExtra(KEY_STUDENT)) {
            setTitle(TITLE_APPBAR_EDIT_STUDENT);
            student = (Student) data.getSerializableExtra(KEY_STUDENT);
            fillFields();
        } else {
            setTitle(TITLE_APPBAR_NEW_STUDENT);
            student = new Student();
        }
    }

    private void fillFields() {
        inputName.setText(student.getName());
        inputPhone.setText(student.getPhone());
        inputEmail.setText(student.getEmail());
    }

    // All the extracted Functions bellow


    private void finishForm() {
        fillStudentsFields();
        if (student.hasValidId()) {
            dao.edit(student);
        } else{
            dao.save(student);
        }
        finish();
    }

    private void initializingVariables() {
        inputName = findViewById(R.id.activity_student_form_name);
        inputPhone = findViewById(R.id.activity_student_form_number);
        inputEmail = findViewById(R.id.activity_student_form_email);
    }


    private void fillStudentsFields() {
        String name = inputName.getText().toString();
        String phone = inputPhone.getText().toString();
        String email = inputEmail.getText().toString();

        student.setName(name);
        student.setPhone(phone);
        student.setEmail(email);

    }
}