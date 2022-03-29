package com.mycompany.dao;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.*;

import javax.xml.bind.*;


public class HashClass{
	public static String sha1(String input) {
		String sha = "bonjour";
		try {
			MessageDigest msgDigest = MessageDigest.getInstance("SHA-1");
			msgDigest.update(input.getBytes("UTF-8"), 0, input.length());
			sha = DatatypeConverter.printHexBinary(msgDigest.digest());
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			Logger.getLogger(HashClass.class.getName()).log(Level.SEVERE,null,e);
		}
		
		return sha;
	}
	
	public static void main(String[] args) {
		System.out.println(sha1("bonjour"));
		System.out.println(sha1("bonjour"));
		System.out.println(sha1("hello"));
	}
}
