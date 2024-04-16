/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp11;


import javax.smartcardio.*;
import java.io.UnsupportedEncodingException;
import java.util.List;



public class Tp11 {

    private static byte [] APDU_ic ={(byte) 0x80,(byte) 0x20,(byte)0x07,(byte)0x00,(byte)0x08,0x41,0x43,
       0x4F,0x53,0x54,0x45,0x53,0x54};
private static byte [] APDU_PIN ={(byte) 0x80,(byte) 0x20,(byte)0x07,(byte)0x00,(byte)0x08,0x01,0x01,
       0x01,0x01,0x01,0x01,0x01,0x01};
    private static byte [] SELECT_APDU ={(byte) 0x80,(byte) 0xA4,(byte)0x00,(byte)0x00,(byte)0x02,(byte)0xFF,0x02};
    
    public static void main(String[] args) throws CardException{
       TerminalFactory tf = TerminalFactory.getDefault();
        CardTerminals readers = tf.terminals();
    
    /*  List<CardTerminal> readersList = readers.list();
        for (CardTerminal readersList1 : readersList) {
            //readersList.forEach((ct) -> {
            System.out.println(readersList1.getName());
        }
    };*/
     CardTerminal Myreader = readers.getTerminal("Gemalto Prox-DU Contact_12400712 0");
        
      Card card = null ; 
        if (Myreader.isCardPresent()){
            card = Myreader.connect("*");
            if (card !=null){
                StringBuilder atr = new StringBuilder();
                for( byte c:card.getATR().getBytes())
                   atr.append(String.format("%02X", c));
                
              System.out.println("ATR = "+atr.toString());
              CardChannel ch = card.getBasicChannel();
              CommandAPDU APDU_IC = new CommandAPDU(APDU_ic);
              ResponseAPDU rep = ch.transmit(APDU_IC);
              if  (rep.getSW()==0x9000){
                 System.out.println(" OK Submit IC; SW=0x9000");
              } else{
                  
                System.out.println("ok : Code IC");
               System.out.println("Nombre tentqtive restant :" + (rep.getSW2()& 0x0F));
            }

              SELECT_APDU[5]=(byte)0xFF;
              SELECT_APDU[6]=(byte)0x02;
              CommandAPDU APDU = new CommandAPDU(SELECT_APDU);
              rep = ch.transmit(APDU);
                      if  (rep.getSW()==0x9000){
                        System.out.println(" ok FF02 selected");
                     } else{
                  
                       System.out.println("Nok FF02 not found");
        
            }
            

             byte[] vect = new byte[9];
             vect[0]= (byte) 0x80;
             vect[1]= (byte) 0xD2;
             vect[2]= (byte) 0x00;
             vect[3]= (byte) 0x00;
             vect[4]= (byte) 0x04;
             vect[5]= (byte) 0x00;
             vect[6]= (byte) 0x00;
             vect[7]= (byte) 0x01;
             vect[8]= (byte) 0x00;

        APDU = new CommandAPDU(vect);
        rep = ch.transmit(APDU);
           if (rep.getSW()== 0x9000)
                System.out.println("ok : write FF02 ");
           else 
                System.out.println("Nok : write FF02 " + Integer.toHexString(rep.getSW()));
      
            } 
           card = Myreader.connect("*");
            if (card !=null){
              CardChannel ch = card.getBasicChannel();
              CommandAPDU APDU_IC = new CommandAPDU(APDU_ic);
              ResponseAPDU rep = ch.transmit(APDU_IC);
              if  (rep.getSW()==0x9000){
                 System.out.println(" OK :code IC");
              } else{
                  
                System.out.println("nok : Code IC");
               System.out.println("Nombre tentqtive restant :" + (rep.getSW2()& 0x0F));
            }     
}  
         
          SELECT_APDU[5]=(byte)0xFF;
              SELECT_APDU[6]=(byte)0x04;
              CardChannel ch = card.getBasicChannel();
              CommandAPDU APDU = new CommandAPDU(SELECT_APDU);
              ResponseAPDU rep = ch.transmit(APDU);
                      if  (rep.getSW()==0x9000){
                        System.out.println(" ok FF04 selected");
                     } else{
                  
                       System.out.println("Nok FF04 not found");
        
            }
            

             byte[] vect = new byte[11];
             vect[0]= (byte) 0x80;
             vect[1]= (byte) 0xD2;
             vect[2]= (byte) 0x00;
             vect[3]= (byte) 0x00;
             vect[4]= (byte) 0x06;
             vect[5]= (byte) 0x30;
             vect[6]= (byte) 0x03;
             vect[7]= (byte) 0x40;
             vect[8]= (byte) 0x80;
             vect[9]= (byte) 0xAA;
             vect[10]= (byte) 0x00;
            

        APDU = new CommandAPDU(vect);
        rep = ch.transmit(APDU);
           if (rep.getSW()== 0x9000)
                System.out.println("ok : write FF04 ");
           else 
                System.out.println("Nok : write FF04 " + Integer.toHexString(rep.getSW()));
      
            

             SELECT_APDU[5]=(byte)0xFF;
              SELECT_APDU[6]=(byte)0x03;
              APDU = new CommandAPDU(SELECT_APDU);
              rep = ch.transmit(APDU);
                      if  (rep.getSW()==0x9000){
                        System.out.println(" ok FF03 selected");
                     } else{
                  
                       System.out.println("Nok FF03 not found");
        
            }
            

            vect = new byte[13];
             vect[0]= (byte) 0x80;
             vect[1]= (byte) 0xD2;
             vect[2]= (byte) 0x01;
             vect[3]= (byte) 0x00;
             vect[4]= (byte) 0x08;
             vect[5]= (byte) 0x01;
             vect[6]= (byte) 0x01;
             vect[7]= (byte) 0x01;
             vect[8]= (byte) 0x01;
             vect[9]= (byte) 0x01;
            vect[10]= (byte) 0x01;
            vect[11]= (byte) 0x01;
            vect[12]= (byte) 0x01;

        APDU = new CommandAPDU(vect);
        rep = ch.transmit(APDU);
           if (rep.getSW()== 0x9000)
                System.out.println("ok : write FF03 ");
           else 
                System.out.println("Nok : write FF03 " + Integer.toHexString(rep.getSW()));
      
            vect = new byte[13];
             vect[0]= (byte) 0x80;
             vect[1]= (byte) 0x20;
             vect[2]= (byte) 0x06;
             vect[3]= (byte) 0x00;
             vect[4]= (byte) 0x08;
             vect[5]= (byte) 0x01;
             vect[6]= (byte) 0x01;
             vect[7]= (byte) 0x01;
             vect[8]= (byte) 0x01;
             vect[9]= (byte) 0x01;
            vect[10]= (byte) 0x01;
            vect[11]= (byte) 0x01;
            vect[12]= (byte) 0x01;

        APDU = new CommandAPDU(vect);
        rep = ch.transmit(APDU);
           if (rep.getSW()== 0x9000)
                System.out.println("ok : read FF03 ");
                
           else 
                System.out.println("Nok : read FF03 " + Integer.toHexString(rep.getSW()));
      
            SELECT_APDU[5]=(byte)0xAA;
            SELECT_APDU[6]=(byte)0x00;
APDU = new CommandAPDU(SELECT_APDU);
              rep = ch.transmit(APDU);
                      if  (rep.getSW()==0x9100){
                        System.out.println(" ok AA00 selected");
                     } else{
                  
                       System.out.println("Nok AA00 not found");
        
            }
            
            String data =" Aitlafkih AMINA                 ";
             vect = new byte[5+ data.length()];
             vect[0]= (byte) 0x80;
             vect[1]= (byte) 0xD2;
             vect[2]= (byte) 0x00;
             vect[3]= (byte) 0x00;
             vect[4]= (byte) data.length();
             for( int i=0 ; i< data.length();i++)
               vect[5+i]= (byte) data.getBytes()[i];

        APDU = new CommandAPDU(vect);
        rep = ch.transmit(APDU);
           if (rep.getSW()== 0x9000)
                System.out.println("ok : write AA00 ");
           else 
                System.out.println("Nok : write AA00 " + Integer.toHexString(rep.getSW()));
      
             vect = new byte[5];
             vect[0]= (byte) 0x80;
             vect[1]= (byte) 0xB2;
             vect[2]= (byte) 0x00;
             vect[3]= (byte) 0x00;
             vect[4]= (byte) data.length();
   
        APDU = new CommandAPDU(vect);
        rep = ch.transmit(APDU);
           if (rep.getSW()== 0x9000){
                System.out.println("ok : read AA00 ");
                System.out.println(new String(rep.getData()));}
           else 
                System.out.println("Nok : read AA00 " + Integer.toHexString(rep.getSW()));


          card.disconnect(true);
          } 
       }   
         
       
        private static String conv_hex(byte[] v){
             StringBuilder s = new StringBuilder();    
             for (byte b:v)
                 s.append(String.format("%02X", b));
             return s.toString();
         }   
}
