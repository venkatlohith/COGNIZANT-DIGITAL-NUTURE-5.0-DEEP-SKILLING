package com.cognizant.ormlearn.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qu_id")
    private int id;

    @Column(name = "qu_text")
    private String text;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private Set<Option> optionList;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Set<Option> getOptionList() { return optionList; }
    public void setOptionList(Set<Option> optionList) { this.optionList = optionList; }

    @Override
    public String toString() {
        return "Question [id=" + id + ", text=" + text + "]";
    }
}
