package com.example;

import org.apache.commons.cli.*;

import java.io.File;

public class OptionFilter {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption(Option
                .builder("o")
                .longOpt("output")
                .hasArg()
                .argName("path")
                .desc("Путь для выходных файлов")
                .build()
        );
        options.addOption(Option
                .builder("p")
                .longOpt("prefix")
                .hasArg()
                .argName("sample-")
                .desc("Префикс выходных фалов")
                .build()
        );

        options.addOption(Option
                .builder("s")
                .longOpt("shortStatistics")
                .desc("Краткая статистика")
                .build()
        );

        options.addOption(Option
                .builder("f")
                .longOpt("fullStatistics")
                .desc("Полная статистика")
                .build()
        );

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            String outputPath = cmd.getOptionValue("o", ".");
            String prefix = cmd.getOptionValue("p", "");
            boolean shortStatistics = cmd.hasOption("s");
            boolean fullStatistics = cmd.hasOption("f");

            String[] inputFiles = cmd.getArgs();

            Main.logic(
                    inputFiles,
                    outputPath,
                    prefix,
                    shortStatistics,
                    fullStatistics
            );

            if (inputFiles.length < 2) {
                throw new ParseException("Необходимо указать два входных файла");
            }

            for (String fileNew : inputFiles) {
                File file = new File(fileNew);

                if (!file.exists()) {
                    throw new ParseException("Файл " +fileNew+ " не найден");
                }

                if(!file.isFile()){
                    throw new ParseException(fileNew +" не является файлом");
                }

                if(!fileNew.endsWith(".txt")){
                    throw new ParseException("Файл "+fileNew+ " не является файлом .txt");
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
