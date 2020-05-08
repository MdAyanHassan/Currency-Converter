package mainPackage;

import java.util.Scanner;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
public class Program
{
 public static void main(String[] args)throws IOException {
     Scanner input = new Scanner(System.in);
     String base = null, link = null;
     try {
         base = input.nextLine().trim().toUpperCase();
         link = "https://api.exchangeratesapi.io/latest?base=" + base;
     } catch(Exception e) {
         System.out.println("An error occured, taking default base as EUR");
         link = "https://api.exchangeratesapi.io/latest?base=";
     }
     finally {
         input.close();
     }
     
     BufferedReader readURL = null;
     ArrayList<String> contentURL = new ArrayList<String>();
     try {
         URL currencyLink = new URL(link);
         readURL = new BufferedReader(new InputStreamReader(currencyLink.openStream()));
         String line;
         while((line = readURL.readLine()) != null) {
             String[] lineSplit = line.split(",");
             addToList(contentURL, lineSplit);
         }
         
     } catch(MalformedURLException m) {
         System.out.println("URL not found " + m);
         return;
     } catch(FileNotFoundException f) {
         System.out.println("File not found " + f);
         return;
     } catch(Exception a) {
         System.out.println("An error occurred " + a);
         return;
     }
     finally {
         readURL.close();
     }
     HashMap<String, String> rates = new HashMap<>();
    makeMap(rates, contentURL);
    printRates(base, rates);
 }
 public static void addToList(ArrayList<String> addTo, String[] readFrom) {
     for(String i:readFrom) {
         addTo.add(i);
     }
 }
 public static void makeMap(HashMap<String, String> rates, ArrayList<String> currencies) {
     currencies.remove(0);
     currencies.remove(currencies.size()-1);
     currencies.remove(currencies.size()-1);
     
     for(String i:currencies) {
         String[] splitConRate = i.split(":");
         rates.put(splitConRate[0].toString(), splitConRate[1].toString());
     }
 }
 public static void printRates(String base, HashMap<String, String> rates) {
     System.out.println("\t====================");
     System.out.println("\t|Currency Converter|");
     System.out.println("\t====================");
     System.out.println("\nEntered base: " + "\"" + base + "\"" + "\n");
     
     System.out.println("The rates:\n");
     ArrayList<String> countries = new ArrayList<>(rates.keySet());
     ArrayList<String> ratesList = new ArrayList<>(rates.values());
     int n = 0;
     while(n<rates.size()) {
         System.out.print(countries.get(n) + "\t=\t");
         System.out.printf("%.2f", Double.parseDouble(ratesList.get(n).replace("}", "")));
         System.out.println();
         n++;
     }
 }

}
