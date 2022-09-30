package com.vzl;

public class DMA {
	private Bus bus;
	private static final int OAM_START_ADDRESS = 0xFE00;
	private int DMA;
	
	public void connectBus(Bus b) {
		this.bus = b;
	}
	
	public int read(int addr) {
		if(addr == 0xFF46) {
			return DMA;
		}
		return 00;
	}
	
	public void write(int addr, int data) {
		if(addr == 0xFF46) {
			DMA = data;
			enableDMA = true;
			if(data > 0xFD) {
				dmaAddr = ((data - 0x20) << 4) & 0xFF00;
			} else {
				dmaAddr = (data << 4) & 0xFF00;
			}
			return;
		}
	}
	
	public void tick() {
		if(enableDMA) {
			OAMDMATransfer();
		}
	}
	
	private boolean enableDMA = false;
	private int oamDelay = 4;
	private int dmaAddr = 0, oamIndex = 0;
	private void OAMDMATransfer() {
		if(oamDelay == 0) {
			if((oamIndex >= 0) && (oamIndex < 160)) {
				bus.write((OAM_START_ADDRESS + oamIndex), bus.read(dmaAddr));
				dmaAddr++;
				oamIndex++;
				if((dmaAddr & 0xFF) > 0x9F) {
					enableDMA = false;
					oamDelay = 4;
					oamIndex = 0;
					dmaAddr = 0;
				}
			}
		} else {
			oamDelay--;
		}
	}
}
