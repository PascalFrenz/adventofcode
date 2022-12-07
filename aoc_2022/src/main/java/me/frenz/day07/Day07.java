package me.frenz.day07;

import me.frenz.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Day07 extends Day<Long, Long> {

    private static final long MAX_SPACE_AVAIL = 70000000;
    private static final long MIN_SPACE_UPDATE = 30000000;

    private final CommandFactory commandFactory = new CommandFactory();

    public Day07(final List<String> input) {
        super(input);
    }

    @Override
    protected Long part1() {
        return createDirectoryTree().getDirsMatching(dir -> dir.size() <= 100000)
                .stream()
                .mapToLong(AocDirectory::size)
                .sum();
    }

    @Override
    protected Long part2() {
        final AocDirectory fs = createDirectoryTree();
        final long unusedSpace = MAX_SPACE_AVAIL - fs.size();
        final long spaceToClear = MIN_SPACE_UPDATE - unusedSpace;

        return fs.getDirsMatching(dir -> dir.size() >= spaceToClear)
                .stream()
                .min(Comparator.comparingLong(AocDirectory::size))
                .map(AocDirectory::size)
                .orElse(-1L);
    }

    public AocDirectory createDirectoryTree() {
        final List<Command> commands = parseCommands(input);
        if (commands.isEmpty()) {
            throw new IllegalArgumentException("Must not be empty");
        } else if (!commands.get(0).output().get(0).contains("cd /")) {
            throw new IllegalArgumentException("First command must be root cd!");
        }

        AocDirectory root = AocDirectory.ROOT;
        AocDirectory context = root;
        for (final Command command : commands.subList(1, commands.size())) {
            if (command instanceof CdCommand cd) {
                context = cd.directory(context);
            } else if (command instanceof LsCommand ls) {
                ls.appendFilesTo(context);
            }
        }

        return root;
    }

    public List<Command> parseCommands(final List<String> input) {
        final List<List<String>> commandsOutput = splitIntoCommands(input);
        return commandsOutput.stream().map(commandFactory::createCommand).toList();
    }

    public List<List<String>> splitIntoCommands(final List<String> input) {
        final List<List<String>> rawCommandOutput = new ArrayList<>();
        List<String> buffer = new ArrayList<>();
        for (final String line : input) {
            if (line.startsWith("$") && !buffer.isEmpty()) {
                rawCommandOutput.add(new ArrayList<>(buffer));
                buffer.clear();
            }
            buffer.add(line);
        }
        rawCommandOutput.add(buffer);
        return rawCommandOutput;
    }
}
