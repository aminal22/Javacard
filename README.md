### Java Card Development Overview

Java Card technology brings numerous innovations to the realm of smart card development, offering enhanced interoperability, 
support for multiple applications on a single card, dynamic architecture for adding new applets, and robust security features.
Additionally, the smartcardio package provides classes for connecting to and interacting with Java Card-enabled smart cards.

#### Features of Java Card Technology:
1. Interopérabilité: Verified applets function across any Java Card platform.
2. Applications Multiples: Multiple applets can coexist on a single card.
3. Architecture Dynamique: New applets can be added post-card personalization.
4. Sécurité: Dedicated security concepts replace the inherent security of Java.

#### Smartcardio Package:
The smartcardio package facilitates the connection and communication with smart cards. The procedure typically involves the following steps:
1. **Connection and Activation**: The card reader establishes contact with the card and activates it.
2. **Reset**: The card sends its Answer To Reset (ATR).
3. **Exchange**: Data exchange between the reader and the card, initiated by the former.
4. **Disconnection**: The reader deactivates the connectors.

#### Practical Application:
In a hands-on exercise, an applet is defined to modify information stored in files FF02 and FF03, along with other manipulations.

#### Wallet Project:
The final project involves the creation of a wallet application where the user can conduct transactions.
If the transaction amount is less than 200, no password is required. However, for transactions exceeding 200, 
the user must enter a password. Additionally, the user can update the password. After three unsuccessful attempts, the card is blocked.


### Designing a Java Card Applet 

#### Architecture and Class Design:
- The architecture and classes of the applet are designed to ensure modularity, encapsulation, and extensibility.
- Each class within the applet serves a specific purpose and contributes to the overall functionality.
- Careful consideration is given to the interaction between different classes and how they collaborate to achieve the desired objectives.

#### `selectingApplet()` Method:
- The `selectingApplet()` method is utilized within the applet's `process()` method to differentiate the APDU command "select" from all other APDUs.
- This method plays a crucial role in handling the selection of the applet by the card or terminal.

#### `getShareableInterfaceObject()` Method:
- The `getShareableInterfaceObject()` method is invoked by the Java Card Runtime Environment (JCRE) to obtain a shareable interface object from the server applet for a client applet.
- This method facilitates communication between different applets, allowing them to share resources or exchange data securely.

### Designing a Java Card Applet

#### Defining the Interface:
- It is essential to define the interface between the applet and the client application (Card Acceptance Device/CAD or terminal).
- The communication between an applet and a CAD primarily involves a set of Application Protocol Data Units (APDUs).
- These APDU commands must be carefully designed and supported by both the applet and the client application.
- In our case, the INS (Instruction) part of the APDU will contain the following commands:

1. `READ`: Read data from the applet.
2. `WRITE`: Write data to the applet.
3. `VERIFY`: Verify a password or authentication token.
4. `UPDATE`: Update information stored in the applet.
5. `RESET`: Reset the applet or restore default settings.


Designing a Java Card applet involves creating a robust architecture, implementing essential methods such as `selectingApplet()` and `getShareableInterfaceObject()`, and defining a clear interface for communication with the client application. By carefully designing the communication protocol and supporting the necessary commands, seamless interaction between the applet and the client application can be ensured.


#### Conclusion:
Java Card technology offers a versatile platform for developing secure and interoperable smart card applications. The provided exercises 
and projects offer hands-on experience in leveraging Java Card capabilities for various use cases.
