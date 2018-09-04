package com.ciandt.service;

import com.ciandt.model.Quote;

import java.util.List;

public interface QuoteService {

    Quote createQuote(Quote quote); // Method = POST, URL: /quote

    Quote readQuote(Integer id); // Method = get, URL: /quote/{id}

    Quote updateQuote(Quote updatedQuote); // Method = PUT, URL: /quote/{id}

    Boolean deleteQuote(Integer id); // Method = DELETE, URL: /quote/{id}

    List<Quote> listQuotes(); // Method = GET, URL: /quotes

    Boolean performLike(Integer id); // Method = POST, URL: /quote/like/{id}
}
