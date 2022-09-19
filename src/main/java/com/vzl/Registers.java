package com.vzl;

public class Registers {
	enum RegisterType {
		RT_A, RT_F, RT_B, RT_C, RT_D, RT_E, RT_H, RT_L, RT_AF, RT_BC, RT_DE, RT_HL, RT_SP, RT_PC
	};

	private int A;
	private int B;
	private int C;
	private int D;
	private int E;
	private int H;
	private int L;
	private int F;	// Flags (z n h c)
	private int SP;	// Stack pointer
	private int PC;	// Program counter

	public Registers() {
		this.A = 0x01;
		this.B = 0x00;
		this.C = 0x13;
		this.D = 0x00;
		this.E = 0xD8;
		this.H = 0x01;
		this.L = 0x4D;
		this.F = 0xB0;
		this.SP = 0xFFFE;
		this.PC = 0x0100;
	}

	public int fetchRegisterData(RegisterType r) {
		switch (r) {
		case RT_A:
			return A;
		case RT_B:
			return B;
		case RT_C:
			return C;
		case RT_D:
			return D;
		case RT_E:
			return E;
		case RT_F:
			return F;
		case RT_H:
			return H;
		case RT_L:
			return L;
		case RT_AF:
			return Utils.to16bit(A, F);
		case RT_BC:
			return Utils.to16bit(B, C);
		case RT_DE:
			return Utils.to16bit(D, E);
		case RT_HL:
			return Utils.to16bit(H, L);
		case RT_SP:
			return SP;
		case RT_PC:
			return PC;
		default:
			System.out.println("Unknown register type for fetch");
			System.exit(3);
			return 0;
		}
	}

	public void updateRegisterData(RegisterType r, int data) {
		switch (r) {
		case RT_A:
			A = data;
			break;
		case RT_B:
			B = data;
			break;
		case RT_C:
			C = data;
			break;
		case RT_D:
			D = data;
			break;
		case RT_E:
			E = data;
			break;
		case RT_F:
			F = data & 0xF0; // lower 4 bits are always zero when updating F register
			break;
		case RT_H:
			H = data;
			break;
		case RT_L:
			L = data;
			break;
		case RT_AF:
			A = (data & 0xFF00) >> 8;
			F = data & 0xF0; // lower 4 bits are always zero when updating F register
			break;
		case RT_BC:
			B = (data & 0xFF00) >> 8;
			C = data & 0xFF;
			break;
		case RT_DE:
			D = (data & 0xFF00) >> 8;
			E = data & 0xFF;
			break;
		case RT_HL:
			H = (data & 0xFF00) >> 8;
			L = data & 0xFF;
			break;
		case RT_SP:
			SP = data;
			break;
		case RT_PC:
			PC = data;
			break;
		default:
			System.out.println("Unknown register type for update");
			System.exit(3);
			break;
		}
	}

	public void setFlags(int z, int n, int h, int c) {
		// 1 - set to 1; 0 - set to 0
		F = (z << 7) | (n << 6) | (h << 5) | (c << 4);
	}

	public String getRegistersStatus() {
		return String.format("A: %02X F: %02X B: %02X C: %02X D: %02X E: %02X H: %02X L: %02X SP: %04X PC: %04X", A, F,
				B, C, D, E, H, L, SP, PC);
	}

	public String getFlagsStatus() {
		return String.format("Z: %X, N: %X, H: %X, C: %X", ((F & 0x80) >> 7), ((F & 0x40) >> 6), ((F & 0x20) >> 5),
				((F & 0x10) >> 4));
	}
}
