package com.handwoong.everyonewaiter.common.config.log;

import java.util.List;

public enum FormatType {
    DDL(List.of("create", "alter", "comment")),
    ;

    private final List<String> commands;

    FormatType(final List<String> commands) {
        this.commands = commands;
    }

    public static boolean isDDL(final String sql) {
        return FormatType.DDL
            .commands
            .stream()
            .anyMatch(sql::startsWith);
    }
}
