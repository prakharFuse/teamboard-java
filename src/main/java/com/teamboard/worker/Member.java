package com.teamboard.worker;

/** One row of the TeamBoard directory, as the worker reads it. */
public final class Member {
    private final int id;
    private final String name;
    private final String email;
    private final String role;
    private final String department;
    private final String startDate;
    private final boolean active;

    public Member(int id, String name, String email, String role, String department,
                  String startDate, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.department = department;
        this.startDate = startDate;
        this.active = active;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getDepartment() { return department; }
    public String getStartDate() { return startDate; }
    public boolean isActive() { return active; }
}
