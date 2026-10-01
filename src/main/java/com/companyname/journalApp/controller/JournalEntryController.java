package com.companyname.journalApp.controller;

import com.companyname.journalApp.entity.JournalEntry;
import com.companyname.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry) {
//        JournalEntry is coming from your own entity package, specifically from this import at the top:
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }


//    FIND BY ID
    @GetMapping("id/{myId}")
//    http://localhost:8080/journal/id/6abdbee1e0ade572331530ce
    public JournalEntry getJournalById(@PathVariable ObjectId myId) {
        return journalEntryService.findById(myId).orElse(null);

    }


//    DELETE
    @DeleteMapping("id/{myId}")
//    http://localhost:8080/journal/id/3
    public boolean deleteJournalById(@PathVariable ObjectId myId) {
        journalEntryService.deleteById(myId);
        return true;

    }



//    UPDATE
    @PutMapping("id/{myId}")
//    http://localhost:8080/journal/id/6abdbed8e0ade572331530cd
    public JournalEntry updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        JournalEntry old = journalEntryService.findById(myId).orElse(null);

        if( old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")?newEntry.getTitle():old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")?newEntry.getContent():old.getContent());

        }
        journalEntryService.saveEntry(old);
        return old;

    }
}
