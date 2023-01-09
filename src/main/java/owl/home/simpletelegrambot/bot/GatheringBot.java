package owl.home.simpletelegrambot.bot;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import owl.home.simpletelegrambot.message_manager.UpdateHandler;
import owl.home.simpletelegrambot.model.Postman;


@Component
public class GatheringBot extends TelegramLongPollingBot {
    private UpdateHandler updateHandler;
    @Value("${BOT_name}")
    private String botName;
    @Value("${BOT_token}")
    private String botToken;

    @Autowired
    public GatheringBot(UpdateHandler updateHandler) {
        super();
        this.updateHandler = this.updateHandler;
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
        Postman postman = updateHandler.handleAndGetPostman(update);
        if(postman.isGroup()){
            if(postman.isHaveActive()) {
                postman.getReceiverList()
                        .stream()
                        .forEach(r -> {
                            SendMessage sendMessage = SendMessage.builder()
                                    .chatId(r.getId())
                                    .text(postman.getText())
                                    .build();
                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        });
            }
        } else {
            if (postman.isHaveActive()){
                postman.getReceiverList()
                        .stream()
                        .forEach(r -> {
                            SendMessage sendMessage = SendMessage.builder()
                                    .chatId(r.getId())
                                    .text(postman.getText())
                                    .build();
                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        });
            }
        }
    }
}