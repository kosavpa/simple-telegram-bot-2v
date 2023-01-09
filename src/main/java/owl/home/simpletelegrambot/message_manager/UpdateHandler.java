package owl.home.simpletelegrambot.message_manager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import owl.home.simpletelegrambot.model.People;
import owl.home.simpletelegrambot.model.Postman;
import owl.home.simpletelegrambot.util.CommandType;
import owl.home.simpletelegrambot.util.MessageText;
import owl.home.simpletelegrambot.util.UpdateType;

import java.util.List;


@Component
public class UpdateHandler {
    private Watcher watcher;

    @Autowired
    public UpdateHandler(Watcher watcher) {
        this.watcher = watcher;
    }

    public Postman handleAndAnswerUpdate(Update update){
        switch (defineType(update)){
            case MESSAGE -> {
                return handleMessage(update);
            }
            case MEMBER -> {
                return handleMember(update);
            }
            default -> {
                return new Postman();
            }
        }
    }

    private Postman handleMember(Update update) {
        Postman postman = new Postman();

        if(update.hasMyChatMember() & update.getMyChatMember().getNewChatMember().getStatus().equals(CommandType.NEW_MEMBER.getCommand())) {
            postman.setHaveActive(true);
            postman.setText(MessageText.BOT_URL.getText());
            postman.setText(MessageText.INFO.getText());
            postman.setGroup(true);

            return postman;
        }

        return postman;
    }

    private Postman handleMessage(Update update) {
        Postman postman = new Postman();
        Message message = update.getMessage();

        if (message.hasText()) {
            switch (messageCommandTypeResolver(message)){
                case REGISTER -> registerNewPeople(postman, message);
                case ASSEMBLE -> generalGathering(postman, message);
                case UNKNOWN -> {}
            }
        }

        return postman;
    }

    private void generalGathering(Postman postman, Message message) {
        postman.setPeoples(watcher.getPeoplesByChatId(message.getChatId()));
        postman.setText(MessageText.ASSEMBLE.getText());
        postman.setHaveActive(true);
    }

    private CommandType messageCommandTypeResolver(Message message) {
        String splitMessage = message.getText().split("@")[0];

        if(splitMessage.equalsIgnoreCase(CommandType.REGISTER.getCommand()))
            return CommandType.REGISTER;

        if(splitMessage.equalsIgnoreCase(CommandType.ASSEMBLE.getCommand()))
            return CommandType.ASSEMBLE;

        return CommandType.UNKNOWN;
    }

    private void registerNewPeople(Postman postman, Message message) {
        People people = People.builder()
                .id(message.getFrom().getId())
                .firstName(message.getFrom().getFirstName())
                .lastName(message.getFrom().getLastName())
                .username(message.getFrom().getUserName())
                .build();

        watcher.addPeopleByChatId(message.getChatId(), people);
        postman.setPeoples(List.of(people));
        postman.setText(MessageText.REGISTERED.getText());
        postman.setHaveActive(true);
    }

    private UpdateType defineType(Update update) {
        if (update.hasMessage())
            return UpdateType.MESSAGE;

        return UpdateType.MEMBER;
    }
}
