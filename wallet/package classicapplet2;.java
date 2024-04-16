package classicapplet2;

import javacard.framework.*;

public class ClassicApplet2 extends Applet {

    public final static byte CLA = (byte) 0x80;
    public final static byte INS_ACHAT = (byte) 0x00;
    public final static byte INS_RECH = (byte) 0x01;
    public final static byte INS_INI = (byte) 0x02;
    public final static byte INS_LIR = (byte) 0x03;
    public final static byte INS_MAJ_PIN = (byte) 0x05;
    private static final byte UNB = (byte) 0x04;
    
    public final static byte INS_DEBIT = (byte) 0xF0;
    private OwnerPIN BankPin;
    private static final byte[] init_BankPin = {0x01, 0x01, 0x01, 0x01};
    private short compteur = 1000;
    private OwnerPIN pin;
    public final static byte[] Initial = {0x00, 0x01, 0x02, 0x03};
    private static final byte SEL = (byte) 0xA4;
    private static final byte READ = (byte) 0xB2;
    
    private static final byte[] F000 = {0x04, 0x01, 0x01, 0x01, 0x01} ;
    private static final byte[] F001 = {0x04, 0x01, 0x01, 0x01, 0x01} ;
    private static final byte[] F002 = {0x04, 0x01, 0x01, 0x01, 0x01} ;
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new ClassicApplet2();
    }

    protected ClassicApplet2() {
        register();
        pin = new OwnerPIN((byte) 3, (byte) 4);
        pin.update(Initial, (short) 0, (byte) 4);
        BankPin = new OwnerPIN(Max_tries, Max_length);
        BankPin.update(init_BankPin, (short) 0, Max_length);
    }

    public void process(APDU apdu) {
        byte[] buffer = apdu.getBuffer();
        if (selectingApplet()) {
            return;
        }
        if (buffer[ISO7816.OFFSET_CLA] != CLA) {
            ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
        }
        switch (buffer[ISO7816.OFFSET_INS]) {
            case SEL:{
                byte m = buffer[ISO7816.OFFSET_LC];
                if (!pin.isValidated()){
                    ISOException.throwIt(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED);
                    return;
                }
                if (m == 0x02 ){
                    if (buffer[5] == 0xf0) {
                        switch (buffer[6]){
                            case  0x00:{
                                buffer = F000;
                                //apdu.setOutgoingAndSend((short)0,(short) 5);
                                return;
                            }
                            case  0x01:{
                                buffer = F001;
                                //apdu.setOutgoingAndSend((short)0,(short) 5);
                                return;
                            }
                            case  0x02:{
                                buffer = F002;
                                //apdu.setOutgoingAndSend((short)0,(short) 5);
                                return;
                            }
                            default:
                                ISOException.throwIt(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED);
                        }
                    }
                    
                    else{
                        ISOException.throwIt(ISO7816.SW_APPLET_SELECT_FAILED);
                    }
                }
                    
            }
            case VER:{
                switch(buffer[ISO7816.OFFSET_P1]){
                    case 1:{
                        if (!pin.check(buffer,(short) ISO7816.OFFSET_CDATA,Max_length))
                            ISOException.throwIt((short) (0x63C0 + pin.getTriesRemaining()));
                        return;
                    }
                    case 2:{
                        if (!BankPin.check(buffer,(short) ISO7816.OFFSET_CDATA,Max_length))
                            ISOException.throwIt((short) (0x63C0 + pin.getTriesRemaining()));
                        return;
                    } 
                }
            }
            case UNB:{
                if ( (!BankPin.isValidated())){
                    ISOException.throwIt(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED);
                    return;
                }
                pin.resetAndUnblock();
                return;
            }
            case INS_RECH:{
                byte m = buffer[ISO7816.OFFSET_P1];
                if ((!BankPin.isValidated()) || (!pin.isValidated())){
                    ISOException.throwIt(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED);
                    return;
                }
                if ((byte) (m+compteur) < (byte) 255){
                    compteur = (byte)(compteur + m );
                    return;}
                else
                    ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
            }
            case INS_ACHAT: {
                short debit = (short) ((buffer[ISO7816.OFFSET_P1] << 8) | (buffer[ISO7816.OFFSET_P2] & 0xFF));
                if (debit > compteur) {
                    ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
                } else {
                        if (debit > 200) {
                                       // Si le montant est supérieur à 200, vérifier d'abord le PIN
                                       if (!pin.check(buffer, ISO7816.OFFSET_CDATA, (byte) 0x04)) {
                                       ISOException.throwIt((short) (0x63C0 + pin.getTriesRemaining()));
                                        }
                                         else {
                                            // Sinon, débiter le montant du solde
                                            compteur -= debit;
                                        }}
                          else {
                                            // Sinon, débiter le montant du solde
                                            compteur -= debit;
                                        }
                break;
            }}
            
            case INS_INI: {
                compteur = 0;
                break;
            }
            case INS_LIR: {
                Util.setShort(buffer, (short) 0, compteur);
                apdu.setOutgoingAndSend((short) 0, (short) 2);
                break;
            }
            case INS_MAJ_PIN: {
                if (!pin.isValidated()) {
                    ISOException.throwIt(ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED);
                } else {
                    byte[] oldPIN = new byte[4];
                    byte[] newPIN = new byte[4];

                    Util.arrayCopy(buffer, ISO7816.OFFSET_CDATA, oldPIN, (short) 0, (short) 4);
                    Util.arrayCopy(buffer, (short) (ISO7816.OFFSET_CDATA + 4), newPIN, (short) 0, (short) 4);

                    if (pin.check(oldPIN, (short) 0, (byte) 4)) {
                        pin.update(newPIN, (short) 0, (byte) 4);
                    } else {
                        ISOException.throwIt((short) (ISO7816.SW_SECURITY_STATUS_NOT_SATISFIED + pin.getTriesRemaining()));
                    }
                }
                break;
            }
            default: {
                ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
            }
        }
    }
}
