package com.vzl;

import com.vzl.Instruction.ConditionType;
import com.vzl.Registers.RegisterType;

public class CPU {	
	static final long FREQ = 4194304L;
	
	enum State {
		RUNNING, HALT, STOP
	}
	private State currentState = State.RUNNING;
	private InterruptController ic;
	private Registers reg;
	
	public CPU(InterruptController ic) {
		this.ic = ic;
		this.reg = new Registers();
	}
	
	private int opcode = 0x00;
	private int cycles = 0;
	private Instruction currentInstruction;
	
	private boolean IME;
	private boolean enableIME;
	
	private Bus bus;
	
	void connectBus(Bus b) {
		this.bus = b;
	}
	
	public int read(int addr) {
		cycles++;
		return bus.read(addr);		
	}
	
	public void write(int addr, int data) {
		cycles++;
		bus.write(addr, data);
	}
	
	int run() {
		if(!(currentState == State.HALT)) {
			cycles = 0;
			fetch();
			if(enableIME) {
				IME = true;
				enableIME = false;
			}
			execute();		
			return cycles;
		} else {
			if(ic.pending()) {
				currentState = State.RUNNING;
			}
			return 0;
		}		
	}
	
	void handleInterrupts() {
		int IF = 0, IE = 0;
		if(IME) {
			IF = bus.read(0xFF0F);
			IE = bus.read(0xFFFF);			
			if (((IE & 0x1) & (IF & 0x1)) == 0x1) {					//VBlank
				bus.write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, (reg.fetchRegisterData(RegisterType.RT_PC) >> 8) & 0xFF); //high
				bus.write(reg.fetchRegisterData(RegisterType.RT_SP) - 2, reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF); //low
				reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) - 2));
				reg.updateRegisterData(RegisterType.RT_PC, 0x40);
				bus.write(0xFF0F, (IF & (~0x1 & 0xFF)) & 0xFF);
				IME = false;
			} else if (((IE & 0x2) & (IF & 0x2)) == 0x2) {			//LCD STAT
				bus.write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, (reg.fetchRegisterData(RegisterType.RT_PC) >> 8) & 0xFF); //high
				bus.write(reg.fetchRegisterData(RegisterType.RT_SP) - 2, reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF); //low
				reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) - 2));
				reg.updateRegisterData(RegisterType.RT_PC, 0x48);
				bus.write(0xFF0F, (IF & (~0x2 & 0xFF)) & 0xFF);
				IME = false;
			} else if (((IE & 0x4) & (IF & 0x4)) == 0x4) {			//Timer
				bus.write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, (reg.fetchRegisterData(RegisterType.RT_PC) >> 8) & 0xFF); //high
				bus.write(reg.fetchRegisterData(RegisterType.RT_SP) - 2, reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF); //low
				reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) - 2));
				reg.updateRegisterData(RegisterType.RT_PC, 0x50);
				bus.write(0xFF0F, (IF & (~0x4 & 0xFF)) & 0xFF);
				IME = false;
			} else if (((IE & 0x8) & (IF & 0x8)) == 0x8) {			//Serial
				bus.write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, (reg.fetchRegisterData(RegisterType.RT_PC) >> 8) & 0xFF); //high
				bus.write(reg.fetchRegisterData(RegisterType.RT_SP) - 2, reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF); //low
				reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) - 2));
				reg.updateRegisterData(RegisterType.RT_PC, 0x58);
				bus.write(0xFF0F, (IF & (~0x8 & 0xFF)) & 0xFF);
				IME = false;
			} else if (((IE & 0x10) & (IF & 0x10)) == 0x10) {		//Joypad
				bus.write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, (reg.fetchRegisterData(RegisterType.RT_PC) >> 8) & 0xFF); //high
				bus.write(reg.fetchRegisterData(RegisterType.RT_SP) - 2, reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF); //low
				reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) - 2));
				reg.updateRegisterData(RegisterType.RT_PC, 0x60);
				bus.write(0xFF0F, (IF & (~0x10 & 0xFF)) & 0xFF);
				IME = false;
			}
		}
	}
	
	public void fetch() {
		opcode = read(reg.fetchRegisterData(RegisterType.RT_PC));
		currentInstruction = InstructionSet.OPCODE_TABLE[opcode/16][opcode%16];
	}
	
	public void execute() {
		switch(currentInstruction.type) {
			case IN_NOP:execute_IN_NOP();break;
			case IN_JP:execute_IN_JP();break;
			case IN_INC:execute_IN_INC();break;
			case IN_LD:execute_IN_LD();break;
			case IN_SUB:execute_IN_SUB();break;
			case IN_SBC:execute_IN_SBC();break;
			case IN_DEC:execute_IN_DEC();break;
			case IN_JR:execute_IN_JR();break;
			case IN_CPL:execute_IN_CPL();break;
			case IN_CALL:execute_IN_CALL();break;
			case IN_XOR:execute_IN_XOR();break;
			case IN_DI:execute_IN_DI();break;
			case IN_LDH:execute_IN_LDH();break;
			case IN_CP:execute_IN_CP();break;
			case IN_OR:execute_IN_OR();break;
			case IN_RET:execute_IN_RET();break;
			case IN_EI:execute_IN_EI();break;
			case IN_AND:execute_IN_AND();break;
			case IN_CB:execute_IN_CB();break;
			case IN_RST:execute_IN_RST();break;
			case IN_ADD:execute_IN_ADD();break;
			case IN_ADC:execute_IN_ADC();break;
			case IN_POP:execute_IN_POP();break;
			case IN_PUSH:execute_IN_PUSH();break;
			case IN_RRA:execute_IN_RRA();break;
			case IN_RLA:execute_IN_RLA();break;
			case IN_CCF:execute_IN_CCF();break;
			case IN_SCF:execute_IN_SCF();break;
			case IN_DAA:execute_IN_DAA();break;
			case IN_RLCA:execute_IN_RLCA();break;
			case IN_RRCA:execute_IN_RRCA();break;
			case IN_RETI:execute_IN_RETI();break;
			case IN_HALT:execute_IN_HALT();break;
			case IN_STOP:execute_IN_STOP();break;
			case IN_NONE:
				System.err.printf("%02X - Not implemented",opcode);
				System.exit(1);
				break;
			default:
				System.err.printf("%02X - Invalid operation",opcode);
				System.exit(2);
				break;
		}
	}
	
	int x=0,y=0,result=0,flag=0;
	byte sb=0;
	
	private void execute_IN_NOP() {
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	}
	
	private void execute_IN_JP() {
		x=0;y=0;result=0;
		switch(currentInstruction.mode) {
			case AM_D16:
				x=read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				y=read(reg.fetchRegisterData(RegisterType.RT_PC) + 2);
				result=Utils.to16bit(y, x);
				if(currentInstruction.cond != ConditionType.CT_NONE) {
					//Conditional Jump
					if((currentInstruction.cond == ConditionType.CT_Z) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 7) & 0x1)  == 1)) {
						cycles++;
						reg.updateRegisterData(RegisterType.RT_PC, result);
					} else if((currentInstruction.cond == ConditionType.CT_NZ) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 7) & 0x1)  == 0)) {
						cycles++;
						reg.updateRegisterData(RegisterType.RT_PC, result);
					} else if((currentInstruction.cond == ConditionType.CT_C) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1)  == 1)) {
						cycles++;
						reg.updateRegisterData(RegisterType.RT_PC, result);
					} else if((currentInstruction.cond == ConditionType.CT_NC) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1)  == 0)) {
						cycles++;
						reg.updateRegisterData(RegisterType.RT_PC, result);
					} else {
						reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
					}
				} else {
					//Non conditional Jump
					reg.updateRegisterData(RegisterType.RT_PC, result);
					cycles++;
				}
				break;
			case AM_R:
				result=reg.fetchRegisterData(currentInstruction.reg1);
				reg.updateRegisterData(RegisterType.RT_PC, result);
				break;
			default:
				System.err.println("\tJP INVALID ADDRESS MODE");
				System.exit(1);
		}
	};
	
	private void execute_IN_INC() {
		x=0;y=1;flag=0;		
		switch(currentInstruction.mode) {
			case AM_R:
				x = reg.fetchRegisterData(currentInstruction.reg1);
				if(!(currentInstruction.reg1.toString()=="RT_BC" 
						|| currentInstruction.reg1.toString()=="RT_DE" 
						|| currentInstruction.reg1.toString()=="RT_HL"
						|| currentInstruction.reg1.toString()=="RT_SP")) {
					result = (x + y) & 0xFF;
					reg.setFlags(((result == 0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) ? 1 : 0), ((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1));
				} else {
					result = (x + y) & 0xFFFF;
					cycles++;
				}
				reg.updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR:
				x = read(reg.fetchRegisterData(currentInstruction.reg1));
				result = (x + y) & 0xFF;
				write(reg.fetchRegisterData(currentInstruction.reg1),result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) ? 1 : 0), ((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1));
				break;
			default:
				System.err.println("\tINC INVALID ADDRESS MODE");
				System.exit(1);
		}		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_XOR() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R_R:				
				y = (reg.fetchRegisterData(currentInstruction.reg2));
				break;
			case AM_R_MR:
				y = read(reg.fetchRegisterData(currentInstruction.reg2));
				break;
			case AM_R_D8:
				y = read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);				
				break;
			default:
				System.err.println("\tXOR INVALID ADDRESS MODE");
				System.exit(1);
		}
		result = (x ^ y) & 0xFF;
		reg.updateRegisterData(currentInstruction.reg1, result);
		reg.setFlags(((result == 0) ? 1 : 0), 0, 0, 0);
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_LD() {
		x=0;y=0;
		switch(currentInstruction.mode) {
			case AM_R_R:
				x = reg.fetchRegisterData(currentInstruction.reg2);
				reg.updateRegisterData(currentInstruction.reg1,x);
				break;
			case AM_R_D16:
				x=read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				y=read(reg.fetchRegisterData(RegisterType.RT_PC) + 2);
				reg.updateRegisterData(currentInstruction.reg1,Utils.to16bit(y, x));
				break;
			case AM_R_MR:
				x = reg.fetchRegisterData(currentInstruction.reg2);
				y=read(x);
				reg.updateRegisterData(currentInstruction.reg1,y);
				break;
			case AM_MR_R:
				x = reg.fetchRegisterData(currentInstruction.reg2);
				y = reg.fetchRegisterData(currentInstruction.reg1);
				write(y,x);
				break;
			case AM_R_D8:
				x = read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				reg.updateRegisterData(currentInstruction.reg1,x);
				break;
			case AM_MR_D8:
				x = read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				y = reg.fetchRegisterData(currentInstruction.reg1);
				write(y,x);
				break;
			case AM_A16_R:
				x = Utils.to16bit(read(reg.fetchRegisterData(RegisterType.RT_PC) + 2), read(reg.fetchRegisterData(RegisterType.RT_PC) + 1));
				y = reg.fetchRegisterData(currentInstruction.reg1);
				if(currentInstruction.reg1.toString() == "RT_SP") {
					write(x,(y & 0xFF)); //lo
					write(x+1,((y & 0xFF00) >> 8)); //hi
				} else {
					write(x,y);
				}
				break;
			case AM_R_A16:
				x = Utils.to16bit(read(reg.fetchRegisterData(RegisterType.RT_PC) + 2), read(reg.fetchRegisterData(RegisterType.RT_PC) + 1));
				y = read(x);
				reg.updateRegisterData(currentInstruction.reg1,y);
				break;
			case AM_HLD_R:
				x=reg.fetchRegisterData(currentInstruction.reg2);
				y=reg.fetchRegisterData(currentInstruction.reg1);
				write(y, x);
				reg.updateRegisterData(currentInstruction.reg1, y - 1);
				break;
			case AM_HLI_R:
				x=reg.fetchRegisterData(currentInstruction.reg2);
				y=reg.fetchRegisterData(currentInstruction.reg1);
				write(y, x);
				reg.updateRegisterData(currentInstruction.reg1, y + 1);
				break;
			case AM_R_HLI:
				x = reg.fetchRegisterData(currentInstruction.reg2);
				y=read(x);
				reg.updateRegisterData(currentInstruction.reg1,y);
				reg.updateRegisterData(currentInstruction.reg2,x + 1);
				break;
			case AM_R_HLD:
				x = reg.fetchRegisterData(currentInstruction.reg2);
				y=read(x);
				reg.updateRegisterData(currentInstruction.reg1,y);
				reg.updateRegisterData(currentInstruction.reg2,x - 1);
				break;
			case AM_HL_SP:
				x = reg.fetchRegisterData(currentInstruction.reg2);
				y = read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				result = (x + ((byte)y)) & 0xFFFF;
				int h = 0,c = 0;
				h = (((((x & 0xF) + ((y & 0xFF) & 0xF)) & 0x10) == 0x10) ? 1 : 0);
				c = (((x & 0xFF) + (y & 0xFF)) > 0xFF) ? 1 : 0;
				reg.setFlags(0, 0, h, c);
				reg.updateRegisterData(currentInstruction.reg1, result);
				break;
			default:
				System.err.println("\tLD INVALID ADDRESS MODE");
				System.exit(1);
		}
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_DEC() {
		x=0;y=1;flag=0;result=0;
		switch(currentInstruction.mode) {
			case AM_R:
				x = reg.fetchRegisterData(currentInstruction.reg1);
				if(!(currentInstruction.reg1.toString()=="RT_BC" 
						|| currentInstruction.reg1.toString()=="RT_DE" 
						|| currentInstruction.reg1.toString()=="RT_HL"
						|| currentInstruction.reg1.toString()=="RT_SP")) {
					result = (x - y) & 0xFF;
					reg.setFlags(((result == 0) ? 1 : 0), 1, (((x & 0xF) < (y & 0xF)) ? 1 : 0), ((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1));
				} else {
					result = (x - y) & 0xFFFF;
					cycles++;
				}				
				reg.updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR:
				x = read(reg.fetchRegisterData(currentInstruction.reg1));
				result = (x - y) & 0xFF;
				write(reg.fetchRegisterData(currentInstruction.reg1),result);
				reg.setFlags(((result == 0) ? 1 : 0), 1, (((x & 0xF) < (y & 0xF)) ? 1 : 0), ((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1));
				break;
			default:
				System.err.println("\tDEC INVALID ADDRESS MODE");
				System.exit(1);
		}
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_JR() {
		x=0;y=0;sb=0;
		switch(currentInstruction.mode) {
			case AM_D8:
				sb = (byte) read(reg.fetchRegisterData(RegisterType.RT_PC) + 1); //signed byte
				result = reg.fetchRegisterData(RegisterType.RT_PC) + sb + currentInstruction.length;
				if(currentInstruction.cond == ConditionType.CT_NONE) {
					reg.updateRegisterData(RegisterType.RT_PC, result);
					cycles++;
					break;
				} else {
					if((currentInstruction.cond == ConditionType.CT_NZ) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 7) & 0x1)  == 0)) {
						reg.updateRegisterData(RegisterType.RT_PC, result);
						cycles++;
						break;
					} else if((currentInstruction.cond == ConditionType.CT_Z) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 7) & 0x1)  == 1)) {
						reg.updateRegisterData(RegisterType.RT_PC, result);
						cycles++;
						break;
					} else if((currentInstruction.cond == ConditionType.CT_NC) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1)  == 0)) {
						reg.updateRegisterData(RegisterType.RT_PC, result);
						cycles++;
						break;
					} else if((currentInstruction.cond == ConditionType.CT_C) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1)  == 1)) {
						reg.updateRegisterData(RegisterType.RT_PC, result);
						cycles++;
						break;
					} else {
						reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
						break;
					}
				}
			default:
				System.err.println("\tJR INVALID ADDRESS MODE");
				System.exit(1);
		}
	};
	
	private void execute_IN_DI() {
		IME = false;
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_EI() {
		enableIME = true;
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_LDH() {
		x=0;y=0;result=0;
		switch(currentInstruction.mode) {
			case AM_A8_R:
				x=read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				result=(0xFF00+x) & 0xFFFF;
				write(result,reg.fetchRegisterData(currentInstruction.reg1));
				break;
			case AM_R_A8:
				x=read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				y=(0xFF00+x) & 0xFFFF;
				result=read(y);
				reg.updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR_R:
				x = reg.fetchRegisterData(currentInstruction.reg1);
				x = (0xFF00+x) & 0xFFFF;
				y = reg.fetchRegisterData(currentInstruction.reg2);
				write(x,y);
				break;
			case AM_R_MR:
				x = reg.fetchRegisterData(currentInstruction.reg2);
				x = (0xFF00+x) & 0xFFFF;
				y = read(x);
				reg.updateRegisterData(currentInstruction.reg1,y);
				break;
			default:
				System.err.println("\tLDH INVALID ADDRESS MODE");
				System.exit(1);
		}				
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_CP() {
		x=0;y=0;result=0;flag=0;
		x=reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R_D8:
				y=read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				break;
			case AM_R_R:
				y=reg.fetchRegisterData(currentInstruction.reg2);
				break;
			case AM_R_MR:
				y=read(reg.fetchRegisterData(currentInstruction.reg2));
				break;
			default:
				System.err.println("\tCP INVALID ADDRESS MODE");
				System.exit(1);
		}
		result=(x-y) & 0xFF;
		reg.setFlags(((result == 0) ? 1 : 0), 1, (((x & 0xF) < (y & 0xF)) ? 1 : 0), ((x < y) ? 1 : 0));		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_CALL() {
		x=0;y=0;result=0;
		x=read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
		y=read(reg.fetchRegisterData(RegisterType.RT_PC) + 2);
		result = Utils.to16bit(y, x);
		if(currentInstruction.cond == ConditionType.CT_NONE) {
			reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length)); //next PC(to be stored in stack)
			write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, ((reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF00) >> 8)); //high
			write(reg.fetchRegisterData(RegisterType.RT_SP) - 2, (reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF)); //low
			reg.updateRegisterData(RegisterType.RT_SP, reg.fetchRegisterData(RegisterType.RT_SP) - 2);
			reg.updateRegisterData(RegisterType.RT_PC, result);
			cycles++;
		} else {
			if((currentInstruction.cond == ConditionType.CT_NZ) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 7) & 0x1)  == 0)) {
				reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length)); //next PC(to be stored in stack)
				write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, ((reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF00) >> 8)); //high
				write(reg.fetchRegisterData(RegisterType.RT_SP) - 2, (reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF)); //low
				reg.updateRegisterData(RegisterType.RT_SP, reg.fetchRegisterData(RegisterType.RT_SP) - 2);
				reg.updateRegisterData(RegisterType.RT_PC, result);
				cycles++;
			} else if((currentInstruction.cond == ConditionType.CT_Z) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 7) & 0x1)  == 1)) {
				reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length)); //next PC(to be stored in stack)
				write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, ((reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF00) >> 8)); //high
				write(reg.fetchRegisterData(RegisterType.RT_SP) - 2, (reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF)); //low
				reg.updateRegisterData(RegisterType.RT_SP, reg.fetchRegisterData(RegisterType.RT_SP) - 2);
				reg.updateRegisterData(RegisterType.RT_PC, result);
				cycles++;
			} else if((currentInstruction.cond == ConditionType.CT_NC) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1)  == 0)) {
				reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length)); //next PC(to be stored in stack)
				write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, ((reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF00) >> 8)); //high
				write(reg.fetchRegisterData(RegisterType.RT_SP) - 2, (reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF)); //low
				reg.updateRegisterData(RegisterType.RT_SP, reg.fetchRegisterData(RegisterType.RT_SP) - 2);
				reg.updateRegisterData(RegisterType.RT_PC, result);
				cycles++;
			}else if((currentInstruction.cond == ConditionType.CT_C) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1)  == 1)) {
				reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length)); //next PC(to be stored in stack)
				write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, ((reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF00) >> 8)); //high
				write(reg.fetchRegisterData(RegisterType.RT_SP) - 2, (reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF)); //low
				reg.updateRegisterData(RegisterType.RT_SP, reg.fetchRegisterData(RegisterType.RT_SP) - 2);
				reg.updateRegisterData(RegisterType.RT_PC, result);
				cycles++;
			} else {
				reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
			}
		}		
	};
	
	private void execute_IN_OR() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R_R:
				y = reg.fetchRegisterData(currentInstruction.reg2);
				break;
			case AM_R_D8:
				y = read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				break;
			case AM_R_MR:
				y = read(reg.fetchRegisterData(currentInstruction.reg2));
				break;
			default:
				System.err.println("\tOR INVALID ADDRESS MODE");
				System.exit(1);
		}
		result = (x | y) & 0xFF;
		reg.updateRegisterData(currentInstruction.reg1, result);
		reg.setFlags(((result == 0) ? 1 : 0), 0, 0, 0);
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RET() {
		x=0;y=0;result=0;
		x=read(reg.fetchRegisterData(RegisterType.RT_SP)); //low
		y=read(reg.fetchRegisterData(RegisterType.RT_SP) + 1);//high
		result = Utils.to16bit(y, x);
		if(currentInstruction.cond == ConditionType.CT_NONE) {
			//SP=SP+2;
			//PC = result;
			reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) + 2));
			reg.updateRegisterData(RegisterType.RT_PC, result);
			cycles++;
		} else {
			if((currentInstruction.cond == ConditionType.CT_NZ) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 7) & 0x1)  == 0)) {
//				SP=SP+2;
//				PC = result;
				reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) + 2));
				reg.updateRegisterData(RegisterType.RT_PC, result);
				cycles+=2;
			} else if((currentInstruction.cond == ConditionType.CT_Z) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 7) & 0x1)  == 1)) {
//				SP=SP+2;
//				PC = result;
				reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) + 2));
				reg.updateRegisterData(RegisterType.RT_PC, result);
				cycles+=2;
			} else if((currentInstruction.cond == ConditionType.CT_NC) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1)  == 0)) {
//				SP=SP+2;
//				PC = result;
				reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) + 2));
				reg.updateRegisterData(RegisterType.RT_PC, result);
				cycles+=2;
			} else if((currentInstruction.cond == ConditionType.CT_C) && (((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1)  == 1)) {
//				SP=SP+2;
//				PC = result;
				reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) + 2));
				reg.updateRegisterData(RegisterType.RT_PC, result);
				cycles+=2;
			} else {
				reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
			}
		}
	};
	
	private void execute_IN_CPL() {
		flag=0;
		x=reg.fetchRegisterData(currentInstruction.reg1);
		reg.updateRegisterData(currentInstruction.reg1, ((~x) & 0xFF));
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
		reg.setFlags(((reg.fetchRegisterData(RegisterType.RT_F) >> 7) & 0x1), 1, 1, ((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1));
	};
	
	private void execute_IN_AND() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R_D8:
				y = read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);				
				break;
			case AM_R_R:
				y = (reg.fetchRegisterData(currentInstruction.reg2));
				break;
			case AM_R_MR:
				y = read(reg.fetchRegisterData(currentInstruction.reg2));
				break;
			default:
				System.err.println("\tAND INVALID ADDRESS MODE");
				System.exit(1);
		}
		
		result = (x & y) & 0xFF;
		reg.updateRegisterData(currentInstruction.reg1, result);
		reg.setFlags(((result==0) ? 1 : 0), 0, 1, 0);		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RST() {
		x=0;y=0;result=0;
		x=currentInstruction.param;
		result = 0x0000 + x;
		
		//next PC to be stored in stack
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
		write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, ((reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF00) >> 8)); //high
		write(reg.fetchRegisterData(RegisterType.RT_SP) - 2,(reg.fetchRegisterData(RegisterType.RT_PC) & 0xFF)); //low
		//SP=(SP-2);
		reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) - 2));
		//PC=result;
		reg.updateRegisterData(RegisterType.RT_PC, result);
		cycles++;
	};
	
	private void execute_IN_ADD() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R_R:				
				y = reg.fetchRegisterData(currentInstruction.reg2);
				if(!(currentInstruction.reg1.toString()=="RT_BC" 
						|| currentInstruction.reg1.toString()=="RT_DE" 
						|| currentInstruction.reg1.toString()=="RT_HL"
						|| currentInstruction.reg1.toString()=="RT_SP")) {
					result = (x + y) & 0xFF;
					reg.setFlags(((result==0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) ? 1 : 0), (((x + y) > 0xFF) ? 1 : 0));
				} else {
					result = (x + y) & 0xFFFF;
					reg.setFlags(((reg.fetchRegisterData(RegisterType.RT_F) >> 7) & 0x1), 0, (((((x & 0xFFF) + (y & 0xFFF)) & 0x1000) == 0x1000) ? 1 : 0), (((x + y) > 0xFFFF) ? 1 : 0));
					cycles++;
				}				
				reg.updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_MR:
				y = read(reg.fetchRegisterData(currentInstruction.reg2));
				result = (x + y) & 0xFF;
				reg.updateRegisterData(currentInstruction.reg1, result);
				reg.setFlags(((result==0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) ? 1 : 0), (((x + y) > 0xFF) ? 1 : 0));
				break;
			case AM_R_D8:
				y = read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				result = (x + y) & 0xFF;
				reg.updateRegisterData(currentInstruction.reg1, result);
				reg.setFlags(((result==0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) ? 1 : 0), (((x + y) > 0xFF) ? 1 : 0));
				break;
			case AM_R_A8:
				if(currentInstruction.reg1.toString()=="RT_SP") {
					y = read(reg.fetchRegisterData(RegisterType.RT_PC)+1);
					result = (x + ((byte)y)) & 0xFFFF;
					int h = 0,c = 0;
					h = (((((x & 0xF) + ((y & 0xFF) & 0xF)) & 0x10) == 0x10) ? 1 : 0);
					c = (((x & 0xFF) + (y & 0xFF)) > 0xFF) ? 1 : 0;
					reg.setFlags(0, 0, h, c);
					reg.updateRegisterData(currentInstruction.reg1, result);
				}
				break;
			default:
				System.err.println("\tADD INVALID ADDRESS MODE");
				System.exit(1);
		}		
		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_ADC() {
		x=0;y=0;flag=0;result=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R_R:				
				y = reg.fetchRegisterData(currentInstruction.reg2);
				break;
			case AM_R_MR:
				y = read(reg.fetchRegisterData(currentInstruction.reg2));
				break;
			case AM_R_D8:
				y = read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				break;
			default:
				System.err.println("\tADC INVALID ADDRESS MODE");
				System.exit(1);
		}		

		result = (x + y + ((reg.fetchRegisterData(RegisterType.RT_F) & 0x10) >> 4)) & 0xFF;
		reg.updateRegisterData(currentInstruction.reg1, result);
		reg.setFlags(((result==0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF) + ((reg.fetchRegisterData(RegisterType.RT_F) & 0x10) >> 4)) & 0x10) == 0x10) ? 1 : 0), (((x + y + ((reg.fetchRegisterData(RegisterType.RT_F) & 0x10) >> 4)) > 0xFF) ? 1 : 0));
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_SUB() {
		x=0;y=0;flag=0;result=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R_R:				
				y = reg.fetchRegisterData(currentInstruction.reg2);				
				break;
			case AM_R_MR:
				y = read(reg.fetchRegisterData(currentInstruction.reg2));
				break;
			case AM_R_D8:
				y = read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);				
				break;
			default:
				System.err.println("\tSUB INVALID ADDRESS MODE");
				System.exit(1);
		}
		result = (x - y) & 0xFF;
		reg.updateRegisterData(currentInstruction.reg1, result);
		reg.setFlags(((result==0) ? 1 : 0), 1, (((x & 0xF) < (y & 0xF)) ? 1 : 0), ((x < y) ? 1 : 0));		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_SBC() {
		x=0;y=0;flag=0;result=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R_R:
				y = reg.fetchRegisterData(currentInstruction.reg2);
				break;	
			case AM_R_MR:
				y = read(reg.fetchRegisterData(currentInstruction.reg2));
				break;
			case AM_R_D8:
				y = read(reg.fetchRegisterData(RegisterType.RT_PC) + 1);
				break;
			default:
				System.err.println("\tSBC INVALID ADDRESS MODE");
				System.exit(1);
		}
		result = (x - y - ((reg.fetchRegisterData(RegisterType.RT_F) & 0x10) >> 4)) & 0xFF;
		reg.updateRegisterData(currentInstruction.reg1, result);
		reg.setFlags(((result==0) ? 1 : 0), 1, (( ((x & 0xF) - (y & 0xF) - ((reg.fetchRegisterData(RegisterType.RT_F) & 0x10) >> 4)) < 0) ? 1 : 0), (( (x - y - ((reg.fetchRegisterData(RegisterType.RT_F) & 0x10) >> 4)) < 0) ? 1 : 0));
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_POP() {
		x=0;y=0;result=0;
		x=read(reg.fetchRegisterData(RegisterType.RT_SP)); //lo - F,C,E,L
		y=read(reg.fetchRegisterData(RegisterType.RT_SP) + 1);//hi - A,B,D,H
		result = Utils.to16bit(y, x);
		switch(currentInstruction.mode) {
			case AM_R:
				//SP=SP+2;
				reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) + 2));
				reg.updateRegisterData(currentInstruction.reg1, result);
				break;
			default:
				System.err.println("\tPOP INVALID ADDRESS MODE");
				System.exit(1);
		}
		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_PUSH() {
		cycles++;
		x=0;y=0;
		x=reg.fetchRegisterData(currentInstruction.reg1);
		y=(x & 0xFF00) >> 8; //hi - A,B,D,H
		x=x&0xFF;//lo - F,C,E,L		
		switch(currentInstruction.mode) {
			case AM_R:
				write(reg.fetchRegisterData(RegisterType.RT_SP) - 1, y); //high
				write(reg.fetchRegisterData(RegisterType.RT_SP) - 2, x); //low
				//SP=(SP-2);
				reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) - 2));
				break;
			default:
				System.err.println("\tPUSH INVALID ADDRESS MODE");
				System.exit(1);
		}
		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RRA() {
		x = reg.fetchRegisterData(RegisterType.RT_A);
		result = ((x >> 1) | ((reg.fetchRegisterData(RegisterType.RT_F) << 3) & 0x80)) & 0xFF;
		reg.setFlags(0, 0, 0, (x & 0x1));
		reg.updateRegisterData(RegisterType.RT_A,result);		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RLA() {
		x = reg.fetchRegisterData(RegisterType.RT_A);
		result = ((x << 1) | ((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1)) & 0xFF;
		reg.setFlags(0, 0, 0, ((x & 0x80) >> 7));
		reg.updateRegisterData(RegisterType.RT_A,result);		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_CCF() {
		flag = (reg.fetchRegisterData(RegisterType.RT_F) ^ 0x10) & 0x90; //z=unchanged,n=0,h=0,c=dependent
		//F = flag;
		reg.updateRegisterData(RegisterType.RT_F, flag);
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_SCF() {
		flag = (reg.fetchRegisterData(RegisterType.RT_F) | (1 << 4)) & 0x90; //z=unchanged,n=0,h=0,c=1
		//F = flag;
		reg.updateRegisterData(RegisterType.RT_F, flag);
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_DAA() {
		int correction = 0;
		int n = ((reg.fetchRegisterData(RegisterType.RT_F) >> 6) & 0x1);
		int h = ((reg.fetchRegisterData(RegisterType.RT_F) >> 5) & 0x1);
		int c = ((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1);
		
		int newCarryFlag = 0;
		x = reg.fetchRegisterData(RegisterType.RT_A);
		if((h == 0x1) || ( !(n == 0x1) && ((x & 0xF) > 9) )) {
			correction |= 0x6;
		}
		
		if((c == 0x1) || ( !(n == 0x1) && (x > 0x99) )) {
			correction |= 0x60;
			newCarryFlag = 1;
		}
		
		x += (n == 0x1) ? -correction: correction;
		x &= 0xFF;
		reg.updateRegisterData(RegisterType.RT_A,x);
		
		reg.setFlags( ((x == 0) ? 1 : 0), n, 0, newCarryFlag );		
		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RLCA() {
		x = reg.fetchRegisterData(RegisterType.RT_A);
		result = ((x << 1) | ((x & 0x80) >> 7)) & 0xFF;		
		reg.updateRegisterData(RegisterType.RT_A,result);
		reg.setFlags(0, 0, 0, ((x & 0x80) >> 7));
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RRCA() {
		x = reg.fetchRegisterData(RegisterType.RT_A);
		result = ((x >> 1) | ((x & 0x1) << 7)) & 0xFF;		
		reg.updateRegisterData(RegisterType.RT_A,result);
		reg.setFlags(0, 0, 0, (x & 0x1));
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RETI() {
		x=0;y=0;result=0;
		x=read(reg.fetchRegisterData(RegisterType.RT_SP)); //low
		y=read(reg.fetchRegisterData(RegisterType.RT_SP) + 1);//high
		result = Utils.to16bit(y, x);
		
		//SP=SP+2;
		reg.updateRegisterData(RegisterType.RT_SP, (reg.fetchRegisterData(RegisterType.RT_SP) + 2));
		//PC = result;
		reg.updateRegisterData(RegisterType.RT_PC, result);
		IME = true;
		cycles++;		
	};
	
	private void execute_IN_HALT() {
		currentState = State.HALT;
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	}
	
	private void execute_IN_STOP() {
		currentState = State.STOP;
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length) + 1);//skips next PC
	}
	
	//CB instructions	
	private void execute_IN_CB() {
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
		fetchCBInstruction();
		executeCBInstruction();
	};
	
	public void fetchCBInstruction() {
		int opcode = read(reg.fetchRegisterData(RegisterType.RT_PC));
		currentInstruction = InstructionSet.CB_OPCODE_TABLE[opcode/16][opcode%16];
	}
	
	public void executeCBInstruction() {
		int hi=0,lo=0,result=0;
		int a=0,b=0,flag=0;
		switch(currentInstruction.type) {
			case IN_SWAP:execute_IN_SWAP();break;
			case IN_RES:execute_IN_RES();break;
			case IN_SET:execute_IN_SET();break;
			case IN_SRL:execute_IN_SRL();break;
			case IN_RR:execute_IN_RR();break;
			case IN_RL:execute_IN_RL();break;
			case IN_RLC:execute_IN_RLC();break;
			case IN_RRC:execute_IN_RRC();break;
			case IN_SLA:execute_IN_SLA();break;
			case IN_SRA:execute_IN_SRA();break;
			case IN_BIT:execute_IN_BIT();break;
			case IN_NONE:
				System.err.print("CB Instruction not implemented");
				System.exit(2);
			default:
				System.err.print("CB Instruction invalid");
				System.exit(2);
		}
	}
	
	private void execute_IN_SWAP() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		//swaps upper 4 bits to lower
		switch(currentInstruction.mode) {
			case AM_R:
				y = (x & 0xF0) >> 4; //hi
				x = x & 0xF; //lo
				result = ((x << 4) | y) & 0xFF;
				reg.updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR:				
				y = read(x);
				x = y & 0xF; //lo
				y = (y & 0xF0) >> 4; //hi				
				result = ((x << 4) | y) & 0xFF;
				write(reg.fetchRegisterData(currentInstruction.reg1),result);
				break;
			default:
				System.err.println("\tSWAP INVALID ADDRESS MODE");
				System.exit(1);
		}				
		reg.setFlags(((result == 0) ? 1 : 0), 0, 0, 0);		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RES() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		//reset specified bit to 0
		switch(currentInstruction.mode) {
			case AM_R:
				y = currentInstruction.param;
				y = (~(1 << y)) & 0xFF;
				result = (x & y) & 0xFF;
				reg.updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR:				
				y = read(x);
				result = (((~(1 << currentInstruction.param)) & 0xFF) & y) & 0xFF;				
				write(x,result);
				break;
			default:
				System.err.println("\tRES INVALID ADDRESS MODE");
				System.exit(1);
		}		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_SET() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		//set specified bit to 1
		switch(currentInstruction.mode) {
			case AM_R:
				y = currentInstruction.param;
				y = (1 << y) & 0xFF;
				result = (x | y) & 0xFF;
				reg.updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR:
				y = read(x);
				result = (((1 << currentInstruction.param) & 0xFF) | y) & 0xFF;				
				write(x,result);
				break;
			default:
				System.err.println("\tSET INVALID ADDRESS MODE");
				System.exit(1);
		}		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_SRL() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R:				
				result = (x >> 1) & 0xFF;
				reg.updateRegisterData(currentInstruction.reg1, result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, (x & 0x1));
				break;
			case AM_MR:
				y = read(x);
				result = (y >> 1) & 0xFF;				
				write(x,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, (y & 0x1));
				break;
			default:
				System.err.println("\tSRL INVALID ADDRESS MODE");
				System.exit(1);
		}		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RR() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R:
				result = ((x >> 1) | ((reg.fetchRegisterData(RegisterType.RT_F) << 3) & 0x80)) & 0xFF;
				reg.updateRegisterData(currentInstruction.reg1,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, (x & 0x1));
				break;
			case AM_MR:				
				y = read(x);
				result = ((y >> 1) | ((reg.fetchRegisterData(RegisterType.RT_F) << 3) & 0x80)) & 0xFF;
				write(x,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, (y & 0x1));
				break;
			default:
				System.err.println("\tRR INVALID ADDRESS MODE");
				System.exit(1);
		}		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RL() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R:
				result = ((x << 1) | ((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1)) & 0xFF;
				reg.updateRegisterData(currentInstruction.reg1,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, ((x & 0x80) >> 7));
				break;
			case AM_MR:				
				y = read(x);
				result = ((y << 1) | ((reg.fetchRegisterData(RegisterType.RT_F) >> 4) & 0x1)) & 0xFF;
				write(x,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, ((y & 0x80) >> 7));
				break;
			default:
				System.err.println("\tRR INVALID ADDRESS MODE");
				System.exit(1);
		}		
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RLC() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R:
				result = ((x << 1) | ((x & 0x80) >> 7)) & 0xFF;	
				reg.updateRegisterData(currentInstruction.reg1,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, ((x & 0x80) >> 7));
				break;
			case AM_MR:;
				y = read(x);
				result = ((y << 1) | ((y & 0x80) >> 7)) & 0xFF;
				write(x,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, ((y & 0x80) >> 7));
				break;
			default:
				System.err.println("\tRLC INVALID ADDRESS MODE");
				System.exit(1);
		}
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_RRC() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R:
				result = ((x >> 1) | ((x & 0x1) << 7)) & 0xFF;	
				reg.updateRegisterData(currentInstruction.reg1,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, (x & 0x1));
				break;
			case AM_MR:
				y = read(x);
				result = ((y >> 1) | ((y & 0x1) << 7)) & 0xFF;
				write(x,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, (y & 0x1));
				break;
			default:
				System.err.println("\tRRC INVALID ADDRESS MODE");
				System.exit(1);
		}
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_SLA() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R:
				result = (x << 1) & 0xFF;
				reg.updateRegisterData(currentInstruction.reg1,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, ((x & 0x80) >> 7));
				break;
			case AM_MR:				
				y = read(x);
				result = (y << 1) & 0xFF;
				write(x,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, ((y & 0x80) >> 7));
				break;
			default:
				System.err.println("\tSLA INVALID ADDRESS MODE");
				System.exit(1);
		}
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_SRA() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R:				
				result = ((x & 0x80) | (x >> 1)) & 0xFF;
				reg.updateRegisterData(currentInstruction.reg1,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, (x & 0x1));
				break;
			case AM_MR:
				y = read(x);
				result = ((y & 0x80) | (y >> 1)) & 0xFF;
				write(x,result);
				reg.setFlags(((result == 0) ? 1 : 0), 0, 0, (y & 0x1));
				break;
			default:
				System.err.println("\tSRA INVALID ADDRESS MODE");
				System.exit(1);
		}
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
	
	private void execute_IN_BIT() {
		x=0;y=0;result=0;flag=0;
		x = reg.fetchRegisterData(currentInstruction.reg1);
		switch(currentInstruction.mode) {
			case AM_R:
				result = ~((x >> currentInstruction.param) & 0x1);
				break;
			case AM_MR:
				y = read(x);
				result = ~((y >> currentInstruction.param) & 0x1);				
				break;
			default:
				System.err.println("\tBIT INVALID ADDRESS MODE");
				System.exit(1);
		}
		reg.setFlags(result, 0, 1, ((reg.fetchRegisterData(RegisterType.RT_F) & 0x10) >> 4));
		reg.updateRegisterData(RegisterType.RT_PC, (reg.fetchRegisterData(RegisterType.RT_PC) + currentInstruction.length));
	};
}
