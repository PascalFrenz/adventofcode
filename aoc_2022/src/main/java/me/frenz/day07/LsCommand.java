package me.frenz.day07;

import java.util.HashSet;
import java.util.List;


record LsCommand(List<String> output) implements Command {

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final LsCommand lsCommand = (LsCommand) o;

        return output.equals(lsCommand.output);
    }

    void appendFilesTo(AocDirectory currentDir) {
        final List<AbstractAocFile> files = output.stream()
                .map(line -> line.split(" "))
                .map(parts -> {
                    if ("dir".equals(parts[0])) {
                        return new AocDirectory(parts[1], currentDir, new HashSet<>());
                    } else {
                        return new AocFile(Long.parseLong(parts[0]), parts[1], currentDir);
                    }
                }).toList();
        currentDir.appendFiles(files);
    }

}
