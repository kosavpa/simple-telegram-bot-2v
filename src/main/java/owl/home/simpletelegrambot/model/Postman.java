package owl.home.simpletelegrambot.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Postman {
    private List<People> peopleList;
    private String text;
    @Getter @Setter
    private boolean group = false;
    @Getter @Setter
    private boolean haveActive = false;

    public Postman setPeoples(List<People> peoples) {
        this.peopleList = peoples;

        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }
}
