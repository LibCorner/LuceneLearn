package lucene.learn.analyzer;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

/**
 * Lucene中的分词器
 *
 */
public class LuAnalyzer {
	public static void main(String[] args) throws Exception {
		String text="汇聚起实现中国梦的磅礴力量——习近平总书记关于知识分子的讲话引起强烈反响";
		LuAnalyzer a=new LuAnalyzer();
		a.tokenize(text);
	}
	/**
	 *1.大部分分词器对所有的Fields执行相同的分词操作，PerFieldAnalyzerWrapper可以用来对不同的Field指定不同的分词器。
     *2.Analyzer与CharFilter, Tokenizer 和TokenFilter的区别：
	 *   Analyzer是分词链的工厂，它本身不处理文本。Analyzer构造CharFilter, Tokenizer,和TokenFilter。Analyzer主要有两个任务：生成TokenStream对象，TokenStream可以接受一个reader然后产生tokens； 另一个任务是包装或预处理Reader对象。
	 *   CharFilter是Reader的子类，支持偏移跟踪（offset tracking）,为每个字符提供正确的偏移。这个功能在高亮原始文本时很有用。
	 *   Tokenizer是一个TokenStream，负责把输入的文本分割成tokens，很多情况下，Analyzer使用Tokenizer作为分词过程的第一步，然而，如果要在分词之前修改文本，需要使用CharFilter
	 *   TokenFilter也是一个TokenStream，负责修改由Tokenizer创建的tokens，一般的修改包括：检测、词干化、同义词注、以及case folding
	 * @param text
	 * @throws Exception 
	 */
	public void tokenize(String text) throws Exception{
		//lucene中的分词器都要继承抽象类Analyzer
		Analyzer a=new CJKAnalyzer();  //二分法
		//Analyzer a=new StandardAnalyzer();  //单字分词
		//Analyzer a=new SimpleAnalyzer();  //按照标点，空格来分词
		
		StringBuffer sb=new StringBuffer();
		//分词,TokenStream可以接收reader然后分词产生tokens
		TokenStream ts=a.tokenStream("field", new StringReader(text));
		//开始的时候必须调用，清理TokenStream的状态
		ts.reset();
		//TokenStream继承自AttributeSource，AttributeSource可以为TokenStream提供对所有的token的Attribute的访问权限
		//Attribute保存了关于一个文本token的特定信息，
		//比如：CharTermAttribute包含token的词条文本，OffsetAttribute包含token的开始和结束字符偏移
		CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
		OffsetAttribute offset=ts.getAttribute(OffsetAttribute.class);
		//遍历ts中的所有的token
		while(ts.incrementToken()){
			sb.append(term.toString()+"("+offset.startOffset()+","+offset.endOffset()+")|");
		}
		System.out.println(sb.toString());
		//关闭TokenStream
		ts.close();
		a.close();
	}

}
