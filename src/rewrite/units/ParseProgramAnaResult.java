package rewrite.units;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
* this is used to read rewriting specifications
* currently only support for only app
* but we can rewrite multiple functions
*/
public class ParseProgramAnaResult {

	ArrayList<String> lines;
	// this is used for the rewriting
	public String apkName;
	public ArrayList<String> className;
	public ArrayList<String> methodName;
	public ArrayList<String> stmtName;

	public static ParseProgramAnaResult instance;
	
	public static synchronized ParseProgramAnaResult getInstance() throws IOException {
		if (instance == null) {
			instance = new ParseProgramAnaResult();
		}
		return instance;
	}
	
	
	//this is the construction function, we read the rewriting 
	public ParseProgramAnaResult() throws IOException {
		
		lines = new ArrayList<String>();
		apkName = "";
		className = new ArrayList<String>();
		methodName = new ArrayList<String>();
		stmtName = new ArrayList<String>();
		
		readResultFromPA();
		runAnalysisForRewritingGuidelines();
		printAnalysisResult();
		
	}

	public void readResultFromPA() throws IOException {
		FileReader fileReader = new FileReader(new File("rewriteTextSummary.txt"));

		BufferedReader br = new BufferedReader(fileReader);

		String line = null;
		// if no more lines the readLine() returns null
		while ((line = br.readLine()) != null) {

			// reading lines until the end of the file
			String cur = line.trim();
			lines.add(cur);
			// System.out.println(cur);

		}
	}

	
	public void readResultFromPA(String s) throws IOException {
		FileReader fileReader = new FileReader(new File(s));

		BufferedReader br = new BufferedReader(fileReader);

		String line = null;
		// if no more lines the readLine() returns null
		while ((line = br.readLine()) != null) {

			// reading lines until the end of the file
			String cur = line.trim();
			if (!line.startsWith("#"))		
			    lines.add(cur);
			// System.out.println(cur);

		}
	}

	public void runAnalysisForRewritingGuidelines() {

		for (int i = 0; i < this.lines.size(); i++) {

			if (lines.get(i).equals("APKname")) {
				this.apkName = lines.get(i + 1);
			}

			if (lines.get(i).equals("classname")) {
				this.className.add(lines.get(i + 1));
			}

			if (lines.get(i).equals("methodname")) {
				this.methodName.add(lines.get(i + 1));
			}

			if (lines.get(i).equals("StmtMethod")) {
				this.stmtName.add(lines.get(i + 1));
			}
		}
	}

	public void printAnalysisResult() {

		System.out.println("==========  REWRITE APP IS  ==========");
		System.out.println(apkName);
 
		
		System.out.println("========== STMTS TO REWRITE ==========");
		for (int i = 0; i < this.className.size(); i++) {
			System.out.println(className.get(i));
			System.out.println(methodName.get(i));
			System.out.println(stmtName.get(i));
		}

	}

	public static void main(String[] args) throws IOException {

		/*
		ParseProgramAnaResult PPAR = new ParseProgramAnaResult();
		// default path
		PPAR.readResultFromPA();
		PPAR.runAnalysisForRewritingGuidelines();
		PPAR.printAnalysisResult();*/
		
		ParseProgramAnaResult i = ParseProgramAnaResult.getInstance();
	}

}

