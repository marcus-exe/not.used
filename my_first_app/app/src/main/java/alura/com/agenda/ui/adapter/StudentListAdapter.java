package alura.com.agenda.ui.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alura.com.agenda.R;
import alura.com.agenda.model.Student;

public class StudentListAdapter extends BaseAdapter {
    private final List<Student> students = new ArrayList<>();
    private final Context context;

    public StudentListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Student getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View createdView = getCreatedView(viewGroup);
        Student studentReturned = students.get(position);
        attachView(createdView, studentReturned);
        return createdView;
    }

    private void attachView(View view, Student student) {
        TextView name = view.findViewById(R.id.item_student_name);
        name.setText(student.getName());
        TextView phone = view.findViewById(R.id.item_student_number);
        phone.setText(student.getPhone());
    }

    private View getCreatedView(ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_student, viewGroup, false);
    }


    public void update(List<Student> students){
        this.students.clear();
        this.students.addAll(students);
        notifyDataSetChanged();
    }

    public void remove(Student student) {
        students.remove(student);
        notifyDataSetChanged();
    }
}
