package lucene.learn.statistic;

import lucene.learn.search.LuIndexSearcher;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermContext;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.BytesRef;

/**
 *使用TermStatistics来统计某个词项的统计信息,
 *可以使用IndexSearcher.termStatistics()来获得该类的实例，
 */
public class TermStatistic {
	String indexPath="data/index";
	public static void main(String[] args) throws Exception {
		TermStatistic ts=new TermStatistic();
		ts.termStatistic();
	}
	public void termStatistic() throws Exception{
		//创建IndexSearcher
		IndexSearcher is=new LuIndexSearcher().buildBaseIndexSearcher(indexPath);
		Term t=new Term("content","3");
		
		//获取TermsStatistics对象
		TermStatistics ts=is.termStatistics(t, new TermContext(is.getIndexReader().getContext()));
		//term的文本
		BytesRef term=ts.term();
		System.out.println("Term文本:"+new String(term.bytes));
		
		//统计出现该词的文档数
		long docFreq=ts.docFreq();
		System.out.println(docFreq);
		
		//统计词项出现的总次数
		long termFreq=ts.totalTermFreq();
		System.out.println("TermFreq:"+termFreq);
	}
	

}
