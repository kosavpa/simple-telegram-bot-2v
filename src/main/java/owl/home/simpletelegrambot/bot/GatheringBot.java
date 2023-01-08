package owl.home.simpletelegrambot.bot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import owl.home.simpletelegrambot.model.People;
import owl.home.simpletelegrambot.util.Watcher;

import static owl.home.simpletelegrambot.util.Action.*;


@Component
public class GatheringBot extends TelegramLongPollingBot {
    private final Watcher watcher;
    @Value("${BOT.name}")
    private String botName;
    @Value("${BOT.token}")
    private String botToken;

    @Autowired
    public GatheringBot(Watcher watcher) {
        super();
        this.watcher = watcher;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                if (message.getText().equals(REGISTER.getCommand())){
                    registration(
                            message.getChatId(),
                            People.builder()
                                    .id(message.getFrom().getId())
                                    .firstName(message.getFrom().getFirstName())
                                    .lastName(message.getFrom().getLastName())
                                    .username(message.getFrom().getUserName())
                                    .build());
                } else if (message.getText().equals(ASSEMBLE.getCommand())) {
                    avengersAssemble(message.getChatId());
                }
            }
        } else if (update.hasMyChatMember()){
            if(update.getMyChatMember().getNewChatMember().getStatus().equals(NEW_MEMBER.getCommand()))
                helloMessage(update.getMyChatMember().getChat().getId());
        }
    }

    private void registration(long chatId, People people) {
        watcher.addPeopleByChatId(chatId, people);
        System.out.println("registration people - " + people.toString());
    }

    private void avengersAssemble(long id){
        watcher.getPeoplesByChatId(id)
                .forEach(people -> {
                    try {
                        execute(SendMessage.builder()
                                .chatId(people.getId())
                                .text(TEXT.getCommand())
                                .build());
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void helloMessage(long chatId){
        try {
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(BOT_URL.getCommand())
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}