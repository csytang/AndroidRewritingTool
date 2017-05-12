package rewrite.units;


import java.util.Iterator;
import soot.Body;
import soot.Local;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.AbstractStmtSwitch;
import soot.jimple.AssignStmt;
import soot.jimple.IfStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;
import soot.util.Chain;

/*
 * 
 *  The help function to print all the jimple body and local variable 
 *  ke tian @ 2016
 * 
 * 
 */
public class helperDebug {

    public static void printBodyInfo(final Body body)
    {
    	
		final SootMethod method = body.getMethod();
		
		//if(method.toString().equals("<example.tesst.inst.MainActivity: void openyaogroupWeb(android.content.Intent)>")){
		System.out.println("++++ Print the Method Signature ++++ ");	
		System.out.println(method.getSignature());
		
		
		System.out.println("++++ Print the Body ++++ ");			
		System.out.println(body.toString());
			
			
		Chain<Local> locals = body.getLocals();
		System.out.println("++++ Print the Locals ++++ ");	
		for(Iterator<Local> iter = locals.snapshotIterator(); iter.hasNext();) 
		{
			Local l = iter.next();
			System.out.println("Local:" + l.getType().toString() + "  " +  l.toString());
		}
			
    	
    }
    
    // We use Jimple IR here
    // Focus on the invoke and assignment statements
    public static void printUnitInfo(final Unit unit)
    {
    	Stmt stmt = (Stmt) unit;
    	//System.out.println(stmt.toString());
    	
    	unit.apply(new AbstractStmtSwitch() {
			
			public void caseInvokeStmt(InvokeStmt stmt) {
			      
				System.out.println(stmt.toString());
				System.out.println("+++ the invoke statement +++");
				InvokeExpr invokeExpr = stmt.getInvokeExpr();
				System.out.println("method name:  " + invokeExpr.getMethod().getName().toString());
				System.out.println("arguments " + invokeExpr.getArgs().toString());
			}
			
			public void caseAssignStmt(AssignStmt stmt)
		    {
				
				System.out.println(stmt.toString());
				System.out.println("+++ the assignment statement +++");
		    } 
			
			public void caseIfStmt(IfStmt stmt)
			{
				System.out.println(stmt.toString());
				System.out.println("+++ the if conodition statement +++");
			}
			
			
        });
    }

}
