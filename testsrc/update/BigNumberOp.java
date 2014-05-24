package org.bob.test.syntax;

public class BigNumberOp {
	private Integer[] value;
	
	public BigNumberOp (Integer[] val){
		value = new Integer[val.length];
		value = val;
	}
	
	public BigNumberOp (String val){
		int cursor = 0;
		int nonzero = 0;
		int len = val.length();
		//value = new int[len];
		
		/*int index = val.lastIndexOf('-');
		if (index != -1) {
		    if (index == 0 ) {
		        if (val.length() == 1)
		             throw new NumberFormatException("Zero length BigInteger");
		        cursor = 1;
		    } else {
		        throw new NumberFormatException("Illegal embedded sign character");
		    }
		}*/
		boolean first = true;
		while (cursor < len) {
			if (!Character.isDigit(val.charAt(cursor))) 
				throw new NumberFormatException("Illegal embedded sign character");
			if (Character.digit(val.charAt(cursor), 10) == 0 && first)
				nonzero++;
			else
				first = false;
			cursor++;
		}
		int numDigits = len - nonzero;
		value = new Integer[numDigits];
		for (int i=nonzero;i<len;i++)
			value[i-nonzero] = Character.digit(val.charAt(i), 10);
	}
	
	public Integer[] getValue() {
		return value;
	}
	
	public static BigNumberOp add(BigNumberOp op1, BigNumberOp op2) {
		Integer[] num1 = op1.getValue();
		Integer[] num2 = op2.getValue();
		int size = num1.length > num2.length ? num1.length : num2.length;
		Integer[] result = new Integer[size + 1];
		for (int i=0;i<result.length;i++)
			result[i] = 0;
		for (int i=0;i<num1.length;i++)
			result[size-i] = num1[num1.length-1-i];
		int sum = 0;
		int carry = 0;
		for (int i=0;i<num2.length;i++) {
			sum = result[size-i] + num2[num2.length-1-i] + carry;
			carry = 0;
			if (sum >= 10) {
				sum = sum % 10;
				carry = 1;
			}
			result[size-i] = sum;
		}
		while(carry != 0){
			for (int i=0;i<=size-num2.length;i++) {
				if (result[size-num2.length-i] + carry >= 10) {
					result[size-num2.length-i] = (result[size-num2.length-i] + carry)%10;
					carry = 1;
				} else {
					result[size-num2.length-i] = result[size-num2.length-i] + carry;
					carry = 0;
					break;
				}					
			}			
		}
		return new BigNumberOp(result);
	}
	
	public static BigNumberOp mutiply(BigNumberOp op1, BigNumberOp op2) {
		Integer[] num1 = op1.getValue();
		Integer[] num2 = op2.getValue();
		int size = num1.length + num2.length;
		Integer[] result = new Integer[size+1];
		for (int i=0;i<result.length;i++)
			result[i] = 0;
		for (int i=0;i<num1.length;i++)
			result[size-i] = num1[num1.length-1-i];
		int mutiply = 0;
		int carry = 0;
		for (int i=0;i<num2.length;i++) {
			for (int j=0;j<num1.length;j++) {
				if (i == 0)
					mutiply = num2[num2.length-1-i] * num1[num1.length-1-j] + carry;
				else 
					mutiply = num2[num2.length-1-i] * num1[num1.length-1-j] + result[size-j-i] + carry;
				carry = 0;
				if (mutiply >= 10) {
					carry = (mutiply - mutiply%10)/10;
					mutiply = mutiply % 10;
				}
				result[size-j-i] = mutiply;
			}
			while(carry != 0){
				for (int k=0;k<=size-num1.length-i;k++) {
					if (result[size-num1.length-i-k] + carry >= 10) {
						result[size-num1.length-i-k] = (result[size-num1.length-i-k] + carry)%10;
						carry = 1;
					} else {
						result[size-num1.length-i-k] = result[size-num1.length-i-k] + carry;
						carry = 0;
						break;
					}					
				}			
			}			
		}
		return new BigNumberOp(result);
	}
	
	public String toString(){
		String str = "";
		Integer[] temp= this.value;
		int start = 0;
		while(temp[start]==0)
			start++;
		for(int i = start; i < this.value.length;i++)
			str = str + temp[i];
		return str;
	}
}
