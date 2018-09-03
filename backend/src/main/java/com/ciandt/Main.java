package com.ciandt;

import com.ciandt.model.Message;
import com.ciandt.model.Quote;
import com.ciandt.service.QuoteService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


@RestController
public class Main {

    Logger logger = Logger.getLogger(Main.class.getName());

    @Autowired
    QuoteService quoteService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHelloWorld() {
        return "Hello world!!";
    }

    @RequestMapping(value = "/quote", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listQuotes() {
        Gson gson = new Gson();
        List<Quote> quoteList = quoteService.listQuotes();
        return new ResponseEntity<String>(gson.toJson(quoteList), HttpStatus.OK);
    }

    @RequestMapping(value = "/quote/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readQuote(@PathVariable("id") int id) {
        Gson gson = new Gson();
        Quote quote = quoteService.readQuote(id);
        if (quote != null) {
            return new ResponseEntity<String>(gson.toJson(quote), HttpStatus.OK);
        } else {
            return new ResponseEntity<Message>(new Message("Quote not found"), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/quote", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createQuote(@RequestBody String data) {
        Gson gson = new Gson();
        Quote quote = gson.fromJson(data, Quote.class);
        quote = quoteService.createQuote(quote);
        return new ResponseEntity<String>(gson.toJson(quote), HttpStatus.OK);
    }

    @RequestMapping(value = "/quote/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteQuote(@PathVariable("id") int id) {
        Quote quote = quoteService.readQuote(id);
        if (quoteService.deleteQuote(id)) {
            return new ResponseEntity<Message>(new Message("OK"), HttpStatus.OK);
        } else {
            return new ResponseEntity<Message>(new Message("Quote not found"), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/quote/{id}/like", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> performLike(@PathVariable("id") int id) {
        Gson gson = new Gson();
        if (quoteService.performLike(id)) {
            return new ResponseEntity<Message>(new Message("OK"), HttpStatus.OK);
        } else {
            return new ResponseEntity<Message>(new Message("Quote not found"), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/quote", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editQuote(@RequestBody String data) {
        Gson gson = new Gson();
        Quote quote = gson.fromJson(data, Quote.class);
        quote = quoteService.updateQuote(quote);
        return new ResponseEntity<String>(gson.toJson(quote), HttpStatus.OK);
    }

}
