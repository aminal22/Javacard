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



#### Conclusion:
Java Card technology offers a versatile platform for developing secure and interoperable smart card applications. The provided exercises 
and projects offer hands-on experience in leveraging Java Card capabilities for various use cases.
