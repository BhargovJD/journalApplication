package com.companyname.journalApp.controller;

import com.companyname.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    // Creates a private Map that stores JournalEntry objects using a Long ID as the key.
    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping("/abc")
//    http://localhost:8080/journal/abc
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }



    @PostMapping
//    http://localhost:8080/journal
//    {
//        "id":3,
//            "title":"apple",
//            "content":" 8 pc"
//    }
    public boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(), myEntry);
        return true;

    }


    @GetMapping("id/{myId}")
//    http://localhost:8080/journal/id/3
    public JournalEntry getJournalById(@PathVariable Long myId){
        return journalEntries.get(myId);

    }


    @DeleteMapping("id/{myId}")
//    http://localhost:8080/journal/id/3
    public JournalEntry deleteJournalById(@PathVariable Long myId){
        return journalEntries.remove(myId);

    }

    @PutMapping("id/{myId}")
//    http://localhost:8080/journal/id/3
    public JournalEntry updateJournalById(@PathVariable Long myId,@RequestBody JournalEntry myEntry){
        return journalEntries.put(myId, myEntry);

    }
}
