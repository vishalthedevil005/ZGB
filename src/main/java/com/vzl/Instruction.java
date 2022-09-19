package com.vzl;

import com.vzl.Registers.RegisterType;

public class Instruction {
	enum AddressMode {
		AM_IMP, AM_R_D16, AM_R_R, AM_MR_R, AM_R, AM_R_D8, AM_R_MR, AM_R_HLI, AM_R_HLD, AM_HLI_R, AM_HLD_R, AM_R_A8,
		AM_A8_R, AM_HL_SP, AM_D16, AM_D8, AM_D16_R, AM_MR_D8, AM_MR, AM_A16_R, AM_R_A16
	};

	enum InstrType {
		IN_NONE, IN_NOP, IN_LD, IN_INC, IN_DEC, IN_RLCA, IN_ADD, IN_RRCA, IN_STOP, IN_RLA, IN_JR, IN_RRA, IN_DAA,
		IN_CPL, IN_SCF, IN_CCF, IN_HALT, IN_ADC, IN_SUB, IN_SBC, IN_AND, IN_XOR, IN_OR, IN_CP, IN_POP, IN_JP, IN_PUSH,
		IN_RET, IN_CB, IN_CALL, IN_RETI, IN_LDH, IN_JPHL, IN_DI, IN_EI, IN_RST, IN_ERR,
		// CB instructions...
		IN_RLC, IN_RRC, IN_RL, IN_RR, IN_SLA, IN_SRA, IN_SWAP, IN_SRL, IN_BIT, IN_RES, IN_SET
	};

	enum ConditionType {
		CT_NONE, CT_NZ, CT_Z, CT_NC, CT_C
	};

	InstrType type;
	AddressMode mode;
	RegisterType reg1;
	RegisterType reg2;
	ConditionType cond;
	int length;
	int param;

	public Instruction(InstrType type, AddressMode mode, RegisterType reg1, RegisterType reg2, ConditionType cond,
			int length, int param) {
		this.type = type;
		this.mode = mode;
		this.reg1 = reg1;
		this.reg2 = reg2;
		this.cond = cond;
		this.length = length;
		this.param = param;
	}

	@Override
	public String toString() {
		switch (mode) {
		case AM_IMP:
			return String.format("%s" + (param != 0 ? (String.format(" %02Xh", param)) : "")
					+ ((cond != null && cond != ConditionType.CT_NONE)
							? (String.format(" %s", cond.toString().substring(3)))
							: ""),
					type.toString().substring(3));
		case AM_D16:
			return String.format("%s " + ((cond != null && cond != ConditionType.CT_NONE)
					? (String.format("%s,", cond.toString().substring(3)))
					: "") + "u16", type.toString().substring(3));
		case AM_R:
			return String.format("%s %s", type.toString().substring(3), reg1.toString().substring(3));
		case AM_MR:
			return String.format("%s (%s)", type.toString().substring(3), reg1.toString().substring(3));
		case AM_R_R:
			return String.format("%s %s,%s", type.toString().substring(3), reg1.toString().substring(3),
					reg2.toString().substring(3));
		case AM_R_D16:
			return String.format("%s %s,u16", type.toString().substring(3), reg1.toString().substring(3));
		case AM_R_MR:
			return String.format("%s %s,(%s)", type.toString().substring(3), reg1.toString().substring(3),
					reg2.toString().substring(3));
		case AM_MR_R:
			return String.format("%s (" + ((type.toString() == "IN_LDH") ? "$FF00+" : "") + "%s),%s",
					type.toString().substring(3), reg1.toString().substring(3), reg2.toString().substring(3));
		case AM_R_D8:
			return String.format("%s %s,u8", type.toString().substring(3), reg1.toString().substring(3));
		case AM_D8:
			return String.format("%s " + ((cond != null && cond != ConditionType.CT_NONE)
					? (String.format("%s,", cond.toString().substring(3)))
					: "") + "i8", type.toString().substring(3));
		case AM_A8_R:
			return String.format("%s ($FF00+u8),%s", type.toString().substring(3), reg1.toString().substring(3));
		case AM_R_A8:
			return String.format("%s %s,($FF00+u8)", type.toString().substring(3), reg1.toString().substring(3));
		case AM_MR_D8:
			return String.format("%s (%s),u8", type.toString().substring(3), reg1.toString().substring(3));
		case AM_A16_R:
			return String.format("%s (u16),%s", type.toString().substring(3), reg1.toString().substring(3));
		case AM_R_A16:
			return String.format("%s %s,(u16)", type.toString().substring(3), reg1.toString().substring(3));
		case AM_HLI_R:
			return String.format("%s (%s+),%s", type.toString().substring(3), reg1.toString().substring(3),
					reg2.toString().substring(3));
		case AM_HLD_R:
			return String.format("%s (%s-),%s", type.toString().substring(3), reg1.toString().substring(3),
					reg2.toString().substring(3));
		case AM_R_HLI:
			return String.format("%s %s,(%s+)", type.toString().substring(3), reg1.toString().substring(3),
					reg2.toString().substring(3));
		case AM_R_HLD:
			return String.format("%s %s,(%s-)", type.toString().substring(3), reg1.toString().substring(3),
					reg2.toString().substring(3));
		default:
			return super.toString();
		}
	}
}
