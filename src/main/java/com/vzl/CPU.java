package com.vzl;

import com.vzl.Instruction.AddressMode;
import com.vzl.Instruction.ConditionType;
import com.vzl.Instruction.InstrType;
import com.vzl.Instruction.RegisterType;

public class CPU {	
	static final long FREQ = 4194304L;
	int opcode = 0x00;
	int cycles = 0;
	
	static final Instruction[][] OPCODE_TABLE = {
		{//0x
			new Instruction(InstrType.IN_NOP,AddressMode.AM_IMP,null,null,null,1,0),										//x0
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_D16,RegisterType.RT_BC,null,null,3,0),							//x1
			new Instruction(InstrType.IN_LD,AddressMode.AM_MR_R,RegisterType.RT_BC,RegisterType.RT_A,null,1,0),				//x2
			new Instruction(InstrType.IN_INC,AddressMode.AM_R,RegisterType.RT_BC,null,null,1,0),							//x3
			new Instruction(InstrType.IN_INC,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 							//x4
			new Instruction(InstrType.IN_DEC,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 							//x5
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_D8,RegisterType.RT_B,null,null,2,0), 							//x6
			new Instruction(InstrType.IN_RLCA,AddressMode.AM_IMP,null,null,null,1,0), 										//x7
			new Instruction(InstrType.IN_LD,AddressMode.AM_A16_R,RegisterType.RT_SP,null,null,3,0), 						//x8
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_HL,RegisterType.RT_BC,null,1,0), 			//x9
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_BC,null,1,0), 			//xA
			new Instruction(InstrType.IN_DEC,AddressMode.AM_R,RegisterType.RT_BC,null,null,1,0), 							//xB
			new Instruction(InstrType.IN_INC,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 							//xC
			new Instruction(InstrType.IN_DEC,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 							//xD
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_D8,RegisterType.RT_C,null,null,2,0), 							//xE
			new Instruction(InstrType.IN_RRCA,AddressMode.AM_IMP,null,null,null,1,0), 										//xF
		},
		{//1x
			new Instruction(InstrType.IN_STOP,AddressMode.AM_IMP,null,null,null,1,0), 										//x0
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_D16,RegisterType.RT_DE,null,null,3,0), 						//x1
			new Instruction(InstrType.IN_LD,AddressMode.AM_MR_R,RegisterType.RT_DE,RegisterType.RT_A,null,1,0), 			//x2
			new Instruction(InstrType.IN_INC,AddressMode.AM_R,RegisterType.RT_DE,null,null,1,0), 							//x3
			new Instruction(InstrType.IN_INC,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 							//x4
			new Instruction(InstrType.IN_DEC,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 							//x5
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_D8,RegisterType.RT_D,null,null,2,0), 							//x6
			new Instruction(InstrType.IN_RLA,AddressMode.AM_IMP,null,null,null,1,0), 										//x7
			new Instruction(InstrType.IN_JR,AddressMode.AM_D8,null,null,ConditionType.CT_NONE,2,0), 						//x8
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_HL,RegisterType.RT_DE,null,1,0), 			//x9
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_DE,null,1,0), 			//xA
			new Instruction(InstrType.IN_DEC,AddressMode.AM_R,RegisterType.RT_DE,null,null,1,0), 							//xB
			new Instruction(InstrType.IN_INC,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 							//xC
			new Instruction(InstrType.IN_DEC,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 							//xD
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_D8,RegisterType.RT_E,null,null,2,0), 							//xE
			new Instruction(InstrType.IN_RRA,AddressMode.AM_IMP,null,null,null,1,0), 										//xF
		},
		{//2x
			new Instruction(InstrType.IN_JR,AddressMode.AM_D8,null,null,ConditionType.CT_NZ,2,0), 							//x0
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_D16,RegisterType.RT_HL,null,null,3,0), 						//x1
			new Instruction(InstrType.IN_LD,AddressMode.AM_HLI_R,RegisterType.RT_HL,RegisterType.RT_A,null,1,0), 			//x2
			new Instruction(InstrType.IN_INC,AddressMode.AM_R,RegisterType.RT_HL,null,null,1,0), 							//x3
			new Instruction(InstrType.IN_INC,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 							//x4
			new Instruction(InstrType.IN_DEC,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 							//x5
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_D8,RegisterType.RT_H,null,null,2,0), 							//x6
			new Instruction(InstrType.IN_DAA,AddressMode.AM_IMP,null,null,null,1,0), 										//x7
			new Instruction(InstrType.IN_JR,AddressMode.AM_D8,null,null,ConditionType.CT_Z,2,0), 							//x8
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_HL,RegisterType.RT_HL,null,1,0), 			//x9
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_HLI,RegisterType.RT_A,RegisterType.RT_HL,null,1,0), 			//xA
			new Instruction(InstrType.IN_DEC,AddressMode.AM_R,RegisterType.RT_HL,null,null,1,0), 							//xB
			new Instruction(InstrType.IN_INC,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0),								//xC
			new Instruction(InstrType.IN_DEC,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 							//xD
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_D8,RegisterType.RT_L,null,null,2,0), 							//xE
			new Instruction(InstrType.IN_CPL,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0),								//xF
		},
		{//3x
			new Instruction(InstrType.IN_JR,AddressMode.AM_D8,null,null,ConditionType.CT_NC,2,0), 							//x0
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_D16,RegisterType.RT_SP,null,null,3,0), 						//x1
			new Instruction(InstrType.IN_LD,AddressMode.AM_HLD_R,RegisterType.RT_HL,RegisterType.RT_A,null,1,0), 			//x2
			new Instruction(InstrType.IN_INC,AddressMode.AM_R,RegisterType.RT_SP,null,null,1,0), 							//x3
			new Instruction(InstrType.IN_INC,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 							//x4
			new Instruction(InstrType.IN_DEC,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 							//x5
			new Instruction(InstrType.IN_LD,AddressMode.AM_MR_D8,RegisterType.RT_HL,null,null,2,0), 						//x6
			new Instruction(InstrType.IN_SCF,AddressMode.AM_IMP,null,null,null,1,0), 										//x7
			new Instruction(InstrType.IN_JR,AddressMode.AM_D8,null,null,ConditionType.CT_C,2,0), 							//x8
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_HL,RegisterType.RT_SP,null,0,0), 			//x9
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_HLD,RegisterType.RT_A,RegisterType.RT_HL,null,1,0), 			//xA
			new Instruction(InstrType.IN_DEC,AddressMode.AM_R,RegisterType.RT_SP,null,null,1,0), 							//xB
			new Instruction(InstrType.IN_INC,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 							//xC
			new Instruction(InstrType.IN_DEC,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 							//xD
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_D8,RegisterType.RT_A,null,null,2,0), 							//xE
			new Instruction(InstrType.IN_CCF,AddressMode.AM_IMP,null,null,null,1,0), 										//xF
		},
		{//4x
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_B,RegisterType.RT_B,null,1,0), 				//x0
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_B,RegisterType.RT_C,null,1,0), 				//x1
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_B,RegisterType.RT_D,null,1,0), 				//x2
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_B,RegisterType.RT_E,null,1,0), 				//x3
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_B,RegisterType.RT_H,null,1,0), 				//x4
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_B,RegisterType.RT_L,null,1,0), 				//x5
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_MR,RegisterType.RT_B,RegisterType.RT_HL,null,1,0), 			//x6
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_B,RegisterType.RT_A,null,1,0), 				//x7
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_C,RegisterType.RT_B,null,1,0), 				//x8
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_C,RegisterType.RT_C,null,1,0), 				//x9
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_C,RegisterType.RT_D,null,1,0), 				//xA
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_C,RegisterType.RT_E,null,1,0),				//xB
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_C,RegisterType.RT_H,null,1,0), 				//xC
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_C,RegisterType.RT_L,null,1,0), 				//xD
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_MR,RegisterType.RT_C,RegisterType.RT_HL,null,1,0), 			//xE
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_C,RegisterType.RT_A,null,1,0), 				//xF
		},
		{//5x
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_D,RegisterType.RT_B,null,1,0), 				//x0
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_D,RegisterType.RT_C,null,1,0), 				//x1
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_D,RegisterType.RT_D,null,1,0), 				//x2
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_D,RegisterType.RT_E,null,1,0),				//x3
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_D,RegisterType.RT_H,null,1,0), 				//x4
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_D,RegisterType.RT_L,null,1,0), 				//x5
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_MR,RegisterType.RT_D,RegisterType.RT_HL,null,1,0), 			//x6
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_D,RegisterType.RT_A,null,1,0), 				//x7
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_E,RegisterType.RT_B,null,1,0), 				//x8
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_E,RegisterType.RT_C,null,1,0), 				//x9
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_E,RegisterType.RT_D,null,1,0), 				//xA
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_E,RegisterType.RT_E,null,1,0), 				//xB
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_E,RegisterType.RT_H,null,1,0), 				//xC
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_E,RegisterType.RT_L,null,1,0), 				//xD
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_MR,RegisterType.RT_E,RegisterType.RT_HL,null,1,0), 			//xE
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_E,RegisterType.RT_A,null,1,0), 				//xF
		},
		{//6x
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_H,RegisterType.RT_B,null,1,0), 				//x0
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_H,RegisterType.RT_C,null,1,0), 				//x1
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_H,RegisterType.RT_D,null,1,0), 				//x2
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_H,RegisterType.RT_E,null,1,0), 				//x3
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_H,RegisterType.RT_H,null,1,0), 				//x4
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_H,RegisterType.RT_L,null,1,0), 				//x5
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_MR,RegisterType.RT_H,RegisterType.RT_HL,null,1,0), 			//x6
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_H,RegisterType.RT_A,null,1,0), 				//x7
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_L,RegisterType.RT_B,null,1,0), 				//x8
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_L,RegisterType.RT_C,null,1,0), 				//x9
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_L,RegisterType.RT_D,null,1,0), 				//xA
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_L,RegisterType.RT_E,null,1,0), 				//xB
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_L,RegisterType.RT_H,null,1,0), 				//xC
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_L,RegisterType.RT_L,null,1,0), 				//xD
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_MR,RegisterType.RT_L,RegisterType.RT_HL,null,1,0), 			//xE
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_L,RegisterType.RT_A,null,1,0), 				//xF
		},
		{//7x
			new Instruction(InstrType.IN_LD,AddressMode.AM_MR_R,RegisterType.RT_HL,RegisterType.RT_B,null,1,0), 			//x0
			new Instruction(InstrType.IN_LD,AddressMode.AM_MR_R,RegisterType.RT_HL,RegisterType.RT_C,null,1,0), 			//x1
			new Instruction(InstrType.IN_LD,AddressMode.AM_MR_R,RegisterType.RT_HL,RegisterType.RT_D,null,1,0), 			//x2
			new Instruction(InstrType.IN_LD,AddressMode.AM_MR_R,RegisterType.RT_HL,RegisterType.RT_E,null,1,0), 			//x3
			new Instruction(InstrType.IN_LD,AddressMode.AM_MR_R,RegisterType.RT_HL,RegisterType.RT_H,null,1,0), 			//x4
			new Instruction(InstrType.IN_LD,AddressMode.AM_MR_R,RegisterType.RT_HL,RegisterType.RT_L,null,1,0), 			//x5
			new Instruction(InstrType.IN_HALT,AddressMode.AM_IMP,null,null,null,1,0), 										//x6
			new Instruction(InstrType.IN_LD,AddressMode.AM_MR_R,RegisterType.RT_HL,RegisterType.RT_A,null,1,0), 			//x7
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_B,null,1,0), 				//x8
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_C,null,1,0), 				//x9
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_D,null,1,0), 				//xA
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_E,null,1,0), 				//xB
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_H,null,1,0), 				//xC
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_L,null,1,0), 				//xD
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_HL,null,1,0), 			//xE
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_A,null,1,0), 				//xF
		},
		{//8x
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_B,null,1,0), 				//x0
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_C,null,1,0), 				//x1
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_D,null,1,0), 				//x2
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_E,null,1,0), 				//x3
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_H,null,1,0), 				//x4
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_L,null,1,0), 				//x5
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_HL,null,1,0), 			//x6
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_A,null,1,0), 				//x7
			new Instruction(InstrType.IN_ADC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_B,null,1,0), 				//x8
			new Instruction(InstrType.IN_ADC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_C,null,1,0), 				//x9
			new Instruction(InstrType.IN_ADC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_D,null,1,0), 				//xA
			new Instruction(InstrType.IN_ADC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_E,null,1,0), 				//xB
			new Instruction(InstrType.IN_ADC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_H,null,1,0), 				//xC
			new Instruction(InstrType.IN_ADC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_L,null,1,0), 				//xD
			new Instruction(InstrType.IN_ADC,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_HL,null,1,0), 			//xE
			new Instruction(InstrType.IN_ADC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_A,null,1,0), 				//xF
		},
		{//9x
			new Instruction(InstrType.IN_SUB,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_B,null,1,0), 				//x0
			new Instruction(InstrType.IN_SUB,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_C,null,1,0), 				//x1
			new Instruction(InstrType.IN_SUB,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_D,null,1,0), 				//x2
			new Instruction(InstrType.IN_SUB,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_E,null,1,0), 				//x3
			new Instruction(InstrType.IN_SUB,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_H,null,1,0), 				//x4
			new Instruction(InstrType.IN_SUB,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_L,null,1,0), 				//x5
			new Instruction(InstrType.IN_SUB,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_HL,null,1,0), 			//x6
			new Instruction(InstrType.IN_SUB,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_A,null,1,0), 				//x7
			new Instruction(InstrType.IN_SBC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_B,null,1,0), 				//x8
			new Instruction(InstrType.IN_SBC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_C,null,1,0), 				//x9
			new Instruction(InstrType.IN_SBC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_D,null,1,0), 				//xA
			new Instruction(InstrType.IN_SBC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_E,null,1,0), 				//xB
			new Instruction(InstrType.IN_SBC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_H,null,1,0), 				//xC
			new Instruction(InstrType.IN_SBC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_L,null,1,0), 				//xD
			new Instruction(InstrType.IN_SBC,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_HL,null,1,0), 			//xE
			new Instruction(InstrType.IN_SBC,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_A,null,1,0), 				//xF
		},
		{//Ax
			new Instruction(InstrType.IN_AND,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_B,null,1,0), 				//x0
			new Instruction(InstrType.IN_AND,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_C,null,1,0), 				//x1
			new Instruction(InstrType.IN_AND,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_D,null,1,0), 				//x2
			new Instruction(InstrType.IN_AND,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_E,null,1,0), 				//x3
			new Instruction(InstrType.IN_AND,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_H,null,1,0), 				//x4
			new Instruction(InstrType.IN_AND,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_L,null,1,0), 				//x5
			new Instruction(InstrType.IN_AND,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_HL,null,1,0), 			//x6
			new Instruction(InstrType.IN_AND,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_A,null,1,0), 				//x7
			new Instruction(InstrType.IN_XOR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_B,null,1,0), 				//x8
			new Instruction(InstrType.IN_XOR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_C,null,1,0), 				//x9
			new Instruction(InstrType.IN_XOR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_D,null,1,0), 				//xA
			new Instruction(InstrType.IN_XOR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_E,null,1,0), 				//xB
			new Instruction(InstrType.IN_XOR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_H,null,1,0), 				//xC
			new Instruction(InstrType.IN_XOR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_L,null,1,0), 				//xD
			new Instruction(InstrType.IN_XOR,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_HL,null,1,0), 			//xE
			new Instruction(InstrType.IN_XOR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_A,null,1,0), 				//xF
		},
		{//Bx
			new Instruction(InstrType.IN_OR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_B,null,1,0), 				//x0
			new Instruction(InstrType.IN_OR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_C,null,1,0), 				//x1
			new Instruction(InstrType.IN_OR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_D,null,1,0), 				//x2
			new Instruction(InstrType.IN_OR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_E,null,1,0), 				//x3
			new Instruction(InstrType.IN_OR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_H,null,1,0), 				//x4
			new Instruction(InstrType.IN_OR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_L,null,1,0), 				//x5
			new Instruction(InstrType.IN_OR,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_HL,null,1,0), 			//x6
			new Instruction(InstrType.IN_OR,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_A,null,1,0), 				//x7
			new Instruction(InstrType.IN_CP,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_B,null,1,0), 				//x8
			new Instruction(InstrType.IN_CP,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_C,null,1,0), 				//x9
			new Instruction(InstrType.IN_CP,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_D,null,1,0), 				//xA
			new Instruction(InstrType.IN_CP,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_E,null,1,0), 				//xB
			new Instruction(InstrType.IN_CP,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_H,null,1,0), 				//xC
			new Instruction(InstrType.IN_CP,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_L,null,1,0), 				//xD
			new Instruction(InstrType.IN_CP,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_HL,null,1,0), 			//xE
			new Instruction(InstrType.IN_CP,AddressMode.AM_R_R,RegisterType.RT_A,RegisterType.RT_A,null,1,0), 				//xF
		},
		{//Cx
			new Instruction(InstrType.IN_RET,AddressMode.AM_IMP,null,null,ConditionType.CT_NZ,1,0), 						//x0
			new Instruction(InstrType.IN_POP,AddressMode.AM_R,RegisterType.RT_BC,null,null,1,0), 							//x1
			new Instruction(InstrType.IN_JP,AddressMode.AM_D16,null,null,ConditionType.CT_NZ,3,0), 							//x2
			new Instruction(InstrType.IN_JP,AddressMode.AM_D16,null,null,ConditionType.CT_NONE,3,0), 						//x3
			new Instruction(InstrType.IN_CALL,AddressMode.AM_D16,null,null,ConditionType.CT_NZ,3,0), 						//x4
			new Instruction(InstrType.IN_PUSH,AddressMode.AM_R,RegisterType.RT_BC,null,null,1,0), 							//x5
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_D8,RegisterType.RT_A,null,null,2,0), 							//x6
			new Instruction(InstrType.IN_RST,AddressMode.AM_IMP,null,null,null,1,0x00), 									//x7
			new Instruction(InstrType.IN_RET,AddressMode.AM_IMP,null,null,ConditionType.CT_Z,1,0), 							//x8
			new Instruction(InstrType.IN_RET,AddressMode.AM_IMP,null,null,ConditionType.CT_NONE,1,0), 						//x9
			new Instruction(InstrType.IN_JP,AddressMode.AM_D16,null,null,ConditionType.CT_Z,3,0), 							//xA
			new Instruction(InstrType.IN_CB,AddressMode.AM_IMP,null,null,null,1,0), 										//xB
			new Instruction(InstrType.IN_CALL,AddressMode.AM_D16,null,null,ConditionType.CT_Z,3,0), 						//xC
			new Instruction(InstrType.IN_CALL,AddressMode.AM_D16,null,null,ConditionType.CT_NONE,3,0), 						//xD
			new Instruction(InstrType.IN_ADC,AddressMode.AM_R_D8,RegisterType.RT_A,null,null,2,0), 							//xE
			new Instruction(InstrType.IN_RST,AddressMode.AM_IMP,null,null,null,1,0x08), 									//xF
		},
		{//Dx
			new Instruction(InstrType.IN_RET,AddressMode.AM_IMP,null,null,ConditionType.CT_NC,1,0), 						//x0
			new Instruction(InstrType.IN_POP,AddressMode.AM_R,RegisterType.RT_DE,null,null,1,0), 							//x1
			new Instruction(InstrType.IN_JP,AddressMode.AM_D16,null,null,ConditionType.CT_NC,3,0), 							//x2
			new Instruction(InstrType.IN_NONE,AddressMode.AM_IMP,null,null,null,0,0), //x3
			new Instruction(InstrType.IN_CALL,AddressMode.AM_D16,null,null,ConditionType.CT_NC,3,0), 						//x4
			new Instruction(InstrType.IN_PUSH,AddressMode.AM_R,RegisterType.RT_DE,null,null,1,0), 							//x5
			new Instruction(InstrType.IN_SUB,AddressMode.AM_R_D8,RegisterType.RT_A,null,null,2,0), 							//x6
			new Instruction(InstrType.IN_RST,AddressMode.AM_IMP,null,null,null,1,0x10), 									//x7
			new Instruction(InstrType.IN_RET,AddressMode.AM_IMP,null,null,ConditionType.CT_C,1,0), 							//x8
			new Instruction(InstrType.IN_RETI,AddressMode.AM_IMP,null,null,null,1,0), 										//x9
			new Instruction(InstrType.IN_JP,AddressMode.AM_D16,null,null,ConditionType.CT_C,3,0), 							//xA
			new Instruction(InstrType.IN_NONE,AddressMode.AM_IMP,null,null,null,0,0), //xB
			new Instruction(InstrType.IN_CALL,AddressMode.AM_D16,null,null,ConditionType.CT_C,3,0), 						//xC
			new Instruction(InstrType.IN_NONE,AddressMode.AM_IMP,null,null,null,0,0), //xD
			new Instruction(InstrType.IN_SBC,AddressMode.AM_R_D8,RegisterType.RT_A,null,null,2,0), 							//xE
			new Instruction(InstrType.IN_RST,AddressMode.AM_IMP,null,null,null,1,0x18), 									//xF
		},
		{//Ex
			new Instruction(InstrType.IN_LDH,AddressMode.AM_A8_R,RegisterType.RT_A,null,null,2,0), 							//x0
			new Instruction(InstrType.IN_POP,AddressMode.AM_R,RegisterType.RT_HL,null,null,1,0), 							//x1
			new Instruction(InstrType.IN_LDH,AddressMode.AM_MR_R,RegisterType.RT_C,RegisterType.RT_A,null,1,0), 			//x2
			new Instruction(InstrType.IN_NONE,AddressMode.AM_IMP,null,null,null,0,0), //x3
			new Instruction(InstrType.IN_NONE,AddressMode.AM_IMP,null,null,null,0,0), //x4
			new Instruction(InstrType.IN_PUSH,AddressMode.AM_R,RegisterType.RT_HL,null,null,1,0), 							//x5
			new Instruction(InstrType.IN_AND,AddressMode.AM_R_D8,RegisterType.RT_A,null,null,2,0), 							//x6
			new Instruction(InstrType.IN_RST,AddressMode.AM_IMP,null,null,null,1,0x20), 									//x7
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_A8,RegisterType.RT_SP,null,null,2,0), 						//x8
			new Instruction(InstrType.IN_JP,AddressMode.AM_R,RegisterType.RT_HL,null,ConditionType.CT_NONE,1,0), 			//x9
			new Instruction(InstrType.IN_LD,AddressMode.AM_A16_R,RegisterType.RT_A,null,null,3,0), 							//xA
			new Instruction(InstrType.IN_NONE,AddressMode.AM_IMP,null,null,null,0,0), //xB
			new Instruction(InstrType.IN_NONE,AddressMode.AM_IMP,null,null,null,0,0), //xC
			new Instruction(InstrType.IN_NONE,AddressMode.AM_IMP,null,null,null,0,0), //xD
			new Instruction(InstrType.IN_XOR,AddressMode.AM_R_D8,RegisterType.RT_A,null,null,2,0), 							//xE
			new Instruction(InstrType.IN_RST,AddressMode.AM_IMP,null,null,null,1,0x28), 									//xF			
		},
		{//Fx
			new Instruction(InstrType.IN_LDH,AddressMode.AM_R_A8,RegisterType.RT_A,null,null,2,0), 							//x0
			new Instruction(InstrType.IN_POP,AddressMode.AM_R,RegisterType.RT_AF,null,null,1,0), 							//x1
			new Instruction(InstrType.IN_LDH,AddressMode.AM_R_MR,RegisterType.RT_A,RegisterType.RT_C,null,1,0), 			//x2
			new Instruction(InstrType.IN_DI,AddressMode.AM_IMP,null,null,null,1,0), 										//x3
			new Instruction(InstrType.IN_NONE,AddressMode.AM_IMP,null,null,null,0,0), //x4
			new Instruction(InstrType.IN_PUSH,AddressMode.AM_R,RegisterType.RT_AF,null,null,1,0), 							//x5
			new Instruction(InstrType.IN_OR,AddressMode.AM_R_D8,RegisterType.RT_A,null,null,2,0), 							//x6
			new Instruction(InstrType.IN_RST,AddressMode.AM_IMP,null,null,null,1,0x30), 									//x7
			new Instruction(InstrType.IN_LD,AddressMode.AM_HL_SP,RegisterType.RT_HL,RegisterType.RT_SP,null,2,0), 			//x8
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_R,RegisterType.RT_SP,RegisterType.RT_HL,null,1,0), 			//x9
			new Instruction(InstrType.IN_LD,AddressMode.AM_R_A16,RegisterType.RT_A,null,null,3,0), 							//xA
			new Instruction(InstrType.IN_EI,AddressMode.AM_IMP,null,null,null,1,0), 										//xB
			new Instruction(InstrType.IN_NONE,AddressMode.AM_IMP,null,null,null,0,0), //xC
			new Instruction(InstrType.IN_NONE,AddressMode.AM_IMP,null,null,null,0,0), //xD
			new Instruction(InstrType.IN_CP,AddressMode.AM_R_D8,RegisterType.RT_A,null,null,2,0), 							//xE
			new Instruction(InstrType.IN_RST,AddressMode.AM_IMP,null,null,null,1,0x38), 									//xF
		}
	};
	
	//Registers
	private int A=0x01;
	private int B=0xFF;
	private int C=0x13;
	private int D=0x00;
	private int E=0xC1;
	private int H=0x84;
	private int L=0x83;
	private int F=0x00;	// Flags
	/* 
	 * Flag registers - lower 8bits of AF
		 Bit	Name	Explanation
		 7		z		Zero flag
		 6		n		Subtraction flag (BCD)
		 5		h		Half Carry flag (BCD)
		 4		c		Carry flag 	 */
	private int SP = 0xFFFE; // Stack pointer
	private int PC = 0x0100; // Program counter
	
	private boolean IME;
	
	private Bus bus;
	
	Instruction currentInstruction;
	
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
	
	public void printRegisters() {
		System.out.printf("[AF: %04X,",Utils.to16bit(A, F))
		.printf(" BC: %04X,",Utils.to16bit(B, C))
		.printf(" DE: %04X,",Utils.to16bit(D, E))
		.printf(" HL: %04X,",Utils.to16bit(H, L))
		.printf(" PC: %04X,",PC)
		.printf(" SP: %04X]",SP);
	}	
	
	public void printFlags() {
		System.out.printf("\t[Z: %X,", ((F & 0x80) >> 7))
		.printf(" N: %X,", ((F & 0x40) >> 6))
		.printf(" H: %X,", ((F & 0x20) >> 5))
		.printf(" C: %X]", ((F & 0x10) >> 4));
	}
	
	public String getRegistersStatus() {
		return String.format("[AF: %04X, BC: %04X, DE: %04X, HL: %04X, PC: %04X, SP: %04X]", Utils.to16bit(A, F), Utils.to16bit(B, C), Utils.to16bit(D, E), Utils.to16bit(H, L), PC, SP);
	}
	
	public String getFlagsStatus() {
		return String.format("[Z: %X, N: %X, H: %X, C: %X]", ((F & 0x80) >> 7), ((F & 0x40) >> 6), ((F & 0x20) >> 5), ((F & 0x10) >> 4));
	}
	
	public void fetch() {
		cycles = 0;
		opcode = read(PC);
		currentInstruction = OPCODE_TABLE[opcode/16][opcode%16];
//		System.out
//		//.printf("\t(%02X %02X %02X %02X)",read(PC),read(PC+1),read(PC+2),read(PC+3))
//		.printf("\t%s -> %s",Utils.intToHexString(opcode),currentInstruction.toString())
//		.println();
	}
	
	public void setFlags(int z, int n, int h, int c) {
		// 1 - set to 1; 0 - set to 0
		F = (z << 7) | (n << 6) | (h << 5) | (c << 4);
	}
	
	public void execute() {
		switch(currentInstruction.type) {
			case IN_NOP:
				iexec_IN_NOP.execute();
				break;
			case IN_JP:
				iexec_IN_JP.execute();
				break;
			case IN_INC:
				iexec_IN_INC.execute();
				break;
			case IN_LD:
				iexec_IN_LD.execute();
				break;
			case IN_SUB:
				iexec_IN_SUB.execute();
				break;
			case IN_SBC:
				iexec_IN_SBC.execute();
				break;
			case IN_DEC:
				iexec_IN_DEC.execute();
				break;
			case IN_JR:
				iexec_IN_JR.execute();
				break;
			case IN_CPL:
				iexec_IN_CPL.execute();
				break;
			case IN_CALL:
				iexec_IN_CALL.execute();
				break;
			case IN_XOR:
				iexec_IN_XOR.execute();
				break;
			case IN_DI:
				iexec_IN_DI.execute();
				break;
			case IN_LDH:
				iexec_IN_LDH.execute();
				break;
			case IN_CP:
				iexec_IN_CP.execute();
				break;
			case IN_OR:
				iexec_IN_OR.execute();
				break;
			case IN_RET:
				iexec_IN_RET.execute();
				break;
			case IN_EI:
				iexec_IN_EI.execute();
				break;
			case IN_AND:
				iexec_IN_AND.execute();
				break;
			case IN_CB:
				iexec_IN_CB.execute();
				break;
			case IN_RST:
				iexec_IN_RST.execute();
				break;
			case IN_ADD:
				iexec_IN_ADD.execute();
				break;
			case IN_ADC:
				iexec_IN_ADC.execute();
				break;
			case IN_POP:
				iexec_IN_POP.execute();
				break;
			case IN_PUSH:
				iexec_IN_PUSH.execute();
				break;
			case IN_RRA:
				iexec_IN_RRA.execute();
				break;
			case IN_RLA:
				iexec_IN_RLA.execute();
				break;
			case IN_CCF:
				iexec_IN_CCF.execute();
				break;
			case IN_SCF:
				iexec_IN_SCF.execute();
				break;
			case IN_DAA:
				iexec_IN_DAA.execute();
				break;
			case IN_RLCA:
				iexec_IN_RLCA.execute();
				break;
			case IN_RRCA:
				iexec_IN_RRCA.execute();
				break;
			case IN_HALT:
				System.err.println("\tHALT");				
				PC = PC + currentInstruction.length;
				break;
			case IN_STOP:
				//System.err.println("\tSTOP");				
				PC = PC + currentInstruction.length;
				PC++; //skips next PC
				break;
			case IN_NONE:
				System.err.printf("%02X - Not implemented",opcode);
				System.exit(1);
				break;
			default:
				System.err.printf("%02X - Invalid operation",opcode);
				System.exit(2);
				break;
		}
		//System.out.println("\tCycles: "+cycles);
	}
	
	public int fetchRegisterData(RegisterType r) {
		switch(r) {
			case RT_A:return A;
			case RT_B:return B;
			case RT_C:return C;
			case RT_D:return D;
			case RT_E:return E;
			case RT_F:return F;
			case RT_H:return H;
			case RT_L:return L;
			case RT_AF:return Utils.to16bit(A, F);
			case RT_BC:return Utils.to16bit(B, C);
			case RT_DE:return Utils.to16bit(D, E);
			case RT_HL:return Utils.to16bit(H, L);
			case RT_SP:return SP;
			default:System.out.println("Unknown register type for fetch");System.exit(3);return 0;
		}
	}
	
	public void updateRegisterData(RegisterType r, int data) {
		switch(r) {
			case RT_A:A=data;break;
			case RT_B:B=data;break;
			case RT_C:C=data;break;
			case RT_D:D=data;break;
			case RT_E:E=data;break;
			case RT_F:F=data&0xF0;break; //lower 4 bits are always zero when updating F register
			case RT_H:H=data;break;
			case RT_L:L=data;break;
			case RT_AF:A=(data&0xFF00)>>8;F=data&0xF0;break; //lower 4 bits are always zero when updating F register
			case RT_BC:B=(data&0xFF00)>>8;C=data&0xFF;break;
			case RT_DE:D=(data&0xFF00)>>8;E=data&0xFF;break;
			case RT_HL:H=(data&0xFF00)>>8;L=data&0xFF;break;
			case RT_SP:SP=data;break;
			default:System.out.println("Unknown register type for update");System.exit(3);break;
		}		
	}
	
	int x=0,y=0,result=0,flag=0;
	byte sb=0;
	InstructionExecutor iexec_IN_NOP = () -> {
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting NOP");
	};
	
	InstructionExecutor iexec_IN_JP = () -> {
		x=0;y=0;result=0;
		switch(currentInstruction.mode) {
			case AM_D16:
				x=read(PC+1);
				y=read(PC+2);
				result=Utils.to16bit(y, x);
				if(currentInstruction.cond != ConditionType.CT_NONE) {
					//Conditional Jump
					if((currentInstruction.cond == ConditionType.CT_Z) && (((F >> 7) & 0x1)  == 1)) {
						//System.out.println("\tExecuting JP Z,"+String.format("%04X", result));
						cycles++;
						PC=result;
					} else if((currentInstruction.cond == ConditionType.CT_NZ) && (((F >> 7) & 0x1)  == 0)) {
						//System.out.println("\tExecuting JP NZ,"+String.format("%04X", result));
						cycles++;
						PC=result;
					} else if((currentInstruction.cond == ConditionType.CT_C) && (((F >> 4) & 0x1)  == 1)) {
						//System.out.println("\tExecuting JP C,"+String.format("%04X", result));
						cycles++;
						PC=result;
					} else if((currentInstruction.cond == ConditionType.CT_NC) && (((F >> 4) & 0x1)  == 0)) {
						//System.out.println("\tExecuting JP NC,"+String.format("%04X", result));
						cycles++;
						PC=result;
					} else {
						//System.out.println("\tJP $"+String.format("%04X", result)+". " + currentInstruction.cond.toString().substring(3) +" CONDITION NOT MET. SKIPPING TO NEXT INSTRUCTION");
						PC = PC + currentInstruction.length;
					}
				} else {
					//Non conditional Jump
					PC = result;
					cycles++;
					//System.out.println("\tExecuting JP $"+String.format("%04X",result));
				}
				break;
			case AM_R:
				result=fetchRegisterData(currentInstruction.reg1);
				PC = result;
				//System.out.println("\tExecuting JP");
				break;
			default:
				System.err.println("\tJP INVALID ADDRESS MODE");
				System.exit(1);
		}
	};
	
	InstructionExecutor iexec_IN_INC = () -> {
		x=0;y=1;flag=0;
		switch(currentInstruction.mode) {
			case AM_R:
				x = fetchRegisterData(currentInstruction.reg1);				
				if(!(currentInstruction.reg1.toString()=="RT_BC" 
						|| currentInstruction.reg1.toString()=="RT_DE" 
						|| currentInstruction.reg1.toString()=="RT_HL"
						|| currentInstruction.reg1.toString()=="RT_SP")) {
					result = (x + y) & 0xFF;
//					if(result==0) {
//						flag = flag | (1 << 7);
//					}//z dependent
//					//n = 0	(flag = flag | (0 << 6))
//					if((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) {
//						flag = flag | (1 << 5);
//					}//h dependent
//					flag = flag | (F & 0x10);//c unchanged			
//					F = flag;
					setFlags(((result == 0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) ? 1 : 0), ((F >> 4) & 0x1));
				} else {
					result = (x + y) & 0xFFFF;
					cycles++;
				}
				updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR:
				x = read(fetchRegisterData(currentInstruction.reg1));
				result = (x + y) & 0xFF;
//				if(result==0) {
//					flag = flag | (1 << 7);
//				}//z dependent
//				//n = 0	(flag = flag | (0 << 6))
//				if((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) {
//					flag = flag | (1 << 5);
//				}//h dependent
//				flag = flag | (F & 0x10);//c unchanged			
//				F = flag;
				write(fetchRegisterData(currentInstruction.reg1),result);
				setFlags(((result == 0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) ? 1 : 0), ((F >> 4) & 0x1));
				break;
			default:
				System.err.println("\tINC INVALID ADDRESS MODE");
				System.exit(1);
		}		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting INC");
	};
	
	InstructionExecutor iexec_IN_XOR = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R_R:
				x = (fetchRegisterData(currentInstruction.reg1));
				y = (fetchRegisterData(currentInstruction.reg2));
				result = (x ^ y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_MR:
				x = (fetchRegisterData(currentInstruction.reg1));
				y = read(fetchRegisterData(currentInstruction.reg2));
				result = (x ^ y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_D8:
				x = (fetchRegisterData(currentInstruction.reg1));
				y = read(PC+1);
				result = (x ^ y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			default:
				System.err.println("\tXOR INVALID ADDRESS MODE");
				System.exit(1);
		}
//		if(result==0) {
//			flag = flag | (1 << 7);
//		}//z dependent, n=0,h=0,c=0		
//		F = flag;
		setFlags(((result == 0) ? 1 : 0), 0, 0, 0);
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting XOR");
	};
	
	InstructionExecutor iexec_IN_LD = () -> {
		x=0;y=0;
		switch(currentInstruction.mode) {
			case AM_R_R:
				x = fetchRegisterData(currentInstruction.reg2);
				updateRegisterData(currentInstruction.reg1,x);
				break;
			case AM_R_D16:
				x=read(PC+1);
				y=read(PC+2);
				updateRegisterData(currentInstruction.reg1,Utils.to16bit(y, x));
				break;
			case AM_R_MR:
				x = fetchRegisterData(currentInstruction.reg2);
				y=read(x);
				updateRegisterData(currentInstruction.reg1,y);
				break;
			case AM_MR_R:
				x = fetchRegisterData(currentInstruction.reg2);
				y = fetchRegisterData(currentInstruction.reg1);
				write(y,x);
				break;
			case AM_R_D8:
				x = read(PC+1);
				updateRegisterData(currentInstruction.reg1,x);
				break;
			case AM_MR_D8:
				x = read(PC+1);
				y = fetchRegisterData(currentInstruction.reg1);
				write(y,x);
				break;
			case AM_A16_R:
				x = Utils.to16bit(read(PC+2),read(PC+1));
				y = fetchRegisterData(currentInstruction.reg1);
				write(x,y);
				break;
			case AM_R_A16:
				x = Utils.to16bit(read(PC+2),read(PC+1));
				y = read(x);
				updateRegisterData(currentInstruction.reg1,y);
				break;
			case AM_HLD_R:
				x=fetchRegisterData(currentInstruction.reg2);
				y=fetchRegisterData(currentInstruction.reg1);
				write(y, x);
				updateRegisterData(currentInstruction.reg1, y - 1);
				break;
			case AM_HLI_R:
				x=fetchRegisterData(currentInstruction.reg2);
				y=fetchRegisterData(currentInstruction.reg1);
				write(y, x);
				updateRegisterData(currentInstruction.reg1, y + 1);
				break;
			case AM_R_HLI:
				x = fetchRegisterData(currentInstruction.reg2);
				y=read(x);
				updateRegisterData(currentInstruction.reg1,y);
				updateRegisterData(currentInstruction.reg2,x + 1);
				break;
			case AM_R_HLD:
				x = fetchRegisterData(currentInstruction.reg2);
				y=read(x);
				updateRegisterData(currentInstruction.reg1,y);
				updateRegisterData(currentInstruction.reg2,x - 1);
				break;
			case AM_HL_SP:
				x = fetchRegisterData(currentInstruction.reg2);
				sb = (byte) read(PC+1);
				updateRegisterData(currentInstruction.reg1, x + sb);
				break;
			default:
				System.err.println("\tLD INVALID ADDRESS MODE");
				System.exit(1);
		}
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting LD");
	};
	
	InstructionExecutor iexec_IN_DEC = () -> {
		x=0;y=1;flag=0;result=0;
		switch(currentInstruction.mode) {
			case AM_R:
				x = fetchRegisterData(currentInstruction.reg1);
				if(!(currentInstruction.reg1.toString()=="RT_BC" 
						|| currentInstruction.reg1.toString()=="RT_DE" 
						|| currentInstruction.reg1.toString()=="RT_HL"
						|| currentInstruction.reg1.toString()=="RT_SP")) {
					result = (x - y) & 0xFF;
//					if(result==0) {
//						flag = flag | (1 << 7);
//					}//z dependent
//					flag = flag | (1 << 6);//n=1
//					if(((x & 0xF) - (y & 0xF)) < 0) {
//						flag = flag | (1 << 5);
//					}//h dependent
//					flag = flag | (F & 0x10);//c unchanged				
//					F = flag;
					setFlags(((result == 0) ? 1 : 0), 1, (((x & 0xF) < (y & 0xF)) ? 1 : 0), ((F >> 4) & 0x1));
				} else {
					result = (x - y) & 0xFFFF;
					cycles++;
				}				
				updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR:
				x = read(fetchRegisterData(currentInstruction.reg1));
				result = (x - y) & 0xFF;
//				if(result==0) {
//					flag = flag | (1 << 7);
//				}//z dependent
//				flag = flag | (1 << 6);//n=1
//				if(((x & 0xF) - (y & 0xF)) < 0) {
//					flag = flag | (1 << 5);
//				}//h dependent
//				flag = flag | (F & 0x10);//c unchanged				
//				F = flag;
				write(fetchRegisterData(currentInstruction.reg1),result);
				setFlags(((result == 0) ? 1 : 0), 1, (((x & 0xF) < (y & 0xF)) ? 1 : 0), ((F >> 4) & 0x1));
				break;
			default:
				System.err.println("\tDEC INVALID ADDRESS MODE");
				System.exit(1);
		}
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting DEC");
	};
	
	InstructionExecutor iexec_IN_JR = () -> {
		x=0;y=0;sb=0;
		switch(currentInstruction.mode) {
			case AM_D8:
				sb = (byte) read(PC+1); //signed byte
				result = PC + sb + currentInstruction.length;
				if(currentInstruction.cond == ConditionType.CT_NONE) {					
					//System.out.println("Executing JR "+String.format("%04X", result));
					PC=result;
					cycles++;
					break;
				} else {
					if((currentInstruction.cond == ConditionType.CT_NZ) && (((F >> 7) & 0x1)  == 0)) {
						//System.out.println("\tExecuting JR NZ,"+String.format("%04X", result));
						PC=result;
						cycles++;
						break;
					} else if((currentInstruction.cond == ConditionType.CT_Z) && (((F >> 7) & 0x1)  == 1)) {
						//System.out.println("\tExecuting JR Z,"+String.format("%04X", result));
						PC=result;
						cycles++;
						break;
					} else if((currentInstruction.cond == ConditionType.CT_NC) && (((F >> 4) & 0x1)  == 0)) {
						//System.out.println("\tExecuting JR NC,"+String.format("%04X", result));
						PC=result;
						cycles++;
						break;
					} else if((currentInstruction.cond == ConditionType.CT_C) && (((F >> 4) & 0x1)  == 1)) {
						//System.out.println("\tExecuting JR C,"+String.format("%04X", result));
						PC=result;
						cycles++;
						break;
					} else {
						//System.out.println("\tJR $"+String.format("%04X", result)+". " + currentInstruction.cond.toString().substring(3) +" CONDITION NOT MET. SKIPPING TO NEXT INSTRUCTION");
						PC = PC + currentInstruction.length;
						break;
					}
				}
			default:
				System.err.println("\tJR INVALID ADDRESS MODE");
				System.exit(1);
		}
	};
	
	InstructionExecutor iexec_IN_DI = () -> {
		IME = false;
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting DI");
	};
	
	InstructionExecutor iexec_IN_EI = () -> {
		IME = true;
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting EI");
	};
	
	InstructionExecutor iexec_IN_LDH = () -> {
		x=0;y=0;result=0;
		switch(currentInstruction.mode) {
			case AM_A8_R:
				x=read(PC+1);
				result=(0xFF00+x) & 0xFFFF;
				write(result,fetchRegisterData(currentInstruction.reg1));
				break;
			case AM_R_A8:
				x=read(PC+1);
				y=(0xFF00+x) & 0xFFFF;
				result=read(y);
				updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR_R:
				x = fetchRegisterData(currentInstruction.reg1);
				x = (0xFF00+x) & 0xFFFF;
				y = fetchRegisterData(currentInstruction.reg2);
				write(x,y);
				break;
			case AM_R_MR:
				x = fetchRegisterData(currentInstruction.reg2);
				x = (0xFF00+x) & 0xFFFF;
				result = read(x);
				updateRegisterData(currentInstruction.reg1,result);				
				y = fetchRegisterData(currentInstruction.reg2);
				write(x,y);
				break;
			default:
				System.err.println("\tLDH INVALID ADDRESS MODE");
				System.exit(1);
		}				
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting LDH");
	};
	
	InstructionExecutor iexec_IN_CP = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R_D8:
				x=fetchRegisterData(currentInstruction.reg1);
				y=read(PC+1);
				result=(x-y) & 0xFF;
				break;
			case AM_R_R:
				x=fetchRegisterData(currentInstruction.reg1);
				y=fetchRegisterData(currentInstruction.reg2);
				result=(x-y) & 0xFF;
				break;
			case AM_R_MR:
				x=fetchRegisterData(currentInstruction.reg1);
				y=read(fetchRegisterData(currentInstruction.reg2));
				result=(x-y) & 0xFF;
				break;
			default:
				System.err.println("\tCP INVALID ADDRESS MODE");
				System.exit(1);
		}
		
//		if(result==0) {
//			flag = flag | (1 << 7);
//		}//z dependent
//		flag = flag | (1 << 6);//n=1
//		if(((x & 0xF) - (y & 0xF)) < 0) {
//			flag = flag | (1 << 5);
//		}//h dependent
//		if(x < y) {
//			flag = flag | (1 << 4);
//		}//c dependent				
//		F = flag;
		setFlags(((result == 0) ? 1 : 0), 1, (((x & 0xF) < (y & 0xF)) ? 1 : 0), ((x < y) ? 1 : 0));
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting CP");
	};
	
	InstructionExecutor iexec_IN_CALL = () -> {
		x=0;y=0;result=0;
		x=read(PC+1);
		y=read(PC+2);
		result = Utils.to16bit(y, x);
		if(currentInstruction.cond == ConditionType.CT_NONE) {
			PC=PC+currentInstruction.length; //next PC(to be stored in stack)
			write(SP-1,((PC & 0xFF00) >> 8)); //high
			write(SP-2,(PC & 0xFF)); //low
			SP=(SP-2);
			PC=result;
			cycles++;
			//System.out.println("\tExecuting CALL $"+String.format("%04X", result));
		} else {
			if((currentInstruction.cond == ConditionType.CT_NZ) && (((F >> 7) & 0x1)  == 0)) {
				PC=PC+currentInstruction.length; //next PC(to be stored in stack)
				write(SP-1,((PC & 0xFF00) >> 8)); //high
				write(SP-2,(PC & 0xFF)); //low
				SP=(SP-2);
				PC=result;
				cycles++;
				//System.out.println("\tExecuting CALL NZ,"+String.format("%04X", result));
			} else if((currentInstruction.cond == ConditionType.CT_Z) && (((F >> 7) & 0x1)  == 1)) {
				PC=PC+currentInstruction.length; //next PC(to be stored in stack)
				write(SP-1,((PC & 0xFF00) >> 8)); //high
				write(SP-2,(PC & 0xFF)); //low
				SP=(SP-2);
				PC=result;
				cycles++;
				//System.out.println("\tExecuting CALL Z,"+String.format("%04X", result));
			} else if((currentInstruction.cond == ConditionType.CT_NC) && (((F >> 4) & 0x1)  == 0)) {
				PC=PC+currentInstruction.length; //next PC(to be stored in stack)
				write(SP-1,((PC & 0xFF00) >> 8)); //high
				write(SP-2,(PC & 0xFF)); //low
				SP=(SP-2);
				PC=result;
				cycles++;
				//System.out.println("\tExecuting CALL NC,"+String.format("%04X", result));
			}else if((currentInstruction.cond == ConditionType.CT_C) && (((F >> 4) & 0x1)  == 1)) {
				PC=PC+currentInstruction.length; //next PC(to be stored in stack)
				write(SP-1,((PC & 0xFF00) >> 8)); //high
				write(SP-2,(PC & 0xFF)); //low
				SP=(SP-2);
				PC=result;
				cycles++;
				//System.out.println("\tExecuting CALL C,"+String.format("%04X", result));
			} else {
				//System.out.println("\tCALL $"+String.format("%04X", result)+". " + currentInstruction.cond.toString().substring(3) +" CONDITION NOT MET. SKIPPING TO NEXT INSTRUCTION");
				PC = PC + currentInstruction.length;
			}
		}		
	};
	
	InstructionExecutor iexec_IN_OR = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R_R:
				x = (fetchRegisterData(currentInstruction.reg1));
				y = (fetchRegisterData(currentInstruction.reg2));
				result = (x | y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_D8:
				x = (fetchRegisterData(currentInstruction.reg1));
				y = read(PC+1);
				result = (x | y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_MR:
				x = (fetchRegisterData(currentInstruction.reg1));
				y = read(fetchRegisterData(currentInstruction.reg2));
				result = (x | y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			default:
				System.err.println("\tOR INVALID ADDRESS MODE");
				System.exit(1);
		}
//		if(result==0) {
//			flag = flag | (1 << 7);
//		}//z dependent,n=0,h=0,c=0
//		F = flag;
		setFlags(((result == 0) ? 1 : 0), 0, 0, 0);
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting OR");
	};
	
	InstructionExecutor iexec_IN_RET = () -> {
		x=0;y=0;result=0;
		x=read(SP); //low
		y=read(SP+1);//high
		result = Utils.to16bit(y, x);
		if(currentInstruction.cond == ConditionType.CT_NONE) {
			SP=SP+2;
			PC = result;
			cycles++;
			//System.out.println("\tExecuting RET $"+String.format("%04X", result));
		} else {
			if((currentInstruction.cond == ConditionType.CT_NZ) && (((F >> 7) & 0x1)  == 0)) {
				SP=SP+2;
				PC = result;
				cycles+=2;
				//System.out.println("\tExecuting RET NZ,"+String.format("%04X", result));
			} else if((currentInstruction.cond == ConditionType.CT_Z) && (((F >> 7) & 0x1)  == 1)) {
				SP=SP+2;
				PC = result;
				cycles+=2;
				//System.out.println("\tExecuting RET Z,"+String.format("%04X", result));
			} else if((currentInstruction.cond == ConditionType.CT_NC) && (((F >> 4) & 0x1)  == 0)) {
				SP=SP+2;
				PC = result;
				cycles+=2;
				//System.out.println("\tExecuting RET NC,"+String.format("%04X", result));
			} else if((currentInstruction.cond == ConditionType.CT_C) && (((F >> 4) & 0x1)  == 1)) {
				SP=SP+2;
				PC = result;
				cycles+=2;
				//System.out.println("\tExecuting RET C,"+String.format("%04X", result));
			} else {
				//System.out.println("\tRET $"+String.format("%04X", result)+". " + currentInstruction.cond.toString().substring(3) +" CONDITION NOT MET. SKIPPING TO NEXT INSTRUCTION");
				PC = PC + currentInstruction.length;
			}
		}
	};
	
	InstructionExecutor iexec_IN_CPL = () -> {
		flag=0;
		x=fetchRegisterData(currentInstruction.reg1);
		updateRegisterData(currentInstruction.reg1, ((~x) & 0xFF));
		PC = PC + currentInstruction.length;
		
//		flag = (F & 0x80) | (1 << 6) | (1 << 5) | (F & 0x10); //z unchanged, n=1, h=1 and c unchanged
//		F = flag;
		setFlags(((F >> 7) & 0x1), 1, 1, ((F >> 4) & 0x1));
		
		//System.out.println("\tExecuting CPL");
	};
	
	InstructionExecutor iexec_IN_AND = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R_D8:
				x = (fetchRegisterData(currentInstruction.reg1));
				y = read(PC+1);
				result = (x & y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_R:
				x = (fetchRegisterData(currentInstruction.reg1));
				y = (fetchRegisterData(currentInstruction.reg2));
				result = (x & y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_MR:
				x = (fetchRegisterData(currentInstruction.reg1));
				y = read(fetchRegisterData(currentInstruction.reg2));
				result = (x & y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			default:
				System.err.println("\tAND INVALID ADDRESS MODE");
				System.exit(1);
		}
//		if(result==0) {
//			flag = flag | (1 << 7);
//		}//z dependent
//		//n=0 (flag = flag | (0 << 6))
//		flag = flag | (1 << 5); //h=1
//		//c=0 (flag = flag | (0 << 4))
//		F = flag;
		setFlags(((result==0) ? 1 : 0), 0, 1, 0);
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting AND");
	};
	
	InstructionExecutor iexec_IN_RST = () -> {
		x=0;y=0;result=0;
		x=currentInstruction.param;
		result = 0x0000 + x;
		
		//next PC to be stored in stack
		PC = PC + currentInstruction.length;
		write(SP-1,((PC & 0xFF00) >> 8)); //high
		write(SP-2,(PC & 0xFF)); //low
		SP=(SP-2);
		PC=result;
		cycles++;
		//System.out.println("\tExecuting RST $"+String.format("%04X", result));
	};
	
	InstructionExecutor iexec_IN_ADD = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R_R:
				x = fetchRegisterData(currentInstruction.reg1);
				y = fetchRegisterData(currentInstruction.reg2);
				if(!(currentInstruction.reg1.toString()=="RT_BC" 
						|| currentInstruction.reg1.toString()=="RT_DE" 
						|| currentInstruction.reg1.toString()=="RT_HL"
						|| currentInstruction.reg1.toString()=="RT_SP")) {
					result = (x + y) & 0xFF;
					//Update flags
//					if(result==0) {
//						flag = flag | (1 << 7);
//					}//z dependent
//					//n = 0 (flag = flag + (0 << 6))
//					if((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) {
//						flag = flag | (1 << 5);
//					}//h dependent					
//					if(result>0xFF) {
//						flag = flag | (1 << 4);
//					}//c dependent
//					
//					F = flag;
					setFlags(((result==0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) ? 1 : 0), (((x + y) > 0xFF) ? 1 : 0));
				} else {
					result = (x + y) & 0xFFFF;
//					//Update flags
//					flag = F & 0x80;//z unchanged
//					//n = 0 (flag = flag | (0 << 6))
//					if((((x & 0xFFF) + (y & 0xFFF)) & 0x100) == 0x100) {
//						flag = flag | (1 << 5);
//					}//h dependent					
//					if(result>0xFFFF) {
//						flag = flag | (1 << 4);
//					}//c dependent					
//					F = flag;
					setFlags(((F >> 7) & 0x1), 0, (((((x & 0xFFF) + (y & 0xFFF)) & 0x1000) == 0x1000) ? 1 : 0), (((x + y) > 0xFFFF) ? 1 : 0));
					cycles++;
				}				
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(fetchRegisterData(currentInstruction.reg2));
				result = (x + y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
//				//Update flags
//				if(result==0) {
//					flag = flag | (1 << 7);
//				}//z dependent
//				//n = 0 (flag = flag | (0 << 6))
//				if((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) {
//					flag = flag | (1 << 5);
//				}//h dependent				
//				if(result>0xFF) {
//					flag = flag | (1 << 4);
//				}//c dependent				
//				F = flag;
				setFlags(((result==0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) ? 1 : 0), (((x + y) > 0xFF) ? 1 : 0));
				break;
			case AM_R_D8:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(PC+1);
				result = (x + y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
//				//Update flags
//				if(result==0) {
//					flag = flag | (1 << 7);
//				}//z dependent
//				//n = 0 (flag = flag | (0 << 6))
//				if((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) {
//					flag = flag | (1 << 5);
//				}//h dependent				
//				if(result>0xFF) {
//					flag = flag | (1 << 4);
//				}//c dependent				
//				F = flag;
				setFlags(((result==0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) ? 1 : 0), (((x + y) > 0xFF) ? 1 : 0));
				break;
			default:
				System.err.println("\tADD INVALID ADDRESS MODE");
				System.exit(1);
		}		
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting ADD");
	};
	
	InstructionExecutor iexec_IN_ADC = () -> {
		x=0;y=0;flag=0;result=0;
		switch(currentInstruction.mode) {
			case AM_R_R:
				x = fetchRegisterData(currentInstruction.reg1);
				y = fetchRegisterData(currentInstruction.reg2);
				result = (x + y + ((F & 0x10) >> 4)) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(fetchRegisterData(currentInstruction.reg2));
				result = (x + y + ((F & 0x10) >> 4)) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_D8:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(PC+1);
				result = (x + y + ((F & 0x10) >> 4)) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			default:
				System.err.println("\tADC INVALID ADDRESS MODE");
				System.exit(1);
		}
		
//		//Update flags
//		if(result==0) {
//			flag = flag | (1 << 7);
//		}//z dependent
//		//n = 0 (flag = flag | (0 << 6))
//		if((((x & 0xF) + (y & 0xF)) & 0x10) == 0x10) {
//			flag = flag | (1 << 5);
//		}//h dependent		
//		if(result>0xFF) {
//			flag = flag | (1 << 4);
//		}//c dependent		
//		F = flag;
		setFlags(((result==0) ? 1 : 0), 0, (((((x & 0xF) + (y & 0xF) + ((F & 0x10) >> 4)) & 0x10) == 0x10) ? 1 : 0), (((x + y + ((F & 0x10) >> 4)) > 0xFF) ? 1 : 0));
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting ADC");
	};
	
	InstructionExecutor iexec_IN_SUB = () -> {
		x=0;y=0;flag=0;result=0;
		switch(currentInstruction.mode) {
			case AM_R_R:
				x = fetchRegisterData(currentInstruction.reg1);
				y = fetchRegisterData(currentInstruction.reg2);
				result = (x - y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(fetchRegisterData(currentInstruction.reg2));
				result = (x - y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_D8:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(PC+1);
				result = (x - y) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);				
				break;
			default:
				System.err.println("Not implemented");
				System.exit(3);
				break;
		}
//		//Update flags
//		if(result==0) {
//			flag = flag | (1 << 7);
//		}//z dependent						
//		flag = flag | (1 << 6);//n=1						
//		if((x & 0xF) < (y & 0xF)) {
//			flag = flag | (1 << 5);
//		}//h dependent						
//		if(x < y) {
//			flag = flag | (1 << 4);
//		}//c dependent						
//		F = flag;
		setFlags(((result==0) ? 1 : 0), 1, (((x & 0xF) < (y & 0xF)) ? 1 : 0), ((x < y) ? 1 : 0));
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting SUB");
	};
	
	InstructionExecutor iexec_IN_SBC = () -> {
		x=0;y=0;flag=0;result=0;
		switch(currentInstruction.mode) {
			case AM_R_R:
				x = fetchRegisterData(currentInstruction.reg1);
				y = fetchRegisterData(currentInstruction.reg2);
				result = (x - y - ((F & 0x10) >> 4)) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;	
			case AM_R_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(fetchRegisterData(currentInstruction.reg2));
				result = (x - y - ((F & 0x10) >> 4)) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			case AM_R_D8:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(PC+1);
				result = (x - y - ((F & 0x10) >> 4)) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				break;
			default:
				System.err.println("\tSBC INVALID ADDRESS MODE");
				System.exit(1);
		}
		
//		//Update flags
//		if(result==0) {
//			flag = flag | (1 << 7);
//		}//z dependent						
//		flag = flag + (1 << 6);//n=1						
//		if((x & 0xF) < (y & 0xF)) {
//			flag = flag | (1 << 5);
//		}//h dependent						
//		if(x < y) {
//			flag = flag | (1 << 4);
//		}//c dependent						
//		F = flag;
		setFlags(((result==0) ? 1 : 0), 1, (( ((x & 0xF) - (y & 0xF) - ((F & 0x10) >> 4)) < 0) ? 1 : 0), (( (x - y - ((F & 0x10) >> 4)) < 0) ? 1 : 0));
		
		
		PC = PC + currentInstruction.length;
		//System.out.println("Executing SBC");
	};
	
	InstructionExecutor iexec_IN_POP = () -> {
		x=0;y=0;result=0;
		x=read(SP); //lo - F,C,E,L
		y=read(SP+1);//hi - A,B,D,H
		result = Utils.to16bit(y, x);
		switch(currentInstruction.mode) {
			case AM_R:
				SP=SP+2;
				updateRegisterData(currentInstruction.reg1,result);
				break;
			default:
				System.err.println("\tPOP INVALID ADDRESS MODE");
				System.exit(1);
		}
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting POP");
	};
	
	InstructionExecutor iexec_IN_PUSH = () -> {
		cycles++;
		x=0;y=0;
		x=fetchRegisterData(currentInstruction.reg1);
		y=(x & 0xFF00) >> 8; //hi - A,B,D,H
		x=x&0xFF;//lo - F,C,E,L		
		switch(currentInstruction.mode) {
			case AM_R:
				write(SP-1,y); //high
				write(SP-2,x); //low
				SP=(SP-2);
				break;
			default:
				System.err.println("\tPUSH INVALID ADDRESS MODE");
				System.exit(1);
		}
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting PUSH");
	};
	
	InstructionExecutor iexec_IN_RRA = () -> {
		x = fetchRegisterData(RegisterType.RT_A);
		result = ((x >> 1) | ((F << 3) & 0x80)) & 0xFF;
		flag = (x & 0x1) << 4; //z=0,h=0,n=0,c=dependent
		F = flag;
		updateRegisterData(RegisterType.RT_A,result);
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting RRA");
	};
	
	InstructionExecutor iexec_IN_RLA = () -> {
		x = fetchRegisterData(RegisterType.RT_A);
		result = ((x << 1) | ((F >> 4) & 0x1)) & 0xFF;
		flag = (x & 0x80) >> 3; //z=0,h=0,n=0,c=dependent
		F = flag;
		updateRegisterData(RegisterType.RT_A,result);
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting RLA");
	};
	
	InstructionExecutor iexec_IN_CCF = () -> {
		flag = (F ^ 0x10) & 0x90; //z=unchanged,n=0,h=0,c=dependent
		F = flag;
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting CCF");
	};
	
	InstructionExecutor iexec_IN_SCF = () -> {
		flag = (F | (1 << 4)) & 0x90; //z=unchanged,n=0,h=0,c=1
		F = flag;
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting SCF");
	};
	
	InstructionExecutor iexec_IN_DAA = () -> {
//		private void Daa()
//        {
//            byte adjustment = 0;
//
//            if (carry || (A > 0x99 && !negative))
//            {
//                adjustment = 0x60;
//                carry = true;
//            }
//
//            if (halfCarry || ((A & 0x0f) > 0x09 && !negative))
//            {
//                adjustment += 0x06;
//            }
//
//            A += negative ? (byte)-adjustment : adjustment;
//
//            zero = A == 0;
//            halfCarry = false;
//        }
		
		
		
		
		
		int correction = 0;
		int n = ((F >> 6) & 0x1);
		int h = ((F >> 5) & 0x1);
		int c = ((F >> 4) & 0x1);
		
		int newCarryFlag = 0;
		x = fetchRegisterData(RegisterType.RT_A);
		if((h == 0x1) || ( !(n == 0x1) && ((x & 0xF) > 9) )) {
			correction |= 0x6;
		}
		
		if((c == 0x1) || ( !(n == 0x1) && (x > 0x99) )) {
			correction |= 0x60;
			newCarryFlag = 1;
		}
		
		x += (n == 0x1) ? -correction: correction;
		x &= 0xFF;
		updateRegisterData(RegisterType.RT_A,x);
		
		setFlags( ((x == 0) ? 1 : 0), n, 0, newCarryFlag );		
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting DAA");
	};
	
	InstructionExecutor iexec_IN_RLCA = () -> {
		x = fetchRegisterData(RegisterType.RT_A);
		result = (x << 1) | ((x & 0x80) >> 7);		
		updateRegisterData(RegisterType.RT_A,result);
		setFlags(0, 0, 0, ((x & 0x80) >> 7));
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting RLCA");
	};
	
	InstructionExecutor iexec_IN_RRCA = () -> {
		x = fetchRegisterData(RegisterType.RT_A);
		result = (x >> 1) | ((x & 0x1) << 7);		
		updateRegisterData(RegisterType.RT_A,result);
		setFlags(0, 0, 0, (x & 0x1));
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting RRCA");
	};
	
	//CB instructions	
	InstructionExecutor iexec_IN_CB = () -> {
		PC = PC + currentInstruction.length;
		fetchCBInstruction();
		executeCBInstruction();
	};
	
	static final Instruction[][] CB_OPCODE_TABLE = {
		{//0x
			new Instruction(InstrType.IN_RLC,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 				//x0
			new Instruction(InstrType.IN_RLC,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 				//x1
			new Instruction(InstrType.IN_RLC,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 				//x2
			new Instruction(InstrType.IN_RLC,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 				//x3
			new Instruction(InstrType.IN_RLC,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 				//x4
			new Instruction(InstrType.IN_RLC,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 				//x5
			new Instruction(InstrType.IN_RLC,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//x6
			new Instruction(InstrType.IN_RLC,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 				//x7
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_B,null,null,0,0), 				//x8
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_C,null,null,0,0), 				//x9
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_D,null,null,0,0), 				//xA
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_E,null,null,0,0), 				//xB
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_H,null,null,0,0), 				//xC
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_L,null,null,0,0), 				//xD
			new Instruction(InstrType.IN_RRC,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//xE
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_A,null,null,0,0), 				//xF
		},
		{//1x
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_B,null,null,0,0), 					//x0
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_C,null,null,0,0), 					//x1
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_D,null,null,0,0), 					//x2
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_E,null,null,0,0), 					//x3
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_H,null,null,0,0), 					//x4
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_L,null,null,0,0), 					//x5
			new Instruction(InstrType.IN_RL,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//x6
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_A,null,null,0,0), 					//x7
			new Instruction(InstrType.IN_RR,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 					//x8
			new Instruction(InstrType.IN_RR,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 					//x9
			new Instruction(InstrType.IN_RR,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 					//xA
			new Instruction(InstrType.IN_RR,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 					//xB
			new Instruction(InstrType.IN_RR,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 					//xC
			new Instruction(InstrType.IN_RR,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 					//xD
			new Instruction(InstrType.IN_RR,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//xE
			new Instruction(InstrType.IN_RR,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 					//xF
		},
		{//2x
			new Instruction(InstrType.IN_SLA,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 				//x0
			new Instruction(InstrType.IN_SLA,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 				//x1
			new Instruction(InstrType.IN_SLA,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 				//x2
			new Instruction(InstrType.IN_SLA,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 				//x3
			new Instruction(InstrType.IN_SLA,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 				//x4
			new Instruction(InstrType.IN_SLA,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 				//x5
			new Instruction(InstrType.IN_SLA,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//x6
			new Instruction(InstrType.IN_SLA,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 				//x7
			new Instruction(InstrType.IN_SRA,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 				//x8
			new Instruction(InstrType.IN_SRA,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 				//x9
			new Instruction(InstrType.IN_SRA,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 				//xA
			new Instruction(InstrType.IN_SRA,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 				//xB
			new Instruction(InstrType.IN_SRA,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 				//xC
			new Instruction(InstrType.IN_SRA,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 				//xD
			new Instruction(InstrType.IN_SRA,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//xE
			new Instruction(InstrType.IN_SRA,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 				//xF
		},
		{//3x
			new Instruction(InstrType.IN_SWAP,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 				//x0
			new Instruction(InstrType.IN_SWAP,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 				//x1
			new Instruction(InstrType.IN_SWAP,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 				//x2
			new Instruction(InstrType.IN_SWAP,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 				//x3
			new Instruction(InstrType.IN_SWAP,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 				//x4
			new Instruction(InstrType.IN_SWAP,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 				//x5
			new Instruction(InstrType.IN_SWAP,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//x6
			new Instruction(InstrType.IN_SWAP,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 				//x7
			new Instruction(InstrType.IN_SRL,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 				//x8
			new Instruction(InstrType.IN_SRL,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 				//x9
			new Instruction(InstrType.IN_SRL,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 				//xA
			new Instruction(InstrType.IN_SRL,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 				//xB
			new Instruction(InstrType.IN_SRL,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 				//xC
			new Instruction(InstrType.IN_SRL,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 				//xD
			new Instruction(InstrType.IN_SRL,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//xE
			new Instruction(InstrType.IN_SRL,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 				//xF
		},
		{//4x
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 				//x0
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 				//x1
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 				//x2
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 				//x3
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 				//x4
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 				//x5
			new Instruction(InstrType.IN_BIT,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//x6
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 				//x7
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_B,null,null,1,1), 				//x8
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_C,null,null,1,1), 				//x9
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_D,null,null,1,1), 				//xA
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_E,null,null,1,1), 				//xB
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_H,null,null,1,1), 				//xC
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_L,null,null,1,1), 				//xD
			new Instruction(InstrType.IN_BIT,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,1), 				//xE
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_A,null,null,1,1), 				//xF
		},
		{//5x
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_B,null,null,1,2), 				//x0
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_C,null,null,1,2), 				//x1
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_D,null,null,1,2), 				//x2
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_E,null,null,1,2), 				//x3
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_H,null,null,1,2), 				//x4
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_L,null,null,1,2), 				//x5
			new Instruction(InstrType.IN_BIT,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,2), 				//x6
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_A,null,null,1,2), 				//x7
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_B,null,null,1,3), 				//x8
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_C,null,null,1,3), 				//x9
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_D,null,null,1,3), 				//xA
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_E,null,null,1,3), 				//xB
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_H,null,null,1,3), 				//xC
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_L,null,null,1,3), 				//xD
			new Instruction(InstrType.IN_BIT,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,3), 				//xE
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_A,null,null,1,3), 				//xF
		},
		{//6x
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_B,null,null,1,4), 				//x0
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_C,null,null,1,4), 				//x1
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_D,null,null,1,4), 				//x2
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_E,null,null,1,4), 				//x3
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_H,null,null,1,4), 				//x4
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_L,null,null,1,4), 				//x5
			new Instruction(InstrType.IN_BIT,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,4), 				//x6
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_A,null,null,1,4), 				//x7
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_B,null,null,1,5), 				//x8
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_C,null,null,1,5), 				//x9
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_D,null,null,1,5), 				//xA
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_E,null,null,1,5), 				//xB
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_H,null,null,1,5), 				//xC
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_L,null,null,1,5), 				//xD
			new Instruction(InstrType.IN_BIT,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,5), 				//xE
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_A,null,null,1,5), 				//xF
		},
		{//7x
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_B,null,null,1,6), 				//x0
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_C,null,null,1,6), 				//x1
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_D,null,null,1,6), 				//x2
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_E,null,null,1,6), 				//x3
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_H,null,null,1,6), 				//x4
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_L,null,null,1,6), 				//x5
			new Instruction(InstrType.IN_BIT,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,6), 				//x6
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_A,null,null,1,6), 				//x7
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_B,null,null,1,7), 				//x8
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_C,null,null,1,7), 				//x9
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_D,null,null,1,7), 				//xA
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_E,null,null,1,7), 				//xB
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_H,null,null,1,7), 				//xC
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_L,null,null,1,7), 				//xD
			new Instruction(InstrType.IN_BIT,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,7), 				//xE
			new Instruction(InstrType.IN_BIT,AddressMode.AM_R,RegisterType.RT_A,null,null,1,7), 				//xF
		},
		{//8x
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 				//x0
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 				//x1
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 				//x2
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 				//x3
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 				//x4
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 				//x5
			new Instruction(InstrType.IN_RES,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//x6
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 				//x7
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_B,null,null,1,1), 				//x8
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_C,null,null,1,1), 				//x9
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_D,null,null,1,1), 				//xA
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_E,null,null,1,1), 				//xB
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_H,null,null,1,1), 				//xC
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_L,null,null,1,1), 				//xD
			new Instruction(InstrType.IN_RES,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,1), 				//xE
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_A,null,null,1,1), 				//xF
		},
		{//9x
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_B,null,null,1,2), 				//x0
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_C,null,null,1,2), 				//x1
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_D,null,null,1,2), 				//x2
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_E,null,null,1,2), 				//x3
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_H,null,null,1,2), 				//x4
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_L,null,null,1,2), 				//x5
			new Instruction(InstrType.IN_RES,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,2), 				//x6
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_A,null,null,1,2), 				//x7
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_B,null,null,1,3), 				//x8
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_C,null,null,1,3), 				//x9
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_D,null,null,1,3), 				//xA
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_E,null,null,1,3), 				//xB
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_H,null,null,1,3), 				//xC
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_L,null,null,1,3), 				//xD
			new Instruction(InstrType.IN_RES,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,3), 				//xE
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_A,null,null,1,3), 				//xF
		},
		{//Ax
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_B,null,null,1,4), 				//x0
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_C,null,null,1,4), 				//x1
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_D,null,null,1,4), 				//x2
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_E,null,null,1,4), 				//x3
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_H,null,null,1,4), 				//x4
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_L,null,null,1,4), 				//x5
			new Instruction(InstrType.IN_RES,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,4), 				//x6
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_A,null,null,1,4), 				//x7
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_B,null,null,1,5), 				//x8
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_C,null,null,1,5), 				//x9
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_D,null,null,1,5), 				//xA
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_E,null,null,1,5), 				//xB
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_H,null,null,1,5), 				//xC
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_L,null,null,1,5), 				//xD
			new Instruction(InstrType.IN_RES,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,5), 				//xE
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_A,null,null,1,5), 				//xF
		},
		{//Bx
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_B,null,null,1,6), 				//x0
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_C,null,null,1,6), 				//x1
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_D,null,null,1,6), 				//x2
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_E,null,null,1,6), 				//x3
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_H,null,null,1,6), 				//x4
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_L,null,null,1,6), 				//x5
			new Instruction(InstrType.IN_RES,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,6), 				//x6
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_A,null,null,1,6), 				//x7
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_B,null,null,1,7), 				//x8
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_C,null,null,1,7), 				//x9
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_D,null,null,1,7), 				//xA
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_E,null,null,1,7), 				//xB
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_H,null,null,1,7), 				//xC
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_L,null,null,1,7), 				//xD
			new Instruction(InstrType.IN_RES,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,7), 				//xE
			new Instruction(InstrType.IN_RES,AddressMode.AM_R,RegisterType.RT_A,null,null,1,7), 				//xF
		},
		{//Cx
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 				//x0
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 				//x1
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 				//x2
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 				//x3
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 				//x4
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 				//x5
			new Instruction(InstrType.IN_SET,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//x6
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 				//x7
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_B,null,null,1,1), 				//x8
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_C,null,null,1,1), 				//x9
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_D,null,null,1,1), 				//xA
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_E,null,null,1,1), 				//xB
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_H,null,null,1,1), 				//xC
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_L,null,null,1,1), 				//xD
			new Instruction(InstrType.IN_SET,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,1), 				//xE
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_A,null,null,1,1), 				//xF
		},
		{//Dx
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_B,null,null,1,2), 				//x0
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_C,null,null,1,2), 				//x1
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_D,null,null,1,2), 				//x2
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_E,null,null,1,2), 				//x3
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_H,null,null,1,2), 				//x4
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_L,null,null,1,2), 				//x5
			new Instruction(InstrType.IN_SET,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,2), 				//x6
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_A,null,null,1,2), 				//x7
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_B,null,null,1,3), 				//x8
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_C,null,null,1,3), 				//x9
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_D,null,null,1,3), 				//xA
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_E,null,null,1,3), 				//xB
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_H,null,null,1,3), 				//xC
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_L,null,null,1,3), 				//xD
			new Instruction(InstrType.IN_SET,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,3), 				//xE
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_A,null,null,1,3), 				//xF
		},
		{//Ex
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_B,null,null,1,4), 				//x0
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_C,null,null,1,4), 				//x1
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_D,null,null,1,4), 				//x2
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_E,null,null,1,4), 				//x3
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_H,null,null,1,4), 				//x4
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_L,null,null,1,4), 				//x5
			new Instruction(InstrType.IN_SET,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,4), 				//x6
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_A,null,null,1,4), 				//x7
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_B,null,null,1,5), 				//x8
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_C,null,null,1,5), 				//x9
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_D,null,null,1,5), 				//xA
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_E,null,null,1,5), 				//xB
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_H,null,null,1,5), 				//xC
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_L,null,null,1,5), 				//xD
			new Instruction(InstrType.IN_SET,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,5), 				//xE
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_A,null,null,1,5), 				//xF
		},
		{//Fx
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_B,null,null,1,6), 				//x0
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_C,null,null,1,6), 				//x1
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_D,null,null,1,6), 				//x2
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_E,null,null,1,6), 				//x3
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_H,null,null,1,6), 				//x4
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_L,null,null,1,6), 				//x5
			new Instruction(InstrType.IN_SET,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,6), 				//x6
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_A,null,null,1,6), 				//x7
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_B,null,null,1,7), 				//x8
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_C,null,null,1,7), 				//x9
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_D,null,null,1,7), 				//xA
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_E,null,null,1,7), 				//xB
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_H,null,null,1,7), 				//xC
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_L,null,null,1,7), 				//xD
			new Instruction(InstrType.IN_SET,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,7), 				//xE
			new Instruction(InstrType.IN_SET,AddressMode.AM_R,RegisterType.RT_A,null,null,1,7), 				//xF
		}
	};
	
	public void fetchCBInstruction() {
		int opcode = read(PC);
		currentInstruction = CB_OPCODE_TABLE[opcode/16][opcode%16];
		//System.out.printf("%s -> PREFIX %s",Utils.intToHexString(opcode),currentInstruction.toString()).println();
	}
	
	public void executeCBInstruction() {
		int hi=0,lo=0,result=0;
		int a=0,b=0,flag=0;
		switch(currentInstruction.type) {
			case IN_SWAP:
				iexec_IN_SWAP.execute();
				break;
			case IN_RES:
				iexec_IN_RES.execute();
				break;
			case IN_SET:
				iexec_IN_SET.execute();
				break;
			case IN_SRL:
				iexec_IN_SRL.execute();
				break;
			case IN_RR:
				iexec_IN_RR.execute();
				break;
			case IN_RL:
				iexec_IN_RL.execute();
				break;
			case IN_RLC:
				iexec_IN_RLC.execute();
				break;
			case IN_RRC:
				iexec_IN_RRC.execute();
				break;
			case IN_SLA:
				iexec_IN_SLA.execute();
				break;
			case IN_SRA:
				iexec_IN_SRA.execute();
				break;
			case IN_BIT:
				iexec_IN_BIT.execute();
				break;
			case IN_NONE:
				System.err.print("CB Instruction not implemented");
				System.exit(2);
				break;
			default:
				System.err.print("CB Instruction invalid");
				System.exit(2);
				break;
		}
	}
	
	InstructionExecutor iexec_IN_SWAP = () -> {
		x=0;y=0;result=0;flag=0;		
		//swaps upper 4 bits to lower
		switch(currentInstruction.mode) {
			case AM_R:
				x = fetchRegisterData(currentInstruction.reg1);
				y = (x & 0xF0) >> 4; //hi
				x = x & 0xF; //lo
				result = ((x << 4) | y) & 0xFF;
				updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(x);
				x = y & 0xF; //lo
				y = (y & 0xF0) >> 4; //hi				
				result = ((x << 4) | y) & 0xFF;
				write(fetchRegisterData(currentInstruction.reg1),result);
				break;
			default:
				System.err.println("\tSWAP INVALID ADDRESS MODE");
				System.exit(1);
		}
		
//		if(result==0) {
//			flag = flag | (1 << 7);
//		}// z dependent, n=0, h=0, c=0
//		F = flag;		
		setFlags(((result == 0) ? 1 : 0), 0, 0, 0);
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting SWAP");
	};
	
	InstructionExecutor iexec_IN_RES = () -> {
		x=0;y=0;result=0;flag=0;		
		//reset specified bit to 0
		switch(currentInstruction.mode) {
			case AM_R:
				x = currentInstruction.param;
				y = fetchRegisterData(currentInstruction.reg1);
				x = (~(1 << x)) & 0xFF;
				result = (x & y) & 0xFF;
				updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(x);
				result = (((~(1 << currentInstruction.param)) & 0xFF) & y) & 0xFF;				
				write(x,result);
				break;
			default:
				System.err.println("\tRES INVALID ADDRESS MODE");
				System.exit(1);
		}
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting RES");
	};
	
	InstructionExecutor iexec_IN_SET = () -> {
		x=0;y=0;result=0;flag=0;		
		//set specified bit to 1
		switch(currentInstruction.mode) {
			case AM_R:
				x = currentInstruction.param;
				y = fetchRegisterData(currentInstruction.reg1);
				x = (1 << x) & 0xFF;
				result = (x | y) & 0xFF;
				updateRegisterData(currentInstruction.reg1,result);
				break;
			case AM_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(x);
				result = (((1 << currentInstruction.param) & 0xFF) | y) & 0xFF;				
				write(x,result);
				break;
			default:
				System.err.println("\tSET INVALID ADDRESS MODE");
				System.exit(1);
		}
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting SET");
	};
	
	InstructionExecutor iexec_IN_SRL = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R:
				x = fetchRegisterData(currentInstruction.reg1);
				result = (x >> 1) & 0xFF;
				updateRegisterData(currentInstruction.reg1, result);
				
//				if(result == 0) {
//					flag = flag | (1 << 7);
//				}//z=dependent
//				//n=0,h=0
//				flag = flag | ((x & 0x1) << 4);//c=dependent(stores bit 0 of data)
//				F = flag;
				setFlags(((result == 0) ? 1 : 0), 0, 0, (x & 0x1));
				break;
			case AM_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(x);
				result = (y >> 1) & 0xFF;				
				write(x,result);
				
//				if(result == 0) {
//					flag = flag | (1 << 7);
//				}//z=dependent
//				//n=0,h=0
//				flag = flag | ((y & 0x1) << 4);//c=dependent(stores bit 0 of data)
//				F = flag;
				setFlags(((result == 0) ? 1 : 0), 0, 0, (y & 0x1));
				break;
			default:
				System.err.println("\tSRL INVALID ADDRESS MODE");
				System.exit(1);
		}
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting SRL");
	};
	
	InstructionExecutor iexec_IN_RR = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R:
				x = fetchRegisterData(currentInstruction.reg1);
				result = ((x >> 1) | ((F << 3) & 0x80)) & 0xFF;
				updateRegisterData(currentInstruction.reg1,result);
				
//				if(result == 0) {
//					flag = flag | (1 << 7);
//				}//z=dependent
//				//n=0,h=0
//				flag = flag | ((x & 0x1) << 4);//c=dependent(stores bit 0 of data)
//				F = flag;
				setFlags(((result == 0) ? 1 : 0), 0, 0, (x & 0x1));
				break;
			case AM_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(x);
				result = ((y >> 1) | ((F << 3) & 0x80)) & 0xFF;
				write(x,result);
				
//				if(result == 0) {
//					flag = flag | (1 << 7);
//				}//z=dependent
//				//n=0,h=0
//				flag = flag | ((y & 0x1) << 4);//c=dependent(stores bit 0 of data)
//				F = flag;
				setFlags(((result == 0) ? 1 : 0), 0, 0, (y & 0x1));
				break;
			default:
				System.err.println("\tRR INVALID ADDRESS MODE");
				System.exit(1);
		}
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting RR");
	};
	
	InstructionExecutor iexec_IN_RL = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R:
				x = fetchRegisterData(currentInstruction.reg1);
				result = ((x << 1) | ((F >> 4) & 0x1)) & 0xFF;
				updateRegisterData(currentInstruction.reg1,result);
				setFlags(((result == 0) ? 1 : 0), 0, 0, ((x & 0x80) >> 7));
				break;
			case AM_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(x);
				result = ((y << 1) | ((F >> 4) & 0x1)) & 0xFF;
				write(x,result);
				setFlags(((result == 0) ? 1 : 0), 0, 0, ((y & 0x80) >> 7));
				break;
			default:
				System.err.println("\tRR INVALID ADDRESS MODE");
				System.exit(1);
		}
		
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting RR");
	};
	
	InstructionExecutor iexec_IN_RLC = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R:
				x = fetchRegisterData(currentInstruction.reg1);
				result = ((x << 1) | ((x & 0x80) >> 7)) & 0xFF;	
				updateRegisterData(currentInstruction.reg1,result);
				setFlags(((result == 0) ? 1 : 0), 0, 0, ((x & 0x80) >> 7));
				break;
			case AM_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(x);
				result = ((y << 1) | ((y & 0x80) >> 7)) & 0xFF;
				write(x,result);
				setFlags(((result == 0) ? 1 : 0), 0, 0, ((y & 0x80) >> 7));
				break;
			default:
				break;
		}
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting RLC");
	};
	
	InstructionExecutor iexec_IN_RRC = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R:
				x = fetchRegisterData(currentInstruction.reg1);
				result = ((x >> 1) | ((x & 0x1) << 7)) & 0xFF;	
				updateRegisterData(currentInstruction.reg1,result);
				setFlags(((result == 0) ? 1 : 0), 0, 0, (x & 0x1));
				break;
			case AM_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(x);
				result = ((y >> 1) | ((y & 0x1) << 7)) & 0xFF;
				write(x,result);
				setFlags(((result == 0) ? 1 : 0), 0, 0, (y & 0x1));
				break;
			default:
				break;
		}
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting RRC");
	};
	
	InstructionExecutor iexec_IN_SLA = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R:
				x = fetchRegisterData(currentInstruction.reg1);
				result = (x << 1) & 0xFF;
				updateRegisterData(currentInstruction.reg1,result);
				setFlags(((result == 0) ? 1 : 0), 0, 0, ((x & 0x80) >> 7));
				break;
			case AM_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(x);
				result = (y << 1) & 0xFF;
				write(x,result);
				setFlags(((result == 0) ? 1 : 0), 0, 0, ((y & 0x80) >> 7));
				break;
			default:
				break;
		}
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting SLA");
	};
	
	InstructionExecutor iexec_IN_SRA = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R:
				x = fetchRegisterData(currentInstruction.reg1);
				result = ((x & 0x80) | (x >> 1)) & 0xFF;
				updateRegisterData(currentInstruction.reg1,result);
				setFlags(((result == 0) ? 1 : 0), 0, 0, (x & 0x1));
				break;
			case AM_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(x);
				result = ((y & 0x80) | (y >> 1)) & 0xFF;
				write(x,result);
				setFlags(((result == 0) ? 1 : 0), 0, 0, (y & 0x1));
				break;
			default:
				break;
		}
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting SRA");
	};
	
	InstructionExecutor iexec_IN_BIT = () -> {
		x=0;y=0;result=0;flag=0;
		switch(currentInstruction.mode) {
			case AM_R:
				x = fetchRegisterData(currentInstruction.reg1);
				result = ~((x >> currentInstruction.param) & 0x1);
				setFlags(result, 0, 1, ((F & 0x10) >> 4));
				break;
			case AM_MR:
				x = fetchRegisterData(currentInstruction.reg1);
				y = read(x);
				result = ~((y >> currentInstruction.param) & 0x1);
				setFlags(result, 0, 1, ((F & 0x10) >> 4));
				break;
			default:
				break;
		}
		PC = PC + currentInstruction.length;
		//System.out.println("\tExecuting BIT");
	};
}

interface InstructionExecutor {
	public void execute();
}
