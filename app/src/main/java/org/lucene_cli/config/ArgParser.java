package org.lucene_cli.config;

import java.util.HashMap;

public class ArgParser {
    private String command = null;
    private HashMap<String, String> flags = new HashMap<>();

    public ArgParser (String[] args) throws Exception {
        for (String arg : args) {
            // Support "index" and "query" as command
            if (arg.equals("index") || arg.equals("query")) {
                if (command != null) {
                    throw new Exception(String.format("Multiple commands specified: %s and %s", command, arg));
                }
                command = arg;
            } else if (arg.startsWith("--")) {
                // Support flags like --flag=value

                // "--flag=value" => "flag=value" => "flag", "value"
                String[] parts = arg.substring(2).split("=", 2);
                if (parts.length == 2) {
                    flags.put(parts[0], parts[1]);
                } else {
                    flags.put(parts[0], "");
                }
            } else {
                throw new Exception(String.format("Unknown argument: %s.", arg));
            }
        }
    }

    public String getCommand() {
        return command;
    }

    public String getFlag(String key) {
        return flags.get(key);
    }
}
