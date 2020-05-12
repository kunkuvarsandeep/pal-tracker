package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary summary;
    private final Counter counter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;

        summary = meterRegistry.summary("timeEntry.summary");
        counter = meterRegistry.counter("timeEntry.counter");

    }

    @RequestMapping(value = "/time-entries", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = timeEntryRepository.create(timeEntryToCreate);
        counter.increment();
        summary.record(timeEntryRepository.list().size());
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/time-entries/{timeEntryId}", method = RequestMethod.GET)
    public ResponseEntity read(@PathVariable Long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        if(timeEntry!=null){
            counter.increment();
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        }
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/time-entries", method = RequestMethod.GET)
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment();
        List<TimeEntry> timeEntryLst = timeEntryRepository.list();
        return new ResponseEntity<List<TimeEntry>>(timeEntryLst, HttpStatus.OK);
    }

    @RequestMapping(value = "/time-entries/{timeEntryId}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long timeEntryId, @RequestBody TimeEntry expected) {

        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId, expected);
        if(timeEntry!=null){
            counter.increment();
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        }
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/time-entries/{timeEntryId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        counter.increment();
        summary.record(timeEntryRepository.list().size());
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
