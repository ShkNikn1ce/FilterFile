/*Вариант 1
        InputStream is = Main.class.getResourceAsStream("/input_1.txt");
        if(is ==null){
            System.out.println("file not found");
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            String line;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
/* Вариант 2
            Scanner sc = new Scanner(new File("input_1.txt"));
            //TODO:Add scan input2
            File integerFile = new File("int.txt");
            File floatFile = new File("float.txt");
            File stringFile = new File("string.txt");


            while (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    if (!integerFile.exists()) {
                        integerFile.createNewFile();
                    }
                    PrintWriter integerPW = new PrintWriter(integerFile);
                    int i = sc.nextInt();
                    integerCount++;
                    integerSum += i;
                    if (i < integerMin) integerMin = i;
                    if (i > integerMax) integerMax = i;

                    integerPW.println(i);
                    integerPW.close();
                } else if (sc.hasNextFloat()) {
                    if (!floatFile.exists()) {
                        floatFile.createNewFile();
                    }
                    PrintWriter floatPW = new PrintWriter(floatFile);
                    float f = sc.nextFloat();
                    floatCount++;
                    floatSum += f;
                    if (f < floatMin) floatMin = f;
                    if (f > floatMax) floatMax = f;
                    floatPW.println(f);
                    floatPW.close();

                } else {
                    if (!stringFile.exists()) {
                        stringFile.createNewFile();
                    }
                    PrintWriter stringPW = new PrintWriter(stringFile);
                    String s = sc.next();
                    stringCount++;
                    if (s.length() < stringLengthMin) stringLengthMin = s.length();
                    if (s.length() > stringLengthMax) stringLengthMax = s.length();
                    stringPW.println(s);
                    stringPW.close();
                }

            }*/
/*Вариант 3
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader("input_1.txt"));
            String line;
            while((line=br.readLine())!=null){
                line = line.trim();
                if(line.isEmpty()){
                    System.out.println("ERROR: пустая строка");
                }
                (Integer.parseInt(line)){

                }
            }
        }catch(IOException e){
            System.out.println("ERROR:" + e);

        }finally{
            try{
                if(br==null) {
                    br.close();
                }
            } catch (IOException e) {
                System.out.println("ERROR:"+ e);
            }
        }*/
/*try (BufferedReader br = new BufferedReader(new FileReader("input_1.txt"));
    PrintWriter integerPW = new PrintWriter("int.txt");
    PrintWriter floatPW = new PrintWriter("float.txt");
    PrintWriter stringPW = new PrintWriter("string.txt");) {
   String line;
   while ((line = br.readLine()) != null) {
       line = line.trim();
       if (line.isEmpty()) continue;
       try {
           int i = Integer.parseInt(line);
           integerCount++;
           integerSum += i;
           if (i < integerMin) integerMin = i;
           if (i > integerMax) integerMax = i;
           integerPW.println(i);
       } catch (NumberFormatException e1) {
           try {
               float f = Float.parseFloat(line);
               floatCount++;
               floatSum += f;
               if (f < floatMin) floatMin = f;
               if (f > floatMax) floatMax = f;
               floatPW.println(f);
           } catch (NumberFormatException e2) {
               stringCount++;
               if (line.length() < stringLengthMin) stringLengthMin = line.length();
               if (line.length() > stringLengthMax) stringLengthMax = line.length();
               stringPW.println(line);
           }
       }
   }
       /////Вывод результата
} catch (IOException e) {
   System.out.println("ERROR:" + e);
}*/
package com.example;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void logic(String[] filesInput, String outputPath, String prefix, boolean shortStatistics, boolean fullStatistics) {
        int integerCount = 0;
        int integerSum = 0;
        int integerMin = Integer.MAX_VALUE;
        int integerMax = Integer.MIN_VALUE;

        int floatCount = 0;
        float floatSum = 0;
        float floatMin = Float.MAX_VALUE;
        float floatMax = Float.MIN_VALUE;

        int stringCount = 0;
        int stringLengthMin = Integer.MAX_VALUE;
        int stringLengthMax = Integer.MIN_VALUE;


        File intFile = new File(outputPath, prefix + "int.txt");
        File floatFile = new File(outputPath, prefix + "float.txt");
        File stringFile = new File(outputPath, prefix + "string.txt");

        for (String file : filesInput) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    try {
                        int i = Integer.parseInt(line);
                        try (PrintWriter integerPW = new PrintWriter(new FileWriter(intFile, true))) {
                            integerCount++;
                            integerSum += i;
                            if (i < integerMin) integerMin = i;
                            if (i > integerMax) integerMax = i;
                            integerPW.println(i);
                        }
                    } catch (NumberFormatException e1) {
                        try {
                            float f = Float.parseFloat(line.replace(',', '.'));
                            try (PrintWriter floatPW = new PrintWriter(new FileWriter(floatFile, true))) {
                                floatCount++;
                                floatSum += f;
                                if (f < floatMin) floatMin = f;
                                if (f > floatMax) floatMax = f;
                                floatPW.println(f);
                            }
                        } catch (NumberFormatException e2) {
                            try (PrintWriter stringPW = new PrintWriter(new FileWriter(stringFile, true))) {
                                stringCount++;
                                if (line.length() < stringLengthMin) stringLengthMin = line.length();
                                if (line.length() > stringLengthMax) stringLengthMax = line.length();
                                stringPW.println(line);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(" Ошибка чтения файла: " + file);
            }
        }

        if (shortStatistics || fullStatistics) {
            System.out.println("==========Statistics of dataset==========\n");
        }

        if (shortStatistics) {
            System.out.println("==========Short Statistics==========\n");

            System.out.println("===int.txt===");
            System.out.println("Count elements: " + integerCount + "\n");
            System.out.println("===float.txt===");
            System.out.println("Count elements: " + floatCount + "\n");
            System.out.println("===string.txt===");
            System.out.println("Count elements: " + stringCount + "\n");
        }

        if (fullStatistics) {
            System.out.println("==========Full statistics==========\n");

            System.out.println("===int.txt===");
            if(integerCount>0){
                System.out.println("Count elements: " + integerCount);
                System.out.println("Sum elements: " + integerSum);
                System.out.println("Average value: " + (float) integerSum / integerCount);
                System.out.println("Minimal element: " + integerMin);
                System.out.println("Maximal element: " + integerMax + "\n");
            }else {
                System.out.println("Count elements: " + integerCount);
                System.out.println("Sum elements: " + integerSum);
                System.out.println("Average value: There is no average value for type int!");
                System.out.println("Minimal element: zero!");
                System.out.println("Maximal element: zero! \n");
            }


            System.out.println("===float.txt===");
            if(floatCount>0){
                System.out.println("Count elements: " + floatCount);
                System.out.println("Sum elements: " + floatSum);
                System.out.println("Average value: " + floatSum / floatCount);
                System.out.println("Minimal element: " + floatMin);
                System.out.println("Maximal element: " + floatMax + "\n");
            }else{
                System.out.println("Count elements: " + floatCount);
                System.out.println("Sum elements: " + floatSum);
                System.out.println("Average value: There is no average value for type float!");
                System.out.println("Minimal element: zero!");
                System.out.println("Maximal element: zero! \n");
            }

            System.out.println("===string.txt===");
            if(stringCount>0){
                System.out.println("Count elements: " + stringCount);
                System.out.println("Minimal string length: " + stringLengthMin);
                System.out.println("Maximal string length: " + stringLengthMax);
            }else{
                System.out.println("Count elements: " + stringCount);
                System.out.println("Minimal string length: String is not fount");
                System.out.println("Maximal string length: String is not fount");
            }
        }

    }
}