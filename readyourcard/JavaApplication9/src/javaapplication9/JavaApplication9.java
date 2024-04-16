package JavaApplication9 ;
import javax.smartcardio.*;
import java.io.UnsupportedEncodingException;
import java.util.List;



public class JavaApplication9 {

    private static byte [] APDU_ic ={(byte) 0x80,(byte) 0x20,(byte)0x07,(byte)0x00,(byte)0x08,0x41,0x43,
       0x4F,0x53,0x54,0x45,0x53,0x54};
    
    
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
        
    /*    Card card = null ; 
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
            }        
        
}*/
    
    }}
