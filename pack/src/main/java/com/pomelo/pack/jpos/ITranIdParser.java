package com.pomelo.pack.jpos;

interface ITranIdParser {
    String getTranId(JposMessage message);
}