package com.ccnu.seg;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class NLPIRAnalyzer extends Analyzer{
	
	public NLPIRAnalyzer() {
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		final Tokenizer source=new NLPIRTokenizer();
		return new TokenStreamComponents(source);
	}
	
	public static void main(String[] args) throws IOException {
		final String text="This is a demo of the TokenStream API";
		
		NLPIRAnalyzer analyzer=new NLPIRAnalyzer();
		TokenStream stream=analyzer.tokenStream("field",new StringReader(text));
		
		CharTermAttribute termAtt=stream.addAttribute(CharTermAttribute.class);
		stream.reset();
		while(stream.incrementToken()){
			System.out.println(termAtt.toString());
		}
		stream.end();
		stream.close();
	}

}
