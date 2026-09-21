package com.teamboard.worker;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Renders the directory as the CSV the HR drop expects. */
public class MemberCsvWriter {

    static final String HEADER = "id,name,email,role,department,start_date,is_active";

    public String render(List<Member> members) {
        StringBuilder csv = new StringBuilder(HEADER);
        for (Member m : members) {
            csv.append('\n')
               .append(m.getId()).append(',')
               .append(m.getName()).append(',')
               .append(m.getEmail()).append(',')
               .append(m.getRole()).append(',')
               .append(m.getDepartment()).append(',')
               .append(m.getStartDate()).append(',')
               .append(m.isActive() ? 1 : 0);
        }
        return csv.toString();
    }

    public void writeTo(Path destination, List<Member> members) throws IOException {
        Files.createDirectories(destination.getParent());
        try (BufferedWriter out = Files.newBufferedWriter(destination, StandardCharsets.UTF_8)) {
            out.write(render(members));
        }
    }
}
