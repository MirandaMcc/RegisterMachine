import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterMachine {

	//R0...Rn values
	private int[] registers;
	
	//list of instructions L0 ---> Ln
	private Instruction[] instructions;
	
	public RegisterMachine(int[] regs, Instruction[] instr){
		this.registers = regs;
		this.instructions = instr;
	}
	
	public void start(){
		int label = 0;
		while(label != -1){
			if(label > instructions.length)
			{
				System.err.println("Invalid Instruction Label: "+ label);
				return;
			}
			label = instructions[label].execute(registers);

			System.out.println(Arrays.toString(registers));
		}
	}
	
	public static void main(String[] args) {
		//create register machine
		//addition r1 and r2 into r0
		int[] regs = {0,3,2};
		Instruction[] instr = {
				new CheckDecr(1,1,3),
				new CheckDecr(2,2,5),
				new Increment(0,0), 
				new CheckDecr(2,4,5),
				new CheckDecr(0,4,5),
				new Halt()};
		RegisterMachine rm = new RegisterMachine(regs,instr);
		rm.start();
	}
	
}

abstract class Instruction {
	/**
	 * returns label number for next instruction
	 * or -1 if execution should terminate
	 */
	public abstract int execute(int[] registers);
}

class Increment extends Instruction {
	private int reg, label;
	
	//Rr+ -> L
	public Increment(int r, int l){
		this.reg = r;
		this.label = l;
	}
	
	@Override
	public int execute(int[] registers) {
		registers[reg]++;
		return label;
	}
	
}

class Halt extends Instruction {

	@Override
	public int execute(int[] registers) {
		return -1;
	}
	
}

class CheckDecr extends Instruction {
	private int reg, label1, label2;
	
	//Rr- -> L1, L2
	public CheckDecr(int r, int l1, int l2){
		this.reg = r;
		this.label1 = l1;
		this.label2 = l2;
	}
	
	@Override
	public int execute(int[] registers) {
		if(registers[reg] > 0)
		{
			registers[reg]--;
			return label1;
		}
		else
			return label2;
	}
}