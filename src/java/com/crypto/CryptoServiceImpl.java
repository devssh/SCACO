package com.crypto;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import com.crypto.HexSupport;

public class CryptoServiceImpl {
    private BigInteger n, d, e;

    private int bitlen = 1024;

    /** Create an instance that can encrypt using someone elses public key. */
    public CryptoServiceImpl(BigInteger newn, BigInteger newe) {
        n = newn;
        e = newe;
    }
    
    public CryptoServiceImpl(String newn, String newe,String newd) throws IOException{
        n = stringToBig(newn);
        e = stringToBig(newe);
        d = stringToBig(newd);
    }

    /** Create an instance that can both encrypt and decrypt. */
    public CryptoServiceImpl(int bits) {
        bitlen = bits;
        generateKeys();
    }

    /** Encrypt the given plaintext message. */
    public synchronized String encrypt(String message) throws IOException{
        return bigToString((new BigInteger(message.getBytes())).modPow(e, n));
    }

    /** Encrypt the given plaintext message. */
    public synchronized BigInteger encrypt(BigInteger message) {
        return message.modPow(e, n);
    }

    /** Decrypt the given ciphertext message. */
    public synchronized String decrypt(String message) throws IOException{
        return new String((stringToBig(message)).modPow(d, n).toByteArray());
    }

    public synchronized String decrypt(String message,BigInteger n,BigInteger d) throws IOException{
        return new String(stringToBig(message).modPow(d, n).toByteArray());
    }

    /** Decrypt the given ciphertext message. */
    public synchronized BigInteger decrypt(BigInteger message) {
        return message.modPow(d, n);
    }

    /** Generate a new public and private key set. */
    public synchronized void generateKeys() {
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bitlen / 2, 100, r);
        BigInteger q = new BigInteger(bitlen / 2, 100, r);
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
                .subtract(BigInteger.ONE));
        e = new BigInteger("3");
        while (m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        d = e.modInverse(m);

    }

    /** Return the modulus. */
    public synchronized BigInteger getN() {
        return n;
    }

    /** Return the public key. */
    public synchronized BigInteger getE() {
        return e;
    }
    
    public synchronized BigInteger getD() {
        return d;
    }

    public synchronized String bigToString(BigInteger big){

        return HexSupport.toHexFromBytes(big.toByteArray());
//        return new String(big.toByteArray(),"ISO-8859-1");
    }

    public synchronized BigInteger stringToBig(String str){
        return new BigInteger(HexSupport.toBytesFromHex(str));
//        return new BigInteger(str.getBytes("ISO-8859-1"));
    }

    public synchronized String[] storeKeys()throws IOException{
        String[] str=new String[3];

        str[0]=bigToString(n);
        str[1]=bigToString(e);
        str[2]=bigToString(d);

        return str;
    }
    
    


    
    
}