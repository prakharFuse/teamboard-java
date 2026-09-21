package com.teamboard.worker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class MemberCsvWriterTest {

    @Test
    void rendersHeaderAndOneRowPerMember() {
        String csv = new MemberCsvWriter().render(List.of(
            new Member(1, "Ada Lovelace", "ada@teamboard.test", "Engineer", "Platform", "2021-04-01", true),
            new Member(2, "Alan Turing", "alan@teamboard.test", "Engineer", "Platform", "2020-01-06", false)
        ));

        String[] lines = csv.split("\n");
        assertEquals(3, lines.length);
        assertEquals(MemberCsvWriter.HEADER, lines[0]);
        assertTrue(lines[1].startsWith("1,Ada Lovelace,"));
        assertTrue(lines[2].endsWith(",0"));
    }
}
