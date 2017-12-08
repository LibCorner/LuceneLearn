package lucene.learn.statistic;

import java.io.File;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

/**
 *使用IndexReader里的方法统计文档和词项
 */
public class IndexReaderStatistic {
	String indexPath="data/index";
	public static void main(String[] args) throws Exception {
		IndexReaderStatistic irs=new IndexReaderStatistic();
		irs.numStatistic();
		irs.docStatistic();
	}
	/**
	 * 统计索引中所有和文档和词的数目
	 * @throws Exception
	 */
	public void numStatistic() throws Exception{
		//使用DirectoryReader.open()方法创建IndexReader对象
		IndexReader ir=DirectoryReader.open(FSDirectory.open(new File(indexPath).toPath()));
		//统计索引中所有文档的数目
		int docNum=ir.numDocs();
		System.out.println("docNum:"+docNum);
		
		//统计Field中文档的数目，文档至少要有一个词在该Field中
		long fieldDocNum=ir.getDocCount("content");
		System.out.println("content Field DocNum:"+fieldDocNum);
		
		//对于某个Field中所有词,统计出现该词的文档数,并求和, 
		//返回的是所有Term的TermsEnum.docFreq()的和, TermsEnum.docFreq()返回的是包含当前Term的文档数,
		long sumDocNum=ir.getSumDocFreq("content");
		System.out.println("content term occur sumDocNum:"+sumDocNum);
		
		//对于每个的词，统计改词在所有文档中出现的总次数，并对所有词求和
		long sumTermNum=ir.getSumTotalTermFreq("content");
		System.out.println("sumTermNum:"+sumTermNum);
		
		//统计索引中某个词条出现的总次数
		long totalTermNum=ir.totalTermFreq(new Term("content","这"));
		System.out.println("totalTermNum:"+totalTermNum);
	}
	
	/**
	 * 统计某篇文档中词的信息
	 * @throws Exception 
	 */
	public void docStatistic() throws Exception{
		//使用DirectoryReader.open()方法创建IndexReader对象
		IndexReader ir=DirectoryReader.open(FSDirectory.open(new File(indexPath).toPath()));
		
		//返回某个文档在某个域中的Term vector,如果term vector没有索引就返回null,Terms对象中保存了该域中的文档和词的统计信息
		//可以使用Terms.iterator()方法遍历所有的Term
		Terms ts=ir.getTermVector(3,"content");  //文档id为1，filed为"content"
		//Terms ts=MultiFields.getTerms(ir, "content");
		
		//遍历所有的Term, TermsEnum对象中包含了该Term的统计信息，包括出现改词的文档数，改词在所有文档中出现的总次数等
		TermsEnum te=ts.iterator();
		te.next();
		//该词的文本
		BytesRef term=te.term();
		System.out.println(new String(term.bytes));
		//统计出现改词的文档数
		long occurDocNum=te.docFreq();
		System.out.println("该词出现的文档数："+occurDocNum);
	}

}
