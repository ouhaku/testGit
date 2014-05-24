package org.bob.test.algorithm;

public class SimpleRegular {
	
	public static void main(String[] args){
		String raw = "abcbcd";//"aab";
		String pattern = ".*.*";//"a.*c.*d";//"c*a*b";
		if(ismatch(raw,pattern))
			System.out.println("raw: " + raw + " pattern: " + pattern + " is matched");
		else
			System.out.println("no match");
	}

	static boolean ismatch(String raw, String pattern){
		char[] rawchar= raw.toCharArray();
		char[] patchar= pattern.toCharArray();
		return fullmatch(rawchar, patchar,0,0);
	}
	
	static boolean fullmatch(char[] raw, char[] pattern, int rawidx, int patternidx){
	
		if(patternidx==pattern.length){
			return rawidx==raw.length;
		}
		
		/*if(pattern[patternidx+1]!='*'){
			return ((raw[rawidx]==pattern[patternidx])|| 
					(pattern[patternidx]=='.' && rawidx < raw.length-1)) &&
					fullmatch(raw,pattern,rawidx+1,patternidx+1);
		}
		while ((raw[rawidx]==pattern[patternidx])|| (pattern[patternidx]=='.' && rawidx < raw.length-1)){
			if(fullmatch(raw,pattern,rawidx,patternidx+2))
				return true;
			rawidx++;
		}
		return fullmatch(raw,pattern,rawidx,patternidx+2);*/
		
		if(rawidx < raw.length && patternidx < pattern.length){
		//if current p and s matched, or ('.' also counts for match), and s is not the last(at least raw.length-2)		
			if(pattern[patternidx]=='.' || (raw[rawidx]==pattern[patternidx])){
				//if current p is not '*', step over current p and s, means not consider current p
				// any more, just like match new s and p from(rawidx+1,patternidx+1)
				return pattern[patternidx+1]!='*' ? fullmatch(raw,pattern,rawidx+1,patternidx+1):
					//if current p is '*', recursion s for '*', like 'p*' can match 'p','pp','ppp' ...
					//or '*' can stand for 0 time, means drop current p, like 'p*s' match 's' ... 
					fullmatch(raw,pattern,rawidx+1,patternidx) || fullmatch(raw,pattern,rawidx,patternidx+2);
			}
		}
		
		return pattern[patternidx+1]=='*' && fullmatch(raw,pattern,rawidx,patternidx+2);
	}
}
