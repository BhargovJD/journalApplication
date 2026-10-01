package com.companyname.journalApp.controller;

import com.companyname.journalApp.entity.JournalEntry;
import com.companyname.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    // @Autowired tells Spring Boot to automatically provide (inject) an object of JournalEntryService.
    @Autowired
    // Declares a private variable named journalEntryService to use the injected JournalEntryService object.
    private JournalEntryService journalEntryService;


//    READ
//    http://localhost:8080/journal/abc
@GetMapping("/abc")
public List<JournalEntry> getAll() {
    return journalEntryService.getAll();
}


    //    CREATE
//    http://localhost:8080/journal
//    {
//        "content": " 20 pc",
//            "id": 1,
//            "title": "orange"
//    }
//{
//    "content": "20 pc",
//        "title": "banana"
//}
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
    try{
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    }
    catch (Exception e){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    }


//    FIND BY ID
    @GetMapping("id/{myId}")
//    http://localhost:8080/journal/id/6abdbee1e0ade572331530ce
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId myId) {

        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


//    DELETE
@DeleteMapping("id/{myId}")
public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId) {
//    "The response body can be of any type."

    boolean deleted = journalEntryService.deleteById(myId);

    if (deleted) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        204

    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    404
}


//    UPDATE
@PutMapping("id/{myId}")
public ResponseEntity<?> updateJournalById(
        @PathVariable ObjectId myId,
        @RequestBody JournalEntry newEntry) {

    JournalEntry old = journalEntryService.findById(myId).orElse(null);

    if (old != null) {

        old.setTitle(
                newEntry.getTitle() != null && !newEntry.getTitle().equals("")
                        ? newEntry.getTitle()
                        : old.getTitle()
        );

        old.setContent(
                newEntry.getContent() != null && !newEntry.getContent().equals("")
                        ? newEntry.getContent()
                        : old.getContent()
        );

        journalEntryService.saveEntry(old);

        return new ResponseEntity<>(old, HttpStatus.OK);
//        200
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    404
}
}
