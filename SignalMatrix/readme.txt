------------------------------------------------------------------------------------------
3x2 signal matrix
------------------------------------------------------------------------------------------
Input:- 3
Output:- 2

Route control chart
		Handle Position
I1		I1 && ~I2 && ~I3
I2		I2 && ~I1 && ~I3
I3		I3 && ~I2 && ~I1

SW1		sw1 && ~sw2 && ~sw3
sw2		sw2 && ~sw1 && ~sw3
sw3 	sw3 && ~sw1 && ~sw2

I1-SW1: I1 && sw1
I2-SW2: I2 && sw2
I3-SW3: I3 && sw3

output:-
O_LHS-GREEN -> (I1-sw1 || I2-sw2 || I3-sw3) && Signal_L
O_RHS-GREEN	-> (I1-sw1 || I2-sw2 || I3-sw3) && Signal_R

Locked:-> O_LHS-GREEN || O_RHS-GREEN

------------------------------------------------------------------------------------------------------
3x1 signal matrix
------------------------------------------------------------------------------------------------------
I1		I1 && ~I2 && ~I3
I2		I2 && ~I1 && ~I3
I3		I3 && ~I2 && ~I1

SW1		sw1 && ~sw2 && ~sw3
sw2		sw2 && ~sw1 && ~sw3
sw3 	sw3 && ~sw1 && ~sw2

I1-SW1: I1 && sw1
I2-SW2: I2 && sw2
I3-SW3: I3 && sw3

Output:-> (I1-sw1 || I2-sw2 || I3-sw3)
Locked:-> Output

----------------------------------------------------------------------------------------------------------
2x1 signal matrix
----------------------------------------------------------------------------------------------------------
Input	2
Output	1
			O					O																	
		I1	Handle-1 (H1)		I1	H1																		
		I2	Handle-2 (H2)		I2	H2																		
			Handle-3 (H3)																					

Route control chart																									
Source		Handle position 	Output
I1			H1, ~H2, ~H3		Yes
I2			~H1, H2, ~H3		Yes
																									
Logic design
O ->	I1 * H1-SW + I2 * H2-SW
Lock ->	Button

---------------------------------------------------------------------------------------------------------
1x2 signal matrix
---------------------------------------------------------------------------------------------------------
S1-LHS-GREEN = S1 OK, O1 OK, LHS OK, RHS not OK
S1-RHS-GREEN = S1 OK, O1 OK, RHS OK, LHS not OK

S1-LHS = (!S1-LOCK, S1-input-LHS) || (S1-LOCK && S1-LHS)
																									