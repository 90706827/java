package com.example.demo.CompletableFuture.oreilly;

import com.example.demo.thread.CompletableFuture.oreilly.GamePageParser;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

public class GamePageParserTest {
    private GamePageParser parser = new GamePageParser();

    @Test
    public void getGames() {
        parser.printGames(LocalDate.of(2017, Month.MAY, 5), 3);
    }
}