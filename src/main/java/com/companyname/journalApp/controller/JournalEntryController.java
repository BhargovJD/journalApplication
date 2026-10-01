package com.companyname.journalApp.controller;

import com.companyname.journalApp.entity.JournalEntry;
import com.companyname.journalApp.entity.User;
import com.companyname.journalApp.service.JournalEntryService;
import com.companyname.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;


    // =========================
    // READ - GET ALL JOURNALS OF A USER
    // =========================
    // GET http://localhost:8080/journal/bhargov
    @GetMapping("/{userName}")
    public ResponseEntity<?> getAll(@PathVariable String userName) {

        User username = userService.findByUserName(userName);

        if (username != null) {

            return new ResponseEntity<>(
                    username.getJournalEntries(),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }


    // =========================
    // CREATE JOURNAL FOR USER
    // =========================
    // POST http://localhost:8080/journal/bhargov
    //
    // Body:
    // {
    //     "title": "My Journal",
    //     "content": "Today was a good day"
    // }
    @PostMapping("/{userName}")
    public ResponseEntity<?> createEntry(
            @PathVariable String userName,
            @RequestBody JournalEntry myEntry) {

        User username = userService.findByUserName(userName);

        if (username != null) {

            try {
                // Set current date
                myEntry.setDate(LocalDateTime.now());

                // First save JournalEntry
                journalEntryService.saveEntry(myEntry);

                // Then add it to user's journal list
                username.getJournalEntries().add(myEntry);

                // Save updated user
                userService.saveEntry(username);

                return new ResponseEntity<>(
                        myEntry,
                        HttpStatus.CREATED
                );

            } catch (Exception e) {

                // Temporarily print the actual error
                e.printStackTrace();

                return new ResponseEntity<>(
                        HttpStatus.BAD_REQUEST
                );
            }
        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }

    // =========================
    // FIND JOURNAL BY ID
    // =========================
    // GET
    // http://localhost:8080/journal/bhargov/id/6abdbed8e0ade572331530cd
    @GetMapping("/{userName}/id/{myId}")
    public ResponseEntity<?> getJournalById(
            @PathVariable String userName,
            @PathVariable ObjectId myId) {

        User username = userService.findByUserName(userName);

        if (username != null) {

            for (JournalEntry journalEntry : username.getJournalEntries()) {

                if (journalEntry.getId().equals(myId)) {

                    return new ResponseEntity<>(
                            journalEntry,
                            HttpStatus.OK
                    );
                }
            }
        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }


    // =========================
    // DELETE JOURNAL
    // =========================
    // DELETE
    // http://localhost:8080/journal/bhargov/id/6abdbed8e0ade572331530cd
    @DeleteMapping("/{userName}/id/{myId}")
    public ResponseEntity<?> deleteJournalById(
            @PathVariable String userName,
            @PathVariable ObjectId myId) {

        User username = userService.findByUserName(userName);

        if (username != null) {

            // Remove journal entry from user's journalEntries list
            boolean removed = username.getJournalEntries()
                    .removeIf(entry -> entry.getId().equals(myId));

            if (removed) {

                // Save updated User
                userService.saveEntry(username);

                // Delete JournalEntry from JournalEntry collection
                journalEntryService.deleteById(myId);

                return new ResponseEntity<>(
                        HttpStatus.NO_CONTENT
                );
            }
        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }


    // =========================
    // UPDATE JOURNAL
    // =========================
    // PUT
    // http://localhost:8080/journal/bhargov/id/6abdbed8e0ade572331530cd
    //
    // Body:
    // {
    //     "title": "Updated title",
    //     "content": "Updated content"
    // }
    @PutMapping("/{userName}/id/{myId}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable String userName,
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry) {

        User username = userService.findByUserName(userName);

        if (username != null) {

            for (JournalEntry old : username.getJournalEntries()) {

                if (old.getId().equals(myId)) {

                    // Update title
                    if (newEntry.getTitle() != null
                            && !newEntry.getTitle().isEmpty()) {

                        old.setTitle(newEntry.getTitle());
                    }

                    // Update content
                    if (newEntry.getContent() != null
                            && !newEntry.getContent().isEmpty()) {

                        old.setContent(newEntry.getContent());
                    }

                    // Save the actual JournalEntry document
                    journalEntryService.saveEntry(old);

                    // Save User also, if needed
                    userService.saveEntry(username);

                    return new ResponseEntity<>(
                            old,
                            HttpStatus.OK
                    );
                }
            }
        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );
    }
}