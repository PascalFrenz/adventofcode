package me.frenz.day07;

import java.util.ArrayList;
import java.util.List;


class CommandFactory {

    public Command createCommand(final List<String> output) {
        if (output.isEmpty()) {
            throw new IllegalArgumentException("Output must at least contain command call");
        }
        final String command = output.get(0).substring(2).split(" ")[0];
        return switch (command) {
            case "ls" -> new LsCommand(new ArrayList<>(output.subList(1, output.size())));
            case "cd" -> new CdCommand(output.get(0));
            default -> throw new UnsupportedOperationException("Command [" + command + "] unknown");
        };
    }
}
