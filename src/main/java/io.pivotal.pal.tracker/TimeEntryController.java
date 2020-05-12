package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @RequestMapping(value = "/time-entries", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/time-entries/{timeEntryId}", method = RequestMethod.GET)
    public ResponseEntity read(@PathVariable Long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);

        return new ResponseEntity<TimeEntry>(timeEntry, timeEntry!=null?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/time-entries", method = RequestMethod.GET)
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> timeEntryLst = timeEntryRepository.list();
        return new ResponseEntity<List<TimeEntry>>(timeEntryLst, HttpStatus.OK);
    }

    @RequestMapping(value = "/time-entries/{timeEntryId}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId, expected);
        return new ResponseEntity<TimeEntry>(timeEntry, timeEntry!=null?HttpStatus.OK:HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/time-entries/{timeEntryId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
