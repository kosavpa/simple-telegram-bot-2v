package owl.home.simpletelegrambot.message_manager;


import org.springframework.stereotype.Component;
import owl.home.simpletelegrambot.model.People;

import javax.annotation.PostConstruct;
import java.util.*;


@Component
public class Watcher {
    private Map<Long, List<People>> repository;

    @PostConstruct
    private void init(){
        repository = new HashMap<>();
    }

    public List<People> getPeoplesByChatId(long id){
        List<People> peopleList = repository.get(id);
        if (peopleList != null) return peopleList;

        return new ArrayList<>();
    }

    public void addPeopleByChatId(long id, People people){
        List<People> peopleList = repository.get(id);

        if (peopleList == null) {
            repository.put(id, new ArrayList<>(Arrays.asList(people)));
        } else {
            repository.get(id).add(people);
        }
    }
}
