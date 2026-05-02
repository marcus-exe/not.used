package fiap.com.example.challenge.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Task {
    @Id
    private String number;
    private LocalDateTime opened;
    private LocalDateTime updated;
    private String reportState;
    private LocalDateTime reportResolved;
    private String reportCategory;
    private String reportSubcategory;
    @Column(nullable = true)
    private String symptom;
    @Column(nullable = true)
    private String symptomQualification;
    private String assignmentGroup;
    private String category;
    private String assignedTo;
    private String priority;
    private LocalDateTime closed;
    private String requester;
    private String location;
    private String description;
    private String visibleComments1;
    private String visibleComments2;
    private String visibleComments3;
    private String visibleComments4;
    private String visibleComments5;
    private String visibleComments6;
    private String visibleComments7;
    private String visibleComments8;
    private String visibleComments9;
    private String resolution;
}