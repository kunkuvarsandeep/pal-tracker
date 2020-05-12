package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    Map<Long, TimeEntry> timeEntryList = new HashMap<>();
    private Long counter = 1L;

    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(counter++);
        timeEntryList.put(timeEntry.getId(),timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long timeEntryId) {

        return timeEntryList.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {

        return new ArrayList<>(timeEntryList.values());
    }

    @Override
    public TimeEntry update(long eq, TimeEntry any) {
        if(timeEntryList.containsKey(eq)){
            any.setId(eq);
            timeEntryList.put(eq, any);
        }
        return timeEntryList.get(eq);
    }

    @Override
    public void delete(long timeEntryId) {
        timeEntryList.remove(timeEntryId);

    }
}
