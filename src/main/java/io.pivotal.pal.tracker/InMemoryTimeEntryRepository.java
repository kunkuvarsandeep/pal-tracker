package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    Map<Long, TimeEntry> timeEntryList = new HashMap<>();
    private Long counter = 1L;

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(counter++);
        timeEntryList.put(timeEntry.getId(),timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(Long timeEntryId) {

        return timeEntryList.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {

        return new ArrayList<>(timeEntryList.values());
    }

    @Override
    public TimeEntry update(Long eq, TimeEntry any) {
        if(timeEntryList.containsKey(eq)){
            any.setId(eq);
            timeEntryList.put(eq, any);
        }
        return timeEntryList.get(eq);
    }

    @Override
    public void delete(Long timeEntryId) {
        timeEntryList.remove(timeEntryId);

    }
}
