package owl.home.simpletelegrambot.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class Postman {
    @Getter
    private List<Receiver> receiverList;
    @Getter
    private String text;
    @Getter @Setter
    private boolean group = false;
    @Getter @Setter
    private boolean haveActive = false;

    public Postman setReceiver(List<Receiver> receivers) {
        this.receiverList = receivers;

        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }
}
