package org.jembi.jempi.bootstrapper.data.stream.kafka.cli;

import picocli.CommandLine.Command;

@Command(name = "kafka", mixinStandardHelpOptions = true, subcommands = {KafkaResetAllCommand.class,
                                                                         KafkaDeleteAllCommand.class,
                                                                         KafkaCreateAllSchemaDataCommand.class,
                                                                         KafkaListTopicsCommand.class,
                                                                         KafkaDescribeTopicCommand.class})
public class KafkaCLI {
}
