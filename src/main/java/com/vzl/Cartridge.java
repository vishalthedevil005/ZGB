package com.vzl;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cartridge {
	private int[] romBank00;
	private int[] romBankNN;
	private int[][] ramBank; // 8 KB External RAM. From cartridge, switchable bank if any
	
	private int noOfROMBanks = 0;
	private int romBankBitMask = 0;
	private int noOfRAMBanks = 0;
	
	private byte bankMode;
	private int romBankNum = 0;
	private byte ramBankNum = 0;
	private boolean ramAccessFlag;
	
	private static final Map<Integer, String> CART_TYPE = new HashMap<Integer, String>();
    static {
    	CART_TYPE.put(0x00, "ROM ONLY");
    	CART_TYPE.put(0x01, "MBC1");
    	CART_TYPE.put(0x02, "MBC1+RAM");
    	CART_TYPE.put(0x03, "MBC1+RAM+BATTERY");
    	CART_TYPE.put(0x05, "MBC2");
    	CART_TYPE.put(0x06, "MBC2+BATTERY");
    	CART_TYPE.put(0x08, "ROM+RAM");
    	CART_TYPE.put(0x09, "ROM+RAM+BATTERY");
    	CART_TYPE.put(0x0B, "MMM01");
    	CART_TYPE.put(0x0C, "MMM01+RAM");
    	CART_TYPE.put(0x0D, "MMM01+RAM+BATTERY");
    	CART_TYPE.put(0x0F, "MBC3+TIMER+BATTERY");
    	CART_TYPE.put(0x10, "MBC3+TIMER+RAM+BATTERY");
    	CART_TYPE.put(0x11, "MBC3");
    	CART_TYPE.put(0x12, "MBC3+RAM");
    	CART_TYPE.put(0x13, "MBC3+RAM+BATTERY");
    	CART_TYPE.put(0x19, "MBC5");
    	CART_TYPE.put(0x1A, "MBC5+RAM");
    	CART_TYPE.put(0x1B, "MBC5+RAM+BATTERY");
    	CART_TYPE.put(0x1C, "MBC5+RUMBLE");
    	CART_TYPE.put(0x1D, "MBC5+RUMBLE+RAM");
    	CART_TYPE.put(0x1E, "MBC5+RUMBLE+RAM+BATTERY");
    	CART_TYPE.put(0x20, "MBC6");
    	CART_TYPE.put(0x22, "MBC7+SENSOR+RUMBLE+RAM+BATTERY");
    	CART_TYPE.put(0xFC, "POCKET CAMERA");
    	CART_TYPE.put(0xFD, "BANDAI TAMA5");
    	CART_TYPE.put(0xFE, "HuC3");
    	CART_TYPE.put(0xFF, "HuC1+RAM+BATTERY");
    }
    
    private static final Map<String, String> NEW_LIC_CODE = new HashMap<String, String>();
    static {
    	NEW_LIC_CODE.put("00","None");
    	NEW_LIC_CODE.put("01","Nintendo R&D1");
    	NEW_LIC_CODE.put("08","Capcom");
    	NEW_LIC_CODE.put("13","Electronic Arts");
    	NEW_LIC_CODE.put("18","Hudson Soft");
    	NEW_LIC_CODE.put("19","b-ai");
    	NEW_LIC_CODE.put("20","kss");
    	NEW_LIC_CODE.put("22","pow");
    	NEW_LIC_CODE.put("24","PCM Complete");
    	NEW_LIC_CODE.put("25","san-x");
    	NEW_LIC_CODE.put("28","Kemco Japan");
    	NEW_LIC_CODE.put("29","seta");
    	NEW_LIC_CODE.put("30","Viacom");
    	NEW_LIC_CODE.put("31","Nintendo");
    	NEW_LIC_CODE.put("32","Bandai");
    	NEW_LIC_CODE.put("33","Ocean/Acclaim");
    	NEW_LIC_CODE.put("34","Konami");
    	NEW_LIC_CODE.put("35","Hector");
    	NEW_LIC_CODE.put("37","Taito");
    	NEW_LIC_CODE.put("38","Hudson");
    	NEW_LIC_CODE.put("39","Banpresto");
    	NEW_LIC_CODE.put("41","Ubi Soft");
    	NEW_LIC_CODE.put("42","Atlus");
    	NEW_LIC_CODE.put("44","Malibu");
    	NEW_LIC_CODE.put("46","angel");
    	NEW_LIC_CODE.put("47","Bullet-Proof");
    	NEW_LIC_CODE.put("49","irem");
    	NEW_LIC_CODE.put("50","Absolute");
    	NEW_LIC_CODE.put("51","Acclaim");
    	NEW_LIC_CODE.put("52","Activision");
    	NEW_LIC_CODE.put("53","American sammy");
    	NEW_LIC_CODE.put("54","Konami");
    	NEW_LIC_CODE.put("55","Hi tech entertainment");
    	NEW_LIC_CODE.put("56","LJN");
    	NEW_LIC_CODE.put("57","Matchbox");
    	NEW_LIC_CODE.put("58","Mattel");
    	NEW_LIC_CODE.put("59","Milton Bradley");
    	NEW_LIC_CODE.put("60","Titus");
    	NEW_LIC_CODE.put("61","Virgin");
    	NEW_LIC_CODE.put("64","LucasArts");
    	NEW_LIC_CODE.put("67","Ocean");
    	NEW_LIC_CODE.put("69","Electronic Arts");
    	NEW_LIC_CODE.put("70","Infogrames");
    	NEW_LIC_CODE.put("71","Interplay");
    	NEW_LIC_CODE.put("72","Broderbund");
    	NEW_LIC_CODE.put("73","sculptured");
    	NEW_LIC_CODE.put("75","sci");
    	NEW_LIC_CODE.put("78","THQ");
    	NEW_LIC_CODE.put("79","Accolade");
    	NEW_LIC_CODE.put("80","misawa");
    	NEW_LIC_CODE.put("83","lozc");
    	NEW_LIC_CODE.put("86","Tokuma Shoten Intermedia");
    	NEW_LIC_CODE.put("87","Tsukuda Original");
    	NEW_LIC_CODE.put("91","Chunsoft");
    	NEW_LIC_CODE.put("92","Video system");
    	NEW_LIC_CODE.put("93","Ocean/Acclaim");
    	NEW_LIC_CODE.put("95","Varie");
    	NEW_LIC_CODE.put("96","Yonezawa/s’pal");
    	NEW_LIC_CODE.put("97","Kaneko");
    	NEW_LIC_CODE.put("99","Pack in soft");
    	NEW_LIC_CODE.put("A4","Konami (Yu-Gi-Oh!)");
    }
	
	class CartridgeHeader {
		String title;				//0134-0143
		String manufacturerCode;	//013F-0142
		int cgbFlag;				//0143
		String newLicenseeCode;		//0144-0145
		int sgbFlag;				//0146
		int cartridgeType;			//0147
		int romSize;				//0148
		int ramSize;				//0149
		int destinationCode;		//014A
		int oldLicenseeCode;		//014B
		int maskRomVerNum;			//014C
		int headerChecksum;			//014D
		int globalChecksum;			//014E-014F		
	}
	
	private CartridgeHeader ch;
	private int[] romData;
	
	public Cartridge() {
		ch = new CartridgeHeader();
		ramAccessFlag = false;
	}
	
	public void loadCart(File f) {		
		DataInputStream stream = null;
		try {
			romData = new int[(int)f.length()];
			stream = new DataInputStream(new FileInputStream(f));
			int offset = 0;
			while (stream.available() > 0) {
				romData[offset] = stream.readUnsignedByte();
				offset++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
			}
		}
		
		parseCartridgeHeader();
		printCartInfo();
		
		//Checksum validation
		int x = 0;
		for(int i=0x0134;i<=0x014C;i++) {
			x = x - (romData[i] & 0xFF) - 1;
		}
		System.out.printf("\t Checksum : %02X (%s)", ch.headerChecksum, (romData[0x014D] == (x & 0xFF)) ? "PASSED" : "FAILED").println();
		
		initializeMemory();
	}
	
	public void parseCartridgeHeader() {
		String tmp = "";
		for(int i=0x134; i<=0x0143;i++) {
			tmp = tmp + Character.toString((char) (romData[i] & 0xFF));
		}
		ch.title = tmp;
		
		tmp = "";
		for(int i=0x13F; i<=0x0142;i++) {
			tmp = tmp + String.valueOf(romData[i] & 0xFF);
		}
		ch.manufacturerCode = tmp;
		
		ch.cgbFlag = (romData[0x0143] & 0xFF);		
		ch.newLicenseeCode = Character.toString((char) (romData[0x0144] & 0xFF)) + Character.toString((char) (romData[0x0145] & 0xFF));		
		ch.sgbFlag = (romData[0x0146] & 0xFF);		
		ch.cartridgeType = (romData[0x0147] & 0xFF);		
		ch.romSize = (romData[0x0148] & 0xFF);		
		ch.ramSize = (romData[0x0149] & 0xFF);		
		ch.destinationCode = (romData[0x014A] & 0xFF);		
		ch.oldLicenseeCode = (romData[0x014B] & 0xFF);		
		ch.maskRomVerNum = (romData[0x014C] & 0xFF);		
		ch.headerChecksum = (romData[0x014D] & 0xFF);		
		ch.globalChecksum = ((romData[0x014E] & 0xFF) << 8) + (romData[0x014F] & 0xFF);
	}
	
	void printCartInfo() {		
		System.out.println("Cartridge Loaded:");
		System.out.printf("\t Title    : %s", ch.title).println();
		System.out.printf("\t Type     : %02X (%s)", ch.cartridgeType, getCartTypeName()).println();
		System.out.printf("\t ROM Size : %02X (%d KB)", ch.romSize, getROMSize()).println();
		System.out.printf("\t RAM Size : %02X (%d KB)", ch.ramSize, getRAMSize()).println();
		System.out.printf("\t LIC Code : %s (%s)", getLicCode(), getLicName()).println();
		System.out.printf("\t ROM Vers : %02X", ch.maskRomVerNum).println();
	}
	
	String getCartTypeName() {
		String temp = CART_TYPE.get(ch.cartridgeType);
		return (temp != null) ? temp : "UNKNOWN";
	}
	
	String getLicName() {
		if(ch.oldLicenseeCode == 0x33) {
			String temp = NEW_LIC_CODE.get(ch.newLicenseeCode);
			return (temp != null) ? temp : "UNKNOWN";
		}
		return "OLD";
	}
	
	String getLicCode() {
		if(ch.oldLicenseeCode == 0x33) {
			return ch.newLicenseeCode;
		}
		return String.format("%02X",ch.oldLicenseeCode);		
	}
	
	int getROMSize() {
		return 32 << ch.romSize;
	}
	
	int getRAMSize() {
		switch(ch.ramSize) {
			case 0x00: return 0;
			case 0x01: return 2;
			case 0x02: return 8;
			case 0x03: return 32;
			case 0x04: return 128;
			case 0x05: return 64;
			default: return 0;
		}
	}
	
	public void initializeMemory() {
		noOfROMBanks = getROMSize() / 16;
		romBankBitMask = 0xFF >> (7 - (int)(Math.log(noOfROMBanks) / Math.log(2)));
		
		romBank00 = Arrays.copyOfRange(romData, 0x0000, 0x4000);
		romBankNN = Arrays.copyOfRange(romData, 0x4000, 0x8000);
		
		noOfRAMBanks = getRAMSize() / 8;
		if(noOfRAMBanks > 0) {
			ramBank =  new int[noOfRAMBanks][8 * 1024]; 	//switchable
		}
	}
	
	public void switchROMBank(int bankNum) {
		int start = 0x4000;
		int end = 0x8000;
		if((bankNum == 0x00) || (bankNum == 0x01)) {
			romBankNN = Arrays.copyOfRange(romData, start, end);
		} else {
			romBankNN = Arrays.copyOfRange(romData, start + ((bankNum - 1) * 0x4000), end + ((bankNum - 1) * 0x4000));
		}
	}
	
	public void switchRAMBank(int bankNum) {
		ramBankNum = (byte) (bankNum & 0xF);
	}
	
	public int read(int addr) {
		//return romData[addr];
		if(addr>=0x0000 && addr<=0x3FFF) {
			return romBank00[addr];
		}
		
		if(addr>=0x4000 && addr<=0x7FFF) {
			return romBankNN[addr - 0x4000];
		}
		
		if(addr>=0xA000 && addr<=0xBFFF) {
			return ramAccessFlag ? ramBank[ramBankNum][addr - 0xA000] : 0xFF;
		}
		
		return 0xFF;
	}
	
	public void write(int addr,int data) {
		//romData[addr] = data;
		
		//0000-1FFF - RAM enable
		if(addr>=0x0000 && addr<=0x1FFF) {
			//Enable RAM
			if((data & 0xF) == 0xA) {
				ramAccessFlag = true;
				return;
			}
			//Disable RAM
			if((data & 0xF) == 0x0) {
				ramAccessFlag = false;
				return;
			}
		}
		
		if(addr>=0x2000 && addr<=0x3FFF) {
			romBankNum = (data & 0x1F); //only first 5 bits
			if(romBankNum > noOfROMBanks && noOfROMBanks <= 32) {
				romBankNum &= romBankBitMask;
			}
			switchROMBank(romBankNum);
			return;
		}
		
		if(addr>=0x4000 && addr<=0x5FFF) {
			if(bankMode == 0x01) {
				switchRAMBank(data);
				return;
			}
			
			if(bankMode == 0x00) {
				if(ch.romSize > 0x04) {
					romBankNum = ((data << 5) & 0xFF) + romBankNum;
					switchROMBank(romBankNum);
					return;
				}
			}
		}
		
		if(addr>=0x6000 && addr<=0x7FFF) {
			bankMode = (byte) (data & 0xF);
			return;
		}
		
		if(addr>=0xA000 && addr<=0xBFFF) {
			if(ramAccessFlag) {
				ramBank[ramBankNum][addr - 0xA000] = data;
				return;
			}			
		}
	}
}
