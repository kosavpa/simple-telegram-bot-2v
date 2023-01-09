package owl.home.simpletelegrambot.message_manager;


import org.springframework.stereotype.Component;
import owl.home.simpletelegrambot.model.Receiver;

import javax.annotation.PostConstruct;
import java.util.*;


@Component
public class Watcher {
    private Map<Long, List<Receiver>> repository;

    @PostConstruct
    private void init(){
        repository = new HashMap<>();
    }

    public List<Receiver> getReceiversByChatId(long id){
        List<Receiver> receiverList = repository.get(id);
        if (receiverList != null) return receiverList;

        return new ArrayList<>();
    }

    public void addReceiverByChatId(long id, Receiver receiver){
        List<Receiver> receiverList = repository.get(id);

        if (receiverList == null) {
            repository.put(id, new ArrayList<>(Arrays.asList(receiver)));
        } else {
            repository.get(id).add(receiver);
        }
    }
}
