package me.frenz.day07;

import java.util.HashSet;
import java.util.List;


class CdCommand implements Command {

    private final String command;

    public CdCommand(final String command) {
        this.command = command;
    }

    @Override
    public List<String> output() {
        return List.of(command);
    }

    AocDirectory directory(AocDirectory parent) {
        final String name = command.substring(5);
        if ("..".equals(name)) {
            return parent.parent();
        } else {
            return parent.update(name, new AocDirectory(name, parent, new HashSet<>()));
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CdCommand cdCommand = (CdCommand) o;

        return command.equals(cdCommand.command);
    }

    @Override
    public int hashCode() {
        return command.hashCode();
    }
}
