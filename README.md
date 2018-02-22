# Secure Cloud based Access Control and Optimization
## Final Year Project
## B. Tech Computer Science and Engineering 
## - Devashish Sood, SRM University, Jan 2016 
# Paper publication(Indian Journal of Science and Technology) http://www.indjst.org/index.php/indjst/article/view/102112

## Build the Project
```
mvn clean build
```
Deploy the jar in Tomcat


## Gist
Proof of Concept(PoC) to show how RSA can be used to store information in a decentralized cloud where the users can store and share data with other users. 
Public Key Encryption is used and users could take control of their own keys if they wanted. The cloud provider would thus be unable to decrypt users information on their own cloud.

Bob would be able to share data with Alice by knowing her public key and Eve would be unable to eavesdrop on the data or decrypt it. 
Only Alice would be able to decrypt the data using her private key.

The transfer of public keys can happen offline or through a secure channel created by Diffie Hellman technique or SSL.
Even if the public keys were shared publically users could communicate securely.


Once data has been shared, it cannot be unshared, thus the users own their data locally and this makes the model decentralized in nature.
Although, if the user updates the data, these updates won't be shared with the users unless they are explicitly given the data again.

The PoC was themed around sharing sensitive medical records of patients between doctors on a cloud, which would be a perfectly valid and legal business scenario.

## References 

Rivest Shamir Adleman(RSA) https://people.csail.mit.edu/rivest/Rsapaper.pdf
