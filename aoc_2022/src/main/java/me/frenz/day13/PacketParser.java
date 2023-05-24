package me.frenz.day13;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PacketParser {

    private final Pattern number = Pattern.compile("^(\\d+)\\D*");

    String parsePacket(PacketValue packet, String line) {
        while (!line.isBlank()) {
            if (line.startsWith("[")) {
                ContainerPacket childs = new ContainerPacket();
                line = parsePacket(childs, line.substring(1));
                packet.add(childs);
            }
            if (line.startsWith("]")) {
                return line.substring(1);
            }
            final Matcher m = number.matcher(line);

            if (m.find()) {
                int num = Integer.parseInt(m.group(1));
                packet.add(new IntPacket(num));
                line = line.substring(m.group(1).length());
            }
            if (line.startsWith(",")) {
                line = line.substring(1);
            }
        }

        return null;
    }
}
