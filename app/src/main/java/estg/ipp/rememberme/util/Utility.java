package estg.ipp.rememberme.util;

import java.text.SimpleDateFormat;
import java.util.Date;

//classe para definir data de registo do medicamento
public class Utility {

    public static String getCurrentData(){

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
            String currentDateTime = dateFormat.format(new Date());

            return currentDateTime;
        }catch(Exception e){
            return null;
        }
    }

    public static String getMonthFromNumber(String monthNumber){
       switch (monthNumber){
           case "01":{
               return "Janeiro";
           }
           case "02":{
               return "Fevereiro";
           }
           case "03":{
               return "Março";
           }
           case "04":{
               return "Abril";
           }
           case "05":{
               return "Maio";
           }
           case "06":{
               return "Junho";
           }
           case "07":{
               return "Julho";
           }
           case "08":{
               return "Agosto";
           }
           case "09":{
               return "Setembro";
           }
           case "10":{
               return "Outubro";
           }
           case "11":{
               return "Novembro";
           }
           case "12":{
               return "Dezembro";
           }

           default:{
               return "Erro";
           }
       }
    }
}
