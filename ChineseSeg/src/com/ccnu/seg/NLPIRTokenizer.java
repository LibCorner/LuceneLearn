package com.ccnu.seg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.util.CharacterUtils;
import org.apache.lucene.analysis.util.CharacterUtils.CharacterBuffer;

public class NLPIRTokenizer extends Tokenizer {

	// 词元文本属性
	private CharTermAttribute termAtt;
	// 词元位移属性
	private OffsetAttribute offsetAtt;
	// 记录最后一个词元的结束位置
	private int finalOffset=0;
	

	Iterator<Lexeme> iter=null;
	int flag=0;

	public NLPIRTokenizer() {
		offsetAtt = addAttribute(OffsetAttribute.class);
		termAtt = addAttribute(CharTermAttribute.class);
	}

	@Override
	public boolean incrementToken() throws IOException {
		if(flag==0){
			NLPIRSegmenter seg = new NLPIRSegmenter();
			List<Lexeme> results = seg.getLexeme(readToStr(input));
			input.reset();
			iter = results.iterator();
			flag=1;
			seg.exit();
		}
		// 清除所有的词元属性
		clearAttributes();
		if (iter.hasNext()) {
			Lexeme nextLexeme = iter.next();
			// 将Lexeme转成Attributes
			// 设置词元文本
			termAtt.append(nextLexeme.getLexemeText());
			// 设置词元长度
			termAtt.setLength(nextLexeme.getLength());
			// 设置词元位移
			offsetAtt.setOffset(nextLexeme.getBeginPosition(),
					nextLexeme.getEndPosition());
			// 记录分词的最后位置
			finalOffset = nextLexeme.getEndPosition();
			// 返会true告知还有下个词元
			return true;
		}
		// 返会false告知词元输出完毕
		return false;
	}

	@Override
	public void reset() throws IOException {
		super.reset();
		flag = 0;
		finalOffset = 0;
	}

	private String readToStr(Reader r) throws IOException {
		BufferedReader br = new BufferedReader(r);
		StringBuffer sb = new StringBuffer();
		String line = br.readLine();
		while (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		return sb.toString();
	}

}
