package lucene.learn.statistic;

import java.io.File;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class TermsEnumStatistic {
	String indexPath="data/index";
	public static void main(String[] args) throws Exception {
		TermsEnumStatistic tes=new TermsEnumStatistic();
		tes.docStatistic();
	}
	
	/**
	 * 统计某个所有Term的信息，包括出现改词的文档数和该词在所有文档中出现的总数
	 * @throws Exception 
	 */
	public void docStatistic() throws Exception{
		//使用DirectoryReader.open()方法创建IndexReader对象
		IndexReader ir=DirectoryReader.open(FSDirectory.open(new File(indexPath).toPath()));
		
		//Terms对象中保存了该域中的文档和词的统计信息,可以使用Terms.iterator()方法遍历所有的Term
		Terms ts=MultiFields.getTerms(ir, "content");
		//统计该域中的文档总数
		long docNum=ts.getDocCount();
		
		//遍历所有的Term, TermsEnum对象中包含了该Term的统计信息，包括出现该词的文档数，该词在所有文档中出现的总次数等
		TermsEnum te=ts.iterator();
		while((te.next())!=null){
			//该词的文本
			BytesRef term=te.term();
			System.out.println(new String(term.bytes));
			//统计出现改词的文档数
			long occurDocNum=te.docFreq();
			System.out.println("出现该词的文档数："+occurDocNum);
			//统计该词出现的总次数
			long termNum=te.totalTermFreq();
			System.out.println("总次数:"+termNum);
		}

	}


}
