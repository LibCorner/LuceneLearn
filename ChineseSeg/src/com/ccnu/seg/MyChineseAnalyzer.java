package com.ccnu.seg;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;



public class MyChineseAnalyzer extends Analyzer{
	public static void main(String[] args) throws IOException {
		String seperator="|";
		String text="w123 w234,hello 12345";
		//Analyzer analyzer=new CJKAnalyzer();//二分法
		Analyzer analyzer=new StandardAnalyzer();//单字分词
		//Analyzer analyzer=new SimpleAnalyzer();//按标点符号，空格等分词
		
		//Analyzer analyzer=new NLPIRAnalyzer();
		
		StringBuffer sb=new StringBuffer();
	    //分词
		TokenStream ts=analyzer.tokenStream("ss", new StringReader(text));
		ts.reset();
		CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);

		while(ts.incrementToken()){
			sb.append(term.toString()+seperator);
		}
		System.out.println(sb.toString());
		ts.close();
		analyzer.close();
	}


	@Override
	protected TokenStreamComponents createComponents(String arg0) {
		return new TokenStreamComponents(new LowerCaseTokenizer());
	}

}
