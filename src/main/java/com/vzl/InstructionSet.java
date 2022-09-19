package com.vzl;

import com.vzl.Instruction.AddressMode;
import com.vzl.Instruction.ConditionType;
import com.vzl.Instruction.InstrType;
import com.vzl.Registers.RegisterType;

public class InstructionSet {
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
			new Instruction(InstrType.IN_ADD,AddressMode.AM_R_R,RegisterType.RT_HL,RegisterType.RT_SP,null,1,0), 			//x9
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
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 				//x8
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 				//x9
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 				//xA
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 				//xB
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 				//xC
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 				//xD
			new Instruction(InstrType.IN_RRC,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//xE
			new Instruction(InstrType.IN_RRC,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 				//xF
		},
		{//1x
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_B,null,null,1,0), 					//x0
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_C,null,null,1,0), 					//x1
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_D,null,null,1,0), 					//x2
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_E,null,null,1,0), 					//x3
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_H,null,null,1,0), 					//x4
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_L,null,null,1,0), 					//x5
			new Instruction(InstrType.IN_RL,AddressMode.AM_MR,RegisterType.RT_HL,null,null,1,0), 				//x6
			new Instruction(InstrType.IN_RL,AddressMode.AM_R,RegisterType.RT_A,null,null,1,0), 					//x7
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
}
