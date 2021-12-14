package com.epam.brest.serializationDemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Arrays;
import java.util.List;



public class RandomObjectTest {

    @Test
    public void testXml() throws IOException{
        String fileName = "fileObjects.xml";
        RandomObject obj1 = new RandomObject();
        RandomObject obj2 = new RandomObject();



       // System.out.println("write");
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(fileName))) {
            encoder.writeObject(obj1);
            encoder.writeObject(obj2);

        }
        RandomObject result1;
        RandomObject resul2;



       // System.out.println("read");
        try (XMLDecoder decoder = new XMLDecoder((new FileInputStream(fileName)))) {
            result1 = (RandomObject) decoder.readObject();
            resul2 = (RandomObject) decoder.readObject();
        }
        Assertions.assertArrayEquals(obj1.data, result1.data);
       Assertions.assertArrayEquals(obj2.data,resul2.data);
    }

    @Test
      void testJson() throws IOException{
        String fileName = "fileObjects.json";
        RandomObject obj1 = new RandomObject();
        RandomObject obj2 = new RandomObject();

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("write");
        try (OutputStream out = new FileOutputStream(fileName)) {
            mapper.writeValue(out, Arrays.asList(obj1,obj2));

        }


        List<RandomObject> list;

        System.out.println("read");
        try (InputStream input = new FileInputStream(fileName)) {
            list =
mapper.readerForListOf(RandomObject.class).readValue(input);       }
        Assertions.assertArrayEquals(obj1.data, list.get(0).data);
        Assertions.assertArrayEquals(obj2.data,list.get(1).data);
    }
    }
