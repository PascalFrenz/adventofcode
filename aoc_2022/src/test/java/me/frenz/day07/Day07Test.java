package me.frenz.day07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class Day07Test {

    private Day07 dut;

    @BeforeEach
    void setUp() {
        String EXAMPLE_INPUT = """
                $ cd /
                $ ls
                dir a
                14848514 b.txt
                8504156 c.dat
                dir d
                $ cd a
                $ ls
                dir e
                29116 f
                2557 g
                62596 h.lst
                $ cd e
                $ ls
                584 i
                $ cd ..
                $ cd ..
                $ cd d
                $ ls
                4060174 j
                8033020 d.log
                5626152 d.ext
                7214296 k
                """;
        dut = new Day07(EXAMPLE_INPUT.lines().collect(Collectors.toList()));
    }

    @Test
    void part1() {
        assertEquals(95437, dut.part1());
    }

    @Test
    void part2() {
        assertEquals(24933642, dut.part2());
    }

    @Test
    void testParseLsCommand() {
        final String commandExample = """
                $ ls
                dir a
                14848514 b.txt
                8504156 c.dat
                dir d
                $ cd a
                """;

        final Command expected = new LsCommand(List.of("dir a", "14848514 b.txt", "8504156 c.dat", "dir d"));
        final List<Command> actual = dut.parseCommands(commandExample.lines().toList());
        assertTrue(actual.contains(expected));
        assertInstanceOf(LsCommand.class, actual.get(0));
        assertInstanceOf(CdCommand.class, actual.get(1));

        final LsCommand actualLsCommand = (LsCommand) actual.get(0);
        assertEquals(List.of("dir a", "14848514 b.txt", "8504156 c.dat", "dir d"), actualLsCommand.output());
    }

    @Test
    void testSplitInputIntoCommand() {
        final String commandExample = """
                $ ls
                dir a
                14848514 b.txt
                8504156 c.dat
                dir d
                $ cd a
                """;

        assertEquals(
                List.of(
                        List.of(
                                "$ ls",
                                "dir a",
                                "14848514 b.txt",
                                "8504156 c.dat",
                                "dir d"
                        ),
                        List.of("$ cd a")
                ),
                dut.splitIntoCommands(commandExample.lines().toList())
        );
    }

    @Test
    void testCanCreateDirectoryStructure() {
        AocDirectory dir = new AocDirectory("name", null, Set.of());
        assertNotNull(dir);
        assertEquals("name", dir.name());
    }

    @Test
    void testCanCreateDirectoryTree() {
        final AocDirectory dir = dut.createDirectoryTree();
        assertNull(dir.parent());
        assertTrue(dir.contains("a"));
        assertTrue(dir.contains("b.txt"));
        assertTrue(dir.contains("c.dat"));
        assertTrue(dir.contains("d"));

        if (dir.get("a") instanceof AocDirectory a) {
            assertTrue(a.contains("e"));
            assertTrue(a.contains("f"));
            assertTrue(a.contains("g"));
            assertTrue(a.contains("h.lst"));

            if (a.get("e") instanceof AocDirectory e) {
                assertEquals(584, e.size());
            }
        }
    }

    @Test
    void testCalculateSize() {
        assertEquals(48381165, dut.createDirectoryTree().size());
    }
}